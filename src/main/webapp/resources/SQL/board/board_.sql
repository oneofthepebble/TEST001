create table BOARD
(
    back_end_list_no NUMBER(38)         not null
        primary key,
    back_end_list_img   VARCHAR2(4000) not null,
    back_end_list_title VARCHAR2(400)  not null,
    back_end_list_cont  VARCHAR2(4000) not null,
    back_end_list_id    VARCHAR2(100)  not null
        constraint MV_ID_B
            references MemberJoin(user_id) on delete cascade,
    back_end_list_date  DATE,
    step                NUMBER,
    ref                 NUMBER
);


drop table BOARD;
delete from BOARD where BACK_END_LIST_ID='dlrudgns90'


insert into Board (BACK_END_LIST_NO, BACK_END_LIST_IMG, BACK_END_LIST_TITLE, BACK_END_LIST_CONT,BACK_END_LIST_ID,BACK_END_LIST_DATE,STEP,REF)
values (back_end_list_no_seq.nextval, '이미지','제목','내용','adsfsd',sysdate,'0',back_end_list_no_seq.nextval);

alter table BOARD drop constraint FOUR.MEMVER_ID_Q

select * from board

create sequence back_end_list_no_seq
start with 1
increment by 1
nocache;


