package com.example.exmeeting.security.config;

import com.example.exmeeting.security.jwt.JwtAuthenticationFilter;
import com.example.exmeeting.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http
               .authorizeRequests()
               .antMatchers("/", "/login", "/signup", "/h2-console/**").permitAll()
               .antMatchers(HttpMethod.GET, "/exMeeting/**").permitAll()
               .anyRequest().authenticated()
           .and()

               .antMatcher("images")
               .httpBasic().disable()
               .csrf().disable()
               .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .headers().frameOptions().disable()
               .and().build();
    }
}
