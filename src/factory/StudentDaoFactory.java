package factory;

import dao.StudentDao;
import dao.StudentDaoImp;

public class StudentDaoFactory {

    /**
     * 通过静态工厂方法模式来实现StudentDao实现类对象的创建并返回
     * @return
     */
    public static StudentDao getStudentDao() {
        return new StudentDaoImp();
    }
}
