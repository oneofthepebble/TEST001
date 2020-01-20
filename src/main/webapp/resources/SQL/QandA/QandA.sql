--q_board 게시판 테이블 생성
create table q_board(
	q_no int primary key
	,q_id varchar2(200) not null constraint member_id_q references MemberJoin on delete cascade
	,q_title varchar2(200) not null --제목
	,q_cont CLOB not null --내용
	,q_hit int default 0 --조회수
	,q_ref int --원본글과 답변글을 묶어주는 글그룹번호
	,q_step int --원본글이면 0, 첫번째 답변글이면 1, 두번째 답변글이면 2
	,q_level int --답변글 정렬순서
	,q_date date --등록날짜
);

alter table q_board drop constraint memver_id_q

select * from q_board order by q_no desc;

drop table q_board

--QandA_no_seq 시퀀스 생성
create sequence q_no_seq
start with 1
increment by 1
nocache;

--시퀀스 삭제
drop sequence q_no_seq;

--QandA_no_seq 시퀀스 다음번호값 확인
select q_no_seq.nextval from dual;

--참조키 제약조건 확인 위한 테이블
create table q_name(
	qanda_name varchar2(20) primary key --이름
);
insert into q_name (qanda_name) values('이경훈')
select * from q_name

--참조키 제약조건 생성
alter table QandA
add constraint QandA_name_fk
foreign key (QandA_name) references q_name(qanda_name) on delete cascade;

--참조키 제약조건 삭제
alter table QandA drop constraint QandA_name_fk

