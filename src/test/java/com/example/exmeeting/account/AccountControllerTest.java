package com.example.exmeeting.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("Account signup")
    void signUpSubmit_with_correct_input() {
    }

    @Test
    void login() {
    }
}