package com.project.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.CompanyVO;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

	@Autowired
	private SqlSession sqlSession;
	@Override
	public List selectCompany_name() {
		return sqlSession.selectList("Company.name_list");
	}
	@Override
	public int insertCompany(CompanyVO companyVO) {
		return sqlSession.insert("Company.insertCompany", companyVO);
	}
	@Override
	public CompanyVO selectCompany_info(CompanyVO companyVO) {
		return sqlSession.selectOne("Company.CompanyInfo", companyVO);
	}

}
