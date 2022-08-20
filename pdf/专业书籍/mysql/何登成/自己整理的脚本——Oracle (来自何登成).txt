f-1. editplus 正则表达式匹配
http://suo.javaeye.com/blog/506441

f-2. Oracle之FAQ pinner
http://www.360doc.com/content/06/1118/17/1137_264286.shtml


0. --Oracle常用傻瓜问题1000问
http://blog.csdn.net/chinayuan/archive/2008/12/07/3469745.aspx

1. 查看当前锁信息中HW enqueue等待。
select count(*) from v$lock where type='HW';

1.1 查指定时间 BLOCK 变更量
--查询指定时间的snap_id
select snap_id
  from DBA_HIST_SNAPSHOT b
 where b.end_interval_time between       to_date('2011-03-08 02:00:00', 'yyyy-MM-dd HH24:mi:ss') and       to_date('2011-03-08 02:30:00', 'yyyy-MM-dd HH24:mi:ss');

--根据snap_id，查询这段时间的block变更量
select *
  from (select object_name, db_block_changes_delta
          from dba_hist_seg_stat a, dba_objects b
         where snap_id = 24472           and a.obj# = b.object_id
         order by db_block_changes_delta desc
) where rownum < 20;

--session logical reads "逻辑读"
--physical reads,       "物理读"
--redo size             "日志量"
--user rollbacks        "用户 ROLLBACK"
--user commits          "用户 COMMIT"

SELECT SNAP_ID || '-->' || to_char(SNAP_ID - 1) "SNAP_SCOPE",
       to_char(TIME,'mm-dd hh24:mi')time,stat_name "Load Profile",
       round((VALUE - LAG(VALUE)OVER(PARTITION BY stat_name ORDER BY stat_name, time)) /
       (SECONDS - LAG(SECONDS)OVER(
PARTITION BY stat_name ORDER BY stat_name, time)),2) "Per Second"
  FROM (select to_number(b.snap_id) "SNAP_ID",
               b.end_interval_time "TIME",
               stat_name,a.value,
               EXTRACT(HOUR FROM b.end_interval_time) * 3600 +
               EXTRACT(MINUTE FROM b.end_interval_time) * 60 +
               EXTRACT(SECOND FROM b.end_interval_time) "SECONDS"
          from DBA_HIST_SYSSTAT a, DBA_HIST_SNAPSHOT b
         where a.snap_id = b.snap_id
           
and a.instance_number = SYS_CONTEXT('USERENV', 'INSTANCE')
           and b.instance_number = SYS_CONTEXT('USERENV', 'INSTANCE')
           and stat_name in
                    ('session logical reads')
           and b.end_interval_time between
               to_date('2009-03-01 00:00:00', 'yyyy-MM-dd HH24:mi:ss') and
               to_date('2009-03-01 23:00:00', 'yyyy-MM-dd HH24:mi:ss')
         order by stat_name, time)
 ORDER BY "TIME", "Load Profile";

2. 查看当前HW enqueue所对应的sql。
select se.username, sq.sql_text
	from	v$lock l, v$session se, v$sqltext sq
	where	l.sid = se.sid
		and se.sql_hash_value = sq.hash_value
		and l.type = 'HW';

select count(*) from v$lock l, v$session s where l.type='HW' and l.sid = s.sid and s.status='ACTIVE';

3. 查看每个machine下的连接数
	select machine, count(*) from v$session where status = 'ACTIVE' group by machine;

	--修改数据库连接数
	alter system set processes=300 scope=spfile;

	shutdown immediate;
	startup;

	--杀除oracle实例下的远程连接，用于关闭数据库
	ps -ef|grep 'test (LOCAL=NO)'|grep -v grep|awk '{print$2}'|xargs -i kill {}

4. 显示当前undo segment信息
	select name from v$rollstat a ,v$rollname b where a.usn=b.usn;

--查询undo的消耗速度
	select begin_time,end_time,undoblks
	from v$undostat where begin_time>sysdate-1 order by begin_time;
--查询undo的retention时间
	show parameter undo

http://www.cnblogs.com/rootq/archive/2009/04/20/1439860.html

5. 显示undo segment头信息。
select header_file, header_block, blocks from dba_segments
	where segment_name='_SYSSMU10$';

6. 查看数据库中的temp表空间，sort信息。


7. 查看数据库中的锁信息。


8. 查看当前持有锁的session
select t2.username,t2.sid,t2.serial#,t2.logon_time
	from v$locked_object t1,v$session t2
	where t1.session_id=t2.sid order by t2.logon_time;

9. 检查加锁属于哪个表。
select sql_text from v$session a,v$sqltext_with_newlines b
  where DECODE(a.sql_hash_value, 0, prev_hash_value, sql_hash_value)=b.hash_value
  and a.sid=&sid order by piece;

10. 杀指定session
	alter system kill session '111,22222';
	-- 从操作系统杀进程
	select 'kill -9 ' || a.spid from v$process a ,v$session b 
	where a.addr=b.paddr and type='USER' and b.sql_id='19gwmjusr9mnh';


11. drop column 删除列
alter table quot_receive_record drop column receive_status;

12. add column 增加列
alter table quot_receive_record add
(status                        VARCHAR2(10)    DEFAULT 'enable' );

13. modify column 修改列，修改列时，只需要标识出需要修改的模式
ALTER TABLE quot_item_template modify
(title                         VARCHAR2(50)    NOT NULL );

14. 创建sequence，序列
CREATE SEQUENCE seq_quot_reject_distribt START WITH 1;

15. 添加primary key约束，新建索引，然后新建约束
http://space.itpub.net/22238176/viewspace-665361	--Oracle 约束


CREATE INDEX quot_reject_distribt_pk ON quot_reject_distribt (id) TABLESPACE mytbs;
ALTER TABLE quot_reject_distribt ADD CONSTRAINT quot_reject_distribt_pk PRIMARY KEY (id) USING INDEX quot_reject_distribt_pk;

--添加unique约束
CREATE INDEX quot_reject_distribt_uk ON quot_reject_distribt (id) TABLESPACE mytbs;
ALTER TABLE quot_reject_distribt ADD CONSTRAINT quot_reject_distribt_uk unique (id) USING INDEX quot_reject_distribt_uk;

ALTER TABLE quot_reject_distribt ADD CONSTRAINT quot_reject_distribt_uk unique (id) USING INDEX quot_reject_distribt_uk enable novalidate; --不检查已有数据项

--删除约束，保留索引
alter table buyer_info drop constraint BUYER_INFO_MID_UK keep index;	

--禁用，启用约束
alter table bkeep3 disable constraint bkeep3_pk;
alter table bkeep3 modify constraint bkeep3_pk disable;

alter table bkeep3 enable constraint bkeep3_pk;
alter table bkeep3 modify constraint bkeep3_pk enable;

17. 查看日志组
select group#, member from v$logfile;

18. 查看日志组状态
select group#, status from v$log;

19. 给定rowid，查询其所属的file#，block#，rownum#
	select	dbms_rowid.rowid_object(m.rowid) "OBJECT",
		dbms_rowid.rowid_relative_fno(m.rowid) "FILE",
		dbms_rowid.rowid_block_number(m.rowid) "BLOCK",
		dbms_rowid.rowid_row_number(m.rowid) "ROW"
	from member m 
	where rownum = 1;

	--给定rowid，查询所对应的数据项
	select * from test.t1 where rowid = 'AAAXUZAH4AABa6pABE';

	http://hi.baidu.com/bystander1983/blog/item/727e2b12658f8b085aaf53a4.html
	http://blog.csdn.net/lovingprince/archive/2009/04/16/4084786.aspx

	http://www.orawh.com/54.html		--根据rowid模拟并行查询
	--通过 dba_Extents 视图 和 dbms_rowid 来构造 每个 extent的 起止 rowid
		SELECT relative_fno,block_id,
		  Dbms_Rowid.rowid_create(
		  1,
		  b.object_id,
		  relative_fno,
		  block_id,
		  1) AS begin_rowid,
		  Dbms_Rowid.rowid_create(
		  1,
		  b.object_id,
		  relative_fno,
		  block_id,
		  9999) AS end_rowid
		FROM dba_extents a, dba_objects b 
		WHERE segment_name='T1'
		AND a.segment_name=b.object_name
		AND b.object_type='TABLE'
		AND b.owner=a.owner
		AND a.owner='TEST';

	--通过起至rowid来取数据，相当于用于自定义并行
		SELECT /*+rowid(a)*/Count(*) FROM t1 a WHERE ROWID BETWEEN 
		CharToRowid('AAACPSAAgAAAFcJAAB') AND CharToRowid('AAACPSAAgAAAFeJCcP');
	
19.1 并行处理
--查询当前并行进程
select b.QCSID,count(*) from v$session a,v$px_session b where a.sid=b.sid group by qcsid;
--查询并行进程的等待
select sid,sql_text,username, machine,audsid,a.hash_value from v$sql a,v$session b where a.sql_id=b.sql_id and b.sid=&sid;

select a.sid,b.QCSID,a.audsid,a.event from v$session a,v$px_session b where a.sid=b.sid ;

19.2 parallel dml，ddl（并行dml，ddl）
http://www.akadia.com/services/ora_parallel_processing.html	--using oracle's parallel execution features
insert /*+ append parallel(test_parallel,2)*/ into test_parallel select /*+ parallel(t1,2)*/ * from t1;



