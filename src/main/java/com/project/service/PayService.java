package com.project.service;

import java.util.List;

import com.project.vo.BasketVO;
import com.project.vo.PayVO;
import com.project.vo.PayokVO;
import com.project.vo.ShopVO;

public interface PayService {

	void insertPay(PayVO pay,BasketVO basket);

	List<PayVO> list_pay(String user_id);

	int getAdminPayListCount(PayVO pay);

	List<PayVO> getAdminPayList(PayVO pay);

	void payConfirm(PayVO pay);
	
	List<PayokVO> stockView(int pay_no);

	void sendConfirm(List<PayokVO> stockView,PayVO pay,ShopVO s);

	int sumMoney(int pay_no);

	List<String> getProductName(int pay_no);

	List<String> getProductName2(int pay_no);

	PayVO choicePay(int pay_no);
	
}
