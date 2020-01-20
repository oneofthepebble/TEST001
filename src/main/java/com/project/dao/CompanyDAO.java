package com.project.dao;

import java.util.List;

import com.project.vo.CompanyVO;

public interface CompanyDAO {

	List selectCompany_name();

	int insertCompany(CompanyVO companyVO);

	CompanyVO selectCompany_info(CompanyVO companyVO);

}
