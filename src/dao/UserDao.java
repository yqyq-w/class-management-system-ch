package dao;

/**
 * assn 402
 * yiqingw
 */

import bean.User;

public interface UserDao {
    // 自定义抽象方法描述登录功能的实现
    public abstract User userLogin(User user);
}
