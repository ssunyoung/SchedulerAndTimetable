package com.example.demo;

// termproject.member 테이블 ( 회원 정보 )
public class Member {
	private String name;
	private String email;
	private String password;
	private String studentId;

	// 생성자
	public Member(String name, String email, String password, String studentId) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.studentId = studentId;
	}

	public Member(String email) {
		this.email = email;
	}

	// getter & setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

}