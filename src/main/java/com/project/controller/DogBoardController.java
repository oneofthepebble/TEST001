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
import com.project.service.DogService;
import com.project.vo.CatVO;
import com.project.vo.DogVO;

@Controller
public class DogBoardController {

	@Autowired
	private DogService dogService;

	/** 강아지 글쓰기 **/
	@RequestMapping("/dog/dog_write")
	public String dog_write() {		

		return "dog/dog_write";
	}//dog_write();

	/** 자료실 저장 **/
	@RequestMapping("/dog_write_ok")
	public String dog_write_ok(
			DogVO d,
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
		
		String dog_title=multi.getParameter("dog_title");
		String dog_cont=multi.getParameter("dog_cont");
		//고양이 종류, 내용을 가져옴
		File UpFile=multi.getFile("dog_file");
		//첨부한 파일을 가져옴
		
		if(UpFile != null) {//첨부한 파일이 있는 경우
			String fileName=UpFile.getName();//첨부한 파일명
			Calendar cal=Calendar.getInstance();
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;//+1을 한 이유는
			//1월이 0으로 반환되기 때문이다.
			int date=cal.get(Calendar.DATE);//일 값
			
			String datedir=saveFolder+"/"+year+"-"+month+"-"+date;
			String dogdir=saveFolder+"/"+year+"-"+month+"-"+date+"/"+"dog";
			//오늘 날짜 폴더 경로를 저장
			File path1=new File(datedir);
			File path2=new File(dogdir);
			
			if(!(path1.exists())) {
				path1.mkdir();//오늘 날짜 폴더 경로를 확인하고 저장
			}
			if(!(path2.exists())) {
				path2.mkdir();//오늘 날짜 폴더 경로 + 하위의 dog폴더를 확인하고 저장
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
			String refilename="dog"+year+month+date+random+"."+
					fileExtendsion;//새로운 첨부파일명을 저장
			String fileDBName="/"+year+"-"+month+"-"+date+"/"+
					"dog"+"/"+refilename;//DB에 저장될 레코드 값
			
			UpFile.renameTo(new File(dogdir+"/"+refilename));
			//바뀌어진 첨부파일명으로 업로드
			d.setDog_file(fileDBName);
			
		}else {
			 //mybatis는 컬럼에 null을 저장하지 못함. 그러므로 파일을
			 //null저장을 막기 위해서 else 로 처리해야 한다.
			 
			d.setDog_file("");//빈 공백을 넣어서, null이 들어가
			//에러가 나는 것을 막아준다.
		}//if else
		
		d.setDog_title(dog_title); d.setDog_cont(dog_cont);
		
		this.dogService.insertDog(d);//글쓰기 저장
 
		return "redirect:/dog/total_dog?page=1";
	}//dog_write_ok

	/** 강아지 목록 **/
	@RequestMapping("/dog/total_dog")
	public String total_dog(
			Model listM,DogVO d,
			HttpServletRequest request,
			HttpSession session) {
		
		int page=1;
		int limit=12;//한페이지에 보여지는 목록 개수
		String user_id=(String)session.getAttribute("user_id");
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String find_name=request.getParameter("find_name");
		String find_field=request.getParameter("find_field");
		//검색어, 검색 필드를 저장
		
		d.setFind_field(find_field);
		d.setFind_name("%"+find_name+"%");
		
		int totalCount=this.dogService.getListCount(d);
		//검색전후 레코드 개수
		d.setStartrow((page-1)*12+1);//시작행 번호
		d.setEndrow(d.getStartrow()+limit-1);//끝행 번호
		
		List<DogVO> dlist=this.dogService.getDogList(d);
		//검색 전후 목록
		
		//총 페이지 수
		int maxpage=(int)((double)totalCount/limit+0.95);
		
		//시작 페이지 수
		int startpage=(((int)((double)page/10+0.9))-1)*12+1;
		
		//마지막 페이지
		int endpage=maxpage;
		
		if(endpage>startpage+12-1)
			endpage=startpage+12-1;
		
		listM.addAttribute("dlist", dlist);
		listM.addAttribute("page", page);
		listM.addAttribute("startpage", startpage);
		listM.addAttribute("endpage", endpage);
		listM.addAttribute("maxpage", maxpage);
		listM.addAttribute("totalcount", totalCount);
		listM.addAttribute("find_field", find_field);
		listM.addAttribute("find_name", find_name);
		listM.addAttribute("user_id", user_id);

		return "dog/total_dog";
	}
	
	/** 내용보기 + 수정폼 + 삭제폼 **/
	@RequestMapping("/dog/dog_cont")
	public ModelAndView dog_cont(
			int dog_no, int page,
			String state, DogVO d) {
		
		d=this.dogService.getDogCont(dog_no);
		
		String dog_cont=d.getDog_cont();
		
		ModelAndView cm=new ModelAndView();
		cm.addObject("d", d);
		cm.addObject("dog_cont", dog_cont);
		cm.addObject("page", page);
		
		if(state.equals("cont")) {//내용보기
			cm.setViewName("dog/dog_cont");
			
		}else if(state.equals("edit")) {//수정
			cm.setViewName("dog/dog_edit");
			
		}//if else if
		return cm;
	}//dog_cont
	
	/** 게시물 수정 **/
	@RequestMapping("/dog_edit_ok")
	public ModelAndView dog_edit_ok(
			HttpServletRequest request,
			HttpServletResponse response,
			DogVO d) throws Exception {
		
		response.setContentType("text/html;chrset=UTF-8");
		
		String saveFolder=request.getSession().getServletContext().getRealPath("resources/photo_upload");
		/* 첨부파일 업로드 경로, 실제 톰캣 프로젝트 경로를 반환 =>
		 * c:\spring_work\.metadata\.plugins\org.eclipse.wst.
		 * server.core\tmp1\wtbwebapps\project\photo_upload
		 */
		
		int fileSize=5*1024*1024;//이진파일 최대크기
		
		MultipartRequest multi=null;//이진파일을 받을 참조변수
		multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
		int dog_no=Integer.parseInt(multi.getParameter("dog_no"));
		int page=1;
		if(multi.getParameter("page") != null) {
			page=
				Integer.parseInt(multi.getParameter("page"));
		}
		
		String dog_title=multi.getParameter("dog_title");
		String dog_cont=multi.getParameter("dog_cont");
		
		DogVO dogImg=this.dogService.getDogCont(dog_no);
		
		
			File UpFile=multi.getFile("dog_file");//수정 첨부한 파일
			
			if(UpFile != null) {
				String fileName=UpFile.getName();//첨부파일명
				File DelFile=new File(saveFolder+dogImg.getDog_file());
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
				String dogdir=saveFolder+"/"+year+"-"+month+"-"+date+"/"+"dog";
				//오늘 날짜 폴더 경로 + 하위의 dog폴더를 생성
				File path1=new File(datedir);
				File path2=new File(dogdir);	
				
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
				String refileName="dog"+year+month+random+"."
						+fileExtension;//새로운 첨부파일명
				String fileDBName="/"+year+"-"+month+"-"+date+"/"+
						"dog"+"/"+refileName;//DB에 저장될 레코드 값
				UpFile.renameTo(new File(dogdir+"/"+refileName));
				//바뀌어진 이진파일명으로 업로드
				d.setDog_file(fileDBName);
				
			}else {//파일을 첨부하지 않았을 때
				if(d.getDog_file() != null) {//기존파일이 있는 경우
					d.setDog_file(d.getDog_file());
				}else {//기존 파일이 없는 경우
					d.setDog_file("");
				}//if else
			}//if else
			d.setDog_no(dog_no);
			d.setDog_title(dog_title); d.setDog_cont(dog_cont);
			
			this.dogService.editDog(d);//자료실 수정
			
			ModelAndView em=new ModelAndView(
					"redirect:/dog/dog_cont");
			em.addObject("dog_no",dog_no);
			em.addObject("page",page);
			em.addObject("state","cont");
			
			return em;//주소창에 dog_cont?bbs_no=번호&page=쪽번호&state=cont
			//3개의 인자값이 get방식으로 redirect됨
		
	}//dog_edit_ok
	
	/** 게시물 삭제 **/
	@RequestMapping("/dog_del_ok")
	public String dog_del_ok(DogVO d,int page,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");		
	
		String folder=request.getSession().getServletContext().getRealPath("resources/photo_upload");
		//이진파일 업로드 서버 경로		
		
		int dog_no=Integer.parseInt(request.getParameter("dog_no"));
		DogVO dogImg=this.dogService.getDogCont(dog_no);		
		
		if(folder+dogImg.getDog_file() != null) {
			//첨부파일이 있는 경우
			File file=new File(folder+dogImg.getDog_file());
			//삭제할 파일 객체 생성
			file.delete();
			System.out.print(file);
		}
		
		this.dogService.delDog(d.getDog_no());//DB로 부터 게시물 삭제
		
		return "redirect:/dog/total_dog?page="+page;
	}//dog_del_ok
	
	
	//시츄 강아지 목록
	/** 강아지 목록 **/
	@RequestMapping("/dog/dog_shih_list")
	public String dog_shih_list(
			Model listM,DogVO d_shih,
			HttpServletRequest request) {
		
		int page=1;
		int limit=8;//한페이지에 보여지는 목록 개수
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String find_name=request.getParameter("find_name");
		String find_field=request.getParameter("find_field");
		//검색어, 검색 필드를 저장
		
		d_shih.setFind_field(find_field);
		d_shih.setFind_name("%"+find_name+"%");
		
		int totalCount=this.dogService.getListCount_shih(d_shih);
		//검색전후 레코드 개수
		d_shih.setStartrow((page-1)*8+1);//시작행 번호
		d_shih.setEndrow(d_shih.getStartrow()+limit-1);//끝행 번호
		
		List<DogVO> dlist=this.dogService.getDogList_shih(d_shih);
		//검색 전후 목록
		
		//총 페이지 수
		int maxpage=(int)((double)totalCount/limit+0.95);
		
		//시작 페이지 수
		int startpage=(((int)((double)page/10+0.9))-1)*8+1;
		
		//마지막 페이지
		int endpage=maxpage;
		
		if(endpage>startpage+8-1)
			endpage=startpage+8-1;
		
		listM.addAttribute("dlist", dlist);
		listM.addAttribute("page", page);
		listM.addAttribute("startpage", startpage);
		listM.addAttribute("endpage", endpage);
		listM.addAttribute("maxpage", maxpage);
		listM.addAttribute("totalcount", totalCount);
		listM.addAttribute("find_field", find_field);
		listM.addAttribute("find_name", find_name);
		

		return "dog/dog_shih_list";
	}
	
	//말티즈 강아지 목록
	/** 강아지 목록 **/
	@RequestMapping("/dog/dog_mal_list")
	public String dog_mal_list(
			Model listM,DogVO d_mal,
			HttpServletRequest request) {
		
		int page=1;
		int limit=8;//한페이지에 보여지는 목록 개수
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String find_name=request.getParameter("find_name");
		String find_field=request.getParameter("find_field");
		//검색어, 검색 필드를 저장
		
		d_mal.setFind_field(find_field);
		d_mal.setFind_name("%"+find_name+"%");
		
		int totalCount=this.dogService.getListCount_mal(d_mal);
		//검색전후 레코드 개수
		d_mal.setStartrow((page-1)*8+1);//시작행 번호
		d_mal.setEndrow(d_mal.getStartrow()+limit-1);//끝행 번호
		
		List<DogVO> dlist=this.dogService.getDogList_mal(d_mal);
		//검색 전후 목록
		
		//총 페이지 수
		int maxpage=(int)((double)totalCount/limit+0.95);
		
		//시작 페이지 수
		int startpage=(((int)((double)page/10+0.9))-1)*8+1;
		
		//마지막 페이지
		int endpage=maxpage;
		
		if(endpage>startpage+8-1)
			endpage=startpage+8-1;
		
		listM.addAttribute("dlist", dlist);
		listM.addAttribute("page", page);
		listM.addAttribute("startpage", startpage);
		listM.addAttribute("endpage", endpage);
		listM.addAttribute("maxpage", maxpage);
		listM.addAttribute("totalcount", totalCount);
		listM.addAttribute("find_field", find_field);
		listM.addAttribute("find_name", find_name);
		

		return "dog/dog_mal_list";
	}
	
	//말티즈 강아지 목록
	/** 강아지 목록 **/
	@RequestMapping("/dog/dog_poodle_list")
	public String dog_poodle_list(
			Model listM,DogVO d_poodle,
			HttpServletRequest request) {
		
		int page=1;
		int limit=8;//한페이지에 보여지는 목록 개수
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String find_name=request.getParameter("find_name");
		String find_field=request.getParameter("find_field");
		//검색어, 검색 필드를 저장
		
		d_poodle.setFind_field(find_field);
		d_poodle.setFind_name("%"+find_name+"%");
		
		int totalCount=this.dogService.getListCount_poodle(d_poodle);
		
		//검색전후 레코드 개수
		d_poodle.setStartrow((page-1)*8+1);//시작행 번호
		d_poodle.setEndrow(d_poodle.getStartrow()+limit-1);//끝행 번호
		
		List<DogVO> dlist=this.dogService.getDogList_poodle(d_poodle);
		//검색 전후 목록
		
		//총 페이지 수
		int maxpage=(int)((double)totalCount/limit+0.95);
		
		//시작 페이지 수
		int startpage=(((int)((double)page/10+0.9))-1)*8+1;
		
		//마지막 페이지
		int endpage=maxpage;
		
		if(endpage>startpage+8-1)
			endpage=startpage+8-1;
		
		listM.addAttribute("dlist", dlist);
		listM.addAttribute("page", page);
		listM.addAttribute("startpage", startpage);
		listM.addAttribute("endpage", endpage);
		listM.addAttribute("maxpage", maxpage);
		listM.addAttribute("totalcount", totalCount);
		listM.addAttribute("find_field", find_field);
		listM.addAttribute("find_name", find_name);
		

		return "dog/dog_poodle_list";
	}


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
