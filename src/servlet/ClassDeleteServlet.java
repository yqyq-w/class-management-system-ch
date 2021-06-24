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
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "DeleteClassServlet", urlPatterns = "/deleteClass")
public class ClassDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=utf-8");

        // 获取被勾选的cid参数
        String[] cids = request.getParameterValues("chosen");
//        System.out.println(Arrays.toString(cids));

        // 调用ClassService完成删除
        ClassService classService = new ClassService();
        List<String> deletedCids = classService.deleteClassService(cids);

        // 通过输出流向前台页面展示处理结果，然后重新请求一次数据
        PrintWriter writer = response.getWriter();
        // 获取模块名称
        String contextPath = this.getServletContext().getContextPath();
        // 拼接请求路径
        String hrefPath = contextPath + "/showAllClasses";

        if (cids.length == deletedCids.size()){
            writer.print("<script>alert('全部删除成功！'); location.href='" + hrefPath
                    + "';</script>");
        } else if (deletedCids.size() > 0){
            writer.print("<script>alert('部分删除成功，编号为：" + deletedCids +"'); location.href='" + hrefPath
                    + "';</script>");
        } else {
            writer.print("<script>alert('删除失败！注：只有当前人数为0的班级才能删除哦！'); location.href='" + hrefPath
                    + "';</script>");
        }

        // 跳转到显示页面
//        response.sendRedirect(request.getContextPath() + "/showAllClasses");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
