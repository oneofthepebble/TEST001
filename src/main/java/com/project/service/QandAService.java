package com.project.service;

import java.util.List;

import com.project.vo.QandAVO;

public interface QandAService {

	void insertQandA(QandAVO q);

	int getListCount(QandAVO q);

	List<QandAVO> getQandAList(QandAVO q);

	QandAVO getQandACont(int q_no);

	QandAVO getBbsCont2(int q_no);

	void replyQandA(QandAVO q);

	void editQandA(QandAVO q);

	void delQandA(int q_no);

}
