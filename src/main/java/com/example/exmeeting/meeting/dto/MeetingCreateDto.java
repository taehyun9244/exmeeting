package com.example.exmeeting.meeting.dto;

import lombok.Data;

@Data
public class MeetingCreateDto {

    private String title;
    private String subtitle;
    private String body;
    private String location;
}
