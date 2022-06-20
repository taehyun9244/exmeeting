package com.example.websocket.Controller;

import com.example.websocket.Service.UserService;
import com.example.websocket.dto.UserDto;
import com.example.websocket.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public User signUp(@RequestBody UserDto userDto){
        return userService.signup(userDto);
    }
}
