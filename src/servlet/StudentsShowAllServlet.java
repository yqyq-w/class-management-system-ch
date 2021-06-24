package servlet;

/**
 * assn 402
 * yiqingw
 */

import bean.ClassInfo;
import bean.Page;
import bean.Student;
import service.ClassService;
import service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShowAllStudentsServlet", urlPatterns = "/showAllStudents")
public class StudentsShowAllServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置编码
        request.setCharacterEncoding("utf-8");

        // 获取查询条件的参数
        String queryID = request.getParameter("queryID");
        String queryName = request.getParameter("queryName");
        System.out.println(queryID + ", " + queryName);


        // 获取前端页面传来的页码数值并构造分页查询的对象
        String page = request.getParameter("page");
//        System.out.println(page);
        // 这里创建默认值为1，也就是第一个默认查询第一页数据内容
        Page pageBean = new Page(1, 5);
        if (null != page && page.length() > 0) {
            pageBean.setPage(Integer.parseInt(page));
        }
        // 调用StudentService完成显示功能
        StudentService stuService = new StudentService();
        List<Student> studentList = null;
        Long totalPage = null;


        // 调用ClassService完成显示功能
        if (("" == queryName || null == queryName) && ("" == queryID || null == queryID)){

            studentList = stuService.showStudentsByPageService(pageBean);
            totalPage = totalPage = (stuService.getTotalRowService() - 1) / 5 + 1;
        } else {
            studentList = stuService.findStudentService(pageBean, queryID, queryName);
            totalPage = stuService.getTotalRowService(queryID, queryName) / 5 + 1;

            System.out.println(queryID + ", " + queryName);

        }


        ClassService classService = new ClassService();
        List<ClassInfo> classList = classService.showClassesService();

        request.setAttribute("classes",classList);

        // 设置为request的新属性
        request.setAttribute("students",studentList);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("queryInfo", new String[]{queryID, queryName});

        // 转发到main.jsp
        request.getRequestDispatcher("/main.jsp").forward(request,response);


//        // 将获取到的所有学员信息放到内置对象，通过客户端跳转的方式显示出来
//        request.getSession().setAttribute("studentList", studentList);
//        response.sendRedirect("manageStudent.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
