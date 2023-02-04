package com.example.exmeeting.account.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class LoginDto {

    @NotBlank(message = "emailを入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9가-힇ㄱ-ㅎㅏ-ㅣぁ-ゔァ-ヴー々〆〤一-龥]*$", message = "空欄は入れないでください")
    private String email;

    @NotBlank(message = "Passwordを入力してください")
    private String password;
}
