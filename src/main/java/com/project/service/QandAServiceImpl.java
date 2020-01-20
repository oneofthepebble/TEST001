package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dao.QandADOA;
import com.project.vo.QandAVO;

@Service
public class QandAServiceImpl implements QandAService {

	@Autowired
	private QandADOA QandDoa;

	@Override
	public void insertQandA(QandAVO q) {
		this.QandDoa.insertQandA(q);
	}

	@Override
	public int getListCount(QandAVO q) {
		return this.QandDoa.getListCount(q);
	}

	@Override
	public List<QandAVO> getQandAList(QandAVO q) {
		return this.QandDoa.getQandAList(q);
	}

	@Transactional
	@Override
	public QandAVO getQandACont(int q_no) {
		this.QandDoa.updateHit(q_no);//조회수 증가
		return this.QandDoa.getQandACont(q_no);//번호에 해당하는 DB레코드값을 가져옴
	}//트랜잭션 적용

	@Override
	public QandAVO getBbsCont2(int q_no) {
		return this.QandDoa.getQandACont(q_no);
	}//답변폼 + 수정폼 => 조회수 증가 x

	@Transactional
	@Override
	public void replyQandA(QandAVO q) {
		this.QandDoa.QandA_updateLevel(q);//답변 레벨 증가
		this.QandDoa.replyQandA(q);//답변 저장
	}

	@Override
	public void editQandA(QandAVO q) {
		this.QandDoa.editQandA(q);
	}

	@Override
	public void delQandA(int q_no) {
		this.QandDoa.delQandA(q_no);
	}
}
