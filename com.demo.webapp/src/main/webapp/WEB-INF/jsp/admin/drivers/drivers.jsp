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

    <title><d:title/> | 司机列表</title>

    <link href="http://static.lajiaoer.com/css\bootstrap.min.css" rel="stylesheet">
    <link href="http://static.lajiaoer.com/font-awesome\css\font-awesome.css" rel="stylesheet">

    <link href="http://static.lajiaoer.com/css\animate.css" rel="stylesheet">
    <link href="http://static.lajiaoer.com/css\style.css" rel="stylesheet">

</head>

<body>

    <div id="wrapper">

    <jsp:include page="../leftmenu.jsp"/>

        <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
        <jsp:include page="../topmenu.jsp" />
        </div>
            <div class="row wrapper border-bottom white-bg page-heading">
                <div class="col-sm-4">
                    <h2>司机审核列表</h2>
                    <ol class="breadcrumb">
                        <li>
                            <a href="index.html">首页</a>
                        </li>
                        <li class="active">
                            <strong>司机审核列表</strong>
                        </li>
                    </ol>
                </div>
            </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="wrapper wrapper-content animated fadeInUp">

                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>列表内容</h5>
                            <%--
                            <div class="ibox-tools">
                                <a href="" class="btn btn-primary btn-xs">Create new project</a>
                            </div>
                             --%>
                        </div>
                        <div class="ibox-content">
                        	
                            <div class="row m-b-sm m-t-sm">
                                <div class="col-md-1">
                                    <button type="button" id="loading-example-btn" class="btn btn-white btn-sm"><i class="fa fa-refresh"></i> 刷新</button>
                                </div>
                                <%--
                                <div class="col-md-11">
                                    <div class="input-group"><input type="text" placeholder="Search" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div>
                                </div>
                                --%>
                            </div>
 							
                            <div class="project-list">

                                <table class="table table-hover">
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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
    <script src="http://static.lajiaoer.com/js/plugins/dateformat/jquery.dateformat.min.js"></script>

    <script>
        $(document).ready(function(){

        	var $loading = $('#loading-example-btn');
        	var $tbody = $('.table > tbody');
        	$loading.click(function () {
                btn = $(this);
                simpleLoad(btn, true)

                // Ajax example
//                $.ajax().always(function () {
//                    simpleLoad($(this), false)
//                });
                $loading.trigger('driver.list', [true]);
            });
            
        	$loading.on('driver.list', function(e, d) {
            	$.getJSON(
            		"http://api.lajiaoer.com/rest/admin/drivers?jsoncallback=?",
            		{s: 0, n: 10, q: ''},
            		function(result) {
            			if (result && result.code == 200) {
            				var arr = result.data;
            				if (d == true) {
            					$tbody.empty();
            				}
            				$.each(arr, function(index, data) {
            					var htmlArr = [];
            					htmlArr.push("<tr>");
            					htmlArr.push("  <td class=\"project-status\">");
            					htmlArr.push("    <span class=\"label label-"+(data.actived == 0 ? 'danger' : 'primary')+"\">");
            					htmlArr.push("      " + (data.actived == 0 ? '未激活' : '已激活'));
            					htmlArr.push("    </span>");
            					htmlArr.push("  </td>");
            					htmlArr.push("  <td class=\"project-title\">");
            					htmlArr.push("    <a href=\"/admin/drivers/detail?id="+data.id+"\">");
            					htmlArr.push("      " + data.name);
            					htmlArr.push("    </a>");
            					htmlArr.push("    <br>");
            					htmlArr.push("    <small>");
            					htmlArr.push("      注册时间：" + $.format.date(new Date(data.createdTime), 'yyyy-MM-dd HH:mm'));
            					htmlArr.push("    </small>");
            					htmlArr.push("  </td>");
            					htmlArr.push("  <td class=\"project-completion\">");
            					htmlArr.push("    <small>");
            					htmlArr.push("      信息完整度: 100%");
            					htmlArr.push("    </small>");
            					htmlArr.push("    <div class=\"progress progress-mini\">");
            					htmlArr.push("      <div style=\"width: 100%;\" class=\"progress-bar\">");
            					htmlArr.push("      </div>");
            					htmlArr.push("    </div>");
            					htmlArr.push("  </td>");
            					htmlArr.push("  <td class=\"project-people\">");
            					htmlArr.push("    <a href=\"\">");
            					htmlArr.push("      <img alt=\"image\" class=\"img-circle\" src=\""+data.sfzA+"\">");
            					htmlArr.push("    </a>");
            					htmlArr.push("    <a href=\"\">");
            					htmlArr.push("      <img alt=\"image\" class=\"img-circle\" src=\""+data.sfzB+"\">");
            					htmlArr.push("    </a>");
            					htmlArr.push("    <a href=\"\">");
            					htmlArr.push("      <img alt=\"image\" class=\"img-circle\" src=\""+data.jszA+"\">");
            					htmlArr.push("    </a>");
            					htmlArr.push("  </td>");
            					htmlArr.push("  <td class=\"project-actions\">");
            					htmlArr.push("    <a href=\"#\" class=\"btn btn-white btn-sm\">");
            					htmlArr.push("      <i class=\"fa fa-folder\">");
            					htmlArr.push("      </i>");
            					htmlArr.push("      查看");
            					htmlArr.push("    </a>");
            					htmlArr.push("    <a href=\"#\" class=\"btn btn-white btn-sm\">");
            					htmlArr.push("      <i class=\"fa fa-pencil\">");
            					htmlArr.push("      </i>");
            					htmlArr.push("      编辑");
            					htmlArr.push("    </a>");
            					htmlArr.push("  </td>");
            					htmlArr.push("</tr>");
            					$tbody.append(htmlArr.join(''));
            				});
            				
            				if (d == true) {
            					simpleLoad(btn, false);
            				}
            			}
            		}
            	);
            });
            
        	$loading.trigger('driver.list');
        });

        function simpleLoad(btn, state) {
            if (state) {
                btn.children().addClass('fa-spin');
                btn.contents().last().replaceWith(" 加载中");
            } else {
                //setTimeout(function () {
                    btn.children().removeClass('fa-spin');
                    btn.contents().last().replaceWith(" 刷新");
                //}, 2000);
            }
        }
    </script>
</body>

</html>
