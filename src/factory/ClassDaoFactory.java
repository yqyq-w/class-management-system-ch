package factory;

/**
 * assn 402
 * yiqingw
 */

import dao.ClassDao;
import dao.ClassDaoImpl;

public class ClassDaoFactory {
    /**
     * 通过静态工厂方法模式来实现ClassDao实现类对象的创建并返回
     * @return
     */
    public static ClassDao getClassDao() {
        return new ClassDaoImpl();
    }
}
