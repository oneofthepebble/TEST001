package com.project.service;

import java.util.List;

import com.project.vo.ShopVO;

public interface ShopService {

	void insertShop(ShopVO s);

	int getListCount(ShopVO s);

	List<ShopVO> getShopList(ShopVO s);

	ShopVO getShopCont(int item_no);

	void editShop(ShopVO s);

	void delShop(int item_no);

}
