package com.project.controller;

import com.project.service.NormalService;
import com.project.vo.NormalBoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class NormalController {

    @Autowired
    private NormalService normalService;


    @RequestMapping("normal_board_list")
    public String normal_board_list(HttpSession session,Model m, NormalBoardVO nbv, HttpServletResponse response, HttpServletRequest request)throws Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ////////////////// 추가
        session.setAttribute("c_path",request.getServletPath());
        //////////////////

        int page = 1;
        int limit = 15;//한페이지 보여지는 목록개수
        if (request.getParameter("page") != null) {
            //get으로 전달된 쪽번호가 있는경우 실행
            page = Integer.parseInt(request.getParameter("page"));
            //전달된 쪽번호를 정수 숫자로 치환
        }
        String title = request.getParameter("title");
        //검색어
        if (request.getParameter("title")==null){
            title="";
        }
        String field = request.getParameter("field");
        //검색필드
        if (request.getParameter("field")==null){
            field="";
        }

        nbv.setTitle(title);
        nbv.setField("%" + field + "%");//%는 오라클에서 하나 이상의 임의의 모르는 문자와 매핑대응

        int listcount = this.normalService.getListCount(nbv);//검색전후 레코드 개수
        nbv.setStartrow((page - 1) * 15 + 1);//시작행번호
        nbv.setEndrow(nbv.getStartrow() + limit - 1);//끝행번호

        List<NormalBoardVO> nlist = this.normalService.getUserBoardList(nbv);
        //관리자 게시판 검색전후 목록


        //총페이지
        int maxpage = (int) ((double) listcount / limit + 0.95);

        //시작 페이지
        int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;

        //마지막 페이지
        int endpage = maxpage;
        if (endpage > startpage + 10 - 1)
            endpage = startpage + 10 - 1;

        m.addAttribute("nlist", nlist);//blist키이름에 컬렉션 제네릭 blist저장
        m.addAttribute("page", page);
        m.addAttribute("startpage", startpage);
        m.addAttribute("endpage", endpage);
        m.addAttribute("maxpage", maxpage);
        m.addAttribute("listcount", listcount);
        m.addAttribute("field", field);
        m.addAttribute("title", title);

        return "normal_board/normal_board";
    }

    //글쓰기
    @RequestMapping("normal_list_write")
    public String normal_list_cont(HttpSession session,HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        String user_id=(String)session.getAttribute("user_id");
        PrintWriter out=response.getWriter();
        if(user_id==null){
            out.println("<script>");
            out.println("alert('로그인 후 이용해주세요');");
            out.println("location='normal_board_list';");
            out.println("</script>");
        }else {
            return "normal_board/normal_board_write";
        }
        return null;
    }

    //게시글저장
    @RequestMapping("normal_list_write_ok")
    public String normal_list_write_ok(NormalBoardVO nbv, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();

        int re=-1;
        re=this.normalService.insertBoard(nbv);

        if(re==1){
            out.println("<script>");
            out.println("alert('저장 완료');");
            out.println("location='normal_board_list';");
            out.println("</script>");
        }else{
            out.println("<script>");
            out.println("alert('저장 실패 목록으로 되돌아갑니다');");
            out.println("location='normal_board_list';");
            out.println("</script>");
        }

        return null;
    }

    //게시글 삭제
    @RequestMapping("normal_list_write_del_ok")
    public String normal_list_write_del_ok(HttpServletResponse response,NormalBoardVO nbv,HttpServletRequest request) throws IOException {

        PrintWriter out=response.getWriter();

        nbv.setNormal_ref(Integer.parseInt(request.getParameter("normal_ref")));

        int re=-1;

        re=this.normalService.del_board(nbv);

        if(re==1) {
            out.println("<script>");
            out.println("alert('게시글을 삭제 완료!');");
            out.println("</script>");
        }else {
            out.println("<script>");
            out.println("alert('게시글을 삭제 실패! 목록으로 돌아갑니다!');");
            out.println("</script>");
        }
        return "redirect:/normal_board_list";
    }

    //내용
    @RequestMapping("normal_board_list_cont")
    public String board_list_cont(Model m, NormalBoardVO nbv, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();

        String user_id=(String)session.getAttribute("user_id");

        int page=1;

        if(request.getParameter("page")!=null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        if(request.getParameter("normal_no")!=null){
            nbv.setNormal_no(Integer.parseInt(request.getParameter("normal_no")));
        }




        this.normalService.update_hit(nbv);
        NormalBoardVO n=this.normalService.selectnormalboardcont(nbv);
        List<NormalBoardVO> nlist=this.normalService.getreplylist(nbv);

            m.addAttribute("page",page);
            m.addAttribute("nlist",nlist);
            m.addAttribute("user_id", user_id);
            m.addAttribute("n", n);

            return "normal_board/normal_board_cont";

    }

    //댓글저장
    @RequestMapping("normal_board_list_cont_reply_ok")
    public String normal_board_list_cont_reply_ok(String id,String cont,int no,NormalBoardVO nbv,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        String user_id=(String)session.getAttribute("uesr_id");

        nbv.setNormal_id(id);
        nbv.setNormal_cont(cont);
        nbv.setNormal_no(no);

        int re=-1;
        re=this.normalService.normal_reply_ok(nbv);

        if(re!=1){
            re=-1;
        }

        out.println(re);

        return null;
    }

    //댓글삭제
    @RequestMapping("reply_del_ok")
    public String reply_del_ok(NormalBoardVO nbv, HttpServletRequest request, ServletResponse response, RedirectAttributes redirect)throws IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();


        nbv.setNormal_id(request.getParameter("normal_id"));
        nbv.setNormal_no(Integer.parseInt(request.getParameter("normal_no")));



        int re=-1;

        re=this.normalService.reply_del(nbv);

        if(re==1){
            re=1;

        }

        int no=Integer.parseInt(request.getParameter("no"));

        redirect.addAttribute("normal_no",no);
        return "redirect:/normal_board_list_cont";
    }

    //게시글 수정
    @RequestMapping("board_cont_update")
    public String board_cont_update(HttpSession session,NormalBoardVO nbv,Model m,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String user_id=(String)session.getAttribute("user_id");
        PrintWriter out = response.getWriter();

        if(user_id!=null){
            NormalBoardVO n=this.normalService.selectnormalboardcont(nbv);
            m.addAttribute("n",n);
            return "normal_board/normal_update";

        }else{
            out.println("<script>");
            out.println("alert('세션만료 다시로그인 해주세요');");
            out.println("location='normal_board_list';");
            out.println("</script>");
        }

        return null;



    }

    //게시글수정완료
    @RequestMapping("board_cont_update_ok")
    public String board_cont_update(NormalBoardVO nbv,HttpServletRequest request,RedirectAttributes redirect,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out=response.getWriter();

        this.normalService.update_board(nbv);


        return "redirect:/normal_board_list_cont?normal_no="+nbv.getNormal_no();
    }

}
