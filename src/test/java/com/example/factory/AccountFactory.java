package com.example.factory;

import com.example.exmeeting.account.Account;
import com.example.exmeeting.account.AccountRepository;
import com.example.exmeeting.account.AccountService;
import com.example.exmeeting.account.dto.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountFactory {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    public Account createAccountSeoul(String email) {
        SignupDto signupDto = new SignupDto(
                "seoul",
                "test123",
                "test1234@email.com"
        );
        Account account = accountService.createAccount(signupDto);
        accountRepository.save(account);
        return account;
    }

    public Account createAccountTokyo(String email) {
        SignupDto signupDto = new SignupDto(
                "tokyo",
                "test1234",
                "test1234@email.com"
        );
        Account account = accountService.createAccount(signupDto);
        accountRepository.save(account);
        return account;
    }
}
