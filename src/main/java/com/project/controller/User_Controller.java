package com.project.controller;

import com.oreilly.servlet.MultipartRequest;
import com.project.codesend.CodeSend;
import com.project.gmail.Gmail;
import com.project.messagesend.CodeMessage;
import com.project.service.UserService;
import com.project.vo.BoardVO;
import com.project.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


@Controller
public class User_Controller {

    String code;

    @Autowired
    private UserService userService;

    //	임시시로 만들어둔 매핑주소

    //게시판
//    @RequestMapping("board_list")
//    public String board_list(){
//        return "board/board_list";
//    }

    //로그인
    @RequestMapping("login")
    public String login() {
        return "login/login";
    }

    //아이디찾기
    @RequestMapping("find_id")
    public String find_id() {
        return "find_id/find_id";
    }

    //비번찾기
    @RequestMapping("find_pwd")
    public String find_pwd() {
        return "find_pwd/find_pwd";
    }

    //로그아웃
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //게시판 목록
    @RequestMapping("user_board_list")
    public String back_end_list(Model listM, BoardVO b, HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();



        int page = 1;
        int limit = 7;//한페이지 보여지는 목록개수
        if (request.getParameter("page") != null) {
            //get으로 전달된 쪽번호가 있는경우 실행
            page = Integer.parseInt(request.getParameter("page"));
            //전달된 쪽번호를 정수 숫자로 치환
        }
        String back_end_field = request.getParameter("back_end_field");
        //검색어
        if(request.getParameter("back_end_field")==null){
            back_end_field="";
        }
        String back_end_title = request.getParameter("back_end_title");
        //검색종류
        if(request.getParameter("back_end_title")==null){
            back_end_title="";
        }

        b.setBack_end_title(back_end_title);
        b.setBack_end_field("%" + back_end_field + "%");//%는 오라클에서 하나 이상의 임의의 모르는 문자와 매핑대응


        int listcount = this.userService.getListCount(b);//검색전후 레코드 개수
        b.setStartrow((page - 1) * 7 + 1);//시작행번호
        b.setEndrow(b.getStartrow() + limit - 1);//끝행번호

        List<BoardVO> blist = this.userService.getUserBoardList(b);
        //관리자 게시판 검색전후 목록


        //총페이지
        int maxpage = (int) ((double) listcount / limit + 0.95);

        //시작 페이지
        int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;

        //마지막 페이지
        int endpage = maxpage;
        if (endpage > startpage + 10 - 1)
            endpage = startpage + 10 - 1;


        listM.addAttribute("blist", blist);//blist키이름에 컬렉션 제네릭 blist저장
        listM.addAttribute("page", page);
        listM.addAttribute("startpage", startpage);
        listM.addAttribute("endpage", endpage);
        listM.addAttribute("maxpage", maxpage);
        listM.addAttribute("listcount", listcount);
        listM.addAttribute("back_end_field", back_end_field);
        listM.addAttribute("back_end_title", back_end_title);

        return "board/board_list";

    }

    //로그인 확인
    @RequestMapping("login_ok")
    public String login_ok(MemberVO m, HttpSession session, HttpServletResponse response,HttpServletRequest request) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        MemberVO mem = this.userService.select_id_pwd(m);

        if (mem == null) {
            out.println("<script>");
            out.println("alert('해당 정보를 찾지못하였습니다')");
            out.println("location='login';");
            out.println("</script>");
        } else {
        	String user_id = mem.getUser_id();
            session.setAttribute("user_id", user_id);
            return "redirect:/cat/total_cat?page=1";
        }
        return null;
    }

    //전화번호 및 코드 발송
    @RequestMapping("codesend")
    public String codesend(String user_phone, HttpServletResponse response, Model m) throws Exception {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        MemberVO phonecode = this.userService.phone_check(user_phone);
        String codenum = "";
        if (phonecode != null) {
            codenum = new CodeSend().cd1();
            CodeMessage.sms_send(user_phone,codenum);

            out.print(codenum);
        }else{
            codenum = new CodeSend().cd1();
            CodeMessage.sms_send(user_phone,codenum);
            out.println(codenum);
        }
        return null;
    }


    //아이디 찾기
    @RequestMapping("find_id_check")
    public String find_id_check(String user_name, String phone, MemberVO m, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        m.setUser_name(user_name);
        m.setUser_phone(phone);
        MemberVO uv = this.userService.select_name_phone(m);


        if (uv != null) {
            Gmail.gmailSendID(uv.getUser_name(), uv.getUser_email(), uv.getUser_id());
            out.println("<script>");
            out.println("alert('이메일 발송 완료!');");
            out.println("location='login';");
            out.println("</script>");

        } else {
            out.println("<script>");
            out.println("alert('정보가 없습니다 다시입력해주세요!')");
            out.println("history.back();");
            out.println("</script>");
        }
        return null;
    }

    //비밀번호찾기
    @RequestMapping("find_pwd_check")
    public String find_pwd_check(String user_id, String user_name, String phone, MemberVO m, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        m.setUser_id(user_id);
        m.setUser_name(user_name);
        m.setUser_phone(phone);
        MemberVO uv = this.userService.select_id_name_phone(m);
        int re = -1;
        if (uv != null) {
            uv.setUser_pwd(new CodeSend().cd1());
            re = this.userService.pwdUPdate(uv);

            if (re == 1) {
                Gmail.gmailSendPWD(uv.getUser_name(), uv.getUser_email(), uv.getUser_pwd());
                out.println("<script>");
                out.println("alert('이메일 발송 완료!');");
                out.println("location='login';");
                out.println("</script>");
            }

        } else {
            out.println("<script>");
            out.println("alert('정보가 없습니다 다시입력해주세요!')");
            out.println("history.back();");
            out.println("</script>");
        }
        return null;
    }


    //내용보기
    @RequestMapping("cont")
    public String cont(BoardVO b,int no,int page, HttpServletResponse response,HttpSession session,Model m) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String member_id= (String) session.getAttribute("user_id");

