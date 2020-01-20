package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.CatDAO;
import com.project.vo.CatVO;

@Service
public class CatServiceImpl implements CatService {

	@Autowired
	private CatDAO catDao;

	@Override
	public void insertCat(CatVO c) {
		this.catDao.insertCat(c);
	}

	@Override
	public int getListCount(CatVO c) {
		return this.catDao.getListCount(c);
	}

	@Override
	public List<CatVO> getCatList(CatVO c) {
		return this.catDao.getCatList(c);
	}

	@Override
	public CatVO getCatCont(int cat_no) {
		return this.catDao.getCatCont(cat_no);
	}

	@Override
	public CatVO getCatCont2(int cat_no) {
		return this.catDao.getCatCont(cat_no);
	}//수정폼 + 삭제폼

	@Override
	public void editCat(CatVO c) {
		this.catDao.editCat(c);
	}//수정

	@Override
	public void delCat(int cat_no) {
		this.catDao.delCat(cat_no);
	}//삭제

	//고양이(먼치킨)
	@Override
	public int getListCount_mun(CatVO c_mun) {
		return this.catDao.getListCount_mun(c_mun);
	}

	@Override
	public List<CatVO> getCatList_mun(CatVO c_mun) {
		return this.catDao.getCatList_mun(c_mun);
	}

	//고양이(샴)
	@Override
	public int getListCount_shiam(CatVO c_shiam) {
		return this.catDao.getListCount_shiam(c_shiam);
	}

	@Override
	public List<CatVO> getCatList_shiam(CatVO c_shiam) {
		return this.catDao.getCatList_shiam(c_shiam);
	}

	//고양이(폴드)
	@Override
	public int getListCount_fold(CatVO c_fold) {
		return this.catDao.getListCount_fold(c_fold);
	}

	@Override
	public List<CatVO> getCatList_fold(CatVO c_fold) {
		return this.catDao.getCatList_fold(c_fold);
	}

	//고양이(페르시안)
	@Override
	public int getListCount_persian(CatVO c_persian) {
		return this.catDao.getListCount_persian(c_persian);
	}

	@Override
	public List<CatVO> getCatList_persian(CatVO c_persian) {
		return this.catDao.getCatList_persian(c_persian);
	}
}
