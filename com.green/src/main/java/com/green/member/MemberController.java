package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	// 회원가입 확인
		@PostMapping("/member/signup_confirm")
		public String signupConfirm(MemberDTO mdto, Model model) {
			System.out.println("MemberController signupConfirm()메소드 확인");
			String nextPage = "member/signup_result";
			//회원가입이 제대로 되었는지, 혹은 회원가입이 실패했는지 예외처리
			int result = memberservice.signupConfirm(mdto);
			
			// 회원가입이 성공하였을 경우 => 회원 목록인 새로운 주소로 이동하는 Redirect
			if(result == memberservice.user_signup_success) {
				return "redirect:/member/list";
			}else {
				// 회원가입이 실패한 경우
				model.addAttribute("result",result);
				return nextPage;
			}
		}
		
		// 회원 전체 목록화면 호출
		@GetMapping("/member/list")
		public String memberList(Model model) {
			// MemberService의 allListMember()
			List<MemberDTO> memberlist = memberservice.allListMember();
			model.addAttribute("list",memberlist);
			
			String nextPage = "member/memberList";
			return nextPage;
			
		}
		
		// 개인회원 상세 정보 
		@GetMapping("/member/memberInfo")
		public String memberInfo(MemberDTO mdto, Model model) {
			System.out.println("MemberController memberInfo()메소드 확인");
			MemberDTO onememberInfo = memberservice.oneMember(mdto.getId());
			model.addAttribute("onelist",onememberInfo);
			
			String nextPage = "member/memberInfo";
			return nextPage;
			
		}
		
		// 개인 정보 수정
		 // 🔹 수정 화면 이동
	    @GetMapping("/member/modify")
	    public String modifyForm(MemberDTO mdto, Model model) {

	        MemberDTO oneModify = memberservice.oneMember(mdto.getId());
	        model.addAttribute("member", oneModify);

	        String nextPage = "member/member_modify";
			return nextPage;
	    }
       
	 // 🔹 수정 처리
	    @PostMapping("/member/modify")
	    public String modifySubmit(MemberDTO mdto, RedirectAttributes ra) {

//	    	memberservice.modifyMember(mdto);
//
//	        ra.addFlashAttribute("msg", "회원정보가 수정되었습니다.");
//	        return "redirect:/member/list";
//	        
	        boolean result = memberservice.modifyMember(mdto);

	        if (result) {
	            // 성공
	            ra.addFlashAttribute("msg", "회원정보가 수정되었습니다.");
	            return "redirect:/member/list";
	        } else {
	            // 비밀번호 틀림
	            ra.addFlashAttribute("msg", "비밀번호가 틀렸습니다.");
	            return "redirect:/member/modify?id=" + mdto.getId();
	        }
	    }
	    
	    
	    // 삭제
	    @PostMapping("/member/delete")
	    public String deleteMember(MemberDTO mdto, RedirectAttributes ra) {

	        boolean result = memberservice.oneDelete(mdto);

	        if (result) {
	            ra.addFlashAttribute("msg", "회원이 삭제되었습니다.");
	            return "redirect:/member/list";
	        } else {
	            ra.addFlashAttribute("msg", "비밀번호가 틀렸습니다.");
	            return "redirect:/member/memberInfo?id=" + mdto.getId();
	        }
	    }

	    
//	//회원가입 확인
//	@PostMapping("/member/signup_confirm")
//	public String signupConfirm(MemberDTO mdto, Model model) {
//		System.out.println("회원가입 확인 핸들러");
//		// signup_result.html파일로 응답을 처리한다.
//		String nextPage ="member/signup_result"; 
//		//회원가입이 제대로 되었는지, 실패했는지를 확인한다.
//		int result = memberservice.signupConfirm(mdto);
//		model.addAttribute("result",result);
//		return nextPage;
//	}
}

