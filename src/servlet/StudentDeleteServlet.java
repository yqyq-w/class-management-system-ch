package servlet;

/**
 * assn 402
 * yiqingw
 */

import bean.Student;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "DeleteStudentServlet", urlPatterns = "/deleteStudent")
public class StudentDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 获取被勾选的学号参数
        String[] stuIds = request.getParameterValues("chosen");
//        System.out.println(Arrays.toString(stuIds));

        // 调用StudentService完成删除
        StudentService stuService = new StudentService();
        stuService.deleteStudentService(stuIds);

        // 跳转到显示页面
        response.sendRedirect(request.getContextPath() + "/showAllStudents");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
