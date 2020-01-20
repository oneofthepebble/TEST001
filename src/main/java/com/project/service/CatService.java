package com.project.service;

import java.util.List;

import com.project.vo.CatVO;

public interface CatService {

	void insertCat(CatVO c);

	int getListCount(CatVO c);

	List<CatVO> getCatList(CatVO c);

	CatVO getCatCont(int cat_no);

	CatVO getCatCont2(int cat_no);

	void editCat(CatVO c);

	void delCat(int cat_no);
	
	//고양이(먼치킨)
	int getListCount_mun(CatVO c_mun);

	List<CatVO> getCatList_mun(CatVO c_mun);
	
	//고양이(샴)
	int getListCount_shiam(CatVO c_shiam);

	List<CatVO> getCatList_shiam(CatVO c_shiam);

	//고양이(폴드)
	int getListCount_fold(CatVO c_fold);

	List<CatVO> getCatList_fold(CatVO c_fold);

	//고양이(페르시안)
	int getListCount_persian(CatVO c_persian);

	List<CatVO> getCatList_persian(CatVO c_persian);


}
