package com.project.controller;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.project.service.CatService;
import com.project.vo.CatVO;

@Controller
public class CatBoardController {

	@Autowired
	private CatService catService;

	/** 고양이 글쓰기 **/
	@RequestMapping("/cat/cat_write")
	public String cat_write() {		

		return "cat/cat_write";
	}//cat_write();

	/** 자료실 저장 **/
	@RequestMapping("/cat_write_ok")
	public String cat_write_ok(
			CatVO c,
			HttpServletRequest request) throws Exception{
		
		//날짜별 폴더 생성
		String saveFolder=request.getSession().getServletContext().getRealPath("resources/photo_upload");
		/* 첨부파일 업로드 경로, 실제 톰캣 프로젝트 경로를 반환 =>
		 * c:\spring_work\.metadata\.plugins\org.eclipse.wst.
		 * server.core\tmp1\wtbwebapps\project\photo_upload
		 */
		
		int fileSize=5*1024*1024;//첨부파일 최대크기(5MB)
		MultipartRequest multi=null;//첨부파일을 가져오는 api
		multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
		
		String cat_title=multi.getParameter("cat_title");
		String cat_cont=multi.getParameter("cat_cont");
		//고양이 종류, 내용을 가져옴
		File UpFile=multi.getFile("cat_file");
		//첨부한 파일을 가져옴
		
		if(UpFile != null) {//첨부한 파일이 있는 경우
			String fileName=UpFile.getName();//첨부한 파일명
			Calendar cal=Calendar.getInstance();
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;//+1을 한 이유는
			//1월이 0으로 반환되기 때문이다.
			int date=cal.get(Calendar.DATE);//일 값
			
			String datedir=saveFolder+"/"+year+"-"+month+"-"+date;
			String catdir=saveFolder+"/"+year+"-"+month+"-"+date+"/"+"cat";
			//오늘 날짜 폴더 경로를 저장
			File path1=new File(datedir);
			File path2=new File(catdir);
			
			if(!(path1.exists())) {
				path1.mkdir();//오늘 날짜 폴더 경로를 확인하고 저장
			}
			if(!(path2.exists())) {
				path2.mkdir();//오늘 날짜 폴더 경로 + 하위의 cat폴더를 확인하고 저장
			}
			
			Random r=new Random();
			int random=r.nextInt(100000000);//0이상 1억미만 사이의
			//정수 숫자 난수 발생
			
			/** 첨부한 파일 확장자를 구함 **/
			int index=fileName.lastIndexOf(".");
			 //첨부한 파일에서 .를 맨 오른쪽부터 찾아서 가장 먼저 나오는 .의 위치번호를
			 //왼쪽부터 세어서 번호값을 반환. 첫문자는 0부터 센다.
			 
			String fileExtendsion=fileName.substring(index+1);
			//마침표 이후부터 마지막 문자까지 구함. 즉 확장자를 구함.
			String refilename="cat"+year+month+date+random+"."+
					fileExtendsion;//새로운 첨부파일명을 저장
			String fileDBName="/"+year+"-"+month+"-"+date+"/"+
					"cat"+"/"+refilename;//DB에 저장될 레코드 값
			
			UpFile.renameTo(new File(catdir+"/"+refilename));
			//바뀌어진 첨부파일명으로 업로드
			c.setCat_file(fileDBName);
			
		}else {
			 //mybatis는 컬럼에 null을 저장하지 못함. 그러므로 파일을
			 //null저장을 막기 위해서 else 로 처리해야 한다.
			 
			c.setCat_file("");//빈 공백을 넣어서, null이 들어가
			//에러가 나는 것을 막아준다.
		}//if else
		
		c.setCat_title(cat_title); c.setCat_cont(cat_cont);
		
		this.catService.insertCat(c);//글쓰기 저장
 
		return "redirect:/cat/total_cat?page=1";
	}//cat_write_ok

