package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SamplePjtApplication {

	public static void main(String[] args) {
		//SpringApplication.run는 Ioc 컨테이너를 만들고 Bean객체 자동생성
		//DI(의존객체)주입까지 알아서 생성한다.
		SpringApplication.run(SamplePjtApplication.class, args);
	}

}
