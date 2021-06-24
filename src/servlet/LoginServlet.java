package servlet;
/**
 * assn 402
 * yiqingw
 */

import bean.User;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取请求中的用户名和密码信息并打印出来
        String userName = request.getParameter("userName");
        System.out.println("获取到的用户名为：" + userName);
        String password = request.getParameter("password");
        System.out.println("获取到的密码为：" + password);
        // 2.创建UserService类型的对象去实现数据的校验功能
        UserService userService = new UserService();
        User user = userService.userLoginService(new User(userName, password));
        if (null == user) {
            System.out.println("登录失败，用户名或密码错误！");
            request.setAttribute("error", "登录失败，用户名或密码错误！");
            // 实现服务器跳转  共享request和response对象
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
            requestDispatcher.forward(request, response);
        } else {
            System.out.println("登录成功，欢迎使用！");

            // Cookie实现一周内免登录 7天
            Cookie nameCookie = new Cookie("userName", user.getUserName());
            nameCookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(nameCookie);
//            Cookie pwdCookie=new Cookie("password", user.getPassword());
//            pwdCookie.setMaxAge(7 * 24 * 60 * 60);
//            response.addCookie(pwdCookie);
            System.out.println("将cookie的生命周期设置为7天，" + user.getUserName() + "一周内免登录！");

            // 将登录成功的用户信息放入session对象中实现多个请求共享
            request.getSession().setAttribute("user", user);
            // 通过服务器跳转的方式 实现内部Servlet之间的跳转 直接到显示全部学生页面
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/showAllStudents");
            requestDispatcher.forward(request, response);
//            // 实现客户端跳转
//            response.sendRedirect("main.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