20。 dump数据文件
alter system dump datafile 5 block min 50 block max 55;
--file# can be find in v$datafile

--dump数据文件（从rowid中dump）
alter system dump datafile dbms_rowid.rowid_relative_fno(p_rowid) block dbms_rowid.rowid_block_number(p_rowid);

--dump library cache
alter system set events 'immediate trace name library_cache level 4';

22. 根据表名获得tablespace名
select table_name, tablespace_name, owner from dba_tables where table_name = upper('t1');

23. 查询有primary key，unique key，foreign key约束，同时没有lob字段的表
--有主键或唯一键，外键，且不含clob的
select a.table_name
	from 
	   (select distinct table_name
		from dba_constraints
		where constraint_type in ('U','P')
		and table_name not in ('t1')
		and owner='test') a,
	   (select distinct table_name
		 from dba_lobs
		where owner='test') b
	where a.table_name=b.table_name(+)
		and b.table_name is null
	order by 1;

--查询外键
	select a.table_name, a.column_name, b.table_name, b.column_name
	  from (select a.constraint_name,
		       b.table_name,
		       b.column_name,
		       a.r_constraint_name
		  from user_constraints a, user_cons_columns b
		 WHERE a.constraint_type = 'R'
		   and a.constraint_name = b.constraint_name) a,
	       (select distinct a.r_constraint_name, b.table_name, b.column_name
		  from user_constraints a, user_cons_columns b
		 WHERE a.constraint_type = 'R'
		   and a.r_constraint_name = b.constraint_name) b
	 where a.r_constraint_name = b.r_constraint_name	

24. 修改数据库系统参数
方案一：
alter system set processes=1000 scope=spfile;

--create pfile from spfile;

注意：在shutdown immediate之前一定要kill掉所有的LOCAL=NO进程
shutdown immediate;
startup;

方案二：
直接修改$ORACLE_HOME/dbs/spfile$SID.ora文件

select * from v$sgastat where pool='shared pool' and name like '%process%';

25. 查看tablespace，以及tablespace下的datafiles
	select ts.name, df.name from v$tablespace ts, v$datafile df
	where ts.ts# = df.ts#
	order by ts.name;

	select ts.name, count(*) from v$tablespace ts, v$datafile df
	where ts.ts# = df.ts#
	group by ts.name
	order by ts.name;

--查询数据文件的使用情况
	SELECT SUBSTR(max(A.TABLESPACE_NAME), 1, 16) "Tablespace",
	       A.FILE_ID "File ID",
	       substr(max(A.file_name), 1, 43) "Data file",
	       substr(max(A.status), 1, 10) "Status",
	       (MAX(A.BYTES) - nvl(sum(B.BYTES), 0)) / 1024 / 1024 "USED SIZE(Mb)",
	       MAX(A.BLOCKS) - nvl(sum(B.BLOCKS), 0) "USED BLOCKS",
	       TO_CHAR((MAX(A.BYTES) - nvl(sum(B.BYTES), 0)) * 100 / MAX(A.BYTES),
		       '999.99') || '%' "USED USAGE",
	       nvl(sum(B.BYTES), 0) / 1024 / 1024 "FREE SIZE(Mb)",
	       nvl(SUM(B.BLOCKS), 0) "FREE BLOCKS",
	       TO_CHAR(nvl(SUM(B.BYTES), 0) * 100 / MAX(A.BYTES), '999.99') || '%' "FREE USAGE",
	       MAX(A.bytes) / 1024 / 1024 "TOTAL SIZE(Mb)",
	       MAX(A.blocks) "TOTAL BLOCKS"
	  from dba_data_files A, DBA_FREE_SPACE B
	 WHERE A.FILE_ID = B.FILE_ID(+)
	 group by a.file_id
	 order by 7;

--resize数据文件

	alter database datafile '/data/oracle10g/oradata/test/system01.dbf' resize 2048M;

26. 查看指定数据文件内是否有extens在dba_extents中
select * from dba_extents where file_id in (103, 104);

27. 使数据文件offline
alter datafile '/opt/oracle/product/.../.dbf' offline;

28. 数据库恢复sql
recover database until cancel
recover database until time '2004-03-21:22:59:04'
recover database until change 123456

recover datafile 'filename' until cancel
recover datafile 'filename' until time '2004-03-21:22:59:04'
recover datafile 'filename' until change 123456

recover tablespace ts_name until cancel
recover tablespace ts_name until time '2004-03-21:22:59:04'
recover tablespace ts_name until change 123456

recover database using backup controlfile


29. 

30. --在线添加索引，更新统计信息
	create index t1_c1_ind on t1
	(c1) tablespace ts1 online;

	--在线rebuild索引，表
	http://wwwwwfco.itpub.net/post/5073/258462							--Online Operations on Indexes and Tables in Oracle9i
	http://hi.baidu.com/danghj/blog/item/541b4b43ef564f179313c6aa.html	--oracle重建索引
	http://space.itpub.net/35489/viewspace-594278						--rebuild index和recreate index的区别
	http://www.ixdba.net/article/47/1604.html							--oracle 9i特性之一在线表格重定义研究 pinner

	alter index idx_test_c1 rebuild online;
	
	--更新指定表上的统计信息，estimate方式，统计表，索引
	http://hi.baidu.com/%CC%EC%C0%B6%D2%ED%D0%C4/blog/item/75007e648dcf76fcf636541e.html	--使用analyze命令收集oracle统计信息，定时分析
	--分析index统计信息(estimate)
	analyze index t1_oid_ind estimate statistics sample 2 percent;
	--分析index统计信息(compute)
	analyze index t1_oid_ind compute statistics;
	--通过索引，单独分析表列，此方法不通
	analyze index t1_oid_ind compute statistics for columns c1;
	--分析全表统计信息，包含索引，索引列
	analyze table t1 estimate statistics sample 5 percent for table for all indexes for all indexed columns;
	--通过表，单独分析表列，此方案可行
	analyze table t1 compute statistics for columns c1;

	analyze table t1 estimate statistics for table for all indexes for all indexed columns;

	exec dbms_stats.gather_table_stats(ownname => 'owner', tabname => 'table_name',estimate_percent => null, method_opt => 'for all indexed columns', cascade => true);

	exec DBMS_STATS.GATHER_TABLE_STATS(OWNNAME => 'test', TABNAME => 't1', ESTIMATE_PERCENT => 10, method_opt =>'FOR ALL INDEXED COLUMNS SIZE 1', no_invalidate=>FALSE, CASCADE => TRUE, degree=>1);
	--也可以让数据库自动选择sample size
	--estimate_percent =>dbms_stats.AUTO_SAMPLE_SIZE


	--设置表的统计信息
	DBMS_STATS.SET_TABLE_STATS ('test','t1',numrows=>10000000,numblks=>50000,avgrlen=>50);
	DBMS_STATS.SET_COLUMN_STATS ('test','t1','c1',distcnt=>10000000,density=>0.0000001,avgclen=>20);
	DBMS_STATS.SET_INDEX_STATS('test','t1_c1_ind',numrows=>10000000,numlblks=>10000,numdist=>10000000,avglblk=>1,avgdblk=>1,clstfct=>50000,indlevel=>3);

	begin
	  dbms_stats.set_column_stats(ownname => 'test',
				      tabname => 't1',
				      colname => 'c1',
				      no_invalidate => FALSE,                           
				      distcnt => 1000000,
				      density => 0.000001);
	end;
	/
	--删除统计信息
	analyze table credit_gnt_account delete statistics;
	exec dbms_stats.delete_table_stats(ownname => 'owner', tabname => 'table_name');

	--查询统计信息是否加锁
	select STATTYPE_LOCKED from dba_tab_statistics where table_name = 't1' and STATTYPE_LOCKED is not null;
	select STATTYPE_LOCKED from dba_tab_statistics where table_name = 't1' and STATTYPE_LOCKED is not null;
	--解锁指定表上的统计信息
	exec dbms_stats.unlock_table_stats('test', 't1');

	http://www.cublog.cn/u/10516/showart_1933890.html
	http://space.itpub.net/8183550/viewspace-666335

	--备份统计信息，导入统计信息
	http://www.cnblogs.com/rootq/archive/2008/12/01/1345197.html	--Oracle 统计信息备份/表分析
	http://space.itpub.net/92530/viewspace-503177					--Oracle 统计信息备份，表分析
	http://www.dbasupport.com/oracle/ora9i/CBO4_6.shtml				--Oracle 9i,Moving to and working with CBO 
	

31. 查看sql的执行计划
explain plan for 

select * from table(dbms_xplan.display);
--此处需要注意的是，explain plan for语句的执行会产生insert动作，而此动作是需要用户手动commit，或者是rollback的
--不然就会导致长事务执行超时。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
--EXPLAIN PLAN is a DML statement and hence you need to COMMIT the transaction explicitly as Oracle does not 
--implicitly commit the changes made by DML statements.

--通过set autotrace的方式
SQL> set autotrace traceonly exp stat;

SQL> select user from dual;


32. 分页查询（两表join）
select count(*) from (
	select b.* from (
		select a.*, ROWNUM as rown from (
			select * from test.t1 where c1 = 'aaaa' 
			order by id
			) a
		where ROWNUM <= 200
	) b
	where b.rown > 180 
) c, test.t2 d
where c.id = d.uid;

