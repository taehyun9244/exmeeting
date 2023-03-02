package com.example.exmeeting.meeting;

import com.example.exmeeting.account.Account;
import com.example.exmeeting.account.AccountRepository;
import com.example.exmeeting.exception.UserNotFoundException;
import com.example.exmeeting.meeting.dto.MeetingAllDto;
import com.example.exmeeting.meeting.dto.MeetingByIdDto;
import com.example.exmeeting.meeting.dto.MeetingCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MeetingService {
    private final AccountRepository accountRepository;

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

    public Meeting createMeeting(MeetingCreateDto meetingCreateDto, Account account) {
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

    public Meeting patchMeeting(Long id, Account account, MeetingCreateDto meetingCreateDto) {
        return meetingRepository.save(editMeeting(id, meetingCreateDto, account));
    }

    public Meeting putMeeting(Long id, Account account, MeetingCreateDto meetingCreateDto) {
        return meetingRepository.save(editMeeting(id, meetingCreateDto, account));
    }

    public void deleteMeeting(Long id, Account account) {
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
}
