package com.example.exmeeting.account;

import com.example.exmeeting.account.dto.LoginDto;
import com.example.exmeeting.account.dto.SignupDto;
import com.example.exmeeting.account.dto.TokenInfoDto;
import com.example.exmeeting.security.UserAccount;
import com.example.exmeeting.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public Account createAccount(SignupDto signupDto) {
        signupDuplicateCheck(signupDto);
        Account newAccount = Account.builder()
                .nickname(signupDto.getNickname())
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword())).build();
        Account createAccount = accountRepository.save(newAccount);
        return createAccount;
    }

    public TokenInfoDto login(LoginDto loginDto) {
        Account account = checkIdAndPassword(loginDto);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                new UserAccount(account), account.getPassword(),  List.of(new SimpleGrantedAuthority("ROLE_USER")));
        TokenInfoDto tokenInfo = jwtTokenProvider.generateToken(authenticationToken);
        return tokenInfo;
    }

    private Account checkIdAndPassword(LoginDto loginDto) {
        Account account = accountRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("email not found")
        );
        if (!passwordEncoder.matches(loginDto.getPassword(), account.getPassword())){
            throw new IllegalArgumentException("??????????????????????????????");
        }
        return account;
    }

    private void signupDuplicateCheck(SignupDto signupDto) {
        this.accountRepository.findByEmail(signupDto.getEmail()).ifPresent(e -> {
                    throw new IllegalArgumentException("??????????????????email??????");
                });
        this.accountRepository.findByNickname(signupDto.getNickname()).ifPresent(e -> {
                    throw new IllegalArgumentException("??????????????????nickname??????");
                });
    }
}
