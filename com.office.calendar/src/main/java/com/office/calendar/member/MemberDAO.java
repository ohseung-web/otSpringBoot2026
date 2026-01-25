package com.office.calendar.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	//자원해제 다 필요없다
	
	//---------------------------------------------
	public boolean isMmeber(String id) {
		System.out.println("isMember()");
	
		// 많은 개발자들이 쿼리문은 대문자로 작성한다. 단, 소문자로 작성해도 무방하다.
		String sql = "SELECT COUNT(*) FROM USER_MEMBER WHERE ID=?";
	    int result = jdbcTemplate.queryForObject(sql, Integer.class, id);
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	//------------------------------------------------
	public int insertMember(MemberDTO mdto) {
		System.out.println("insertMember()");
		//MemberDTO 이용해서 삽입한다.
		String sql = "INSERT INTO USER_MEMBER(ID,PW,MAIL,PHONE) VALUES(?,?,?,?)";
		int result  = -1;
		try {
			// 추가된 행의 개수가 result에 담기는데 있으면 1, 아니면 0이 담긴다.
		 result = jdbcTemplate.update(sql,mdto.getId(),
				  mdto.getPw(),
				  mdto.getMail(),
				  mdto.getPhone());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//------------------------------------------------
	public MemberDTO selectMemberById(String id) {
		System.out.println("MemberDAO selectMemberById");
		
		String sql = "SELECT * FROM USER_MEMBER WHERE ID=?";
		
		List<MemberDTO> memberdtos = new ArrayList<>();
		
		try {
			memberdtos = jdbcTemplate.query(sql, new RowMapper<MemberDTO>() {

				@Override
				public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					MemberDTO memberdto = new MemberDTO();
					memberdto.setNo(rs.getInt("no"));
					memberdto.setId(rs.getString("id"));
					memberdto.setPw(rs.getString("pw"));
					memberdto.setMail(rs.getString("mail"));
					memberdto.setPhone(rs.getString("phone"));
					memberdto.setReg_date(rs.getString("reg_date"));
					memberdto.setMod_date(rs.getString("mod_date"));
					
					return memberdto;
				}
			}, id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return memberdtos.size()>0 ? memberdtos.get(0):null;
	}

	//------------------------------------------------------	
}
