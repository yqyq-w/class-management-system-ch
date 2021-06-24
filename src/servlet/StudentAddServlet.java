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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "AddStudentServlet", urlPatterns = "/addStudent")
public class StudentAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        // 获取新增学生信息的参数，并封装到Student对象
        Student newStudent = new Student();
        newStudent.setStuId(request.getParameter("stuId"));
        newStudent.setStuName(request.getParameter("stuName"));
        newStudent.setSex(request.getParameter("sex"));
        newStudent.setbDay(request.getParameter("bDay"));

//        String birthday = request.getParameter("bDay");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = null;
//        try {
//            date = sdf.parse(birthday);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//       // new java.sql.Date(student.getBirthday().getTime() // dao中

        newStudent.setEmail(request.getParameter("email"));
        newStudent.setNote(request.getParameter("note"));
        newStudent.setCid(Integer.parseInt(request.getParameter("selectClass")));
//        System.out.println(newStudent);

        // 调用StudentService完成增加
        StudentService stuService = new StudentService();
        int res = stuService.addStudentService(newStudent);

        PrintWriter writer = response.getWriter();
        // 获取模块名称
        String contextPath = this.getServletContext().getContextPath();
        // 拼接请求路径
        String hrefPath = contextPath + "/showAllStudents";

        // 学号重复报error
        if (0 == res) {
            System.out.println("增加学生失败！");
            request.setAttribute("error", "增加学生失败，已存在该学号！");

            writer.print("<script>alert('增加学生失败！'); location.href='" + hrefPath
                        + "';</script>");
//            // 实现服务器跳转  共享request和response对象
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("main.jsp");
//            requestDispatcher.forward(request, response);
        } else {
            System.out.println("增加学生成功！");
            writer.print("<script>alert('成功增加学生！'); location.href='" + hrefPath
                    + "';</script>");
//            // 跳转到显示页面
//            response.sendRedirect(request.getContextPath() + "/showAllStudents");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
