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
    @DisplayName("회원가입 처리 입력값 오류")
    void signUpSubmit_with_wrong_input() {

    }

    @Test
    @DisplayName("회원가입 처리 입력값 정상")
    void signUpSubmit_with_correct_input() {
    }

    @Test
    @DisplayName("로그인 틀린 입력 jwt token 반환 안됨")
    void loginSubmit_with_wrong_input() {

    }

    @Test
    @DisplayName("로그인 정상 입력 jwt token 받기")
    void loginSubmit_with_correct_input() {
    }
}