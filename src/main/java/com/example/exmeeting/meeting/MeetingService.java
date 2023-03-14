package com.example.exmeeting.meeting;

import com.example.exmeeting.account.Account;
import com.example.exmeeting.meeting.dto.*;
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
        Meeting newMeeting = meetingRepository.save(meeting);
        return newMeeting;
    }

    public Meeting patchMeeting(Long id, Account account, MeetingOnlyTitleDto onlyTitleDto) {
        Meeting meeting = getMeeting(id);
        if (meeting.getAccount().getNickname().equals(account.getNickname())) {
            return meeting.builder().title(onlyTitleDto.getTitle()).build();
        } else throw new IllegalArgumentException("You are not the author of this meeting");
    }

    public Meeting putMeeting(Long id, Account account, MeetingCreateDto meetingEditDto) {
        return meetingRepository.save(editMeeting(id, meetingEditDto, account));
    }

    public void deleteMeeting(Long id, Account account) {
        Meeting findMeeting = getMeeting(id);
        if (findMeeting.getAccount().getNickname().equals(account.getNickname())) {
            meetingRepository.delete(findMeeting);
        } else throw new IllegalArgumentException("You are not the author of this meeting");
    }

    private Meeting editMeeting(Long id, MeetingCreateDto meetingEditDto, Account account) {
        Meeting meeting = getMeeting(id);
        if (account.getNickname().equals(meeting.getAccount().getNickname())) {
//            return meeting.builder()
//                    .title(meetingEditDto.getTitle())
//                    .subtitle(meetingEditDto.getSubtitle())
//                    .body(meetingEditDto.getBody())
//                    .location(meetingEditDto.getLocation())
//                    .build();
            meeting.editMeeting(meetingEditDto);
            return meeting;
        } else throw new IllegalArgumentException("You are not the author of this post");
    }

    @Transactional(readOnly = true)
    public Meeting getMeeting(Long id) {
        Meeting findMeeting = meetingRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("can not found meeting")
        );
        return findMeeting;
    }
}
