<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" trimDirectiveWhitespaces="true"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="zh">
<head>

    <jsp:include page="../common/meta.jsp" />
    
    <jsp:include page="../common/title.jsp">
    	<jsp:param value="500" name="title"/>
    </jsp:include>
    
    <jsp:include page="../common/link.jsp" />
    <jsp:include page="../common/favicon.jsp" />
    
</head>
<body class="gray-bg">

    <div class="middle-box text-center animated fadeInDown">
        <h1>500</h1>
        <h3 class="font-bold">出错啦</h3>

        <div class="error-desc">
        	<c:choose>
        		<c:when test="${not empty detailMessage}">
        			原因：${detailMessage}<br>
        		</c:when>
        		<c:otherwise>
		        	非常抱歉，服务器发生了一些错误。<br>
        		</c:otherwise>
        	</c:choose>
           	<a href="/" class="btn btn-primary m-t">返回首页</a>
        </div>
    </div>

    <jsp:include page="../common/script.jsp" />

</body>

</html>