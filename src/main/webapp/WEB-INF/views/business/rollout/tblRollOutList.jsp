<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转出管理人员管理</title>
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
			window.location.href = "${ctx}/rollout/tblRollOut/delete?idStr="+idStr;
		}
		
		function editData(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条记录");
				return;
			}
			window.location.href = "${ctx}/rollout/tblRollOut/form?id="+rows[0].value;
		}
		
		//转递单
		function relayBill(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条记录");
				return;
			}
			window.location.href = "${ctx}/order/createRelayBill?rollOutId="+rows[0].value;
		}
		
		//回执单导入
		function receiptImport(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条记录");
				return;
			}
			$("#rollOutId").val(rows[0].value);
			$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
				bottomText:"仅允许导入“xls”或“xlsx”格式文件且不能超过5M！"});
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
		<form id="importForm" action="${ctx}/rollout/tblRollOutBack/receiptSave" method="post" 
			class="form-horizontal" style="align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
				<div class="control-group">
					<label class="control-label" style="margin-top:5px;">回执单附件：</label>
					<div class="controls">
						<sys:upFIle input="rollApproveAttachment"  type="files" 
							 name="rollApproveAttachment"  value=""  uploadPath="/file" 
									selectMultiple="false" maxWidth="100" maxHeight="100" text="上传文件"/>
					</div>
				</div>
			<input type="hidden" id="rollOutId" name="rollOutId" />
			<input id="btnImportSubmit" style="margin:0px 0 5px 180px;" class="btn btn-primary" type="submit" value="   导  入   "/>&nbsp;&nbsp;
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/rollout/tblRollOut/">转出管理人员列表</a></li>
		<shiro:hasPermission name="rollouts:tblRollOut:edit"><li><a href="${ctx}/rollout/tblRollOut/form">转出管理人员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tblRollOut" action="${ctx}/rollout/tblRollOut/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>转出时间：</label>
				<input name="startRollOutTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblRollOut.startRollOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					至
				<input name="endRollOutTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblRollOut.endRollOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label style="width:85px;">现存档单位：</label>
				<sys:treeselect id="saveUnit" name="saveUnit" allowClear="true" value="${tblRollOut.saveUnit}" 
									labelName="saveUnitName" labelValue="${tblRollOut.saveUnitName}" title="单位列表" url="/sys/dict/treeDataPop" ></sys:treeselect>
			</li>
			<li><label>接收人：</label>
				<form:input path="recipient" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>批次号：</label>
				<form:input path="character" htmlEscape="false" style="width:82px;" maxlength="11" />字
				<form:input path="number" htmlEscape="false" style="width:82px;" maxlength="11" />号
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
	        <li><a <a href="${ctx}/rollout/tblRollOut/form"><i class="icon-plus"></i>&nbsp;新增</a></li>
	         <li><a onclick="editData();"><i class="icon-edit"></i>&nbsp;编辑</a></li>
	        <li><a onclick="delData();"><i class="icon-remove"></i>&nbsp;删除</a></li>
	        <li><a onclick="relayBill()"><i class="icon-share-alt"></i>&nbsp;转递单</a></li>
	        <li><a onclick="receiptImport();"><i class="icon-upload-alt"></i>&nbsp;回执单导入</a></li>
	    </ul>
	</div>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input id="selected" type="checkbox" /></th>
				<th>批次</th>
				<th>转出时间</th>
				<th>现存档单位</th>
				<th>接收人</th>
				<th>录入时间</th>
				<th>录入人</th>
				<shiro:hasPermission name="rollouts:tblRollOut:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblRollOut">
			<tr>
				<td>
					<input type="checkbox" value="${tblRollOut.id}" />
				</td>
				<td><a href="${ctx}/rollout/tblRollOut/personlist?mainId=${tblRollOut.id}&batchNum=${tblRollOut.character}zi${tblRollOut.number}hao">
					${tblRollOut.character}字${tblRollOut.number}号</a></a>
				</td>
				<td>
					<fmt:formatDate value="${tblRollOut.rollOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblRollOut.saveUnitName}
				</td>
				<td>
					${tblRollOut.recipient}
				</td>
				<td>
					<fmt:formatDate value="${tblRollOut.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblRollOut.createBy.id}
				</td>
				<shiro:hasPermission name="rollouts:tblRollOut:edit"><td>
    				<a href="${ctx}/rollout/tblRollOut/form?id=${tblRollOut.id}">修改</a>
					<a href="${ctx}/rollout/tblRollOut/delete?id=${tblRollOut.id}" onclick="return confirmx('确认要删除该转出管理人员吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>