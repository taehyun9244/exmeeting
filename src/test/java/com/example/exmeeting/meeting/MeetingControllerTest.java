package com.example.exmeeting.meeting;

import com.example.MockMvcTest;
import com.example.exmeeting.account.Account;
import com.example.exmeeting.account.AccountRepository;
import com.example.exmeeting.account.AccountService;
import com.example.exmeeting.account.dto.SignupDto;
import com.example.exmeeting.meeting.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .andExpect(status().is4xxClientError());
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

    @Test
    @DisplayName("meeting 전체 조회 성공")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getAll_Meeting_success() throws Exception {
        MeetingCreateDto meetingCreateDto =
                new MeetingCreateDto("title", "subtitle", "body", "location");
        Meeting meeting = meetingService.createMeeting(meetingCreateDto, account);

        mockMvc.perform(get("/meetings"))
                .andExpect(status().isOk());

        List<MeetingAllDto> allMeeting = meetingService.findAllMeeting();
        assertTrue(allMeeting.contains(meeting));

    }

    @Test
    @DisplayName("meeting 전체 조회 실패")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getAll_Meeting_fail() throws Exception {
        mockMvc.perform(get("/meetingss"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("meeting 개별 조회 성공")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void findOne_Meeting_success() throws Exception {
        MeetingCreateDto meetingCreateDto =
                new MeetingCreateDto("title", "subtitle", "body", "location");
        Meeting meeting = meetingService.createMeeting(meetingCreateDto, account);

        mockMvc.perform(get("/meetings/{id}", meeting.getId()))
                .andExpect(status().isOk());

        MeetingByIdDto meetingById = meetingService.findMeetingOfId(meeting.getId());
        assertThat(meetingById.getTitle()).isEqualTo(meetingCreateDto.getTitle());

    }

    @Test
    @DisplayName("meeting 개별 조회 실패")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void findOne_Meeting_fail() throws Exception {
        MeetingCreateDto meetingCreateDto =
                new MeetingCreateDto("title", "subtitle", "body", "location");
        meetingService.createMeeting(meetingCreateDto, account);
        mockMvc.perform(get("/meetings/{id}", 200))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("meeting patch 수정 성공")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void editPatch_Meeting_success() throws Exception {
        MeetingCreateDto meetingCreateDto =
                new MeetingCreateDto("title", "subtitle", "body", "location");
        Meeting meeting = meetingService.createMeeting(meetingCreateDto, account);

        mockMvc.perform(patch("/meetings/{id}", meeting.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MeetingOnlyTitleDto("editTitle"))))
                .andExpect(status().isOk());

        assertThat(meeting.getTitle()).isEqualTo("editTitle");
    }

    @Test
    @DisplayName("meeting 수정 실패")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void edit_Meeting_fail() throws Exception {

    }

    @Test
    @DisplayName("meeting put 수정 성공")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void editPut_Meeting_success() throws Exception {
        MeetingCreateDto meetingCreateDto =
                new MeetingCreateDto("title", "subtitle", "body", "location");
        Meeting meeting = meetingService.createMeeting(meetingCreateDto, account);

        mockMvc.perform(put("/meetings/{id}", meeting.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MeetingCreateDto(
                        "putTitle", "putSubtitle", "putBody","putLocation"))))
                        .andExpect(status().isOk());

        assertThat(meeting.getTitle()).isEqualTo("putTitle");
        assertThat(meeting.getSubtitle()).isEqualTo("putSubtitle");
        assertThat(meeting.getBody()).isEqualTo("putBody");
        assertThat(meeting.getLocation()).isEqualTo("putLocation");
    }

    @Test
    @DisplayName("meeting put 수정 실패")
    @WithUserDetails(value = "test1234@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void editPut_Meeting_fail() throws Exception {
        MeetingCreateDto meetingCreateDto =
                new MeetingCreateDto("title", "subtitle", "body", "location");
        Meeting meeting = meetingService.createMeeting(meetingCreateDto, account);

        mockMvc.perform(put("/meetings/{id}", meeting.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new MeetingCreateDto(
                                "putTitle", null, "putBody","putLocation"))))
                .andExpect(status().is4xxClientError());
    }

}