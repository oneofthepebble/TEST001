package com.project.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.BoardVO;
import com.project.vo.MemberVO;
import com.project.vo.NormalBoardVO;

@Repository
public class MemberJoinDAOImpl implements MemberJoinDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int insert(MemberVO mb) {
		return sqlSession.insert("MemberJoin.MbJoin", mb);
	}

	@Override
	public String SearchID(String checkwords) {
		System.out.println("DAO에서 Query에 값 전달 : "+checkwords);
		return sqlSession.selectOne("MemberJoin.SearchID", checkwords);		
	}

	@Override
	public MemberVO MypageView(MemberVO mVo) {
		return sqlSession.selectOne("MemberJoin.MyPageInfo", mVo);
	}

	@Override
	public int UserInfoEmail(MemberVO updateMember) {
		System.out.println(updateMember.getUser_id()+"/"+updateMember.getUser_email());
		return sqlSession.update("MemberJoin.updateEmail", updateMember);
	}

	@Override
	public int UserInfoPhone(MemberVO updateMember) {
		return sqlSession.update("MemberJoin.updatePN",updateMember);
	}

	@Override
	public int UserInfoPwd(MemberVO updateMember) {
		return sqlSession.update("MemberJoin.updatePWD",updateMember);
	}

	@Override
	public List<NormalBoardVO> NormalBoardView(MemberVO mVo) {
		return sqlSession.selectList("MemberJoin.NBView", mVo);
	}

	@Override
	public List<BoardVO> BoardView(MemberVO mVo) {
		return sqlSession.selectList("MemberJoin.BView", mVo);
	}

}
