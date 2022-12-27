package com.example.exmeeting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http
                .authorizeRequests()
                .antMatchers("/", "/login", "/signup", "/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET, "/exMeeting/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                    .antMatcher("images").build();
    }
}
