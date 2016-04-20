<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="d" uri="/WEB-INF/taglib/demo.tld" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><d:title/> | 后台登录</title>

    <link href="http://static.lajiaoer.com/css\bootstrap.min.css" rel="stylesheet">
    <link href="http://static.lajiaoer.com/font-awesome\css\font-awesome.css" rel="stylesheet">

    <link href="http://static.lajiaoer.com/css\animate.css" rel="stylesheet">
    <link href="http://static.lajiaoer.com/css\style.css" rel="stylesheet">

</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>
            <h3>欢迎来到<d:title/> 后台管理</h3>
            <p>请先登录，再进行操作</p>
            <c:if test="${not empty error }">
            <div class="alert alert-danger">
            	${error }
            </div>
            </c:if>
            <form class="m-t" role="form" action="/admin/login" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="用户名" name="username" value="${username}" required>
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="密码" name="password" value="${password}" required>
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">登录</button>

            </form>
            <p class="m-t"> <small><d:title/> &copy; 2016</small> </p>
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="http://static.lajiaoer.com/js\jquery-2.1.1.js"></script>
    <script src="http://static.lajiaoer.com/js\bootstrap.min.js"></script>

</body>

</html>
