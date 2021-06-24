package service;

/**
 * assn 402
 * yiqingw
 */

import bean.ClassInfo;
import bean.Page;
import dao.ClassDao;
import factory.ClassDaoFactory;

import java.util.List;

public class ClassService {

    private ClassDao classDao;

    public ClassService() {
        this.classDao = ClassDaoFactory.getClassDao();
    }

    /**
     * 自定义成员方法实现根据参数指定的对象来调用DAO层实现增加班级信息功能
     * @param
     * @return
     */
    public int addClassService(ClassInfo classInfo) {
        return classDao.addClass(classInfo);
    }

    /**
     * 自定义成员方法实现根据参数指定的数组来调用DAO层实现删除班级信息功能
     * @param
     */
    public List<String> deleteClassService(String[] cids) {
        return classDao.deleteClass(cids);
    }

//    public boolean deleteClassService(String[] cids) {
//        return classDao.deleteClass(cids);
//    }

    /**
     * 自定义成员方法实现根据参数指定的cName和对象来调用DAO层实现修改班级信息功能
     * @param
     * @param
     * @return
     */
    public int modifyClassService(int id, ClassInfo modifiedClassInfo ) {
        return classDao.modifyClass(id, modifiedClassInfo);
    }

    /**
     * 自定义成员方法实现根据参数指定的查询条件对象来调用DAO层实现查询班级信息功能
     * @param queryName
     * @return
     */
    public List<ClassInfo> findClassService(Page page, String queryName) {
        return classDao.findClass(page, queryName);
    }

    /**
     * 自定义成员方法调用DAO层实现显示全部班级信息功能
     * @return
     */
    public List<ClassInfo> showClassesService() {
        return classDao.showClass();
    }


    /**
     * 自定义成员方法分页显示所有班级的功能
     * @param page
     * @return
     */
    public List<ClassInfo> showClassesByPageService(Page page) {
        return classDao.showClassByPage(page);
    }

    public Long getTotalRowService() {
        return classDao.getTotalRow();
    }

    public Long getTotalRowService(String queryName) {
        return classDao.getTotalRow(queryName);
    }


}
