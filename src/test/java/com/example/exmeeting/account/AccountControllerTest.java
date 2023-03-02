package com.example.exmeeting.account;

import com.example.MockMvcTest;
import com.example.exmeeting.account.dto.SignupDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@MockMvcTest
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("회원가입 입력 정상")
    void signUpSubmit_with_correct_input() throws Exception {
        SignupDto signupDto = new SignupDto("test", "1234", "test@test.com");

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8").content(objectMapper.writeValueAsString(signupDto)))
                .andExpect(status().isOk());


    }

    @Test
    @DisplayName("회원가입 입력 오류")
    void signUpSubmit_with_wrong_input() {
    }

    @Test
    @DisplayName("로그인 입력 정상")
    void loginSubmit_with_correct_input() {
    }

    @Test
    @DisplayName("로그인시 패스워드 입력 오류")
    void loginSubmit_with_wrong_password() {
    }

    @Test
    @DisplayName("로그인시 이메일 입력 오류")
    void loginSubmit_with_wrong_email() {
    }

}