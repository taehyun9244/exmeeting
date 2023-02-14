package com.example.exmeeting.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {

    @NotBlank(message = "日本語でニックネームを入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9가-힇ㄱ-ㅎㅏ-ㅣぁ-ゔァ-ヴー々〆〤一-龥]*$", message = "空欄は入れないでください")
    private String nickname;

    @NotBlank
    private String password;

    @NotBlank(message = "メールの形式が正しくありません")
    @Email
    private String email;

//    @NotBlank(message = "roleを入力してください")
//    private String role;

}
