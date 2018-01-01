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
	reqUrl = getUrl('template/list');

	$(function() {
		$(tableId).datagrid({
			title : '模板配置',
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
				field : 'templateCode',
				title : '模板编码',
				width : 140
			}, {
				field : 'templateName',
				title : '模板名称',
				width : 100,
				sortable : true
			}, {
				field : 'validateType',
				title : '匹配类型',
				width : 150,
				formatter : function(value, row, index) {
					if (value == "1")
						return "同时满足";
					return "任一满足";
				}
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
			},{
				field : 'opt',
				title : '操作',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.url == '')
						return "";
					return "<img src='images/viewmore.gif' title='规则映射' onclick='addRules("
							+ row.id + ")' />";
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
					newItem('/template/add', '新建模板');
				}
			}, '-', {
				id : 'btnEdit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					editItem('/template/update/', '修改模板');
				}
			}, '-', {
				id : 'btnDel',
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					var list = getSelections();
					if (list && list.length > 0) {
						reqUrl = getUrl('template/delete/' + list.join(','));
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
		$('#validateType')[0].selectedIndex = 0;
	};
	var editCallback = function(row) {
		$('#ID').val(row.id);
	}
	var addRules = function(id) {
		window.location.href = getUrl("rule/" + id);
	};
</script>
</head>

<body>
	<table id="tb"></table>
	<div id="dlg" class="easyui-dialog"
		style="width:400px;height:290px;padding:10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">模板信息</div>
		<form:form id="ff" method="post" modelAttribute="template">
			<form:input path="ID" type="hidden" />
			<div class="fitem">
				<label>模板编码:</label>
				<form:input path="templateCode" maxlength="20"
					class="easyui-validatebox textbox" required="true"
					style="width:150px" />
			</div>
			<div class="fitem">
				<label>模板名称:</label>
				<form:input path="templateName" maxlength="150"
					class="easyui-validatebox textbox" required="true"
					style="width:150px" />
			</div>
			<div class="fitem">
				<label>模板类型:</label>
				<form:select path="validateType" items="${type}" itemLabel="desc"
					itemValue="code" style="width:150px" />
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
