<%--
  Created by IntelliJ IDEA.

 * assn 402
 * yiqingw

使用EL+JSTL表达式、
过滤器和监听器对第四阶段模块一的作业进行技术升级、
使用Cookie实现一周内免登录、
增加班级管理：可以创建班级、修改班级信息、删除班级(前提:保证班级内没有关联学生)、班级查询。
为学生表增加字段: 所属班级、
班级信息如下:班级名称、年级、班主任名称、班级口号 、班级人数、
要求部署到linux系统中，通过本地浏览器能正常访问。

回顾：
使用前端、数据库、JavaWeb等技术并采用MVC设计模式实现学生信息管理系统，
要求使用管理员账号密码登录后进行学员信息增加、学员信息修改、学员信息删除、学员信息查找、学员信息显示功能。
其中学生信息有: 学号、姓名、性别、出生日期、邮箱、备注
--%>

<%@ page import="bean.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=utf-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>学生信息管理页面</title>
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/bootstrap-select.css"> <%--<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/css/bootstrap-select.min.css">--%>
    <link rel="stylesheet" href="./css/main.css">     <!--引入样式-->
</head>

<body>

    <div class="container">
        <div>
            <div class="h4 centerT">当前在线人数为：${applicationScope.count}</div>
            <div class="h4 centerT">
                <%--新登录用户名sessionScope.user["userName"]，或者七天内有效的cookie.userName.value--%>
                登录成功，欢迎${null == sessionScope.user["userName"] ? cookie.userName.value: sessionScope.user["userName"] }使用！
            </div>
            <div class="h3 centerT">学生信息列表</div>
        </div>

        <c:set var="qId" value="${queryInfo[0]}" scope="page"></c:set>
        <c:set var="qName" value="${queryInfo[1]}" scope="page"></c:set>

        <!--按钮和搜索框-->
        <form id="form_1" method="post">
            <div class="form-group">
                <input type="button" class="btn btn-success" value="新增学生" id="add" data-toggle="modal" data-target="#addModal">
                <input type="button" class="btn btn-success" value="删除学生" id="delete" data-toggle="modal" data-target="#deleteModal">
    <%--                <button class="btn btn-info" id="delete" formaction="deleteStudent">删除</button>--%>
                <input type="button" class="btn btn-success" value="编辑学生" id="edit" data-toggle="modal" data-target="#editModal">
                <%-- formaction属性设置点击查询按钮后的action地址 --%>
                <input type="submit" class="btn btn-success" formaction="showAllStudents?page=1" value="查询学生" id="query">
                <input value="${qId}" type="text" class="form-inline" placeholder="按学号查询" id="queryID" name="queryID">
                <input value="${qName}" type="text" class="form-inline" placeholder="按姓名查询" id="queryName" name="queryName">
                <%-- formaction属性设置点全部显示按钮后的action地址 --%>
                <input type="submit" class="btn btn-info" formaction="showAllStudents?page=1&queryID=&queryName=" value="全部学生" id="reset">
    <%--                <input type="submit" class="btn btn-success" onclick="this.form.action='showAllStudents'" value='全部显示' id="reset">--%>
                <%--切换--%>
                <input type="submit" class="btn btn-warning" formaction="switchMng" name="switchTo" value="班级管理" id="manageClass">
            </div>
        </form>

        <form id="form_2" method="post">
            <%-- 如有error进行提示 --%>
            <div> <span style="color: red">${error==null?"":error}</span><br/></div>
            <!--学生信息表格-->
            <table class="table table-responsive">
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>出生日期</th>
                        <th>邮箱</th>
                        <th>备注</th>
                        <th>所属班级</th>
                    </tr>
                </thead>

                <!--具体学生信息-->
                <tbody>

                <c:forEach var="ts" items="${students}" >
                    <tr>
                        <td>
                            <input type="checkbox" name="chosen" value="${ts.stuId}" />
                        </td>
                        <td class="id">${ts.stuId}</td>
                        <td class="name">${ts.stuName}</td>
                        <td>${ts.sex}</td>
                        <td>${ts.bDay}</td>
                        <td>${ts.email}</td>
                        <td>${ts.note}</td>
                        <%--显示所属班级--%>
                        <td>${ts.classInfo["cName"]}</td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>


            <!-- 声明定义deleteModal模态框组件 -->
            <div class="modal" id="deleteModal" data-backdrop="static">
                <!-- 窗口声明 -->
                <div class="modal-dialog">
                    <!-- 内容声明 -->
                    <div class="modal-content">
                        <!-- 1.框的标题 -->
                        <div class="modal-header">
                            <button class="close" data-dismiss="modal">
                                <span>&times;</span>
                            </button>
                            <h4 class="modal-title">删除学生信息</h4>
                        </div>
                        <!-- 2.框内信息 -->
                        <div class="modal-body">
                            <div style="padding-left: 25%">
                                <img src="./img/warning.jpeg" alt="震惊脸" style="width: 20%;">
                                <span class="h5"> &nbsp;<mark style="background-color:khaki;">&nbsp;&nbsp;确定要删除吗？</mark></span>
                            </div>
                        </div>
                        <!-- 3.框的按钮 -->
                        <div class="modal-footer">
                            <%-- 确认删除后提交到formaction="deleteStudent" --%>
                            <button class="btn btn-success" id="deleteSubmit" formaction="deleteStudent" >确定</button><button class="btn btn-default" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 声明定义editModal模态框组件 -->
            <div class="modal" id="editModal" data-backdrop="static">
                <!-- 窗口声明 -->
                <div class="modal-dialog">
                    <!-- 内容声明 -->
                    <div class="modal-content">
                        <!-- 1.框的标题 -->
                        <div class="modal-header">
                            <button class="close" data-dismiss="modal">
                                <span>&times;</span>
                            </button>
                            <h4 class="modal-title">修改学生信息</h4>
                        </div>
                        <!-- 2.框内信息 -->
                        <div class="modal-body">
                            <div style="padding-left: 25%">
                                <p>
                                    <input placeholder="请修改新学生的学号..." id="eid2" name="stuId" />
                                </p>
                                <p>
                                    <input placeholder="请修改学生的姓名..." id="name2" name="stuName"/>
                                </p>
                                <p>性别：
                                    <input type="radio" name="sex" value="男" />男
                                    <!--单选，分组-->
                                    <input type="radio" name="sex" value="女" />女
                                </p>
                                <p>
                                    <input placeholder="请修改学生的生日..." id="birthday2" name="bDay"/>
                                </p>
                                <p>
                                    <input placeholder="请修改学生的邮箱..." id="email2" name="email" />
                                </p>
                                <p>
                                    <input placeholder="请修改学生的备注..." id="note2" name="note"/>
                                </p>

                                <p>
                                    <select id="selectC2" class="selectpicker show-tick" data-live-search=true"
                                            title="请修改学生的班级..." data-width="180px" data-size="5" name="selectClass" >
                                        <option value="0" hidden>请修改学生的班级...</option>
                                        <c:forEach var="tc" items="${classes}" >
                                            <option value="${tc.id}" name="classInfo"> ${tc.cName} </option>
                                        </c:forEach>

                                    </select>
                                    <%--   <input placeholder="请选择新学生的所属班级..." id="class" name="class"/>--%>
                                </p>

                            </div>
                        </div>
                        <!-- 3.框的按钮 -->
                        <div class="modal-footer">
                            <%-- 确认修改后提交到formaction="modifyStudent" --%>
                            <button class="btn btn-success" id="editSubmit" formaction="modifyStudent">确定</button><button class="btn btn-default" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>

        <form id="form_3" action="addStudent" method="post">
            <!-- 声明定义addModal模态框组件 -->
            <div class="modal" id="addModal" data-backdrop="static">
                <!-- 窗口声明 -->
                <div class="modal-dialog">
                    <!-- 内容声明 -->
                    <div class="modal-content">
                        <!-- 1.框的标题 -->
                        <div class="modal-header">
                            <button class="close" data-dismiss="modal">
                                <span>&times;</span>
                            </button>
                            <h4 class="modal-title">增加学生信息</h4>
                        </div>
                        <!-- 2.框内信息 -->
                        <div class="modal-body">
                            <div style="padding-left: 25%">
                                <div class="form-group">
                                    <p>
                                        <input class="mustWrite" placeholder="请输入新学生的学号..." id="eid" name="stuId" />
                                    </p>
                                    <p>
                                        <input class="mustWrite" placeholder="请输入新学生的姓名..." id="name" name="stuName"/>
                                    </p>
                                    <p>性别：
                                        <input type="radio" name="sex" value="男" />男
                                        <!--单选，分组-->
                                        <input type="radio" name="sex" checked="checked" value="女" />女
                                    </p>
                                    <p>
                                        <input class="mustWrite" placeholder="请输入新学生的生日..." id="birthday" name="bDay"/>
                                    </p>
                                    <p>
                                        <input class="mustWrite" placeholder="请输入新学生的邮箱..." id="email" name="email" />
                                    <p>
                                        <input class="mustWrite" placeholder="请输入新学生的备注..." id="note" name="note"/>
                                    </p>
                                    <p>
                                        <select id="selectC" class="selectpicker show-tick" data-live-search=true"
                                        title="请选择新学生的班级..." data-width="180px" data-size="5" name="selectClass" >
                                            <option value="0" hidden>请选择新学生的班级...</option>
                                            <c:forEach var="tc" items="${classes}" >
                                                <option value="${tc.id}" name="classInfo"> ${tc.cName} </option>
                                            </c:forEach>
                                        </select>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <!-- 3.框的按钮 -->
                        <div class="modal-footer">
                            <%--                --%>
                            <button class="btn btn-success" id="addSubmit" formaction="addStudent">确定</button>
                            <button class="btn btn-default" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>



        <%--分页--%>
        <div class="pagination_div">
            <ul class="pagination">

                <c:set var="i" value="1" scope="page"></c:set>
                <c:set var="len" value="${totalPage.longValue()}" scope="page"></c:set>

                <li class="previous"><a href="showAllStudents?page=${i>1?--i:i}&queryID=${qId}&queryName=${qName}">&laquo;上一页</a></li>

                <c:forEach  var="j" begin="1" end="${len}">
                    <li><a href="showAllStudents?page=${j}&queryID=${qId}&queryName=${qName}" id=${j}> ${j} </a></li>
                </c:forEach>

                <li class="next"><a href="showAllStudents?page=${i<len?i+1:i}&queryID=${qId}&queryName=${qName}">&raquo;下一页</a></li>

                <li><a href="#">总共${len}页</a></li>
            </ul>


