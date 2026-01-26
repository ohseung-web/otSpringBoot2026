package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	// 회원가입의 중복을 확인하는 상수
	public final static int user_id_alreday_exist = 0;
	// 회원가입의 성공여부를 확인하는 상수
	public final static int user_signup_success = 1;
	// 회원가입의 실패를 확인하는 상수
	public final static int user_signup_fail = -1;

	@Autowired
	MemberDAO memberDao;
	
	//회원 전체 목록 출력하는 메소드
		public List<MemberDTO> allListMember(){
			return memberDao.allSelectMember();
		}

	//개인 한사람 목록만 출력하는 메소드
		public MemberDTO oneMember(String id) {
			return memberDao.oneSelectMember(id);
		}
		
	// 개인 한사람 패스워드만 출력하는 메소드
		public String onePass(String id) {
			return memberDao.getPss(id);
		}
	//----------------------------------------------------------	
	
		 //한 사람의 회원 삭제 메소드
		   public boolean oneDelete(MemberDTO mdto) {
			/// 1. DB에 저장된 비밀번호 조회
			    String dbPass = memberDao.getPss(mdto.getId());

			    // 2. 비밀번호 비교
			    if (dbPass != null && dbPass.equals(mdto.getPw())) {
			        // 3. 삭제 실행
			        return memberDao.deleteMember(mdto.getId()) == 1;
			    } else {
			        // 비밀번호 틀림
			        return false;
			    }
		   }
		   //------------------------------------------------------
		   
	// 개인 한사람의 정보를 수정하는 메소드
		 public boolean modifyMember(MemberDTO mdto) {
			    
			  // 1. DB에 저장된 비밀번호 조회
			    String dbPass = memberDao.getPss(mdto.getId());

			    // 2. 입력한 비밀번호와 비교
			    if (dbPass != null && dbPass.equals(mdto.getPw())) {
			        // 비밀번호가 맞으면 수정
			        return memberDao.updateMember(mdto) == 1;
			    } else {
			        // 비밀번호 틀림
			        return false;
			    }
		    }
		 

	public int signupConfirm(MemberDTO mdto) {
		System.out.println("MemberService 로그인 확인 메소드 호출 완료");
		// 회원가입 중복체크하는 부분
		boolean isMember = memberDao.isMember(mdto.getId());
		// 아이디 중복체크를 통과했다면
		if (isMember == false) {
			int result = memberDao.insertMember(mdto);
			if (result > 0) {
				return user_signup_success;
			} else {
				return user_signup_fail;
			}
		} else {
			return user_id_alreday_exist;
		}
	}
}
