//package servlet;
//
///**
// * assn 402
// * yiqingw
// */
//
//import bean.ClassInfo;
//import service.ClassService;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet(name = "FindClassServlet", urlPatterns = "/findClass")
//public class ClassFindServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//
//        // 设置编码
//        request.setCharacterEncoding("utf-8");
//
//        // 获取查询条件的参数
//        String queryName = request.getParameter("queryName");
//
//
//        // 调用ClassService完成查询
//        ClassService classService = new ClassService();
//        List<ClassInfo> classList = classService.(queryName);
//        Long totalPage = classService.getTotalRowService(queryName) / 5 + 1;
//
//        // studentList设置为request的属性
//        request.setAttribute("classes",classList);
//        request.setAttribute("totalPage",totalPage);
//        request.setAttribute("queryInfo", new String[]{queryName});
//
//        // 转发到main.jsp
//        request.getRequestDispatcher("/classManagement.jsp").forward(request,response);
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        this.doPost(request, response);
//    }
//}
