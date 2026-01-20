package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class HomeController {

	// Get방식으로 요청하면 "/"이 아이와 Mapping(연결)하겠다는 의미
	// https://localhost:8090/ => 이 요청이 들어오면
	// home()매소드가 호출된다.
	@GetMapping("/")  // https://localhost:8090/ 홈지정하는 부분 
	public String home() {
		return "home";   // home.html로 반환하는데 -> 반환하는 역활을 viewResolver가환다.
		// 그럼 어떻게 home.html에서 html확장자가 자동으로 생성되는 이유는
		// application.properties의 spring.thymeleaf.suffix=.html 
		//  => 이부분 꼬리말이 html로 환경이 설정되어 있기 때문에 가능한 것이다.
		// spring.thymeleaf.prefix=classpath:/templates/ 
		// => 이부분의 머릿말로 home.html이 templates폴더 안에 들어있음을 알려준다.
		// 이 모든 작업을 viewResolver가 담당한다.
	}
}
