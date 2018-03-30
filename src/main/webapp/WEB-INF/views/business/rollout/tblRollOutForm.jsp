<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转出管理人员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/rollout/tblRollOut/">转出管理人员列表</a></li>
		<li class="active"><a href="${ctx}/rollout/tblRollOut/form?id=${tblRollOut.id}">转出管理人员<shiro:hasPermission name="rollouts:tblRollOut:edit">${not empty tblRollOut.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rollouts:tblRollOut:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tblRollOut" action="${ctx}/rollout/tblRollOut/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">批次：</label>
			<div class="controls">
				<div  style="float:left;">
					<form:input path="character" htmlEscape="false" style="width:105px;" maxlength="11" />字
				</div>
				
				<div  style="float:left;">
					<form:input path="number" htmlEscape="false" style="width:105px;margin-left:10px;" maxlength="11" />号
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转出时间：</label>
			<div class="controls">
				<input name="rollOutTime" type="text" readonly="readonly" style="width:268px;" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tblRollOut.rollOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转出回执：</label>
			<div class="controls">
				<form:radiobuttons path="isReturn" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经办人：</label>
			<div class="controls">
				<form:input path="operator" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收人：</label>
			<div class="controls">
				<form:input path="recipient" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原存档单位：</label>
			<div class="controls">
				<form:input path="beforeUnit" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现存档单位：</label>
			<div class="controls">
				<form:input path="saveUnit" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收单位电话：</label>
			<div class="controls">
				<form:input path="receiveUnitTel" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		
			<div class="control-group">
				<label class="control-label">相关信息：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>姓名</th>
								<th>正本（卷）</th>
								<th>副本（卷）</th>
								<th>档案材料（份）</th>
								<th>单位及职务</th>
								<th>转出形式</th>
								<th>转出事由</th>
								<th>转出原因</th>
								<th>审批附件</th>
								<th>备注信息</th>
								<shiro:hasPermission name="rollouts:tblRollOut:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="tblRollOutPersonsList">
						</tbody>
						<tfoot>
							<tr><td colspan="14"><a href="javascript:" onclick="addRow('#tblRollOutPersonsList', tblRollOutPersonsRowIdx, tblRollOutPersonsTpl);tblRollOutPersonsRowIdx = tblRollOutPersonsRowIdx + 1;" class="btns">新增</a></td></tr>
						</tfoot>
					</table>
					<script type="text/template" id="tblRollOutPersonsTpl">//<!--
						<tr id="tblRollOutPersonsList{{idx}}">
							<td class="hide">
								<input id="tblRollOutPersonsList{{idx}}_id" name="tblRollOutPersonsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblRollOutPersonsList{{idx}}_delFlag" name="tblRollOutPersonsList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="tblRollOutPersonsList{{idx}}_name" name="tblRollOutPersonsList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="tblRollOutPersonsList{{idx}}_originalNo" name="tblRollOutPersonsList[{{idx}}].originalNo" type="text" value="{{row.originalNo}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<input id="tblRollOutPersonsList{{idx}}_viceNo" name="tblRollOutPersonsList[{{idx}}].viceNo" type="text" value="{{row.viceNo}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<input id="tblRollOutPersonsList{{idx}}_filesNo" name="tblRollOutPersonsList[{{idx}}].filesNo" type="text" value="{{row.filesNo}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<input id="tblRollOutPersonsList{{idx}}_duty" name="tblRollOutPersonsList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<c:forEach items="${fns:getDictList('roll_out_type')}" var="dict" varStatus="dictStatus">
									<span><input id="tblRollOutPersonsList{{idx}}_outType${dictStatus.index}" name="tblRollOutPersonsList[{{idx}}].outType" type="radio" value="${dict.value}" data-value="{{row.outType}}"><label for="tblRollOutPersonsList{{idx}}_outType${dictStatus.index}">${dict.label}</label></span>
								</c:forEach>
							</td>
							<td>
								<c:forEach items="${fns:getDictList('roll_out_reason')}" var="dict" varStatus="dictStatus">
									<span><input id="tblRollOutPersonsList{{idx}}_reason${dictStatus.index}" name="tblRollOutPersonsList[{{idx}}].reason" type="radio" value="${dict.value}" data-value="{{row.reason}}"><label for="tblRollOutPersonsList{{idx}}_reason${dictStatus.index}">${dict.label}</label></span>
								</c:forEach>
							</td>
							<td>
								<input id="tblRollOutPersonsList{{idx}}_reasonContent" name="tblRollOutPersonsList[{{idx}}].reasonContent" type="text" value="{{row.reasonContent}}" maxlength="2000" class="input-small "/>
							</td>
							<td>
								<input id="tblRollOutPersonsList{{idx}}_approveAttachment" name="tblRollOutPersonsList[{{idx}}].approveAttachment" type="hidden" value="{{row.approveAttachment}}" maxlength="64"/>
								<sys:ckfinder input="tblRollOutPersonsList{{idx}}_approveAttachment" type="files" uploadPath="/rollouts/tblRollOut" selectMultiple="true"/>
							</td>
							<td>
								<textarea id="tblRollOutPersonsList{{idx}}_remarks" name="tblRollOutPersonsList[{{idx}}].remarks" rows="2" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#tblRollOutPersonsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var tblRollOutPersonsRowIdx = 0, tblRollOutPersonsTpl = $("#tblRollOutPersonsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(tblRollOut.tblRollOutPersonsList)};
							for (var i=0; i<data.length; i++){
								addRow('#tblRollOutPersonsList', tblRollOutPersonsRowIdx, tblRollOutPersonsTpl, data[i]);
								tblRollOutPersonsRowIdx = tblRollOutPersonsRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>