<%--                <%--%>
<%--                    // 声明一个局部变量i负责来实现当前页和上一页以及下一页的控制--%>
<%--                    int i = 1;--%>
<%--                    // 声明一个变量来获取总页数--%>
<%--                    Long totalRow = (Long) request.getAttribute("totalRow");--%>
<%--                    long len = totalRow.longValue() / 5 + 1;--%>
<%--                %>--%>

<%--                <li class="previous"><a href="showAllStudents?page=<%=(i>1?--i:i)%>">&laquo;上一页</a></li>--%>
<%--                <%--%>
<%--                    // 循环显示所有页码--%>
<%--                    for (int j = 1; j <= len; j++) {--%>
<%--                        // 当页码过多时需要使用...来代表，此时的编写思路就是采用条件判断--%>
<%--                        // 若j的数值大于某个范围且小于某个范围时，把打印j的位置换成...即可--%>
<%--                        // 当然随着当前页的改变也会随之改变--%>
<%--                        //if (j == i) {--%>

<%--                        // 初始显示第一页，第一页为active--%>
<%--                       // if (j == 1) {--%>
<%--                %>--%>
<%--&lt;%&ndash;                <li class="active"><a href="showAllStudents?page=<%=j%>" id=<%=j%>> <%=j%> </a></li>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <% } else {&ndash;%&gt;--%>
<%--&lt;%&ndash;                %>&ndash;%&gt;--%>

<%--                <li><a href="showAllStudents?page=<%=j%>" id=<%=j%>> <%=j%> </a></li>--%>

<%--&lt;%&ndash;                <%} %>&ndash;%&gt;--%>

