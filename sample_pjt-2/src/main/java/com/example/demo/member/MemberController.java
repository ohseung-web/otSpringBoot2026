package com.example.demo.member;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.SamplePjt1Application;

@Controller // Controller이라는 Bean객체가 생성되도록 어노테이션처리 한다.
public class MemberController {

   // MemberController가 직접 MemberService를 생성하지 않고
   // 스프링 컨테이너가 만든 MemberService 객체를 주입받는다	
   @Autowired
   MemberService memberService;

	// 아래는 핸들러 메소드들이다.
	//회원 가입 양식
	@GetMapping("/member/singup")
	public String singUpForm() {
		System.out.println("singUpForm()");
		return "singUpForm"; //응답에 사용하는 html이름
	} 
	
	//로그인 양식
	@GetMapping("/member/singin")
	public String singinForm() {
		System.out.println("singinForm()");
		return "singInForm2"; //응답에 사용하는 html이름
	}
	
//	@PostMapping("/member/signup_comfirm")
//	public String signUpConfirm(
//			@RequestParam(value="id") String id,
//			@RequestParam(value="pw") String pw,
//			@RequestParam(value="email") String email,//요청파라미터
//			Model model //데이터 전달
//			) {
//		System.out.println("id :"+id);
//		System.out.println("pw : "+pw);
//		System.out.println("email : "+email);
//		System.out.println("signUpComfirm");
//		
//		// 현재 시간 구하는 코드
//		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date now = new Date();
//	
//		//응답 model에 데이터를 넣어 클라이언트에 전달
//		model.addAttribute("now", sd.format(now));
//		model.addAttribute("new_member_id", id);
//		
//		
//		return "signUpResult";
//	}
//	
	
//	@PostMapping("/member/signup_comfirm")
//	public ModelAndView signUpConfirm(
//			@RequestParam(value="id") String id,
//			@RequestParam(value="pw") String pw,
//			@RequestParam(value="email") String email//요청파라미터
//			) {
//		System.out.println("id :"+id);
//		System.out.println("pw : "+pw);
//		System.out.println("email : "+email);
//		System.out.println("signUpComfirm");
//		
//		// 현재 시간 구하는 코드
//		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date now = new Date();
//		
//		//데이터와 모델을 하나로 합쳐서 클라이언트에 전달하는 Model&view
//		ModelAndView modelview = new ModelAndView();
//		modelview.addObject("now",sd.format(now));
//		modelview.addObject("new_member_id",id);
//		modelview.setViewName("signUpResult");
//		
//		return modelview;	
//	}
//	
	@PostMapping("/member/signup_comfirm")
	public ModelAndView signUpConfirm(MemberDTO mdto) {
		System.out.println("id :"+mdto.getId());
		System.out.println("pw : "+mdto.getPw());
		System.out.println("email : "+mdto.getEmail());
		System.out.println("signUpComfirm");
		
//		MemberService memberservice = new MemberService();
		memberService.signUpConfirm(mdto);
		
		// 현재 시간 구하는 코드
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		
		//데이터와 모델을 하나로 합쳐서 클라이언트에 전달하는 Model&view
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("now",sd.format(now));
		modelview.addObject("new_member_id",mdto.getId());
		modelview.setViewName("signUpResult");
		
		return modelview;	
	}
	
	
	
//	@PostMapping("/member/sigin_comfirm")
//	public String signipConfirm(
//			@RequestParam("id") String id,
//			@RequestParam("pw") String pw,
//			Model model //데이터 전달
//			) {
//		System.out.println("id"+id);
//		System.out.println("pw"+pw);
//		System.out.println("signipComfirm");
//		
//		// 현재 시간 구하는 코드
//				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date now = new Date();
//				
//				model.addAttribute("now", sd.format(now));
//				model.addAttribute("member_id", id);
//		return "signinResult";
//	}
	@PostMapping("/member/sigin_comfirm")
	public String signipConfirm(MemberDTO mdto, Model model) {
		System.out.println("id"+mdto.getId());
		System.out.println("pw"+mdto.getPw());
		System.out.println("signipComfirm");
		

//		MemberService memberservice = new MemberService();
		memberService.signipConfirm(mdto);
		
		// 현재 시간 구하는 코드
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		
		model.addAttribute("now", sd.format(now));
		model.addAttribute("member_id", mdto.getId());
		return "signinResult";
	}
}