--先对表一分页，然后将分页结果与表二进行join，如此大大减少了两表join量。
--分页语句必须使用order by，同时，order by的字段必须是unique值

--快速分页，访问全表数据，可用于全表数据dump，迁移等
--用id来分页，保证每次分页，都访问索引，不至于访问全表
	SELECT *
	  FROM test.t1
	 WHERE id between 111991 and 121991
	   and c_time < to_date('2010-09-20', 'yyyy-mm-dd');

33. --查看指定表的索引信息，constraint信息
select constraint_name, constraint_type from user_constraints where table_name = upper('t1');
select index_name from user_indexes where table_name = upper('t1');
--查看索引的列信息
select index_name, column_name from user_ind_columns where table_name = upper('t1');

34. --删除指定索引，指定表的约束
alter table t1 drop constraint t1_c1_uk;
drop index t1_c1_uk;


35. --统计多列的distinct值
--先group by，然后统计group的个数
select sum(count(*)) as distinct_num from quot_receive_record group by quotation_id, distributor_id, recipient_id;

--将多列连接，然后通过distinct算子计算
select count(distinct quotation_id||distributor_id||recipient_id) distinct_num from quot_receive_record;

36. --oracle连接符 ||
select st_name||'的学号是'||st_no from student;

37. --查看被锁的表信息
SELECT /*+ rule*/ a.sid, b.owner, object_name, object_type 
	FROM v$lock a, all_objects b 
	WHERE TYPE = 'TM' and a.id1 = b.object_id;

38. --查看失效对象，并且重新编译
alter PACKAGE BODY b1 compile;
alter PACKAGE p1 compile;
alter PROCEDURE p1 compile;
alter VIEW v1 compile;
alter TRIGGER t1 compile;

39. --离线查看oracle error错误
oerr ora 01555
oerr tns 12541
oerr eman 202242
oerr exp 00091
oerr NID 131

40. --新建keep cache，并将指定表移动到keep pool中
alter system set db_keep_cache_size=3M;
alter table dual cache storage(buffer_pool keep);

41. --创建同义词
create or replace public synonym dual for mydual;

42. 


43. --收集统计信息
语法：	Analyze		Dbms_utility.analyze_schema	dbms_stats.gather_***_stats

	ANALYZE table tableName {compute|estimate|delete) statistics options
	ANALYZE table indexName {compute|estimate|delete) statistics options
	
	


实例：
	ANALYZE table scott compute statistics;

	analyze table *** estimate statistics 
						for table 
						for all indexes 
						for all indexed columns;

	ANALYZE table scott estimate statistics sample 25 percent;
	ANALYZE table scott estimate statistics sample 1000 rows;
	analyze index sc_idx validate structure;

	exec DBMS_UTILITY.ANALYZE_SCHEMA('SCOTT','COMPUTE');
	exec DBMS_UTILITY.ANALYZE_SCHEMA('SCOTT','ESTIMATE', estimate_rows =>1000);
	exec DBMS_UTILITY.ANALYZE_SCHEMA('SCOTT','ESTIMATE', estimate_percent => 25);
	exec DBMS_UTILITY.ANALYZE_SCHEMA('SCOTT','DELETE');

	EXEC DBMS_STATS.gather_table_stats('SCOTT', 'EMPLOYEES');
	EXEC DBMS_STATS.gather_index_stats('SCOTT', 'EMPLOYEES_PK');
	exec DBMS_STATS.DELETE_SCHEMA_STATS('SCOTT');

44. --获得指定表的average row length
select avg_row_len from user_tables where table_name = 'T1';

45. --定位当前数据库中的blocked sessions信息
select l1.sid, ' IS BLOCKING ', l2.sid
	from v$lock l1, v$lock l2
	where l1.block =1 and l2.request > 0
		and l1.id1=l2.id1
		and l1.id2=l2.id2
/

46. --查询数据库中的隐含参数
--设置隐含参数值
http://www.runningoracle.com/product_info.php?cPath=2_46&products_id=169
ALTER SYSTEM SET "_log_io_size"= 1048576 SCOPE = SPFILE;

不带条件，返回所有隐含参数
	SELECT NAME 
	,value 
	,description 
	FROM ( --GV$SYSTEM_PARAMETER 
	SELECT x.inst_id as instance 
	,x.indx+1 
	,ksppinm as NAME 
	,ksppity 
	,ksppstvl as value 
	,ksppstdf as isdefault 
	,decode(bitand(ksppiflg/256,1),1,'TRUE','FALSE') as ISEM 
	,decode(bitand(ksppiflg/65536,3), 
	1,'IMMEDIATE',2,'DEFERRED','FALSE') as ISYM 
	,decode(bitand(ksppstvf,7),1,'MODIFIED','FALSE') as IMOD 
	,decode(bitand(ksppstvf,2),2,'TRUE','FALSE') as IADJ 
	,ksppdesc as DESCRIPTION 
	FROM x$ksppi x 
	,x$ksppsv y 
	WHERE x.indx = y.indx 
	AND substr(ksppinm,1,1) = '_' 
	AND x.inst_id = USERENV('Instance') 
	) 
	ORDER BY NAME;

指定隐含参数关键字，返回匹配的隐含参数
	set linesize 132
	column name format a30
	column value format a25
	select
	  x.ksppinm  name,
	  y.ksppstvl  value,
	  y.ksppstdf  isdefault,
	  decode(bitand(y.ksppstvf,7),1,'MODIFIED',4,'SYSTEM_MOD','FALSE')  ismod,
	  decode(bitand(y.ksppstvf,2),2,'TRUE','FALSE')  isadj
	from
	  sys.x$ksppi x,
	  sys.x$ksppcv y
	where
	  x.inst_id = userenv('Instance') and
	  y.inst_id = userenv('Instance') and
	  x.indx = y.indx and
	  x.ksppinm like '%_&par%'
	order by
	  translate(x.ksppinm, ' _', ' ')
	/

47. --查看CR块信息
select file#, block#, status, objd 
	from v$bh
	where objd=13389 and status='cr' order by block#;

查询索引所含的object_id号
select object_id, data_object_id, object_type
	from dba_objects 
	where object_name = upper('t1');

48. --建立database link，查询dblink
	create database link l1 connect to test identified by xxxxxxxxxxxxx using 'xxx';

	CREATE PUBLIC DATABASE LINK l2
	  CONNECT TO test IDENTIFIED BY "xxxxxxx" USING '
	  (DESCRIPTION =
	    (ADDRESS_LIST =
	      (ADDRESS = (PROTOCOL = TCP)(HOST = 127.0.0.1)(PORT = 1234))
	    )
	    (CONNECT_DATA =
	      (SID = tsid)
	    )
	  )'
	/

select owner,object_name from dba_objects where object_type='DATABASE LINK';

--dblink的使用
	http://space6212.itpub.net/post/12157/501917	--如何通过dblink取remote db的ddl
	http://lihuiitpub.itpub.net/post/38323/496322	--dblink及字符集转换问题
	
	网络传输关键要以RAW的形式转换。

	create view view_test_raw as select utl_raw.cast_to_raw(name)as name from test_raw;

	其实RAW和VARCHAR是类似的,只是存储在RAW里的是二进制值,在任何时候会做自动的字符集转换,
	这是RAW和VARCHAR的同,RAW只是一种外部类型,其内部存储是VARRAW

	　　VARCHAR的Oracle内部定义是:struct { ub2 len; char arr[n] }

	　　VARRAW的ORACLE内部定义是: struct { ub2 len; unsigned char arr[n] }

	SELECT decode(sorted,
		      1,'CREATE VIEW V_' || table_name || ' AS SELECT ',
		      2,chr(9) || decode(data_type,
					    'VARCHAR2','utl_raw.cast_to_raw(' || column_name ||') AS ' || column_name,
					    'CHAR','utl_raw.cast_to_raw(' || column_name ||') AS ' || column_name,
				       column_name) || decode(rid, 1, NULL, ','),
		      3,'FROM money.' || table_name || ';' || chr(10))
	  FROM (SELECT tc.table_name,tc.column_name,tc.data_type,2 AS sorted,
		       row_number() over(PARTITION BY table_name ORDER BY column_id DESC) AS rid
		  FROM dba_tab_columns tc
		 WHERE owner = 'test'
		
	UNION ALL
		SELECT table_name, '', '', 1, 0
		  FROM dba_tables
		 WHERE owner = 'test'
		UNION ALL
		SELECT table_name, '', '', 3, 0
		  FROM dba_tables
		 WHERE owner = 'test')
	 ORDER BY table_name, sorted, rid DESC;

49. --查询当前session所对应的trace file name
    --启用trace，关闭，查看trace

	SET LINESIZE 100
	COLUMN trace_file FORMAT A60

	SELECT s.sid,
	       s.serial#,
	       pa.value || '/' || LOWER(SYS_CONTEXT('userenv','instance_name')) ||    
	       '_ora_' || p.spid || '.trc' AS trace_file
	FROM   v$session s,
	       v$process p,
	       v$parameter pa
	WHERE  pa.name = 'user_dump_dest'
	AND    s.paddr = p.addr
	AND    s.audsid = SYS_CONTEXT('USERENV', 'SESSIONID');

	alter session set events '10046 trace name context forever, level 12';
	alter session set events '10046 trace name context off';

	tkprof dev1_ora_367660.trc translated.txt explain=test/test table=sys.plan_table sys=no waits=yes

