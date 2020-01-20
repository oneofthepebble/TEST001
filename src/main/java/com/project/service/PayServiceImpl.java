package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dao.PayDAO;
import com.project.vo.BasketVO;
import com.project.vo.PayVO;
import com.project.vo.PayokVO;
import com.project.vo.ShopVO;

@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private PayDAO payDAO;
	
	@Transactional
	@Override
	public void insertPay(PayVO pay,BasketVO basket) {
		this.payDAO.insertPay(pay);//주문내역 추가
		this.payDAO.updateBasket(basket);//장바구니 validity 업데이트
	}//주문 내역 추가

	@Override
	public List<PayVO> list_pay(String user_id) {
		return this.payDAO.list_pay(user_id);
	}//주문 내역 불러옴

	@Override
	public int getAdminPayListCount(PayVO pay) {
		return this.payDAO.getAdminPayListCount(pay);
	}//관리자 주문 내역 레코드 개수

	@Override
	public List<PayVO> getAdminPayList(PayVO pay) {
		return this.payDAO.getAdminPayList(pay);
	}//관리자 주문 내역 목록 뽑아오기

	@Transactional
	@Override
	public void payConfirm(PayVO pay) {
		this.payDAO.updatePay(pay);//Pay 테이블 validity=2로 업데이트
		this.payDAO.copyBasket(pay);//장바구니 validity=2인 정보를 새로운 테이블에 옮김
		this.payDAO.cleanBasket(pay);//pay_no값에 해당하는 장바구니 비우기
	}//Transaction 적용
	
	@Override
	public List<PayokVO> stockView(int pay_no) {
		return this.payDAO.stockView(pay_no);
	}
	
	@Transactional
	@Override
	public void sendConfirm(List<PayokVO> stockView,PayVO pay,ShopVO s) {
		this.payDAO.updatePay(pay);//Pay 테이블 validity=3으로 업데이트
		
		for(PayokVO i : stockView) {
			s.setItem_no(i.getProduct_no());
			s.setItem_stockCount(i.getBasket_count());
			this.payDAO.updateStock(s);
		}//확장 for -> 재고 수정 메서드 장바구니 상품수 만큼 반복
	}//Transaction 적용

	@Override
	public int sumMoney(int pay_no) {
		return this.payDAO.sumMoney(pay_no);
	}//결제완료 장바구니 pay_ok의 가격 계산

	@Override
	public List<String> getProductName(int pay_no) {
		return this.payDAO.getProductName(pay_no);
	}//주문리스트에 해당하는 상품명 가져오기

	@Override
	public List<String> getProductName2(int pay_no) {
		return this.payDAO.getProductName2(pay_no);
	}//주문리스트에 해당하는 상품명 가져오기 2

	@Override
	public PayVO choicePay(int pay_no) {
		return this.payDAO.choicePay(pay_no);
	}//주문내역하나만 가져오기

	


}
