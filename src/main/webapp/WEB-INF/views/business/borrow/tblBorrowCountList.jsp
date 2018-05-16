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
			});
			$("input[type='hidden']").each(function(){
				$(this).val("");
			});
			$("select").val("");
			//$("select").each(function(){
			//	$(this).select2("val","");
			//})
		}
		
		//导出数据
		function exportData(){
			$("#btnExport").attr("disabled", true);
			var startBorrowDate = $("#startBorrowDate").val();
			var endBorrowDate = $("#endBorrowDate").val();
			var consultUnit = $("#consultUnitId").val();
			var perStr = $("#perStr").val();
			
			$.ajax({
				type:'post',
				url:'${ctx}/borrow/tblBorrowArchives/export',
				data:{'startBorrowDate':startBorrowDate, 'endBorrowDate':endBorrowDate, 'consultUnit':consultUnit, 'perStr':perStr},
				//dataType:'json',
				success:function(result){
					if(result.flag == 'success'){
						$("#btnExport").attr("disabled", false);
						window.location.href = "${ctx}/borrow/tblBorrowArchives/doDown?filePath="+encodeURIComponent(result.filePath);
					}else{
						alertx('导出失败');
					}
				}
			});
		}
	</script>
	<style type="text/css">
		.table th, .table td{
			text-align : center;
			max-width: 310px;
		}
		.ul-form li.btns{
			padding-left: 0px !important;
		}
		.ul-form li label{
			width: 155px !important;
		}
		body {
			font-family: "微软雅黑";
			font-size:120%;
		}
		#btnCancel,#btnSubmit,#btnExport{
			font-size : 150%;
			white-space:nowrap;
			overflow:hidden;
		}
		a,th,td,label,select{
			font-size : 120%;
			white-space:nowrap;
			overflow:hidden;
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
			<li><label>借阅开始日期：</label>
				<input id="startBorrowDate" name="startBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.startBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>借阅截止日期：</label>
				<input id="endBorrowDate" name="endBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.endBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>借阅单位：</label>
				<sys:treeselect url="/sys/dict/treeDataPop" id="consultUnit" name="consultUnit" allowClear="true" value="${tblBorrowArchives.consultUnit}" 
									labelName="consultUnitName" labelValue="${tblBorrowArchives.consultUnitName}" title="单位列表" cssStyle="width:179px;"></sys:treeselect>
			</li>
			<li><label>借阅人：</label>
				<form:input path="perStr" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium" style="width:220px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('audit_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<div style="float:right;">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<li class="btns"><input id="btnCancel" class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
				<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="exportData();" value="导出"/></li>
			</div>
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
				<th>状态</th>
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
				<td title="${tblBorrowArchives.consultUnitName}" rowspan="${tblBorrowArchives.tblBorrowTargetList.size()}">
					${tblBorrowArchives.consultUnitName}
				</td>
				<c:forEach items="${tblBorrowArchives.tblBorrowTargetList}" var="tblBorrowTarget" varStatus="sign">
						<c:if test="${sign.count == 1}">
							<td>${tblBorrowTarget.name}</td>
							<td title="${tblBorrowTarget.unitName}">${tblBorrowTarget.unitName}</td>
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
				<td rowspan="${tblBorrowArchives.tblBorrowTargetList.size()}">
					${fns:getDictLabel(tblBorrowArchives.status, 'audit_status', '')}
				</td>
			</tr>
			<c:forEach items="${tblBorrowArchives.tblBorrowTargetList}" var="tblBorrowTarget" varStatus="sign">
				<c:if test="${sign.count > 1}">
					<tr>
						<td>${tblBorrowTarget.name}</td>
						<td title="${tblBorrowTarget.unitName}">${tblBorrowTarget.unitName}</td>
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