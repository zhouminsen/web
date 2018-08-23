<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>form表单</title>
</head>

<body>
<form action="${pageContext.request.contextPath}/fromResubmit/submit2" method="post" onsubmit="preEvent()">
    用户名：<input type="text" name="username">
    <br>
    <input type="submit" value="提交" id="submit_id">
</form>
<script type="application/javascript">
    function preEvent() {
        document.getElementById("submit_id").setAttribute("disabled", "disabled");
    }
</script>
</body>
</html>