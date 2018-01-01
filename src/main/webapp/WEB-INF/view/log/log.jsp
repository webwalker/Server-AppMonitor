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
	reqUrl = '';

	$(function() {
		$(tableId).datagrid({
			title : '消息日志',
			width : 700,
			height : 280,
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			collapsible : false,
			method : 'get',
			url : reqUrl,
			remoteSort : false,
			idField : 'id',
			columns : [ [ {
				title : '编号',
				field : 'id',
				width : 60
			}, {
				field : 'userID',
				title : '用户编号',
				width : 150
			}, {
				field : 'userGroup',
				title : '用户分组',
				width : 150,
				formatter : function(value, row, index) {
					return formatStr(value, 13);
				}
			}, {
				field : 'area',
				title : '驻地',
				width : 60
			}, {
				field : 'osVersion',
				title : '系统版本',
				width : 150,
				formatter : function(value, row, index) {
					return formatStr(value, 13);
				}
			}, {
				field : 'apkVersion',
				title : '应用版本',
				width : 65
			}, {
				field : 'appCode',
				title : '应用编码',
				width : 150,
				formatter : function(value, row, index) {
					return formatStr(value, 13);
				}
			}, {
				field : 'msgType',
				title : '消息类型',
				width : 80
			}, {
				field : 'label',
				title : '消息标签',
				width : 80
			}, {
				field : 'message',
				title : '提示消息',
				width : 150,
				formatter : function(value, row, index) {
					return formatStr(value, 15);
				}
			}, {
				field : 'isValid',
				title : '状态',
				width : 50,
				formatter : function(value, row, index) {
					if (value == "1")
						return "合法";
					return "非法";
				}
			}, {
				field : 'createTime',
				title : '生成时间',
				width : 180,
				formatter : function(value, row, index) {
					return new Date(value).format('yyyy-MM-dd hh:mm:ss');
				}
			} ] ],
			pagination : true,
			rownumbers : false,
			fitColumns : true,
			singleSelect : true,
			onBeforeLoad : function(param) {
			},
			onDblClickRow : function(rowIndex, rowData) {
				showDetail(rowData);
			}
		});
		$('#msgType').combobox({
			url : getUrl('type/singlelist'),
			valueField : 'id',
			textField : 'msgName',
			panelHeight : 'auto'
		});
		$('#inputMail').combobox({
			url : getUrl('log/mailgroup'),
			valueField : 'id',
			textField : 'groupName',
			panelHeight : 'auto',
			multiple : true
		});
		pageInit();

		$('#aQuery').bind("click", function() {
			queryLog();
		});
		$('#aDown').bind("click", function() {
			down();
		});
		$('#aMail').bind("click", function() {
			mail();
		});
		$('#aSend').bind("click", function() {
			var ids = $('#inputMail').combobox('getValues');
			if(ids){
				reqUrl = getUrl('log/mail') + "?pageNo=1&pageSize=60000&ids=" + ids.join(',');
				submitQuery();
			}
		});
		$('#aClose').bind("click", function() {
			$(dialogId).dialog('close');
		});
	});
	var pageCallback = function() {
		queryLog();
	};
	var queryLog = function() {
		reqUrl = getUrl('log/list') + "?pageNo=" + page_no + "&pageSize="
				+ page_size;
		$(formId).form('submit', {
			url : reqUrl,
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.total == 0) {
					$.messager.show({
						title : '提示',
						msg : '没查询到数据'
					});
					$(tableId).datagrid('loadData', {
						total : 0,
						rows : []
					});
				} else {
					$(tableId).datagrid('clearSelections');
					$(tableId).datagrid('loadData', result);
				}
			}
		});
	}
	var down = function() {
		reqUrl = getUrl('log/down') + "?pageNo=1&pageSize=60000";
		submitQuery();
	}
	var mail = function() {
		$('#dgMail').dialog('open').dialog('setTitle', '发送邮件');
	}
	var submitQuery = function() {
		var data = $(tableId).datagrid('getData');
		var userID = $('#userID').val();
		if (data.total == 0 || data.total > 2000) {
			$('#userID').focus();
			alert('无法处理，请重新设定查询条件：\r\n1、无消息记录可下载\r\n2、下载的记录数超出2000行');
			return;
		}

		$(formId).form('submit', {
			url : reqUrl,
			success : function(result) {
				var result = eval('(' + result + ')');
				if (!result.success) {
					$.messager.show({
						title : '提示',
						msg : '无法生成消息文件，请重新设定查询条件.'
					});
				} else {
					$.messager.show({
						title : '提示',
						msg : '操作已成功.'
					});
				}
			}
		});
	};

	var showDetail = function(row) {
		$(dialogId).dialog('open').dialog('setTitle', '消息明细');
		$('#l1').text(row.id);
		$('#l2').text(row.userID);
		$('#l3').text(row.userGroup);
		$('#l4').text(row.area);
		$('#l5').text(row.osVersion);
		$('#l6').text(row.apkVersion);
		$('#l7').text(row.appCode);
		$('#l8').text(row.msgType);
		$('#l9').text(row.label);
		$('#l10').text(row.tempCode);
		$('#l11').text(row.moduleUrl);
		$('#l12').text(row.payAgentUrl);
		$('#l13').text(row.message);
		$('#l14').text(row.data);
		$('#l15').text(row.ext);

		if (row.isValid == "1") {
			$('#l16').text('合法');
		} else {
			$('#l16').text('非法');
		}
		var cTime = new Date(row.createTime).format('yyyy-MM-dd hh:mm:ss');
		$('#l17').text(cTime);

	};
