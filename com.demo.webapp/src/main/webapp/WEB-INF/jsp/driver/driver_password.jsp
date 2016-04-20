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
                    	输入手机号，您的密码将会重置后发送给您。
                    </p>

                    <div class="row">

                        <div class="col-lg-12">
                            <form class="m-t" role="form" id="driver-password-form" action="/driver/password" method="post">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="cell" placeholder="手机号" required>
                                </div>

                                <button type="submit" class="btn btn-primary block full-width m-b">发送新密码</button>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-md-6">
                Copyright <d:title />
            </div>
            <div class="col-md-6 text-right">
               <small>© 2016</small>
            </div>
        </div>
    </div>

	<jsp:include page="../common/script.jsp" />
	<!-- Jquery Validate -->
	<script src="http://static.lajiaoer.com\js\plugins\validate\jquery.validate.min.js"></script>
	<script type="text/javascript">
	$(function() {
		
		var validator = $('#driver-password-form').validate({
			errorPlacement: function(error, element) {
				element.before(error);
			},
			messages: {
				cell: {
					required: "手机号必须填写",
					email: "请输入正确的手机号",
					maxlength: "手机号超过最大长度11位",
					minlength: "手机号长度为11位",
					digits: "手机号格式不正确"
				}
			},
			rules: {
				cell: {
					required: true,
					digits: true,
					email: true,
					maxlength: 11,
					minlength: 11
				}
			}
		});
	});
	</script>
</body>

</html>