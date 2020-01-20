package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.CompanyDAO;
import com.project.vo.CompanyVO;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDAO companyDAO;
	
	@Override
	public List selectCompany_name() {
		return companyDAO.selectCompany_name();
	}

	@Override
	public int insertCompany(CompanyVO companyVO) {
		return companyDAO.insertCompany(companyVO);
	}

	@Override
	public CompanyVO selectCompany_info(CompanyVO companyVO) {
		return companyDAO.selectCompany_info(companyVO);
	}


}
