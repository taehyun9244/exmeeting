package com.example.exmeeting.account;

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

    private boolean chatEnrollmentResultByEmail;

    private boolean chatEnrollmentResultByWeb = true;

    public Account(SignupForm signupForm, String passwordEncode) {
        this.nickname = signupForm.getNickname();
        this.password = passwordEncode;
        this.email = signupForm.getEmail();
    }
}
