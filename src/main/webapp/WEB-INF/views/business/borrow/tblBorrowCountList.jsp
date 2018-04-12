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
		<li class="active"><a href="#">借阅管理列表</a></li>
	</ul>
	<sys:message content="${message}"/>		
	<form:form id="searchForm" modelAttribute="tblBorrowArchives" action="${ctx}/borrow/tblBorrowArchives/querycountlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>借阅日期：</label>
				<input name="startBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.startBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					至
				<input name="endBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.endBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>借阅单位：</label>
				<sys:treeselect url="/sys/dict/treeDataPop" id="consultUnit" name="consultUnit" allowClear="true" value="${tblBorrowArchives.consultUnit}" 
									labelName="consultUnitName" labelValue="${tblBorrowArchives.consultUnitName}" title="单位列表"></sys:treeselect>
			</li>
			<li><label>借阅人：</label>
				<form:input path="perStr" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" " value="导出"/></li>
		</ul>
	</form:form>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th> 
				<th>借阅日期</th>
				<th>借阅单位</th>
				<th>姓名</th>
				<th>单位</th>
				<th>职务</th>
				<th>政治面貌</th>
				<th>被借阅人数</th>
				<th>借阅人</th>
				<th>借阅人数</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblBorrowArchives">
			<tr>
				<td rowspan="${tblBorrowArchives.tblBorrowTargetList.size()}">
					${tblBorrowArchives.xh}
				</td>
				<td rowspan="${tblBorrowArchives.tblBorrowTargetList.size()}">
					<fmt:formatDate value="${tblBorrowArchives.borrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td rowspan="${tblBorrowArchives.tblBorrowTargetList.size()}">
					${tblBorrowArchives.consultUnitName}
				</td>
				<c:forEach items="${tblBorrowArchives.tblBorrowTargetList}" var="tblBorrowTarget" varStatus="sign">
						<c:if test="${sign.count == 1}">
							<td>${tblBorrowTarget.name}</td>
							<td>${tblBorrowTarget.unitName}</td>
							<td>${tblBorrowTarget.duty}</td>
							<td>${tblBorrowTarget.politicalStatus}</td>
						</c:if>
				</c:forEach>
				<td rowspan="${tblBorrowArchives.tblBorrowTargetList.size()}">
					${tblBorrowArchives.borrowTarNum}
				</td>
				<td rowspan="${tblBorrowArchives.tblBorrowTargetList.size()}">
					${tblBorrowArchives.perStr}
				</td>
				<td rowspan="${tblBorrowArchives.tblBorrowTargetList.size()}">
					${tblBorrowArchives.borrowPerNum}
				</td>
			</tr>
			<c:forEach items="${tblBorrowArchives.tblBorrowTargetList}" var="tblBorrowTarget" varStatus="sign">
				<c:if test="${sign.count > 1}">
					<tr>
						<td>${tblBorrowTarget.name}</td>
						<td>${tblBorrowTarget.unitName}</td>
						<td>${tblBorrowTarget.duty}</td>
						<td>${tblBorrowTarget.politicalStatus}</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
</body>
</html>