package com.green;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.carproduct.CarProductDTO;
import com.green.carproduct.CarProductService;
import com.green.member.MemberDTO;
import com.green.member.MemberService;

import jakarta.servlet.http.HttpSession;

// @RestController는 @Controller와 @ResponseBody가 합쳐진 "종합 선물 세트" 같은 어노테이션이다.
// @Controller: 이 클래스가 사용자의 요청을 받는 컨트롤러임을 선언한다.
// @ResponseBody: 메서드가 반환하는 데이터를 HTML 뷰를 찾는 용도가 아니라, 
//  데이터 그 자체(JSON)**로 응답 바디에 직접 쓰겠다는 뜻이다.
//  즉, 클래스 상단에 @RestController를 한 번만 적어주면, 그 안의 모든 메서드에 
//  일일이 @ResponseBody를 붙이지 않아도 자동으로 적용된다.

@RestController // JSON 전용 컨트롤러
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
    CarProductService carProductService; // 서비스 주입
	
	 @Autowired
	 MemberService memberService;
	
	@GetMapping("/hello")
    public String hello() {
        return "스프링부트에서 온 데이터입니다!";
    }
	
	// 자동차 리스트를 JSON으로 반환하는 API
    @GetMapping("/cars")
    public List<CarProductDTO> getCarList() {
        System.out.println("ApiController: 자동차 리스트 요청됨");
        
        // DB에서 데이터를 가져와서 그대로 리턴 (Spring이 자동으로 JSON 배열로 변환함)
        return carProductService.getAllCarProduct();
    }
    
    //----- 회원 가입 ------
   

    // 회원가입 API (POST 방식)
    @PostMapping("/member/signup")
    public int signup(@RequestBody MemberDTO mdto) {
        System.out.println("회원가입 요청: " + mdto.getId());
        // Service에서 이미 암호화 및 중복 체크를 다 처리합니다.
        return memberService.signupConfirm(mdto);
    }

    // 로그인 API (POST 방식)
    @PostMapping("/member/login")
    public MemberDTO login(@RequestBody MemberDTO mdto) {
        System.out.println("로그인 요청: " + mdto.getId());
        // 성공 시 회원정보 객체 반환, 실패 시 null 반환
        return memberService.loginConfirm(mdto);
    }
    
    // 로그아웃 API 수정 버전
    @GetMapping("/member/logout")
    public int logout(HttpSession session) {
        System.out.println("ApiController: 로그아웃 요청됨");
        
        // 1. 서버 세션 무효화
        // (사실 리액트 방식에서는 sessionStorage를 쓰기 때문에 서버 세션이 비어있을 확률이 높지만, 
        // 혹시 모를 보안을 위해 남겨두는 것은 괜찮습니다.)
        session.invalidate(); 
        
        // 2. 성공했다는 신호(1)만 리턴합니다.
        // 이동(Redirect)은 서버가 아니라 리액트가 결정합니다.
        return 1; 
    }
    
}
