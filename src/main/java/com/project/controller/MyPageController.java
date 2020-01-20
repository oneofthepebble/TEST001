package com.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.client.http.HttpResponse;
import com.project.service.MemberJoinService;
import com.project.vo.BoardVO;
import com.project.vo.MemberVO;
import com.project.vo.NormalBoardVO;

@Controller
public class MyPageController {
	
	@Autowired
	private MemberJoinService memberJoinService;
	
	//MyPage 정보값 출력
	@RequestMapping("/mypage")
	public ModelAndView MyPage(ModelAndView mav, HttpSession session,HttpServletResponse response) throws IOException {
		MemberVO mVo = new MemberVO();
		String user_id = (String) session.getAttribute("user_id");
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
				
//		mVo.setUser_id((String) session.getAttribute("user_id"));
System.out.println("session="+user_id);
//		아이디를 기준으로 회원 정보 출력
		if(user_id != null) {
			mVo.setUser_id(user_id);
			mVo = memberJoinService.MypageView(mVo);
			List<NormalBoardVO> normalBoard = memberJoinService.NormalBoardView(mVo);
			List<BoardVO> board = memberJoinService.BoardView(mVo);
			
//			회원정보들을 ModelAndView에 담음
			mav.addObject("MemberInfo",mVo);
			mav.addObject("NormalBoard",normalBoard);
			mav.addObject("Board",board);
			mav.setViewName("MyPage/MyPage");
			return mav;	
		}else {
			out.println("<script>alert('로그인을 해주세요'); history.go(-1);</script>");
		}
		
	return null;
	}
	
	//비동기식 ajax 회원정보 email 수정
	@RequestMapping("/UpdateUserEmail")
	@ResponseBody
	public String UpdateEmail(String user_id, String user_update) {
		
		MemberVO updateMember = new MemberVO();
		updateMember.setUser_id(user_id);
		updateMember.setUser_email(user_update);
		
		int result;
		
		System.out.println("user_id:"+updateMember.getUser_id() + "/ update_email:" +updateMember.getUser_email());
		result = memberJoinService.UserInfoEmail(updateMember);
		
		if(result == 1) {
			return updateMember.getUser_email();
		}else {
			String result1 = "-1";
			return result1;
		}
	}
	
	//비동기식 ajax 회원정보 휴대폰 번호 수정
	@RequestMapping("/updateUserPN")
	@ResponseBody
	public String UpdatePhoneNumber(String user_id, String user_update) {
		
		MemberVO updateMember = new MemberVO();
		updateMember.setUser_id(user_id);
		updateMember.setUser_phone(user_update);
		
		int result;
		
		System.out.println("user_id:"+updateMember.getUser_id() + "/ update_phone:" +updateMember.getUser_phone());
		result = memberJoinService.UserInfoPhone(updateMember);
		
		if(result == 1) {
			return updateMember.getUser_phone();
		}else {
			String result1 = "-1";
			return result1;
		}
	}
	
	//비동기식 ajax 회원정보 비밀번호 수정
	@RequestMapping("/UpdateUserPwd")
	@ResponseBody
	public String UpdatePassword(String user_id, String user_update) {
		
		MemberVO updateMember = new MemberVO();
		updateMember.setUser_id(user_id);
		updateMember.setUser_pwd(user_update);
		
		int result;
		
		System.out.println("user_id:"+updateMember.getUser_id() + "/ update_pwd:" +updateMember.getUser_pwd());
		result = memberJoinService.UserInfoPwd(updateMember);
		
		if(result == 1) {
			return "비밀번호 변경 완료";
		}else {
			String result1 = "-1";
			return result1;
		}
	}

}
