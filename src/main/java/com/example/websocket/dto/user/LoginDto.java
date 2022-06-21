package com.example.websocket.dto.user;

import com.example.websocket.validation.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @Email
    @NotEmpty
    private String email;

    @Password
    @NotEmpty
    private String password;
}
