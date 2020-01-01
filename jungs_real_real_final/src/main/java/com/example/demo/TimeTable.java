package com.example.demo;

// termproject.timetable 테이블 ( 강의 시간표 내용 )
public class TimeTable {
	private String subject;
	private String professor;
	private String color;
	private String day;
	private String start;
	private String finish;
	private String place;
	private String email;
	private int auto;

	// 생성자
	public TimeTable(String subject, String professor, String color, String day, String start, String finish,
			String place, String email, int auto) {
		this.subject = subject;
		this.professor = professor;
		this.color = color;
		this.day = day;
		this.start = start;
		this.finish = finish;
		this.place = place;
		this.email = email;
		this.auto = auto;

	}

	// getter&setter
	public int getAuto() {
		return auto;
	}

	public void setAuto(int auto) {
		this.auto = auto;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