50. --定位job，查看job内容
	select job, what from dba_jobs;								--定位job
	select text from dba_source where name = upper('t1');		--查看job内容

51. --获得指定表的ddl
	select dbms_metadata.get_ddl('TABLE','t1') from dual;
	select dbms_metadata.get_ddl('TABLE','t1','test') from dual;
	

52. --查看数据库监听端口信息
	tnsping service_name	--得到service所对应的sid
	lsnrctl	status		--
	lsnrctl service		--

53. --重命名表名，sequence名，表中字段名
	alter table old_name rename to new_name;
	rename old_seq to new_seq;
	alter table *** rename column *** to ***;

54. --获取表注释信息（comments）
	select table_name, comments
	from dba_tab_comments
	where table_name = &1
	union all
	select table_name, column_name, comments
	from dba_col_comments
	where table_name = &1;

http://www.cnblogs.com/chenleiustc/archive/2009/09/17/1568715.html

55. --Linux 远程copy命令scp
	scp filename user@ip:/target/

56. --定义一个每天晚上零点30运行的job

	http://space.itpub.net/35489/viewspace-586821	--Job不能运行原因分析
	http://space.itpub.net/35489/viewspace-586818	--Oracle Job用法小结
	--方案一,declare 变量
	declare job1 number;
	begin
	dbms_job.submit(job1,'pro_hdc_test_0708;',trunc(sysdate+1)+1/48,'trunc(sysdate+1)+1/48');
	end;
	/

	print job1;

	--在完成submit之后，必须commit;
	commit;	

	--方案二，variable变量
	variable job1 number;
	begin
	dbms_job.submit(
		job =>:job1,
		what=>'pro_hdc_test_0708;',
		next_date=>trunc(sysdate+1)+1/48,
		interval=>'trunc(sysdate+1)+1/48');
	end;
	/

	print :job1;

	commit;

   --手动运行job
	exec dbms_job.run(82);
   --删除job
	exec dbms_job.remove(4);

   http://hi.baidu.com/tangwei_bd/blog/item/0042e846025aeb46500ffe45.html	
   http://www.bitscn.com/pdb/oracle/200604/19366.html

57. --dbms_output.putline过程，调整buffer_size

	set serveroutput on size 100000
	
	dbms_output.enable(999999);

58. --按照时间分组查询（按小时分组查询）
	select to_char(a.c1, 'hh24'), count(*) from test.t1 a group by to_char(a.c1, 'hh24');


59. --flashback query (闪回查询)
	http://www.oracle-base.com/articles/10g/Flashback10g.php
	--查询系统当前scn，timestamp
	select current_scn, to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss') from v$database;
	select systimestamp from dual;

	--指定timestamp，闪回
	select count(*) from flashback_query_test as of timestamp to_timestamp('2004-03-29 13:34:12', 'yyyy-mm-dd hh24:mi:ss');

	--指定scn，闪回
	select count(*) from flashback_query_test as of scn 722452;

	--flashback version query
	select versions_startscn, versions_starttime,
		versions_endscn, versions_endtime,
		versions_xid, versions_operation,
		description
		from flashback_version_query_test
		versions between timestamp *** and ***;

	--flashback table
	flashback table flashback_table_test to scn 715315;
	FLASHBACK TABLE flashback_table_test TO TIMESTAMP TO_TIMESTAMP('2004-03-03 10:00:00', 'YYYY-MM-DD HH:MI:SS');

	--flashback drop
	show recyclebin;
	flashback table flash_back_test to before drop;
	select * from "BIN$TDGqmJZKR8u+Hrc6PGD8kw==$0"; --recyclebin 

60. --Oracle分区操作
	http://space.itpub.net/1698901/viewspace-416205		--Oracle 分区表 Partition table
	http://tianzt.blog.51cto.com/459544/171759			--oracle分区表详解

	--查询分区信息
	select PARTITION_NAME, blocks/1024/1024 MB, high_value, high_value_length,partition_position from dba_tab_partitions where table_name = 't1';	--(是否存在分区)

	--指定分区查询
	select max(event_gmt_occur) from test.t1 partition (t1_200810);

	select TABLE_NAME,tablespace_name,PARTITION_NAME,HIGH_VALUE,PARTITION_POSITION,SUBPARTITION_COUNT from useR_TAB_PARTITIONS where table_name = upper('t1');

	select count(*) from ctu.t1 partition (t1_201010);	--(指定分区查询)
	--查询分区类型，分区键值
	SELECT p.table_name,
	       decode(p.partitioning_key_count, 1, 'main par'),
	       p.partitioning_type,
	       p.column_name,
	       decode(nvl(q.subpartitioning_key_count, 0), 0, 'nonsub', 1, 'sub') sub_par,
	       q.subpartitioning_type,
	       q.column_name
	  FROM (SELECT a.table_name,
		       a.partitioning_type,
		       b.column_name,
		       a.partitioning_key_count
		  FROM all_part_tables a, all_part_key_columns b
		 WHERE a.table_name = b.NAME
		   AND b.object_type = 'TABLE') p,
	       (SELECT a.table_name,
		       a.subpartitioning_type,
		       b.column_name,
		       a.subpartitioning_key_count
		  FROM all_part_tables a, all_subpart_key_columns b
		 WHERE a.table_name = b.NAME
		   AND a.subpartitioning_key_count <> 0
		   AND b.object_type = 'TABLE') q
	 WHERE p.table_name = q.table_name(+)
	 ORDER BY 5, 4, 1;

	--添加分区，子分区
	ALTER TABLE t1 ADD PARTITION t1_201101 VALUES LESS THAN(TO_DATE('2011-04-01 00:00:00','YYYY-MM-DD HH24:MI:SS')) tablespace ts1;

	ALTER TABLE SALES MODIFY PARTITION P3 ADD SUBPARTITION P3SUB1 VALUES('COMPLETE');
	
	--交换分区，重命名表（exchange partition，rename table）
	--exchange partition
	execute immediate ('alter table t1 exchange partition p_all with table t1_tmp including indexes without validation');

	--rename table
	alter table old_name rename to new_name;

	--删除分区
	ALTER table train_part DROP partition acct_p1;

	--失效分区索引
	select 'alter index '||t.index_name||' rebuild partition '||t.partition_name from dba_ind_partitions t where t.index_name='t1_ind'  and t.status='unusable'


61. --动态绑定变量语句
	--动态游标的绑定变量
	declare 
	 msql varchar2(500);
	 mcur number;
	 mstat number;
	 jg varchar2(4000);
	 cg number;
	begin
	 mcur:=dbms_sql.open_cursor;   --注意，此语句千万不能放在循环中
	 msql:='select myid from t4 where myid=:x';
	 for i in 1..5000 loop
	   dbms_sql.parse(mcur,'select myid from t4 where myid=:x',dbms_sql.native);
	   dbms_sql.bind_variable(mcur,':x',i);
	   dbms_sql.define_column(mcur,1,jg,4000);
	   mstat:=dbms_sql.execute(mcur);
	   cg:=dbms_sql.fetch_rows(mcur);
	   dbms_sql.column_value(mcur,1,jg);
	   dbms_output.put_line('查询结果:'||jg);
	  end loop;
	  dbms_sql.close_cursor(mcur);
	end;
	/

62. 查看指定对象上的依赖关系
	select count(*) from dba_dependencies where REFERENCED_NAME = 't1';
	select owner, name, type from dba_dependencies where REFERENCED_NAME = '&1';


63. oracle 字符集问题
	--深入理解（操作系统字符集，客户端字符集，oracle服务器字符集）
	操作系统字符集：	显示字符集，以何种编码显示数据
	客户端字符集：		转换字符集，数据存入服务器，是否需要字符集转换
	oracle服务器字符集：	存储字符集，数据在oracle中，是以何种字符集存储的（例外：如果客户端欺骗了服务器，那么就会违反这一条）

	--消除乱码
	1. 客户端字符集，必须要与操作系统字符集一致。（保证显示的字符集，就是转换后的字符集）
	2. 服务器字符集，必须是客户端字符集的超集。（保证转换前的字符集，能被完全的转换为转换后的字符集）
	3. 例外：将客户端字符集与服务器字符集设置为一致。存储与读取都不需要进行字符集转换。所有的数据，都按照操作系统字符集存入数据库服务器。此时，仅仅需要保证用同样的操作系统字符集读取，就能正确显示。（缺陷在于，服务器中存储的数据，并不是以服务器自身字符集存储，此时如果操作系统字符集设置不对，或是客户端字符集与服务器不一致，都不能保证正确的读取）

	---可以参考"D:\my document\日常工作 脚本\搞懂oracle字符集.pdf"


	NLS_LANGUAGE, NLS_TERRITORY, NLS_CHARACTERSET：语言_地区.字符集
	AMERICAN_AMERICA.ZHS16GBK

	--nls_database_parameters, nls_instance_parameters
	select userenv('language') from dual;

	参考：
	http://www.mldn.cn/articleview/2006-12-27/article_view_114.htm
	http://blog.csdn.net/nini1109/archive/2009/05/07/4158796.aspx
	http://www.heysky.net/archives/2006/01/oracle_32.html

