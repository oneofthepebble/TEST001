package com.project.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.BasketVO;
import com.project.vo.PayVO;
import com.project.vo.PayokVO;
import com.project.vo.ShopVO;

@Repository
public class PayDAOImpl implements PayDAO{

	@Autowired
	private SqlSession sqlSession;
	
	
	@Override
	public void insertPay(PayVO pay) {
		this.sqlSession.insert("pay_insert",pay);
	}//주문목록에 추가

	@Override
	public void updateBasket(BasketVO basket) {
		this.sqlSession.update("bs_update",basket);
	}//장바구니 컬럼 업데이트(유효성+주문값)

	@Override
	public List<PayVO> list_pay(String user_id) {
		return this.sqlSession.selectList("pay_list",user_id);
	}//주문 내역 불러오기

	@Override
	public int getAdminPayListCount(PayVO pay) {
		return this.sqlSession.selectOne("pay_admin_count",pay);
	}//관리자 주문 내역 검색 전후 레코드 개수

	@Override
	public List<PayVO> getAdminPayList(PayVO pay) {
		return this.sqlSession.selectList("pay_admin_list",pay);
	}//관리자 주문 내역 목록

	@Override
	public void updatePay(PayVO pay) {
		this.sqlSession.update("pay_update",pay);
	}//주문 내역 validity 업데이트

	@Override
	public void copyBasket(PayVO pay) {
		this.sqlSession.insert("payok_copy",pay);
	}//장바구니 validity=2인 정보를 복사

	@Override
	public void cleanBasket(PayVO pay) {
		this.sqlSession.delete("bs_clean",pay);
	}//기존 장바구니 비우기

	@Override
	public List<PayokVO> stockView(int pay_no) {
		return this.sqlSession.selectList("payok_list",pay_no);
	}//pay_ok 테이블에 담긴 상품 목록
	
	@Override
	public void updateStock(ShopVO s) {
		this.sqlSession.update("stock_change",s);
	}//상품 재고 변경

	@Override
	public int sumMoney(int pay_no) {
		return this.sqlSession.selectOne("payok_sumMoney",pay_no);
	}//결제 확인 pay_ok테이블의 장바구니 금액 합계

	@Override
	public List<String> getProductName(int pay_no) {
		return this.sqlSession.selectList("getbasket_name", pay_no);
	}//주문리스트에 해당하는 상품명 가져오기(장바구니)

	@Override
	public List<String> getProductName2(int pay_no) {
		return this.sqlSession.selectList("getbasket_name2", pay_no);
	}//주문리스트에 해당하는 상품명 가져오기(pay_ok)

	@Override
	public PayVO choicePay(int pay_no) {
		return this.sqlSession.selectOne("payList_one",pay_no);
	}//주문번호에 따른 하나의 주문내역을 가져옴

	

}
