package com.project.controller;

import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.service.BasketService;
import com.project.vo.BasketVO;

/** 장바구니 컨트롤러 **/
@Controller
public class BasketController {
	
	@Autowired
	private BasketService basketService;
	
	/** 장바구니 목록으로 이동 **/
	@RequestMapping("shop/basket_list_go")
	public String basket_list_go(
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		
		int page = Integer.parseInt(request.getParameter("page"));
		
		redirectAttributes.addAttribute("page",page);
		
		/*  <매우 중요!>
		 * 리다이렉트란? 무언가 기능이 작동해서 값이 변하는 등의 이벤트가 있을 때
		 * 페이지를 다시 새로 연결해서 다른 페이지로 이동시키는 것이다.
		 * 값이 변하는데도 단순하게 포워딩으로 넘겨버리면, 
		 * 새로고침했을때 메서드가 계속해서 돌아버리는 문제점이 발생한다!
		 * 주의 할것!
		 * 
		 * 장바구니 목록 리스트를 리다이렉트 해줄때, 장바구니 목록에 들어가는  변수 
		 * page를 똑같이 보내줘야 한다. 장바구니 목록에서 이 변수를
		 * 담아서 리스트를 출력하고 있기 때문. 그러기위해서 변수를 받아와서
		 * 리다이렉트 할때 변수를 보내주게끔 함. 
		 */
		
		return "redirect:/shop/basket_list";
	}//basket_list_go()
	
	/** 장바구니 상품 목록 **/
	@RequestMapping("shop/basket_list")
	public String basket_list(
			BasketVO basket,
			HttpServletRequest request,
			HttpServletResponse response,
			Model basketList,HttpSession session) throws Exception {
		
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}//page 값 받아옴
		
		response.setContentType("text/html;chrset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		
		String user_id= (String)session.getAttribute("user_id");
	
		if(user_id == null) {//id 값이 없을 때
			out.println("<script>");
			out.println("alert('로그인 하신 후 이용해주세요.');");
			out.println("history.back();");
			out.println("</script>");
		
		}else {
			Map<String,Object> map=new HashMap<String, Object>();
			basket.setBasket_id(user_id); basket.setValidity(1);
			basket.setPay_no(0);
			List<BasketVO> list=this.basketService.listBasket(basket);//장바구니 정보
			
			int sumMoney=this.basketService.sumMoney(basket);//장바구니 전체 금액 호출
			/* 장바구니 전체 금액에 따라 배송비 구분 */
			//배송료(10만원 이상 -> 무료, 미만 -> 2500원)
			int fee = sumMoney >= 100000 ? 0 : 2500;
			
			map.put("list",list); //장바구니 정보
			map.put("count",list.size());//장바구니 상품 유무
			map.put("sumMoney",sumMoney);//장바구니 합계 금액
			map.put("fee",fee);//배송비
			map.put("allSum",sumMoney+fee);//주문 총 합계 금액(상품 + 배송비)
			
			basketList.addAttribute("basket_id",user_id);//id값 전달
			basketList.addAttribute("page",page);//page 값 받아서 전달(목록버튼에 전달하기위함)
			basketList.addAttribute("map",map);
			
			return "/shop/basket_list";
		}//if else
		
		return null;
	}
	
	/** 장바구니 상품 삭제 **/
	@RequestMapping("shop/basket_del")
	public String basket_del(
				BasketVO basket, @RequestParam int basket_no,
				HttpServletRequest request,
				HttpServletResponse response,
				RedirectAttributes redirectAttributes,
				HttpSession session) throws Exception {
		
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
			basket.setBasket_no(basket_no); basket.setBasket_id(user_id);
			
			this.basketService.delBasket(basket);//폼 액션 매핑된 장바구니 번호값을 가져와서
			//원하는 장바구니상품 한개만을 삭제
			
			int page = Integer.parseInt(request.getParameter("page"));
			
			redirectAttributes.addAttribute("basket_id",user_id);
			redirectAttributes.addAttribute("page",page);
			
			return "redirect:/shop/basket_list";
		}//if else 
		
		return null;
	}//basket_del()	
}
