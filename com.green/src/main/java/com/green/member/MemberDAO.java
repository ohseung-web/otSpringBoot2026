package com.green.member;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
	
	@Autowired 
	private DataSource dataSource;

	
	// 회원 추가
	 public int insertMember(MemberDTO mdto) {
	     System.out.println("회원 추가 메소드 호출");

	     String sql = "insert into user_member(id,pw,mail,phone) values (?,?,?,?)";
	     int result = 0;

	     try (
	         Connection conn = dataSource.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)
	     ) {
	         pstmt.setString(1, mdto.getId());
	         pstmt.setString(2, mdto.getPw());
	         pstmt.setString(3, mdto.getMail());
	         pstmt.setString(4, mdto.getPhone());
	         
	         result = pstmt.executeUpdate();

	     } catch (Exception e) {
	         e.printStackTrace();
	     }

	     return result;
	 }

	 
	public boolean isMmeber(String id) {
		System.out.println("같은 id존재 여부 확인 메소드 호출");
		return false;
	}

//	public int insertMember(MemberDTO mdto) {
//		System.out.println("회원 추가 메소드 호출");
//		return 0;
//	}
	
 
}
