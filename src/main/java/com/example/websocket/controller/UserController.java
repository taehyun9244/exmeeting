package com.example.websocket.controller;

import com.example.websocket.dto.user.LoginDto;
import com.example.websocket.dto.user.SignupDto;
import com.example.websocket.model.User;
import com.example.websocket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping(value = "/signup")
    public String createUser(Model model){
        model.addAttribute("signupDto", new SignupDto());
        return "login/register";
    }

    @PostMapping(value="/signup")
    public String createUser(@Validated @ModelAttribute SignupDto dto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        log.info("Signup = {}", dto);

        //검증실패시
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "login/register";
        }

        //검증 성공시
        User user = userService.saveUser(dto, passwordEncoder);
        redirectAttributes.addAttribute("userId", user.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/";
    }

    @GetMapping(value="/login")
    public String login(Model model){
        model.addAttribute("loginDto", new LoginDto());
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute SignupDto dto,
                        BindingResult bindingResult){
        //검증실패시
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "login/login";
        }

        //검증성공시
        return "redirect:/";
    }

}
