package com.example.exmeeting.account;

import com.example.exmeeting.account.dto.SignupDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    private boolean chatEnrollmentResultByEmail;

    private boolean chatEnrollmentResultByWeb = true;

    public Account(SignupDto signupDto, String passwordEncode) {
        this.nickname = signupDto.getNickname();
        this.password = passwordEncode;
        this.email = signupDto.getEmail();
    }
}
