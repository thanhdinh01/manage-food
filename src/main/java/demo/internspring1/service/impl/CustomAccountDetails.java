package demo.internspring1.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import demo.internspring1.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomAccountDetails implements UserDetails {
    private int accountId;

    private String userName;

    @JsonIgnore
    private String password;

    private String avatar;

    private Collection<? extends GrantedAuthority> decentralizations;

    public static UserDetails build(Account account) {
        List<GrantedAuthority> authorities = account.getDecentralizations().stream()
                .map(decentralization -> new SimpleGrantedAuthority(decentralization.getAuthorityName().name()))
                .collect(Collectors.toList());
        return new CustomAccountDetails(
                account.getAccountId(),
                account.getUserName(),
                account.getPassword(),
                account.getAvatar(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return decentralizations;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
