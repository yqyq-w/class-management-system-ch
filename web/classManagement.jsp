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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page contentType="text/html;charset=utf-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>班级信息管理页面</title>
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/main.css"> <!--引入样式-->
</head>

<%--<style type="text/css">--%>
<%--    .pagination_div {--%>
<%--        margin: 0 auto;--%>
<%--        display: flex;--%>
<%--        justify-content: center;--%>
<%--        align-items: center;--%>
<%--    }--%>
<%--</style>--%>

<body>

<div class="container">
    <div>
        <div class="h4 centerT">当前在线人数为：${applicationScope.count}</div>
        <div class="h4 centerT">
            <%--新登录用户名sessionScope.user["userName"]，或者七天内有效的cookie.userName.value--%>
            登录成功，欢迎${null == sessionScope.user["userName"] ? cookie.userName.value: sessionScope.user["userName"] }使用！
        </div>
        <div class="h3 centerT">班级信息列表</div>
    </div>

    <c:set var="qName" value="${queryInfo[0]}" scope="page"></c:set>

    <!--按钮和搜索框-->
    <form id="form_1" method="post">
        <div class="form-group">
            <input type="button" class="btn btn-success" value="创建班级" id="add" data-toggle="modal" data-target="#addModal">
            <input type="button" class="btn btn-success" value="删除班级" id="delete" data-toggle="modal" data-target="#deleteModal">
            <%--   保证班级内没有关联学生 size==0 where id=cid         --%>
            <input type="button" class="btn btn-success" value="修改班级" id="editC" data-toggle="modal" data-target="#editModal">
            <%-- formaction属性设置点击查询按钮后的action地址 --%>
            <input type="submit" class="btn btn-success" formaction="showAllClasses?page=1" value="查询班级" id="query">
           <input value="${qName}" type="text" class="form-inline" placeholder="按关键词查询" id="queryName" name="queryName" style="width: 6.5em;">
            <%-- formaction属性设置点全部显示按钮后的action地址 --%>
            <input type="submit" class="btn btn-info" formaction="showAllClasses?page=1&queryName=" value="全部班级" id="reset">
            <%--切换--%>
            <input type="submit" class="btn btn-warning" formaction="switchMng" name="switchTo" value="学生管理" id="manageStu">

        </div>
    </form>

    <form id="form_2" method="post">
        <%-- 如有error进行提示 --%>
<%--        <div> <span style="color: red">${error==null?"":error}</span><br/></div>--%>
<%--            <div> <span style="color: black"><c:if test="${!empty deletedLen}"> ${fn:length(deletedLen)} </c:if> </span><br/></div>--%>

        <!--班级名称、年级、班主任名称、班级口号 、班级人数-->
        <table class="table table-responsive">
            <thead>
            <tr>
                <th>课号</th>
                <th>班级名称</th>
                <th>年级</th>
                <th>班主任名称</th>
                <th>班级口号</th>
                <th>班级人数</th>
            </tr>
            </thead>

            <!--具体学生信息-->
            <tbody>

            <c:forEach var="tc" items="${classes}" >
                <tr>
                    <td>
                        <input type="checkbox" name="chosen" value="${tc.id}" /> ${tc.id}
                    </td>
                    <td class="name">${tc.cName}</td>
                    <td>${tc.grade}</td>
                    <td>${tc.teacher}</td>
                    <td>${tc.slogan}</td>
                    <td class="size">${tc.size}</td>
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
                        <h4 class="modal-title">删除班级信息</h4>
                    </div>
                    <!-- 2.框内信息 -->
                    <div class="modal-body">
                        <div style="padding-left: 25%">
                            <img src="./img/warning.jpeg" alt="震惊脸" style="width: 20%;">
                            <span class="h5"> &nbsp;<mark style="background-color: khaki;">&nbsp;&nbsp;确定要删除吗？</mark></span>
                        </div>
                    </div>
                    <!-- 3.框的按钮 -->
                    <div class="modal-footer">
                        <%-- 确认删除后提交到formaction="deleteStudent" --%>
                        <button class="btn btn-success" id="deleteSubmit" formaction="deleteClass" >确定</button><button class="btn btn-default" data-dismiss="modal">取消</button>
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
                                <input placeholder="请修改该班级的名称..." id="cName2" name="cName" />
                            </p>
                            <p>
                                <input placeholder="请修改该班级的年级..." id="grade2" name="grade"/>
                            </p>

                            <p>
                                <input placeholder="请修改该班级的班主任..." id="teacher2" name="teacher"/>
                            </p>
                            <p>
                                <input placeholder="请修改该班级的口号..." id="slogan2" name="slogan" />
                            </p>
                        </div>
                    </div>
                    <!-- 3.框的按钮 -->
                    <div class="modal-footer">
                        <%-- 确认修改后提交到formaction="modifyClass" --%>
                        <button class="btn btn-success" id="editSubmit" formaction="modifyClass">确定</button><button class="btn btn-default" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <form id="form_3" action="addClass" method="post">
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
                        <h4 class="modal-title">增加班级信息</h4>
                    </div>
                    <!-- 2.框内信息 -->
                    <div class="modal-body">
                        <div style="padding-left: 25%">
                            <div class="form-group">

                                <input class="mustWrite" placeholder="请输入新班级的名称..." id="cname" name="cName" />
                                </p>
                                <p>
                                    <input class="mustWrite" placeholder="请输入新班级的年级..." id="grade" name="grade"/>
                                </p>

                                <p>
                                    <input class="mustWrite" placeholder="请输入新班级的班主任..." id="teacher" name="teacher"/>
                                </p>
                                <p>
                                    <input class="mustWrite" placeholder="请输入新班级的口号..." id="slogan" name="slogan" />
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- 3.框的按钮 -->
                    <div class="modal-footer">
                        <%--                --%>
                        <button class="btn btn-success" id="addSubmit" formaction="addClass">确定</button>
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

            <li class="previous"><a href="showAllClasses?page=${i>1?--i:i}&queryName=${qName}">&laquo;上一页</a></li>

            <c:forEach  var="j" begin="1" end="${len}">
                <li><a href="showAllClasses?page=${j}&queryName=${qName}" id=${j}> ${j} </a></li>
            </c:forEach>

            <li class="next"><a href="showAllClasses?page=${i<len?i+1:i}&queryName=${qName}">&raquo;下一页</a></li>

            <li><a href="#">总共${len}页</a></li>
        </ul>

    </div>


</div>

<!--导入js-->
<script src="./js/jquery-3.3.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/script.js"></script>
</body>

</html>

