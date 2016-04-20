<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="d" uri="/WEB-INF/taglib/demo.tld" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><d:title/> | 司机详情</title>

    <link href="http://static.lajiaoer.com/css\bootstrap.min.css" rel="stylesheet">
    <link href="http://static.lajiaoer.com/font-awesome\css\font-awesome.css" rel="stylesheet">
	<!-- Toastr style -->
    <link href="http://static.lajiaoer.com/css\plugins\toastr\toastr.min.css" rel="stylesheet">
	<!-- Sweet Alert -->
    <link href="http://static.lajiaoer.com/css\plugins\sweetalert\sweetalert.css" rel="stylesheet">
    <link href="http://static.lajiaoer.com/css\plugins\blueimp\css\blueimp-gallery.min.css" rel="stylesheet">
    <!-- Ladda style -->
    <link href="http://static.lajiaoer.com/css\plugins\ladda\ladda-themeless.min.css" rel="stylesheet">
    
    <link href="http://static.lajiaoer.com/css\plugins\select2\select2.min.css" rel="stylesheet">
    <link href="http://static.lajiaoer.com/css\plugins\awesome-bootstrap-checkbox\awesome-bootstrap-checkbox.css" rel="stylesheet">
    

    <link href="http://static.lajiaoer.com/css\animate.css" rel="stylesheet">
    <link href="http://static.lajiaoer.com/css\style.css" rel="stylesheet">
    

</head>

<body>

    <div id="wrapper">

    	<jsp:include page="../leftmenu.jsp" />

        <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
        <jsp:include page="../topmenu.jsp" />
        </div>
            <div class="row wrapper border-bottom white-bg page-heading">
                <div class="col-sm-4">
                    <h2>司机审核详情</h2>
                    <ol class="breadcrumb">
                        <li>
                            <a href="index.html">首页</a>
                        </li>
                        <li>
                        	<a href="/admin/drivers">司机审核列表</a>
                        </li>
                        <li class="active">
                            <strong>司机审核详情</strong>
                        </li>
                    </ol>
                </div>
            </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox">
                        <div class="ibox-content">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="m-b-md">
                                        <h2>${driver.name}</h2>
                                        <h4>${driver.cell}</h4>
                                    </div>
                                    <dl class="dl-horizontal">
                                    </dl>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12" id="cluster_info">
                                    <dl class="dl-horizontal"> 
                                    	<jsp:useBean id="dateObject" class="java.util.Date" />
                                    	<jsp:setProperty property="time" name="dateObject" value="${driver.createdTime}"/>
                                        <dt>注册时间:</dt> 
                                        <dd>
                                        	<fmt:formatDate value="${dateObject}" pattern="yyyy-MM-dd HH:mm"/> 
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <dl class="dl-horizontal"> 
                                        <dt>审核图片:</dt>
                                        <dd class="project-people">
