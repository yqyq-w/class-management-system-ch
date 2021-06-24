package dao;

/**
 * assn 402
 * yiqingw
 */

import bean.ClassInfo;
import bean.Page;

import java.util.List;

public interface ClassDao {
    // 班级信息增加
    public abstract int addClass(ClassInfo classInfo);

    // 班级信息修改
    public abstract int modifyClass(int id, ClassInfo classInfo);

    // 班级信息删除
//    public abstract boolean deleteClass(String[] cids);

    public abstract List<String> deleteClass(String[] cids);

    // 班级信息查找
//    public abstract List<ClassInfo> findClass(String queryName);

    public abstract List<ClassInfo> findClass(Page pageBean ,String queryName);

    // 班级信息显示功能
    public abstract List<ClassInfo> showClass();

   // 班级信息分页显示功能
    public abstract List<ClassInfo> showClassByPage(Page page);

    public abstract Long getTotalRow();

    public abstract Long getTotalRow(String queryName);

//    public abstract int getTotalRow(List<ClassInfo> list);


}
