package dao;

/**
 * assn 402
 * yiqingw
 */

import bean.ClassInfo;
import bean.Page;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassDaoImpl implements ClassDao {

    private QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
    private String sql;
    private Object[] param;

    @Override
    public int addClass(ClassInfo classInfo) {
        sql = "INSERT INTO class(cName, grade, teacher, slogan, size) values(?, ?, ?, ?, '0')";
        param = new Object[]{classInfo.getcName(), classInfo.getGrade(), classInfo.getTeacher(), classInfo.getSlogan()};
        int update = 0;
        try {
            update = qr.update(sql, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;

    }

    @Override
    public int modifyClass(int id, ClassInfo classInfo) {
        sql = "UPDATE class SET cName=?, grade=?, teacher=?, slogan=? WHERE id=?";
        param = new Object[]{classInfo.getcName(), classInfo.getGrade(), classInfo.getTeacher(), classInfo.getSlogan(), classInfo.getId()};
        int update = 0;
        try {
            update = qr.update(sql, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }



    @Override
    public List<String> deleteClass(String[] cids) {
        List<String> deleted = new ArrayList<>();
        for (String id : cids) {
            try {
                sql = "select * from class where id=?";
                ClassInfo tc = qr.query(sql, new BeanHandler<ClassInfo>(ClassInfo.class), id);
                if (tc.getSize() > 0) {
                    System.out.println("班级《"+ tc.getcName() + "》内还有关联学生，删除失败！");
                } else {
                    sql = "delete from class where id=?";
                    qr.update(sql, id);
                    System.out.println("成功删除班级《"+ tc.getcName() + "》！");
                    deleted.add(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return deleted;
    }



//    @Override
//    public boolean deleteClass(String[] cids) {
//        for (String id : cids) {
//            try {
//                sql = "select * from class where id=?";
//                ClassInfo tc = qr.query(sql, new BeanHandler<ClassInfo>(ClassInfo.class), id);
//                if (tc.getSize() > 0) {
//                    System.out.println("班级《"+ tc.getcName() + "》内还有关联学生，删除失败！");
//                    return false;
//                } else {
//                    sql = "delete from class where id=?";
//                    qr.update(sql, id);
//                    System.out.println("成功删除班级《"+ tc.getcName() + "》！");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//        return true;
//    }

    @Override
    public List<ClassInfo> findClass(Page page ,String queryName) {

        List<ClassInfo> classList = null;

        if (!"".equals(queryName) && null != queryName){
            try {
                // 模糊查询
                sql = "select * from class where cName like ? limit ?, ?" ;
                param =  new Object[]{"%" + queryName + "%", page.getStart(), page.getRow()};
                classList = qr.query(sql, new BeanListHandler<ClassInfo>(ClassInfo.class), param);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }



//    public List<ClassInfo> findClass(String queryName) {
//
//        List<ClassInfo> classList = null;
//        if (!"".equals(queryName) && null != queryName){
//            try {
//                // 模糊查询
//                sql = "select * from class where cName like ?" ;
//                param =  new Object[]{"%" + queryName + "%"};
//                classList = qr.query(sql, new BeanListHandler<ClassInfo>(ClassInfo.class), param);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return classList;
//    }

    @Override
    public List<ClassInfo> showClass() {
        sql = "select * from class";
        List<ClassInfo> classList = null;
        try {
            classList = qr.query(sql, new BeanListHandler<ClassInfo>(ClassInfo.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classList;
    }

    @Override
    public List<ClassInfo> showClassByPage(Page page) {

        sql = "select * from class limit ?, ?";
        param = new Object[]{page.getStart(), page.getRow()};

        List<ClassInfo> classList = null;
        try {
            classList = qr.query(sql, new BeanListHandler<ClassInfo>(ClassInfo.class), param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classList;
    }

    @Override
    public Long getTotalRow() {
        sql = "select count(*) from class";

        Long totalRow = Long.valueOf(0);
        try {
            totalRow = qr.query(sql, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRow;
    }

    @Override
    public Long getTotalRow(String queryName) {

        // 模糊查询
        sql = "select count(*) from class where cName like ?";
        param =  new Object[]{"%" + queryName + "%"};

        Long totalRow = Long.valueOf(0);
        try {
            totalRow = qr.query(sql, new ScalarHandler<>(), param);
//            System.out.println(totalRow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRow;
    }

    //    public int getTotalRow(List<ClassInfo> list) {
//        return list.size();
//    }


}
