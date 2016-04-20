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
    	<jsp:param value="页面未找到" name="title"/>
    </jsp:include>
    
    <jsp:include page="../common/link.jsp" />
    <jsp:include page="../common/favicon.jsp" />

</head>

<body class="gray-bg">


    <div class="middle-box text-center animated fadeInDown">
        <h1>404</h1>
        <h3 class="font-bold">页面未找到</h3>

        <div class="error-desc">
        	<c:choose>
        		<c:when test="${not empty detailMessage}">
        			原因：${detailMessage}
        		</c:when>
        		<c:otherwise>
		        	抱歉，您要访问的页面不存在。
        		</c:otherwise>
        	</c:choose>
            <form class="form-inline m-t" role="form">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="请输入查询内容">
                </div>
                <button type="submit" class="btn btn-primary">查询</button>
            </form>
        </div>
    </div>

    <jsp:include page="../common/script.jsp" />

</body>

</html>