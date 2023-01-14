package com.example.exmeeting.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/signup")
    public String accountView(Model model){
        model.addAttribute("signupForm", new SignupForm());
        return "account/signup";
    }

    @PostMapping("/signup")
    public String createAccount(@Validated @ModelAttribute SignupForm signupForm, BindingResult bindingResult) {

        //validator fail
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "account/signup";
        }

        //validator success
        Account account = accountService.createAccount(signupForm);
        accountService.login(account);
        return "redirect:/";
    }
}
