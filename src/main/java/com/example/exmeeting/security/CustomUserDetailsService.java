package com.example.exmeeting.security;

import com.example.exmeeting.account.Account;
import com.example.exmeeting.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("登録してないEmailです"));
    }

    public UserDetails createUserDetails(Account account) {
        return User.builder()
                .username(account.getEmail())
                .password(passwordEncoder.encode(account.getPassword()))
                .roles(String.valueOf(account.getAccountRole()))
                .build();
    }
}
