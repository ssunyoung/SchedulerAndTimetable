package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TimeTableController {
	@Autowired
	private TimeTableDao timetableDao;
	TimeTable timetable;
	// 시간표를 출력하기 위한 배열
	private String time[] = { "09:00", "09:15", "09:30", "09:45", "09:50", "10:00", "10:15", "10:30", "10:45", "10:50",
			"11:00", "11:15", "11:30", "11:45", "11:50", "12:00", "12:15", "12:30", "12:45", "12:50", "13:00", "13:15",
			"13:30", "13:45", "13:50", "14:00", "14:15", "14:30", "14:45", "14:50", "15:00", "15:15", "15:30", "15:45",
			"15:50", "16:00", "16:15", "16:30", "16:45", "16:50", "17:00", "17:15", "17:30", "17:45", "17:50",
			"18:00" };
	// 요일을 출력하기 위한 배열
	private String allDay[] = { "월", "화", "수", "목", "금" };

	// timetable - 시간표와 강좌추가부분을 출력해주는 화면
	@RequestMapping(value = "/timetable", method = { RequestMethod.GET, RequestMethod.POST })
	public String timetable(Model model, HttpSession session) {
		// 세션에 존재하는 로그인한 회원의 Email
		String memberEmail = (String) session.getAttribute("memberEmail");
		// 로그인한 회원의 시간표 정보를 다 가져옴
		List<TimeTable> timeList = timetableDao.selectAll(memberEmail);

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			// 로그인한 회원의 시간표가 차 있는 경우
			if (timetableDao.count(memberEmail) != 0) {
				model.addAttribute("isTable", true);
				// 로그인한 회원의 시간표가 비어있는 경우
			} else {
				model.addAttribute("isTable", false);
				model.addAttribute("message", "시간표가 비어있습니다. 강의를 추가해주세요!!");
			}
			// 시간 출력하기 위한 배열 전달
			model.addAttribute("time", time);
			model.addAttribute("timeList", timeList);

			// 시간표의 시작시간, 끝시간, 요일을 얻기위한 변수선언
			int S[] = new int[timeList.size()];
			int F[] = new int[timeList.size()];
			int D[] = new int[timeList.size()];

			// 각각의 변수에 넣어줌
			for (int i = 0; i < timeList.size(); i++) {
				String day = timeList.get(i).getDay();
				String start = timeList.get(i).getStart();
				String finish = timeList.get(i).getFinish();

//				System.out.println(start + finish);
				// 위에서 선언한 Time배열과 비교해서 시작시간, 끝시간 세팅
				for (int j = 0; j < time.length; j++) {
					for (int k = 0; k < time.length; k++) {
						if (start.equals(time[j]) && finish.equals(time[k])) {
							S[i] = j; // 시작시간 index
							F[i] = k; // 종료시간 index
						}
					}
				}
				// 위에서 선언한 allDay배열과 비교해서 요일 세팅
				for (int m = 0; m < allDay.length; m++) {
					if (day.equals(allDay[m])) {
						D[i] = m; // 요일 index
					}
				}
			}
			model.addAttribute("start", S);
			model.addAttribute("finish", F);
			model.addAttribute("day", D);
		}
		return "timetable";
	}

	// 시간표 DB에 새로운 강의를 추가
	@RequestMapping(value = "/timetableInsert", method = { RequestMethod.GET, RequestMethod.POST })
	public String timetableInsert(Model model, @RequestParam(value = "subject", required = false) String subject,
			@RequestParam(value = "professor", required = false) String professor,
			@RequestParam(value = "color", required = false) String color,
			@RequestParam(value = "day", required = false) String day,
			@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "finish", required = false) String finish,
			@RequestParam(value = "place", required = false) String place,
			@RequestParam(value = "submit", required = false) String submit, HttpSession session) {

		// 세션에 존재하는 로그인한 회원의 Email
		String memberEmail = (String) session.getAttribute("memberEmail");
		String ss = "Add";
		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			if (ss.equals(submit)) {
				// 만약 과목명,교수명,요일,시간,장소가 입력되지 않은값이면 계속 같은페이지를 리턴
				if ((subject.equals("")) || (professor.equals("")) || (color.equals("")) || (day.equals(""))
						|| (start.equals("")) || (finish.equals("")) || (place.equals(""))) {
					return "redirect:/timetable";
				}
				// 아니면, db 에 각 값들을 insert
				else {
					int S = 0;
					int F = 0;
					for (int j = 0; j < time.length; j++) {
						for (int k = 0; k < time.length; k++) {
							if (start.equals(time[j]) && finish.equals(time[k])) {
								S = j; // 시작시간 index
								F = k; // 종료시간 index
							}
						}
					}
					// 시작시간이 종료시간보다 크다면 timetable 리다이렉트
					if (S >= F) {
						return "redirect:/timetable";
					} else {
						// 시간표DB에 강의 추가
						timetableDao.insertAll(subject, professor, color, day, start, finish, place, memberEmail);

						return "redirect:/timetable";
					}
				}
			} else {
				return "timetable";
			}
		}
	}

	// timetableUpdate - 시간표 DB에 존재하는 강의를 수정
	@RequestMapping(value = "/timetableUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	public String timetableUpdate(Model model, @RequestParam(value = "m_subject", required = false) String subject,
			@RequestParam(value = "m_professor", required = false) String professor,
			@RequestParam(value = "m_auto", required = false) String aa,
			@RequestParam(value = "m_color", required = false) String color,
			@RequestParam(value = "m_day", required = false) String day,
			@RequestParam(value = "m_start", required = false) String start,
			@RequestParam(value = "m_finish", required = false) String finish,
			@RequestParam(value = "m_place", required = false) String place,
			@RequestParam(value = "modify", required = false) String modify, HttpSession session) {

		String memberEmail = (String) session.getAttribute("memberEmail");
		String mm = "Modify";
		int auto = Integer.parseInt(aa);

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			if (mm.equals(modify)) {
				if ((subject.equals("")) || (professor.equals("")) || (color.equals("")) || (day.equals(""))
						|| (start.equals("")) || (finish.equals("")) || (place.equals(""))) {
					// 입력값이 입력되지 않았을 경우
					return "redirect:/timetable";

				} else {
					// 시작시간과 종료시간을 받아와서 세팅 함
					int S = 0;
					int F = 0;
					for (int j = 0; j < time.length; j++) {
						for (int k = 0; k < time.length; k++) {
							if (start.equals(time[j]) && finish.equals(time[k])) {
								S = j; // 시작시간 index
								F = k; // 종료시간 index
							}
						}
					}
					// 시간입력이 제대로 됬을경우
					if (S < F) {
						TimeTable tt = new TimeTable(subject, professor, color, day, start, finish, place, memberEmail,
								auto);
						tt.setSubject(subject);
						tt.setProfessor(professor);
						tt.setColor(color);
						tt.setDay(day);
						tt.setStart(start);
						tt.setFinish(finish);
						tt.setEmail(memberEmail);
						tt.setPlace(place);
						tt.setAuto(auto);
						// 시간표의 정보를 새로운 값들로 update하여라
						timetableDao.updateAll(tt);

						return "redirect:/timetable";
					}
					// 시작시간이 종료시간보다 크다면 timetable 리다이렉트
					else {

						return "redirect:/timetable";
					}
				}
			}
			return "timetable";
		}
	}

	// timetalbeDelete - 시간표 DB에 존재하는 강의를 삭제
	@RequestMapping(value = "/timetableDelete", method = { RequestMethod.GET, RequestMethod.POST })
	public String timetableUpdate(Model model, @RequestParam(value = "md_subject", required = false) String subject,
			@RequestParam(value = "md_auto", required = false) String aa,
			@RequestParam(value = "ok", required = false) String ok, HttpSession session) {

		String memberEmail = (String) session.getAttribute("memberEmail");
		String ss = "OK";
		int auto = Integer.parseInt(aa);

		if (memberEmail == null) {
			return "login";

		} else {
			if (ss.equals(ok)) {
				// 강의 삭제
				timetableDao.deleteAll(auto);

				return "redirect:/timetable";
			}

		}

		return "timetable";
	}
}
