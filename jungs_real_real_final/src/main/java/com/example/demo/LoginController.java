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
public class LoginController {

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private TimeTableDao timetableDao;
	@Autowired
	private PlannerDao plannerDao;
	Member member;

	// index 화면은 login 화면으로 설정
	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public String home(Model model) {
		return "/login";
	}

	// login 화면
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(Model model) {
		return "login";
	}

	// logout 화면
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(Model model, HttpSession session) {
		session.invalidate();
		return "/login";
	}

	// loginConfirm - 로그인한 내용을 검사
	@RequestMapping(value = "/loginConfirm", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginConfirm(Model model, @RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "submit", required = false) String submit, HttpSession session) {
		String ss = "Login";
		String nextPage = null;

		if (ss.equals(submit)) {
			// email, password가 입력되지 않으면 페이지이동 X
			if ((email.equals("")) || (address.equals("")) || (password.equals(""))) {
				model.addAttribute("message", "Please, Enter the Correct Information");
				nextPage = "login";
			}
			// email password 값 입력되면 loginCheck로 페이지 이동
			else {
				email = email + address;
				int rowCount = memberDao.count(email);

				// 회원이 존재하는 경우
				if (rowCount == 1) {
					List<Member> memberList = memberDao.selectByMember(email);
					model.addAttribute("members", memberList.get(0));

					// 데이터 베이스에서의 회원 비밀번호 확인
					String passwordCheck = memberList.get(0).getPassword();

					// DB 비밀번호와 입력 비밀번호 일치 여부
					if (passwordCheck.equals(password)) {
						model.addAttribute("check", true);
						// 비밀번호 일치하는 경우
						// 아이디로 사용되는 email을 session에 memberEmail로 저장
						session.setAttribute("memberEmail", email);

						nextPage = "redirect:/timetable";
					} else {
						// 비밀번호 불일치하는 경우
						model.addAttribute("message", "Password incorrect!");
						nextPage = "login";
					}
				} else {
					// 회원이 존재하지 않는 경우
					model.addAttribute("message", "Email does not exist");
					nextPage = "login";

				}
			}
		}
		return nextPage;
	}

