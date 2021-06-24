package dao;

/**
 * assn 402
 * yiqingw
 */

import bean.ClassInfo;
import bean.Page;
import bean.Student;

import java.util.List;

/*
admin登录后进行学员信息增加、学员信息修改、学员信息删除、学员信息查找、学员信息显示功能
 */
public interface StudentDao {

    // 学员信息增加
    public abstract int addStudent(Student student);

    // 学员信息修改
    public abstract int modifyStudent(String stuId, Student student);

    // 学员信息删除
    public abstract void deleteStudent(String[] stuIds);

    // 学员信息查找
//    public abstract List<Student> findStudent(String queryID, String queryName);
    public abstract List<Student> findStudent(Page page, String queryID, String queryName);

    // 学员信息显示功能
    public abstract List<Student> showStudents();

//    // 学员信息分页显示功能
    public abstract List<Student> showStudentsByPage(Page page);

    public abstract Long getTotalRow();

    public abstract Long getTotalRow(String queryID, String queryName);


    public abstract ClassInfo findClassById(int cid);



}
