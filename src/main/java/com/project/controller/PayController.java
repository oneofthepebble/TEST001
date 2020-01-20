package com.project.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.service.BasketService;
import com.project.service.MemberJoinService;
import com.project.service.PayService;
import com.project.vo.BasketVO;
import com.project.vo.MemberVO;
import com.project.vo.PayVO;
import com.project.vo.PayokVO;
import com.project.vo.ShopVO;

@Controller
public class PayController {
	
	@Autowired
	private BasketService basketService;
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private MemberJoinService memberJoinService;
	
	/** 결제페이지로 이동 **/
	@RequestMapping("shop/pay")
	public String pay(
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		
		int page = Integer.parseInt(request.getParameter("page"));
		redirectAttributes.addAttribute("page",page);
		redirectAttributes.addAttribute("vali",1);
		
		return "redirect:/shop/pay_page";
	}//pay()
	
	
	/** 결제 페이지의 상품 목록 **/
	@RequestMapping("shop/pay_page")
	public String pay_list(
			BasketVO basket,MemberVO mvo,
			HttpServletRequest request,
			HttpServletResponse response,
			Model basketList,
			HttpSession session) throws Exception  {
		
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}//page 값 받아옴
		int vali = Integer.parseInt(request.getParameter("vali"));
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		//session 처리!
		
		String user_id = (String)session.getAttribute("user_id");;
		
		if(user_id == null) {
			out.println("<script>");
			out.println("alert('로그인 하신 후 이용해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {
			Map<String,Object> map=new HashMap<String, Object>();
			basket.setBasket_id(user_id); 
			basket.setPay_no(0);
			
			if(vali == 1) {
				basket.setValidity(1);
				
			}else if(vali == 3) {
				basket.setValidity(3); basket.setBasket_page(page);
				//장바구니에 먼저 담은 상품이 있는지 검사
				int count=
						this.basketService.countBasket(basket);
				
				if(count == 0) {//없으면 추가
					this.basketService.addBasket(basket);//장바구니에 상품 추가
					
				}else {//있으면 수정(update)
					this.basketService.updateBasket(basket);//장바구니 상품 갱신
					
				}//if else
				
			}//if else if
			
			List<BasketVO> list=this.basketService.listBasket(basket);//결제 페이지의 상품 정보
			//만약 유저정보도 뽑아올거면 여기서 메서드 돌리기 -> 회원아이디로 회원정보에서 이름 전번 이메일
			mvo.setUser_id(user_id);
			
			mvo = this.memberJoinService.MypageView(mvo);
			
			String user_name = mvo.getUser_name();
			String user_phone = mvo.getUser_phone();
					
			int sumMoney=this.basketService.sumMoney(basket);//결제 페이지의 상품 전체 금액 호출
			/* 장바구니 전체 금액에 따라 배송비 구분 */
			//배송료(10만원 이상 -> 무료, 미만 -> 2500원)
			int fee = sumMoney >= 100000 ? 0 : 2500;
			
			map.put("list",list); //장바구니 정보
			map.put("count",list.size());//장바구니 상품 유무
			map.put("sumMoney",sumMoney);//장바구니 합계 금액
			map.put("fee",fee);//배송비
			map.put("allSum",sumMoney+fee);//주문 총 합계 금액(상품 + 배송비)
			map.put("validity",vali);//validity 값 전달
			map.put("page",page);//페이지값전달
			map.put("user_name",user_name);
			map.put("user_phone",user_phone);
			
			
			basketList.addAttribute("map",map);
			
			return "shop/pay_page";
			
		}//if else 
		
		return null;
	}
	
	
	/** 결제하기(주문 목록 추가) **/
	@RequestMapping("shop/pay_page_ok")
	public String pay_page_ok(
			PayVO pay,BasketVO basket,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		//session 처리!
		
		String user_id = (String)session.getAttribute("user_id");
		String basket_id = user_id;
		
		if(user_id == null) {
			out.println("<script>");
			out.println("alert('로그인 하신 후 이용해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {
			int page=1;
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));
			}//page 값 받아옴
			
			int validity = Integer.parseInt(request.getParameter("validity"));
			
			int pay_price=Integer.parseInt(request.getParameter("pay_price"));
			//총 결제 금액
			basket.setBasket_id(basket_id); basket.setValidity(validity);
			pay.setUser_id(user_id); pay.setPay_price(pay_price);
			//유저 아이디와 결제 금액 저장
			
			this.payService.insertPay(pay,basket);//주문 내역 추가,장바구니 업데이트
			//트랜잭션 적용
			
