package com.example.exmeeting.account;

import com.example.exmeeting.account.dto.LoginDto;
import com.example.exmeeting.account.dto.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public Account createAccount(SignupDto signupDto) {
        Account newAccount = modelMapper.map(signupDto, Account.class);
        passwordEncoder.encode(newAccount.getPassword());
        accountRepository.save(newAccount);
        return newAccount;
    }

    public Account login(LoginDto loginDto) {
        Account logngAccount = accountRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                ()-> new IllegalArgumentException("登録してないEmailです"));
        if (!passwordEncoder.matches(loginDto.getPassword(), logngAccount.getPassword())) {
            throw new IllegalArgumentException("間違ってるPasswordです" );
        };
        return logngAccount;
    }
}
