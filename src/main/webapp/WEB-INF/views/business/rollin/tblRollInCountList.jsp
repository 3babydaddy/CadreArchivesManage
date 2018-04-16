<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转入管理人员管理</title>
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
		//导出数据
		function exportData(){
			var startCreateDate = $("#startCreateDate").val();
			var endCreateDate = $("#endCreateDate").val();
			var beforeUnit = $("#beforeUnitId").val();
			var recipient = $("#recipient").val();
			window.location.href = "${ctx}/rollin/tblRollIn/export?startCreateDate="+startCreateDate+"&endCreateDate="+endCreateDate+
														"&beforeUnit="+beforeUnit+"&recipient="+recipient;
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
		<li class="active"><a href="#">转入管理人员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblRollInPersons" action="${ctx}/rollin/tblRollIn/querycountlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>转入时间：</label>
				<input id="startCreateDate" name="startCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblRollInPersons.startCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					至
				<input id="endCreateDate" name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblRollInPersons.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label style="width:85px;">原存档单位：</label>
				<sys:treeselect id="beforeUnit" name="beforeUnit" allowClear="true" value="${tblRollInPersons.beforeUnit}" 
									labelName="beforeUnitName" labelValue="${tblRollInPersons.beforeUnitName}" title="单位列表" url="/sys/dict/treeDataPop" ></sys:treeselect>
			</li>
			<li><label>接收人：</label>
				<form:input path="recipient" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<div style="float:right;">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" onclick="exportData();" value="导出"/></li>
			</div>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>转入时间</th>
				<th>姓名</th>
				<th>单位及职务</th>
				<th>原存档单位</th>
				<th>接收人</th>
				<th>材料份数</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblRollInPersons">
			<tr>
				<td>
					${tblRollInPersons.xh}
				</td>
				<td>
					<fmt:formatDate value="${tblRollInPersons.rollInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblRollInPersons.name}
				</td>
				<td>
					${tblRollInPersons.duty}
				</td>
				<td>
					${tblRollInPersons.beforeUnitName}
				</td>
				<td>
					${tblRollInPersons.recipient}
				</td>
				<td>
					${tblRollInPersons.filesNo}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>