package servlet;

/**
 * assn 402
 * yiqingw
 */

import bean.ClassInfo;
import service.ClassService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ModifyClassServlet", urlPatterns = "/modifyClass")
public class ClassModifyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置编码
        request.setCharacterEncoding("utf-8");

        int idToModify = Integer.parseInt(request.getParameter("chosen"));
        // 获取修改参数，并封装到ClassInfo对象
        ClassInfo modifiedClass = new ClassInfo();
        modifiedClass.setId(idToModify);
        modifiedClass.setcName(request.getParameter("cName"));
        modifiedClass.setGrade(Integer.parseInt(request.getParameter("grade")));
        modifiedClass.setTeacher(request.getParameter("teacher"));
        modifiedClass.setSlogan(request.getParameter("slogan"));

        // 调用ClassService完成增加
        ClassService classService = new ClassService();
        int res = classService.modifyClassService(idToModify, modifiedClass);

        // error
        if (0 == res) {
            System.out.println("修改班级失败！");
            request.setAttribute("error", "修改班级失败！");
            // 实现服务器跳转  共享request和response对象
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("classManagement.jsp");
            requestDispatcher.forward(request, response);
        } else {
            System.out.println("修改班级成功！");
            // 跳转到显示页面
            response.sendRedirect(request.getContextPath() + "/showAllClasses");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
