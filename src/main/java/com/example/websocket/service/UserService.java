package com.example.websocket.service;

import com.example.websocket.dto.user.SignupDto;
import com.example.websocket.model.User;
import com.example.websocket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(@Valid SignupDto signupDto, PasswordEncoder passwordEncoder){

        log.info("user = {}", signupDto);

        User findUsername = userRepository.findByEmail(signupDto.getEmail());
        if (findUsername != null){
            throw new RuntimeException("이미 등록된 이메일입니다");
        }
        User user = new User(signupDto, passwordEncoder);

        User saveUser = userRepository.save(user);
        return saveUser;
    }
}
