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
    	<jsp:param value="司机等客中" name="title"/>
    </jsp:include>

	<jsp:include page="../common/link.jsp" />
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
            <h3>司机等客</h3>
            <p>点击开始倒计时</p>
            <form class="m-t" role="form" id="driver-waiting-form" action="/driver/waiting" method="post">
            	<div style="display: none;">
            		<label id="waiting-error" class="error"></label>
            	</div>
            	<div style="display:none;">
            		<span id="clock"></span>
            	</div>
                <button class="btn btn-primary dim btn-large-dim" type="button" style="display: none;"><i class="fa fa-play"></i></button>
                <button class="btn btn-primary dim btn-large-dim" type="button" style="display: none;"><i class="fa fa-stop"></i></button>
            </form>
            <p class="m-t"> <small>版权所有 &copy; 2016</small> </p>
        </div>
    </div>

    <jsp:include page="../common/script.jsp" />
    <!-- Jquery Validate -->
	<script src="http://static.lajiaoer.com\js\plugins\validate\jquery.validate.min.js"></script>
	<!-- Jquery countdown -->
	<script src="http://static.lajiaoer.com\js\plugins\countdown\jquery.countdown.min.js"></script>
    <script>
        $(function() {
        	var $clock = $("#clock");
        	var $play = $(".fa-play").parent();
        	var $stop = $(".fa-stop").parent();
        	
        	$clock.on("clock.stop", function() {
        		$clock.parent().hide();
				$stop.hide();
				$play.show();
				$.ajax({
					url: "http://api.lajiaoer.com/rest/driver/waiting",
					type: "put",
					xhrFields:{
						withCredentials:true
					},
					crossDomain:true,
					data: {id: $stop.data("id")},
					dataType: "json",
					success: function(result) {
						
					}
				});
        	});
        	
        	$clock.on("clock.countdown", function(event, time) {
        		var overtime = new Date(time);
		            $clock.countdown(overtime, function(event) {
		                $(this).html(event.strftime('%H:%M:%S'));
		            }).on("finish.countdown", function() {
		            	$clock.text("").parent().hide();
						$stop.hide();
						$play.show();
		            	$clock.trigger("clock.stop");
		            });
        	});
        	
        	$stop.click(function() {
        		$clock.countdown("stop").text("");
        		$clock.trigger("clock.stop");
        	});
            
            $play.click(function() {
   				$("#waiting-error").hide().text("").parent().hide();
   				$.ajax({
   					url: "http://api.lajiaoer.com/rest/driver/waiting",
   					type: "post",
   					dataType: "json",
   					xhrFields:{
						withCredentials:true
					},
					crossDomain:true,
   					success: function(result) {
   						var code = result.code;
   						if (code == 200) {
   							//form.submit();
   							var data = result.data;
							$clock.trigger("clock.countdown", [data.endTime]);
   				         	$clock.parent().show();
   				            $play.hide();
   				            $stop.data("id", data.id)
   				            $stop.show();
   							
   							return;
   						}
   						
   						$("#waiting-error").text(result.msg).show().parent().show();
   					}
   				});
    		});
            
            $.getJSON("http://api.lajiaoer.com/rest/driver/waiting?jsoncallback=?", 
            	 function(result) {
	            		console.log(result);
            
	            	var code = result.code;
	            	if (code == 200) {
	            		var data = result.data;
	            		var overtime = new Date(data.endTime);
	            		$clock.trigger("clock.countdown", [overtime]);
			         	$clock.parent().show();
			            $stop.data("id", data.id);
			            $stop.show();
							
							return;
	            	}
	            	
	            	if (code == 10003) {
			            $play.show();
						return;
	            	}
	            	$("#waiting-error").text(result.msg).show().parent().show();
	            	
	            }
            );
            
        });
    </script>
</body>

</html>
