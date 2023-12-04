package demo.internspring1.service.impl;

import demo.internspring1.dto.request.ClientRegisterRequest;
import demo.internspring1.dto.request.LoginRequest;
import demo.internspring1.exception.NotFoundException;
import demo.internspring1.model.Account;
import demo.internspring1.model.Decentralization;
import demo.internspring1.model.RoleName;
import demo.internspring1.model.User;
import demo.internspring1.repository.AccountRepo;
import demo.internspring1.repository.RoleRepo;
import demo.internspring1.service.IAccountService;
import demo.internspring1.util.OtpUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private OtpUtil otpUtil;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String nameOfUser;

    @Override
    @Transactional
    public String register(ClientRegisterRequest clientRequest) {

        Optional<Account> accountOptional = accountRepo.findAccByEmail(clientRequest.getEmail());
        if (accountOptional.isPresent()) {
            return "The email is existed";
        }

        nameOfUser= clientRequest.getName();

        System.out.println("service register");
        String otp = otpUtil.generateOtp();
        String htmlContent= """
                    <div>
                        <a href="http://localhost:8080/api/v1/auth/verify-account?email=%s&otp=%s" target="_blank">Click link to verify </a>
                    </div>
                """.formatted(clientRequest.getEmail(), otp);
        try {
            emailService.sendOtpEmail(clientRequest.getEmail(),"Verify Register Account", otp, htmlContent);
        } catch (MessagingException e) {
            throw new RuntimeException("cannot to send otp, please try again ");
        }

        String encodedPW = passwordEncoder.encode(clientRequest.getPassword());
        Account account = new Account();
        account.setUserName(clientRequest.getEmail());
        account.setPassword(encodedPW);
        account.setOtp(otp);
        account.setOtpGeneratedTime(LocalDateTime.now());

        Set<Integer> strRoles = clientRequest.getDecentralizationSet();
        Set<Decentralization> roles = new HashSet<>();
        if(strRoles == null){
            Decentralization userRole = roleRepo.findByName(RoleName.ROLE_USER.name()).orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(userRole);
        }
        strRoles.forEach(role -> {
            switch (role) {
                case 1:
                    System.out.println("role admin");
                    Decentralization adminRole = roleRepo.findByName(RoleName.ROLE_ADMIN.name()).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;
                default:
                    System.out.println("role user");
                    Decentralization userRole = roleRepo.findByName(RoleName.ROLE_USER.name()).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
            }
        });
        account.setDecentralizations(roles);
        accountRepo.save(account);
        return "User registration successfully!, Please verify email within 1 minute";
    }

    private String getNameFromClientRequest(String name){
        return name;
    }

    @Override
    public String verifyAccount(String email, String otp) {
        Optional<Account> accountOptional = accountRepo.findAccByEmail(email);
        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not found with this email");
        }
        Account account = accountOptional.get();

        if (Objects.equals(account.getOtp(), otp) && Duration.between(account.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < 60) {
            account.setStatus(1);
            User user = new User();
            user.setUserName(nameOfUser);
            user.setEmail(email);
            user.setAccount(account);
            account.setUser(user);
            accountRepo.save(account);
            return "OTP verified, you can login";
        }
        return "OTP is expired, please rgenerate other otp and try again";
    }

    @Override
    public String regenerateOtp(String email) {
        Optional<Account> accountOptional = accountRepo.findAccByEmail(email);
        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not found with this email");
        }
        Account account = accountOptional.get();

        String otp = otpUtil.generateOtp();
        String htmlContent= """
                    <div>
                        <a href="http://localhost:8080/verify-account?email=%s&otp=%s" target="_blank">Click link to verify </a>
                    </div>
                """.formatted(email, otp);
        try {
            emailService.sendOtpEmail(email,"Verify Register Account", otp, htmlContent);
        } catch (MessagingException e) {
            throw new RuntimeException("cannot to send otp, please try again ");
        }

        account.setOtp(otp);
        account.setOtpGeneratedTime(LocalDateTime.now());
        accountRepo.save(account);
        return "Email sent. Please verify email within 1 minute";
    }

}
