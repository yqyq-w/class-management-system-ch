package servlet;

/**
 * assn 402
 * yiqingw
 */

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SwitchMngServlet", urlPatterns = "/switchMng")
public class SwitchMngServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("utf-8");
        //获取切换指令
        String switchTo = request.getParameter("switchTo");
//        System.out.println(switchTo);
        if ("班级管理".equals(switchTo)){
            response.sendRedirect(request.getContextPath() + "/showAllClasses");
        } else if ("学生管理".equals(switchTo)) {
            response.sendRedirect(request.getContextPath() + "/showAllStudents");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
