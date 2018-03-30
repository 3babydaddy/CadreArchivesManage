<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转入管理人员管理</title>
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
		<li><a href="${ctx}/rollin/tblRollIn/">转入管理人员列表</a></li>
		<li class="active"><a href="${ctx}/rollin/tblRollIn/form?id=${tblRollIn.id}">转入管理人员<shiro:hasPermission name="rollins:tblRollIn:edit">${not empty tblRollIn.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rollins:tblRollIn:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tblRollIn" action="${ctx}/rollin/tblRollIn/save" method="post" class="form-horizontal">
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
			<label class="control-label">转入时间：</label>
			<div class="controls">
				<input name="rollInTime" type="text" readonly="readonly" maxlength="20" style="width:268px;" class="input-medium Wdate "
					value="<fmt:formatDate value="${tblRollIn.rollInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
			<label class="control-label">原存档单位电话：</label>
			<div class="controls">
				<form:input path="beforeUnitTel" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现存档单位：</label>
			<div class="controls">
				<form:input path="saveUnit" htmlEscape="false" maxlength="64" class="input-xlarge "/>
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
								<th>工作单位及职务</th>
								<th>转入原因</th>
								<th>备注信息</th>
								<shiro:hasPermission name="rollins:tblRollIn:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="tblRollInPersonsList">
						</tbody>
						<tfoot>
							<tr><td colspan="12"><a href="javascript:" onclick="addRow('#tblRollInPersonsList', tblRollInPersonsRowIdx, tblRollInPersonsTpl);tblRollInPersonsRowIdx = tblRollInPersonsRowIdx + 1;" class="btns">新增</a></td></tr>
						</tfoot>
					</table>
					<script type="text/template" id="tblRollInPersonsTpl">//<!--
						<tr id="tblRollInPersonsList{{idx}}">
							<td class="hide">
								<input id="tblRollInPersonsList{{idx}}_id" name="tblRollInPersonsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblRollInPersonsList{{idx}}_delFlag" name="tblRollInPersonsList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="tblRollInPersonsList{{idx}}_name" name="tblRollInPersonsList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="tblRollInPersonsList{{idx}}_originalNo" name="tblRollInPersonsList[{{idx}}].originalNo" type="text" value="{{row.originalNo}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<input id="tblRollInPersonsList{{idx}}_viceNo" name="tblRollInPersonsList[{{idx}}].viceNo" type="text" value="{{row.viceNo}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<input id="tblRollInPersonsList{{idx}}_filesNo" name="tblRollInPersonsList[{{idx}}].filesNo" type="text" value="{{row.filesNo}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<input id="tblRollInPersonsList{{idx}}_duty" name="tblRollInPersonsList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="tblRollInPersonsList{{idx}}_reasonContent" name="tblRollInPersonsList[{{idx}}].reasonContent" type="text" value="{{row.reasonContent}}" maxlength="2000" class="input-small "/>
							</td>
							<td>
								<textarea id="tblRollInPersonsList{{idx}}_remarks" name="tblRollInPersonsList[{{idx}}].remarks" rows="2" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#tblRollInPersonsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var tblRollInPersonsRowIdx = 0, tblRollInPersonsTpl = $("#tblRollInPersonsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(tblRollIn.tblRollInPersonsList)};
							for (var i=0; i<data.length; i++){
								addRow('#tblRollInPersonsList', tblRollInPersonsRowIdx, tblRollInPersonsTpl, data[i]);
								tblRollInPersonsRowIdx = tblRollInPersonsRowIdx + 1;
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