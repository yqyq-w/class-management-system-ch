<%--
 * assn 402
 * yiqingw
--%>

<%@ page contentType="text/html;charset=utf-8" language="java" %>

<%@ page import="bean.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>管理员登录界面</title>
  <link rel="stylesheet" href="./css/bootstrap.min.css">
  <link rel="stylesheet" href="./css/login.css"> <!--引入样式-->
</head>
  <body>

  <%-- 判断是否存在用户名cookie，存在就输出,不存在跳转到登录页面--%>
    <c:if test="${null != cookie.userName}">
      <% response.sendRedirect(request.getContextPath() + "/showAllStudents"); %>
    </c:if>

    <div class="container">
      <h2>登录</h2>
      <form action="login" method="post" class="form-box">
        <div class="inputBox">
          <input type="text" name="userName" required="required">
          <label>用户名</label>
        </div>
        <div class="inputBox">
          <input type="password" name="password" required="required">
          <label>密&nbsp;&nbsp;&nbsp;&nbsp;码</label>
        </div>
        <span style="color: red">${error==null?"":error}</span><br/>
        <input type="submit" name="" value="登录">
     </form>
    </div>

  </body>
</html>




<%--  <%--%>
<%--    //判断是否存在用户名cookie,存在就输出,不存在跳转到登录页面--%>
<%--    Cookie cookie = null;--%>
<%--    Cookie[] cookies = null;--%>
<%--    cookies = request.getCookies();--%>
<%--    cookie = getCookieByName(cookies, "userName");--%>
<%--    if (cookie != null) {--%>
<%--//      out.print(cookie.getValue());--%>
<%--      response.sendRedirect(request.getContextPath() + "/showAllStudents");--%>
<%--    }--%>
<%--  %>--%>

<%--  <%!// 创建方法,用于查找指定名称的cookie--%>
<%--    public static Cookie getCookieByName(Cookie[] cookies, String name) {--%>
<%--      if (null != cookies && 0 != cookies.length) {--%>
<%--        for (Cookie c : cookies) {--%>
<%--          if (name.equals(c.getName())) {--%>
<%--            return c;--%>
<%--          }--%>
<%--        }--%>
<%--      }--%>
<%--      return null;--%>
<%--    }%>--%>



<%--  --%>
<%--  <div class="container" style="">--%>
<%--    <div>--%>
<%--      <form action="login" method="post">--%>
<%--        用户名：<input type="text" name="userName"/><br/>--%>
<%--        密&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="text" name="password"/><br/>--%>
<%--        <span style="color: red"><%= request.getAttribute("error")==null?"":request.getAttribute("error")%></span><br/>--%>
<%--        <input type="submit" value="登录"/>--%>
<%--      </form>--%>
<%--    </div>--%>
<%--  </div> --%>