			return "redirect:/shop/pay_page_confirm?page="+page;
		}//if else 

		return null;
	}
	
	/** 결제완료 페이지 **/
	@RequestMapping("shop/pay_page_confirm")
	public String pay_page_confirm(
			HttpServletRequest request,
			HttpServletResponse response,
			Model m, HttpSession session) throws Exception {
		
		int page = Integer.parseInt(request.getParameter("page"));
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		//session 처리!
		
		String user_id = (String)session.getAttribute("user_id");
		
		if(user_id == null) {
			out.println("<script>");
			out.println("alert('로그인 하신 후 이용해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {
			
			m.addAttribute("page",page);
			
			return "shop/pay_page_confirm";
		}//if else
		
		return null;
	}//pay_page_confirm()
	
	/** 주문 내역으로 이동 **/
	@RequestMapping("shop/pay_list_go")
	public String pay_list_go(
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		
		int page = Integer.parseInt(request.getParameter("page"));
		
		redirectAttributes.addAttribute("page",page);
		
		return "redirect:/shop/pay_list";
	}//pay_list_go()
	
	/** 주문 내역 **/
	@RequestMapping("shop/pay_list")
	public String pay_list(
			PayVO pay,
			HttpServletRequest request,
			HttpServletResponse response,
			Model payList, HttpSession session) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		//session 처리!
		
		String user_id = (String)session.getAttribute("user_id");
		
		if(user_id == null) {
			out.println("<script>");
			out.println("alert('로그인 하신 후 이용해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {
			int page=1;
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));
			}//page 값 받아옴
			
			List<PayVO> list = this.payService.list_pay(user_id);//주문 내역 목록
			
			/** 각 주문 목록에서 상품 하나의 상품명만 가져와서 출력하게끔 한다. **/
			List<String> payNameList = new ArrayList<String>();
			List<String> payNameList2 = new ArrayList<String>();

			for(PayVO p : list) {
				payNameList.addAll(this.payService.getProductName(p.getPay_no()));
				//장바구니의 상품명을 가져옴
				payNameList2.addAll(this.payService.getProductName2(p.getPay_no()));
				//주문확정 pay_ok 상품명을 가져옴
			}//for
			
			payList.addAttribute("user_id",user_id);//id값 전달
			payList.addAttribute("list",list);
			payList.addAttribute("payNameList",payNameList);//상품명값
			payList.addAttribute("payNameList2",payNameList2);//상품명값(2,3)
			payList.addAttribute("page",page);
			
			return "shop/pay_list";
		}//if else
		
		return null;
	}//pay_list()
	
	/** 주문 상품명 클릭시, 해당 주문의 상품 리스트로 이동 **/
	@RequestMapping("shop/pay_item_list_go")
	public String pay_item_list_go(
			PayVO pay,
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		
		int pay_no = Integer.parseInt(request.getParameter("pay_no"));
		int validity = Integer.parseInt(request.getParameter("validity"));
		
		redirectAttributes.addAttribute("pay_no",pay_no);
		redirectAttributes.addAttribute("validity",validity);
		
		return "redirect:/shop/pay_item_list";
	}//pay_item_list_go()
	
	@RequestMapping("shop/pay_item_list")
	public String pay_item_list(
			BasketVO basket,PayVO pay,PayokVO payOK,
			HttpServletRequest request,
			HttpServletResponse response,
			Model payItemList, HttpSession session) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		//session 처리!
		
		String user_id = (String)session.getAttribute("user_id");
		
		if(user_id == null) {
			out.println("<script>");
			out.println("alert('로그인 하신 후 이용해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {
			int page=1;
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));
			}//page 값 받아옴
			int pay_no = Integer.parseInt(request.getParameter("pay_no"));
			//선택한 주문 번호
			
			pay = this.payService.choicePay(pay_no);
			//선택한 주문의 번호에 따른 주문내역 하나만 불러옴
			
			int validity = pay.getValidity();
			//주문 내역의 validity. 1 -> 결제 확인중 // 2 -> 결제 확인 완료, 3 -> 발송완료
			String pay_id = pay.getUser_id();
			//해당 주문 내역의 유저 아이디
			
			//1 -> 결제 확인 중인 장바구니 상품 리스트. 장바구니의 vali=2인 상품을 불러옴
			if(validity == 1) {//주문 내역의 validity가 1일 때(결제확인전)
				Map<String,Object> map=new HashMap<String, Object>();
				basket.setBasket_id(pay_id); basket.setValidity(2);
				basket.setPay_no(pay_no);//주문내역 번호. 주문내역을 묶어서 
				//해당 주문 번호의 상품 목록만 보여주게끔 해주는 변수.
				List<BasketVO> list=this.basketService.listBasket(basket);//장바구니 정보
				//validity=2 인 장바구니 목록을 가져온다.
				
				int sumMoney=this.basketService.sumMoney(basket);//장바구니 전체 금액 호출
				/* 장바구니 전체 금액에 따라 배송비 구분 */
				//배송료(10만원 이상 -> 무료, 미만 -> 2500원)
				int fee = sumMoney >= 100000 ? 0 : 2500;
				
				map.put("list",list); //장바구니 정보
				map.put("count",list.size());//장바구니 상품 유무
				map.put("sumMoney",sumMoney);//장바구니 합계 금액
				map.put("fee",fee);//배송비
				map.put("allSum",sumMoney+fee);//주문 총 합계 금액(상품 + 배송비)
				
				payItemList.addAttribute("user_id",pay_id);//id값 전달
				payItemList.addAttribute("page",page);//page 값 받아서 전달(목록버튼에 전달하기위함)
				payItemList.addAttribute("map",map);
				
			
			//2 -> 결제 확인 끝난 장바구니 상품 리스트. 구매 확정 테이블의 상품을 불러옴
			//3 -> 발송 끝난 장바구니 상품 리스트. 함께 불러옴
			}else if(validity == 2 || validity == 3) {//주문 내역의 validity가 2이거나 3일 때(결제확인후)
				
				Map<String,Object> map=new HashMap<String, Object>();
				
				List<PayokVO> stockView = this.payService.stockView(pay_no);
				//pay_ok테이블에 담겨있는 상품 목록을 가져오기 위한 List객체 stockView
				
				
				int sumMoney=this.payService.sumMoney(pay_no);//장바구니 전체 금액 호출
				/* 장바구니 전체 금액에 따라 배송비 구분 */
				//배송료(10만원 이상 -> 무료, 미만 -> 2500원)
				int fee = sumMoney >= 100000 ? 0 : 2500;
				
				map.put("list",stockView); //장바구니 정보
				map.put("count",stockView.size());//장바구니 상품 유무
				map.put("sumMoney",sumMoney);//장바구니 합계 금액
				map.put("fee",fee);//배송비
				map.put("allSum",sumMoney+fee);//주문 총 합계 금액(상품 + 배송비)
				
				payItemList.addAttribute("user_id",pay_id);//id값 전달
				payItemList.addAttribute("map",map);
				payItemList.addAttribute("page",page);//page 값 받아서 전달(목록버튼에 전달하기위함)
			
			}//if else if -> 장바구니 basket에서 불러오는가 // 주문확인 이후 payok테이블에서 불러오는가 분기시킴
			
			return "shop/pay_item_list";
		}//if else -> 로그인 여부 분기
		
		return null;
	}//pay_item_list()
	
	/** 관리자 주문 내역 **/
	@RequestMapping("shop/admin_paylist_go")
	public String admin_paylist_go() {
		
		return "redirect:/shop/admin_paylist?find_field=item_name&find_name=";
	}//admin_paylist_go()
	
	@RequestMapping("shop/admin_paylist")
	public String admin_paylist(
			Model m,PayVO pay,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		//session 처리!(관리자)
		
		String user_id = (String)session.getAttribute("user_id");
		
		if((user_id == null) || !(user_id.equals("admin"))) {
			out.println("<script>");
			out.println("alert('관리자 영역입니다. 관리자 계정으로 로그인해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {
			int page=1;
			int limit=6;//한페이지에 보여지는 주문내역 개수
			
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));
			}
			String find_name = request.getParameter("find_name");
			String find_field = request.getParameter("find_field");
			//검색어, 검색필드 를 저장
			
			pay.setFind_field(find_field);
			pay.setFind_name("%"+find_name+"%");
				
			int totalCount=this.payService.getAdminPayListCount(pay);
			//검색전후 레코드 개수
			
			pay.setStartrow((page-1)*limit+1);//시작행번호
			pay.setEndrow(pay.getStartrow()+limit-1);//끝행 번호
			
			List<PayVO> payList=this.payService.getAdminPayList(pay);
			//검색 전후 목록
			
			//총 페이지 수
			int maxpage=(int)((double)totalCount/limit+0.95);
			
			//시작페이지
			int startpage=(((int)((double)page/10+0.9))-1)*6+1;
			
			//마지막 페이지
			int endpage=maxpage;
			
			if(endpage>startpage+6-1)
				endpage=startpage+6-1;
			
			/** 각 주문 목록에서 상품 하나의 상품명만 가져와서 출력하게끔 한다. **/
			List<String> payNameList = new ArrayList<String>();
			List<String> payNameList2 = new ArrayList<String>();

			for(PayVO p : payList) {
				payNameList.addAll(this.payService.getProductName(p.getPay_no()));
				//장바구니의 상품명을 가져옴
				payNameList2.addAll(this.payService.getProductName2(p.getPay_no()));
				//주문확정 pay_ok 상품명을 가져옴
			}//for
			
			m.addAttribute("payList",payList);
			m.addAttribute("payNameList",payNameList);//상품명값
			m.addAttribute("payNameList2",payNameList2);//상품명값(2,3)
			m.addAttribute("page",page);
			m.addAttribute("startpage",startpage);
			m.addAttribute("endpage",endpage);
			m.addAttribute("maxpage",maxpage);
			m.addAttribute("totalcount",totalCount);
			m.addAttribute("find_field",find_field);
			m.addAttribute("find_name",find_name);
			
			return "shop/shop_admin_paylist";
		}//if else
		
		return null;
	}//admin_paylist()
	
	/** 판매 승인 처리 **/
	@RequestMapping("shop/pay_admin_confirm")
	public String pay_admin_confirm(
			PayVO pay,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		//session 처리!(관리자)
		
		String user_id = (String)session.getAttribute("user_id");
		
		if((user_id == null) || !(user_id.equals("admin"))) {
			out.println("<script>");
			out.println("alert('관리자 영역입니다. 관리자 계정으로 로그인해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {/** 관리자 계정으로 수정해야할것 **/
			int pay_no = Integer.parseInt(request.getParameter("pay_no"));
			int validity = 2;//결제확인여부
			
			/* 트랜잭션 적용(총 3개)
			 * 	1. pay_no에 해당하는 Pay 테이블의 주문내역 validity 값 2로 변경(결제 확인됐다는 의미).
			 *  2. pay_no에 해당하는 장바구니 validity 2에 담긴 정보를 주문확정 테이블로 옮김
			 *  3. 장바구니에 남은 validity 2 데이터 삭제
			 */
			
			pay.setPay_no(pay_no); pay.setValidity(validity);
			this.payService.payConfirm(pay);//판매 승인
			//트랜잭션 적용
			
			return "redirect:/shop/admin_paylist?find_field=item_name&find_name=";
		}//if else
		
		return null;
	}//pay_admin_confirm()
	
	/** 배송처리(상품 재고 감소) **/
	@RequestMapping("shop/pay_admin_sendItem")
	public String pay_admin_sendItem(
			PayVO pay, PayokVO payOK,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		//session 처리!(관리자)
		
		String user_id = (String)session.getAttribute("user_id");
		
		if((user_id == null) || !(user_id.equals("admin"))) {
			out.println("<script>");
			out.println("alert('관리자 영역입니다. 관리자 계정으로 로그인해주세요.');");
			out.println("location='/login';");
			out.println("</script>");
			
		}else {/** 관리자 계정으로 수정해야할것 **/
			int pay_no = Integer.parseInt(request.getParameter("pay_no"));
			int validity = 3;//주문상태를 3(발송처리)으로 변경
			
			/* 트랜잭션 적용 : 1. 주문내역 validity=3 으로 수정  2. 상품 재고 줄이기
			 * 주문확정 테이블에서  payCom_no에 해당하는 장바구니 정보에서 상품 수량만 불러와 
			 * shop테이블의 상품 수량 정보를 업데이트 시킨다(마이너스 해서 수량을 줄임)
			 */
			
			List<PayokVO> stockView = this.payService.stockView(pay_no);
			//pay_ok테이블에 담겨있는 상품 목록을 가져오기 위한 List객체 stockView
			
			pay.setPay_no(pay_no); pay.setValidity(validity);//주문 validity 
			//변경을 위한 변수 담기
			
			ShopVO s = new ShopVO();
			this.payService.sendConfirm(stockView,pay,s);//상품 발송 승인
			//트랜잭션을 함께 적용
			
			return "redirect:/shop/admin_paylist?find_field=item_name&find_name=";
		}//if else
			
		return null;
	}//pay_admin_sendItem()
	
	/** 바로 구매 페이지 이동 **/
	@RequestMapping("shop/pay_direct_go")
	public String pay_direct_go(
			BasketVO basket,
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		
		int page = Integer.parseInt(request.getParameter("page"));
		redirectAttributes.addAttribute("page",page);
		redirectAttributes.addAttribute("product_no",basket.getProduct_no());
		redirectAttributes.addAttribute("basket_count",basket.getBasket_count());
		redirectAttributes.addAttribute("vali",3);//3값 전달
		
		return "redirect:/shop/pay_page";
	}//pay_direct_go()
	

}



