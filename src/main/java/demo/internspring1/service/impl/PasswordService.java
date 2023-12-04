package demo.internspring1.service.impl;

import demo.internspring1.dto.request.PasswordRequest;
import demo.internspring1.exception.NotFoundException;
import demo.internspring1.model.Account;
import demo.internspring1.repository.AccountRepo;
import demo.internspring1.service.IPasswordService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordService implements IPasswordService {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final int EXPIRATION_PW_TOKEN = 2;

    @Override
    public String passwordResetRequest(String email) {
        Optional<Account> accountOptional = accountRepo.findAccByEmail(email);
        if (accountOptional.isEmpty()) {
            throw new NotFoundException("this email not existed!");
        }

        String pwToken = UUID.randomUUID().toString();

        Account account = accountOptional.get();
        account.setResetPasswordToken(pwToken);
        account.setResetPasswordTokenExpiry(LocalDateTime.now().plusMinutes(EXPIRATION_PW_TOKEN));
        accountRepo.save(account);
        String htmlContent = """
                    <div>
                        <a href="http://localhost:8080/api/v1/auth/reset-password?token=%s" target="_blank">Click link to verify password reset</a>
                    </div>
                """.formatted(pwToken);

        try {
            emailService.sendOtpEmail(email, "Verification password reset", pwToken, htmlContent);
        } catch (MessagingException e) {
            throw new RuntimeException("cannot to send token reset password, please try again: {} ", e);
        }
        return "send request to reset password successfully!, Please verify email within 2 minute";
    }

    @Override
    public String passwordResetVerification(PasswordRequest passwordRequest, String token) {
        String validateStr = validatePwResetToken(token);
        System.out.println(validateStr);
        if (!validateStr.equals("valid")) {
            System.out.println("validate bi sai");
            return validateStr;
        }

        Optional<Account> accountOptional = accountRepo.findAccByPWResetToken(token);
        if (accountOptional.isPresent()) {
            System.out.println(accountOptional.get().getUserName());
            updatePWForReset(accountOptional.get(), passwordRequest.getNewPassword());
            return "Password has been reset successfully";
        }

        return validateStr;
    }

    public String validatePwResetToken(String token) {
        Optional<Account> accountOptional = accountRepo.findAccByPWResetToken(token);
        if (accountOptional.isEmpty()) {
            System.out.println("acc khong ton tai");
            return "this email not existed!";
        }
        Account account = accountOptional.get();
        System.out.println(account.getResetPasswordTokenExpiry().toLocalTime() + ":" + LocalDateTime.now().toLocalTime());
        System.out.println(Duration.between(LocalDateTime.now(), account.getResetPasswordTokenExpiry()).getSeconds());
        if (Duration.between(LocalDateTime.now(), account.getResetPasswordTokenExpiry()).getSeconds() <= 0) {
            System.out.println("token het han");
            return "Link already expired, resend link";
        }
        return "valid";
    }

    public void updatePWForReset(Account account, String newPW) {
        account.setPassword(passwordEncoder.encode(newPW));
        accountRepo.save(account);
    }
}
