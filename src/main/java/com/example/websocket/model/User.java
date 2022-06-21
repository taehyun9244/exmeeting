package com.example.websocket.model;

import com.example.websocket.dto.user.SignupDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String birthday;

    public User(SignupDto signupDto, PasswordEncoder passwordEncoder) {
        this.username = signupDto.getUsername();
        this.password = signupDto.getPassword();
        this.password = passwordEncoder.encode(signupDto.getPassword());
        this.email = signupDto.getEmail();
        this.address = signupDto.getAddress();
        this.phoneNumber = signupDto.getPhoneNumber();
    }
}
