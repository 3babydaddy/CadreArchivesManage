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
					bottomText:"仅允许导入“xls”或“xlsx”格式文件且不能超过5M！"});
			});
			
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
				if(idStr == ""){
					idStr = rows[i].value; 
				}else{
					idStr += "," + rows[i].value; 
				}
			}
			var url = "${ctx}/suphandle/tblSuperviseHandle/delete?idStr="+idStr;
			confirmx('确定要删除选择的数据！！！', url);
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
			max-width: 310px;
			min-width: 60px;
		}
		.circle {
			height: 25px;
			width: 65px;
			text-align: center;
			border-radius: 20%;
		}
		.ul-form li label{
			width: 155px !important;
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
			overflow:hidden;
		}
		input[readonly]{
			background-color: white;
		}
	</style>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/suphandle/tblSuperviseHandle/import" method="post" enctype="multipart/form-data"
			class="form-search" style="text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<ul class="ul-form">
				<li><label>导入文件：</label>
					<input id="uploadFile" name="file" type="file" style="width:180px;margin-bottom:15px;""/>
				</li>
			</ul>
			<input id="btnImportSubmit" class="btn btn-primary" style="margin-left:-30px;width:75px;" type="submit" value="   导    入   "/>&nbsp;&nbsp;
			<button class="btn btn-primary" style="font-size:12px;"><a href="${ctx}/suphandle/tblSuperviseHandle/moduleDown" style="color:white;">下载模板</a></button>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">督查督办列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblSuperviseHandle" action="${ctx}/suphandle/tblSuperviseHandle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>出生开始日期：</label>
				<input name="startBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblSuperviseHandle.startBirthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>出生截止日期：</label>
				<input name="endBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblSuperviseHandle.endBirthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>提档开始时间：</label>
				<input name="startRaisedTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblSuperviseHandle.startRaisedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>提档截止时间：</label>
				<input name="endRaisedTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblSuperviseHandle.endRaisedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>单位及职务：</label>
				<form:input path="unitDuty" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium" style="width:220px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('supervise_handle_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<div style="float:right;margin-right:4px;">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<li class="btns"><input id="btnCancel" class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
			</div>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<div id="toolbar">
	    <ul class="nav nav-pills">
	          <li><a id="btnImport"><i class="icon-upload-alt"></i>&nbsp;导入提拔干部数据</a></li>
	         <li><a onclick="delData();"><i class="icon-remove"></i>&nbsp;删除</a></li>
	    </ul>
	</div>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input id="selected" type="checkbox" /></th>
				<th>姓名</th>
				<th>出生日期</th>
				<th>现单位及职务</th>
				<th>原单位及职务</th>
				<th>应提交档案时间</th>
				<th>倒计时(天)</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblSuperviseHandle">
			<tr onmouseover="$(this).addClass('row-color');" onmouseout="$(this).removeClass('row-color');">
				<td>
					<input type="checkbox" value="${tblSuperviseHandle.id}" />
				</td>
				<td>
					${tblSuperviseHandle.name}
				</td>
				<td>
					<fmt:formatDate value="${tblSuperviseHandle.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${tblSuperviseHandle.unitDuty}
				</td>
				<td>
					${tblSuperviseHandle.beforeUnitDuty}
				</td>
				<td>
					<fmt:formatDate value="${tblSuperviseHandle.raisedTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:if test="${tblSuperviseHandle.status eq '1'}">
						${tblSuperviseHandle.countDown}
					</c:if>
				</td>
				<td>
					${fns:getDictLabel(tblSuperviseHandle.status, 'supervise_handle_status', '')}
				</td>
				<td>
					<c:if test="${tblSuperviseHandle.countDown < 0 || tblSuperviseHandle.status eq '3'}">
						<label class="circle" style="background: #e20001;"><a href="#" style="color:white;font-size:18px;">督办</a></label>
					</c:if>
					<c:if test="${tblSuperviseHandle.countDown >= 0 && tblSuperviseHandle.status eq '1'}">
						<label class="circle" style="background: green;">
							<a href="${ctx}/suphandle/tblSuperviseHandle/updateStatus?id=${tblSuperviseHandle.id}" onclick="return confirmx('该条数据确认要上交吗？', this.href)" style="color:white;font-size:18px;">上交</a>
						</label>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>