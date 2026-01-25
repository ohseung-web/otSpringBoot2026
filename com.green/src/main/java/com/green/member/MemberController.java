package com.green.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

	@Autowired
	MemberService memberservice;
	
	//회원가입 양식
	@GetMapping("/member/signup")
	public String signup() {
		System.out.println("회원가입 핸들러 호출");
		String nextPage = "member/signup_form";
		return nextPage;
	}
	
	//회원가입 확인
	@PostMapping("/member/signup_confirm")
	public String signupConfirm(MemberDTO mdto, Model model) {
		System.out.println("회원가입 확인 핸들러");
		// signup_result.html파일로 응답을 처리한다.
		String nextPage ="member/signup_result"; 
		//회원가입이 제대로 되었는지, 실패했는지를 확인한다.
		int result = memberservice.signupConfirm(mdto);
		model.addAttribute("result",result);
		return nextPage;
	}
}