64. 定位oracle中的热点块（hot block）
	--定位热点块所属的segment

	select t1.owner, t1.segment_name "name", t1.segment_type "type"
	from dba_extents t1, x$bh t2
	where t1.file_id = t2.dbarfil
	and t1.owner <> 'SYS'
	and t1.block_id <= t2.dbablk
	and t1.block_id + t1.blocks > t2.dbablk
	and t2.hladdr in (select addr from 
					(select addr, sleeps from v$latch_children order by sleeps desc)
					where rownum <= 40
			)
	/
	
	http://space.itpub.net/35489/viewspace-664252		--Latch,lock,pin
	http://space.itpub.net/8684388/viewspace-617495
	http://blog.csdn.net/wh62592855/archive/2009/11/26/4881492.aspx
	http://go2-www9.appspot.com/_?bG10aHMuNzQ3MjQyMjUyMjE2MDAyLzUyLTIxLTYwMDIvby9iZC9tb2MuODYxdGkuaGNldC8vOnB0dGg=

65. 定位db file sequential read产生的segment
	select b.sid, nvl(substr(a.object_name,1,30),'P1='||b.p1||'P2='||b.p2||'P3='||b.p3) object_name,
		a.subobject_name,
		a.object_type
	from dba_objects a, v$session_wait b, x$bh c
	where c.obj = a.object_id(+)
	and b.p1 = c.file#(+)
	and b.p2 = c.dbablk(+)
	and b.event = 'db file sequential read'

	union

	select b.sid, nvl(substr(a.object_name,1,30),'P1='||b.p1||'P2='||b.p2||'P3='||b.p3) object_name,
		a.subobject_name,
		a.object_type
	from dba_objects a, v$session_wait b, x$bh c
	where c.obj = a.object_id(+)
	and b.p1 = c.file#(+)
	and b.p2 = c.dbablk(+)
	and b.event = 'db file sequential read'
	order by 1;

	http://www.51testing.com/?uid-132585-action-viewspace-itemid-212157

66. 查询数据库中某一文件对象被cache的数据块数（v$bh，x$bh）
	select sum(blocks) from dba_segments where segment_name = '&2';
	
	select count(*) from v$bh where objd = 
	(select data_object_id from dba_objects
	where owner = 'test' and object_name = '&2')
	and status != 'free';

	http://space.itpub.net/55472/viewspace-374950
	http://go2-www9.appspot.com/f?mao2URL=0068007400740070003A002F002F007700770077002E006400620061002D006F007200610063006C0065002E0063006F006D002F006100720074005F006200750069006C006400650072005F0069006F005F00730070006500650064002E00680074006D

67. Linux下批量删除数据
	cd /data/oradata
	ls -Frt|grep ctrdm|wc -l
	ls -Frt|grep ctrdm|head -n 400|xargs rm

	ls -Frt|head -n 15|xargs rm

68. 获得一个对象的last ddl
	select object_name,object_type,to_char(created,'yyyy-mm-dd hh24:mi:ss')created,to_char(last_ddl_time,'yyyy-mm-dd hh24:mi:ss')last_ddl 
	from user_objects where object_name='&1';

69. 物化视图（materialized views）
	--Before I came on the scene as a DBA / Consultant, the previous team had implemented replication of a table / subset by using manual / --scripted methods to copy the table / data. Since the table has been copied last night and there have been no changes at the source since --then, I just CREATE MATERIALIZED VIEW .. PREBUILT and then let Oracle refresh the table "automagically" for me !
	
	create materialized view log on hdc_test_0818;			--创建物化视图日志
	create table hdc_mv_test_0818 as select * from hdc_test_0818;	--创建表结构
	create materialized view hdc_mv_test_0818 on prebuilt table refresh fast as select * from hdc_test_0818;	--创建依赖于现有表的物化视图
	--全量刷新物化视图
	begin
		dbms_mview.refresh(TAB=>'HDC_MV_TEST_0818', METHOD=>'COMPLETE');
	end;
	/
	exec dbms_mview.refresh('HDC_MV_TEST_0818','C');
	--增量刷新物化视图
	exec dbms_mview.refresh('HDC_MV_TEST_0818','F');

	
	http://space.itpub.net/4227/viewspace-242683					--物化视图删除顺序
	http://space.itpub.net/?uid-193161-action-viewspace-itemid-50263		--differences between rowid & primary key materialized view
	http://blog.csdn.net/hdhai9451/archive/2009/02/10/3875172.aspx			--oracle物化视图语法
	http://www.chinaitpower.com/A/2002-05-02/21578.html				--
	http://blog.itpub.net/post/468/13318						--
	http://www.rampant-books.com/art_nanda_fast_regfresh_materialized_views.htm	--


70. long operation(v$session_longops)
	http://www.runningoracle.com/product_info.php?cPath=2_43&products_id=72

	SELECT DISTINCT a.* FROM (SELECT DECODE(TRUNC(SYSDATE - LOGON_TIME), 0, NULL, 
	TRUNC(SYSDATE - LOGON_TIME) || ' Days' || ' + ') ||
	TO_CHAR(TO_DATE(TRUNC(MOD(SYSDATE-LOGON_TIME,1) * 86400), 'SSSSS'), 'HH24:MI:SS') LOGON,
	v$session.SID, v$session.SERIAL#, status, v$session.USERNAME, v$session.osuser, 
	v$session.machine, v$session.module,
	MESSAGE, ROUND((SOFAR/TOTALWORK)*100,2) || '%' PERC,
	DECODE(TRUNC(time_remaining/86400), 0, TO_CHAR(TO_DATE(time_remaining, 'SSSSS'), 'HH24:MI:SS'), 
	TRUNC(time_remaining/86400) || ' Days + '
	|| TO_CHAR(TO_DATE(time_remaining - (TRUNC(time_remaining/86400))*86400 , 'SSSSS'), 'HH24:MI:SS')) REMAINING,
	DECODE(TRUNC(ELAPSED_SECONDS/86400), 0, TO_CHAR(TO_DATE(ELAPSED_SECONDS, 'SSSSS'), 'HH24:MI:SS'), 
	TRUNC(ELAPSED_SECONDS/86400) || ' Days + '
	|| TO_CHAR(TO_DATE(ELAPSED_SECONDS - (TRUNC(ELAPSED_SECONDS/86400))*86400 , 'SSSSS'), 'HH24:MI:SS')) 
	ELAPSED, sql_text FROM v$sql, 
	(SELECT * FROM v$session_longops WHERE TIME_REMAINING > 0) v$session_longops , v$session
	WHERE v$session.SID = v$session_longops.SID
	AND v$session_longops.sql_address = v$sql.address (+)
	AND v$session_longops.sql_hash_value = v$sql.hash_value (+)
	--and v$session.sid = 1644
	--and osuser = 'kaparelis'
	--order by address, hash_value, child_number
	) a;
	http://go2-www9.appspot.com/f?mao2URL=0068007400740070003A002F002F007700770077002E00670070006C00690076006E0061002E00650075002F007000610070006500720073002F0076002400730065007300730069006F006E005F006C006F006E0067006F00700073002E00680074006D

71. 查询线上的ddl trigger，触发器
http://xiaolele.javaeye.com/blog/650670		--oracle触发器
	
	--ddl trigger
	select TRIGGER_NAME,TRIGGER_TYPE,OWNER,STATUS from dba_triggers where trigger_name like '%DDL%';

	select text from dba_source where name = 'triger1';
	
	--限制delete操作
	create or replace trigger tri_del_3
	before delete on t1
	begin   
	      raise_application_error(-20001,'cannot delete data on t1');
	end;  
	/


72. like操作中转译特殊字符（escape）
	A.c1 like '%'||REPLACE(REPLACE(REPLACE(:1,'/','//'),'%','/%'),'_','/_')||'%' escape '/'

73. 查看sql的执行频率，给定sql_id
	select a.snap_id || ',' || to_char(t.snap_time, 'yyyy-mm-dd hh24:mi:ss') || ',' ||
	       executions_total || ',' || buffer_gets_total || ',' ||
	       EXECUTIONS_DELTA || ',' || buffer_Gets_delta || ',' ||
	       trunc(buffer_Gets_delta / decode(EXECUTIONS_DELTA, 0, 1)) as result
	  from DBA_HIST_SQLSTAT a,
	       (select snap_id, END_INTERVAL_TIME as snap_time
		  from dba_hist_snapshot) t
	 where sql_id = '&1'
	   and a.snap_id = t.snap_id
	 order by a.snap_id;

74. 查询数据库中的隐含参数(x$ksppi, x$ksppcv)
	select x.ksppinm, y.ksppstvl, x.ksppdesc
	from x$ksppi x , x$ksppcv y
	where x.indx = y.indx
	and x.ksppinm like '\_%' escape '\'
	and ksppinm like '%_db_block_hash_buckets%';

75. oracle嵌套事务，自治事务
	http://www.blogjava.net/terry-zj/archive/2006/01/27/29303.html


	PRAGMA AUTONOMOUS_TRANSACTION; 

76. 数据导入，导出操作（imp，exp）（expdp impdp）
	http://hi.baidu.com/dba_james/blog/item/8a77d2515a2fc95b1038c29a.html	--impdb小结
	http://space.itpub.net/35489/viewspace-614126				--expdb/impdb用法详解

	http://okone96.itpub.net/post/9033/196742				--Oracle导入程序Imp使用详解
	http://blueicer.blog.51cto.com/395686/101600				--Oracle Exp/Imp的使用详解
	http://hi.baidu.com/joolingkim/blog/item/54b9fcde77ecef19632798c6.html	--oracle下导出某用户所有表的方法
	http://space.itpub.net/22021749/viewspace-660704			--Oracle自定义类型该如何导入

	exp userid=test/xxxx file=t1.dmp log=t1.log tables=wp_image buffer=4096000 feedback=1000000 statistics=none query=\" where rownum \<= 40000000 \"
	imp userid=test/xxxx buffer=40960000 file=t1.dmp fromuser=test touser=test commit=y indexes=n IGNORE=y FEEDBACK=1000000

