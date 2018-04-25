<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借阅管理管理</title>
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
		
		function editData(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条数据");
				return;
			}
			var mainId = rows[0].value.slice(0, rows[0].value.indexOf(','));
			window.location.href = "${ctx}/borrow/tblBorrowArchives/form?id="+mainId;
		}
		
		function lookData(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条数据");
				return;
			}
			var mainId = rows[0].value.slice(0, rows[0].value.indexOf(','));
			window.location.href = "${ctx}/borrow/tblBorrowArchives/look?id="+mainId;
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
			
			var url = "${ctx}/borrow/tblBorrowArchives/delete?idStr="+idStr;
			confirmx('确定要删除选择的数据！！！', url);
		}
		//归还
		function giveBack(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条数据");
				return;
			}
			var mainId = rows[0].value.slice(0, rows[0].value.indexOf(','));
			window.location.href = "${ctx}/borrow/tblGiveBack/form?mainId="+mainId;
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
					alertx("请选择未审核的数据！！！");
					return;
				}
			}
			
			var url = "${ctx}/borrow/tblBorrowArchives/auditData?idStr="+idStr+"&status=3";
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
			width: 115px !important;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">借阅管理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblBorrowArchives" action="${ctx}/borrow/tblBorrowArchives/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>借阅开始日期：</label>
				<input name="startBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.startBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>借阅截止日期：</label>
				<input name="endBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.endBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>借阅单位：</label>
				<sys:treeselect url="/sys/dict/treeDataPop" id="consultUnit" name="consultUnit" allowClear="true" value="${tblBorrowArchives.consultUnit}" 
									labelName="consultUnitName" labelValue="${tblBorrowArchives.consultUnitName}" title="单位列表"></sys:treeselect>
			</li>
			<li><label>何人档案：</label>
				<form:input path="tarStr" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>归还开始日期：</label>
				<input name="startBackDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.startBackDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>归还截止日期：</label>
				<input name="endBackDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblBorrowArchives.endBackDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
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
			<li><label>状态：</label>
				<form:select path="status" class="input-medium" style="width:220px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('audit_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<div style="float:right;">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
			</div>
		</ul>
	</form:form>
	
	<div id="toolbar">
	    <ul class="nav nav-pills">
	        <li><a <a href="${ctx}/borrow/tblBorrowArchives/form"><i class="icon-plus"></i>&nbsp;新增</a></li>
	         <li><a onclick="editData();"><i class="icon-edit"></i>&nbsp;编辑</a></li>
	         <li><a onclick="lookData();"><i class="icon-eye-open"></i>&nbsp;查看</a></li>
	        <li><a onclick="delData();"><i class="icon-remove"></i>&nbsp;删除</a></li>
	        <li><a onclick="giveBack();"><i class="icon-reply"></i>&nbsp;归还</a></li>
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
			<tr jumpURL="${ctx}/borrow/tblBorrowArchives/look?id=${tblBorrowArchives.id}" onmouseover="$(this).addClass('row-color');" onmouseout="$(this).removeClass('row-color');">
				<td>
					<input type="checkbox" value="${tblBorrowArchives.id},${tblBorrowArchives.status}" />
				</td>
				<td>
					<fmt:formatDate value="${tblBorrowArchives.borrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(tblBorrowArchives.consultUnit, 'unit_list', '')}
				</td>
				<td>
					${tblBorrowArchives.tarStr}
				</td>
				<td>
					${tblBorrowArchives.perStr}
				</td>
				<td>
					${tblBorrowArchives.borrowTelPhone}
				</td>
				<td>
					<fmt:formatDate value="${tblBorrowArchives.backDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblBorrowArchives.backOperator}
				</td>
				<td>
					${tblBorrowArchives.operator}
				</td>
				<td>
					${fns:getDictLabel(tblBorrowArchives.status, 'audit_status', '')}
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