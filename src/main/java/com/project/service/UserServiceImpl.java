package com.project.service;

import java.util.List;

import com.project.dao.UserDAO;
import com.project.vo.BoardVO;
import com.project.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDao;

    @Override
    public int getListCount(BoardVO b) {
        return this.userDao.getListCount(b);
    }

	@Override
	public List<BoardVO> getUserBoardList(BoardVO a) {
		return this.userDao.getUserBoardList(a);
	}

//    @Override
//    public List<BoardVO> getAdminBoardList(AdminVO a) {
//        return this.userDao.getAdminBoardList(a);
//    }

    //로그인
    @Override
    public MemberVO select_id_pwd(MemberVO m) {
        //System.out.println("아이디값2"+u.getUser_id());
        return this.userDao.select_id_pwd(m);
    }

    //번호인증
    @Override
    public MemberVO phone_check(String phone) {
        return this.userDao.phone_check(phone);
    }

    //아이디 찾기
    @Override
    public MemberVO select_name_phone(MemberVO m) {
        return this.userDao.select_name_phone(m);
    }

    //비번 찾기
    @Override
    public MemberVO select_id_name_phone(MemberVO m) {
        return this.userDao.select_id_name_phone(m);
    }

    //임시비번 발생(쿼리문 업데이트
    @Override
    public int pwdUPdate(MemberVO uv) {
        return this.userDao.pwdUPdate(uv);
    }

    //분양게시판 글쓰기
    @Override
    public int board_insert(BoardVO b) {
        return this.userDao.board_insert(b);
    }

    @Override
    public BoardVO select_board(BoardVO b) {
        return this.userDao.select_board(b);
    }

    @Override
    public int insert_reply(BoardVO b) {
        return this.userDao.insert_reply(b);
    }

    @Override
    public List<BoardVO> get_select_reply(BoardVO b) {
        return this.userDao.get_select_reply(b);
    }

    @Override
    public void del_user_board(BoardVO b) {
        this.userDao.del_user_board(b);
    }

    @Override
    public void update_board(BoardVO b) {
        this.userDao.update_board(b);
    }

    @Override
    public void user_reply_update(BoardVO b) {
        this.userDao.user_reply_update(b);
    }

    @Override
    public void user_reply_del(BoardVO b) {
        this.userDao.user_reply_del(b);
    }


}