<!--                                         <a href="http://image.lajiaoer.com/img/profile_small.jpg" title="a" data-gallery=""><img alt="image" class="feed-photo" style="width: 180px;" src="http://image.lajiaoer.com/img/profile_small.jpg"></a> -->
<!--                                         <a href="http://image.lajiaoer.com/img/profile_big.jpg" title="a" data-gallery=""><img alt="image" class="feed-photo" style="width: 180px;" src="http://image.lajiaoer.com/img/profile_big.jpg"></a> -->
<!--                                         <a href="http://image.lajiaoer.com/img/profile.jpg" title="a" data-gallery=""><img alt="image" class="feed-photo" style="width: 180px;" src="http://image.lajiaoer.com/img/profile.jpg"></a> -->
<!--                                         <a href="http://image.lajiaoer.com/img/p1.jpg" title="a" data-gallery=""><img alt="image" class="feed-photo" style="width: 180px;" src="http://image.lajiaoer.com/img/p1.jpg"></a> -->
											<span>身份证正面</span>
	                                        <a href="${driver.sfzA }" data-gallery=""><img alt="image" class="feed-photo lazy" style="width: 180px; height: 113px;" data-original="${driver.sfzA }"></a>
	                                        <span>身份证背面</span>
	                                        <a href="${driver.sfzB }" data-gallery=""><img alt="image" class="feed-photo lazy" style="width: 180px; height: 113px;" data-original="${driver.sfzB }"></a><br>
	                                        <span>驾驶证正面</span>
	                                        <a href="${driver.jszA }" data-gallery=""><img alt="image" class="feed-photo lazy" style="width: 180px; height: 113px;" data-original="${driver.jszA }"></a>
	                                        <span>行驶证正面</span>
	                                        <a href="${driver.xszA }" data-gallery=""><img alt="image" class="feed-photo lazy" style="width: 180px; height: 113px;" data-original="${driver.xszA }"></a>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <dl class="dl-horizontal">
                                        <dt>等车位置:</dt>
                                        <dd>
                                        	<select class="wait-position js-states form-control" style="width: 50%;" data-origin-driverplaceid="${driver.driverPlaceId }">
                                        		<option></option>
                                        		<c:forEach items="${dplace}" var="dp">
                                        			<optgroup label="${dp.key }">
                                        				<c:forEach items="${dp.value }" var="dpItem">
                                        					<option value="${dpItem.id }" <c:if test="${dpItem.id eq driver.driverPlaceId}">selected</c:if>>${dpItem.aName }</option>
                                        				</c:forEach>
                                        			</optgroup>
                                        		</c:forEach>
											</select>
											<a href="#" class="ladda-button btn btn-primary" data-style="zoom-in"><i class="fa fa-save"></i></a>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <dl class="dl-horizontal">
                                        <dt>车牌号:</dt>
                                        <dd>
                                        	${driver.licensePlate }
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <dl class="dl-horizontal">
                                        <dt>完成:</dt>
                                        <dd>
                                            <div class="progress progress-striped active m-b-sm">
                                                <div style="width: ${auditResult.score}%;" class="progress-bar" aria-valuenow="${auditResult.score}" aria-valuemin="0" aria-valuemax="100">
                                                	${auditResult.score}%
                                                </div>
                                            </div>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                            <!-- ################################# -->
                            <!-- 审核操作 -->
                            <!-- ################################# -->
                            <div class="row">
                            	<div class="col-lg-12">
                            		<dl class="dl-horizontal">
                            			<dt>审核操作:</dt>
                            			<dd>
	                                        <c:choose>
		                                        <c:when test="${auditResult.audit.name eq 1}">
		                                        	<button type="button" class="btn btn-primary"><i class="fa fa-check-square-o fa-fw"></i> 姓名 </button>
												</c:when>
												<c:when test="${auditResult.audit.name eq 2}">
		                                        	<button type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="top" title="${auditResult.audit.nameReject }"><i class="fa fa-ban fa-fw"></i> 姓名 </button>
												</c:when>
												<c:otherwise>
													<div class="btn-group">
							                            <button type="button" class="btn btn-danger " data-type="name"><i class="fa fa-square-o fa-fw"></i> 姓名 </button>
							                            <button type="button" class="btn btn-danger  dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							                            	<span class="caret"></span>
							                            	<span class="sr-only">Toggle Dropdown</span>
		  												</button>
							                            <ul class="dropdown-menu">
							                                <li><a href="#" data-toggle="modal" data-target="#myModal4"><i class="fa fa-ban fa-fw"></i> 拒绝</a></li>
							                            </ul>
							                        </div>
												</c:otherwise>	                                        
	                                        </c:choose>
	                                        
	                                        <c:choose>
		                                        <c:when test="${auditResult.audit.cell eq 1}">
		                                        	<button type="button" class="btn btn-primary"><i class="fa fa-check-square-o fa-fw"></i> 手机号 </button>
												</c:when>
												<c:when test="${auditResult.audit.cell eq 2}">
		                                        	<button type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="top" title="${auditResult.audit.cellReject }"><i class="fa fa-ban fa-fw"></i> 手机号 </button>
												</c:when>
												<c:otherwise>
													<div class="btn-group">
							                            <button type="button" class="btn btn-danger " data-type="cell"><i class="fa fa-square-o fa-fw"></i> 手机号 </button>
							                            <button type="button" class="btn btn-danger  dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							                            	<span class="caret"></span>
							                            	<span class="sr-only">Toggle Dropdown</span>
		  												</button>
							                            <ul class="dropdown-menu">
							                                <li><a href="#" data-toggle="modal" data-target="#myModal4"><i class="fa fa-ban fa-fw"></i> 拒绝</a></li>
							                            </ul>
							                        </div>
												</c:otherwise>	                                        
	                                        </c:choose>
	                                        
	                                        <c:choose>
		                                        <c:when test="${auditResult.audit.sfzA eq 1}">
		                                        	<button type="button" class="btn btn-primary"><i class="fa fa-check-square-o fa-fw"></i> 身份证正面 </button>
												</c:when>
												<c:when test="${auditResult.audit.sfzA eq 2}">
		                                        	<button type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="top" title="${auditResult.audit.sfzAReject }"><i class="fa fa-ban fa-fw"></i> 身份证正面 </button>
												</c:when>
												<c:otherwise>
													<div class="btn-group">
							                            <button type="button" class="btn btn-danger " data-type="sfza"><i class="fa fa-square-o fa-fw"></i> 身份证正面 </button>
							                            <button type="button" class="btn btn-danger  dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							                            	<span class="caret"></span>
							                            	<span class="sr-only">Toggle Dropdown</span>
		  												</button>
							                            <ul class="dropdown-menu">
							                                <li><a href="#" data-toggle="modal" data-target="#myModal4"><i class="fa fa-ban fa-fw"></i> 拒绝</a></li>
							                            </ul>
							                        </div>
												</c:otherwise>	                                        
	                                        </c:choose>
	                                        
	                                        <c:choose>
		                                        <c:when test="${auditResult.audit.sfzB eq 1}">
		                                        	<button type="button" class="btn btn-primary"><i class="fa fa-check-square-o fa-fw"></i> 身份证背面 </button>
												</c:when>
												<c:when test="${auditResult.audit.sfzB eq 2}">
		                                        	<button type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="top" title="${auditResult.audit.sfzBReject }"><i class="fa fa-ban fa-fw"></i> 身份证背面 </button>
												</c:when>
												<c:otherwise>
													<div class="btn-group">
							                            <button type="button" class="btn btn-danger " data-type="sfzb"><i class="fa fa-square-o fa-fw"></i> 身份证背面 </button>
							                            <button type="button" class="btn btn-danger  dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							                            	<span class="caret"></span>
							                            	<span class="sr-only">Toggle Dropdown</span>
		  												</button>
							                            <ul class="dropdown-menu">
							                                <li><a href="#" data-toggle="modal" data-target="#myModal4"><i class="fa fa-ban fa-fw"></i> 拒绝</a></li>
							                            </ul>
							                        </div>
												</c:otherwise>	                                        
	                                        </c:choose>
	                                        
	                                        <c:choose>
		                                        <c:when test="${auditResult.audit.jszA eq 1}">
		                                        	<button type="button" class="btn btn-primary"><i class="fa fa-check-square-o fa-fw"></i> 驾驶证正面 </button>
												</c:when>
												<c:when test="${auditResult.audit.jszA eq 2}">
		                                        	<button type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="top" title="${auditResult.audit.jszAReject }"><i class="fa fa-ban fa-fw"></i> 驾驶证正面 </button>
												</c:when>
												<c:otherwise>
													<div class="btn-group">
							                            <button type="button" class="btn btn-danger " data-type="jsza"><i class="fa fa-square-o fa-fw"></i> 驾驶证正面 </button>
							                            <button type="button" class="btn btn-danger  dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							                            	<span class="caret"></span>
							                            	<span class="sr-only">Toggle Dropdown</span>
		  												</button>
							                            <ul class="dropdown-menu">
							                                <li><a href="#" data-toggle="modal" data-target="#myModal4"><i class="fa fa-ban fa-fw"></i> 拒绝</a></li>
							                            </ul>
							                        </div>
												</c:otherwise>	                                        
	                                        </c:choose>
	                                        
	                                        <c:choose>
		                                        <c:when test="${auditResult.audit.xszA eq 1}">
		                                        	<button type="button" class="btn btn-primary"><i class="fa fa-check-square-o fa-fw"></i> 行驶证正面 </button>
												</c:when>
												<c:when test="${auditResult.audit.xszA eq 2}">
		                                        	<button type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="top" title="${auditResult.audit.xszAReject }"><i class="fa fa-ban fa-fw"></i> 行驶证正面 </button>
												</c:when>
												<c:otherwise>
													<div class="btn-group">
							                            <button type="button" class="btn btn-danger " data-type="xsza"><i class="fa fa-square-o fa-fw"></i> 行驶证正面 </button>
							                            <button type="button" class="btn btn-danger  dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							                            	<span class="caret"></span>
							                            	<span class="sr-only">Toggle Dropdown</span>
		  												</button>
							                            <ul class="dropdown-menu">
							                                <li><a href="#" data-toggle="modal" data-target="#myModal4"><i class="fa fa-ban fa-fw"></i> 拒绝</a></li>
							                            </ul>
							                        </div>
												</c:otherwise>	                                        
	                                        </c:choose>
	                                        
	                                        <c:choose>
		                                        <c:when test="${auditResult.audit.licensePlate eq 1}">
		                                        	<button type="button" class="btn btn-primary"><i class="fa fa-check-square-o fa-fw"></i> 车牌号 </button>
												</c:when>
												<c:when test="${auditResult.audit.licensePlate eq 2}">
		                                        	<button type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="top" title="${auditResult.audit.licensePlateReject }"><i class="fa fa-ban fa-fw"></i> 车牌号 </button>
												</c:when>
												<c:otherwise>
													<div class="btn-group">
							                            <button type="button" class="btn btn-danger " data-type="licenseplate"><i class="fa fa-square-o fa-fw"></i> 车牌号 </button>
							                            <button type="button" class="btn btn-danger  dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							                            	<span class="caret"></span>
							                            	<span class="sr-only">Toggle Dropdown</span>
		  												</button>
							                            <ul class="dropdown-menu">
							                                <li><a href="#" data-toggle="modal" data-target="#myModal4"><i class="fa fa-ban fa-fw"></i> 拒绝</a></li>
							                            </ul>
							                        </div>
												</c:otherwise>	                                        
	                                        </c:choose>
                            			</dd>
                            		</dl>
                            	</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="modal inmodal" id="myModal4" tabindex="-1" role="dialog" aria-hidden="true">
	            <div class="modal-dialog">
	                <div class="modal-content animated fadeIn">
	                    <div class="modal-header">
	                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	                        <i class="fa fa-ban modal-icon"></i>
	                        <h4 class="modal-title">拒绝</h4>
	                        <%--<small>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</small> --%>
	                    </div>
	                    <div class="modal-body">
	                        <div class="form-group"><label>拒绝原因</label> <input type="text" id="reason" placeholder="输入拒绝原因" class="form-control" required="required"></div>
	                    </div>
	                    <div class="modal-footer">
	                        <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
	                        <button type="button" class="btn btn-primary reason-submit">提交</button>
	                    </div>
	                </div>
	            </div>
	        </div>

            <%--<div class="col-lg-3">
                <div class="wrapper wrapper-content project-manager">
                    <h4>Project description</h4>
                    <img src="http://image.lajiaoer.com/img\zender_logo.png" class="img-responsive">
                    <p class="small">
                        There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look
                        even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing
                    </p>
                    <p class="small font-bold">
                        <span><i class="fa fa-circle text-warning"></i> High priority</span>
                    </p>
                    <h5>Project tag</h5>
                    <ul class="tag-list" style="padding: 0">
                        <li><a href=""><i class="fa fa-tag"></i> Zender</a></li>
                        <li><a href=""><i class="fa fa-tag"></i> Lorem ipsum</a></li>
                        <li><a href=""><i class="fa fa-tag"></i> Passages</a></li>
                        <li><a href=""><i class="fa fa-tag"></i> Variations</a></li>
                    </ul>
                    <h5>Project files</h5>
                    <ul class="list-unstyled project-files">
                        <li><a href=""><i class="fa fa-file"></i> Project_document.docx</a></li>
                        <li><a href=""><i class="fa fa-file-picture-o"></i> Logo_zender_company.jpg</a></li>
                        <li><a href=""><i class="fa fa-stack-exchange"></i> Email_from_Alex.mln</a></li>
                        <li><a href=""><i class="fa fa-file"></i> Contract_20_11_2014.docx</a></li>
                    </ul>
                    <div class="text-center m-t-md">
                        <a href="#" class="btn btn-xs btn-primary">Add files</a>
                        <a href="#" class="btn btn-xs btn-primary">Report contact</a>

                    </div>
                </div>
            </div> --%>
        </div>
        <div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls">
		    <div class="slides"></div>
		    <h3 class="title"></h3>
		    <a class="prev">‹</a>
		    <a class="next">›</a>
		    <a class="close">×</a>
		    <a class="play-pause"></a>
		    <ol class="indicator"></ol>
		</div>
        <jsp:include page="../footer.jsp" />

        </div>
        </div>

    <!-- Mainly scripts -->
    <script src="http://static.lajiaoer.com/js\jquery-2.1.1.js"></script>
    <script src="http://static.lajiaoer.com/js\bootstrap.min.js"></script>
    <script src="http://static.lajiaoer.com/js\plugins\metisMenu\jquery.metisMenu.js"></script>
    <script src="http://static.lajiaoer.com/js\plugins\slimscroll\jquery.slimscroll.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="http://static.lajiaoer.com/js\inspinia.js"></script>
    <script src="http://static.lajiaoer.com/js\plugins\pace\pace.min.js"></script>
    
    <!-- Toastr script -->
    <script src="http://static.lajiaoer.com/js\plugins\toastr\toastr.min.js"></script>
    <!-- Sweet alert -->
    <script src="http://static.lajiaoer.com/js\plugins\sweetalert\sweetalert.min.js"></script>
    <!-- blueimp gallery -->
    <script src="http://static.lajiaoer.com/js\plugins\blueimp\jquery.blueimp-gallery.min.js"></script>
    <!-- Select2 -->
    <script src="http://static.lajiaoer.com/js\plugins\select2\select2.full.min.js"></script>
    <!-- Ladda -->
    <script src="http://static.lajiaoer.com/js\plugins\ladda\spin.min.js"></script>
    <script src="http://static.lajiaoer.com/js\plugins\ladda\ladda.min.js"></script>
    <script src="http://static.lajiaoer.com/js\plugins\ladda\ladda.jquery.min.js"></script>
    <!-- LazyLoad -->
    <script src="http://static.lajiaoer.com/js/plugins\lazyload\jquery.lazyload.min.js"></script>

    <script>
        $(document).ready(function(){
        	
        	$('[data-toggle=tooltip]').tooltip({
                container: "body"
            });
        	
        	var $progress = $('.progress > div');
        	
        	$(".checkbox-success > input").on('click', function(event) {
        		var $this = $(this);
        		if (!$this.prop("checked")) {
        			return false;
        		}
        	});
        	$(".btn-group > button:first-child").one('click', function() {
        		var $this = $(this);
        		var p = {dId: ${driver.id}};
        		p[$this.data('type')] = 1;
        		$.ajax({
        			url: 'http://api.lajiaoer.com/rest/admin/drivers/audit',
        			type: 'put',
        			data: p,
        			dataType: 'json'
        		}).always(function(result) {
        			if (result) {
        				if (result.code != 200) {
        					toastr.error('审核失败', '审核提示');
        				} else {
        					$progress.trigger('load.progress');
        					$this.parent().replaceWith('<button type="button" class="btn btn-primary"><i class="fa fa-check-square-o fa-fw"></i> #name# </button>'.replace('#name#', $this.text().trim()));
        					toastr.success('审核成功', '审核提示');
        				}
        			}
        		});
        	});
        	
        	var $gBtnThis;
        	$('.btn-group ul > li:first-child a').click(function() {
        		var $this = $(this);
        		$gBtnThis = $this.parents('.btn-group').children(':first-child');
        	});
        	
        	$('.reason-submit').click(function() {
        		var reasonText = $('#reason').val();
        		if ($.trim(reasonText) == '') {
        			swal("请填写拒绝理由");
        			return false;
        		}
        		
        		var p = {dId: ${driver.id}};
        		p[$gBtnThis.data('type')] = 2;
        		p[$gBtnThis.data('type') + 'reject'] = reasonText;
        		$.ajax({
        			url: 'http://api.lajiaoer.com/rest/admin/drivers/audit',
        			type: 'put',
        			data: p,
        			dataType: 'json'
        		}).always(function(result) {
        			if (result) {
        				if (result.code != 200) {
        					toastr.error('审核失败', '审核提示');
        				} else {
        					$progress.trigger('load.progress');
        					$gBtnThis.parent().replaceWith('<button type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="top" title="'+reasonText+'"><i class="fa fa-ban fa-fw"></i> '+$gBtnThis.text().trim()+' </button>');
        					toastr.success('审核拒绝提交成功', '审核提示');
        					$('[data-toggle=tooltip]').tooltip('destroy');
        					$('[data-toggle=tooltip]').tooltip({
        		                container: "body"
        		            });
        					$('#myModal4').modal('hide');
        				}
        			}
        		});
        	});
        	
        	$('#myModal4').on('show.bs.modal', function() {
        		$('#reason').val('');
        	});
        	
        	$progress.on('load.progress', function() {
        		var $this = $(this);
        		$.ajax({
        			url: 'http://api.lajiaoer.com/rest/admin/drivers/audit',
        			type: 'get',
        			data: { dId: ${driver.id} },
        			dataType: 'json'
        		}).always(function(result) {
        			if (result) {
        				if (result.code == 200) {
        					var score = result.data.score;
        					$this.css('width', score+'%');
        					$this.parent().parent().children('small').children('strong').text(score+'%');
        				}
        			}
        		});
        	});
        	
        	$("img.lazy").lazyload();
        	
        	var l = $( '.ladda-button' ).ladda();
            l.click(function() {
                // Start loading 
                var $wait = $('.wait-position');
                var selectDriverPlaceId = $wait.val();
                if (selectDriverPlaceId == '') {
                	toastr.warning('没有选择等待位置', '警告');
                	return;
                }
                var originDriverPlaceId = $wait.data('origin-driverplaceid');
                if (originDriverPlaceId == selectDriverPlaceId) {
                	toastr.info('等待位置的数据没有发生改变，无需保存', '提示');
                	return;
                }
                l.ladda( 'start' );

                // Do something in backend and then stop ladda
                $.ajax({
                	url: "http://api.lajiaoer.com/rest/admin/drivers/place",
                	data: { dpId: $wait.val(), id: ${driver.id} },
                	type: 'put',
                	dataType: 'json'
                }).always(function(result) {
                	//console.log(result);
                    l.ladda('stop');
                    if (result) {
                    	if (result.code == 200) {
                    		$progress.trigger('load.progress');
		                    toastr.success("修改等待位置操作成功！");
                    	} else {
                    		toastr.error(result.msg);
                    	}
                    }
                });
                // setTimeout() is only for demo purpose
                //setTimeout(function(){
                //},2000);

            });
            
        	$(".wait-position").select2({
        		placeholder: "请选择等车位置",
        		allowClear: true
        	});
        	
        	toastr.options = {
        			closeButton: true,
        			progressBar: true,
        			timeOut: 2000
        	};

            $('#loading-example-btn').click(function () {
                btn = $(this);
                simpleLoad(btn, true)

                // Ajax example
//                $.ajax().always(function () {
//                    simpleLoad($(this), false)
//                });

                simpleLoad(btn, false)
            });
            
            $('.m-b-md').on('load.operation', function(e, d) {
            	var $this = $(this);
            	var id = $this.data("id");
            	$.getJSON("http://api.lajiaoer.com/rest/admin/drivers/simple?jsoncallback=?",
            			{id: d},
            			function(result) {
		            		if (result) {
		        				if (result.code == 200) {
		        					var driver = result.data;
		        					$this.find("a").remove();
		        					var $status = $this.next();
		        					$status.empty();
		        					var statusHtml = '<dt>状态:</dt><dd>';
		        					if (driver.actived == 0) {
		        						$this.prepend('<a href="#" id="approve" class="btn btn-white btn-xs pull-right" data-id="'+driver.id+'">通过</a>');
		        						statusHtml +='<span class="label label-danger">未激活</span>';
		        					} else {
		        						statusHtml += '<span class="label label-primary">已激活</span>';
		        					}
		        					if (driver.used == 1) {
                                    	$this.prepend('<a href="#" id="block" class="btn btn-danger btn-xs pull-right" data-id="'+driver.id+'">禁用</a>');
                                    	statusHtml += '<span class="label label-primary">可用</span>';
		        					} else if (driver.used == 0) {
		        						$this.prepend('<a href="#" id="nonblock" class="btn btn-primary btn-xs pull-right" data-id="'+driver.id+'">启用</a>');
		        						statusHtml += '<span class="label label-warning">不可用</span>';
		        					}
		        					statusHtml += '</dd>';
		        					$status.append(statusHtml);
		        				}
		        			}
		            	}
            	);	
            });
            
            $('body').on('click', '#approve', function() {
            	var $this = $(this);
            	var id = $this.data("id");
            	
            	swal({
                    title: "确认",
                    text: "确认执行通过？",
                    showCancelButton: true,
                    confirmButtonText: "确定",
                    cancelButtonText: "取消"
                }, function() {
                	$.ajax({
                		url: "http://api.lajiaoer.com/rest/admin/drivers/actived",
                		type: "PUT",
                		data: { id:  id},
                		dataType: "json",
                		success: function(result) {
                			if (result) {
                				if (result.code == 200) {
                					$this.remove();
                					toastr.success("通过操作成功！");
                					$('.m-b-md').trigger("load.operation", id);
                					$progress.trigger('load.progress');
                				} else {
                					toastr.error(result.msg);
                				}
                			}
                		},
                		failure: function(err) {
                			toastr.error(err);
                		}
                	});
                });
            	
            });
            
            $('body').on('click', '#block', function() {
            	var $this = $(this);
            	var id = $this.data("id");
            	swal({
                    title: "确认",
                    text: "确认执行禁用？",
                    showCancelButton: true,
                    confirmButtonText: "确定",
                    cancelButtonText: "取消"
                }, function() {
	            	$.ajax({
	            		url: "http://api.lajiaoer.com/rest/admin/drivers/forbidUsed",
	            		type: "PUT",
	            		data: { id: $this.data("id") },
	            		dataType: "json",
	            		success: function(result) {
	            			if (result) {
	            				if (result.code == 200) {
	            					$this.remove();
	            					toastr.success("禁用操作成功！");
	            					$('.m-b-md').trigger("load.operation", id);
	            					$progress.trigger('load.progress');
	            				} else {
	            					toastr.error(result.msg);
	            				}
	            			}
	            		},
	            		failure: function(err) {
	            			toastr.error(err);
	            		}
	            	});
                });
            });
            
            $('body').on('click', '#nonblock', function() {
            	var $this = $(this);
            	var id = $this.data("id");
            	swal({
                    title: "确认",
                    text: "确认执行启用？",
                    showCancelButton: true,
                    confirmButtonText: "确定",
                    cancelButtonText: "取消"
                }, function() {
	            	$.ajax({
	            		url: "http://api.lajiaoer.com/rest/admin/drivers/used",
	            		type: "PUT",
	            		data: { id: $this.data("id") },
	            		dataType: "json",
	            		success: function(result) {
	            			if (result) {
	            				if (result.code == 200) {
	            					$this.remove();
	            					toastr.success("启用操作成功！");
	            					$('.m-b-md').trigger("load.operation", id);
	            					$progress.trigger('load.progress');
	            				} else {
	            					toastr.error(result.msg);
	            				}
	            			}
	            		},
	            		failure: function(err) {
	            			toastr.error(err);
	            		}
	            	});
                });
            });
            
            $('.m-b-md').trigger("load.operation", ${driver.id});
        });

        function simpleLoad(btn, state) {
            if (state) {
                btn.children().addClass('fa-spin');
                btn.contents().last().replaceWith(" Loading");
            } else {
                setTimeout(function () {
                    btn.children().removeClass('fa-spin');
                    btn.contents().last().replaceWith(" Refresh");
                }, 2000);
            }
        }
    </script>
</body>

</html>
