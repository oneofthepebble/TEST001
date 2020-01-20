package com.project.service;

import java.util.List;

import com.project.vo.BoardVO;
import com.project.vo.MemberVO;
import com.project.vo.NormalBoardVO;

public interface MemberJoinService {

	int insert(MemberVO mb);

	String SearchID(String checkwords);

	MemberVO MypageView(MemberVO mVo);

	int UserInfoEmail(MemberVO updateMember);

	int UserInfoPhone(MemberVO updateMember);

	int UserInfoPwd(MemberVO updateMember);

	List<NormalBoardVO> NormalBoardView(MemberVO mVo);

	List<BoardVO> BoardView(MemberVO mVo);

}
