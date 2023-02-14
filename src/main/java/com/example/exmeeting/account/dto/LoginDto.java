package com.example.exmeeting.account.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank(message = "emailを入力してください")
    @Email
    private String email;

    @NotBlank(message = "Passwordを入力してください")
    private String password;
}
