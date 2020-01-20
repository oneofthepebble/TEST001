package com.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.BasketVO;

@Repository
public class BasketDAOImpl implements BasketDAO {
	
	@Autowired
	private SqlSession sqlSession; 
	
	@Override
	public void addBasket(BasketVO basket) {
		this.sqlSession.insert("basket_add",basket);
	}//장바구니 추가

	@Override
	public int countBasket(BasketVO basket) {
		return this.sqlSession.selectOne("basket_count",basket);
	}//장바구니 동일 상품 확인

	@Override
	public void updateBasket(BasketVO basket) {
		this.sqlSession.update("basketSame_update",basket);
	}//장바구니 동일 상품 있을 시 갱신

	@Override
	public List<BasketVO> listBasket(BasketVO basket) {
		return this.sqlSession.selectList("basket_list",basket);
	}//장바구니 목록

	@Override
	public int sumMoney(BasketVO basket) {
		this.sqlSession.selectOne("basket_SumMoney",basket);
		return this.sqlSession.selectOne("basket_SumMoney",basket);
	}//장바구니 금액 합산

	@Override
	public void editBasket(BasketVO basket) {
		this.sqlSession.update("basket_update",basket);
	}//장바구니 수정

	@Override
	public void delBasket(BasketVO basket) {
		this.sqlSession.delete("basket_del",basket);
	}//장바구니 삭제

	@Override
	public void directDel(BasketVO basket) {
		this.sqlSession.delete("direct_clean",basket);
	}//장바구니 비우기(페이지 나갈 때)
	
}
