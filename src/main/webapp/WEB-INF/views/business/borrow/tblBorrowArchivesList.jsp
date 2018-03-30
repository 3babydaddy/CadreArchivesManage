<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借阅管理管理</title>
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
			window.location.href = "${ctx}/borrow/tblBorrowArchives/delete?idStr="+idStr;
		}
		
		function editData(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条记录");
				return;
			}
			window.location.href = "${ctx}/borrow/tblBorrowArchives/form?id="+rows[0].value;
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
		<li class="active"><a href="${ctx}/borrow/tblBorrowArchives/">借阅管理列表</a></li>
		<shiro:hasPermission name="borrow:tblBorrowArchives:edit"><li><a href="${ctx}/borrow/tblBorrowArchives/form">借阅管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tblBorrowArchives" action="${ctx}/borrow/tblBorrowArchives/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>借阅日期：</label>
				<input name="startBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.startBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					至
				<input name="endBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.endBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>借阅单位：</label>
				<form:input path="consultUnit" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>何人档案：</label>
				<form:input path="tarStr" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>归还日期：</label>
				<input name="startBackDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.startBackDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					至
				<input name="endBackDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.endBackDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>经办人：</label>
				<form:input path="operator" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="clearfix"></li>
			<li><label>借阅人：</label>
				<form:input path="perStr" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>归还人：</label>
				<form:input path="backOperator" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
		</ul>
	</form:form>
	
	<div id="toolbar">
	    <ul class="nav nav-pills">
	        <li><a <a href="${ctx}/borrow/tblBorrowArchives/form"><i class="icon-plus"></i>&nbsp;新增</a></li>
	         <li><a onclick="editData();"><i class="icon-edit"></i>&nbsp;编辑</a></li>
	        <li><a onclick="delData();"><i class="icon-remove"></i>&nbsp;删除</a></li>
	        <li><a onclick=""><i class="icon-share-alt"></i>&nbsp;归还</a></li>
	        <li><a onclick=""><i class="icon-share-alt"></i>&nbsp;送审</a></li>
	    </ul>
	</div>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>借阅日期</th>
				<th>借阅单位</th>
				<th>何人档案</th>
				<th>借阅人</th>
				<th>联系电话</th>
				<th>归还日期</th>
				<th>归还人</th>
				<th>经办人</th>
				<th>状态</th>
				<th>备注</th>
				<shiro:hasPermission name="borrow:tblBorrowArchives:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblBorrowArchives">
			<tr>
				<td>
					<input type="checkbox" value="${tblBorrowArchives.id}" />
				</td>
				<td><a href="${ctx}/borrow/tblBorrowArchives/form?id=${tblBorrowArchives.id}"></a>
					<fmt:formatDate value="${tblBorrowArchives.borrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblBorrowArchives.consultUnit}
				</td>
				<td>
					${tblBorrowArchives.tarStr}
				</td>
				<td>
					${tblBorrowArchives.perStr}
				</td>
				<td>
					
				</td>
				<td>
					<fmt:formatDate value="${tblBorrowArchives.borrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblBorrowArchives.operator}
				</td>
				<td>
					${tblBorrowArchives.operator}
				</td>
				<td>
					${tblBorrowArchives.status}
				</td>
				<td>
					${tblBorrowArchives.remarks}
				</td>
				<shiro:hasPermission name="borrow:tblBorrowArchives:edit"><td>
    				<a href="${ctx}/borrow/tblBorrowArchives/form?id=${tblBorrowArchives.id}">修改</a>
					<a href="${ctx}/borrow/tblBorrowArchives/delete?id=${tblBorrowArchives.id}" onclick="return confirmx('确认要删除该借阅管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>