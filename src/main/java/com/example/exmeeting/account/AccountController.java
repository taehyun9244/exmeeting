package com.example.exmeeting.account;

import com.example.exmeeting.account.dto.LoginDto;
import com.example.exmeeting.account.dto.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupDto signupDto) {
        accountService.createAccount(signupDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody LoginDto loginDto) {
        Account account = accountService.login(loginDto);
        return ResponseEntity.ok(account);
    }
}
