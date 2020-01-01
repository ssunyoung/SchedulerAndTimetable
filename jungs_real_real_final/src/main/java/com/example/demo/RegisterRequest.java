package com.example.demo;

// 회원 가입시 사용
public class RegisterRequest {
	private String email;
	private String password;
	private String passwordConfirm;
	private String name;
	private String studentId;

	// 생성자
	public RegisterRequest(String email, String name, String password, String passwordConfirm, String studentId) {
		this.email = email;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.studentId = studentId;
		this.name = name;
	}

	// getter & setter
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public boolean PasswordEqual() {
		return password.equals(passwordConfirm);
	}

}
