<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>form表单</title>
</head>

<body>
<form action="${pageContext.request.contextPath}/fromResubmit/submit" method="post">
    <%--使用EL表达式取出存储在session中的token,并存放在表单隐藏域中--%>
    <input type="hidden" name="token" value="${token}"/>
    用户名：<input type="text" name="username">
    <input type="submit" value="提交">
</form>
</body>
</html>