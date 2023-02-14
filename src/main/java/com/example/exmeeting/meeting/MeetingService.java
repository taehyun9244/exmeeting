package com.example.exmeeting.meeting;

import com.example.exmeeting.account.Account;
import com.example.exmeeting.exception.UserNotFoundException;
import com.example.exmeeting.meeting.dto.MeetingAllDto;
import com.example.exmeeting.meeting.dto.MeetingByIdDto;
import com.example.exmeeting.meeting.dto.MeetingCreateDto;
import com.example.exmeeting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<MeetingAllDto> findAllMeeting() {
        List<MeetingAllDto> allMeeting = meetingRepository.findAll().stream()
                .map(meeting -> modelMapper.map(meeting, MeetingAllDto.class))
                .collect(Collectors.toList());
        return allMeeting;
    }

    @Transactional(readOnly = true)
    public MeetingByIdDto findMeetingOfId(Long id) {
        Meeting meeting = getMeeting(id);
        MeetingByIdDto meetingByIdDto = MeetingByIdDto.builder()
                .title(meeting.getTitle())
                .subtitle(meeting.getSubtitle())
                .body(meeting.getBody())
                .location(meeting.getLocation())
                .manager(meeting.getAccount().getNickname())
                .build();
        return meetingByIdDto;
    }

    public Meeting createMeeting(MeetingCreateDto meetingCreateDto, UserDetailsImpl userDetails) {
        Account account = userLogin(userDetails);
        Meeting meeting = Meeting.builder()
                .title(meetingCreateDto.getTitle())
                .subtitle(meetingCreateDto.getSubtitle())
                .body(meetingCreateDto.getBody())
                .location(meetingCreateDto.getLocation())
                .account(account)
                .build();
        Meeting saveMeeting = meetingRepository.save(meeting);
        return saveMeeting;
    }

    public Meeting patchMeeting(Long id, UserDetailsImpl userDetails, MeetingCreateDto meetingCreateDto) {
        Account account = userDetails.getAccount();
        return meetingRepository.save(editMeeting(id, meetingCreateDto, account));
    }

    public Meeting putMeeting(Long id, UserDetailsImpl userDetails, MeetingCreateDto meetingCreateDto) {
        Account account = userLogin(userDetails);
        return meetingRepository.save(editMeeting(id, meetingCreateDto, account));
    }

    public void deleteMeeting(Long id, UserDetailsImpl userDetails) {
        Account account = userLogin(userDetails);
        Meeting findMeeting = getMeeting(id);
        if (findMeeting.getAccount().equals(account)) {
            meetingRepository.delete(findMeeting);
        } else throw new IllegalArgumentException("You are not the author of this post");
    }



    private Meeting editMeeting(Long id, MeetingCreateDto meetingCreateDto, Account account) {
        Meeting findMeeting = getMeeting(id);
        if (findMeeting.getAccount().equals(account)) {
            Meeting editMeeting = Meeting.builder()
                    .title(meetingCreateDto.getTitle())
                    .subtitle(meetingCreateDto.getSubtitle())
                    .body(meetingCreateDto.getBody())
                    .location(meetingCreateDto.getLocation()).build();
            return editMeeting;
        } else throw new IllegalArgumentException("You are not the author of this post");
    }

    private Meeting getMeeting(Long id) {
        Meeting findMeeting = meetingRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("can not found meeting")
        );
        return findMeeting;
    }

    private static Account userLogin(UserDetailsImpl userDetails) {
        Account account = userDetails.getAccount();
        if (account == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", account.getEmail()));
        }
        return account;
    }

}
