package com.example.exmeeting.meeting;

import com.example.MockMvcTest;
import com.example.exmeeting.account.Account;
import com.example.exmeeting.account.AccountRepository;
import com.example.exmeeting.account.AccountService;
import com.example.exmeeting.account.dto.SignupDto;
import com.example.exmeeting.meeting.dto.MeetingCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
@Slf4j
class MeetingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MeetingService meetingService;
    @Autowired
    private MeetingRepository meetingRepository;
    private Account account;
    private Account NonPermission;

    @BeforeEach
    void beforeEach() {
        SignupDto signupDto = new SignupDto("test1234", "1234", "test1234@test.com");
        SignupDto signupDto1 = new SignupDto("abc1234", "1234", "abc1234@test.com");
        account = accountService.createAccount(signupDto);
        NonPermission = accountService.createAccount(signupDto1);
    }

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("meeting 생성 성공")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void createMeeting_success() throws Exception {
        MeetingCreateDto meetingCreateDto =
                new MeetingCreateDto("title", "subtitle", "body", "location");

        mockMvc.perform(post("/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8").content(objectMapper.writeValueAsString(meetingCreateDto)))
                .andExpect(status().isOk());

        Meeting meeting = meetingService.createMeeting(meetingCreateDto, account);
        assertThat(meeting.getTitle()).isEqualTo(meetingCreateDto.getTitle());
        assertThat(meeting.getBody()).isEqualTo(meetingCreateDto.getBody());

    }

    @Test
    @DisplayName("meeting 생성 실패")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void createMeeting_fail() throws Exception {
        MeetingCreateDto meetingCreateDto =
                new MeetingCreateDto(null, "subtitle", "body", "location");

        mockMvc.perform(post("/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8").content(objectMapper.writeValueAsString(meetingCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("meeting 삭제 성공")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void deleteMeeting_success() throws Exception {
        MeetingCreateDto meetingCreateDto =
                new MeetingCreateDto("title", "subtitle", "body", "location");
        Meeting meeting = meetingService.createMeeting(meetingCreateDto, account);

        mockMvc.perform(delete("/meetings/{id}", meeting.getId()))
                .andExpect(status().isOk());

        meetingService.deleteMeeting(meeting.getId(), account);
        assertNull(meeting);
    }

    @Test
    @DisplayName("meeting 삭제 실패")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void deleteMeeting_fail() throws Exception {
        MeetingCreateDto meetingCreateDto =
                new MeetingCreateDto("title", "subtitle", "body", "location");
        Meeting meeting = meetingService.createMeeting(meetingCreateDto, account);

        mockMvc.perform(delete("/meetings/{id}", meeting.getId()))
                .andExpect(status().isOk());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> meetingService.deleteMeeting(meeting.getId(), NonPermission));
    }

}