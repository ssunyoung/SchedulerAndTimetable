package com.example.demo;

import java.sql.ResultSet;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ScheduleDao {
	private JdbcTemplate jdbcTemplate;

	public ScheduleDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// auto를 이용해 select하는 SQL
	public List<Schedule> selectAll(int auto) {
		List<Schedule> results = jdbcTemplate.query("select * from SCHEDULE where AUTO=? order by DATE;",
				(ResultSet rs, int rowNum) -> {
					Schedule schedule = new Schedule(rs.getInt("auto"), rs.getString("date"), rs.getString("title"),
							rs.getString("start"), rs.getString("finish"), rs.getString("color"), rs.getString("place"),
							rs.getString("memo"));
					schedule.setMemo(rs.getString("memo"));
					schedule.setTitle(rs.getString("title"));
					schedule.setColor(rs.getString("color"));
					schedule.setDate(rs.getString("date"));
					schedule.setStart(rs.getString("start"));
					schedule.setFinish(rs.getString("finish"));
					schedule.setPlace(rs.getString("place"));
					schedule.setAuto(rs.getInt("auto"));
					return schedule;
				}, auto);
		return results;
	}

	// PK( auto, date, title)를 이용해 select하는 SQL
	public List<Schedule> selectOne(int auto, String date, String title) {
		List<Schedule> results = jdbcTemplate.query("select * from SCHEDULE where AUTO=? and Date=? and title=?",
				(ResultSet rs, int rowNum) -> {
					Schedule schedule = new Schedule(rs.getInt("auto"), rs.getString("date"), rs.getString("title"),
							rs.getString("start"), rs.getString("finish"), rs.getString("color"), rs.getString("place"),
							rs.getString("memo"));
					schedule.setMemo(rs.getString("memo"));
					schedule.setTitle(rs.getString("title"));
					schedule.setColor(rs.getString("color"));
					schedule.setDate(rs.getString("date"));
					schedule.setStart(rs.getString("start"));
					schedule.setFinish(rs.getString("finish"));
					schedule.setPlace(rs.getString("place"));
					schedule.setAuto(rs.getInt("auto"));
					return schedule;
				}, auto, date, title);
		return results;
	}

	// 로그인한 멤버의 schedule 테이블 count
	public int count(int auto) {
		return jdbcTemplate.queryForObject("select count(*) from SCHEDULE where AUTO=?", Integer.class, auto);

	}

	// schedule 테이블에 insert하는 SQL
	public void insertAll(int auto, String date, String title, String start, String finish, String color, String place,
			String memo) {
		jdbcTemplate.update(
				"insert into SCHEDULE(auto, date, title, start, finish, color, place, memo) values(?,?,?,?,?,?,?,?)",
				auto, date, title, start, finish, color, place, memo);
	}

	// 일정 수정 사항을 update하는 SQL
	public void updateAll(String title, String color, String start, String finish, String memo, String place, int auto,
			String date, String Ltitle) {
		jdbcTemplate.update(
				"update SCHEDULE set TITLE=?, COLOR=?, START=?, FINISH=?, MEMO=?, PLACE=? where AUTO=? and DATE=? and TITLE=?",
				title, color, start, finish, memo, place, auto, date, Ltitle);

	}

	// 일정 delete하는 SQL
	public void deleteAll(int auto, String date, String title) {
		jdbcTemplate.update("delete from SCHEDULE where AUTO=? and DATE=? and TITLE=?", auto, date, title);

	}

	// Planner가 삭제 될 때 해당 Planner의 일정이 모두 delete되도록 하는 SQL
	public void deleteByAuto(int auto) {
		jdbcTemplate.update("delete from SCHEDULE where AUTO=?", auto);

	}
}
