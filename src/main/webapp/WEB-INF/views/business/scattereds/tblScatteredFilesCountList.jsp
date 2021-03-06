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
			var startHandOverDate = $("#startHandOverDate").val();
			var endHandOverDate = $("#endHandOverDate").val();
			var handOverUnit = $("#handOverUnitId").val();
			var operator = $("#operator").val();
			var recipient = $("#recipient").val();
			
			$.ajax({
				type:'post',
				url:'${ctx}/scattereds/tblScatteredFiles/export',
				data:{'startHandOverDate':startHandOverDate, 'endHandOverDate':endHandOverDate, 'handOverUnit':handOverUnit, 'operator':operator, 'recipient':recipient},
				//dataType:'json',
				success:function(result){
					if(result.flag == 'success'){
						$("#btnExport").attr("disabled", false);
						window.location.href = "${ctx}/scattereds/tblScatteredFiles/doDown?filePath="+encodeURIComponent(result.filePath);
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
			min-width: 60px;
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
			overflow:hidden;
		}
		input[readonly]{
			background-color: white;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">零散材料移交统计列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblScatteredFiles" action="${ctx}/scattereds/tblScatteredFiles/querycountlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>移交开始时间：</label>
				<input id="startHandOverDate" name="startHandOverDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblScatteredFiles.startHandOverDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>移交截止时间：</label>
				<input id="endHandOverDate" name="endHandOverDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblScatteredFiles.endHandOverDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>移交单位：</label>
				<sys:treeselect id="handOverUnit" name="handOverUnit" allowClear="true" value="${tblScatteredFiles.handOverUnit}" cssStyle="width:179px;"
									labelName="handOverUnitName" labelValue="${tblScatteredFiles.handOverUnitName}" title="单位列表" url="/sys/dict/treeDataPop" ></sys:treeselect>
			</li>
			<li><label>经手人：</label>
				<form:input path="operator" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>接收人：</label>
				<form:input path="recipient" htmlEscape="false" maxlength="64" class="input-medium"/>
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
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>移交单位</th>
				<th>移交日期</th>
				<th>移交人员</th>
				<th>移交人总数</th>
				<th>材料总数</th>
				<th>经办人</th>
				<th>接收人</th>
				<!-- <th>录入时间</th>
				<th>录入人</th> -->
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblScatteredFiles">
			<tr>
				<td>
					${tblScatteredFiles.xh}
				</td>
				<td>
					${tblScatteredFiles.handOverUnitName}
				</td>
				<td>
					<fmt:formatDate value="${tblScatteredFiles.handOverDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblScatteredFiles.handOverStr}
				</td>
				<td>
					${tblScatteredFiles.handOverNum}
				</td>
				<td>
					${tblScatteredFiles.fileNum}
				</td>
				<td>
					${tblScatteredFiles.operator}
				</td>
				<td>
					${tblScatteredFiles.recipient}
				</td>
			<%-- 	<td>
					<fmt:formatDate value="${tblScatteredFiles.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblScatteredFiles.createBy.id}
				</td> --%>
				<td>
					${fns:getDictLabel(tblScatteredFiles.status, 'audit_status', '')}
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