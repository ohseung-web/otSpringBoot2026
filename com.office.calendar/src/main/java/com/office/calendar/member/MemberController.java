package com.office.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	//회원가입 양식
	@GetMapping("/member/signup")
	public String signup() {
		System.out.println("signup call");
		String nextPage ="member/signup_form";
		return nextPage;
	}
	
	//회원가입 확인
	@PostMapping("/member/signup_confirm")
	public String signup_confirm(MemberDTO mdto, Model model) {
		System.out.println("signup_congirm");
		String nextPage = "member/signup_result";
		
		int result = memberService.signupConfirm(mdto);
		model.addAttribute("result",result);
		
		return nextPage;
	}
	//로그인 양식
	@GetMapping("/member/signin")
	public String signin() {
		System.out.println("로그인 양식 핸들러 호출 완료");
		String nextPage ="member/signin_form";
		return nextPage;
	}
	
	
	//로그인 확인
	@PostMapping("/member/signin_confirm")
	public String signinConfirm(MemberDTO mdto, Model model) {
		System.out.println("로그인 확인 핸들러 호출 완료");
		String nextPage ="member/signin_result";
		
		// memberService에 로그인된 id랑 같은 데이터가 DB에 존재하는지 체크하기 위한코드
		String loginId =  memberService.signinConfirm(mdto);
		model.addAttribute("loginId",loginId);
		return nextPage;
	}
	
	
	
	
	
	
	
	
}
