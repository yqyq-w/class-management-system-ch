package dao;

/**
 * assn 402
 * yiqingw
 */

import bean.User;

import util.DruidUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImp implements UserDao {
    @Override
    public User userLogin(User user) {

        // 使用连接池创建QueryRunner对象；
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        // 准备sql语句
        String sql = "select * from users where userName = ? and pwd = ?";
        Object[] param = {user.getUserName(), user.getPassword()};

        // 执行sql语句后获取结果并返回
        try {
            User tu = qr.query(sql, new BeanHandler<User>(User.class), param);
            return tu;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // 表示查找失败
    }
}
