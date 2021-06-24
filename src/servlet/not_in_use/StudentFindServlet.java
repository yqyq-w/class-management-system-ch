//package servlet;
//
///**
// * assn 402
// * yiqingw
// */
//
//import bean.ClassInfo;
//import bean.Page;
//import bean.Student;
//import service.ClassService;
//import service.StudentService;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//@WebServlet(name = "FindStudentServlet", urlPatterns = "/findStudent")
//public class StudentFindServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        // 设置编码
//        request.setCharacterEncoding("utf-8");
//
//        // 获取查询条件的参数
//        String queryID = request.getParameter("queryID");
//        String queryName = request.getParameter("queryName");
//
//        // 调用StudentService完成查询
//        StudentService stuService = new StudentService();
//        List<Student> studentList = stuService.findStudentService(queryID, queryName);
//        Long totalPage = stuService.getTotalRowService(queryID, queryName) / 5 + 1;
//
//        // studentList设置为request的属性
//        request.setAttribute("students",studentList);
//        request.setAttribute("totalPage",totalPage);
//        request.setAttribute("queryInfo", new String[]{queryID, queryName});
//
//        // 转发到main.jsp
//        request.getRequestDispatcher("/main.jsp").forward(request,response);
//
//
//
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        this.doPost(request, response);
//    }
//}
//
//
////    // 3.通过输出流向前台页面展示处理结果，然后重新请求一次数据
////    PrintWriter writer = response.getWriter();
////    // 获取模块名称
////    String contextPath = this.getServletContext().getContextPath();
////    // 拼接请求路径
////    String hrefPath = contextPath + "/studentPageQueryServlet";
////        if(0 != res) {
////                writer.print("<script>alert('添加学生成功！'); location.href='" + hrefPath
////                + "';</script>");
////                }else{
////                writer.print("<script>alert('添加学生失败！'); location.href='" + hrefPath
////                + "';</script>");
////                }