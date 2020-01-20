package com.project.service;

import java.util.List;

import com.project.vo.CompanyVO;

public interface CompanyService {

	List selectCompany_name();

	int insertCompany(CompanyVO companyVO);

	CompanyVO selectCompany_info(CompanyVO companyVO);

}
