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

               <%-- <p>
                	变变变
                </p> --%>

            </div>
            <div class="col-md-6">
                <div class="ibox-content">
                    <form class="m-t" role="form" id="driver-login-form" action="/driver/login" method="post">
                    	<input type="hidden" name="ref" value="${ref}">
                        <div class="form-group">
                            <input type="text" name="cell" class="form-control" placeholder="手机号">
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
                        <a class="btn btn-sm btn-white btn-block" href="/driver/signup">创建账号</a>
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
		
		var validator = $('#driver-login-form').validate({
			errorPlacement: function(error, element) {
				element.before(error);
			},
			messages: {
				cell: {
					required: "手机号必须填写",
					digits: "请输入正确的手机号",
					maxlength: "手机号超过最大长度11位",
					minlength: "手机号长度为11位"
				},
				password: {
					required: "密码必须填写",
					maxlength: "密码超过最大长度32",
	                minlength: "密码最小长度6"
				}
			},
			rules: {
				cell: {
					required: true,
					maxlength: 11,
					minlength: 11,
					digits: true
				},
				password: {
					required: true,
					maxlength: 32,
					minlength: 6
				}
			},
			submitHandler: function(form) {
				$.ajax({
					url: "http://api.lajiaoer.com/rest/driver/login",
					data: $("#driver-login-form").serialize(),
					type: "post",
					dataType: "json",
					xhrFields:{
						withCredentials:true
					},
					crossDomain:true,
					success: function(result) {
						if (result.code == 200) {
							form.submit();
							return;
						}
						
						var errors = {};
						if (result.code == 10004 || result.code == 10002) {
							errors["password"] = result.msg;
						} else if (result.code == 10001 || result.code == 10003 || result.code == 10007 || result.code == 10008) {
							errors["cell"] = result.msg;
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