	/** 고양이 목록 **/
	@RequestMapping("/cat/total_cat")
	public String total_cat(
			Model listM,CatVO c,
			HttpServletRequest request,HttpSession session) {
		
		String user_id=(String)session.getAttribute("user_id");
		
		int page=1;
		int limit=12;//한페이지에 보여지는 목록 개수
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String find_name=request.getParameter("find_name");
		String find_field=request.getParameter("find_field");
		//검색어, 검색 필드를 저장
		
		c.setFind_field(find_field);
		c.setFind_name("%"+find_name+"%");
		
		int totalCount=this.catService.getListCount(c);
		//검색전후 레코드 개수
		c.setStartrow((page-1)*8+1);//시작행 번호
		c.setEndrow(c.getStartrow()+limit-1);//끝행 번호
		
		List<CatVO> clist=this.catService.getCatList(c);
		//검색 전후 목록
		
		//총 페이지 수
		int maxpage=(int)((double)totalCount/limit+0.95);
		
		//시작 페이지 수
		int startpage=(((int)((double)page/10+0.9))-1)*12+1;
		
		//마지막 페이지
		int endpage=maxpage;
		
		if(endpage>startpage+12-1)
			endpage=startpage+12-1;
		
		listM.addAttribute("clist", clist);
		listM.addAttribute("page", page);
		listM.addAttribute("startpage", startpage);
		listM.addAttribute("endpage", endpage);
		listM.addAttribute("maxpage", maxpage);
		listM.addAttribute("totalcount", totalCount);
		listM.addAttribute("find_field", find_field);
		listM.addAttribute("find_name", find_name);
		listM.addAttribute("user_id", user_id);
		

		return "cat/total_cat";
	}
	
	/** 내용보기 + 수정폼 + 삭제폼 **/
	@RequestMapping("/cat/cat_cont")
	public ModelAndView cat_cont(
			int cat_no, int page,
			String state, CatVO c,
			HttpSession session) {
		
		c=this.catService.getCatCont(cat_no);
		
		String cat_cont=c.getCat_cont();
		
		String user_id=(String)session.getAttribute("user_id");
		
		ModelAndView cm=new ModelAndView();
		cm.addObject("c", c);
		cm.addObject("cat_cont", cat_cont);
		cm.addObject("page", page);
		cm.addObject("user_id", user_id);
		
		if(state.equals("cont")) {//내용보기
			cm.setViewName("cat/cat_cont");
			
		}else if(state.equals("edit")) {//수정
			cm.setViewName("cat/cat_edit");			
		}//if else if
		
		return cm;
	}//cat_cont
	
