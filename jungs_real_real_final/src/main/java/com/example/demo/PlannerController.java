package com.example.demo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlannerController {
	@Autowired
	private PlannerDao plannerDao;
	@Autowired
	private ScheduleDao scheduleDao;

	// planner - 플래너 이름 리스트와 플래너들을 출력
	@RequestMapping(value = "/planner", method = { RequestMethod.GET, RequestMethod.POST })
	public String planner(Model model, HttpSession session, @RequestParam(value = "auto", required = false) int auto_db)
			throws ParseException {
		// 세션에 존재하는 로그인한 회원의 Email
		String memberEmail = (String) session.getAttribute("memberEmail");
		model.addAttribute("isClick", false);
		model.addAttribute("message", "달력을 클릭해 주세요!!");

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			// 로그인한 회원의 달력데이터를 얻기 위한 count
			int count = plannerDao.count(memberEmail);
			// 캘린더 선언
			Calendar Fcal = Calendar.getInstance();
			Calendar Lcal = Calendar.getInstance();

			if (count != 0) {
				model.addAttribute("isList", true);
				// 날짜 변환에 필요한 변수선언
				String[] titleBoard = new String[count];
				int[] autoBoard = new int[count];
				int auto = 0;
				String first = "";
				String last = "";
				int F_week;
				int L_week;
				Date F_date = new Date();
				Date L_date = new Date();

				List<Planner> planner = plannerDao.selectAll(memberEmail);

				// 타이틀, 시작일, 종료일 얻어오기
				for (int i = 0; i < count; i++) {
					titleBoard[i] = planner.get(i).getTitle();
					autoBoard[i] = planner.get(i).getAuto();
					if (auto_db == planner.get(i).getAuto()) {
						auto = planner.get(i).getAuto();
						first = planner.get(i).getFirst();
						last = planner.get(i).getLast();
					}

				}
				// 날짜(date)타입을 format함수를 통해 원하는 형태로 변환
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat transFormat = new SimpleDateFormat("MM / dd");
				// 임의의 값 넣어줌
				String[] dateList = new String[50];
				// 전체 array를 x로 초기화
				Arrays.fill(dateList, "x");

				// 시작일과 종료일 사이의 날짜를 구해서 배열에 저장
				for (int i = 0; i < count; i++) {
					if (auto == autoBoard[i]) {
						F_date = format.parse(first);
						L_date = format.parse(last);
						Fcal.setTime(F_date);
						Lcal.setTime(L_date);
						F_week = Fcal.get(Calendar.DAY_OF_WEEK);// 2 월요일
						L_week = Lcal.get(Calendar.DAY_OF_WEEK);// 6 금요일
						int j = F_week - 1;

						for (int k = 0; k < F_week - 1; k++) {
							dateList[k] = "no";
						}
						while (Fcal.compareTo(Lcal) != 1) {
							dateList[j] = transFormat.format(Fcal.getTime());
							Fcal.add(Calendar.DATE, 1);
							j++;
						}
						for (int k = 0; k < (7 - L_week); k++) {
							dateList[j + k] = "no";
						}
					}
				}

				model.addAttribute("title", titleBoard); // title array
				model.addAttribute("auto", autoBoard); // auto array

				List<Schedule> scheduleList = scheduleDao.selectAll(auto_db);

				if (auto_db != 0) {
					model.addAttribute("isClick", true);
					model.addAttribute("dateList", dateList);
					model.addAttribute("p_auto", auto_db);
					model.addAttribute("scheduleList", scheduleList);

				}

				return "planner";

			}
			// 해당 회원 아이디에 달력정보가 없으면
			else {
				model.addAttribute("isList", false);
				model.addAttribute("message", "달력을 추가해 주세요!!");
				return "planner";
			}
		}
	}

	// addPlanner - 플래너DB에 새로운 플래너 추가
	@RequestMapping(value = "/addPlanner", method = { RequestMethod.GET, RequestMethod.POST })
	public String addPlanner(Model model, HttpSession session,
			@RequestParam(value = "m_title", required = false) String title,
			@RequestParam(value = "m_first", required = false) String first,
			@RequestParam(value = "m_last", required = false) String last,
			@RequestParam(value = "add", required = false) String add) {
		String memberEmail = (String) session.getAttribute("memberEmail");
		String ss = "Add";
		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			if (ss.equals(add)) {
				// 입력값이 입력되지 않았을 경우
				if (title.equals("") || first.equals("") || last.equals("")) {
					return "redirect:/planner?auto=0";
				} else {
					plannerDao.insertAll(memberEmail, title, first, last);
					return "redirect:/planner?auto=0";
				}
			}
			return "planner";
		}
	}

	// deletePlanner - 플래너DB에서 플래너 삭제
	@RequestMapping(value = "/deletePlanner", method = { RequestMethod.GET, RequestMethod.POST })
	public String deletePlanner(Model model, HttpSession session,
			@RequestParam(value = "auto_delete", required = false) String auto_req) {

		String memberEmail = (String) session.getAttribute("memberEmail");
		int auto = Integer.parseInt(auto_req);

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			plannerDao.deleteAll(auto);
			scheduleDao.deleteByAuto(auto);

			return "redirect:/planner?auto=0";
		}
	}

	// updatePlanner - 플래너DB에서 플래너 수정
	@RequestMapping(value = "/updatePlanner", method = { RequestMethod.GET, RequestMethod.POST })
	public String updatePlanner(Model model, HttpSession session,
			@RequestParam(value = "auto_update", required = false) String auto_req,
			@RequestParam(value = "title_update", required = false) String title) {

		String memberEmail = (String) session.getAttribute("memberEmail");
		int auto = Integer.parseInt(auto_req);

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			// 로그인한 회원이 존재하면 title 컬럼 수정
			if (!title.equals(""))
				plannerDao.updateAll(auto, title);

			return "redirect:/planner?auto=0";
		}
	}

	// scheduleAdd - 스케줄DB에 스케줄 추가 페이지 출력
	@RequestMapping(value = "/scheduleAdd", method = { RequestMethod.GET, RequestMethod.POST })
	public String scheduleAdd(Model model, HttpSession session,
			@RequestParam(value = "md_date", required = false) String date,
			@RequestParam(value = "md_auto", required = false) String auto,
			@RequestParam(value = "add", required = false) String add) {

		String memberEmail = (String) session.getAttribute("memberEmail");

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			model.addAttribute("date", date);
			model.addAttribute("auto", auto);
			return "scheduleAdd";
		}
	}

	// scheduleAddCheck - 스케줄DB에 스케줄 추가
	@RequestMapping(value = "/scheduleAddCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public String scheduleAddCheck(Model model, HttpSession session,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "finishTime", required = false) String finishTime,
			@RequestParam(value = "color", required = false) String color,
			@RequestParam(value = "place", required = false) String place,
			@RequestParam(value = "memo", required = false) String memo,
			@RequestParam(value = "auto", required = false) String auto) {

		String memberEmail = (String) session.getAttribute("memberEmail");

		model.addAttribute("date", date);
		model.addAttribute("auto", auto);

		int auto_db = Integer.parseInt(auto);

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			// 입력값이 입력되지 않았을 경우
			if (title.equals("") || color.equals("")) {
				return "scheduleAdd";
			} else {
				scheduleDao.insertAll(auto_db, date, title, startTime, finishTime, color, place, memo);
				return "redirect:/planner?auto=" + auto;
			}
		}
	}

	// scheduleDetail - 스케줄 수정,삭제하는 페이지 출력
	@RequestMapping(value = "/scheduleDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String scheduleDetail(Model model, HttpSession session,
			@RequestParam(value = "auto", required = false) String auto_req,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "title", required = false) String title) {

		String memberEmail = (String) session.getAttribute("memberEmail");
		int auto = Integer.parseInt(auto_req);

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			List<Schedule> scheduleList = scheduleDao.selectOne(auto, date, title);
			String start = scheduleList.get(0).getStart();
			String finish = scheduleList.get(0).getFinish();
			String memo = scheduleList.get(0).getMemo();
			String place = scheduleList.get(0).getPlace();
			model.addAttribute("auto", auto_req);
			model.addAttribute("date", date);
			model.addAttribute("title", title);
			model.addAttribute("color", scheduleList.get(0).getColor());
			model.addAttribute("start", start);
			model.addAttribute("finish", finish);
			model.addAttribute("memo", memo);
			model.addAttribute("place", place);

			return "scheduleDetail";
		}
	}

	// scheduleUpdate - 스케줄DB의 스케줄 수정
	@RequestMapping(value = "/scheduleUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	public String scheduleUpdate(Model model, HttpSession session,
			@RequestParam(value = "auto", required = false) String auto_req,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "last_title", required = false) String last_title,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "color", required = false) String color,
			@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "finish", required = false) String finish,
			@RequestParam(value = "memo", required = false) String memo,
			@RequestParam(value = "place", required = false) String place) {

		String memberEmail = (String) session.getAttribute("memberEmail");
		int auto = Integer.parseInt(auto_req);

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			// 입력값이 입력되지 않았을 경우
			if (title.equals("") || color.equals("")) {

				return "redirect:/scheduleDetail?auto=" + auto + "&title=" + last_title + "&date=" + date;

			} else {
				scheduleDao.updateAll(title, color, start, finish, memo, place, auto, date, last_title);
				return "redirect:/planner?auto=" + auto;
			}
		}
	}

	// scheduleDelete - 스케줄DB의 스케줄 삭제
	@RequestMapping(value = "/scheduleDelete", method = { RequestMethod.GET, RequestMethod.POST })
	public String scheduleDelete(Model model, HttpSession session,
			@RequestParam(value = "m_auto", required = false) String auto_req,
			@RequestParam(value = "m_date", required = false) String date,
			@RequestParam(value = "m_title", required = false) String title) {

		String memberEmail = (String) session.getAttribute("memberEmail");
		int auto = Integer.parseInt(auto_req);

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			scheduleDao.deleteAll(auto, date, title);

			return "redirect:/planner?auto=" + auto;
		}
	}
}
