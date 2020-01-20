package com.project.dao;

import java.util.List;

import com.project.vo.BasketVO;

public interface BasketDAO {

	void addBasket(BasketVO basket);

	int countBasket(BasketVO basket);

	void updateBasket(BasketVO basket);

	List<BasketVO> listBasket(BasketVO basket);

	int sumMoney(BasketVO basket);

	void editBasket(BasketVO basket);

	void delBasket(BasketVO basket);

	void directDel(BasketVO basket);

}
