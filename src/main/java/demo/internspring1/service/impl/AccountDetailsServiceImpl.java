package demo.internspring1.service.impl;

import demo.internspring1.model.Account;
import demo.internspring1.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AccountDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kiểm tra xem user có tồn tại trong database không?
        Account account = accountRepo.findByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return CustomAccountDetails.build(account);
    }
}
