-- ============================== 初始化数据库表结构 ==============================
-- 创建作业4数据库、设置编码并使用该库
CREATE DATABASE db401 CHARACTER SET utf8;
USE db401;

-- 创建用户表
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userName VARCHAR(20) UNIQUE, -- 学生的等于学号
    pwd VARCHAR(20)
);

-- 创建班级表 班级名称、年级、班主任名称、班级口号 、班级人数
CREATE TABLE class (
    id INT PRIMARY KEY AUTO_INCREMENT,
	cName VARCHAR(20),
	grade INT,
	teacher VARCHAR(20),
	slogan VARCHAR(100),
	size INT
);

-- 创建学生表
CREATE TABLE students (
	id INT PRIMARY KEY AUTO_INCREMENT,
	stuId INT UNIQUE,
	stuName VARCHAR(20),
	sex CHAR(1),  -- 男/女
    bDay date,
    email VARCHAR(100),
    note VARCHAR(100),
	cid INT, 			-- 所属班级ID
  FOREIGN KEY (cid) REFERENCES class (id)
);

-- ============================== 触发器 ==============================
-- 创建触发器，当学生表中学生的班级有改动，班级表中相应班级人数也调整
-- MySQL 中定义了 new 和 old，用来表示触发器的所在表中，触发了触发器的那一行数据。

-- 触发器1 班级表中的班级人数 随着 新增学生 自动更新
DELIMITER $
create trigger tri_stuInsert
after insert on students
for each row
begin
  declare currentSize int;
  set currentSize = (select size from class where id = new.cid);
  update class set size = currentSize + 1 where id = new.cid;
end $


-- 触发器2 学生表中学生的班级有改动，班级表中相应班级人数也调整（新的增/旧的减）
-- drop trigger if exists tri_cidUpdate;
DELIMITER $
create trigger tri_cidUpdate
after update on students
for each row
begin
  declare currentSizeNew int;
	declare currentSizeOld int;
  set currentSizeNew = (select size from class where id = new.cid);
	set currentSizeOld = (select size from class where id = old.cid);
  update class set size = currentSizeNew + 1 where id = new.cid; -- 新的增
	update class set size = currentSizeOld - 1 where id = old.cid; -- 旧的减
end $


-- 触发器3 学生表中有学生被删除，班级表中相应班级人数也调整
-- drop trigger if exists tri_stuDel;
DELIMITER $
create trigger tri_stuDel
after delete on students
for each row
begin
	update class set size = size - 1 where id = old.cid;
end $


-- ============================== 初始化数据库表内容 ==============================

-- 增加管理员用户信息
INSERT INTO users(userName,pwd) VALUES('admin', '123456');

-- 增加测试班级信息
INSERT INTO class(cName, grade, teacher, slogan,size ) values('自然语言处理', '4', 'Eliza T.', '我爱自然语言处理', '0');
INSERT INTO class(cName, grade, teacher, slogan,size ) values('离散数学', '1', '甲乙丙丁', '离散数学必过', '0');


-- 增加测试学生信息 完整信息
INSERT INTO students(stuId, stuName, sex, bDay, email, note, cid) values('1001', '张三','男','1991-01-01', 'z1@163.com','博士生', '1');
INSERT INTO students(stuId, stuName, sex, bDay, email, note, cid) values('1002', '李四','男','1992-02-02', '222222@163.com','休学中', '2');
-- 后续测试修改
INSERT INTO students(stuId, stuName, sex, bDay) values('1003', '王五','女','1993-03-03');
INSERT INTO students(stuId, stuName, sex, bDay) values('1004', '赵六','女','1994-04-04');
INSERT INTO students(stuId, stuName, sex, bDay) values('1005', '孙七','女','1995-05-05');
INSERT INTO students(stuId, stuName, sex, bDay) values('1006', '张三二','女','1996-06-06');
INSERT INTO students(stuId, stuName, sex, bDay) values('1007', '张三3','男','1997-07-07');







-- INSERT INTO users(userName,pwd) VALUES('1001', '1234');
-- INSERT INTO users(userName,pwd) VALUES('1002', '1234');
-- INSERT INTO users(userName,pwd) VALUES('1003', '1234');
-- INSERT INTO users(userName,pwd) VALUES('1004', '1234');
-- INSERT INTO users(userName,pwd) VALUES('1005', '1234');
-- INSERT INTO users(userName,pwd) VALUES('1006', '1234');
-- INSERT INTO users(userName,pwd) VALUES('1007', '1234');





