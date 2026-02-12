package com.green;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    
 // -------------------------------
    // 로그인 (세션 저장 추가)
    // -------------------------------
    @PostMapping("/member/login")
    public MemberDTO login(@RequestBody MemberDTO mdto, HttpSession session) {

        MemberDTO loginUser = memberService.loginConfirm(mdto);

        if (loginUser != null) {
            // 🔥 세션에 로그인 사용자 저장
            session.setAttribute("loginUser", loginUser.getId());
        }

        return loginUser;  // React로 JSON 반환
    }

    // -------------------------------
    // 로그아웃
    // -------------------------------
    @GetMapping("/member/logout")
    public int logout(HttpSession session) {

        session.invalidate();  // 세션 삭제

        return 1;  // 성공 신호
    }

    // -------------------------------
    // 🔥 내정보 조회 (세션 기준)
    // -------------------------------
    @GetMapping("/member/myinfo")
    public MemberDTO myInfo(HttpSession session) {

        // 세션에서 로그인한 사용자 꺼냄
        String loginId = (String) session.getAttribute("loginUser");

        if (loginId == null) {
            // 로그인 안 되어 있으면 null 반환
            return null;
        }

        // 로그인 되어 있으면 DB 조회
        return memberService.oneSelect(loginId);
    }
    
	 // -------------------------------
	 // 🔥 회원 삭제
	 // -------------------------------
	 @DeleteMapping("/member/delete")
	 public int delete(HttpSession session) {
	
	     String loginId = (String) session.getAttribute("loginUser");
	
	     if (loginId == null) {
	         return 0; // 로그인 안됨
	     }
	
	     boolean result = memberService.oneDelete(loginId);
	
	     if (result) {
	         session.invalidate();  // 삭제되면 세션도 종료
	         return 1;
	     } else {
	         return 0;
	     }
	 }
    
    // 수정 부분
	 @PutMapping("/member/modify")
	 public boolean modify(@RequestBody MemberDTO dto, HttpSession session) {

	     String loginId = (String) session.getAttribute("loginUser");

	     if (loginId == null) {
	         return false; // 로그인 안된 경우
	     }

	     dto.setId(loginId);

	     return memberService.modifyMember(dto);
	 }
	 
	 // --------------  자동차 등록
	 @PostMapping("/cars/insert")
	 public int insertCarProduct(
	         @RequestParam("carName") String carName,
	         @RequestParam("price") int price,
	         @RequestParam("company") String company,
	         @RequestParam("info") String info,
	         @RequestParam("img") MultipartFile file
	 ) throws Exception {

	     System.out.println("자동차 등록 요청");

	     // 1️⃣ 저장 경로
	     String savePath = "D:/Spring_Boot/pjt/com.green_MyBatis/frontend/public/img/car/";

	     File dir = new File(savePath);
	     if (!dir.exists()) {
	         dir.mkdirs();
	     }

	     String fileName = "";

	     if (!file.isEmpty()) {

	         String originalName = file.getOriginalFilename();
	         fileName = originalName;

	         File saveFile = new File(savePath + fileName);
	         file.transferTo(saveFile);
	     }

	     // 2️⃣ DTO 생성
	     CarProductDTO dto = new CarProductDTO();
	     dto.setCarName(carName);
	     dto.setPrice(price);
	     dto.setCompany(company);
	     dto.setInfo(info);
	     dto.setImg(fileName);

	     // 3️⃣ DB 저장
	     carProductService.insertCarProduct(dto);

	     return 1;
	 }

}
