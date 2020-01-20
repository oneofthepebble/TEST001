package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.DogDAO;
import com.project.vo.DogVO;

import oracle.net.aso.d;


@Service
public class DogServiceImpl implements DogService {

	@Autowired
	private DogDAO dogDao;
	
	@Override
	public void insertDog(DogVO d) {
		this.dogDao.insertDog(d);
	}

	@Override
	public int getListCount(DogVO d) {
		return this.dogDao.getListCount(d);
	}

	@Override
	public List<DogVO> getDogList(DogVO d) {
		return this.dogDao.getDogList(d);
	}

	@Override
	public DogVO getDogCont(int dog_no) {
		return this.dogDao.getDogCont(dog_no);
	}

	@Override
	public DogVO getDogCont2(int dog_no) {
		return this.dogDao.getDogCont(dog_no);
	}//수정폼 + 삭제폼

	@Override
	public void editDog(DogVO d) {
		this.dogDao.editDog(d);
	}//수정

	@Override
	public void delDog(int dog_no) {
		this.dogDao.delDog(dog_no);
	}//삭제

	//시츄
	@Override
	public int getListCount_shih(DogVO d_shih) {
		return this.dogDao.getListCount_shih(d_shih);
	}

	@Override
	public List<DogVO> getDogList_shih(DogVO d_shih) {
		return this.dogDao.getDogList_shih(d_shih);
	}

	//말티즈
	@Override
	public int getListCount_mal(DogVO d_mal) {
		return this.dogDao.getListCount_mal(d_mal);
	}

	@Override
	public List<DogVO> getDogList_mal(DogVO d_mal) {
		return this.dogDao.getDogList_mal(d_mal);
	}

	@Override
	public int getListCount_poodle(DogVO d_poodle) {
		return this.dogDao.getListCount_poodle(d_poodle);
	}

	@Override
	public List<DogVO> getDogList_poodle(DogVO d_poodle) {
		return this.dogDao.getDogList_poodle(d_poodle);
	}
}
