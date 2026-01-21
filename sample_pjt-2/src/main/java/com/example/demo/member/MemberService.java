package com.example.demo.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	//MemberDAO클래스를  MemberService클래스에 의존성 객체로 주입함
	@Autowired
	MemberDAO memberDao;
	public void signUpConfirm(MemberDTO mdto) {
		System.out.println("[MemberService] signUp()");
		memberDao.insertMember(mdto);
	}

	public void signipConfirm(MemberDTO mdto) {
		System.out.println("[MemberService] signIn()");
//		memberDao.selectMember(mdto);
		
	   MemberDTO loginMember =	memberDao.selectMember(mdto);
	   
	   if(loginMember != null && mdto.getPw().equals(loginMember.getPw())) {
			System.out.println("login Success!!");
			System.out.println("id"+loginMember.getId());
			System.out.println("pw"+loginMember.getPw());
			System.out.println("email"+loginMember.getEmail());
		}else {
			System.out.println("login Fail!!");
		}
	   
	   
	}

}