</script>
</head>

<body>
	<div id="p" class="easyui-panel" title="查询条件"
		style="width:1000px;height:130x;padding:10px;">
		<form:form id="ff" method="post" modelAttribute="log">
			<table cellpadding="5">
				<tr>
					<td>用户编号:</td>
					<td><form:input path="userID" maxlength="20" class="textbox"
							style="width:150px" /></td>

					<td>用户分组:</td>
					<td><form:input path="userGroup" maxlength="150"
							class="textbox" style="width:150px" /></td>
					<td>驻地标识:</td>
					<td><form:input path="area" maxlength="150" class="textbox"
							style="width:150px" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>应用编码:</td>
					<td><form:input path="appCode" maxlength="20" class="textbox"
							style="width:150px" /></td>
					<td>消息类型:</td>
					<td><form:input path="msgType" maxlength="20"
							class="easyui-combobox" style="width:150px" /></td>
					<td>消息标签:</td>
					<td><form:input path="label" maxlength="20" class="textbox"
							style="width:150px" /></td>
					<td>状态:</td>
					<td><form:select path="msgStatus" items="${status}"
							itemLabel="key" itemValue="value" style="width:80px" /></td>
				</tr>
				<tr>
					<td colspan="8" style="text-align:center;"><a id="aQuery"
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-ok">查询</a>&nbsp;&nbsp; <a id="aDown"
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-save">下载</a>&nbsp;&nbsp;&nbsp;<a id="aMail"
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-remove">发送邮件</a></td>
				</tr>
			</table>
		</form:form>
	</div>
	<br />
	<table id="tb"></table>
	<div id="dlg" class="easyui-dialog"
		style="width:700px;height:500px;padding:10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">明细信息</div>
		<form:form id="fDetail" method="post">
			<div class="fitem">
				<label>消息编号:</label> <label id="l1"></label>
			</div>
			<div class="fitem">
				<label>用户编号:</label> <label id="l2"></label>
			</div>
			<div class="fitem">
				<label>用户分组:</label> <label id="l3"></label>
			</div>
			<div class="fitem">
				<label>所属驻地:</label> <label id="l4"></label>
			</div>
			<div class="fitem">
				<label>系统版本:</label> <label id="l5"></label>
			</div>
			<div class="fitem">
				<label>应用版本:</label> <label id="l6"></label>
			</div>
			<div class="fitem">
				<label>应用编码:</label> <label id="l7"></label>
			</div>
			<div class="fitem">
				<label>消息类型:</label> <label id="l8"></label>
			</div>
			<div class="fitem">
				<label>消息标签:</label> <label id="l9"></label>
			</div>
			<div class="fitem">
				<label>模板编码:</label> <label id="l10"></label>
			</div>
			<div class="fitem">
				<label>调度地址:</label> <label id="l11"></label>
			</div>
			<div class="fitem">
				<label>支付代理:</label> <label id="l12"></label>
			</div>
			<div class="fitem">
				<label>提示信息:</label> <label id="l13" style="width:150px"></label>
			</div>
			<div class="fitem">
				<label>输入数据:</label> <label id="l14" style="width:150px"></label>
			</div>
			<div class="fitem">
				<label>扩展信息:</label> <label id="l15" style="width:150px"></label>
			</div>
			<div class="fitem">
				<label>消息状态:</label> <label id="l16"></label>
			</div>
			<div class="fitem">
				<label>生成时间:</label> <label id="l17" style="width:150px"></label>
			</div>
		</form:form>
	</div>
	<div id="dlg-buttons">
		<a id="aClose" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel">关闭</a>
	</div>

	<div id="dgMail" class="easyui-dialog"
		style="width:200px;height:300px;padding:10px 20px" closed="true"
		buttons="#mail-buttons">
		<div class="ftitle">请选择要发送的分组</div>
		<input id="inputMail" class="easyui-combobox" style="width:150px;" />
	</div>
	<div id="mail-buttons">
		<a id="aSend" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok">发送</a>
	</div>
</body>
</html>
