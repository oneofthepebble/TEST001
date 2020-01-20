create table NormalBoard(
   normal_no number(38) primary key,
   normal_id varchar2(400) constraint B_ID_N references MemberJoin(user_id) on delete cascade ,
   normal_title varchar2(400),
   normal_hit number(38) default 0,
   normal_cont CLOB not null,
   normal_date date,
   normal_ref number(38),
   normal_step number(38)
);
select * from NormalBoard;
alter table NormalBoard drop constraint B_ID_N

insert into NormalBoard (normal_no, normal_id, normal_title, normal_hit, normal_cont, normal_date,normal_ref,normal_step)
 values(seq_normal_no.nextval,'adsfsd','제목','1','내용',sysdate,'1','0');

select normal_id from normalBoard where normal_id = 'asdfsaf' order by normal_no desc;

create sequence seq_normal_no
start with 1
increment by 1
nocache;




select * from
			(select rowNum rNum,normal_title from
				(select * from NormalBoard where normal_id='adsfsd' order by normal_no desc)
				)where rNum >= 1 and rNum <=5