<%--                &lt;%&ndash;<li class="active"><a href="showAllStudents?page=<%=i%>" id=<%=i%>> <%=i%> </a></li>&ndash;%&gt;--%>
<%--                <%--%>
<%--                        //}--%>
<%--                    }--%>
<%--                %>--%>
<%--                <li class="next"><a href="showAllStudents?page=<%=(i<len?++i:i)%>">&raquo;下一页</a></li>--%>
<%--                &lt;%&ndash;<li><a href="#">总共<%=len%>页</a></li>&ndash;%&gt;--%>
<%--            </ul>--%>
        </div>


    </div>

    <!--导入js-->
    <script src="./js/jquery-3.3.1.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>
    <%--下载bootstrap-select--%>
    <script src="./js/bootstrap-select.min.js"></script> <%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>--%>
    <script src="./js/script.js"></script>
</body>

</html>


<%--<div>--%>
<%--    <ul class="pagination">--%>
<%--        <li class="previous"> <a href="#"> &laquo; </a> </li>--%>
<%--        <li class="active" name="page" value="1"> <a href="#">1</a> </li>--%>
<%--        <li> <a href="#">2</a> </li>--%>
<%--        <li> <a href="#">3</a> </li>--%>
<%--        <li class="next"> <a href="#">&raquo;</a> </li>--%>
<%--    </ul>--%>
<%--</div>--%>