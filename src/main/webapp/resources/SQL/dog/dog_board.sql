--dog_board 자료실 테이블 생성
create table dog_board(
 dog_no int primary key -- 게시물 번호
 ,dog_title varchar2(200) not null -- 고양이 종류(제목)
 ,dog_cont varchar2(4000) not null -- 내용
 ,dog_file varchar2(200) not null -- 첨부파일
);

--dog_cont2 임시 컬럼 추가
alter table dog_board add (dog_cont2 CLOB)
--dog_cont2 임시 컬럼에 기존 컬럼 dog_cont 값을 복사
update dog_board set dog_cont2=dog_cont
--기존 컬럼 dog_cont 삭제
alter table dog_board drop column dog_cont
--임시컬럼 dog_cont2를 기존컬럼 dog_cont로 변경
ALTER TABLE dog_board RENAME COLUMN dog_cont2 TO dog_cont;

select * from dog_board order by dog_no desc

--dog_no_seq 시퀀스 생성
create sequence dog_no_seq
start with 1
increment by 1
nocache;

--bbs_no_seq 시퀀스 다음번호값 확인
select dog_no_seq.nextval from dual;

select * from dog_board 

