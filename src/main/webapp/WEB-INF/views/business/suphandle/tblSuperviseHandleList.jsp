<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>督查督办管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
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
			window.location.href = "${ctx}/suphandle/tblSuperviseHandle/delete?idStr="+idStr;
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
		
		function importDate(){
			 $('#importForm').submit();  
		}
	</script>
	<style type="text/css">
		.table th, .table td{
			text-align : center;
		}
	</style>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/suphandle/tblSuperviseHandle/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>&nbsp;&nbsp;
			<a href="${ctx}/suphandle/tblSuperviseHandle/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">督查督办列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblSuperviseHandle" action="${ctx}/suphandle/tblSuperviseHandle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>出生日期：</label>
				<input name="startBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblSuperviseHandle.startBirthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					至
				<input name="endBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblSuperviseHandle.endBirthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label style="width:85px;">姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>提档时间：</label>
				<input name="startRaisedTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblSuperviseHandle.startRaisedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					至
				<input name="endRaisedTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblSuperviseHandle.endRaisedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>性别：</label>
				<form:input path="sex" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label style="width:85px;">单位及职务：</label>
				<form:input path="unitDuty" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>业务状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<div id="toolbar">
	    <ul class="nav nav-pills">
	          <li><a id="btnImport"><i class=" icon-upload"></i>&nbsp;导入提拔干部数据</a></li>
	         <li><a onclick="delData();"><i class="icon-remove"></i>&nbsp;删除</a></li>
	    </ul>
	</div>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<td>选择</td>
				<th>姓名</th>
				<th>性别</th>
				<th>出生日期</th>
				<th>单位及职务</th>
				<th>应提交档案时间</th>
				<th>业务状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblSuperviseHandle">
			<tr>
				<td>
					<input type="checkbox" value="${tblSuperviseHandle.id}" />
				</td>
				<td>
					${tblSuperviseHandle.name}
				</td>
				<td>
					${tblSuperviseHandle.sex}
				</td>
				<td>
					<fmt:formatDate value="${tblSuperviseHandle.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${tblSuperviseHandle.unitDuty}
				</td>
				<td>
					<fmt:formatDate value="${tblSuperviseHandle.raisedTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(tblSuperviseHandle.status, 'supervise_handle_status', '')}
				</td>
				<td>
    				<a href="#">督办</a>
					<a href="#" onclick="return confirmx('确认要删除该督查督办吗？', this.href)">上交</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>