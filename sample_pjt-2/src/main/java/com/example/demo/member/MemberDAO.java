package com.example.demo.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//@Service
@Repository
public class MemberDAO {
	//우선 DB가 존재하지 않으니까 DB역활을 하는 Map<>생성
	private Map<String, MemberDTO> memberDB = new HashMap<>();

  	public void insertMember(MemberDTO mdto) {
		System.out.println("[MemberService] insertMember()");
		
		System.out.println("id: "+mdto.getId());
		System.out.println("pw: "+mdto.getPw());
		System.out.println("email: "+mdto.getEmail());
		
		//meberDB에 put메소드 이용해서 삽입함다.
		memberDB.put(mdto.getId(), mdto);
		printMember();
	}

	public MemberDTO selectMember(MemberDTO mdto) {
		System.out.println("[MemberService] selectMember()");
		
		//회원의 id,pw가 일치하는지 처리
		MemberDTO loginMember = memberDB.get(mdto.getId());
		
		return loginMember;
		
	}
	
	// Map<> 데이터 출력하는 메소드
	public void printMember() {
		System.out.println("[MemberDao]");
		
		for(String key : memberDB.keySet()) {
			MemberDTO mdto = memberDB.get(key);
			
			System.out.println("id: "+mdto.getId());
			System.out.println("pw: "+mdto.getPw());
			System.out.println("email: "+mdto.getEmail());
			System.out.println("-----------------");
		}
		
	}
}








