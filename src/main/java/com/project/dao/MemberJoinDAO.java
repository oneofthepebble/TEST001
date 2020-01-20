package com.project.dao;

import java.util.List;

import com.project.vo.BoardVO;
import com.project.vo.MemberVO;
import com.project.vo.NormalBoardVO;

public interface MemberJoinDAO {

	int insert(MemberVO mb);

	String SearchID(String searchwords);

	MemberVO MypageView(MemberVO mVo);

	int UserInfoEmail(MemberVO updateMember);

	int UserInfoPhone(MemberVO updateMember);

	int UserInfoPwd(MemberVO updateMember);

	List<NormalBoardVO> NormalBoardView(MemberVO mVo);

	List<BoardVO> BoardView(MemberVO mVo);
	
	

}
