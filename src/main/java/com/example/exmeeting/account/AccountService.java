package com.example.exmeeting.account;

import com.example.exmeeting.account.dto.LoginDto;
import com.example.exmeeting.account.dto.SignupDto;
import com.example.exmeeting.account.dto.TokenInfoDto;
import com.example.exmeeting.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
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
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenInfoDto tokenInfo = jwtTokenProvider.generateToken(authentication);
        return tokenInfo;
    }

    private void signupDuplicateCheck(SignupDto signupDto) {
        Optional<Account> existEmail = accountRepository.findByEmail(signupDto.getEmail());
        Optional<Account> existNickname = accountRepository.findByNickname(signupDto.getNickname());

        if (existEmail.isPresent()) {
            throw new IllegalArgumentException("登録しているemailです");
        } else if (existNickname.isPresent()) {
            throw new IllegalArgumentException("登録しているnicknameです");
        }
    }
}
