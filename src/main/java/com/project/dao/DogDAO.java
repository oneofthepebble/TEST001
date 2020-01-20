package com.project.dao;

import java.util.List;

import com.project.vo.DogVO;

public interface DogDAO {

	void insertDog(DogVO d);

	int getListCount(DogVO d);

	List<DogVO> getDogList(DogVO d);

	DogVO getDogCont(int dog_no);

	void editDog(DogVO d);

	void delDog(int dog_no);

	//강아지(시츄)
	int getListCount_shih(DogVO d_shih);

	List<DogVO> getDogList_shih(DogVO d_shih);

	//강아지(말티즈)
	int getListCount_mal(DogVO d_mal);

	List<DogVO> getDogList_mal(DogVO d_mal);

	int getListCount_poodle(DogVO d_poodle);

	List<DogVO> getDogList_poodle(DogVO d_poodle);

}
