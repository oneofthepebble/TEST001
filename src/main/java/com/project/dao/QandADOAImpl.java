package com.project.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.QandAVO;

@Repository
public class QandADOAImpl implements QandADOA {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertQandA(QandAVO q) {
		this.sqlSession.insert("QandA.QandA_in",q);
	}//게시판 저장

	@Override
	public int getListCount(QandAVO q) {
		return this.sqlSession.selectOne("QandA.QandA_count",q);
	}//검색 전후 레코드 개수

	@Override
	public List<QandAVO> getQandAList(QandAVO q) {
		return this.sqlSession.selectList("QandA.QandA_list",q);
	}//검색전후 자료실 전체목록

	@Override
	public void updateHit(int q_no) {
		this.sqlSession.update("QandA.QandA_hit",q_no);
	}//조회수 증가

	@Override
	public QandAVO getQandACont(int q_no) {
		return this.sqlSession.selectOne("QandA.QandA_cont",q_no);
	}//내용보기

	@Override
	public void QandA_updateLevel(QandAVO q) {
		this.sqlSession.update("QandA.QandA_level_up",q);
	}//답변글 레벨 증가

	@Override
	public void replyQandA(QandAVO q) {
		this.sqlSession.insert("QandA.QandA_reply_in", q);
	}//답변글 저장

	@Override
	public void editQandA(QandAVO q) {
		this.sqlSession.update("QandA.QandA_edit", q);
	}

	@Override
	public void delQandA(int q_no) {
		this.sqlSession.delete("QandA.QandA_del", q_no);
	}
}
