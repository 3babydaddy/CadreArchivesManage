<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>零散材料移交人员管理</title>
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
		
		function delData(){
			var idStr = "";
			var rows = getRowData();
			if(rows.length == 0){
				alertx("请选择记录");
				return;
			}
			for(var i = 0; i < rows.length; i++){
				if(idStr == ""){
					idStr = rows[i].value; 
				}else{
					idStr += "," + rows[i].value; 
				}
			}
			window.location.href = "${ctx}/scattereds/tblScatteredFiles/delete?idStr="+idStr;
		}
		
		function editData(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条记录");
				return;
			}
			window.location.href = "${ctx}/scattereds/tblScatteredFiles/form?id="+rows[0].value;
		}
		
		function getRowData(){
			return $("input[type='checkbox']:checked");
		}
		
		function setNull(){
			$("input[type='text']").each(function(){
				$(this).val("");
			})
			$("input[type='hidden']").each(function(){
				$(this).val("");
			})
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
		<li class="active"><a href="${ctx}/scattereds/tblScatteredFiles/">零散材料移交人员列表</a></li>
		<shiro:hasPermission name="scattereds:tblScatteredFiles:edit"><li><a href="${ctx}/scattereds/tblScatteredFiles/form">零散材料移交人员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tblScatteredFiles" action="${ctx}/scattereds/tblScatteredFiles/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>移交时间：</label>
				<input name="startHandOverDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblScatteredFiles.startHandOverDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					至
				<input name="endHandOverDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblScatteredFiles.endHandOverDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>移交单位：</label>
				<sys:treeselect url="/sys/dict/treeDataPop" id="handOverUnit" name="handOverUnit" allowClear="true" value="${tblScatteredFiles.handOverUnit}" 
									labelName="handOverUnitName" labelValue="${tblScatteredFiles.handOverUnitName}" title="单位列表"></sys:treeselect>
			
			</li>
			<li><label>经手人：</label>
				<form:input path="operator" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>接收人：</label>
				<form:input path="recipient" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<div id="toolbar">
	    <ul class="nav nav-pills">
	        <li><a <a href="${ctx}/scattereds/tblScatteredFiles/form"><i class="icon-plus"></i>&nbsp;新增</a></li>
	        <li><a onclick="editData();"><i class="icon-edit"></i>&nbsp;编辑</a></li>
	        <li><a onclick="delData();"><i class="icon-remove"></i>&nbsp;删除</a></li>
	        <li><a onclick=""><i class="icon-share-alt"></i>&nbsp;导入</a></li>
	    </ul>
	</div>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<td>选择</td>
				<th>移交单位</th>
				<th>移交日期</th>
				<th>经办人</th>
				<th>接收人</th>
				<th>录入时间</th>
				<th>录入人</th>
				<shiro:hasPermission name="scattereds:tblScatteredFiles:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblScatteredFiles">
			<tr>
				<td>
					<input type="checkbox" value="${tblBorrowArchives.id}" />
				</td>
				<td><a href="${ctx}/scattereds/tblScatteredFiles/personlist?mainId=${tblScatteredFiles.id}">
					${tblScatteredFiles.handOverUnitName}</a>
				</td>
				<td>
					<fmt:formatDate value="${tblScatteredFiles.handOverDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblScatteredFiles.operator}
				</td>
				<td>
					${tblScatteredFiles.recipient}
				</td>
				<td>
					<fmt:formatDate value="${tblScatteredFiles.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblScatteredFiles.createBy.id}
				</td>
				<shiro:hasPermission name="scattereds:tblScatteredFiles:edit"><td>
    				<a href="${ctx}/scattereds/tblScatteredFiles/form?id=${tblScatteredFiles.id}">修改</a>
					<a href="${ctx}/scattereds/tblScatteredFiles/delete?id=${tblScatteredFiles.id}" onclick="return confirmx('确认要删除该零散材料移交人员吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>