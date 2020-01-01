package com.example.demo;

import java.sql.ResultSet;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class PlannerDao {
	private JdbcTemplate jdbcTemplate;

	public PlannerDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// 로그인한 멤버의 Planner를 select하는 SQL
	public List<Planner> selectAll(String email) {
		List<Planner> results = jdbcTemplate.query("select * from PLANNER where EMAIL=?",
				(ResultSet rs, int rowNum) -> {
					Planner planner = new Planner(rs.getString("email"), rs.getString("title"), rs.getString("first"),
							rs.getString("last"), rs.getInt("auto"));

					planner.setEmail(rs.getString("email"));
					planner.setTitle(rs.getString("title"));
					planner.setFirst(rs.getString("first"));
					planner.setLast(rs.getString("last"));
					planner.setAuto(rs.getInt("auto"));

					return planner;
				}, email);
		return results;
	}

	// 로그인한 멤버의 calendar 테이블 count (달력 개수)
	public int count(String email) {
		return jdbcTemplate.queryForObject("select count(*) from PLANNER where EMAIL=?", Integer.class, email);

	}

	// planner 테이블에 insert 하는 SQL
	public void insertAll(String email, String title, String first, String last) {
		jdbcTemplate.update("insert into PLANNER(EMAIL, TITLE, FIRST, LAST) values(?,?,?,?)", email, title, first,
				last);
	}
	// planner 테이블에 delete하는 SQL
	public void deleteAll(int auto) {
		jdbcTemplate.update("delete from PLANNER where AUTO=?", auto);

	}	
	// planner 테이블에 delete하는 SQL
	public void deleteByEmail(String email) {
		jdbcTemplate.update("delete from PLANNER where EMAIL=?", email);

	}
	// planner 테이블에 title update하는 SQL
	public void updateAll(int auto, String title) {
		jdbcTemplate.update("update PLANNER set TITLE=? where AUTO=?", title, auto);

	}

}