	// findEmail - 회원이 Email 분실했을 경우 Email을 찾는 페이지
	@RequestMapping(value = "/findEmail", method = { RequestMethod.GET, RequestMethod.POST })
	public String findEmail(Model model, @RequestParam(value = "studentId", required = false) String studentId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "submit", required = false) String submit) {
		String ss = "Find";

		if (ss.equals(submit)) {
			if (studentId.equals("") || name.equals("")) {
				// 학번과 이름이 입력되지 않은 경우
				model.addAttribute("message", "Please, Enter the Correct Information");
			} else if (memberDao.countId(studentId) == 1) {
				// 데이터베이스에 학번이 존재하는 경우
				List<Member> memberList = memberDao.selectByEmail(studentId);
				String nameCheck = memberList.get(0).getName();

				if (nameCheck.equals(name)) {
					// 데이터베이스에 존재하는 학번,이름이 입력된 학번,이름과 매칭되는 경우
					model.addAttribute("message", "Your Email is <br><br> < " + memberList.get(0).getEmail() + " >");
				} else
					// 데이터베이스에 학번이 존재하지만 이름이 불일치 하는 경우
					model.addAttribute("message", "Please, Enter the Correct Name");

			} else {
				// 입력된 학번,이름에 의한 회원이 없는 경우
				model.addAttribute("message", "Email does not Exist.");
			}
		}
		return "findEmail";
	}

	// changePassword - 비밀번호 분실하였을 경우 이메일,이름 확인 후 비밀번호 변경
	@RequestMapping(value = "/changePassword", method = { RequestMethod.GET, RequestMethod.POST })
	public String changePassword(Model model, @RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "submit", required = false) String submit) {
		String ss = "Check";
		String fullEmail = email + address;
		model.addAttribute("isButton", false);

		if (ss.equals(submit)) {
			if (email.equals("") || address.equals("") || name.equals("")) {
				// 입력값이 입력되지 않았을 경우
				model.addAttribute("message", "Please, Enter the Correct Information");

			} else if (memberDao.count(fullEmail) == 1) {
				// 이메일이 존재하는 경우(회원)
				// 이메일을 이용하여 데이터베이스에서 회원 정보를 가져옴
				List<Member> memberList = memberDao.selectByMember(fullEmail);
				String nameCheck = memberList.get(0).getName();

				if (nameCheck.equals(name)) {
					// 입력한 회원의 이메일,이름이 회원정보와 일치하는 경우
					// 비밀번호 변경 버튼 추가
					model.addAttribute("message", "Do you want to Change Your Password?");
					model.addAttribute("isButton", true);
					model.addAttribute("fullEmail", fullEmail);
				} else
					// 이름이 일치하지 않는 경우
					model.addAttribute("message", "Please, Enter the Correct Name");

			} else {
				// 이메일이 존재하지 않는 경우
				model.addAttribute("message", "Email does not Exist.");
			}
		}
		return "changePassword";
	}

	// changePasswordPost - changePassword에서 비밀번호 변경 버튼을 눌렀을 경우 새로운 비밀번호 생성
	@RequestMapping(value = "/changePasswordPost", method = { RequestMethod.GET, RequestMethod.POST })
	public String changePasswordPost(Model model, @RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "passwordConfirm", required = false) String passwordConfirm,
			@RequestParam(value = "Memail", required = false) String Memail,
			@RequestParam(value = "change", required = false) String change) {
		String ss = "Change";

		model.addAttribute("isMessage", false);
		model.addAttribute("isLog", false);

		if (ss.equals(change)) {
			// 새 비밀번호와 새 비밀번호 check를 입력받음
			if (password.equals("") || passwordConfirm.equals("")) {
				// 입력값이 입력되지 않았을 경우
				model.addAttribute("isMessage", true);
				model.addAttribute("modalMessage", "<font color=red>Password Change Failed!</font>");
			} else if (password.equals(passwordConfirm)) {
				// 새 비밀번호와 새 비밀번호 check가 일치하는 경우 데이터베이스 update
				memberDao.updatePassword(password, Memail);
				model.addAttribute("isMessage", true);
				model.addAttribute("modalMessage", "<font color=blue>Password Changed Successfully!</font>");
				model.addAttribute("isLog", true);
			} else {
				// 새 비밀번호와 새 비밀번호 check가 일치하지 않는 경우
				model.addAttribute("isMessage", true);
				model.addAttribute("modalMessage", "<font color=red>Passwords Change Failed!</font>");
			}
		}
		return "changePassword";
	}

	// myPage - 회원 정보 상세보기
	@RequestMapping(value = "/myPage", method = { RequestMethod.GET, RequestMethod.POST })
	public String myPage(Model model, HttpSession session) {
		// 세션에 존재하는 로그인한 회원의 Email
		String memberEmail = (String) session.getAttribute("memberEmail");

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			// 이메일을 이용하여 데이터 베이스에서 로그인한 회원의 정보를 가져옴
			List<Member> memberList = memberDao.selectByMember(memberEmail);
			model.addAttribute("memberEmail", memberEmail);
			model.addAttribute("memberName", memberList.get(0).getName());
			model.addAttribute("memberStudentId", memberList.get(0).getStudentId());
		}
		return "myPage";
	}

	// myPageUpdate - 회원 정보 수정
	@RequestMapping(value = "/myPageUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	public String myPageUpdate(Model model, HttpSession session,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "passwordConfirm", required = false) String passwordConfirm,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "studentId", required = false) String studentId,
			@RequestParam(value = "modify", required = false) String modify) {

		String memberEmail = (String) session.getAttribute("memberEmail");
		String ss = "Modify";

		// 로그인되어 있는 회원의 정보를 가져옴
		List<Member> memberList = memberDao.selectByMember(memberEmail);
		String db_password = memberList.get(0).getPassword();
		String db_name = memberList.get(0).getName();
		String db_studentId = memberList.get(0).getStudentId();

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			if (ss.equals(modify)) {
				if (name.equals("") || password.equals("") || passwordConfirm.equals("") || studentId.equals("")
						|| !password.equals(passwordConfirm)) {
					// 입력값이 입력되지 않았을 경우
					return "redirect:/myPage";

				} else if (db_password.equals(password)) {
					// 입력한 비밀번호와 로그인되어 있는 회원의 비밀번호가 같은 경우
					if (name.equals(db_name) && studentId.equals(db_studentId)) {
						// 수정사항이 없는 경우
						return "redirect:/myPage";

					} else {
						// 수정사항이 있는 경우 데이터베이스 update
						memberDao.updateMember(memberEmail, name, studentId);
						model.addAttribute("message",
								"<font color=\"blue\">Please Login Again with changed Member Information!!</font>");
						// update 후 다시 로그인하게 함 - session의 정보 삭제
						session.invalidate();
						return "/login";
					}
				} else {
					return "redirect:/myPage";
				}
			} else {
				return "myPage";
			}
		}
	}

	// myPageDelete - 회원 정보 삭제
	@RequestMapping(value = "/myPageDelete", method = { RequestMethod.GET, RequestMethod.POST })
	public String myPageDelete(Model model, HttpSession session,
			@RequestParam(value = "m_password", required = false) String password,
			@RequestParam(value = "m_passwordConfirm", required = false) String passwordConfirm,
			@RequestParam(value = "ok", required = false) String ok) {

		String memberEmail = (String) session.getAttribute("memberEmail");
		String ss = "OK";
		List<Member> memberList = memberDao.selectByMember(memberEmail);
		String db_password = memberList.get(0).getPassword();

		if (memberEmail == null) {
			// 로그인한 회원이 없는 경우 접속 제한
			return "login";

		} else {
			if (ss.equals(ok)) {
				if (password.equals("") || passwordConfirm.equals("") || !password.equals(passwordConfirm)) {
					// 입력값이 입력되지 않았을 경우
					return "redirect:/myPage";

				} else if (db_password.equals(password)) {
					// 입력한 비밀번호와 로그인되어 있는 회원의 비밀번호가 같은 경우
					memberDao.deleteMember(memberEmail);

					// 회원 정보, 강의 시간표, 플래너 모두 제거 -> 세션 종료 후 로그인 화면으로 이동
					if (timetableDao.count(memberEmail) > 0) {
						if (plannerDao.count(memberEmail) > 0) {
							plannerDao.deleteByEmail(memberEmail);
						}
						timetableDao.deleteMember(memberEmail);
					}
					model.addAttribute("message",
							"<font color=\"red\">Your membership information has been deleted.!!</font>");
					session.invalidate();

					return "/login";
				}
			}
		}
		return "myPage";
	}

}