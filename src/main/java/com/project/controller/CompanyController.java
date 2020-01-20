package com.project.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.project.service.CompanyService;
import com.project.vo.CompanyVO;

@Controller
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping("/Company")
	public ModelAndView MapTest(ModelAndView mav ,HttpSession session) {
		String user_id = (String) session.getAttribute("user_id");
		
		List Company_names = companyService.selectCompany_name();
		mav.setViewName("CompanyMap/CompanyMap");
		mav.addObject("company_names", Company_names);
		mav.addObject("user_id", user_id);
		return mav;		
	}

	//지점 추가 multipart 라서 multipart request로 응답받아야 값을 가져올 수 있다.
	@RequestMapping("/newCompany")
	public String newCompany(CompanyVO companyVO,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		
		//image 저장 path경로 뒤에 폴더
		String saveFolder=request.getRealPath("resources/photo_upload");
		
		//jsp에서 multipart 사용시 사용해야 하는 객체
		int fileSize=5*1024*1024;//첨부파일 최대크기(5MB)
		MultipartRequest multi=null;//첨부파일을 가져오는 api
		multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
		
		//mulltipartRequest에서 받은 request 값을 vo 값에 저장
		companyVO.setCompany_name(multi.getParameter("company_name"));
		companyVO.setCompany_x(multi.getParameter("company_x"));
		companyVO.setCompany_y(multi.getParameter("company_y"));
		companyVO.setCompany_addr(multi.getParameter("company_addr"));
		

		
		
		
		//input값에 빈공백이 들어왔을경우
		if(companyVO.getCompany_name().equals("") || companyVO.getCompany_x().equals("") || companyVO.getCompany_y().equals("")
				|| companyVO.getCompany_addr().equals("")) {
			out.println("<script> alert('정보를 다시 확인해 주세요'); history.back(); </script>");
			
		}else {
			File UpFile = null;
			//image값 안들어왔을 경우 기본이미지 경로로 초기화
			
				
			if(multi.getFile("company_image") != null) {
				UpFile=multi.getFile("company_image");
				
				String fileName=UpFile.getName();//첨부한 파일명
				//saveforder Path경로에 /companyimage 폴더 생성후 날짜 타입으로 폴더 생성
				Date today = new Date();
				SimpleDateFormat folderDate = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat fileDate = new SimpleDateFormat("yyyyMMdd");
				String folderName = saveFolder+"/CompanyImage/"+folderDate.format(today);
				
				File path1=new File(folderName);
				if(!(path1.exists())) {
					path1.mkdir();//오늘 날짜 폴더 생성
				}//if
				
				//0이상 1억미만 사이의 정수 숫자 난수 발생
				Random r=new Random();
				int random=r.nextInt(100000000);
				
				/** 첨부한 파일 확장자를 구함 **/
				//첨부한 파일에서 .를 맨 오른쪽부터 찾아서 가장 먼저 나오는 .의 위치번호를
				//왼쪽부터 세어서 번호값을 반환. 첫문자는 0부터 센다.
				int index=fileName.lastIndexOf(".");
				 
				//마침표 이후부터 마지막 문자까지 구함. 즉 확장자를 구함.
				String fileExtendsion=fileName.substring(index+1);
				
				//날짜이름+random으로 file 이름 정하고 . fileExtendsion으로 확장자 붙여줌
				String newFileName=fileDate.format(today)+random+"."+fileExtendsion;
				
				//DB에 저장될 폴더 경로 지정 추후에 값가져와서 html에 뿌려줄 값
				String locationImage=folderName+"/"+newFileName;//DB에 저장될 레코드 값
				
				//첨부한 파일을 가져옴
				//바뀌어진 첨부파일명으로 업로드
				UpFile.renameTo(new File(locationImage));
				
				companyVO.setCompany_image("resources/photo_upload/CompanyImage/"+folderDate.format(today)+"/"+newFileName);
				System.out.println("locationImage="+locationImage);
			}else {
				//이미지 파일을 첨부 안했을 경우 기본 이미지 경로
				companyVO.setCompany_image("resources/photo_upload/default/default.png");			
	
			}
			//정상적으로 값이 들어오면 db에 저장		
			companyService.insertCompany(companyVO);			
			out.println("<script> alert('지점 등록 성공'); location='/Company'; </script>");			
		}
		
		
		return null;
	}
	
	//AJAX로 지점 정보 값 return
	@RequestMapping("/MapOverlay")
	@ResponseBody
	public CompanyVO MapOverlay(CompanyVO companyVO) {
		System.out.println(companyVO.getCompany_name());
		companyVO = companyService.selectCompany_info(companyVO);
		System.out.println("반환 name = "+ companyVO.getCompany_name());
		System.out.println("반환 no = "+ companyVO.getCompany_no());
		System.out.println("반환 image = "+ companyVO.getCompany_image());
		System.out.println("반환 addr = "+ companyVO.getCompany_addr());
		return companyVO;
	}

}
