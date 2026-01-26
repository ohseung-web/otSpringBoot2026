package com.green.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
	
	// MySQL Driver 설치 및 JDBC 환경 설정 완료
		// 외부에서 DataSource를 DI로 삽입한다.
		
		@Autowired
		private DataSource dataSource;
		
	   // 쿼리문 사용할 공간
	   public int insertMember(MemberDTO mdto) {
		  System.out.println("MemberDAO insertMember()메소드 확인"); 
		  
		  // 실무에서 쿼리문작성시 대문자로 작성함
		  // NO, REG_DATE, MOD_DATE는 default 값이 존재하므로 추가하지 않아도 된다.
		  // 추가할 필드명이 정해져 있을 경우 반드시 (필드명1, 필드명2...)필드명을 명시한다.
		  // INSERT INTO USER_MEMBER(id,pw,mail,phone) VALUES(?,?,?,?);
		  
		  String sql = "INSERT INTO user_member(id,pw,mail,phone) VALUES(?,?,?,?)";
		  int result = 0;
		  
		  // DB는 네트워크를 통해 자료를 가져오므로 try ~ catch() 구문 이용한다.
		  
		  try(
				 // Connection 클래스를 이용해 dataSource를 getConnection()해야 한다.
				 // Connection은 연결하는 자원으로 사용하고 나면 반드시 반납을 해야 함, close()를 해야함
				 // try(Connetction~) => 자동 close()가됨 
				Connection conn =  dataSource.getConnection(); 
				PreparedStatement  pstmt =  conn.prepareStatement(sql);
				  ){
			  
			  		// ?,?,?,? 값을 대응해주어야 한다.
			  //    input => 입력 => mdto 가방에 담긴 상태
			  //    mdto라는 가방에서 필요한 자원을 getId()꺼내온다.
			  		pstmt.setString(1, mdto.getId());
			        pstmt.setString(2, mdto.getPw());
			        pstmt.setString(3, mdto.getMail());
			        pstmt.setString(4, mdto.getPhone());
			        
			        // insert, delete, update구문은 실행명령 : executeUpdate()이다.
			        result = pstmt.executeUpdate();
//			        executeUpdate()메소드의 의미는 insert, delete, update문을 실행하고
//			        나면 실행결과를 int데이터 타입의 행의 개수로 반환한다는 의미
//			        insert 1건 성공 => 반환값 : 1
//			        insert 0건 중복체크 => 반환값 : 0
//			        update 3건 수정 => 반환값 : 3
//			        delete 5건 삭제 => 반환값 : 5
			        System.out.println("MemberDAO insertMember result값"+result);
		  }catch(Exception e) {
			  e.printStackTrace();
		  }
		  
		  return result;
	   }

	   
	   // 회원가입한 유저 모두 출력되는 메소드 작성
	   
	   
	   
	   
	   public boolean isMember(String id) {
		   System.out.println("MemberDAO isMember()메소드 확인");
		   return false;
	   }


	   // 회원 전체 목록 검색 쿼리
	   public List<MemberDTO> allSelectMember() {
		   System.out.println("MemberDAO allSelectMember()메소드 확인");
		   
		   // 전체 목록 검색 sql
		   String sql ="SELECT * FROM user_member";
		   // List<E> 인터페이스이므로 => 구현할 수 없다
		   // 고로 ArrayList<>를 이용해야 한다.
		   List<MemberDTO> list = new ArrayList<MemberDTO>();
		   
		   try(
					Connection conn =  dataSource.getConnection(); 
					PreparedStatement  pstmt =  conn.prepareStatement(sql);
				   // select 구문은 executeQuery()실행한 결과를 ResultSet객체에
				   // 담는다.
				    ResultSet rs = pstmt.executeQuery();
					  ){
			   // rs 의 결과 값
			   // no  id   pw  mail phone reg_date mod_date
			   // 1    1    1   1     1   2026-~   2026~ 
			   // 2    2    2   2     2   2026-~   2026~ 
			   // rs.next()다음행의 값이 존재하면 true, 아니면 false를 반환한다.
			   // while문은 rs.next()가 false가 되는 순간 종료된다.
			   // while문의 rs.next()먼저 한 행을 루프돌고, 그 다음행..
			    while(rs.next()) {
			    	MemberDTO mdto = new MemberDTO();
			    	// mdto가방을 rs의 결과값을 저장하는 용도로 사용할 예정
			    	mdto.setNo(rs.getInt("no"));
			    	mdto.setId(rs.getString("id"));
			    	mdto.setPw(rs.getString("pw"));
			    	mdto.setMail(rs.getString("mail"));
			    	mdto.setPhone(rs.getString("phone"));
			    	mdto.setReg_date(rs.getString("reg_date"));
			    	mdto.setMod_date(rs.getString("mod_date"));
			    	
			    	// ArrayList에 추가한다.
			    	list.add(mdto);
			    }
		   }catch(Exception e) {
			   e.printStackTrace();
		   }
		   
		return list;
	   }
	
 
	   //한사람에 대한 정보를 리턴하는 메소드
	   // 이 메소드의 목적은 👉 매개변수로 받은 id로 조회
	   // pstmt.setString(1, mdto.getId()); (x)
	   // 그런데 지금은 👉 빈 DTO(mdto)에서 id를 꺼냄
	   // mdto는 방금 new MemberDTO() 했기 때문에
	   // 👉 mdto.getId()는 null
	   // SELECT * FROM user_member WHERE id = null
	   public MemberDTO oneSelectMember(String id) {
		   System.out.println("MemberDAO oneSelectMember()메소드 확인");
		   // 한사람에 대한 정보만 리턴하기에 MemberDTO 클래스 생성
		   MemberDTO mdto = new MemberDTO();
		   String sql = "SELECT * FROM user_member WHERE id=?";
		   
		   try(
					Connection conn =  dataSource.getConnection(); 
					PreparedStatement  pstmt =  conn.prepareStatement(sql);
					  ){
			     // ✅ 실행문은 여기!
			     pstmt.setString(1, id);
			     ResultSet rs = pstmt.executeQuery();
			    //mdto.setId(rs.getString(1));
			   // rs.next() 없이 값 꺼내면 → 항상 빈 DTO임 주의 요망
			   if (rs.next()) {
			    mdto.setId(rs.getString("id"));
		    	mdto.setPw(rs.getString("pw"));
		    	mdto.setMail(rs.getString("mail"));
		    	mdto.setPhone(rs.getString("phone"));
		    	mdto.setReg_date(rs.getString("reg_date"));
		    	mdto.setMod_date(rs.getString("mod_date"));
			   }
		   }catch(Exception e) {
			    e.printStackTrace();
		   }
		   
		   return mdto;
	   }

	   //한사람에 패스워드만 리턴하는 메소드
	   public String getPss(String id) {
		   String pass="";
		   String sql = "SELECT pw FROM user_member WHERE id=?";
		   try(
					Connection conn =  dataSource.getConnection(); 
					PreparedStatement  pstmt =  conn.prepareStatement(sql);
					  ){
				        pstmt.setString(1, id);
				        ResultSet rs = pstmt.executeQuery();
				        if (rs.next()) {
						    pass=rs.getString(1);//패스워드 값에 저장된 매핑인덱스
						   }
			  }catch(Exception e) {
				  e.printStackTrace();
			  }
			  
		return pass;
	   }
	   
	   // ------------------------------------------------------------------
	   // 개인 정보 수정하는 메소드
	   public int updateMember(MemberDTO mdto) {
		   System.out.println("MemberDAO oneSelectMember()메소드 확인");
		   
		   int result = 0;
		   String sql = "UPDATE user_member SET mail=?,phone=? WHERE id=? ";
		   try(
					Connection conn =  dataSource.getConnection(); 
					PreparedStatement  pstmt =  conn.prepareStatement(sql);
					  ){
				        pstmt.setString(1, mdto.getMail());
				        pstmt.setString(2, mdto.getPhone());
				        pstmt.setString(3, mdto.getId());
				        result = pstmt.executeUpdate();
				        System.out.println("UPDATE result = " + result);
			  }catch(Exception e) {
				  e.printStackTrace();
			  }
			  
		return result;
	   }
	   
	   //-------------------------------------------------------------------
	   
	   //한 사람의 회원 삭제 메소드
	   public int deleteMember(String id) {
		   int result = 0;
		   String sql = "DELETE FROM user_member WHERE id=? ";
		   try(
					Connection conn =  dataSource.getConnection(); 
					PreparedStatement  pstmt =  conn.prepareStatement(sql);
					  ){
				        pstmt.setString(1, id);
				        result = pstmt.executeUpdate();
			  }catch(Exception e) {
				  e.printStackTrace();
			  }
		   
		   return result;
	   }
	  
	   
	   // 개인 정보 삭제하는 메소드
	   
}