	/** 게시물 수정 **/
	@RequestMapping("/cat_edit_ok")
	public ModelAndView cat_edit_ok(
			HttpServletRequest request,
			HttpServletResponse response,
			CatVO c) throws Exception {
		
		response.setContentType("text/html;chrset=UTF-8");
	
		
		String saveFolder=request.getSession().getServletContext().getRealPath("resources/photo_upload");
		/* 첨부파일 업로드 경로, 실제 톰캣 프로젝트 경로를 반환 =>
		 * c:\spring_work\.metadata\.plugins\org.eclipse.wst.
		 * server.core\tmp1\wtbwebapps\project\photo_upload
		 */
		
		int fileSize=5*1024*1024;//이진파일 최대크기
		
		MultipartRequest multi=null;//이진파일을 받을 참조변수
		multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
		int cat_no=Integer.parseInt(multi.getParameter("cat_no"));
		int page=1;
		if(multi.getParameter("page") != null) {
			page=
				Integer.parseInt(multi.getParameter("page"));
		}
		
		String cat_title=multi.getParameter("cat_title");
		String cat_cont=multi.getParameter("cat_cont");
		
		CatVO catImg=this.catService.getCatCont(cat_no);
		
		
			File UpFile=multi.getFile("cat_file");//수정 첨부한 파일
			
			if(UpFile != null) {
				String fileName=UpFile.getName();//첨부파일명
				File DelFile=new File(saveFolder+catImg.getCat_file());
				//삭제할 파일 객체 생성
				if(DelFile.exists()) {
					DelFile.delete();//기존파일 삭제
				}
				
				Calendar cc=Calendar.getInstance();
				int year=cc.get(Calendar.YEAR);//년도값
				int month=cc.get(Calendar.MONTH)+1;//월값
				int date=cc.get(Calendar.DATE);//일값
				
				String datedir=saveFolder+"/"+year+"-"+month+"-"+date;
				//오늘 날짜 폴더 경로를 저장
				String catdir=saveFolder+"/"+year+"-"+month+"-"+date+"/"+"cat";
				//오늘 날짜 폴더 경로 + 하위의 cat폴더를 생성
				File path1=new File(datedir);
				File path2=new File(catdir);
				
				if(!(path1.exists())) {
					path1.mkdir();//오늘 날짜 폴더 경로를 확인하고 저장
				}
				if(!(path2.exists())) {
					path2.mkdir();//오늘 날짜 폴더 경로 + 하위의 cat폴더를 확인하고 저장
				}
				
				Random r=new Random();
				int random=r.nextInt(100000000);
				
				/* 첨부파일 확장자를 구함 */
				int index=fileName.lastIndexOf(".");//.를 맨우측
				//부터 찾아서 가장 먼저 나오는 .의 위치번호를 왼쪽부터 세어서
				//반환. 첫문자를 0부터 시작
				String fileExtension=fileName.substring(index+1);
				// .이후부터 마지막 문자까지 구함. 즉 확장자를 구함
				String refileName="cat"+year+month+random+"."
						+fileExtension;//새로운 첨부파일명
				String fileDBName="/"+year+"-"+month+"-"+date+"/"+
						"cat"+"/"+refileName;//DB에 저장될 레코드 값v
				
				UpFile.renameTo(new File(catdir+"/"+refileName));
				//바뀌어진 이진파일명으로 업로드
				c.setCat_file(fileDBName);
				
			}else {//파일을 첨부하지 않았을 때
				if(catImg.getCat_file() != null) {//기존파일이 있는 경우
					c.setCat_file(catImg.getCat_file());
				}else {//기존 파일이 없는 경우
					c.setCat_file("");
				}//if else
			}//if else
			
			c.setCat_no(cat_no);
			c.setCat_title(cat_title); c.setCat_cont(cat_cont);
			
			this.catService.editCat(c);//자료실 수정
			
			ModelAndView em=new ModelAndView(
					"redirect:/cat/cat_cont");
			em.addObject("cat_no",cat_no);
			em.addObject("page",page);
			em.addObject("state","cont");
			
			return em;//주소창에 cat_cont?bbs_no=번호&page=쪽번호&state=cont
			//3개의 인자값이 get방식으로 redirect됨
		
	}//cat_edit_ok
	
	/** 게시물 삭제 **/
	@RequestMapping("/cat_del_ok")
	public String cat_del_ok(CatVO c,int page,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");		
	
		String folder=request.getSession().getServletContext().getRealPath("resources/photo_upload");
		//이진파일 업로드 서버 경로		
		
		int cat_no=Integer.parseInt(request.getParameter("cat_no"));
		CatVO catImg=this.catService.getCatCont(cat_no);

		if(folder+catImg.getCat_file() != null) {
			//첨부파일이 있는 경우
			File file=new File(folder+catImg.getCat_file());
			//삭제할 파일 객체 생성
			file.delete();
			System.out.print(file);
		}
		
		this.catService.delCat(c.getCat_no());//DB로 부터 게시물 삭제
		
		return "redirect:/cat/total_cat?page="+page;
	}//cat_del_ok
	
	
	//먼치킨 고양이 목록
	/** 고양이 목록 **/
	@RequestMapping("/cat/cat_mun_list")
	public String cat_mun_list(
			Model listM,CatVO c_mun,
			HttpServletRequest request) {
		
		int page=1;
		int limit=8;//한페이지에 보여지는 목록 개수
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String find_name=request.getParameter("find_name");
		String find_field=request.getParameter("find_field");
		//검색어, 검색 필드를 저장
		
		c_mun.setFind_field(find_field);
		c_mun.setFind_name("%"+find_name+"%");
		
		int totalCount=this.catService.getListCount_mun(c_mun);
		//검색전후 레코드 개수
		c_mun.setStartrow((page-1)*8+1);//시작행 번호
		c_mun.setEndrow(c_mun.getStartrow()+limit-1);//끝행 번호
		
		List<CatVO> clist=this.catService.getCatList_mun(c_mun);
		//검색 전후 목록
		
		//총 페이지 수
		int maxpage=(int)((double)totalCount/limit+0.95);
		
		//시작 페이지 수
		int startpage=(((int)((double)page/10+0.9))-1)*8+1;
		
		//마지막 페이지
		int endpage=maxpage;
		
		if(endpage>startpage+8-1)
			endpage=startpage+8-1;
		
		listM.addAttribute("clist", clist);
		listM.addAttribute("page", page);
		listM.addAttribute("startpage", startpage);
		listM.addAttribute("endpage", endpage);
		listM.addAttribute("maxpage", maxpage);
		listM.addAttribute("totalcount", totalCount);
		listM.addAttribute("find_field", find_field);
		listM.addAttribute("find_name", find_name);
		

		return "cat/cat_mun_list";
	}
	
