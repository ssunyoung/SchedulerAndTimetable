package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
	@Autowired
	private MemberDao memberDao;
	Member member;

	//register 화면
	@RequestMapping(value = "/register", method = { RequestMethod.GET, RequestMethod.POST })
	public String register(Model model) {
		return "register";
	}
	
	//registerCheck - 회원가입한 내용을 검사
	@RequestMapping(value = "/registerCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public String registerCheck(Model model, @RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "passwordConfirm", required = false) String passwordConfirm,
			@RequestParam(value = "studentId", required = false) String studentId) {
		
		// email, address, name, password, passwordConfirm, studentId 입력되지 않으면 페이지이동 X
		// password가 passwordConfirm 과 일치하지 않으면 페이지 이동x
		if (email.equals("") || address.equals("") || name.equals("") || password.equals("")
				|| passwordConfirm.equals("") || studentId.equals("") || studentId.length() != 10 || memberDao.countId(studentId)==1) {
				model.addAttribute("message","Please, Enter the Correct Information.");
		} 
		//위의 값들이 입력되면 registerCheck로 페이지 이동
		else {
			email = email+address;
			// 회원이 존재하지 않는 경우
			if (memberDao.count(email) == 0) {
				RegisterRequest rr = new RegisterRequest(email, name, password, passwordConfirm, studentId);
				rr.setEmail(email);
				rr.setName(name);
				rr.setPassword(password);
				rr.setPasswordConfirm(passwordConfirm);
				rr.setStudentId(studentId);
				//password와 passwordConfirm이 일치하는 경우
				if (rr.PasswordEqual() == true) {
					memberDao.insertMember(rr); 
					model.addAttribute("message","Your membership is Complete.<br><br> Please, <a href=\"login\"> Sign in.</a>");
				} 
				//password와 passwordConfirm이 일치하지 않은 경우
				else {
					model.addAttribute("message","Please, Enter the Correct Password.");
				}
			} 
			//회원이 존재하는 경우
			else {
				model.addAttribute("message", "This Email Already Exists.");
			}
		}
		return "register";
	}
}
