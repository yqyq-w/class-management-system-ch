package servlet;

/**
 * assn 402
 * yiqingw
 */

import bean.Student;
import service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ModifyStudentServlet", urlPatterns = "/modifyStudent")
public class StudentModifyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        // 获取需要修改学生的学号参数
        String idToModify = request.getParameter("chosen");

        // 获取修改后的参数，并封装到Student对象
        Student modifiedStudent = new Student();
        modifiedStudent.setStuId(request.getParameter("stuId"));
        modifiedStudent.setStuName(request.getParameter("stuName"));
        modifiedStudent.setSex(request.getParameter("sex"));
        modifiedStudent.setbDay(request.getParameter("bDay"));
        modifiedStudent.setEmail(request.getParameter("email"));
        modifiedStudent.setNote(request.getParameter("note"));
        modifiedStudent.setCid(Integer.parseInt(request.getParameter("selectClass")));
//        System.out.println(modifiedStudent);



        PrintWriter writer = response.getWriter();
        // 获取模块名称
        String contextPath = this.getServletContext().getContextPath();
        // 拼接请求路径
        String hrefPath = contextPath + "/showAllStudents";

        // 调用StudentService完成修改
        StudentService stuService = new StudentService();
        int res = stuService.modifyStudentService(idToModify, modifiedStudent);
        // // 学号重复报error
        if (0 == res) {
            System.out.println("修改学生信息失败！");
            request.setAttribute("error", "修改学生信息失败，学号不能重复！");

            writer.print("<script>alert('修改学生信息失败！'); location.href='" + hrefPath
                    + "';</script>");
//            // 实现服务器跳转  共享request和response对象
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("main.jsp");
//            requestDispatcher.forward(request, response);
        } else {
            System.out.println("修改学生信息成功！");
            writer.print("<script>alert('成功修改学生信息！'); location.href='" + hrefPath
                    + "';</script>");
//            // 跳转到显示页面
//            response.sendRedirect(request.getContextPath() + "/showAllStudents");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
