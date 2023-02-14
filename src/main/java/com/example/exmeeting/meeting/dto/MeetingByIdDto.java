package com.example.exmeeting.meeting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeetingByIdDto {

    private String title;
    private String subtitle;
    private String body;
    private String location;
    private String manager;
}
