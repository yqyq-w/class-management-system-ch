package bean;

/*

 * assn 402
 * yiqingw

MySQL数据库分页
 select * from 表名 limit startrow, pagesize (pagesize为每页显示的记录条数)
 */

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;
import java.util.List;

//    // int totalCnt 总记录条数
//    private int totalCnt;
//    // int totalPages 总页码
//    private int totalPages;
//    // List<T> list 每页的数据
//    private List<T> list;
//    // int currentPage 当前页码
//    private  int currentPage;
//    // int cntPerPage 每页显示的记录数
//    private int cntPerPage;


public class Page implements Serializable, HttpSessionActivationListener , HttpSessionBindingListener {
    private int page;   // 用于描述前端页面中第几页信息的成员变量
    private int row;    // 用于描述前端页面中每页记录数的成员变量
    private int start;  // 用于描述起始记录下标的成员变量

    public Page() {
    }

    public Page(int page, int row) {
        this.page = page;
        this.row = row;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getStart() {
        // 使用第几页信息-1再乘以每页的记录数就可以得到接下来从下标为多少的记录开始查询了
        return (page-1) * row;
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("Page执行了钝化操作..." + httpSessionEvent.getSession());
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("Page活化操作进行中...");
    }

    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("Page对象绑定到session中了" + httpSessionBindingEvent.getName());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("Page解除绑定成功！");
    }
}



//
//<div>
//<ul class="pagination">
//<li class="previous"> <a href="#"> &laquo; </a> </li>
//<li class="active" name="page" value="1"> <a href="#">1</a> </li>
//<li> <a href="#">2</a> </li>
//<li> <a href="#">3</a> </li>
//<li class="next"> <a href="#">&raquo;</a> </li>
//</ul>
//</div>

