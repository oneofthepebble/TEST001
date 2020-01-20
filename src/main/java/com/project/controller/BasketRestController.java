package com.project.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.BasketService;
import com.project.vo.BasketVO;

/** 장바구니 컨트롤러(RestController - ajax) **/
@RestController
public class BasketRestController {
	
	@Autowired
	private BasketService basketService;
	
	/** 장바구니에 추가 **/
	@RequestMapping(value="/shop/basket_add",method=RequestMethod.POST)
	public ResponseEntity<String> basket_add(
			@RequestBody BasketVO basket,
			HttpSession session) throws Exception {
		
		/* session id 값이 있을 때 장바구니추가 활성화 */
		
		String user_id= (String)session.getAttribute("user_id");
		
		ResponseEntity<String> entity=null;
	
		try {
			basket.setBasket_id(user_id);//세션의 아이디값을 vo 객체에 저장
			basket.setValidity(1);
			
			//장바구니에 먼저 담은 상품이 있는지 검사
			int count=
					this.basketService.countBasket(basket);
			
			if(count == 0) {//없으면 추가
				this.basketService.addBasket(basket);//장바구니에 상품 추가
			}else {//있으면 수정(update)
				this.basketService.updateBasket(basket);//장바구니 상품 갱신
			}
			
			entity=new ResponseEntity<String>("SUCCESS",
				HttpStatus.OK);//장바구니 성공시 'SUCCESS'문자열을
			//반환하고 정상상태코드 200 을 반환(HttpStatus.OK에 해당)
			
		}catch(Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(e.getMessage(),
					HttpStatus.BAD_REQUEST);//장바구니 담기 실패하면 
			//예외에러 메시지를 전송하고, 사용자에게 나쁜 상태코드가 전송됨
		}//try catch

		
		return entity;
	}//basket_add()
	
	/** 회원 아이디와 상품번호에 따른 장바구니 수정 **/
	@RequestMapping(value="/shop/editBasket/",method={RequestMethod.PUT,RequestMethod.PATCH})
	//PUT은 전체자료 수정, PATCH는 일부 자료 수정, 복수개의 
	//메서드 방식 지정하는 법
	public ResponseEntity<String> editBasket(
				@RequestBody BasketVO basket,
				HttpSession session) throws Exception {
		
		/* session id 값이 있을 때 장바구니추가 활성화시켜야 함(session으로 수정할것) */
		
		String user_id= (String)session.getAttribute("user_id");// 세션으로 수정해야함
		
		ResponseEntity<String> entity=null;
	
			try {
				basket.setBasket_id(user_id);//세션의 아이디값을 vo 객체에 저장
				this.basketService.editBasket(basket);//장바구니 정보 수정
				entity=new ResponseEntity<String>("SUCCESS",
						HttpStatus.OK);
				
			}catch(Exception e) {
				e.printStackTrace();
				entity=new ResponseEntity<String>(e.getMessage(),
						HttpStatus.BAD_REQUEST);
			}//try catch
		
		return entity;
		
	}//editBasket()
	
	/** 결제 단계 장바구니 비우기(페이지 나갔을 때) **/
	@RequestMapping(value="/shop/basket_direct_del",method=RequestMethod.GET)
	public ResponseEntity<String> basket_direct_del(
			BasketVO basket,
			HttpSession session) throws Exception {
	
		String user_id= (String)session.getAttribute("user_id");
		
		ResponseEntity<String> entity=null;	
			try {
				basket.setBasket_id(user_id);//세션의 아이디값을 vo 객체에 저장
				this.basketService.directDel(basket);//페이지 나갈때
				//validity = 3, 유저 아이디 인 경우 삭제
				entity=new ResponseEntity<String>("SUCCESS",
						HttpStatus.OK);
				
			}catch(Exception e) {
				e.printStackTrace();
				entity=new ResponseEntity<String>(e.getMessage(),
						HttpStatus.BAD_REQUEST);
			}//try catch
		return entity;
	}//basket_direct_del()
	
}



















