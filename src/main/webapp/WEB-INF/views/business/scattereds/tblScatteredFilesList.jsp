<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>零散材料移交人员管理</title>
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
				alertx("请选择记录");
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
			window.location.href = "${ctx}/scattereds/tblScatteredFiles/delete?idStr="+idStr;
		}
		
		function editData(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条记录");
				return;
			}
			var mainId = rows[0].value.slice(0, rows[0].value.indexOf(','));
			window.location.href = "${ctx}/scattereds/tblScatteredFiles/form?id="+mainId;
		}
		
		//审核借阅数据
		function auditData(){
			var idStr = "";
			var status = "";
			var rows = getRowData();
			if(rows.length == 0){
				alertx("请选择记录");
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
		
			top.$.jBox.open("<div style='margin: 12px 0 0 48px;'>"+
					"<span class='jbox-icon jbox-icon-question' style='position: absolute; top: 55px; left: 15px; width: 32px; height: 32px;'></span>"+
								"<span>请确定是否审核通过！！！</span></div>", "提示", 350,  126,
			    { buttons:{"确定":true,"取消":false},
					submit:function(v, h, f){
						if(v){
							//审核通过
							status = "3";
							window.location.href = "${ctx}/scattereds/tblScatteredFiles/auditData?idStr="+idStr+"&status="+status;
						}
					}
			    }
			);
		}
		
		function getRowData(){
			return $("table tbody input[type='checkbox']:checked");
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
		
	</script>
	<style type="text/css">
		.table th, .table td{
			text-align : center;
		}
	</style>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/scattereds/tblScatteredFiles/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<ul class="ul-form">
				<li><label>导入文件：</label>
					<input name="file" class="change" type="file" style="width:220px"/>
				</li>
			</ul>
			<input id="btnImportSubmit" style="margin-top:5px;" class="btn btn-primary" type="submit" value="   导  入   "/>&nbsp;&nbsp;
			<a class="btns" href="<c:url value='/static/templet/scatteredFilesModel.xls'/>">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">零散材料移交人员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblScatteredFiles" action="${ctx}/scattereds/tblScatteredFiles/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>移交时间：</label>
				<input name="startHandOverDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblScatteredFiles.startHandOverDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					至
				<input name="endHandOverDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblScatteredFiles.endHandOverDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>移交单位：</label>
				<sys:treeselect id="handOverUnit" name="handOverUnit" allowClear="true" value="${tblScatteredFiles.handOverUnit}" 
									labelName="handOverUnitName" labelValue="${tblScatteredFiles.handOverUnitName}" title="单位列表" url="/sys/dict/treeDataPop" ></sys:treeselect>
			</li>
			<li><label>移交人：</label>
				<form:input path="handOverStr" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>经手人：</label>
				<form:input path="operator" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><div style="width:243px;"></div></li>
			<li><label>接收人：</label>
				<form:input path="recipient" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<div style="float:right;">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
			</div>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<div id="toolbar">
	    <ul class="nav nav-pills">
	        <li><a <a href="${ctx}/scattereds/tblScatteredFiles/form"><i class="icon-plus"></i>&nbsp;新增</a></li>
	        <li><a onclick="editData();"><i class="icon-edit"></i>&nbsp;编辑</a></li>
	        <li><a onclick="delData();"><i class="icon-remove"></i>&nbsp;删除</a></li>
	        <li><a id="btnImport"><i class="icon-upload-alt"></i>&nbsp;导入</a></li>
	       <%--  <shiro:hasAnyRoles  name="admin,user">
	        	<li><a onclick="censorship();"><i class=" icon-share"></i>&nbsp;送审</a></li>
	        </shiro:hasAnyRoles > --%>
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
				<th>移交单位</th>
				<th>移交日期</th>
				<th>经办人</th>
				<th>接收人</th>
				<th>录入时间</th>
				<th>录入人</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblScatteredFiles">
			<tr>
				<td>
					<input type="checkbox" value="${tblScatteredFiles.id},${tblScatteredFiles.status}" />
				</td>
				<td><a href="${ctx}/scattereds/tblScatteredFiles/personlist?mainId=${tblScatteredFiles.id}">
					${tblScatteredFiles.handOverUnitName}</a>
				</td>
				<td>
					<fmt:formatDate value="${tblScatteredFiles.handOverDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblScatteredFiles.operator}
				</td>
				<td>
					${tblScatteredFiles.recipient}
				</td>
				<td>
					<fmt:formatDate value="${tblScatteredFiles.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblScatteredFiles.createBy.id}
				</td>
				<td>
					${fns:getDictLabel(tblScatteredFiles.status, 'audit_status', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>