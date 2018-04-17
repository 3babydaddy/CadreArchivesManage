<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>移交人员列表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function setNull(){
			$("input[type='text']").each(function(){
				$(this).val("");
			})
			//$("input[type='hidden']").each(function(){
			//	$(this).val("");
			//})
			$("select").val("");
			//$("select").each(function(){
			//	$(this).select2("val","");
			//})
		}
	</script>
	<style type="text/css">
		.table th, .table td{
			text-align : center;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">零散材料移交人员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblHandOverFiles" action="${ctx}/scattereds/tblScatteredFiles/personlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>材料名称：</label>
				<form:input path="filesNames" htmlEscape="false" maxlength="2000" class="input-medium"/>
			</li>
			<input type="hidden" name="mainId" value="${mainId}">
			<div style="float:right;">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
			</div>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>职务</th>
				<th>材料名称</th>
				<th>正本（卷）</th>
				<shiro:hasPermission name="scattereds:tblHandOverFiles:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblHandOverFiles">
			<tr>
				<td><a href="${ctx}/scattereds/tblHandOverFiles/form?id=${tblHandOverFiles.id}"></a>
					${tblHandOverFiles.name}
				</td>
				<td>
					${tblHandOverFiles.duty}
				</td>
				<td>
					${tblHandOverFiles.filesNames}
				</td>
				<td>
					${tblHandOverFiles.originalNo}
				</td>
				<shiro:hasPermission name="scattereds:tblHandOverFiles:edit"><td>
    				<a href="${ctx}/scattereds/tblHandOverFiles/form?id=${tblHandOverFiles.id}">修改</a>
					<a href="${ctx}/scattereds/tblHandOverFiles/delete?id=${tblHandOverFiles.id}" onclick="return confirmx('确认要删除该移交人员列表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>