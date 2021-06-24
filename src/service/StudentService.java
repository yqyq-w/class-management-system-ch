package service;

/**
 * assn 402
 * yiqingw
 */

import bean.Page;
import bean.Student;
import dao.StudentDao;
import factory.StudentDaoFactory;

import java.util.List;

public class StudentService {

    private StudentDao studentDao;

    public StudentService() {
        this.studentDao = StudentDaoFactory.getStudentDao();
    }

    /**
     * 自定义成员方法实现根据参数指定的Student对象来调用DAO层实现增加学生信息功能
     * @param student
     * @return
     */
    public int addStudentService(Student student) {
        return studentDao.addStudent(student);
    }

    /**
     * 自定义成员方法实现根据参数指定的stuId数组来调用DAO层实现删除学生信息功能
     * @param stuIds
     */
    public void deleteStudentService(String[] stuIds) {
        studentDao.deleteStudent(stuIds);
    }

    /**
     * 自定义成员方法实现根据参数指定的stuId和Student对象来调用DAO层实现修改学生信息功能
     * @param stuId
     * @param modifiedStudent
     * @return
     */
    public int modifyStudentService(String stuId, Student modifiedStudent) {
        return studentDao.modifyStudent(stuId, modifiedStudent);
    }

    /**
     * 自定义成员方法实现根据参数指定的查询条件对象来调用DAO层实现查询学生信息功能
     * @param queryID
     * @param queryName
     * @return
     */
    public List<Student> findStudentService(Page page, String queryID, String queryName) {
        return studentDao.findStudent(page, queryID, queryName);
    }

    /**
     * 自定义成员方法调用DAO层实现显示全部学生信息功能
     * @return
     */
    public List<Student> showStudentsService() {
        return studentDao.showStudents();
    }



    /**
     * 自定义成员方法分页显示所有学生的功能
     * @param page
     * @return
     */
    public List<Student> showStudentsByPageService(Page page) {
        return studentDao.showStudentsByPage(page);
    }

    public Long getTotalRowService() {
        return studentDao.getTotalRow();
    }

    public Long getTotalRowService(String queryID, String queryName) {
        return studentDao.getTotalRow(queryID, queryName);
    }


}

