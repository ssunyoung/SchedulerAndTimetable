package com.example.demo;

// termproject.planner 테이블 ( 회원 별 복수개의 단기 플래너 관리 )
public class Planner {
	private String title;
	private String first;
	private String last;
	private String email;
	private int auto;

	// 생성자
	public Planner(String email, String title, String first, String last, int auto) {
		this.title = title;
		this.first = first;
		this.last = last;
		this.email = email;
		this.auto = auto;
	}

	// getter & setter
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAuto() {
		return auto;
	}

	public void setAuto(int auto) {
		this.auto = auto;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

}
