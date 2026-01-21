package com.example.demo.member;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration 
public class AppConfig {

	//멤버 서비스를 Bean객체로 생성하는 작업
//	@Bean
	public MemberService memberService() {
		return new MemberService(); //Ioc 컨테이너에 Bean객체로 탑재시킨다.
		//Ioc컨테이너에 MemberService클래스가 탑재된다.
	}
}
