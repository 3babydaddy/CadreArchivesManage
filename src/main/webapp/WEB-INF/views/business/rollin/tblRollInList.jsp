<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转入管理人员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			//全选或全取消
			$("#selected").click(function(){
				var flag = document.getElementById("selected").checked;
				var $tbr = $('table tbody input');  
				if(flag){
					for(var i = 0; i < $tbr.length; i++){
						$tbr[i].checked = true;
					}
				}else{
					for(var i = 0; i < $tbr.length; i++){
						$tbr[i].checked = false;
					}
				}
			})
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
				alertx("请选择一条数据");
				return;
			}
			for(var i = 0; i < rows.length; i++){
				var mainId = rows[i].value.slice(0, rows[i].value.indexOf(','));
				if(idStr == ""){
					idStr = mainId; 
				}else{
					idStr += "," + mainId; 
				}
			}
			var url = "${ctx}/rollin/tblRollIn/delete?idStr="+idStr;
			confirmx('确定要删除选择的数据！！！', url);
		}
		
		function editData(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条数据");
				return;
			}
			var mainId = rows[0].value.slice(0, rows[0].value.indexOf(','));
			window.location.href = "${ctx}/rollin/tblRollIn/form?id="+mainId;
		}
		
		function lookData(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条数据");
				return;
			}
			var mainId = rows[0].value.slice(0, rows[0].value.indexOf(','));
			window.location.href = "${ctx}/rollin/tblRollIn/look?id="+mainId;
		}
		
		//回执
		function receiptBill(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条数据");
				return;
			}
			var mainId = rows[0].value.slice(0, rows[0].value.indexOf(','));
			window.location.href = "${ctx}/order/createReceiptBill?rollInId="+mainId;
		}
		
		//审核借阅数据
		function auditData(){
			var idStr = "";
			var status = "";
			var rows = getRowData();
			if(rows.length == 0){
				alertx("请选择一条数据");
				return;
			}
			for(var i = 0; i < rows.length; i++){
				var mainId = rows[i].value.slice(0, rows[i].value.indexOf(','));
				var status = rows[i].value.slice(rows[i].value.indexOf(',')+1);
				if(status == '1'){
					if(idStr == ""){
						idStr = mainId; 
					}else{
						idStr += "," + mainId; 
					}
				}else{
					alertx('请选择未审核的数据！！！');
					return;
				}
			}
			
			var url = "${ctx}/rollin/tblRollIn/auditData?idStr="+idStr+"&status=3";
			confirmx('请确定是否审核通过！！！', url);
		}
		
		function getRowData(){
			return $("table tbody input[type='checkbox']:checked");
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
		
	</script>
	<style type="text/css">
		.table th, .table td{
			text-align : center;
		}
		.ul-form li label{
			width: 161px !important;
		}
		body {
			font-family: "微软雅黑";
			font-size:120%;
		}
		#btnCancel,#btnSubmit{
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
		<li class="active"><a href="#">转入管理人员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblRollIn" action="${ctx}/rollin/tblRollIn/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>转入开始时间：</label>
				<input name="startRollInTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblRollIn.startRollInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>转入截止时间：</label>
				<input name="endRollInTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblRollIn.endRollInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label style="width:85px;">原存档单位：</label>
				<sys:treeselect id="beforeUnit" name="beforeUnit" allowClear="true" value="${tblRollIn.beforeUnit}" 
									labelName="beforeUnitName" labelValue="${tblRollIn.beforeUnitName}" title="单位列表" url="/sys/dict/treeDataPop" ></sys:treeselect>
			</li>
			<li><label>接收人：</label>
				<form:input path="recipient" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>批次号：</label>
				<form:input path="batchNum" htmlEscape="false" maxlength="64" class="input-medium" />
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
			</div>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<div id="toolbar">
	    <ul class="nav nav-pills">
	        <li><a <a href="${ctx}/rollin/tblRollIn/form"><i class="icon-plus"></i>&nbsp;新增</a></li>
	        <li><a onclick="editData();"><i class="icon-edit"></i>&nbsp;编辑</a></li>
	        <li><a onclick="lookData();"><i class="icon-eye-open"></i>&nbsp;查看</a></li>
	        <li><a onclick="delData();"><i class="icon-remove"></i>&nbsp;删除</a></li>
	        <li><a onclick="receiptBill();"><i class="icon-share-alt"></i>&nbsp;回执</a></li>
	        <shiro:hasRole name="admin">
	        	<li><a onclick="auditData();"><i class=" icon-legal"></i>&nbsp;审核</a></li>
	        </shiro:hasRole>
	    </ul>
	</div>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input id="selected" type="checkbox" /></th>
				<th>批次号</th>
				<th>转入时间</th>
				<th>原存档单位</th>
				<th>接收人</th>
				<th>录入时间</th>
				<th>录入者</th>
				<th>状态</th>
				<shiro:hasPermission name="rollins:tblRollIn:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblRollIn">
			<tr jumpURL="${ctx}/rollin/tblRollIn/look?id=${tblRollIn.id}" onmouseover="$(this).addClass('row-color');" onmouseout="$(this).removeClass('row-color');">
				<td>
					<input type="checkbox" value="${tblRollIn.id},${tblRollIn.status}" />
				</td>
				<td><a href="${ctx}/rollin/tblRollIn/personlist?mainId=${tblRollIn.id}&batchNum=${tblRollIn.character}zi${tblRollIn.number}hao">
					${tblRollIn.character}字${tblRollIn.number}号</a>
				</td>
				<td>
					<fmt:formatDate value="${tblRollIn.rollInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblRollIn.beforeUnitName}
				</td>
				<td>
					${tblRollIn.recipient}
				</td>
				<td>
					<fmt:formatDate value="${tblRollIn.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblRollIn.createBy.id}
				</td>
				<td>
					${fns:getDictLabel(tblRollIn.status, 'audit_status', '')}
				</td>
				<shiro:hasPermission name="rollins:tblRollIn:edit"><td>
    				<a href="${ctx}/rollin/tblRollIn/form?id=${tblRollIn.id}">修改</a>
					<a href="${ctx}/rollin/tblRollIn/delete?id=${tblRollIn.id}" onclick="return confirmx('确认要删除该转入管理人员吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>