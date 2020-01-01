package com.example.demo;

import java.sql.ResultSet;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class TimeTableDao {
	private JdbcTemplate jdbcTemplate;

	public TimeTableDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// email을 이용해 로그인한 멤버의 시간표를 select하는 SQL
	public List<TimeTable> selectAll(String email) {
		List<TimeTable> results = jdbcTemplate.query(
				"select * from TIMETABLE where EMAIL=? order by DAY asc, START asc;", (ResultSet rs, int rowNum) -> {
					TimeTable table = new TimeTable(rs.getString("subject"), rs.getString("professor"),
							rs.getString("color"), rs.getString("day"), rs.getString("start"), rs.getString("finish"),
							rs.getString("place"), rs.getString("email"), rs.getInt("auto"));
					table.setSubject(rs.getString("subject"));
					table.setProfessor(rs.getString("professor"));
					table.setColor(rs.getString("color"));
					table.setDay(rs.getString("day"));
					table.setStart(rs.getString("start"));
					table.setFinish(rs.getString("finish"));
					table.setPlace(rs.getString("place"));
					table.setAuto(rs.getInt("auto"));
					return table;
				}, email);
		return results;
	}

	// email을 이용해  로그인한 멤버의 timetable 테이블 count
	public int count(String email) {
		return jdbcTemplate.queryForObject("select count(*) from TIMETABLE where EMAIL=?", Integer.class, email);

	}

	// 시간표 정보를 insert하는 SQL
	public void insertAll(String subject, String professor, String color, String day, String start, String finish,
			String place, String email) {
		jdbcTemplate.update(
				"insert into TIMETABLE(SUBJECT, PROFESSOR, COLOR, DAY, START, FINISH, PLACE, EMAIL) values(?,?,?,?,?,?,?,?)",
				subject, professor, color, day, start, finish, place, email);
	}

	// 시간표 정보를 update하는 SQL
	public void updateAll(TimeTable tt) {
		jdbcTemplate.update(
				"update TIMETABLE set SUBJECT=?, PROFESSOR=?, COLOR=?, DAY=?, START=?, FINISH=?, PLACE=? WHERE AUTO=?",
				tt.getSubject(), tt.getProfessor(), tt.getColor(), tt.getDay(), tt.getStart(), tt.getFinish(),
				tt.getPlace(), tt.getAuto());

	}

	// 해당 번호의 시간표를 delete하는 SQL
	public void deleteAll(int auto) {
		jdbcTemplate.update("delete from TIMETABLE where AUTO=?", auto);
	}

	// 로그인한 멤버의 시간표를 delete하는 SQL
	public void deleteMember(String email) {
		jdbcTemplate.update("delete from TIMETABLE where EMAIL=?", email);
	}
}
