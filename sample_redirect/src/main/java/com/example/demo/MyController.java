package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MyController {
	
		@GetMapping("/old-url")
		public String oldPage(RedirectAttributes re) {
	        // 리다이렉트 시점에 딱 한 번만 사용할 데이터를 보관함
	        re.addFlashAttribute("msg", "이전 주소에서 자동으로 이동되었습니다!");
	        
	        return "redirect:/new-url";
	    }
		
		@GetMapping("/new-url")
	    public String newPage() {
	        return "new-page-view"; // 실제 보여줄 HTML 파일 이름
	    }
		
		
		
//		@GetMapping("/old-url")
//		public String oldPage() {
//			// "/new-url"로 리다이렉트 하라는 명령
//	        return "redirect:/new-url";
//		}
	}

