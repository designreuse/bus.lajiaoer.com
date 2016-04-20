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
    	<jsp:param value="注册" name="title"/>
    </jsp:include>

	<jsp:include page="../common/link.jsp" />
    <link href="http://static.lajiaoer.com\css\plugins\iCheck\custom.css" rel="stylesheet">
    <jsp:include page="../common/favicon.jsp" />

</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen   animated fadeInDown">
        <div>
        <%--
            <div>

                <h1 class="logo-name"><d:title /></h1>

            </div>
             --%>
            <h3>注册</h3>
            <p>创建账号了解更多</p>
            <form class="m-t" role="form" id="signup-form" action="/signup" method="post">
                <div class="form-group">
                    <input type="email" class="form-control" name="email" placeholder="电子邮箱" required>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="username" placeholder="姓名" required>
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" name="password" placeholder="密码" required>
                </div>
                <div class="form-group">
					<div class="checkbox i-checks"><label><input type="checkbox" name="agree"><i></i> 同意条款 </label></div>
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">注册</button>

                <p class="text-muted text-center"><small>已有账号？</small></p>
                <a class="btn btn-sm btn-white btn-block" href="/login">登录</a>
            </form>
            <p class="m-t"> <small>版权所有 &copy; 2016</small> </p>
        </div>
    </div>

    <jsp:include page="../common/script.jsp" />
    <!-- iCheck -->
    <script src="http://static.lajiaoer.com\js\plugins\iCheck\icheck.min.js"></script>
    <!-- Jquery Validate -->
	<script src="http://static.lajiaoer.com\js\plugins\validate\jquery.validate.min.js"></script>
    <script>
        $(function() {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
            
            $.validator.addMethod("notexist", function(value, element) {
            	var deferred = $.Deferred();
            	$.ajax({
					url: "http://api.lajiaoer.com/rest/ajax/user/valid/signup/email",
					data: {email: $("input[name=email]").val()},
					type: "post",
					async: false,
					dataType: "json",
					success: function(result) {
						if (result.code == 200) {
							deferred.resolve();
						} else if (result.code == 10001 || result.code == 10005) {
							$.validator.messages.notexist = result.msg;
							deferred.reject();
						}
					}
				});
            	
            	return deferred.state() == "resolved" ? true : false;
            }, $.validator.messages.notexist);
            
            var validator = $('#signup-form').validate({
    			errorPlacement: function(error, element) {
    				element.parents(".form-group").prepend(error);
    			},
    			onkeyup: false,
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
    				},
    				username: {
    					required: "姓名必须填写",
    					maxlength: "姓名超过最大长度20",
    	                minlength: "姓名最小长度2"
    				},
    				agree: {
    					required: "请同意条款"
    				}
    			},
    			rules: {
    				email: {
    					required: true,
    					email: true,
    					maxlength: 200,
    					notexist: true,
    					onkeyup: false
    				},
    				password: {
    					required: true,
    					maxlength: 32,
    					minlength: 6
    				},
    				username: {
    					required: true,
    					maxlength: 20,
    					minlength: 2
    				},
    				agree: {
    					required: true
    				}
    			},
    			submitHandler: function(form) {
    				$.ajax({
    					url: "http://api.lajiaoer.com/rest/ajax/user/valid/signup",
    					data: $("#signup-form").serialize(),
    					type: "post",
    					dataType: "json",
    					success: function(result) {
    						if (result.code == 200) {
    							form.submit();
    							return;
    						}
    						
    						var errors = {};
    						if (result.code == 10002) {
    							errors["password"] = result.msg;
    						} else if (result.code == 10001 || result.code == 10005) {
    							errors["email"] = result.msg;
    						} else if (result.code == 10006) {
    							errors["username"] == result.msg;
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