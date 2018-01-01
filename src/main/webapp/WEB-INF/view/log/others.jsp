<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css"
	href="style/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="style/themes/icon.css">
<link rel="stylesheet" type="text/css" href="style/themes/demo.css">
<link rel="stylesheet" type="text/css" href="style/default.css">
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.easyui.min.js"></script>
<script type="text/javascript" src="script/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="script/common.js"></script>

<script>
	baseUrl = '<%=basePath%>';
	$(function() {
		$('#dt').datetimebox({
			showSeconds : true
		});
		$('#aRefresh').bind("click", function() {
			opAction(baseUrl + "/monitor/reconfig");
		});
		$('#aClear').bind("click", function() {
			var date = $('#dt').datetimebox('getValue');
			opAction(getUrl("log/clearlog") + "?date=" + date);
		});
	});
	var opAction = function(url) {
		$.get(url, {
		// id : row.ID
		}, function(result) {
			$.messager.alert('提示', result.message, 'info');
		}, 'json');
	}
</script>
</head>

<body>
	<a id="aRefresh" href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-ok">刷新配置缓存</a>
	<br />
	<br />
	<input id="dt" class="easyui-datetimebox" style="width:150px">

	<a id="aClear" href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-cancel">清除此前的所有日志</a>
</body>
</html>
