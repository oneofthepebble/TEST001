package com.project.service;

import com.project.dao.NormalServiceDAO;
import com.project.vo.NormalBoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NormalServiceImpl implements NormalService {

    @Autowired
    private NormalServiceDAO normalServiceDAO;

    @Override
    public int insertBoard(NormalBoardVO nbv) {
        return this.normalServiceDAO.insertBoard(nbv);
    }

    @Override
    public int getListCount(NormalBoardVO nbv) {
        return this.normalServiceDAO.getListCount(nbv);
    }

    @Override
    public List<NormalBoardVO> getUserBoardList(NormalBoardVO nbv) {
        return this.normalServiceDAO.getUserBoardList(nbv);
    }

    @Override
    public NormalBoardVO selectnormalboardcont(NormalBoardVO nbv) {
        return this.normalServiceDAO.selectnormalboardcont(nbv);
    }

    @Override
    public int normal_reply_ok(NormalBoardVO nbv) {
        return this.normalServiceDAO.normal_reply_ok(nbv);
    }

    @Override
    public List<NormalBoardVO> getreplylist(NormalBoardVO nbv) {
        return this.normalServiceDAO.getreplylist(nbv);
    }

    @Override
    public void update_hit(NormalBoardVO nbv) {
        this.normalServiceDAO.update_hit(nbv);
    }

    @Override
    public int reply_del(NormalBoardVO nbv) {
        return this.normalServiceDAO.reply_del(nbv);
    }

    @Override
    public int del_board(NormalBoardVO nbv) {
        return this.normalServiceDAO.del_board(nbv);
    }

    @Override
    public void update_board(NormalBoardVO nbv) {
        this.normalServiceDAO.update_board(nbv);
    }
}
