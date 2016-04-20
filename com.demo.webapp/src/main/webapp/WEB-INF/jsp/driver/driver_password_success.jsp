<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="d" uri="/WEB-INF/taglib/demo.tld" %>
<!DOCTYPE html>
<html>

<head>

    <jsp:include page="../common/meta.jsp" />

    <jsp:include page="../common/title.jsp">
    	<jsp:param value="忘记密码" name="title"/>
    </jsp:include>

	<jsp:include page="../common/link.jsp" />
	<jsp:include page="../common/favicon.jsp" />
</head>

<body class="gray-bg">

    <div class="passwordBox animated fadeInDown">
        <div class="row">

            <div class="col-md-12">
                <div class="ibox-content">

                    <h2 class="font-bold">忘记密码</h2>

                    <p>
                    	重置的密码已经发送到您的邮箱。<a class="btn btn-primary block full-width m-b" href="${pMailUrl}" target="_blank">打开邮箱</a>
                    </p>

                </div>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-md-6">
                Copyright <d:title/>
            </div>
            <div class="col-md-6 text-right">
               <small>© 2016</small>
            </div>
        </div>
    </div>

</body>

</html>