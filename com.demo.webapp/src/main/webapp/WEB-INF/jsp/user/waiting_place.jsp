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
    	<jsp:param value="城市列表" name="title"/>
    </jsp:include>

	<jsp:include page="../common/link.jsp" />
	<!-- FooTable -->
    <link href="http://static.lajiaoer.com\css\plugins\footable\footable.core.css" rel="stylesheet">
    <jsp:include page="../common/favicon.jsp" />

</head>

<body class="gray-bg">

    <div class="wrapper wrapper-content animated fadeInRight">
        <div>
        <%--
            <div>

                <h1 class="logo-name"><d:title /></h1>

            </div>
        --%>
            <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>位置</h5>
                        </div>
                        <div class="ibox-content">

                            <table class="footable table table-stripped toggle-arrow-tiny" data-page-size="8">
                                <thead>
                                <tr>
                                    <th data-toggle="true">起点/终点</th>
                                    <th data-hide="all">起点地址详情</th>
                                    <th data-hide="all">终点地址详情</th>
                                    <th data-hide="all">等待中数量</th>
                                    <th>车牌号</th>
                                    <th>倒计时</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%--
                                <c:forEach items="${listWaiting}" var="item">
                                	<tr>
	                                    <td>${item.driverPlace.aName} - ${item.driverPlace.aCityName}</td>
	                                    <td>${item.driverPlace.bName} - ${item.driverPlace.bCityName}</td>
	                                    <td>
	                                    	<c:choose>
	                                    		<c:when test="${not empty item.driverPlace.aNameDetail}">
	                                    			${item.driverPlace.aNameDetail}
	                                    		</c:when>
	                                    		<c:otherwise>
	                                    			${item.driverPlace.aName}
	                                    		</c:otherwise>
	                                    	</c:choose>
	                                    </td>
	                                    <td>
											<c:choose>
	                                    		<c:when test="${not empty item.driverPlace.bNameDetail}">
	                                    			${item.driverPlace.bNameDetail}
	                                    		</c:when>
	                                    		<c:otherwise>
	                                    			${item.driverPlace.bName}
	                                    		</c:otherwise>
	                                    	</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${empty item.waitingCount }">
													无
												</c:when>
												<c:when test="${item.waitingCount ne 0}">
													${item.waitingCount}
												</c:when>
												<c:otherwise>
													无
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${empty item.driverWaiting }">
													无
												</c:when>
												<c:otherwise>
													<span data-countdown='<fmt:formatDate value="${item.driverWaiting.end_time }" pattern="yyyy/MM/dd HH:mm:ss"/>'></span>
												</c:otherwise>
											</c:choose>
											
										</td>
	                                </tr>
                                </c:forEach>
                                 --%>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="5">
                                        <ul class="pagination pull-right hide-if-no-paging"></ul>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>

                        </div>
                    </div>
            
            <p class="m-t"> <small>版权所有 &copy; 2016</small> </p>
        </div>
    </div>

    <jsp:include page="../common/script.jsp" />
    <!-- FooTable -->
    <script src="http://static.lajiaoer.com\js\plugins\footable\footable.all.min.js"></script>
	<!-- Jquery countdown -->
	<script src="http://static.lajiaoer.com\js\plugins\countdown\jquery.countdown.min.js"></script>
    <script>
	$(document).ready(function() {

        $('.footable').footable();
        
        $.get("http://api.lajiaoer.com/rest/user/waiting/place",function(result) {
        	if (result && result != undefined) {
        		if (result.code == 200 && result.data) {
        			var arr = result.data;
        			var html = "";
        			$.each(arr, function(i, d) {
	        			var htmlArr = [];
        				htmlArr.push("<tr>");
        				htmlArr.push("<td>"+d.driverPlace.aName + " - " + d.driverPlace.aCityName+"/"+d.driverPlace.bName +"-" + d.driverPlace.bCityName + "</td>" + 
        						"<td>"+(d.driverPlace.aNameDetail ? d.driverPlace.aNameDetail : d.driverPlace.aName)+"</td>" + 
        						"<td>"+(d.driverPlace.bNameDetail ? d.driverPlace.bNameDetail : d.driverPlace.bName)+"</td>" + 
        						"<td>"+(!d.waitingCount ? '无' : (d.waitingCount != 0 ? d.waitingCount : '无'))+"</td>" +
        						"<td>"+(!d.driver ? '无' : d.driver.licensePlate)+"</td>");
        				if (d.driverWaiting) {
        					var countdownTime = d.driverWaiting.end_time;
            				var countdownDate = new Date(countdownTime);
            				var countdownStr = countdownDate.getFullYear() + "/" + addZero(countdownDate.getMonth() + 1) + "/" + addZero(countdownDate.getDate()) + " " + addZero(countdownDate.getHours()) + ":" + addZero(countdownDate.getMinutes()) + ":" + addZero(countdownDate.getSeconds());
        					htmlArr.push('<td><span data-countdown="'+countdownStr+'"></span></td>');
        				} else {
        					htmlArr.push('<td>无</td>');	
        				}
        				htmlArr.push("</tr>");
	        			html += htmlArr.join("");
        			});
        			$('.footable > tbody').append(html);
        			$('.footable').trigger('footable_redraw');
        			
        			$('[data-countdown]').each(function() {
        	        	var $this = $(this), finalDate = $(this).data('countdown');
        	        	$this.countdown(finalDate, function(event) {
        	        		$this.html(event.strftime('%M:%S'));
        	        	}).on('finish.countdown', function(event) {
        	        		window.location.reload();
        	        	});
        	        });
        		}
        	}
        }, "json");

        /*$('[data-countdown]').each(function() {
        	var $this = $(this), finalDate = $(this).data('countdown');
        	$this.countdown(finalDate, function(event) {
        		$this.html(event.strftime('%M:%S'));
        	}).on('finish.countdown', function(event) {
        		window.location.reload();
        	});
        });*/
    });

	function addZero(n) {
		if (n < 10) {
			return "0" + n;
		}
		return n;
	}
    </script>
</body>

</html>