package com.project.dao;

import java.util.List;

import com.project.vo.QandAVO;

public interface QandADOA {

	void insertQandA(QandAVO q);

	int getListCount(QandAVO q);

	List<QandAVO> getQandAList(QandAVO q);

	void updateHit(int q_no);

	QandAVO getQandACont(int q_no);

	void QandA_updateLevel(QandAVO q);

	void replyQandA(QandAVO q);

	void editQandA(QandAVO q);

	void delQandA(int q_no);

}