77. crontab的部分语法
	http://www.360doc.com/content/10/0625/17/16538_35200942.shtml
	http://num7.javaeye.com/blog/267050	--Linux下的crontab的使用
	
	--定期删除日志文件的crontab
	0 * * * * /home/oracle/hdc/deletearch.sh 2>&1 >/dev/null
	
	0 2 * * * find /data/arch/test -xdev -mtime +3 -name "*.dbf" |xargs rm -fr
	
78. 查询某条sql的平均逻辑读（给定sql_id）
	http://space.itpub.net/35489/viewspace-663633	--v$sql,v$sqlarea,v$sqltext区别
	
	--得到b1，e1
	select buffer_gets, executions from v$sql where sql_id = '&1';

	--得到b2，e2
	select buffer_gets, executions from v$sql where sql_id = '&1';

	--得到sql的评价逻辑读
	select (b2 - b1) / (e2 - e1) from dual;

79. 查询某条sql的动态绑定变量（给定sql_id）
	select name, datatype_string, value_string from v$sql_bind_capture where sql_id = '&1';

80. 添加redo日志
	
	--每个redo日志组，可以在线添加，删除组成员，但是必须保证每个组中，至少包含一个成员

	--查看当前日志组
	select * from v$log;

	--查询每个日志组中的日志成员
	select * from v$logfile;

	--添加redo日志组
	alter database add logfile group 4 ('/data/oradata/dd/redo04.log') size 512m;
	alter database add logfile group 5 ('/data/oradata/dd/redo05.log') size 512m;
	
	--查询数据库可以创建多少日志文件（多少数据文件...）
	V$CONTROLFILE_RECORD_SECTION

	select type, records_used, records_total,records_used/records_total*100 "PCT_USED" 
	from sys.v_$controlfile_record_section;



	http://blog.itpub.net/post/41127/494483/

81. connect by 语句使用方法
	http://www.th7.cn/Article/sj/ora/200911/368931.html
	http://blog.csdn.net/meteorlWJ/archive/2008/11/18/3323692.aspx

82. oracle固定执行计划(9i outline, 10g sql profile, 11g baseline)
	http://space.itpub.net/10834762/viewspace-490677
	http://www.databasejournal.com/features/oracle/article.php/3723676/Oracle-Database-11g-SQL-Plan-Management-Part-1.htm
	http://home.ixpub.net/space.php?uid=13651581&do=blog&id=407333

83. physical standby备库
	http://cid-90e3f624bcb77d29.spaces.live.com/blog/cns!90E3F624BCB77D29!162.entry?wa=wsignin1.0&sa=284131892

	--查询备库当前模式
	select open_mode, DATABASE_ROLE,GUARD_STATUS from v$database;

	--查询备库的恢复进度
	select max(checkpoint_time) from v$datafile;

	--查询数据文件的checkpoint
	SELECT FILE# file_nr, TO_CHAR(CHECKPOINT_TIME,'DD/MM/YYYY HH24:MI:SS') checkpoint_time, NAME file_name FROM v$datafile_header;



84. aix系统相关
	--网络设置，检测
	http://www.loveunix.net/thread-103093-1-1.html

	--通过lsdev命令可以看到网卡的个数与类型
	#lsdev -Cc adapter|grep ent

	--查看网卡的IP可以用ifconfig或者是netstat，如
	#ifconfig -a
	#netstat -in

85. oracle数据库错误处理（raise，error）
	http://hi.baidu.com/sygspace/blog/item/b1fbc75c67013c49faf2c071.html
	http://zt19syh2919.blog.163.com/blog/static/54783580200811841147821/


86. oracle时间处理
	http://www.cnblogs.com/fmxyw/archive/2008/08/26/1276850.html

	--to_date函数
	select to_date('2005-01-01 13:14:20','yyyy-MM-dd HH24:mi:ss') from dual;


87. 根据statspack，查询一个sql在一段时间之内的执行情况
	---------查看piner的 ORACLE之FAQ文档-----------------------

	select s.snap_id||','||t.snap_time||','||executions as result
	from perfstat.stats$sql_summary s, perfstat.stats$snapshot t
	where hash_value=3245291238
	and s.snap_id=t.snap_id
	order by t.snap_time

88. 查找被锁定的表
	select t2.sid, t2.machine, t4.spid, t3.* 
	from v$locked_object t1, v$session t2, dba_objects t3, v$process t4
	where t1.session_id = t2.sid and t1.object_id = t3.object_id and t2.paddr = t4.addr;

89. dbms_lob包的使用
	http://www.eygle.com/archives/2005/08/ecieoadbms_lobo.html
	http://space.itpub.net/23065269/viewspace-630417

90. 查询用户权限（DBA_SYS_PRIVS）
	http://junmail.javaeye.com/blog/135331
	--查询用户权限
	select * from DBA_SYS_PRIVS where grantee='test';
	--查询用户所属角色
	select * from dba_role_privs where grantee='test';
	--用户查询权限分析
	http://www.runningoracle.com/product_info.php?products_id=381		--Find user object grants
	--查询用户 表空间 权限
	select 'alter user '||username||' default tablespace '||default_tablespace||';' from dba_users
	where username in ('test');

	select 'alter user '||username||' quota unlimited on '||TABLESPACE_NAME||';' from DBA_TS_QUOTAS
	where username in ('test') order by username;


91. 创建用户，并授权
	create user test identified by test;
	grant connect,resource to test;
	revoke unlimited tablespace from test;
	alter user test quota unlimited on dat1;
	alter user test quota unlimited on dat1;
	alter user test default tablespace dat1;
--解锁用户
	alter user test account unlock;
	alter user test identified by test;
	--查询用户所属profile
	select profile from dba_users where USERNAME = 'test';
	select * from dba_profiles;
	--永不锁定，设置为无数次
	alter profile default limit FAILED_LOGIN_ATTEMPTS unlimited;

92. 使用dd建立文件分区
	dd if=/dev/zero of=swapfile bs=1024k count=xx
	mkswap swapfile
	swapon swapfile

93. 监听器配置（listener.ora & tnsnames.ora）
	http://space.itpub.net/35489/viewspace-673177	--配置网络环境
	http://space.itpub.net/35489/viewspace-566862	--理解sqlnet.ora, tnsnames.ora, listener.ora文件

94. 打开，关闭数据库的操作
	http://space.itpub.net/35489/viewspace-665620	--Oracle shutdown and startup
	http://space.itpub.net/35489/viewspace-612442	--初始化参数和实例

95. 磁盘io性能评估，硬盘繁忙程度
	http://space.itpub.net/35489/viewspace-605118	--磁盘io性能评估

	--查看系统磁盘繁忙程度
	iostat -x 1 10

	--查询系统磁盘读写
	sar -B 1 10

	--查询disk的大小
	fdisk 

96. 自定义类型处理
	http://blog.csdn.net/E_wsq/archive/2008/11/24/3363784.aspx	--oracle自定义类型如何导入？
	http://space.itpub.net/?uid-12932950-action-viewspace-itemid-662514	--oracle 自定义type的几种用法
	--创建
	CREATE OR REPLACE TYPE ID_VARRAY is varray(128) of number;
	--删除
	drop type ID_VARRAY;
	--赋权
	grant all on ID_VARRAY to public;

97. oracle9i处理varchar2中的数字类型（oracle9i不支持正则表达式）
	--全数字
	where translate(id,'x1234567890','x') is null;

	--包含数字
	where length(translate(id,'x1234567890','x'))<>length(id);

	--不包含数字
	where translate(id,'x1234567890','x')=id;

98. oracle 中replace函数与translate函数的区别
	http://dev.firnow.com/course/7_databases/oracle/oraclejs/20100527/205670.html	--oracle replace和translate函数详解
	http://news.newhua.com/news1/program_database/2009/113/09113112913G124D6IKKHD0G9AB5745GC1557FH8IHJK56I4FGKJ31BD.html	--oracle中判断一个字符串是否全部为数字的函数

	translate：以字符级进行替换操作，同时tostring不能为空
	replace：  以字符串级(即整个字符串完全匹配才进行替换)进行替换操作

99. oracle 中的正则表达式（oracle 10g之后）
	http://www.cnblogs.com/gkl0818/archive/2009/02/12/1389521.html	--oracle正则表达式
	http://www.cnblogs.com/gkl0818/archive/2009/02/12/1389533.html	--oracle正则表达式续
	http://blog.163.com/zhyang@yeah/blog/static/130143844201082021429566/	--oracle中的正则表达式（regular expression）


