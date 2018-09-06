<%--
  Created by IntelliJ IDEA.
  User: unbea
  Date: 2018/7/31
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user/login.action" method="post">
        用户名： <input type="text" name="username"> <br/>
        密码： <input type="text" name="password">
        <input type="submit" value="提交">
    </form>

</body>
</html>
