package servlet;

/**
 * assn 402
 * yiqingw
 */

import bean.ClassInfo;
import bean.Page;
import service.ClassService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShowAllClassesServlet", urlPatterns = "/showAllClasses")
public class ClassesShowAllServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置编码
        request.setCharacterEncoding("utf-8");

        // 获取前端页面传来的页码数值并构造分页查询的对象
        String page = request.getParameter("page");
//        System.out.println(page);
        // 这里创建默认值为1，也就是第一个默认查询第一页数据内容
        Page pageBean = new Page(1, 5);
        if (null != page && page.length() > 0) {
            pageBean.setPage(Integer.parseInt(page));
        }

        // 获取查询条件的参数
        String queryName = request.getParameter("queryName");

        // 调用ClassService完成显示功能
        ClassService classService = new ClassService();
        List<ClassInfo> classList = null;
        Long totalPage = null;

        // 调用ClassService完成显示功能
        if ("" != queryName && null != queryName){

            classList = classService.findClassService(pageBean, queryName);
            totalPage = (classService.getTotalRowService(queryName) - 1) / 5 + 1;

            System.out.println(queryName);

        } else {

//        ClassService classService = new ClassService();
            classList = classService.showClassesByPageService(pageBean);
            totalPage = (classService.getTotalRowService() - 1) / 5 + 1;
//        System.out.println(classList);
        }


        // 设置为request的新属性
        request.setAttribute("classes",classList);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("queryInfo", new String[]{queryName});

        // 转发到classManagement.jsp
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("classManagement.jsp");
        requestDispatcher.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
