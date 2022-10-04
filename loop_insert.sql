DELETE from board;

create procedure loop_insert(IN cnt int)
begin 
	declare i int default 1;
	while (i <= cnt) do
		insert into board (title, userid, contents)
		values ('aaa제목(sample1)','aaa아이디(sample1)','aaa 본문 내용 샘플 (sample1)');
		insert into board (title, userid, contents)
		values ('bbb제목(sample2)','bbb아이디(sample2)','bbb 본문 내용 샘플 (sample2)');
		insert into board (title, userid, contents)
		values ('ccc제목(sample3)','ccc아이디(sample3)','ccc 본문 내용 샘플 (sample3)');
		set i = i + 1;	
	end while;
end;

call loop_insert(221004);