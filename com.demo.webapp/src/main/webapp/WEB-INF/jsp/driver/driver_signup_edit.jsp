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
    	<jsp:param value="编辑司机信息" name="title"/>
    </jsp:include>

	<jsp:include page="../common/link.jsp" />
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
            <h3>编辑司机信息</h3>
            <form class="m-t" role="form" id="driver-signup-form" action="/driver/signup/edit" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="cell" placeholder="手机号" value="${driver.cell }" required="required" readonly="readonly">
                </div>
                <div class="form-group">
                	<c:if test="${driverAudit.name eq 2}">
                    	<span class="help-block m-b-none text-left text-danger">${driverAudit.nameReject}</span>
                    </c:if>
                    <c:if test="${driverAudit.name eq 0}">
                    	<span class="help-block m-b-none text-left text-danger">等待审核</span>
                    </c:if>
                    <input type="text" class="form-control" name="name" placeholder="姓名" value="${driver.name }" required="required" <c:if test="${driverAudit.name eq 1}">readonly="readonly"</c:if>>
                </div>
                <div class="form-group">
                	<c:if test="${driverAudit.licensePlate eq 2}">
                    	<span class="help-block m-b-none text-left text-danger">${driverAudit.licensePlateReject}</span>
                    </c:if>
                    <c:if test="${driverAudit.licensePlate eq 0}">
                    	<span class="help-block m-b-none text-left text-danger">等待审核</span>
                    </c:if>
                    <input type="text" class="form-control" name="license" placeholder="车牌号" value="${driver.licensePlate }" required="required" <c:if test="${driverAudit.licensePlate eq 1}">readonly="readonly"</c:if>>
                </div>
                <div class="form-group">
                <c:choose>
                	<c:when test="${driverAudit.sfzA eq 2}">
                    	<span class="help-block m-b-none text-left text-danger">${driverAudit.sfzAReject}</span>
                    	<div class="ibox">
	                		<div class="ibox-content no-padding border-left-right fileinput-button">
	                			<img alt="image" class="image-responsive" src="${driver.sfzA }">
		                		<input id="fileupload" type="file" name="files[]" multiple>
		                		<input type="hidden" name="sfza" value="${driver.sfzA }">
	                		</div>
	                	</div>
                    </c:when>
                    <c:when test="${driverAudit.sfzA eq 0}">
                    	<span class="help-block m-b-none text-left text-danger">等待审核</span>
                    	<div class="ibox">
	                		<div class="ibox-content no-padding border-left-right fileinput-button">
	                			<img alt="image" class="image-responsive" src="${driver.sfzA }">
		                		<input type="hidden" name="sfza" value="${driver.sfzA }">
	                		</div>
	                	</div>
                    </c:when>
                    <c:otherwise>
                	<div class="ibox">
                		<div class="ibox-content no-padding border-left-right fileinput-button">
                			<img alt="image" class="image-responsive" src="${driver.sfzA }">
	                		<input type="hidden" name="sfza" value="${driver.sfzA }">
                		</div>
                	</div>
                	</c:otherwise>
                </c:choose>
                </div>
                <div class="form-group">
                <c:choose>
                	<c:when test="${driverAudit.sfzB eq 2}">
                    	<span class="help-block m-b-none text-left text-danger">${driverAudit.sfzBReject}</span>
                    	<div class="ibox">
	                		<div class="ibox-content no-padding border-left-right fileinput-button">
	                			<img alt="image" class="image-responsive" src="${driver.sfzB }">
		                		<input id="fileupload2" type="file" name="files[]" multiple>
		                		<input type="hidden" name="sfzb" value="${driver.sfzB }">
	                		</div>
	                	</div>
                    </c:when>
                    <c:when test="${driverAudit.sfzB eq 0}">
                    	<span class="help-block m-b-none text-left text-danger">等待审核</span>
                    	<div class="ibox">
	                		<div class="ibox-content no-padding border-left-right fileinput-button">
	                			<img alt="image" class="image-responsive" src="${driver.sfzB }">
		                		<input type="hidden" name="sfzb" value="${driver.sfzB }">
	                		</div>
	                	</div>
                    </c:when>
                	<c:otherwise>
                		<div class="ibox">
	                		<div class="ibox-content no-padding border-left-right fileinput-button">
	                			<img alt="image" class="image-responsive" src="${driver.sfzB }">
		                		<input type="hidden" name="sfzb" value="${driver.sfzB }">
	                		</div>
	                	</div>
                	</c:otherwise>
                </c:choose>
                </div>
                <div class="form-group">
                <c:choose>
                	<c:when test="${driverAudit.jszA eq 2}">
                    	<span class="help-block m-b-none text-left text-danger">${driverAudit.jszAReject}</span>
                    	<div class="ibox">
	                		<div class="ibox-content no-padding border-left-right fileinput-button">
	                			<img alt="image" class="image-responsive" src="${driver.jszA }">
		                		<input id="fileupload3" type="file" name="files[]" multiple>
		                		<input type="hidden" name="jsza" value="${driver.jszA }">
	                		</div>
	                	</div>
                    </c:when>
                    <c:when test="${driverAudit.jszA eq 0}">
                    	<span class="help-block m-b-none text-left text-danger">等待审核</span>
                    	<div class="ibox">
	                		<div class="ibox-content no-padding border-left-right fileinput-button">
	                			<img alt="image" class="image-responsive" src="${driver.jszA }">
		                		<input type="hidden" name="jsza" value="${driver.jszA }">
	                		</div>
	                	</div>
                    </c:when>
                    <c:otherwise>
                	<div class="ibox">
                		<div class="ibox-content no-padding border-left-right fileinput-button">
                			<img alt="image" class="image-responsive" src="${driver.jszA }">
	                		<input type="hidden" name="jsza" value="${driver.jszA }">
                		</div>
                	</div>
                	</c:otherwise>
                </c:choose>
                </div>
                <div class="form-group">
                <c:choose>
                	<c:when test="${driverAudit.xszA eq 2}">
                    	<span class="help-block m-b-none text-left text-danger">${driverAudit.xszAReject}</span>
                    	<div class="ibox">
	                		<div class="ibox-content no-padding border-left-right fileinput-button">
	                			<img alt="image" class="image-responsive" src="${driver.xszA }">
		                		<input id="fileupload4" type="file" name="files[]" multiple>
		                		<input type="hidden" name="xsza" value="${driver.xszA }">
	                		</div>
	                	</div>
                    </c:when> 
                    <c:when test="${driverAudit.xszA eq 0}">
                    	<span class="help-block m-b-none text-left text-danger">等待审核</span>
                    	<div class="ibox">
	                		<div class="ibox-content no-padding border-left-right fileinput-button">
	                			<img alt="image" class="image-responsive" src="${driver.xszA }">
		                		<input type="hidden" name="xsza" value="${driver.xszA }">
	                		</div>
	                	</div>
                    </c:when>
                    <c:otherwise>
                    	<div class="ibox">
	                		<div class="ibox-content no-padding border-left-right fileinput-button">
	                			<img alt="image" class="image-responsive" src="${driver.xszA }">
		                		<input type="hidden" name="xsza" value="${driver.xszA }">
	                		</div>
	                	</div>
                    </c:otherwise>
                    </c:choose>
                </div>
                <c:if test="${pass eq false}">
                <button type="submit" class="btn btn-primary block full-width m-b">保存</button>
				</c:if>
            </form>
            <p class="m-t"> <small>版权所有 &copy;<d:title/> 2016</small> </p>
        </div>
    </div>

    <jsp:include page="../common/script.jsp" />
    <!-- Jquery Validate -->
	<script src="http://static.lajiaoer.com\js\plugins\validate\jquery.validate.min.js"></script>
	<!-- jquery fileupload -->
	<script src="http://static.lajiaoer.com\js\plugins\jquery-ui\jquery.ui.widget.js"></script>
	<script src="http://static.lajiaoer.com\js\plugins\blueimp\jquery.fileupload.js"></script>
    <script>
        $(function() {
            
            var validator = $('#driver-signup-form').validate({
            	ignore: ".ignore",
    			errorPlacement: function(error, element) {
    				element.parents(".form-group").prepend(error);
    			},
    			onkeyup: false,
    			messages: {
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
    				}
    			},
    			rules: {
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
    				}
    			},
    			submitHandler: function(form) {
    				$.ajax({
    					url: "http://api.lajiaoer.com/rest/driver/signup",
    					data: $("#driver-signup-form").serialize(),
    					type: "put",
    					dataType: "json",
    					success: function(result) {
    						if (result.code == 200) {
    							form.submit();
    							return;
    						}
    						var errors = {};
    						if (result.code == 10017 || result.code == 10018) {
    							errors['cell'] = result.msg;
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
                	$fu.siblings("img").attr("src", "http://static.lajiaoer.com/img/landing/uploading.svg");
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
                	$fu.siblings("img").attr("src", "http://static.lajiaoer.com/img/landing/uploading.svg");
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
                	$fu.siblings("img").attr("src", "http://static.lajiaoer.com/img/landing/uploading.svg");
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
                	$fu.siblings("img").attr("src", "http://static.lajiaoer.com/img/landing/uploading.svg");
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