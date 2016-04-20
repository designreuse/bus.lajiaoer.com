<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="d" uri="/WEB-INF/taglib/demo.tld" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../common/meta.jsp" />

    <jsp:include page="../common/title.jsp">
    	<jsp:param value="注册成功" name="title"/>
    </jsp:include>

	<jsp:include page="../common/link.jsp" />
    <link href="http://static.lajiaoer.com\email_templates\styles.css" media="all" rel="stylesheet" type="text/css">
    
    <jsp:include page="../common/favicon.jsp" />
</head>
<body>
<table class="body-wrap">
    <tr>
        <td></td>
        <td class="container" width="600">
            <div class="content">
                <table class="main" style="width: 100%; border-collapse: collapse; border-spacing: 0;">
                    <tr>
                        <td class="alert alert-good">
                            	注册成功
                        </td>
                    </tr>
                    <tr>
                        <td class="content-wrap">
                            <table style="width: 100%; border-collapse: collapse; border-spacing: 0;">
                                <tr>
                                    <td class="content-block">
                                        	请稍等，<d:title/>工作人员会电话联系。
                                    </td>
                                </tr>
<%--
                                <tr>
                                    <td class="content-block">
                                        <a href="#" class="btn-primary">Upgrade my account</a>
                                    </td>
                                </tr>
--%>
                                <tr>
                                    <td class="content-block">
                                       	 感谢您选择<d:title />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <div class="footer">
                   <%--  
					<table width="100%">
                        <tr>
                            <td class="aligncenter content-block"><a href="#">Unsubscribe</a> from these alerts.</td>
                        </tr>
                    </table>
					--%>
                </div></div>
        </td>
        <td></td>
    </tr>
</table>
</body>
</html>