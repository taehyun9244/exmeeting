package com.example.exmeeting.meeting;

import com.example.exmeeting.account.Account;
import com.example.exmeeting.meeting.dto.MeetingAllDto;
import com.example.exmeeting.meeting.dto.MeetingByIdDto;
import com.example.exmeeting.meeting.dto.MeetingCreateDto;
import com.example.exmeeting.meeting.dto.MeetingOnlyTitleDto;
import com.example.exmeeting.security.CurrentAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/meetings")
    public ResponseEntity<List<MeetingAllDto>> findAllMeeting() {

        List<MeetingAllDto> meetingAllDtos = meetingService.findAllMeeting();
        return ResponseEntity.ok(meetingAllDtos);
    }

    @GetMapping("/meetings/{id}")
    public ResponseEntity<MeetingByIdDto> findMeetingOfId(@PathVariable Long id) {
        MeetingByIdDto meetingByIdDto = meetingService.findMeetingOfId(id);
        return ResponseEntity.ok(meetingByIdDto);
    }

    @PostMapping("/meetings")
    public ResponseEntity<Meeting> createMeeting(@RequestBody @Validated MeetingCreateDto meetingCreateDto,
                                                 @CurrentAccount Account account) {
        Meeting meeting = meetingService.createMeeting(meetingCreateDto, account);
        return ResponseEntity.ok(meeting);
    }

    @PatchMapping("/meetings/patch/{id}")
    public ResponseEntity<Meeting> patchMeeting(@PathVariable Long id,
                                                @RequestBody MeetingOnlyTitleDto onlyTitleDto,
                                                @CurrentAccount Account account) {
        Meeting meeting = meetingService.patchMeeting(id, account, onlyTitleDto);
        return ResponseEntity.ok(meeting);
    }


    @PutMapping("/meetings/put/{id}")
    public ResponseEntity<Meeting> putMeeting(@PathVariable Long id,
                                              @RequestBody MeetingCreateDto meetingEditDto,
                                              @CurrentAccount Account account) {
        Meeting meeting = meetingService.putMeeting(id, account, meetingEditDto);
        return ResponseEntity.ok(meeting);
    }

    @DeleteMapping("/meetings/{id}")
    public ResponseEntity<String> deleteMeeting (@PathVariable Long id, @CurrentAccount Account account) {
        meetingService.deleteMeeting(id, account);
        return ResponseEntity.ok("delete");
    }
}
