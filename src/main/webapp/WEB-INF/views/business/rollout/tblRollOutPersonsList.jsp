<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转出管理人员列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		
			$('#searchForm').on("focusin", "#batchNum", function() {
			       $(this).prop('readonly', true);  
			});
			$('#searchForm').on("focusout", "#batchNum", function() {
			   $(this).prop('readonly', false); 
			});
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
		<li class="active"><a href="#">转出管理人员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblRollOutPersons" action="${ctx}/rollout/tblRollOut/personlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width:85px;">创建时间：</label>
				<input name="startCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblRollOutPersons.startCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					至
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblRollOutPersons.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>批次号：</label>
				<input id="batchNum" name="batchNum" htmlEscape="false" maxlength="64" value="${batchNum}" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label style="width:85px;">单位及职务：</label>
				<form:input path="duty" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<input type="hidden" name="mainId" value="${mainId}">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>单位及职务</th>
				<th>转出事由</th>
				<th>正本（卷）</th>
				<th>副本（卷）</th>
				<th>档案材料（份）</th>
				<th>回执</th>
				<shiro:hasPermission name="rollout:tblRollOutPersons:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblRollOutPersons">
			<tr>
				<td><a href="${ctx}/rollout/tblRollOutPersons/form?id=${tblRollOutPersons.id}"></a>
					${tblRollOutPersons.name}
				</td>
				<td>
					${tblRollOutPersons.duty}
				</td>
				<td>
					${tblRollOutPersons.reason}
				</td>
				<td>
					${tblRollOutPersons.originalNo}
				</td>
				<td>
					${tblRollOutPersons.viceNo}
				</td>
				<td>
					${tblRollOutPersons.filesNo}
				</td>
				<td>
					
				</td>
				<shiro:hasPermission name="rollout:tblRollOutPersons:edit"><td>
    				<a href="${ctx}/rollout/tblRollOutPersons/form?id=${tblRollOutPersons.id}">修改</a>
					<a href="${ctx}/rollout/tblRollOutPersons/delete?id=${tblRollOutPersons.id}" onclick="return confirmx('确认要删除该转出管理人员列表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>