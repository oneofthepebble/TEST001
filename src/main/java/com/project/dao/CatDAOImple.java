package com.project.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.CatVO;

@Repository
public class CatDAOImple implements CatDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertCat(CatVO c) {
		this.sqlSession.insert("Cat.cat_in", c);
	}//글쓰기 저장

	@Override
	public int getListCount(CatVO c) {
		return this.sqlSession.selectOne("Cat.cat_count", c);
	}//검색 전후 레코드 개수

	@Override
	public List<CatVO> getCatList(CatVO c) {
		return this.sqlSession.selectList("Cat.cat_list",c);
	}//검색 전후 글쓰기 전체 목록

	@Override
	public CatVO getCatCont(int cat_no) {
		return this.sqlSession.selectOne("Cat.cat_cont", cat_no);
	}//내용 보기

	@Override
	public void editCat(CatVO c) {
		this.sqlSession.update("Cat.cat_edit",c);
	}//수정

	@Override
	public void delCat(int cat_no) {
		this.sqlSession.delete("Cat.cat_del",cat_no);
	}//삭제

	//고양이(먼치킨)
	@Override
	public int getListCount_mun(CatVO c_mun) {
		return this.sqlSession.selectOne("Cat.cat_mun_count", c_mun);
	}//검색 전후 레코드 개수

	@Override
	public List<CatVO> getCatList_mun(CatVO c_mun) {
		return this.sqlSession.selectList("Cat.cat_mun_list",c_mun);
	}

	//고양이(샴)
	@Override
	public int getListCount_shiam(CatVO c_shiam) {
		return this.sqlSession.selectOne("Cat.cat_shiam_count", c_shiam);
	}

	@Override
	public List<CatVO> getCatList_shiam(CatVO c_shiam) {
		return this.sqlSession.selectList("Cat.cat_shiam_list", c_shiam);
	}

	//고양이(폴드)
	@Override
	public int getListCount_fold(CatVO c_fold) {
		return this.sqlSession.selectOne("Cat.cat_fold_count", c_fold);
	}

	@Override
	public List<CatVO> getCatList_fold(CatVO c_fold) {
		return this.sqlSession.selectList("Cat.cat_fold_list", c_fold);
	}

	//고양이(페르시안)
	@Override
	public int getListCount_persian(CatVO c_persian) {
		return this.sqlSession.selectOne("Cat.cat_persian_count",c_persian);
	}

	@Override
	public List<CatVO> getCatList_persian(CatVO c_persian) {
		return this.sqlSession.selectList("Cat.cat_persian_list", c_persian);
	}
}