	//샴 고양이 목록
	/** 샴 고양이 목록 **/
	@RequestMapping("/cat/cat_shiam_list")
	public String cat_shiam_list(
			Model listM,CatVO c_shiam,
			HttpServletRequest request) {
		
		int page=1;
		int limit=8;//한페이지에 보여지는 목록 개수
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String find_name=request.getParameter("find_name");
		String find_field=request.getParameter("find_field");
		//검색어, 검색 필드를 저장
		
		c_shiam.setFind_field(find_field);
		c_shiam.setFind_name("%"+find_name+"%");
		
		int totalCount=this.catService.getListCount_shiam(c_shiam);
		//검색전후 레코드 개수
		c_shiam.setStartrow((page-1)*8+1);//시작행 번호
		c_shiam.setEndrow(c_shiam.getStartrow()+limit-1);//끝행 번호
		
		List<CatVO> clist=this.catService.getCatList_shiam(c_shiam);
		//검색 전후 목록
		
		//총 페이지 수
		int maxpage=(int)((double)totalCount/limit+0.95);
		
		//시작 페이지 수
		int startpage=(((int)((double)page/10+0.9))-1)*8+1;
		
		//마지막 페이지
		int endpage=maxpage;
		
		if(endpage>startpage+8-1)
			endpage=startpage+8-1;
		
		listM.addAttribute("clist", clist);
		listM.addAttribute("page", page);
		listM.addAttribute("startpage", startpage);
		listM.addAttribute("endpage", endpage);
		listM.addAttribute("maxpage", maxpage);
		listM.addAttribute("totalcount", totalCount);
		listM.addAttribute("find_field", find_field);
		listM.addAttribute("find_name", find_name);
		

		return "cat/cat_shiam_list";
	}
	
	//폴드 고양이 목록
	/** 폴드 고양이 목록 **/
	@RequestMapping("/cat/cat_fold_list")
	public String cat_fold_list(
			Model listM,CatVO c_fold,
			HttpServletRequest request) {
		
		int page=1;
		int limit=8;//한페이지에 보여지는 목록 개수
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String find_name=request.getParameter("find_name");
		String find_field=request.getParameter("find_field");
		//검색어, 검색 필드를 저장
		
		c_fold.setFind_field(find_field);
		c_fold.setFind_name("%"+find_name+"%");
		
		int totalCount=this.catService.getListCount_fold(c_fold);
		//검색전후 레코드 개수
		c_fold.setStartrow((page-1)*8+1);//시작행 번호
		c_fold.setEndrow(c_fold.getStartrow()+limit-1);//끝행 번호
		
		List<CatVO> clist=this.catService.getCatList_fold(c_fold);
		//검색 전후 목록
		
		//총 페이지 수
		int maxpage=(int)((double)totalCount/limit+0.95);
		
		//시작 페이지 수
		int startpage=(((int)((double)page/10+0.9))-1)*8+1;
		
		//마지막 페이지
		int endpage=maxpage;
		
		if(endpage>startpage+8-1)
			endpage=startpage+8-1;
		
		listM.addAttribute("clist", clist);
		listM.addAttribute("page", page);
		listM.addAttribute("startpage", startpage);
		listM.addAttribute("endpage", endpage);
		listM.addAttribute("maxpage", maxpage);
		listM.addAttribute("totalcount", totalCount);
		listM.addAttribute("find_field", find_field);
		listM.addAttribute("find_name", find_name);
		

		return "cat/cat_fold_list";
	}

