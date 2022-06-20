package com.example.websocket.Service;

import com.example.websocket.dto.UserDto;
import com.example.websocket.model.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Transactional
    public User signup(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        User user = new User(username, password);
        return user;
    }
}
