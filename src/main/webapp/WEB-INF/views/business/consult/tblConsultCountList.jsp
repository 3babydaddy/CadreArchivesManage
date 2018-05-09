<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查阅档案管理</title>
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
				url:'${ctx}/consult/tblConsultArchives/export',
				data:{'startBorrowDate':startBorrowDate, 'endBorrowDate':endBorrowDate, 'consultUnit':consultUnit, 'perStr':perStr},
				//dataType:'json',
				success:function(result){
					if(result.flag == 'success'){
						$("#btnExport").attr("disabled", false);
						window.location.href = "${ctx}/consult/tblConsultArchives/doDown?filePath="+encodeURIComponent(result.filePath);
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
			max-width: 300px;
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
		<li class="active"><a href="#">查阅档案列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblConsultArchives" action="${ctx}/consult/tblConsultArchives/querycountlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>查阅开始日期：</label>
				<input id="startBorrowDate" name="startBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblConsultArchives.startBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>查阅截止日期：</label>
				<input id="endBorrowDate" name="endBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblConsultArchives.endBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>查阅单位：</label>
				<sys:treeselect url="/sys/dict/treeDataPop" id="consultUnit" name="consultUnit" allowClear="true" value="${tblConsultArchives.consultUnit}" 
									labelName="consultUnitName" labelValue="${tblConsultArchives.consultUnitName}" title="单位列表"></sys:treeselect>
			</li>
			<li><label>查阅人：</label>
				<form:input path="perStr" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium" style="width:220px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('audit_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<div style="float:right;margin-right:2px;">
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
				<th>查阅日期</th>
				<th>查阅单位</th>
				<th>姓名</th>
				<th>单位</th>
				<th>职务</th>
				<th>政治面貌</th>
				<th>被查阅人数</th>
				<th>查阅人</th>
				<th>查阅人数</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblConsultArchives">
			<tr>
				<td rowspan="${tblConsultArchives.tblCheckedTargetList.size()}">
					${tblConsultArchives.xh}
				</td>
				<td rowspan="${tblConsultArchives.tblCheckedTargetList.size()}">
					<fmt:formatDate value="${tblConsultArchives.borrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td title="${tblConsultArchives.consultUnitName}" rowspan="${tblConsultArchives.tblCheckedTargetList.size()}">
					${tblConsultArchives.consultUnitName}
				</td>
				<c:forEach items="${tblConsultArchives.tblCheckedTargetList}" var="tblCheckedTarget" varStatus="sign">
						<c:if test="${sign.count == 1}">
							<td>${tblCheckedTarget.name}</td>
							<td title="${tblCheckedTarget.unitName}">${tblCheckedTarget.unitName}</td>
							<td>${tblCheckedTarget.duty}</td>
							<td>${tblCheckedTarget.politicalStatus}</td>
						</c:if>
				</c:forEach>
				<td rowspan="${tblConsultArchives.tblCheckedTargetList.size()}">
					${tblConsultArchives.consultTarNum}
				</td>
				<td rowspan="${tblConsultArchives.tblCheckedTargetList.size()}">
					${tblConsultArchives.perStr}
				</td>
				<td rowspan="${tblConsultArchives.tblCheckedTargetList.size()}">
					${tblConsultArchives.consultPerNum}
				</td>
				<td rowspan="${tblConsultArchives.tblCheckedTargetList.size()}">
					${fns:getDictLabel(tblConsultArchives.status, 'audit_status', '')}
				</td>
			</tr>
			<c:forEach items="${tblConsultArchives.tblCheckedTargetList}" var="tblCheckedTarget" varStatus="sign">
				<c:if test="${sign.count > 1}">
					<tr>
						<td>${tblCheckedTarget.name}</td>
						<td title="${tblCheckedTarget.unitName}">${tblCheckedTarget.unitName}</td>
						<td>${tblCheckedTarget.duty}</td>
						<td>${tblCheckedTarget.politicalStatus}</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>