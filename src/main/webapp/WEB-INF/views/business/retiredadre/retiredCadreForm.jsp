<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退休干部信息操作管理</title>
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
			$("#btnSubmit").click(function(){
				setTimeout(function(){ 
					var errorArry = $("label.error");
					for(var i = 0; i < errorArry.length; i++){
						$("label.error")[i].innerHTML="必填信息";
					}
				}, 100);	
			});
		});
	</script>
	<style type="text/css">
		input[readonly]{
			background-color: white;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/retiredadre/retiredCadre/">退休干部信息操作列表</a></li>
		<li class="active"><a href="#">退休干部信息<c:if test="${empty retiredCadre.id }">新增</c:if><c:if test="${not empty retiredCadre.id }">编辑</c:if></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="retiredCadre" action="${ctx}/retiredadre/retiredCadre/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">编号：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="input-xlarge number required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生日期：</label>
			<div class="controls">
				<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${retiredCadre.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" style="width:268px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历：</label>
			<div class="controls">
				<form:select path="education" class="input-xlarge required" style="width:284px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('education')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任免文号：</label>
			<div class="controls">
				<form:input path="referenceNo" htmlEscape="false" maxlength="64" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建档时间：</label>
			<div class="controls">
				<input name="archivesCreatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${retiredCadre.archivesCreatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" style="width:268px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转死亡时间：</label>
			<div class="controls">
				<input name="diedTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${retiredCadre.diedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" style="width:268px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转档案时间：</label>
			<div class="controls">
				<input name="recordOfficeTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${retiredCadre.recordOfficeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" style="width:268px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退休工作单位：</label>
			<div class="controls">
				<sys:treeselect url="/sys/dict/treeDataPop" id="workUnit" name="workUnit" allowClear="true" value="${retiredCadre.workUnit}" 
									labelName="workUnitName" labelValue="${retiredCadre.workUnitName}" title="单位列表" cssStyle="width:240px;"></sys:treeselect>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档案局地址：</label>
			<div class="controls">
				<form:input path="recordOfficeAddress" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号：</label>
			<div class="controls">
				<form:input path="certificateNo" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required" style="width:284px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('retired_cadre_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>