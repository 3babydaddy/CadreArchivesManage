<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>零散材料移交人员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		
		window.onload = function(){
			$(document.body).find("input[id!='btnCancel']").each(function(){
				$(this).attr('disabled', 'disabled');
			});
		}
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
	<style type="text/css">
		.table th, .table td, .table input{
			text-align : center;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/scattereds/tblScatteredFiles/">零散材料移交信息列表</a></li>
		<li class="active"><a href="#">零散材料移交信息查看</a></li>
	</ul><br/>
	<sys:message content="${message}"/>		
	<form:form id="inputForm" modelAttribute="tblScatteredFiles" action="${ctx}/scattereds/tblScatteredFiles/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div class="control-group">
			<label class="control-label">移交单位：</label>
			<div class="controls">
				<form:input path="handOverUnitName" htmlEscape="false" readonly="true" maxlength="64" class="input-xlarge "/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">移交时间：</label>
			<div class="controls">
				<input name="handOverDate" type="text" readonly="readonly" style="width:268px;" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tblScatteredFiles.handOverDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
			</div>
		</div>
		
			<div class="control-group">
				<label class="control-label">移交材料：</label>
				<div class="controls">
					<table id="contentTable" style="width:70.5%;" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>姓名</th>
								<th>职务</th>
								<th>材料名称</th>
								<th>正本（卷）</th>
								<shiro:hasPermission name="scattereds:tblScatteredFiles:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="tblHandOverFilesList">
						</tbody>
						<tfoot>
							<tr><td colspan="9"></td></tr>
						</tfoot>
					</table>
					<script type="text/template" id="tblHandOverFilesTpl">//<!--
						<tr id="tblHandOverFilesList{{idx}}">
							<td class="hide">
								<input id="tblHandOverFilesList{{idx}}_id" name="tblHandOverFilesList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblHandOverFilesList{{idx}}_delFlag" name="tblHandOverFilesList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="tblHandOverFilesList{{idx}}_name" name="tblHandOverFilesList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="tblHandOverFilesList{{idx}}_duty" name="tblHandOverFilesList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="tblHandOverFilesList{{idx}}_filesNames" name="tblHandOverFilesList[{{idx}}].filesNames" type="text" value="{{row.filesNames}}" maxlength="2000" class="input-small "/>
							</td>
							<td>
								<input id="tblHandOverFilesList{{idx}}_originalNo" name="tblHandOverFilesList[{{idx}}].originalNo" type="text" value="{{row.originalNo}}" maxlength="11" class="input-small  digits"/>
							</td>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var tblHandOverFilesRowIdx = 0, tblHandOverFilesTpl = $("#tblHandOverFilesTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(tblScatteredFiles.tblHandOverFilesList)};
							for (var i=0; i<data.length; i++){
								addRow('#tblHandOverFilesList', tblHandOverFilesRowIdx, tblHandOverFilesTpl, data[i]);
								tblHandOverFilesRowIdx = tblHandOverFilesRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		
		<div class="control-group">
			<label class="control-label">经办人：</label>
			<div class="controls">
				<form:input path="operator" htmlEscape="false" readonly="true" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
				<label class="control-label">接收人：</label>
				<div class="controls">
					<form:input path="recipient" htmlEscape="false" readonly="true" maxlength="64" class="input-xlarge "/>
				</div>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>