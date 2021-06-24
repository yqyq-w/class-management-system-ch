package dao;

/**
 * assn 402
 * yiqingw
 */

import bean.ClassInfo;
import bean.Page;
import bean.Student;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.List;

/*
admin登录后进行学员信息增加、学员信息修改、学员信息删除、学员信息查找、学员信息显示功能
 */
public class StudentDaoImp implements StudentDao{

    private QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
    private String sql;
    private Object[] param;

    /**
     * 重写实现类增加学员信息的方法
     * @param student
     * @return
     */
    @Override
    public int addStudent(Student student) {
        // bDay格式：yyyy-mm-dd
        sql = "INSERT INTO students(stuId, stuName, sex, bDay, email, note, cid) values(?, ?, ?, ?, ?, ?, ?)";
        param = new Object[]{student.getStuId(), student.getStuName(), student.getSex(), student.getbDay(), student.getEmail(), student.getNote(), student.getCid()};

        int update = 0;
        try {
            update = qr.update(sql, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }

    /**
     * 重写实现类修改学员信息的方法
     * @param stuId
     * @param student
     * @return
     */
    @Override
    public int modifyStudent(String stuId, Student student) {
        sql = "UPDATE students SET stuId=?, stuName=?, sex=?, bDay=?, email=?, note=?, cid=? WHERE stuId=?";
        param = new Object[]{student.getStuId(), student.getStuName(), student.getSex(), student.getbDay(), student.getEmail(), student.getNote(), student.getCid(), stuId};

        int update = 0;
        try {
            update = qr.update(sql, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }

    /**
     * 重写实现类删除学员信息的方法
     * @param stuIds
     */
    @Override
    public void deleteStudent(String[] stuIds) {
        for (String sid : stuIds) {
            sql = "delete from students where stuId=?";
            try {
                qr.update(sql, sid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重写实现类查找学员信息的方法
     * @param page
     * @param queryID
     * @param queryName
     * @return
     */
    @Override
    public List<Student> findStudent(Page page, String queryID, String queryName) {

        if (!("").equals(queryID) && !("").equals(queryName)) {
            // 准确查询
            sql = "select * from students where stuId = ? and stuName = ? limit ?, ?";
            param =  new Object[]{queryID, queryName, page.getStart(), page.getRow()};
        } else if (!("").equals(queryID)) {
            // 模糊查询
            sql = "select * from students where stuId like ? limit ?, ?" ;
            param =  new Object[]{"%" + queryID + "%", page.getStart(), page.getRow()};
        } else if (!("").equals(queryName)) {
            // 模糊查询
            sql = "select * from students where stuName like ? limit ?, ?";
            param =  new Object[]{"%" + queryName + "%", page.getStart(), page.getRow()};
        }

        List<Student> studentList = null;
        try {
            studentList = qr.query(sql, new BeanListHandler<Student>(Student.class), param);
            // cid --> 班级名称
            for (Student s : studentList) {
                int cid = s.getCid();
                s.setClassInfo(findClassById(cid));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }


//    public List<Student> findStudent(String queryID, String queryName) {
//
//        if (!("").equals(queryID) && !("").equals(queryName)) {
//            // 准确查询
//            sql = "select * from students where stuId = ? and stuName = ?";
//            param =  new Object[]{queryID, queryName};
//        } else if (!("").equals(queryID)) {
//            // 模糊查询
//            sql = "select * from students where stuId like ?" ;
//            param =  new Object[]{"%" + queryID + "%"};
//        } else if (!("").equals(queryName)) {
//            // 模糊查询
//            sql = "select * from students where stuName like ?";
//            param =  new Object[]{"%" + queryName + "%"};
//        }
//        //
//        List<Student> studentList = null;
//        try {
//            studentList = qr.query(sql, new BeanListHandler<Student>(Student.class), param);
//            // cid --> 班级名称
//            for (Student s : studentList) {
//                int cid = s.getCid();
//                s.setClassInfo(findClassById(cid));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return studentList;
//    }

    /**
     * 重写实现类显示学员信息的方法
     * @return
     */
    @Override
    public List<Student> showStudents() {
        sql = "select * from students";
        List<Student> studentList = null;
        try {
            studentList = qr.query(sql, new BeanListHandler<Student>(Student.class));
            // cid --> 班级名称
            for (Student s : studentList) {
                int cid = s.getCid();
                s.setClassInfo(findClassById(cid));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    /**
     *
     * @param page
     * @return
     */
    @Override
    public List<Student> showStudentsByPage(Page page) {

        sql = "select * from students limit ?, ?";
        param = new Object[]{page.getStart(), page.getRow()};
//        System.out.println(page.getStart());
//        System.out.println(page.getRow());

        List<Student> studentList = null;
        try {
            studentList = qr.query(sql, new BeanListHandler<Student>(Student.class), param);
            // cid --> 班级名称
            for (Student s : studentList) {
                int cid = s.getCid();
                s.setClassInfo(findClassById(cid));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println(studentList);
        return studentList;
    }


    public Long getTotalRow(){
        sql = "select count(*) from students";

        Long totalRow = Long.valueOf(0);
        try {
            totalRow = qr.query(sql, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRow;
    }


    public Long getTotalRow(String queryID, String queryName){
        if (!("").equals(queryID) && !("").equals(queryName)) {
            // 准确查询
            sql = "select count(*) from students where stuId=? and stuName=?";
            param =  new Object[]{queryID, queryName};
        } else if (!("").equals(queryID)) {
            // 模糊查询
            sql = "select count(*) from students where stuId like ?" ;
            param =  new Object[]{"%" + queryID + "%"};
        } else if (!("").equals(queryName)) {
            // 模糊查询
            sql = "select count(*) from students where stuName like ?";
            param =  new Object[]{"%" + queryName + "%"};
        }

        Long totalRow = Long.valueOf(0);
        try {
            totalRow = qr.query(sql, new ScalarHandler<>(), param);
//            System.out.println(totalRow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRow;
    }

    /**
     * 查找班级
     * @param cid
     * @return
     * @throws SQLException
     */
    @Override
    public ClassInfo findClassById(int cid) {
        sql = "select * from class where id = ?";
        ClassInfo classInfo = null;
        try {
            classInfo = qr.query(sql, new BeanHandler<ClassInfo>(ClassInfo.class), cid);
//            System.out.println(classInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classInfo;
    }


    //    @Override
//    public List<Student> showStudentsByPage(int currentPage, int cntPerPage, Map<String, String[]> queryInfo) {
//        return null;
//    }
}


//    /**
//     * 自定义成员方法描述显示所有学生的功能
//     * @param pageBean
//     * @return
//     */
//    @Override
//    public List<Student> studentShow(PageBean pageBean) {
//        List<Student> studentList = null;
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            // 1.获取Connection连接对象
//            connection = DruidUtils.getConnection();
//            // 2.使用StringBuilder类型的对象来描述SQL语句的目的在于后续可以发生改变
//            StringBuilder stringBuilder = new StringBuilder("select * from t_student");
//            // 3.当输入分页的需求时进行分页查询，于是拼接SQL语句
//            if (pageBean != null) {
//                stringBuilder.append(" limit " + pageBean.getStart() + "," + pageBean.getRow());
//            }
//            // 4.获取PreparedStatement并执行上述SQL语句
//            preparedStatement = connection.prepareStatement(stringBuilder.toString());
//            resultSet = preparedStatement.executeQuery();
//            // 5.将结果集中的数据取出并放到List集合中返回
//            studentList = new ArrayList<>();
//            while (resultSet.next()) {
//                Student student = new Student(resultSet.getInt("id"), resultSet.getString("name"),
//                        resultSet.getString("sex"), new java.util.Date(resultSet.getDate("birthday").getTime()),
//                        resultSet.getString("email"), resultSet.getString("comments"));
//                studentList.add(student);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DruidUtils.close(connection, preparedStatement, resultSet);
//        }
//        return studentList;
//    }