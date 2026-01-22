package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
	@GetMapping("/old-url")
	public String oldPage() {
		// "/new-url"로 리다이렉트 하라는 명령
        return "redirect:/new-url";
	}
	@GetMapping("/new-url")
    public String newPage() {
        return "new-page-view"; // 실제 보여줄 HTML 파일 이름
    }
}
