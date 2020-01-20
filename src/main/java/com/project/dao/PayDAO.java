package com.project.dao;

import java.util.List;

import com.project.vo.BasketVO;
import com.project.vo.PayVO;
import com.project.vo.PayokVO;
import com.project.vo.ShopVO;

public interface PayDAO {

	void insertPay(PayVO pay);

	void updateBasket(BasketVO basket);

	List<PayVO> list_pay(String user_id);

	int getAdminPayListCount(PayVO pay);

	List<PayVO> getAdminPayList(PayVO pay);

	void updatePay(PayVO pay);

	void copyBasket(PayVO pay);

	void cleanBasket(PayVO pay);

	List<PayokVO> stockView(int pay_no);
	
	void updateStock(ShopVO s);

	int sumMoney(int pay_no);

	List<String> getProductName(int pay_no);

	List<String> getProductName2(int pay_no);

	PayVO choicePay(int pay_no);


}