100. oracle logminer使用
--oracle 9i logminer
	http://oracle.chinaitlab.com/induction/725752.html
	http://unix-cd.com/unixcd12/article_6664.html
	http://rake.itpub.net/post/4038/26787				--good
	http://111206wr.blog.chinabyte.com/2010/07/30/148/		--在oracle9i中用logminer分析归档日志文件
	http://www.itpub.net/viewthread.php?tid=380732			--总结logminer使用及各种问题处理
	http://blog.csdn.net/wangtaosuccess/archive/2005/08/11/450750.aspx	--深入分析oracle数据库日志文件
	http://tolywang.itpub.net/post/48/455675			--深入分析oracle日志文件

	0. 准备工作
		--将logmnr的默认表空间，从system移出
		execute dbms_logmnr_d.set_tablespace('ts1');
		--创建数据字典
		execute dbms_logmnr_d.build(
		dictionary_filename => 'ts1_dev01.dbf',
		dictionary_location => '/u03/utl_file_dir');
	
	1. 2个脚本
		$ORACLE_HOME/rdbms/admin/dbmslm.sql   #用来创建DBMS_LOGMNR包，该包用来分析日志文件。
		$ORACLE_HOME/rdbms/admin/dbmslmd.sql  #用来创建DBMS_LOGMNR_D包，该包用来创建数据字典文件。

	2. 3种创建数据字典方式
		1）直接访问数据库中在线数据字典
		2）将数据字典导出为一个文本文件
		3）将数据字典导出到log文件中

	3. 添加要分析的日志文件
		execute dbms_logmnr.add_logfile(
		LogFileName=>'***', Options=>dbms_logmnr.new);

		execute dbms_logmnr.add_logfile(
		LogFileName=>'***', Options=>dbms_logmnr.addfile);

		execute dbms_logmnr.add_logfile(
		LogFileName=>'***', Options=>dbms_logmnr.removefile);

	4. 开始分析
		--无限制条件(使用导出的文本文件数据字典)
		execute dbms_logmnr.start_logmnr(
		DictFileName=>'***');
		--直接使用在线的数据字典
		execute dbms_logmnr.start_logmnr(
		options=>dbms_logmnr.DICT_FROM_ONLINE_CATALOG);
		--使用导出到log文件中的数据字典
		execute dbms_logmnr.start_logmnr(
		options=>dbms_logmnr.DICT_FROM_REDO_LOGS);

		--加入限制条件
		时间范围：对dbms_logmnr.start_logmnr使用StartTime和EndTime参数
		SCN范围：对dbms_logmnr.start_logmnr使用StartScn和EndScn参数
		execute dbms_logmnr.start_logmnr(
		StartTime=>to_date('2001-09-18 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), EndTime=>to_date(), options=>dbms_logmnr.DICT_FROM_ONLINE_CATALOG);

	5. 查看分析结果
		v$logmnr_contents
		select operation, sql_redo, sql_undo from v$logmnr_contents
		where username = '&1' and seg_name = '&2';

	6. 最后，终止日志分析
		execute dbms_logmnr.end_logmnr;


101. 查询，分组，连接
	http://space.itpub.net/13351439/viewspace-470269			--Oracle多行记录合并/连接/聚合字符串的几种方法
	http://www.cnblogs.com/rxie/archive/2010/07/27/1785988.html	--Oracle字符串聚合函数 strcat
	

102. 去除linux文件中的^M字符
	http://dev.firnow.com/course/6_system/linux/Linuxjs/20090517/167195.html	--vim中的替换命令（删除文本中的^M）
	http://lexlan.blog.hexun.com/12130515_d.html								--vim消除文档中的DOS回车换行符以及文件编码转换
	--vi中替换单文件
	1. vi dosfilename
	2. Press Esc 
	3. Enter this string: %s/^M//g  (^M = Ctrl v + Ctrl m)
	4. Press enter  (the ^M cleared!)
	5. :wq! (save the file)

	--批量替换目录下文件中的^M
	--Linux下，^M输入为：Ctrl+v+Enter
	--`find . -type f` 命令两边的字符，为1边上的~
	sed -i "s/oldString//g" `grep oldString -rl /data/tempdata/0215`
	perl -p -i -e "s/^M//g"     `find . -type f`


103. Oracle事件跟踪
	--10053， 10046
	Event 10053 - Dump Optimizer Decisions This event can be used to dump the decisions made by the optimizer when parsing a statement. Level 1 is the most detailed 

	Event 10046 - Enable SQL Statement Trace This event can be used to dump SQL statements executed by a session with execution plans and tatistics. Bind variable and wait statistics can optionally be included. Level 12 is the most detailed.

	--10053	
	http://www.oracle.com.cn/viewthread.php?tid=44302	--深入解析10053事件
	http://space.itpub.net/79499/viewspace-160114		--oracle 10053诊断事件
	开启：
	Alter session set events’10053 trace name context forever[,level {1/2}]’;
	关闭：
	Alter session set events’10053 trace name context off’;
	设置其他session的10053：
	SYS.DBMS_SYSTEM.SET_EV (<sid>, <serial#>, 10053, {1|2}, '')
	SYS.DBMS_SYSTEM.SET_EV (<sid>, <serial#>, 10053,0, '')
	
	--10046
	http://cosio.itpub.net/post/10244/473238	--使用oracle的10046事件跟踪sql语句
	http://logzgh.itpub.net/post/3185/223918	--import_schema_stats报出ora-20000处理
	http://blog.chinaunix.net/u/21220/showart_1810054.html	--跟踪会话使用event10046和tkprof
	http://www.dbabeta.com/2010/ways-to-open-10046-trace.html	--oracle中打开10046 trace的各种方法

	Level 0 Tracing is disabled. This is the same as setting SQL_TRACE = FALSE.
	Level 1 Standard SQL trace information (SQL_TRACE = TRUE). This is the default level.
	Level 4 SQL trace information plus bind variable values.
	Level 8 SQL trace information plus wait event information.
	Level 12 SQL trace information, wait event information, and bind variable values.

	--直接设置10046
	--跟踪本session
	alter session set events '10046 trace name context forever, level 12';
	--跟踪其他session
	select b.spid,a.sid,a.serial#,a.machine from v$session a,v$process b where a.paddr = b.addr and a.machine='SYS_F85';
	execute sys.dbms_system.set_ev(15,196,10046,1,'');
	--停止跟踪
	alter session set events '10046 trace name context off';
	exec dbms_system.set_ev(1082,186,10046,0,'')

	
	--通过oradebug设置10046
	SQL> oradebug setmypid
	SQL> oradebug event 10046 trace name context forever,level 8;
	*******
	SQL> oradebug tracefile_name


	其他SESSION
	SELECT s.username, p.spid os_process_id, p.pid oracle_process_id FROM v$session s, v$process p WHERE s.paddr = p.addr AND s.username = UPPER('&user_name');
	 
	oradebug setospid 12345;

	--查看跟踪的trace文件
	select value from v$parameter where name = 'user_dump_dest';
	tkprof ora9i_ora_24722.trc ora9i_ora_24722.sql

104. RMAN使用
	--备库出错的一些处理方式
	http://blog.csdn.net/panys/archive/2009/01/20/3838846.aspx		--ORA-00257: archiver error. Connect internal only, until freed 错误的处理方法
	http://xchunlnei.blog.sohu.com/149043638.html					--增大闪回恢复区-删除闪回恢复区里的归档日志
	http://blog.chinaunix.net/u3/116463/showart_2321344.html		--ORA-16014: log string sequence# string not archive
	http://space.itpub.net/7607759/viewspace-628518					--RMAN说，我能备份5，RMAN中的字符串定义和CONFIGURE命令
	http://space.itpub.net/10742223/viewspace-345714				--如何删除归档日志文件

	--进入RMAN，删除过期归档
	rman target sys/pass
	rman target / nocatalog
	
	list archivelog all;
	crosscheck archivelog all;
	delete expired archivelog all;
	delete archivelog until time 'sysdate-1';					--删除截止到前一天的所有archivelog
	delete noprompt archivelog until sequence 27971;			--删除到某指定sequence之前的所有归档
	change archivelog until logseq=35760 delete;				--删除指定sequence之前的归档


	--查询flash_recovery_area使用率
	select * from V$FLASH_RECOVERY_AREA_USAGE;

	--查询physical standy使用归档的情况
	select NAME,SEQUENCE#,FIRST_TIME,APPLIED from v$archived_log
	where APPLIED = 'NO';
	
	--设置归档删除策略，强制删除未被APPLIED的归档
	CONFIGURE ARCHIVELOG DELETION POLICY TO APPLIED ON STANDBY;		--可删
	CONFIGURE ARCHIVELOG DELETION POLICY TO NONE;					--归档未applied，不可删

105. 从数据文件恢复数据库
	http://liufei-fir.javaeye.com/blog/795965						--如何从完好的数据文件恢复oracle数据库

106. 取number类型精度（from 叶正盛）
    col_str := '  ' || rs.column_name || ' ' || rs.data_type;
    if rs.data_type in ('CHAR', 'VARCHAR2', 'NVARCHAR2') then
      col_str := col_str || '(' || rs.data_length || ')';
    elsif rs.data_type in ('NUMBER') then
      if rs.data_precision is not null then
        col_str := col_str || '(' || rs.data_precision || ',' ||
                   rs.data_scale || ')';
      end if;
    end if;

107. oracle数据库大页（hugepages配置）
	--优势
	1. Page table大小降低：	cat /proc/meminfo
	2. 提高TLB命中率
	3. 内存不替换，减少替换开销
	4. PGA不能使用huge pages

	http://www.dbafan.com/blog/?tag=linux-oracle				--Linux hugepages
	http://space6212.itpub.net/post/12157/465221				--Hugepages、VLM、SGA和share memory

108. oracle directory，external table（目录，外部表）
	http://hi.baidu.com/endlesslove137/blog/item/63024f2341e9f34aac34de7d.html	--oracle directory使用介绍
	http://www.cuug.com/News_view.asp?NewsID=486								--oracle外部表实现方式集锦

	--diretory创建，查询，赋权
	sqlplus "/as sysdba"
	create or replace directory dir1 as '/data/tempdata/dir1';
	select * from dba_directories;
	grant read, write on directory dir1 to test;
	

109. sql performance analyzer (spa)
	http://download.oracle.com/docs/cd/B28359_01/server.111/e12159/spa.htm		--sql performance analyzer oracle文档
	http://www.oracle-base.com/articles/11g/SqlPerformanceAnalyzer_11gR1.php	--sql performance analyzer in oracle 11g release 1


110. nested tables (行列转换)
	http://blog.csdn.net/you_tube/archive/2009/04/09/4059251.aspx				--oracle行转列

111. 查询sql的执行次数
	select to_char(b.end_interval_time,'dd') as t, 
	   sum(executions_delta) as x
	from dba_hist_sqlstat a, dba_hist_snapshot b
	where sql_id=':1'
	and a.snap_id=b.snap_id
	group by to_char(b.end_interval_time,'dd')
	order by 1;

112. 审计（AUDIT）
http://hi.baidu.com/sonmeika/blog/item/5bdad303110ed17a3912bbbf.html	--Oracle Audit审计

113. 核心应用表dml监控
select table_name from dba_tables where owner='test' and table_name like '%CHG_DATA%' order by 1;

114. 11g倒数据到9i数据库
http://www.orafaq.com/node/2291			--setSegmentation Fault 

115. 通过hash_value定位应用机器
-- 当v$session中过期，可以在open_cursor中查询
select s.username, s.sid, s.serial#, s.saddr, s.machine, s.sql_hash_value
         from v$session s, v$open_cursor o
        where s.sid = o.sid
          and s.saddr = o.saddr
          --and s.SQL_HASH_VALUE = o.HASH_VALUE
          and o.HASH_VALUE = 3851840776;

-- 通过sql_text，查询machine
select s.username, s.sid, s.serial#, s.saddr, s.machine,o.SQL_TEXT
 from v$session s, v$open_cursor o
where s.sid = o.sid
  and s.saddr = o.saddr
  --and s.SQL_HASH_VALUE = o.HASH_VALUE
  and o.SQL_TEXT like '%TRADE_TIMER%';

116. 定位数据库中被锁的表，锁表语句
http://www.runningoracle.com/product_info.php?cPath=2_43&products_id=70

	SELECT /*+ RULE */  
	DECODE(TRUNC(SYSDATE - LOGON_TIME), 0, NULL, TRUNC(SYSDATE - LOGON_TIME) || ' Days' || ' + ') || 
	TO_CHAR(TO_DATE(TRUNC(MOD(SYSDATE-LOGON_TIME,1) * 86400), 'SSSSS'), 'HH24:MI:SS') LOGON,
	       lk.SID, se.serial#, pr.spid pid, se.status, se.username, se.osuser, se.machine,
		   DECODE (lk.TYPE,'TX', 'TRANSACTION', 'TM', 'DML', 'UL', 'PL/SQL User Lock', lk.TYPE) TYPE, 
		   DECODE(lk.TYPE, 'TX','TRANSACTION ROW-LEVEL' , 'TS','TEMPORARY SEGMENT ' , 'TD','TABLE LOCK' , 'TM','ROW LOCK' , lk.TYPE ) lock_TYPE,
		   DECODE (lk.lmode, 0, 'None', 1, 'Null', 2, 'Row-S (SS)', 3, 'Row-X (SX)', 4, 'Share', 5, 'S/Row-X (SSX)', 6, 'Exclusive', TO_CHAR (lk.lmode)) held,
		   DECODE(ob.owner || '.' || ob.object_name, '.', NULL, ob.owner || '.' || ob.object_name) OBJECT,
	       DECODE (lk.request, 0, 'None', 1, 'Null', 2, 'Row-S (SS)', 3, 'Row-X (SX)', 4, 'Share', 5, 'S/Row-X (SSX)', 6, 'Exclusive', TO_CHAR (lk.request)) requested,
	       DECODE(lk.BLOCK, 0, 'NO', 'YES' ) BLOCKER, 
		   'alter system kill session ' || '''' || se.SID || ', ' || se.serial# || '''' || ' immediate;' kill_sql 
	  FROM v$lock lk, dba_objects ob, v$session se, v$process pr
	 WHERE lk.SID = se.SID 
	 AND lk.id1 = ob.object_id (+) 
	 AND se.paddr = pr.addr 
	 AND lk.TYPE IN ('TM', 'UL', 'TX')  --User type locks
	--AND se.osuser = '' 
	--and se.username = 'ATHINA'
	--AND se.status = 'INACTIVE'
	-- AND lk.lmode = 2
	--AND ob.object_name LIKE 'PA_PROJECT_ASSETS_ALL%'
	AND ob.object_type = 'TABLE' 
	--AND owner = 'AR'
	ORDER BY LOGON DESC;

-- 在数据库中，定位被锁的存储过程，function
	SELECT a.SID, a.serial#, a.status, a.username, a.osuser, b.owner,a.program, b.OBJECT, b.TYPE,
	'alter system kill session ' || ''''||a.SID || ', ' || a.serial# || '''' || ' immediate;' sql_kill
	FROM v$session a, v$access b
	WHERE a.SID = b.SID
	AND b.OBJECT LIKE 'INSERT_KOSTOS_ETOS_ANA_YPHR%'
	--AND owner = 'HF2006'
	--AND b.TYPE LIKE 'PACKAGE%'
	--AND status = 'ACTIVE'
	;

117. 查询一段时间之内的等待事件 （10g以上有效）
col event for a30
col SAMPLE_TIME for a40
col MODULE for a25
select SAMPLE_ID,SAMPLE_TIME,session_id,USER_ID,event,p3,module  from DBA_HIST_ACTIVE_SESS_HISTORY where 
   SAMPLE_TIME>=to_date('2011-05-09 13:50:00','yyyy-mm-dd hh24:mi:ss') and SAMPLE_TIME<=to_date('2011-05-09 14:05:00','yyyy-mm-dd hh24:mi:ss') and INSTANCE_NUMBER=1 order by sample_time;


col event for a30
col SAMPLE_TIME for a40
col MODULE for a25
select SAMPLE_ID,SAMPLE_TIME,session_id,USER_ID,event,p3,module  from V$ACTIVE_SESSION_HISTORY  where 
   SAMPLE_TIME>=to_date('2011-05-09 17:20:00','yyyy-mm-dd hh24:mi:ss') and SAMPLE_TIME<=to_date('2011-05-09 18:05:00','yyyy-mm-dd hh24:mi:ss')  order by sample_time;

118. 函数索引对应的实际列信息
col table_name format a25
col index_name format a30
col column_name format a20
col tablespace_name format a15
col index_type format a22
col column_expression format a20
col column_position heading 'COLUMN|POSITION'
select a.table_name,a.index_name,a.column_name,b.tablespace_name,b.index_type,c.column_expression
from dba_ind_columns a,dba_indexes b,dba_ind_expressions c
where a.index_name = b.index_name
and a.index_name=c.index_name(+)
and a.table_name=c.table_name(+)
and a.column_position=c.column_position(+)
and a.table_name = upper('&table_name')
order by a.index_name,a.column_position
/

119. 11g里看一个库里是否有全表扫描，以及这些表的大小。
col object_owner for a10
col object_name for a30
select sql_id, trunc(s/z) as exec_daily, object_owner, object_name,
   (select sum(bytes)/1024/1024 from dba_segments w 
   where w.segment_name=n.object_name and w.owner=n.object_owner) as Msize
from
(select a.sql_id, object_name, object_owner, 
   (select sum(executions_delta) from dba_hist_sqlstat b where b.sql_id=a.sql_id) as s,
   (select count(distinct to_char(c.begin_interval_time,'yyyy-mm-dd')) 
   from dba_hist_sqlstat b, dba_hist_snapshot c
   where b.sql_id=a.sql_id
   and b.snap_id=c.snap_id) as z
from
(select distinct sql_id, object_owner, object_name
from DBA_HIST_SQL_PLAN
where OBJECT_OWNER='test'
and OPERATION='TABLE ACCESS'
and options='FULL') a) n
where z>0
and s/z>100
order by exec_daily;

120. supplemental log
	unconditional:	不管指定的字段是否变化了，都会在变更记录中记录该字段的前映像。
	conditional:	只记录发生变更的字段，不发生变化的就不记录其前映像。
	第一种方式可以通过：
	alter table tab_name add supplemental log group splog_tabname(column_name)always;
	第二种方式去掉always关键字即可。
	以上内容，可以通过dba_log_groups和dba_log_group_columns两个视图来查询。

121. 数据去重
DELETE FROM WIZARD E 
WHERE E.ROWID > (SELECT MIN(X.ROWID) 
		FROM WIZARD X 
		WHERE X.VACCOUNT_ID = E.VACCOUNT_ID
		and X.WIZARD_NAME = E.WIZARD_NAME);

select id from (
	select id,row_number() over(partition by c1 order by id ) rn from tab
) where rn >1;