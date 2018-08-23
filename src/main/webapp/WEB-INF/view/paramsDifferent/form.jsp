<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>form表单</title>
</head>

<body>
<form action="${pageContext.request.contextPath}/params/submit" method="post">
    用户名：<input type="text" name="username">
    年龄: <input type="text" name="age">
    <input type="submit" value="post提交">
</form>

<form action="${pageContext.request.contextPath}/params/submit" method="get">
    用户名：<input type="text" name="username">
    年龄: <input type="text" name="age">
    <input type="submit" value="get提交">
</form>

<input type="button" value="submit2提交" onclick="submit2()">
<input type="button" value="submit3提交" onclick="submit3()">

</body>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script !src="">
    function submit2() {
        var url = "${pageContext.request.contextPath}/params/submit2";
        var params = {};
        params.username = "zjw";
        params.age = 11;
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(params),
            dataType: "text",
            error: function (data) {
                alert(data);
            },
            success: function (data) {
                alert(data);
            }
        });
    }
    function submit3() {
        var url = "${pageContext.request.contextPath}/params/submit3";
        var params = {};
        params.username = "zjw";
        params.age = 11;
        $.ajax({
            type: "POST",
            url: url,
            contentType: "text/plain; charset=utf-8",
            data: JSON.stringify(params),
            dataType: "text",
            error: function (data) {
                alert(data);
            },
            success: function (data) {
                alert(data);
            }
        });
    }
</script>
</html>