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
	reqUrl = getUrl('config/list');

	$(function() {
		$(tableId).datagrid({
			title : '基础配置',
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
				field : 'configKey',
				title : '配置名称',
				width : 140
			}, {
				field : 'configValue',
				title : '配置值',
				width : 100,
				sortable : true
			}, {
				field : 'configType',
				title : '配置类型',
				width : 150,
				formatter : function(value, row, index) {
					if (value == "1")
						return "系统配置";
					return "分组配置";
				}
			}, {
				field : 'remark',
				title : '配置描述',
				width : 150
			}, {
				field : 'isValid',
				title : '是否有效',
				width : 70,
				formatter : function(value, row, index) {
					if (value == "1")
						return "有效";
					return "无效";
				}
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
					newItem('/config/add', '新建配置');
				}
			}, '-', {
				id : 'btnEdit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					editItem('/config/update/', '修改配置');
				}
			}, '-', {
				id : 'btnDel',
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					var list = getSelections();
					if (list && list.length > 0) {
						reqUrl = getUrl('config/delete/' + list.join(','));
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
		$('#configType')[0].selectedIndex = 0;
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
		<div class="ftitle">配置信息</div>
		<form:form id="ff" method="post" modelAttribute="config">
			<form:input path="ID" type="hidden" />
			<div class="fitem">
				<label>配置名称:</label>
				<form:input path="configKey" maxlength="20"
					class="easyui-validatebox textbox" required="true"
					style="width:150px" />
			</div>
			<div class="fitem">
				<label>配置值:</label>
				<form:input path="configValue" maxlength="150"
					class="easyui-validatebox textbox" required="true"
					style="width:150px" />
			</div>
			<div class="fitem">
				<label>配置类型:</label>
				<form:select path="configType" items="${type}" itemLabel="desc"
					itemValue="code" style="width:150px" />
			</div>
			<div class="fitem">
				<label>配置描述:</label>
				<form:input path="remark" maxlength="20"
					class="easyui-validatebox textbox" required="true"
					style="width:150px" />
			</div>
			<div class="fitem">
				<label>是否启用:</label>
				<form:checkbox path="isValid"
					style="width:150px; margin-left:-65px;" />
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