//        b.setBack_end_list_id(id);
//        b.setBack_end_list_title(title);
        b.setBack_end_list_no(no);

        BoardVO bv = this.userService.select_board(b);
        List<BoardVO> blist=this.userService.get_select_reply(b);
        if (bv == null) {
            out.println("<script>");
            out.println("alert('해당 자료는 삭제되거나 없는 자료입니다');");
            out.println("history.back();");
            out.println("</script>");
        } else {
            m.addAttribute("blist",blist);
            m.addAttribute("title", bv.getBack_end_list_title());
            m.addAttribute("id", bv.getBack_end_list_id());
            m.addAttribute("date", bv.getBack_end_list_date());
            m.addAttribute("cont", bv.getBack_end_list_cont());
            m.addAttribute("no",bv.getBack_end_list_no());
            m.addAttribute("member_id",member_id);
            m.addAttribute("page",page);
            return "board/board_cont";
        }
        return  null;
    }

    //글쓰기폼
    @RequestMapping("board_list_write")
    public String board_list_write(String id, BoardVO b, Model m, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String user_id = (String) session.getAttribute("user_id");

        if (user_id == null) {
            out.println("<script>");
            out.println("alert('세션만료 다시로그인하세요!');");
            out.println("location='login';");
            out.println("</script>");
        } else {
            m.addAttribute("user_id", user_id);
            return "board/board_write";
        }

        return null;
    }


    //글쓰기
    @RequestMapping("board_list_write_ok")
    public String board_list_write_ok(BoardVO b, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //날짜별 폴더 생성
        String saveFolder = request.getRealPath("resources/photo_upload");

        int fileSize = 5 * 1024 * 1024;//첨부파일 최대크기(5MB)
        MultipartRequest multi = null;//첨부파일을 가져오는 api
        multi = new MultipartRequest(request, saveFolder, fileSize, "UTF-8");

        String user_id=(String)session.getAttribute("user_id");
        String back_end_list_title = multi.getParameter("back_end_list_title");
        String back_end_list_cont = multi.getParameter("back_end_list_cont");

        //고양이 종류, 내용을 가져옴


        File UpFile = multi.getFile("back_end_list_img");
        //첨부한 파일을 가져옴

        if (UpFile != null) {//첨부한 파일이 있는 경우
            String fileName = UpFile.getName();//첨부한 파일명
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;//+1을 한 이유는
            //1월이 0으로 반환되기 때문이다.
            int date = cal.get(Calendar.DATE);//일 값

            String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
            //오늘 날짜 폴더 경로를 저장
            File path1 = new File(homedir);
            if (!(path1.exists())) {
                path1.mkdir();//오늘 날짜 폴더 생성
            }//if

            Random r = new Random();
            int random = r.nextInt(100000000);//0이상 1억미만 사이의
            //정수 숫자 난수 발생

            /** 첨부한 파일 확장자를 구함 **/
            int index = fileName.lastIndexOf(".");
            //첨부한 파일에서 .를 맨 오른쪽부터 찾아서 가장 먼저 나오는 .의 위치번호를
            //왼쪽부터 세어서 번호값을 반환. 첫문자는 0부터 센다.

            String fileExtendsion = fileName.substring(index + 1);
            //마침표 이후부터 마지막 문자까지 구함. 즉 확장자를 구함.
            String refilename = "cat" + year + month + date + random + "." +
                    fileExtendsion;//새로운 첨부파일명을 저장
            String fileDBName = "/" + year + "-" + month + "-" + date + "/" +
                    refilename;//DB에 저장될 레코드 값
            UpFile.renameTo(new File(homedir + "/" + refilename));
            //바뀌어진 첨부파일명으로 업로드

            b.setBack_end_list_img(fileDBName);

        } else {
            //mybatis는 컬럼에 null을 저장하지 못함. 그러므로 파일을
            //null저장을 막기 위해서 else 로 처리해야 한다.
            b.setBack_end_list_img("/default/default.png");//빈 공백을 넣어서, null이 들어가
            //에러가 나는 것을 막아준다.
        }//if else

        b.setBack_end_list_id(user_id);
        b.setBack_end_list_title(back_end_list_title);
        b.setBack_end_list_cont(back_end_list_cont);


        this.userService.board_insert(b);//글쓰기 저장

        return "redirect:/user_board_list";
    }


    //댓글 저장
    @RequestMapping("reply_ok")
    public String reply_ok(String id, String cont, int no, BoardVO b, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();


        b.setBack_end_list_id(id);
        b.setBack_end_list_cont(cont);
        b.setBack_end_list_no(no);

        int re=this.userService.insert_reply(b);

        if(re!=1){
            re=-1;
        }

        out.println(re);


        return null;
    }

    //게시글 삭제
    @RequestMapping("user_board_list_del")
    public String user_board_list_del(HttpServletResponse response,BoardVO b){
        response.setContentType("text/html;charset=UTF-8");

        this.userService.del_user_board(b);

        return "redirect:/user_board_list";
    }

    //게시글수정페이지
    @RequestMapping("user_board_update")
    public String user_board_update(Model m,BoardVO b,HttpServletResponse response,HttpSession session){
        response.setContentType("text/html;charset=UTF-8");

        BoardVO bv=this.userService.select_board(b);

        m.addAttribute("bv",bv);

        return "board/board_update";
    }

    //게시글 수정 완료
    @RequestMapping("user_board_update_ok")
    public String user_board_update_ok(BoardVO b, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //날짜별 폴더 생성
        String saveFolder = request.getRealPath("resources/photo_upload");

        int fileSize = 5 * 1024 * 1024;//첨부파일 최대크기(5MB)
        MultipartRequest multi = null;//첨부파일을 가져오는 api
        multi = new MultipartRequest(request, saveFolder, fileSize, "UTF-8");

        String user_id=(String)session.getAttribute("user_id");
        String back_end_list_title = multi.getParameter("back_end_list_title");
        String back_end_list_cont = multi.getParameter("back_end_list_cont");
        int back_end_list_no = Integer.parseInt(multi.getParameter("back_end_list_no"));

        //고양이 종류, 내용을 가져옴

        File UpFile = multi.getFile("back_end_list_img");
        //첨부한 파일을 가져옴

        if (UpFile != null) {//첨부한 파일이 있는 경우
            String fileName = UpFile.getName();//첨부한 파일명
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;//+1을 한 이유는
            //1월이 0으로 반환되기 때문이다.
            int date = cal.get(Calendar.DATE);//일 값

            String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
            //오늘 날짜 폴더 경로를 저장
            File path1 = new File(homedir);
            if (!(path1.exists())) {
                path1.mkdir();//오늘 날짜 폴더 생성
            }//if

            Random r = new Random();
            int random = r.nextInt(100000000);//0이상 1억미만 사이의
            //정수 숫자 난수 발생

            /** 첨부한 파일 확장자를 구함 **/
            int index = fileName.lastIndexOf(".");
            //첨부한 파일에서 .를 맨 오른쪽부터 찾아서 가장 먼저 나오는 .의 위치번호를
            //왼쪽부터 세어서 번호값을 반환. 첫문자는 0부터 센다.

            String fileExtendsion = fileName.substring(index + 1);
            //마침표 이후부터 마지막 문자까지 구함. 즉 확장자를 구함.
            String refilename = "cat" + year + month + date + random + "." +
                    fileExtendsion;//새로운 첨부파일명을 저장
            String fileDBName = "/" + year + "-" + month + "-" + date + "/" +
                    refilename;//DB에 저장될 레코드 값
            UpFile.renameTo(new File(homedir + "/" + refilename));
            //바뀌어진 첨부파일명으로 업로드

            b.setBack_end_list_img(fileDBName);

        } else {
            //mybatis는 컬럼에 null을 저장하지 못함. 그러므로 파일을
            //null저장을 막기 위해서 else 로 처리해야 한다.
            b.setBack_end_list_img("/default/default.png");//빈 공백을 넣어서, null이 들어가
            //에러가 나는 것을 막아준다.
        }//if else

        b.setBack_end_list_id(user_id);
        b.setBack_end_list_title(back_end_list_title);
        b.setBack_end_list_cont(back_end_list_cont);
        b.setBack_end_list_no(back_end_list_no);



        this.userService.update_board(b);//글쓰기 수정

        return "redirect:/user_board_list";
    }


    //유저 댓글 수정
    @RequestMapping("reply_update_ok")
    public String reply_update_ok(BoardVO b,int no,int page,HttpSession session,HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");

        this.userService.user_reply_update(b);


        return "redirect:/cont?no="+no+"&page="+page;
    }

    @RequestMapping("user_reply_del_ok")
    public String user_reply_del_ok(BoardVO b,int no,int page) {
        this.userService.user_reply_del(b);


        return "redirect:/cont?no="+no+"&page="+page;
    }
}


