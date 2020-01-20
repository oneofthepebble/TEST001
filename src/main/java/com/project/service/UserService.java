package com.project.service;

import com.project.vo.BoardVO;
import com.project.vo.MemberVO;

import java.util.List;

public interface UserService {

    int getListCount(BoardVO b);

	List<BoardVO> getUserBoardList(BoardVO a);

//    List<BoardVO> getAdminBoardList(AdminVO a);

   MemberVO select_id_pwd(MemberVO m);

    MemberVO phone_check(String phone);

    MemberVO select_name_phone(MemberVO m);

    MemberVO select_id_name_phone(MemberVO m);

    int pwdUPdate(MemberVO uv);

    int board_insert(BoardVO b);

    BoardVO select_board(BoardVO b);

    int insert_reply(BoardVO b);

    List<BoardVO> get_select_reply(BoardVO b);

    void del_user_board(BoardVO b);

    void update_board(BoardVO b);

    void user_reply_update(BoardVO b);

    void user_reply_del(BoardVO b);
}
