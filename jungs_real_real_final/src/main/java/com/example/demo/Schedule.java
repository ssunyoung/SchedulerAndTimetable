package com.example.demo;

public class Schedule {
	private int auto;
	private String date;
	private String title;
	private String start;
	private String finish;
	private String color;
	private String place;
	private String memo;
	
	//constructor
	public Schedule(int auto, String date, String title, String start, String finish, String color, String place,
			String memo) {
		this.auto = auto;
		this.date = date;
		this.title = title;
		this.start = start;
		this.finish = finish;
		this.color = color;
		this.place = place;
		this.memo = memo;
	}
	
	//getter & setters
	public int getAuto() {
		return auto;
	}

	public void setAuto(int auto) {
		this.auto = auto;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	
}
