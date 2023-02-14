package com.example.exmeeting.meeting;

import com.example.exmeeting.meeting.dto.MeetingAllDto;
import com.example.exmeeting.meeting.dto.MeetingByIdDto;
import com.example.exmeeting.meeting.dto.MeetingCreateDto;
import com.example.exmeeting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Meeting> createMeeting(@RequestBody MeetingCreateDto meetingCreateDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Meeting meeting = meetingService.createMeeting(meetingCreateDto, userDetails);
        return ResponseEntity.ok(meeting);
    }

    @PatchMapping("/meetings/patch/{id}")
    public ResponseEntity<Meeting> patchMeeting(@PathVariable Long id,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestBody MeetingCreateDto meetingCreateDto) {
        Meeting meeting = meetingService.patchMeeting(id, userDetails, meetingCreateDto);
        return ResponseEntity.ok(meeting);
    }

    @PutMapping("/meetings/put/{id}")
    public ResponseEntity<Meeting> putMeeting(@PathVariable Long id,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @RequestBody MeetingCreateDto meetingCreateDto) {
        Meeting meeting = meetingService.putMeeting(id, userDetails, meetingCreateDto);
        return ResponseEntity.ok(meeting);
    }

    @DeleteMapping("/meetings/{id}")
    public ResponseEntity<String> deleteMeeting (@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetingService.deleteMeeting(id, userDetails);
        return ResponseEntity.ok("delete");
    }
}
