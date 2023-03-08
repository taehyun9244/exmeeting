package com.example.exmeeting.account;

import com.example.MockMvcTest;
import com.example.exmeeting.account.dto.LoginDto;
import com.example.exmeeting.account.dto.SignupDto;
import com.example.exmeeting.account.dto.TokenInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Autowired
    private AccountRepository accountRepository;
    private SignupDto signupDto;


    @BeforeEach
    void beforeEach() {
        signupDto = new SignupDto("test1234", "1234", "test1234@test.com");
        accountService.createAccount(signupDto);
    }

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 입력 정상")
    void signUpSubmit_with_correct_input() throws Exception {
        SignupDto newSignup = new SignupDto("new", "new1234", "new1234@new.com");

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8").content(objectMapper.writeValueAsString(newSignup)))
                .andExpect(status().isOk());

        Account account = accountService.createAccount(newSignup);
        assertNotNull(account);
        assertThat(account.getNickname()).isEqualTo(newSignup.getNickname());
        assertThat(account.getEmail()).isEqualTo(newSignup.getEmail());
    }

    @Test
    @DisplayName("회원가입 입력 오류")
    void signUpSubmit_with_wrong_input() throws Exception {
        SignupDto signupDto = new SignupDto("test", "1234", "errorEmail@");

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8").content(objectMapper.writeValueAsString(signupDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("로그인 입력 정상")
    void loginSubmit_with_correct_input() throws Exception {
        LoginDto loginDto = new LoginDto("test1234@test.com", "1234");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8").content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk());

        TokenInfoDto tokenInfoDto = accountService.login(loginDto);
        assertNotNull(tokenInfoDto);
    }

    @Test
    @DisplayName("로그인시 패스워드 입력 오류")
    void loginSubmit_with_wrong_password() throws Exception {
        LoginDto loginDto = new LoginDto("test1234@test.com", "12341234");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8").content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인시 이메일 입력 오류")
    void loginSubmit_with_wrong_email() throws Exception {
        LoginDto loginDto = new LoginDto("test@test.com", "1234");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8").content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest());
    }
}