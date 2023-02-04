package com.example.exmeeting.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TokenInfoDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}
