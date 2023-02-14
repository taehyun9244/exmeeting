package com.example.exmeeting.account;

import com.example.exmeeting.account.dto.LoginDto;
import com.example.exmeeting.account.dto.SignupDto;
import com.example.exmeeting.account.dto.TokenInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupDto signupDto) {
        Account account = accountService.createAccount(signupDto);
        return ResponseEntity.ok(account.getNickname());
    }

    @PostMapping("/login")
    public ResponseEntity<TokenInfoDto> login(@RequestBody LoginDto loginDto) {
        TokenInfoDto tokenInfoDto = accountService.login(loginDto);
        return ResponseEntity.ok(tokenInfoDto);
    }
}
