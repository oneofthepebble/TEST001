package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.ShopDAO;
import com.project.vo.ShopVO;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDAO shopDAO;
	
	@Override
	public void insertShop(ShopVO s) {
		this.shopDAO.insertShop(s);
	}

	@Override
	public int getListCount(ShopVO s) {
		return this.shopDAO.getListCount(s);
	}

	@Override
	public List<ShopVO> getShopList(ShopVO s) {
		return this.shopDAO.getShopList(s);
	}

	@Override
	public ShopVO getShopCont(int item_no) {
		return this.shopDAO.getShopCont(item_no);
	}

	@Override
	public void editShop(ShopVO s) {
		this.shopDAO.editShop(s);
	}

	@Override
	public void delShop(int item_no) {
		this.shopDAO.delShop(item_no);
	}
}
