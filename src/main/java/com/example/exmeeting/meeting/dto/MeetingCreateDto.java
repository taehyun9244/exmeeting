package com.example.exmeeting.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingCreateDto {

    @NotBlank
    private String title;
    @NotBlank
    private String subtitle;
    @NotBlank
    private String body;
    @NotBlank
    private String location;
}
