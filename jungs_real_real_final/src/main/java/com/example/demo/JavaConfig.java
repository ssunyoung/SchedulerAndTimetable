package com.example.demo;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
	// jungs 계정으로 termproject 데이터베이스 연결
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost/termproject?characterEncoding=utf8&serverTimezone=UTC");
		ds.setUsername("jungs");
		ds.setPassword("jungs");
		ds.setInitialSize(2);
		ds.setMaxActive(10);
		ds.setTestWhileIdle(true);
		ds.setMinEvictableIdleTimeMillis(60000 * 3);
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
		return ds;
	}

	// termproject.timetable 테이블 ( 강의 시간표 내용 )
	@Bean
	public TimeTableDao timetableDao() {
		return new TimeTableDao(dataSource());
	}

	// termproject.member 테이블 ( 회원 정보 )
	@Bean
	public MemberDao memberDao() {
		return new MemberDao(dataSource());
	}

	// termproject.planner 테이블 ( 회원 별 복수개의 단기 플래너 관리 )
	@Bean
	public PlannerDao plannerDao() {
		return new PlannerDao(dataSource());
	}

	// termproject.schedule 테이블 ( 단기 플래너 마다의 일정 내용 )
	@Bean
	public ScheduleDao scheduleDao() {
		return new ScheduleDao(dataSource());
	}
}
