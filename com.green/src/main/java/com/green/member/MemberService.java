package com.green.member;

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

	public int signupConfirm(MemberDTO mdto) {
		System.out.println("MemberService 로그인 확인 메소드 호출 완료");
		// 회원가입 중복체크하는 부분
		boolean isMember = memberDao.isMmeber(mdto.getId());
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
