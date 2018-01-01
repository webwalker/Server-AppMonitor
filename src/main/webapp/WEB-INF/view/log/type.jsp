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
	var tableId = "#tb";
	var formId = "#ff";
	var dialogId = "#dlg";
	baseUrl = '<%=basePath%>';
	reqUrl = getUrl('type/list');

	$(function() {
		$(tableId).datagrid({
			title : '消息类型定义',
			collapsible : false,
			width : 700,
			height : 500,
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			collapsible : true,
			method : 'get',
			url : reqUrl,
			remoteSort : false,
			idField : 'id',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				title : '编号',
				field : 'id',
				width : 60
			}, {
				field : 'msgName',
				title : '类型名称',
				width : 140
			}, {
				field : 'msgDesc',
				title : '类型描述',
				width : 100,
				sortable : true
			}, {
				field : 'createTime',
				title : '创建时间',
				width : 150,
				formatter : function(value, row, index) {
					return new Date(value).format('yyyy-MM-dd hh:mm:ss');
				}
			}, {
				field : 'updateTime',
				title : '更新时间',
				width : 150,
				formatter : function(value, row, index) {
					return new Date(value).format('yyyy-MM-dd hh:mm:ss');
				}
			} ] ],
			pagination : true,
			rownumbers : false,
			fitColumns : true,
			singleSelect : false,
			toolbar : [ {
				id : 'btnadd',
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {
					newItem('/type/add', '新建类型');
				}
			}, '-', {
				id : 'btnEdit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					editItem('/type/update/', '修改类型');
				}
			}, '-', {
				id : 'btnDel',
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					var list = getSelections();
					if (list && list.length > 0) {
						reqUrl = getUrl('type/delete/' + list.join(','));
						deleteItem(reqUrl);
					}
				}
			} ],
			onBeforeLoad : function(param) {
				param.pageNo = param.requestPage || param.pageNo || 1;
				param.requestPage = undefined;
				param.pageSize = page_size;
			}
		});
		pageInit();
	});
	var addCallback = function() {
	};
	var editCallback = function(row) {
		$('#ID').val(row.id);
	}
</script>
</head>

<body>
	<table id="tb"></table>
	<div id="dlg" class="easyui-dialog"
		style="width:400px;height:290px;padding:10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">消息类型</div>
		<form:form id="ff" method="post" modelAttribute="type">
			<form:input path="ID" type="hidden" />
			<div class="fitem">
				<label>类型名称:</label>
				<form:input path="msgName" maxlength="20"
					class="easyui-validatebox textbox" required="true"
					style="width:150px" />
			</div>
			<div class="fitem">
				<label>类型描述：</label>
				<form:input path="msgDesc" maxlength="150"
					class="easyui-validatebox textbox" required="true"
					style="width:150px" />
			</div>			
		</form:form>
	</div>
	<div id="dlg-buttons">
		<a id="aSave" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a id="aCancel" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	</div>
</body>
</html>
