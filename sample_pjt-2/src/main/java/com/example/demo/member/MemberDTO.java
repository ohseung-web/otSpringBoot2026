package com.example.demo.member;

public class MemberDTO {
	private String id;
	private String pw;
	private String email;
	
	// getter : 값을 조회할때 사용한다
	// setter : 값을 수정할때 사용한다.
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	
	
}
