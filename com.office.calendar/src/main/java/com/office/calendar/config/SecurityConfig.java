package com.office.calendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Spring Security 설정을 위해서 config 패키지를 만들고, SecurityConfig클래스를 만든다.

@Configuration
@EnableWebSecurity //우리가 지정한 암호화를 웹에 적용하겠다는 어노테이션
public class SecurityConfig {
   @Bean // 객체생성
   public PasswordEncoder passwordEncoder() {
	   return new BCryptPasswordEncoder(); //문자열을 암호화 해주는 클래스이다.
   }
   //기본적으로 동작하는 기능을 꺼야하기에 disable()로 비활성화 한다.
   @Bean
   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       http
               .cors(cors -> cors.disable())
               .csrf(csrf -> csrf.disable());

       http
               .formLogin(login -> login.disable());

       return http.build();
   }
}
