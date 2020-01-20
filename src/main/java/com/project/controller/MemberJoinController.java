package com.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.service.MemberJoinService;
import com.project.vo.MemberVO;

@Controller
public class MemberJoinController {
	
	@Autowired
	private MemberJoinService memberJoinService;
	

	//회원가입 view 페이지
	@RequestMapping("/memberjoin")
	public String MemberJoin() {
		
		return "MemberJoin/MemberJoin";
	}
	
	//회원가입 DB저장
	@RequestMapping("/memberjoin_ok")
	public String MemberJoin_ok(MemberVO mb, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		
		String user_birthdayYY = request.getParameter("user_birthdayYY");
		String user_birthdayMM = request.getParameter("user_birthdayMM");
		String user_birthdayDD = request.getParameter("user_birthdayDD");
		
		if(user_birthdayMM.length() == 1) {
			user_birthdayMM = 0 + user_birthdayMM;
		}
		if(user_birthdayDD.length() == 1) {
			user_birthdayDD = 0 + user_birthdayDD;
		}
		
		
		String user_birthday = user_birthdayYY + user_birthdayMM + user_birthdayDD;
		mb.setUser_birthday(user_birthday);
		
		System.out.println(mb.getJoin_date());
		if(mb.getUser_id().equals("") || mb.getUser_pwd().equals("") || mb.getUser_name().equals("") || 
				mb.getUser_gender().equals("") || mb.getUser_email().equals("") || 
				mb.getUser_phone().equals("") || user_birthdayYY.equals("") || user_birthdayMM.equals("") ||
				user_birthdayDD.equals("")) {
			
			System.out.println("회원가입 실패");
            
			out.println("<script>alert('회원가입 정보를 확인해주세요.'); history.go(-1);</script>");
            out.flush();
		}else {
			System.out.println("회원가입 성공");			
			memberJoinService.insert(mb);
			
			out.println("<script>alert('회원가입이 정상적으로 처리되었습니다.'); location='/';</script>");
			out.flush();
		}
		
		
		return null;		
	}
	
	//중복 아이디 체크
	@RequestMapping("/checkID")
	@ResponseBody
	public String NewSmsCode(String checkwords) {

		String result;
		
		System.out.println("회원가입 id값(중복id체크) = "+checkwords);
		result = memberJoinService.SearchID(checkwords);
		
		if(result != null) {
			System.out.println(result + "중복값 반환");
			return result;
		}else {
			result = "no Equals ID";
			return result;
		}
	}

}
