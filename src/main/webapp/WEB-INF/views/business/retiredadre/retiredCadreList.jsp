<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退休干部信息操作管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#reset").click(function(){
				
				$("input[type='text']").val("");
				$("select").val("");
				
			});
			
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
			window.location.href = "${ctx}/retiredadre/retiredCadre/delete?idStr="+idStr;
		}
		
		function editData(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条记录");
				return;
			}
			window.location.href = "${ctx}/retiredadre/retiredCadre/form?id="+rows[0].value;
		}
		
		function getRowData(){
			return $("input[type='checkbox']:checked");
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
		<li class="active"><a href="${ctx}/retiredadre/retiredCadre/">退休干部信息操作列表</a></li>
		<shiro:hasPermission name="retiredadre:retiredCadre:edit"><li><a href="${ctx}/retiredadre/retiredCadre/form">退休干部信息操作添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="retiredCadre" action="${ctx}/retiredadre/retiredCadre/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>编号：</label>
				<form:input path="sort" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium" style="width:220px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('retired_cadre_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>出生日期：</label>
				<input name="startBir" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${retiredCadre.startBir}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="endBir" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${retiredCadre.endBir}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input class="btn btn-primary" id="reset" type="button" value="重置"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<div id="toolbar">
	    <ul class="nav nav-pills">
	        <li><a <a href="${ctx}/retiredadre/retiredCadre/form"><i class="icon-plus"></i>&nbsp;新增</a></li>
	         <li><a onclick="editData();"><i class="icon-edit"></i>&nbsp;编辑</a></li>
	        <li><a onclick="delData();"><i class="icon-remove"></i>&nbsp;删除</a></li>
	        <li><a onclick=""><i class="icon-share-alt"></i>&nbsp;转死亡</a></li>
	        <li><a onclick=""><i class="icon-share-alt"></i>&nbsp;转档案局</a></li>
	        <li><a onclick=""><i class="icon-remove-circle"></i>&nbsp;撤销</a></li>
	    </ul>
	</div>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>编号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>出生日期  </th>
				<th>退休工作单位</th>
				<th>状态</th>
				<th>转死亡时间</th>
				<th>转档案局时间</th>
				<!-- <shiro:hasPermission name="retiredadre:retiredCadre:edit"><th>操作</th></shiro:hasPermission>  -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="retiredCadre">
			<tr>
				<td>
					<input type="checkbox" value="${retiredCadre.id}" />
				</td>
				<td><a href="${ctx}/retiredadre/retiredCadre/form?id=${retiredCadre.id}"></a>
					${retiredCadre.sort}
				</td>
				<td>
					${retiredCadre.name}
				</td>
				<td>
					${fns:getDictLabel(retiredCadre.sex, 'sex', '')}
				</td>
				<td>
					<fmt:formatDate value="${retiredCadre.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${retiredCadre.workUnit}
				</td>
				<td>
					${fns:getDictLabel(retiredCadre.status, 'retired_cadre_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${retiredCadre.diedTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${retiredCadre.recordOfficeTime}" pattern="yyyy-MM-dd"/>
				</td>
				<!-- <shiro:hasPermission name="retiredadre:retiredCadre:edit"></shiro:hasPermission> -->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>