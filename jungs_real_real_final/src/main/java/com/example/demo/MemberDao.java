package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MemberDao {
	private JdbcTemplate jdbcTemplate;

	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// email을 이용해 회원정보 select하는 SQL
	public List<Member> selectByMember(String email) {
		List<Member> results = jdbcTemplate.query("select * from member where email=?", new RowMapper<Member>() {
			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member member = new Member(rs.getString("email"), rs.getString("password"), rs.getString("name"),
						rs.getString("studentid"));

				member.setName(rs.getString("name"));
				member.setStudentId(rs.getString("studentid"));
				member.setPassword(rs.getString("password"));

				return member;
			}
		}, email);

		return results;
	}

	// 학번을 이용해서 회원정보 select하는 SQL
	public List<Member> selectByEmail(String studentId) {
		List<Member> results = jdbcTemplate.query("select * from member where studentid=?", new RowMapper<Member>() {
			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member member = new Member(rs.getString("email"), rs.getString("password"), rs.getString("name"),
						rs.getString("studentid"));
				member.setName(rs.getString("name"));
				member.setStudentId(rs.getString("studentid"));
				member.setPassword(rs.getString("password"));
				member.setEmail(rs.getString("email"));

				return member;
			}
		}, studentId);

		return results;
	}

	// 이메일에 의한 회원 수
	public int count(String email) {
		return jdbcTemplate.queryForObject("select count(*) from MEMBER where EMAIL=?", Integer.class, email);

	}

	// 학번에 의한 회원 수
	public int countId(String studentId) {
		return jdbcTemplate.queryForObject("select count(*) from MEMBER where STUDENTID=?", Integer.class, studentId);

	}

	// 테이블 모두 select하는 SQL
	public List<Member> selectAll() {
		List<Member> results = jdbcTemplate.query("select * from MEMBER", (ResultSet rs, int rowNum) -> {
			Member member = new Member(rs.getString("name"), rs.getString("email"), rs.getString("password"),
					rs.getString("studentid"));
			return member;
		});
		return results;
	}

	// Member 테이블에 insert 하는 SQL
	public void insertMember(RegisterRequest rr) {
		jdbcTemplate.update("insert into MEMBER(EMAIL, PASSWORD, NAME, STUDENTID) values(?,?,?,?)", rr.getEmail(),
				rr.getPassword(), rr.getName(), rr.getStudentId());
	}

	// Member 테이블에 password update 하는 SQL
	public void updatePassword(String password, String email) {
		jdbcTemplate.update("update MEMBER set PASSWORD=? where EMAIL=?", password, email);

	}

	// Member 테이블에 회원정보를 update 하는 SQL
	public void updateMember(String email, String name, String studentId) {
		jdbcTemplate.update("update MEMBER set NAME=?, STUDENTID=? where EMAIL=?", name, studentId, email);

	}
	// Member 테이블에 회원정보를 delete 하는 SQL
	public void deleteMember(String email) {
		jdbcTemplate.update("delete from MEMBER where EMAIL=?", email);

	}

}
