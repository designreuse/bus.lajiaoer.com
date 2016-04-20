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
    <style type="text/css">
    	.fileinput-button {
		  position: relative;
		  overflow: hidden;
		  //display: inline-block;
		  text-align: center;
		}
		.fileinput-button input {
		  position: absolute;
		  top: 0;
		  right: 0;
		  margin: 0;
		  opacity: 0;
		  -ms-filter: 'alpha(opacity=0)';
		  //font-size: 200px !important;
		  height: 100%;
		  width: 100%;
		  direction: ltr;
		  cursor: pointer;
		}
		.fileinput-button img {
			width: 100%;
			height: 100%;
		}
		
		/* Fixes for IE < 8 */
		@media screen\9 {
		  .fileinput-button input {
		    filter: alpha(opacity=0);
		    font-size: 100%;
		    height: 100%;
		  }
		}
    </style>

</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen   animated fadeInDown">
        <div>
        <%--
            <div>

                <h1 class="logo-name"><d:title /></h1>

            </div>
         --%>
            <h3>司机注册</h3>
            <p>创建账号了解更多</p>
            <form class="m-t" role="form" id="driver-signup-form" action="/driver/signup" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="cell" placeholder="手机号" required>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="name" placeholder="姓名" required>
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" name="password" placeholder="密码" required>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="license" placeholder="车牌号" required>
                </div>
                <div class="form-group">
                	<div class="ibox">
                		<div class="ibox-content no-padding border-left-right fileinput-button">
                			<img alt="image" class="image-responsive" src="\img\landing\sfz-a.svg">
	                		<input id="fileupload" type="file" name="files[]" multiple>
	                		<input type="hidden" name="sfza" value="">
                		</div>
                	</div>
                </div>
                <div class="form-group">
                	<div class="ibox">
                		<div class="ibox-content no-padding border-left-right fileinput-button">
                			<img alt="image" class="image-responsive" src="\img\landing\sfz-b.svg">
	                		<input id="fileupload2" type="file" name="files[]" multiple>
	                		<input type="hidden" name="sfzb" value="">
                		</div>
                	</div>
                </div>
                <div class="form-group">
                	<div class="ibox">
                		<div class="ibox-content no-padding border-left-right fileinput-button">
                			<img alt="image" class="image-responsive" src="\img\landing\jsz-a.svg">
	                		<input id="fileupload3" type="file" name="files[]" multiple>
	                		<input type="hidden" name="jsza" value="">
                		</div>
                	</div>
                </div>
                <div class="form-group">
                	<div class="ibox">
                		<div class="ibox-content no-padding border-left-right fileinput-button">
                			<img alt="image" class="image-responsive" src="\img\landing\xsz-a.svg">
	                		<input id="fileupload4" type="file" name="files[]" multiple>
	                		<input type="hidden" name="xsza" value="">
                		</div>
                	</div>
                </div>
                <div class="form-group">
                	<img alt="验证码" src="" style="display: none;" id="captcha-img">
                	<input type="text" class="form-control" name="captcha" placeholder="点击查看验证码" required>
                	<input type="hidden" id="alias" name="alias" value="">
                </div>
                <div class="form-group">
					<div class="checkbox i-checks"><label><input type="checkbox" name="agree"><i></i> 同意条款 </label></div>
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">注册</button>

                <p class="text-muted text-center"><small>已有账号？</small></p>
                <a class="btn btn-sm btn-white btn-block" href="/driver/login">登录</a>
            </form>
            <p class="m-t"> <small>版权所有 &copy;<d:title/> 2016</small> </p>
        </div>
    </div>

    <jsp:include page="../common/script.jsp" />
    <!-- iCheck -->
    <script src="http://static.lajiaoer.com\js\plugins\iCheck\icheck.min.js"></script>
    <!-- Jquery Validate -->
	<script src="http://static.lajiaoer.com\js\plugins\validate\jquery.validate.min.js"></script>
	<!-- jquery fileupload -->
	<script src="http://static.lajiaoer.com\js\plugins\jquery-ui\jquery.ui.widget.js"></script>
	<script src="http://static.lajiaoer.com\js\plugins\blueimp\jquery.fileupload.js"></script>
    <script>
        $(function() {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
            
            
            $('input[name=captcha]').one('focus', function() {
            	
            	$('#captcha-img').click(function() {
            		$(this).trigger('gen');
            	}).on('gen', function() {
                	$.get("/driver/captcha", function(result) {
                		$("#alias").val(result.alias);
               			$("#captcha-img").attr("src", result.base64);
   						$("#captcha-img").show();
   		            	$(this).attr("placeholder", "验证码");
                	}, "json");
                });
            	
            	$('#captcha-img').trigger('gen');
            });
            
            $.validator.addMethod("notexist", function(value, element) {
            	var deferred = $.Deferred();
            	$.ajax({
					url: "http://api.lajiaoer.com/rest/driver/signup/cell",
					data: {cell: $("input[name=cell]").val()},
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
            
            var validator = $('#driver-signup-form').validate({
            	ignore: ".ignore",
    			errorPlacement: function(error, element) {
    				element.parents(".form-group").prepend(error);
    			},
    			onkeyup: false,
    			messages: {
    				cell: {
    					required: "手机号必须填写",
    					digits: "请输入正确的手机号地址",
    					maxlength: "手机号超过最大长度11位",
    					minlength: "手机号长度为11位"
    				},
    				password: {
    					required: "密码必须填写",
    					maxlength: "密码超过最大长度32",
    	                minlength: "密码最小长度6"
    				},
    				name: {
    					required: "姓名必须填写",
    					maxlength: "姓名超过最大长度20",
    	                minlength: "姓名最小长度2"
    				},
    				license: {
    					required: "车牌号必须填写",
    					maxlength: "车牌号最大长度12",
    					minlength: "车牌号最小长度7"
    				},
    				agree: {
    					required: "请同意条款"
    				},
    				jsza: {
    					required: "请上传驾驶证正面图片"
    				},
    				sfza: {
    					required: "请上传身份证正面图片"
    				},
    				sfzb: {
    					required: "请上传身份证背面图片"
    				},
    				xsza: {
    					required: "请上传行驶证证明图片"
    				},
    				captcha: {
    					required: "必须输入验证码"
    				}
    			},
    			rules: {
    				cell: {
    					required: true,
    					digits: true,
    					maxlength: 11,
    					minlength: 11,
    					notexist: true
    				},
    				password: {
    					required: true,
    					maxlength: 32,
    					minlength: 6
    				},
    				name: {
    					required: true,
    					maxlength: 20,
    					minlength: 2
    				},
    				license: {
						required: true,
						maxlength: 12,
						minlength: 7
    				},
    				agree: {
    					required: true
    				},
    				jsza: {
    					required: true
    				},
    				sfza: {
    					required: true
    				},
    				sfzb: {
    					required: true
    				},
    				xsza: {
    					required: true
    				},
    				captcha: {
    					required: true
    				}
    			},
    			submitHandler: function(form) {
    				$.ajax({
    					url: "http://api.lajiaoer.com/rest/driver/signup",
    					data: $("#driver-signup-form").serialize(),
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
    							errors["cell"] = result.msg;
    						} else if (result.code == 10006) {
    							errors["name"] == result.msg;
    						} else if (result.code == 10012 || result.code == 10013) {
    							errors["captcha"] = result.msg;
    						} else if (result.code == 10014) {
								$('#captcha-img').trigger("gen");
								$('input[name=captcha]').val("");
								errors["captcha"] = result.msg;
							}
    						validator.showErrors(errors);
    					}
    				});
    			}
    		});
            
            $('#fileupload').fileupload({
                url: "/driver/image/upload",
                dataType: 'json',
                add: function(e, data) {
                	var $fu = $('#fileupload');
                	$fu.siblings(":hidden").val("");
                	data.submit();
                	$fu.siblings("img").attr("src", "/img/landing/uploading.svg");
                },
                done: function (e, data) {
                    //$.each(data.result.files, function (index, file) {
                    //    $('<p/>').text(file.name).appendTo('#files');
                    //});
                    //console.log(e, data);
                    var $fu = $('#fileupload');
                    $fu.siblings("img").attr("src", data.result.files[0].url);
                    $fu.siblings(":hidden").val(data.result.files[0].url);
                },
                progressall: function (e, data) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    //console.info(progress);
                    $('#progress .progress-bar').css(
                        'width',
                        progress + '%'
                    );
                }
            }).prop('disabled', !$.support.fileInput)
                .parent().addClass($.support.fileInput ? undefined : 'disabled');
            
            $('#fileupload2').fileupload({
                url: "/driver/image/upload",
                dataType: 'json',
                add: function(e, data) {
                	var $fu = $('#fileupload2');
                	$fu.siblings(":hidden").val("");
                	data.submit();
                	$fu.siblings("img").attr("src", "/img/landing/uploading.svg");
                },
                done: function (e, data) {
                    //$.each(data.result.files, function (index, file) {
                    //    $('<p/>').text(file.name).appendTo('#files');
                    //});
                    //console.log(e, data);
                    var $fu = $('#fileupload2');
                    $fu.siblings("img").attr("src", data.result.files[0].url);
                    $fu.siblings(":hidden").val(data.result.files[0].url);
                },
                progressall: function (e, data) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    //console.info(progress);
                    $('#progress .progress-bar').css(
                        'width',
                        progress + '%'
                    );
                }
            }).prop('disabled', !$.support.fileInput)
                .parent().addClass($.support.fileInput ? undefined : 'disabled');
            
            $('#fileupload3').fileupload({
                url: "/driver/image/upload",
                dataType: 'json',
                add: function(e, data) {
                	var $fu = $('#fileupload3');
                	$fu.siblings(":hidden").val("");
                	data.submit();
                	$fu.siblings("img").attr("src", "/img/landing/uploading.svg");
                },
                done: function (e, data) {
                    //$.each(data.result.files, function (index, file) {
                    //    $('<p/>').text(file.name).appendTo('#files');
                    //});
                    //console.log(e, data);
                    var $fu = $('#fileupload3');
                    $fu.siblings("img").attr("src", data.result.files[0].url);
                    $fu.siblings(":hidden").val(data.result.files[0].url);
                },
                progressall: function (e, data) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    //console.info(progress);
                    $('#progress .progress-bar').css(
                        'width',
                        progress + '%'
                    );
                }
            }).prop('disabled', !$.support.fileInput)
                .parent().addClass($.support.fileInput ? undefined : 'disabled');
            
            $('#fileupload4').fileupload({
                url: "/driver/image/upload",
                dataType: 'json',
                add: function(e, data) {
                	var $fu = $('#fileupload4');
                	$fu.siblings(":hidden").val("");
                	data.submit();
                	$fu.siblings("img").attr("src", "/img/landing/uploading.svg");
                },
                done: function (e, data) {
                    //$.each(data.result.files, function (index, file) {
                    //    $('<p/>').text(file.name).appendTo('#files');
                    //});
                    //console.log(e, data);
                    var $fu = $('#fileupload4');
                    $fu.siblings("img").attr("src", data.result.files[0].url);
                    $fu.siblings(":hidden").val(data.result.files[0].url);
                },
                progressall: function (e, data) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    //console.info(progress);
                    $('#progress .progress-bar').css(
                        'width',
                        progress + '%'
                    );
                }
            }).prop('disabled', !$.support.fileInput)
                .parent().addClass($.support.fileInput ? undefined : 'disabled');
        });
    </script>
</body>

</html>