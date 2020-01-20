package com.project.dao;


import java.util.List;

import com.project.vo.BoardVO;
import com.project.vo.MemberVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public int getListCount(BoardVO b) {
        return this.sqlSession.selectOne("list_count",b);
    }

    @Override
    public List<BoardVO> getUserBoardList(BoardVO a) {
        return this.sqlSession.selectList("user_list",a);
    }

    //    유저게시판을 관리자 딴에서 봐야하기때문에 만듬
//    @Override
//    public List<BoardVO> getAdminBoardList(AdminVO a) {
//        return this.sqlSession.selectList("admin_list",a);
//    }

    //아이디 비번 존재여부
    @Override
    public MemberVO select_id_pwd(MemberVO m) {
        return this.sqlSession.selectOne("user_id_check",m);
    }

    //휴대폰번호 확인
    @Override
    public MemberVO phone_check(String phone) {
        return this.sqlSession.selectOne("phone",phone);
    }

    //아이디찾기 이름과 폰번으로 확인
    @Override
    public MemberVO select_name_phone(MemberVO m) {
        return this.sqlSession.selectOne("find_name_phone",m);
    }

    //비번찾기 아이디 이름 폰번호 확인
    @Override
    public MemberVO select_id_name_phone(MemberVO m) {
        return this.sqlSession.selectOne("find_id_name_phone",m);
    }

    //임시비번 발생 (쿼리문으로 update 사용)=비번 임시비번
    @Override
    public int pwdUPdate(MemberVO uv) {
        return this.sqlSession.update("random_pwd",uv);
    }

    //글쓰기
    @Override
    public int board_insert(BoardVO b) {
        return this.sqlSession.insert("board_insert",b);
    }

    //내용 보기
    @Override
    public BoardVO select_board(BoardVO b) {
        return this.sqlSession.selectOne("select_my_board",b);
    }

    //댓글저장
    @Override
    public int insert_reply(BoardVO b) {
        return this.sqlSession.insert("insert_reply",b);
    }

    //댓글 목록 가져오기
    @Override
    public List<BoardVO> get_select_reply(BoardVO b) {
        return this.sqlSession.selectList("get_reply",b);
    }

    @Override
    public void del_user_board(BoardVO b) {
        this.sqlSession.delete("user_board_del",b);
    }

    @Override
    public void update_board(BoardVO b) {
        this.sqlSession.update("user_board_update",b);
    }

    @Override
    public void user_reply_update(BoardVO b) {
        this.sqlSession.update("user_reply_board_up",b);
    }

    @Override
    public void user_reply_del(BoardVO b) {
        this.sqlSession.delete("user_reply_del",b);
    }


}
