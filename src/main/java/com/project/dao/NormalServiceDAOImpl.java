package com.project.dao;

import com.project.vo.NormalBoardVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NormalServiceDAOImpl implements NormalServiceDAO{

    @Autowired
    private SqlSession sqlSession;

    //글쓰기 저장
    @Override
    public int insertBoard(NormalBoardVO nbv) {
        return this.sqlSession.insert("insert",nbv);
    }

    //게시글 본글 갯수
    @Override
    public int getListCount(NormalBoardVO nbv) {
        return this.sqlSession.selectOne("count",nbv);
    }

    //본글 리스트
    @Override
    public List<NormalBoardVO> getUserBoardList(NormalBoardVO nbv) {
        return this.sqlSession.selectList("board_list",nbv);
    }

    //내용 불러오기
    @Override
    public NormalBoardVO selectnormalboardcont(NormalBoardVO nbv) {
        return this.sqlSession.selectOne("normal_cont",nbv);
    }

    //댓글저장
    @Override
    public int normal_reply_ok(NormalBoardVO nbv) {
        return this.sqlSession.insert("reply_in",nbv);
    }

    //댓글목록
    @Override
    public List<NormalBoardVO> getreplylist(NormalBoardVO nbv) {
        return this.sqlSession.selectList("reply_list",nbv);
    }

    //조회수증가
    @Override
    public void update_hit(NormalBoardVO nbv) {
        this.sqlSession.update("hit_up",nbv);
    }

    @Override
    public int reply_del(NormalBoardVO nbv) {
        return this.sqlSession.delete("reply_del",nbv);
    }

    @Override
    public int del_board(NormalBoardVO nbv) {
        return this.sqlSession.delete("del_board",nbv);
    }

    @Override
    public void update_board(NormalBoardVO nbv) {
        this.sqlSession.update("update_board_ok",nbv);
    }
}
