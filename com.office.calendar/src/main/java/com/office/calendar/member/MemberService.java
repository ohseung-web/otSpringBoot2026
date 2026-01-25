package com.office.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	//회원가입의 중복을 확인하는 상수
	public final static int user_id_alreday_exist = 0; 
	//회원가입의 성공여부를 확인하는 상수
	public final static int user_signup_success = 1;
	//회원가입의 실패를 확인하는 상수
	public final static int user_signup_fail = -1;
	
	@Autowired
    MemberDAO memberDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public int signupConfirm(MemberDTO mdto) {
		System.out.println("signup_confirm call");
		//회원가입 중복체크하는 부분
		boolean isMember = memberDao.isMmeber(mdto.getId());
		// 아이디 중복체크를 통과했다면
		if(isMember == false) {
			
			//테이블에 삽입되기전에 패스워드를 암호화해서 넘겨줘야 한다.
		  String encodePw = passwordEncoder.encode(mdto.getPw());
		  // 암호화된 비밀번호를 다시 mdto에 넣어주어야 한다.
		  mdto.setPw(encodePw);
		  
		  int result =	memberDao.insertMember(mdto); // 1234라는 암호를 ~~
		  if(result > 0) {
			  return user_signup_success;
		  }else {
			  return user_signup_fail;
		  }
		}else {
			return user_id_alreday_exist;
		}

	}

	
	public String signinConfirm(MemberDTO mdto) {
		System.out.println("MemberService 로그인 확인 메소드 호출 완료");
		
		//사용자가 입력한 id, pw랑 같은 회원이 존재하는지 확인 해줘
		// memberdto가 존재할 수 도 있고, null일수도 있기에 예외처리한다.
	    MemberDTO dto =	memberDao.selectMemberById(mdto.getId());
	    
	    //실제 DB에 암호는 암호화 되어있으므로 지금 현재 비교 불가, 그래서 복호화해야한다.
	    if(dto != null && passwordEncoder.matches(mdto.getPw(), dto.getPw())) {
	    	//로그인 성공
	    	System.out.println("로그인 성공!!");
	    	return dto.getId();
	    }else {
	    	System.out.println("로그인 실패!");
	    	return null;
	    }
	}

}
