<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借阅管理管理</title>
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
		<li><a href="${ctx}/borrow/tblBorrowArchives/">借阅管理列表</a></li>
		<li class="active"><a href="${ctx}/borrow/tblBorrowArchives/form?id=${tblBorrowArchives.id}">借阅管理<shiro:hasPermission name="borrow:tblBorrowArchives:edit">${not empty tblBorrowArchives.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="borrow:tblBorrowArchives:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tblBorrowArchives" action="${ctx}/borrow/tblBorrowArchives/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">借阅日期：</label>
			<div class="controls">
				<input name="borrowDate" type="text" readonly="readonly" style="width:268px;" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tblBorrowArchives.borrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借阅单位：</label>
			<div class="controls">
				<sys:treeselect2 url="/sys/dict/treeDataPop" id="consultUnit" name="consultUnit" allowClear="true" value="${tblBorrowArchives.consultUnit}" 
									labelName="consultUnitName" labelValue="${tblBorrowArchives.consultUnitName}" title="单位列表"></sys:treeselect2>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借阅事由：</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" rows="4" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查档内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借阅审批附件：</label>
			<div class="controls">
				<sys:upFIle input="approveAttachment"  type="files"  name="approveAttachment"  value="${tblBorrowArchives.approveAttachment}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="上传"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">借阅对象：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>姓名</th>
								<th>单位</th>
								<th>职务</th>
								<th>政治面貌</th>
								<shiro:hasPermission name="borrow:tblBorrowArchives:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="tblBorrowTargetList">
						</tbody>
						<tfoot>
							<tr><td colspan="9"><a href="javascript:" onclick="addRow('#tblBorrowTargetList', tblBorrowTargetRowIdx, tblBorrowTargetTpl);tblBorrowTargetRowIdx = tblBorrowTargetRowIdx + 1;" class="btns">新增</a></td></tr>
						</tfoot>
					</table>
					<script type="text/template" id="tblBorrowTargetTpl">//<!--
						<tr id="tblBorrowTargetList{{idx}}">
							<td class="hide">
								<input id="tblBorrowTargetList{{idx}}_id" name="tblBorrowTargetList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblBorrowTargetList{{idx}}_delFlag" name="tblBorrowTargetList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="tblBorrowTargetList{{idx}}_name" name="tblBorrowTargetList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<sys:treeselect url="/sys/dict/treeDataPop" id="tblBorrowTargetList{{idx}}_unit" name="tblBorrowTargetList[{{idx}}].unit" allowClear="true" value="{{row.unit}}" 
									labelName="unitName" labelValue="{{row.unitName}}" title="单位列表"></sys:treeselect>
							</td>
							<td>
								<input id="tblBorrowTargetList{{idx}}_duty" name="tblBorrowTargetList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="tblBorrowTargetList{{idx}}_politicalStatus" name="tblBorrowTargetList[{{idx}}].politicalStatus" type="text" value="{{row.politicalStatus}}" maxlength="32" class="input-small "/>
							</td>
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#tblBorrowTargetList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var tblBorrowTargetRowIdx = 0, tblBorrowTargetTpl = $("#tblBorrowTargetTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(tblBorrowArchives.tblBorrowTargetList)};
							for (var i=0; i<data.length; i++){
								addRow('#tblBorrowTargetList', tblBorrowTargetRowIdx, tblBorrowTargetTpl, data[i]);
								tblBorrowTargetRowIdx = tblBorrowTargetRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">借阅人员：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>姓名</th>
								<th>照片</th>
								<th>单位</th>
								<th>职务</th>
								<th>政治面貌</th>
								<th>联系方式</th>
								<th>签名</th>
								<shiro:hasPermission name="borrow:tblBorrowArchives:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="tblBorrowPersonList">
						</tbody>
						<tfoot>
							<tr><td colspan="10"><a href="javascript:" onclick="addRow('#tblBorrowPersonList', tblBorrowPersonRowIdx, tblBorrowPersonTpl);tblBorrowPersonRowIdx = tblBorrowPersonRowIdx + 1;" class="btns">新增</a></td></tr>
						</tfoot>
					</table>
					<script type="text/template" id="tblBorrowPersonTpl">//<!--
						<tr id="tblBorrowPersonList{{idx}}">
							<td class="hide">
								<input id="tblBorrowPersonList{{idx}}_id" name="tblBorrowPersonList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblBorrowPersonList{{idx}}_delFlag" name="tblBorrowPersonList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="tblBorrowPersonList{{idx}}_name" name="tblBorrowPersonList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<sys:upFIle input="tblBorrowPersonList{{idx}}_photo"  type="files"  name="tblBorrowPersonList[{{idx}}].photo"  value="{{row.photo}}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="上传"/>
							</td>
							<td>
								<sys:treeselect url="/sys/dict/treeDataPop" id="tblBorrowPersonList{{idx}}_unit" name="tblBorrowPersonList[{{idx}}].unit" allowClear="true" value="{{row.unit}}" 
									labelName="unitName" labelValue="{{row.unitName}}" title="单位列表"></sys:treeselect>
							</td>
							<td>
								<input id="tblBorrowPersonList{{idx}}_duty" name="tblBorrowPersonList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="tblBorrowPersonList{{idx}}_politicalStatus" name="tblBorrowPersonList[{{idx}}].politicalStatus" type="text" value="{{row.politicalStatus}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="tblBorrowPersonList{{idx}}_telphone" name="tblBorrowPersonList[{{idx}}].telphone" type="text" value="{{row.telphone}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="tblBorrowPersonList{{idx}}_siginName" name="tblBorrowPersonList[{{idx}}].siginName" type="text" value="{{row.siginName}}" maxlength="64" class="input-small "/>
							</td>
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#tblBorrowPersonList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var tblBorrowPersonRowIdx = 0, tblBorrowPersonTpl = $("#tblBorrowPersonTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(tblBorrowArchives.tblBorrowPersonList)};
							for (var i=0; i<data.length; i++){
								addRow('#tblBorrowPersonList', tblBorrowPersonRowIdx, tblBorrowPersonTpl, data[i]);
								tblBorrowPersonRowIdx = tblBorrowPersonRowIdx + 1;
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