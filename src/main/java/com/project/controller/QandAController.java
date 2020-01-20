package com.project.controller;


import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.service.QandAService;
import com.project.vo.QandAVO;

@Controller
public class QandAController {//Q&A게시판
	
	@Autowired
	private QandAService QandAService;
	
	/* 질문 게시판 글쓰기*/
	@RequestMapping("/QandA/QandA_write")
	public String QandA_write(
			HttpServletRequest request, Model m, HttpSession session,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		
		String user_id=(String)session.getAttribute("user_id");
		PrintWriter out=response.getWriter();
		
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		if(user_id==null) {
			out.println("<script>");
            out.println("alert('로그인 후 이용해주세요');");
            out.println("location='/login';");
            out.println("</script>");
		}else {
			m.addAttribute("page", page);
			m.addAttribute("user_id", user_id);
			return "QandA/QandA_write";
		}
		
		return null;
	}//QandA_write
	
	/* QandA 게시판 저장 */
	@RequestMapping("/QandA_write_ok")
	public String QandA_write_ok(
			QandAVO q,
			HttpServletRequest request) throws Exception {
		
		String q_title=request.getParameter("q_title");
		String q_cont=request.getParameter("q_cont");
		//제목, 내용을 가져옴
		
		q.setQ_title(q_title); q.setQ_cont(q_cont);
		
		this.QandAService.insertQandA(q);//자료실 저장
		
		return "redirect:/QandA/QandA_list?page=1";
	}//QandA_write_ok
	
	/* QandA 게시판 목록 */
	@RequestMapping("/QandA/QandA_list")
	public String QandA_list(
			Model listM,QandAVO q,
			HttpServletRequest request) {
		
		int page=1;
		int limit=10;
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String find_name = request.getParameter("find_name");
		String find_field = request.getParameter("find_field");
		//검색어, 검색필드 저장
		
		q.setFind_field(find_field);
		q.setFind_name("%"+find_name+"%");
		
		int totalCount=this.QandAService.getListCount(q);
		//검색전후 레코드 개수
		q.setStartrow((page-1)*10+1);//시작행번호
		q.setEndrow(q.getStartrow()+limit-1);//끝행 번호
		
		List<QandAVO> qlist=this.QandAService.getQandAList(q);
		//검색 전후 목록
		
		//총페이지 수
		int maxpage=(int)((double)totalCount/limit+0.95);
		
		//시작페이지
		int startpage=(((int)((double)page/10+0.9))-1)*10+1;
		
		//마지막 페이지
		int endpage=maxpage;
		
		if(endpage>startpage+10-1)
			endpage=startpage+10-1;
		
		listM.addAttribute("qlist",qlist);
		listM.addAttribute("page",page);
		listM.addAttribute("startpage",startpage);
		listM.addAttribute("endpage",endpage);
		listM.addAttribute("maxpage",maxpage);
		listM.addAttribute("totalcount",totalCount);
		listM.addAttribute("find_field",find_field);
		listM.addAttribute("find_name",find_name);
		
		return "QandA/QandA_list";
	}//QandA_list
	
	/* 게시판 내용보기 + 답변폼 + 수정폼 + 삭제폼 */
	@RequestMapping("/QandA/QandA_cont")
	public ModelAndView QandA_cont(
			int q_no,int page,
			String state,QandAVO q,
			HttpSession session,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		
		String user_id=(String)session.getAttribute("user_id");		
		
		PrintWriter out=response.getWriter();
		
		if(state.equals("cont")) {
			q=this.QandAService.getQandACont(q_no);
			//내용 보기 일 때만 조회수 증가
		}else {
			//답변,수정,삭제폼 => 조회수가 증가되지 않음
			q=this.QandAService.getBbsCont2(q_no);
		}
		
		String q_cont=q.getQ_cont();
		int q_ref=q.getQ_ref();
		String q_id=q.getQ_id();
		ModelAndView cm=new ModelAndView();
		cm.addObject("q", q);
		cm.addObject("q_cont",q_cont);
		cm.addObject("page", page);
		cm.addObject("q_ref", q_ref);
		cm.addObject("user_id", user_id);
		cm.addObject("q_id", q_id);
		
		if(user_id != null) {//로그인을 했을 때
			if(user_id.equals(q_id) || user_id.equals("admin")) {
				//로그인한 아이디와 글쓴 아이디가 같거나 아이디가 "admin"일때 실행
				if(state.equals("cont")) {//내용보기
					cm.setViewName("/QandA/QandA_cont");
					return cm;
				}else if(state.equals("reply")) {//답변
					cm.setViewName("/QandA/QandA_reply");
					return cm;
				}else if(state.equals("edit")) {//수정
					cm.setViewName("/QandA/QandA_edit");
					return cm;
				}//if else if
			}else {//로그인한 아이디와 글쓴 아이디와 아이디가 "admin"이 아닐때 실행
				out.println("<script>");
				out.println("alert('읽기 권한이 없습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
		}else {//로그인을 안했을때
			out.println("<script>");
            out.println("alert('로그인을 해주세요.');");
            out.println("location='/login';");
            out.println("</script>");
		}
		
		
		return null;
	}//QandA_cont
	
	/* 답변저장 */
	@RequestMapping("/QandA_reply_ok")
	public String QandA_reply_ok(
			@ModelAttribute QandAVO q,
			Model m,
			@RequestParam("page") int page,
			HttpSession session) throws Exception {
		
		String user_id=(String)session.getAttribute("user_id");
		
		m.addAttribute("user_id", user_id);
		
		this.QandAService.replyQandA(q);
		//답변저장+답변 레벨 증가 => 트랜잭션 적용 대상
		return "redirect:/QandA/QandA_list?page="+page;
		
	}//QandA_reply_ok
	
	/* 게시판 글 수정 */
	@RequestMapping("/QandA_edit_ok")
	public ModelAndView QandA_edit_ok(
			HttpServletRequest request,
			QandAVO q) {
		
		int page=Integer.parseInt(request.getParameter("page"));
		int q_no=Integer.parseInt(request.getParameter("q_no"));
		String q_title=request.getParameter("q_title");
		String q_cont=request.getParameter("q_cont");
		
		q.setQ_no(q_no); q.setQ_title(q_title); q.setQ_cont(q_cont);
		
		this.QandAService.editQandA(q);//게시판 수정
		
		ModelAndView em=new ModelAndView("redirect:/QandA/QandA_cont");
		em.addObject("q_no", q_no);
		em.addObject("page", page);
		em.addObject("state", "cont");
		
		return em;				
	}//QandA_edit_ok
	
	/* 게시판 삭제 */
	@RequestMapping("/QandA_del_ok")
	public String Qand_del_ok(QandAVO q, int page,
			HttpServletRequest request) throws Exception {
		
		this.QandAService.delQandA(q.getQ_no());
		
		return "redirect:/QandA/QandA_list?page="+page;
	}
	
}
