<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统填报有关提示管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			$("textarea").attr("onkeyup","return isMaxLen(this)");
		});
		//在IE8下对textarea的字数长度限制的函数
		function isMaxLen(o){
			var nMaxLen=o.getAttribute ? parseInt(o.getAttribute("maxlength")) : "";  
			if(o.getAttribute && o.value.length>nMaxLen){  
				o.value=o.value.substring(0,nMaxLen)  
			}
		}
	</script>
</head>
<body>
	<ul class="breadcrumb">
        <li><a href="${ctx}/system/fieldInstruction/">系统填报有关提示列表</a></li>
        <li><span class="divider">/</span></li>
        <li class="active">系统填报有关提示<shiro:hasPermission name="system:fieldInstruction:edit">${not empty fieldInstruction.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="system:fieldInstruction:edit">查看</shiro:lacksPermission></li>
    </ul><br/>
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li><a href="${ctx}/system/fieldInstruction/">系统填报有关提示列表</a></li> --%>
<%-- 		<li class="active"><a href="${ctx}/system/fieldInstruction/form?id=${fieldInstruction.id}">系统填报有关提示<shiro:hasPermission name="system:fieldInstruction:edit">${not empty fieldInstruction.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="system:fieldInstruction:edit">查看</shiro:lacksPermission></a></li> --%>
<!-- 	</ul> -->
	<form:form id="inputForm" modelAttribute="fieldInstruction" action="${ctx}/system/fieldInstruction/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">数据类型：</label>
			<div class="controls">${fns:getDictLabel(fieldInstruction.dataType, 'YW_SJLX', '')}
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">模块类型：</label>
			<div class="controls">
				<form:select path="moduleType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('YW_MKLX')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">字段：</label>
			<div class="controls">
				<form:input path="fieldCode" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">字段名：</label>
			<div class="controls">
				${fieldInstruction.fieldName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提示信息：</label>
			<div class="controls">
				<form:textarea path="instruction" htmlEscape="false" rows="4" maxlength="1000" style="width:400px;font-size:14px;"/>
<!-- 				<span class="help-inline"><font color="red">*</font> </span> -->
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="100" style="width:400px;font-size:14px;"/>
			</div>
		</div>
		<div class="form-actions" style="border-top:none;margin-top:0;">
			<shiro:hasPermission name="system:fieldInstruction:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" style="outline:none;"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" style="outline:none;"/>
		</div>
	</form:form>
</body>
</html>