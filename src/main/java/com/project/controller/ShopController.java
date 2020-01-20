package com.project.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.project.service.ShopService;
import com.project.vo.ShopVO;

/** shop 상품 목록 & 관리자 컨트롤러 **/
@Controller
public class ShopController {
	
	@Autowired
	private ShopService shopService;
	
	/** shop 관리자 글쓰기 페이지 이동 **/
	@RequestMapping("shop/shop_write")
	public String shop_write(
			HttpServletRequest request,Model m,
			HttpServletResponse response,
			HttpSession session) throws Exception {
		
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		//session 처리!(관리자)
		
		String user_id = (String)session.getAttribute("user_id");
		//아이디를 받아옴
		if((user_id == null) || !(user_id.equals("admin"))) {
			out.println("<script>");
			out.println("alert('관리자 영역입니다. 관리자 계정으로 로그인해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {
			m.addAttribute("page",page);
			
			return "shop/shop_write";
		}//if else 
		
		return null;
	}//shop_write()
	
	/** 다중 파일 업로드 **/
	@RequestMapping("/multiplePhotoUpload")
	public void multiplePhotoUpload(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try {
//			String saveFolder=request.getSession().getServletContext().getRealPath("resources/photo_upload");
			
			Calendar c=Calendar.getInstance();
			int year=c.get(Calendar.YEAR);
			int month=c.get(Calendar.MONTH)+1;//+1을 한 이유는
			//1월이 0으로 반환되기 때문이다.
			int date=c.get(Calendar.DATE);//일 값
			

			String folderDate=year+"-"+month+"-"+date+"/"+"editor";
			//날짜부분만 따로 분리해서 객체에 저장
			
		 //파일정보
		 String sFileInfo = "";
		 
		 //파일명을 받는다 - 일반 원본파일명
		 String filename = request.getHeader("file-name");
		 
		 //파일 확장자
		 String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
		 
		 //확장자를소문자로 변경
		 filename_ext = filename_ext.toLowerCase();
		 
		 //파일 기본경로
		 String dftFilePath = request.getSession().getServletContext().getRealPath("/");
		 
		 //파일 기본경로 _ 상세경로
		 String filePath = dftFilePath + "resources" + File.separator + "photo_upload" + File.separator + folderDate + File.separator ;
		 
		 File file = new File(filePath);
		
		 if(!file.exists()) {
		    file.mkdirs();
		 }
		 
		 String realFileNm = "";
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		 String today= formatter.format(new java.util.Date());
		 realFileNm = today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
		 String rlFileNm = filePath + realFileNm;
		 
		 ///////////////// 서버에 파일쓰기 /////////////////
		 InputStream is = request.getInputStream();
		 OutputStream os=new FileOutputStream(rlFileNm);
		 int numRead;
		 byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
		 while((numRead = is.read(b,0,b.length)) != -1){
		    os.write(b,0,numRead);
		 }
		 if(is != null) {
		    is.close();
		 }
		 os.flush();
		 os.close();
		 
		 ///////////////// 서버에 파일쓰기 /////////////////
		 
		 // 정보 출력
		 sFileInfo += "&bNewLine=true";
		 
		 // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
		 sFileInfo += "&sFileName="+ filename;;
		 sFileInfo += "&sFileURL="+ "/resources/photo_upload/" + folderDate + "/"+ realFileNm;
		 //파일 쓰는 경로 수정
		     PrintWriter print = response.getWriter();
		     print.print(sFileInfo);
		     print.flush();
		     print.close();
	     
	} catch (Exception e) {
	    e.printStackTrace();
	    
	}//try catch

	}//multiplePhotoUpload()
	
	
	/** shop 상품 게시글 저장(본문 첨부 이미지 추가) **/
	@RequestMapping("shop_write_ok")
	public ModelAndView shop_write_ok(
			ShopVO s,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		session=request.getSession();
		//session 처리!(관리자)
		String user_id = (String)session.getAttribute("user_id");//관리자로 변경
		
		if((user_id == null) || !(user_id.equals("admin"))) {
			out.println("<script>");
			out.println("alert('관리자 영역입니다. 관리자 계정으로 로그인해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {
			String saveFolder=request.getSession().getServletContext().getRealPath("resources/photo_upload");
			/* 첨부파일 업로드 경로, 실제 톰캣 프로젝트 경로를 반환 =>
			 * c:\spring_work\.metadata\.plugins\org.eclipse.wst.
			 * server.core\tmp1\wtbwebapps\project\photo_upload
			 */
			
			int fileSize=5*1024*1024;//첨부파일 최대크기(5MB)
			MultipartRequest multi=null;//첨부파일을 가져오는 api
			multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
			
			String item_name=multi.getParameter("item_name");
			String item_sub=multi.getParameter("item_sub");
			int item_price=Integer.parseInt(multi.getParameter("item_price"));
			int item_stockCount=Integer.parseInt(multi.getParameter("item_stockCount"));
			String item_cont=multi.getParameter("item_cont");
			
			
			File UpFile=multi.getFile("item_img");
			//첨부한 파일을 가져옴
			
			if(UpFile != null) {//첨부한 파일이 있는 경우
				String fileName=UpFile.getName();//첨부한 파일명
				Calendar c=Calendar.getInstance();
				int year=c.get(Calendar.YEAR);
				int month=c.get(Calendar.MONTH)+1;//+1을 한 이유는
				//1월이 0으로 반환되기 때문이다.
				int date=c.get(Calendar.DATE);//일 값
				
				String datedir=saveFolder+"/"+year+"-"+month+"-"+date;
				//오늘 날짜 폴더 경로
				String libdir=saveFolder+"/"+year+"-"+month+"-"+date+"/"+"lib";
				//오늘 날짜 폴더 경로 + 하위의 lib폴더를 저장
				File path1=new File(datedir);
				File path2=new File(libdir);
				
				if(!(path1.exists())) {
					path1.mkdir();//오늘 날짜 폴더 경로를 확인하고 저장
				}
				if(!(path2.exists())) {
					path2.mkdir();//오늘 날짜 폴더 경로 + 하위의 lib폴더를 확인하고 저장
				}
				
				
				Random r=new Random();
				int random=r.nextInt(100000000);//0이상 1억미만 사이의
				//정수 숫자 난수 발생
				
				/** 첨부한 파일 확장자를 구함 **/
				int index=fileName.lastIndexOf(".");
				/* 첨부한 파일에서 .를 맨 오른쪽부터 찾아서 가장 먼저 나오는 .의 위치번호를
				 * 왼쪽부터 세어서 번호값을 반환. 첫문자는 0부터 센다.
				 */
				String fileExtendsion=fileName.substring(index+1);
				//마침표 이후부터 마지막 문자까지 구함. 즉 확장자를 구함.
				String refilename="shop"+year+month+date+random+"."+
						fileExtendsion;//새로운 첨부파일명을 저장
				String fileDBName="/"+year+"-"+month+"-"+date+"/"+
						"lib"+"/"+refilename;//DB에 저장될 레코드 값
				UpFile.renameTo(new File(libdir+"/"+refilename));
				//바뀌어진 첨부파일명으로 업로드
				s.setItem_img(fileDBName);
				
				}else {
					/* mybatis는 컬럼에 null을 저장하지 못함. 그러므로 파일을
					 * null저장을 막기 위해서 else 로 처리해야 한다.
					 */
					s.setItem_img("");//빈 공백을 넣어서, null이 들어가
					//에러가 나는 것을 막아준다.
		 		}//if else
				
				s.setItem_name(item_name); s.setItem_sub(item_sub);
				s.setItem_price(item_price); s.setItem_stockCount(item_stockCount);
				s.setItem_cont(item_cont);
				
				this.shopService.insertShop(s);
			
				return new ModelAndView("redirect:shop/total_shop?find_field=item_name&find_name=");
			
		}//if else
		
		return null;
	}//shop_write_ok()
	
	
	/** shop 상품 리스트 **/
	@RequestMapping("shop/total_shop")
	public String shop_list(
			Model shopList,ShopVO s,
			HttpServletRequest request,
			HttpSession session) throws Exception {
		
		session=request.getSession();
		
		//session 처리!(관리자)
		String user_id = (String)session.getAttribute("user_id");//관리자로 변경
		
		int checkCount = 0;
		
		if(user_id == null) {
			checkCount = 0;
		}else if(user_id.equals("admin")) {
			checkCount = 1;//관리자임을 확인하는 번호
		}
				
		int page=1;
		int limit=6;//한페이지에 보여지는 상품 개수
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String find_name = request.getParameter("find_name");
		String find_field = request.getParameter("find_field");
		//검색어, 검색필드 를 저장
		
		s.setFind_field(find_field);
		s.setFind_name("%"+find_name+"%");
			
		int totalCount=this.shopService.getListCount(s);
		//검색전후 레코드 개수
		
		s.setStartrow((page-1)*limit+1);//시작행번호
		s.setEndrow(s.getStartrow()+limit-1);//끝행 번호
		
		List<ShopVO> slist=this.shopService.getShopList(s);
		//검색 전후 목록
		
		//총 페이지 수
		int maxpage=(int)((double)totalCount/limit+0.95);
		
		//시작페이지
		int startpage=(((int)((double)page/10+0.9))-1)*6+1;
		
		//마지막 페이지
		int endpage=maxpage;
		
		if(endpage>startpage+6-1)
			endpage=startpage+6-1;
		
		shopList.addAttribute("slist",slist);
		shopList.addAttribute("page",page);
		shopList.addAttribute("startpage",startpage);
		shopList.addAttribute("endpage",endpage);
		shopList.addAttribute("maxpage",maxpage);
		shopList.addAttribute("totalcount",totalCount);
		shopList.addAttribute("find_field",find_field);
		shopList.addAttribute("find_name",find_name);
		shopList.addAttribute("checkCount",checkCount);
		
		return "shop/shop_list";
	}//shop_list()
	
	/** 상품 내용 보기 + 수정  **/
	@RequestMapping("shop/shop_cont")
	public ModelAndView shop_cont(
			int item_no,int page,
			String state,ShopVO s,
			HttpServletRequest request,
			HttpSession session) throws Exception {
		
		session=request.getSession();
		
		//session 처리!(관리자)
		String user_id = (String)session.getAttribute("user_id");//관리자로 변경
		
		int checkCount = 0;
		
		if(user_id == null) {
			checkCount = 0;
		}else if(user_id.equals("admin")) {
			checkCount = 1;//관리자임을 확인하는 번호
		}else {
			checkCount = 2;//그 외의 아이디 일때
		}
		
		s=this.shopService.getShopCont(item_no);
		
		ModelAndView model=new ModelAndView();
		model.addObject("s",s);
		model.addObject("page",page);
		model.addObject("checkCount",checkCount);
		
		if(state.equals("cont")) {//내용보기
			model.setViewName("shop/shop_cont");
	
		}else if(state.equals("edit")) {//수정
			model.setViewName("shop/shop_edit");
		
		}//if else if
		
		return model;
	}//shop_cont()
	
	/** shop 관리자 게시글 수정 **/
	@RequestMapping("/shop_edit_ok")
	public ModelAndView shop_edit_ok(
			ShopVO s,
			HttpServletResponse response,
			HttpServletRequest request,
			HttpSession session) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		session=request.getSession();
		//session 처리!
		String user_id = (String)session.getAttribute("user_id");//관리자로 변경
		
		if((user_id == null) || !(user_id.equals("admin"))) {
			out.println("<script>");
			out.println("alert('관리자 영역입니다. 관리자 계정으로 로그인해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {
			String saveFolder=request.getSession().getServletContext().getRealPath("resources/photo_upload");
			/* 첨부파일 업로드 경로, 실제 톰캣 프로젝트 경로를 반환 =>
			 * c:\spring_work\.metadata\.plugins\org.eclipse.wst.
			 * server.core\tmp1\wtbwebapps\project\photo_upload
			 */
			
			int fileSize=5*1024*1024;//첨부파일 최대크기(5MB)
			MultipartRequest multi=null;//첨부파일을 가져오는 api
			multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
			int page=1;
			if(multi.getParameter("page") != null) {
				page=
					Integer.parseInt(multi.getParameter("page"));
			}
			
			int item_no=Integer.parseInt(multi.getParameter("item_no"));
			String item_name=multi.getParameter("item_name");
			String item_sub=multi.getParameter("item_sub");
			int item_price=Integer.parseInt(multi.getParameter("item_price"));
			int item_stockCount=Integer.parseInt(multi.getParameter("item_stockCount"));
			String item_cont=multi.getParameter("item_cont");
			
			ShopVO shopImg=this.shopService.getShopCont(item_no);
			
			File UpFile=multi.getFile("item_img");
			//첨부한 파일을 가져옴
			
			if(UpFile != null) {//첨부한 파일이 있는 경우
				String fileName=UpFile.getName();//첨부한 파일명
				File DelFile=new File(saveFolder+shopImg.getItem_img());
				
				if(DelFile.exists()) {
					DelFile.delete();//기존파일 삭제
				}
				
				Calendar c=Calendar.getInstance();
				int year=c.get(Calendar.YEAR);
				int month=c.get(Calendar.MONTH)+1;//+1을 한 이유는
				//1월이 0으로 반환되기 때문이다.
				int date=c.get(Calendar.DATE);//일 값
				
				String datedir=saveFolder+"/"+year+"-"+month+"-"+date;
				//오늘 날짜 폴더 경로
				String libdir=saveFolder+"/"+year+"-"+month+"-"+date+"/"+"lib";
				//오늘 날짜 폴더 경로 + 하위의 lib폴더를 저장
				File path1=new File(datedir);
				File path2=new File(libdir);
				
				if(!(path1.exists())) {
					path1.mkdir();//오늘 날짜 폴더 경로를 확인하고 저장
				}
				if(!(path2.exists())) {
					path2.mkdir();//오늘 날짜 폴더 경로 + 하위의 lib폴더를 확인하고 저장
				}
				
				
				Random r=new Random();
				int random=r.nextInt(100000000);//0이상 1억미만 사이의
				//정수 숫자 난수 발생
				
				/** 첨부한 파일 확장자를 구함 **/
				int index=fileName.lastIndexOf(".");
				/* 첨부한 파일에서 .를 맨 오른쪽부터 찾아서 가장 먼저 나오는 .의 위치번호를
				 * 왼쪽부터 세어서 번호값을 반환. 첫문자는 0부터 센다.
				 */
				String fileExtendsion=fileName.substring(index+1);
				//마침표 이후부터 마지막 문자까지 구함. 즉 확장자를 구함.
				String refilename="shop"+year+month+date+random+"."+
						fileExtendsion;//새로운 첨부파일명을 저장
				String fileDBName="/"+year+"-"+month+"-"+date+"/"+
						"lib"+"/"+refilename;//DB에 저장될 레코드 값
				UpFile.renameTo(new File(libdir+"/"+refilename));
				//바뀌어진 첨부파일명으로 업로드
				s.setItem_img(fileDBName);
				
			}else {
				/* mybatis는 컬럼에 null을 저장하지 못함. 그러므로 파일을
				 * null저장을 막기 위해서 else 로 처리해야 한다.
				 */
				if(shopImg.getItem_img() != null) {//기존파일이 있는 경우
					s.setItem_img(shopImg.getItem_img());
				}else {//기존 파일이 없는 경우
					s.setItem_img("");
				}
	 		}//if else
			
			s.setItem_name(item_name); s.setItem_sub(item_sub);
			s.setItem_price(item_price); s.setItem_stockCount(item_stockCount);
			s.setItem_cont(item_cont); s.setItem_no(item_no);
			
			this.shopService.editShop(s);//수정 메서드
			
			ModelAndView model=new ModelAndView(
					"redirect:shop/shop_cont");
			model.addObject("state","cont");
			model.addObject("item_no",item_no);
			model.addObject("page",page);
			
			return model;
			
		}//if else
		
		return null;
	}//shop_edit_ok()
	
	/** shop 상품 삭제 **/
	@RequestMapping("/shop_del")
	public String shop_del_ok(
			ShopVO s,int page,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		session=request.getSession();
		//session 처리!
		String user_id = (String)session.getAttribute("user_id");//관리자로 변경
		
		if((user_id == null) || !(user_id.equals("admin"))) {
			out.println("<script>");
			out.println("alert('관리자 영역입니다. 관리자 계정으로 로그인해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {
			String folder=request.getSession().getServletContext().getRealPath("resources/photo_upload");
			//이진파일 업로드 서버 경로		

			if(s.getItem_img() != null) {
				//첨부파일이 있는 경우
				File file=new File(folder+s.getItem_img());
				//삭제할 파일 객체 생성
				file.delete();
				
			}//if
			
			this.shopService.delShop(s.getItem_no());//DB로 부터 게시물 삭제
			
			return "redirect:shop/total_shop?page="+page+"&find_field=item_name&find_name=";
			
		}//if else
		
		return null;
	}//shop_del_ok()
	
}//ShopController


