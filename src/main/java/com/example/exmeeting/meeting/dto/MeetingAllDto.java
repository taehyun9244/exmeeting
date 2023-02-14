package com.example.exmeeting.meeting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeetingAllDto {

    private String title;
    private String manager;
    private String location;
}