	//페르시안 고양이 목록
	/** 페르시안 고양이 목록 **/
	@RequestMapping("/cat/cat_persian_list")
	public String cat_persian_list(
			Model listM,CatVO c_persian,
			HttpServletRequest request) {
		
		int page=1;
		int limit=8;//한페이지에 보여지는 목록 개수
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String find_name=request.getParameter("find_name");
		String find_field=request.getParameter("find_field");
		//검색어, 검색 필드를 저장
		
		c_persian.setFind_field(find_field);
		c_persian.setFind_name("%"+find_name+"%");
		
		int totalCount=this.catService.getListCount_persian(c_persian);
		//검색전후 레코드 개수
		c_persian.setStartrow((page-1)*8+1);//시작행 번호
		c_persian.setEndrow(c_persian.getStartrow()+limit-1);//끝행 번호
		
		List<CatVO> clist=this.catService.getCatList_persian(c_persian);
		//검색 전후 목록
		
		//총 페이지 수
		int maxpage=(int)((double)totalCount/limit+0.95);
		
		//시작 페이지 수
		int startpage=(((int)((double)page/10+0.9))-1)*8+1;
		
		//마지막 페이지
		int endpage=maxpage;
		
		if(endpage>startpage+8-1)
			endpage=startpage+8-1;
		
		listM.addAttribute("clist", clist);
		listM.addAttribute("page", page);
		listM.addAttribute("startpage", startpage);
		listM.addAttribute("endpage", endpage);
		listM.addAttribute("maxpage", maxpage);
		listM.addAttribute("totalcount", totalCount);
		listM.addAttribute("find_field", find_field);
		listM.addAttribute("find_name", find_name);
		

		return "cat/cat_persian_list";
	}

	/*//추천수 업데이트
	@RequestMapping("/RecUpdate.do")
	public void RecUpdate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Action action = null;
		try {
		action =new RecUpdate();
		action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//추천수 검색
	@RequestMapping("/RecCount.do")
	public void RecCount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Action action = null;
		try {
		action =new RecCount();
		action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/

/*	//다중파일업로드
	@RequestMapping("/multiplePhotoUpload")
	public void multiplePhotoUpload(HttpServletRequest request, HttpServletResponse response){
		try {				
			//날짜별 폴더 생성
			String saveFolder=
					request.getRealPath("resources/photo_upload");

			Calendar c=Calendar.getInstance();
			int year=c.get(Calendar.YEAR);
			int month=c.get(Calendar.MONTH)+1;//+1을 한 이유는
			//1월이 0으로 반환되기 때문이다.
			int date=c.get(Calendar.DATE);//일 값

			String homedir=saveFolder+"/"+year+"-"+month+"-"+date;
			String folder=year+"-"+month+"-"+date;
			//오늘 날짜 폴더 경로를 저장
			File path1=new File(homedir);
			if(!(path1.exists())) {
				path1.mkdir();//오늘 날짜 폴더 생성
			}//if
			////////

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
			String filePath = dftFilePath + "resources" + File.separator + "photo_upload" + File.separator + folder + File.separator ;
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
			sFileInfo += "&sFileURL="+"/resources/photo_upload/"+folder+"/"+realFileNm;
			PrintWriter print = response.getWriter();
			print.print(sFileInfo);
			print.flush();
			print.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
*/
}
