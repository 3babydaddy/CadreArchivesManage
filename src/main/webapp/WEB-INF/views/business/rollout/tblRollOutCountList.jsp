<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转出管理人员管理</title>
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
			var startCreateDate = $("#startCreateDate").val();
			var endCreateDate = $("#endCreateDate").val();
			var saveUnit = $("#saveUnitId").val();
			var recipient = $("#recipient").val();
			
			$.ajax({
				type:'post',
				url:'${ctx}/rollout/tblRollOut/export',
				data:{'startCreateDate':startCreateDate, 'endCreateDate':endCreateDate, 'saveUnit':saveUnit, 'recipient':recipient},
				//dataType:'json',
				success:function(result){
					if(result.flag == 'success'){
						$("#btnExport").attr("disabled", false);
						window.location.href = "${ctx}/rollout/tblRollOut/doDown?filePath="+encodeURIComponent(result.filePath);
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
			max-width: 380px;
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
		<li class="active"><a href="#">转出管理人员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblRollOutPersons" action="${ctx}/rollout/tblRollOut/querycountlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>转出开始时间：</label>
				<input id="startCreateDate" name="startCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblRollOutPersons.startCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>转出截止时间：</label>
				<input id="endCreateDate" name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblRollOutPersons.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>接收单位：</label>
				<sys:treeselect id="saveUnit" name="saveUnit" allowClear="true" value="${tblRollOutPersons.saveUnit}" 
									labelName="saveUnitName" labelValue="${tblRollOutPersons.saveUnitName}" title="单位列表" url="/sys/dict/treeDataPop" ></sys:treeselect>
			</li>
			<li><label>接收人：</label>
				<form:input path="recipient" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<div style="float:right;">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<li class="btns"><input id="btnCancel" class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
				<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="exportData();" value="导出"/></li>
			</div>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>转出日期</th>
				<th>姓名</th>
				<th>单位及职务</th>
				<th>转出形式</th>
				<th>转出事由</th>
				<th>接收单位</th>
				<th>接收人</th>
				<th>材料份数</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblRollOutPersons">
			<tr>
				<td>
					${tblRollOutPersons.xh}
				</td>
				<td>
					<fmt:formatDate value="${tblRollOutPersons.rollOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblRollOutPersons.name}
				</td>
				<td>
					${tblRollOutPersons.duty}
				</td>
				<td>
					${fns:getDictLabel(tblRollOutPersons.outType, 'roll_out_type', '')}
				</td>
				<td>
					${fns:getDictLabel(tblRollOutPersons.reason, 'roll_out_reason', '')}
				</td>
				<td title="${tblRollOutPersons.saveUnitName}">
					${tblRollOutPersons.saveUnitName}
				</td>
				<td>
					${tblRollOutPersons.recipient}
				</td>
				<td>
					${tblRollOutPersons.filesNo}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>