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
    	<jsp:param value="登录" name="title"/>
    </jsp:include>

	<jsp:include page="../common/link.jsp" />
	<jsp:include page="../common/favicon.jsp" />
	
</head>

<body class="gray-bg">

    <div class="loginColumns animated fadeInDown">
        <div class="row">

            <div class="col-md-6">
                <h2 class="font-bold">欢迎来到<d:title /></h2>


            </div>
            <div class="col-md-6">
                <div class="ibox-content">
                    <form class="m-t" role="form" id="login-form" action="/login" method="post">
                    	<input type="hidden" name="ref" value="${ref}">
                        <div class="form-group">
                            <input type="email" name="email" class="form-control" placeholder="电子邮箱">
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" class="form-control" placeholder="密码">
                        </div>
                        <button type="submit" class="btn btn-primary block full-width m-b ladda-button ladda-button-demo" data-style="zoom-in">登录</button>

                        <a href="#">
                            <small>忘记密码?</small>
                        </a>

                        <p class="text-muted text-center">
                            <small>没有账号?</small>
                        </p>
                        <a class="btn btn-sm btn-white btn-block" href="/signup">创建账号</a>
                    </form>
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
		
		var validator = $('#login-form').validate({
			errorPlacement: function(error, element) {
				element.before(error);
			},
			messages: {
				email: {
					required: "邮箱必须填写",
					email: "请输入正确的邮箱地址",
					maxlength: "邮箱超过最大长度200"
				},
				password: {
					required: "密码必须填写",
					maxlength: "密码超过最大长度32",
	                minlength: "密码最小长度6"
				}
			},
			rules: {
				email: {
					required: true,
					email: true,
					maxlength: 200
				},
				password: {
					required: true,
					maxlength: 32,
					minlength: 6
				}
			},
			submitHandler: function(form) {
				$.ajax({
					url: "http://api.lajiaoer.com/rest/ajax/user/valid/login",
					data: $("#login-form").serialize(),
					type: "post",
					dataType: "json",
					success: function(result) {
						if (result.code == 200) {
							form.submit();
							return;
						}
						
						var errors = {};
						if (result.code == 10004 || result.code == 10002) {
							errors["password"] = result.msg;
						} else if (result.code == 10001 || result.code == 10003 || result.code == 10007 || result.code == 10008) {
							errors["email"] = result.msg;
						}
						validator.showErrors(errors);
					}
				});
			}
		});
	});
	</script>
</body>

</html>