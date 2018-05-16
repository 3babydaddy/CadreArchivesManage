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
			
			$("#btnSubmit").click(function(){
				setTimeout(function(){ 
					var errorArry = $("label.error");
					for(var i = 0; i < errorArry.length; i++){
						$("label.error")[i].innerHTML="必填信息";
					}
				}, 100);	
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
				$(obj).parent().parent().next().next().next().next().next().remove();
				$(obj).parent().parent().next().next().next().next().remove();
				$(obj).parent().parent().next().next().next().remove();
				$(obj).parent().parent().next().next().remove();
				$(obj).parent().parent().next().remove();
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				//$(obj).parent().parent().addClass("error");
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
		<li class="active"><a href="#">转入管理人员<c:if test="${empty tblRollIn.id }">新增</c:if><c:if test="${not empty tblRollIn.id }">编辑</c:if></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tblRollIn" action="${ctx}/rollin/tblRollIn/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">批次：</label>
			<div class="controls">
				<div  style="float:left;">
					<form:input path="character" htmlEscape="false" style="width:105px;" maxlength="11" class="required" />字
				</div>
				
				<div  style="float:left;">
					<form:input path="number" htmlEscape="false" style="width:105px;margin-left:10px;" maxlength="11" class="required" />号
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转入时间：</label>
			<div class="controls">
				<input name="rollInTime" type="text" readonly="readonly" maxlength="20" style="width:268px;" class="input-medium Wdate required"
					value="<fmt:formatDate value="${tblRollIn.rollInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经办人：</label>
			<div class="controls">
				<form:input path="operator" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收人：</label>
			<div class="controls">
				<form:input path="recipient" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原存档单位：</label>
			<div class="controls">
				<sys:treeselect id="beforeUnit" name="beforeUnit" allowClear="true" value="${tblRollIn.beforeUnit}"  cssStyle="width:240px;"
									labelName="beforeUnitName" labelValue="${tblRollIn.beforeUnitName}" title="单位列表" url="/sys/dict/treeDataPop" cssClass="required"></sys:treeselect>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原存档单位电话：</label>
			<div class="controls">
				<form:input path="beforeUnitTel" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现存档单位：</label>
			<div class="controls">
				<sys:treeselect id="saveUnit" name="saveUnit" allowClear="true" value="${tblRollIn.saveUnit}" cssStyle="width:240px;"
									labelName="saveUnitName" labelValue="${tblRollIn.saveUnitName}" title="单位列表" url="/sys/dict/treeDataPop" cssClass="required"></sys:treeselect>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转递单附件：</label>
			<div class="controls">
				<sys:upImgMul input="rollApproveAttachment"  type="files"  name="rollApproveAttachment"  value="${tblRollIn.rollApproveAttachment}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="上传"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">相关信息：</label>
			<div class="controls">
				<table id="contentTable"  style="width:60%;" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th class="hide"></th>
							
						</tr>
					</thead>
					<tbody id="tblRollInPersonsList">
					</tbody>
					<tfoot>
						<tr><td colspan="12"><a href="javascript:" onclick="addRow('#tblRollInPersonsList', tblRollInPersonsRowIdx, tblRollInPersonsTpl);tblRollInPersonsRowIdx = tblRollInPersonsRowIdx + 1;" style="width:50px;" class="btn btn-primary"><i class="icon-plus"></i>新增</a></td></tr>
					</tfoot>
				</table>
				<script type="text/template" id="tblRollInPersonsTpl">
					<tr>
						<tr id="tblRollInPersonsList{{idx}}">
							<td class="hide">
								<input id="tblRollInPersonsList{{idx}}_id" name="tblRollInPersonsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblRollInPersonsList{{idx}}_delFlag" name="tblRollInPersonsList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td style="text-align:right;width:120px;"><label>姓名：</label></td>
							<td>
								<input id="tblRollInPersonsList{{idx}}_name" name="tblRollInPersonsList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small required"/>
							</td>
							<td rowspan="6" class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#tblRollInPersonsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
						</tr><tr>
							<td colspan="2"><label>正本：</label> 	
								<input id="tblRollInPersonsList{{idx}}_originalNo" style="width:95px;" name="tblRollInPersonsList[{{idx}}].originalNo" type="text" value="{{row.originalNo}}" maxlength="11" class="input-small  digits"/>
								<label>卷&nbsp;</label>
								<label>副本：</label>
								<input id="tblRollInPersonsList{{idx}}_viceNo" style="width:95px;" name="tblRollInPersonsList[{{idx}}].viceNo" type="text" value="{{row.viceNo}}" maxlength="11" class="input-small  digits"/>
								<label>卷&nbsp;</label>
								<label>档案材料：</label>
								<input id="tblRollInPersonsList{{idx}}_filesNo" style="width:95px;" name="tblRollInPersonsList[{{idx}}].filesNo" type="text" value="{{row.filesNo}}" maxlength="11" class="input-small  digits"/>
								<label>卷</label>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>工作单位及职务：</label></td>
							<td>
								<input id="tblRollInPersonsList{{idx}}_duty" name="tblRollInPersonsList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="200" class="input-xlarge "/>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>转入原因：</label></td>
							<td >
								<input id="tblRollInPersonsList{{idx}}_reasonContent" name="tblRollInPersonsList[{{idx}}].reasonContent" type="text" value="{{row.reasonContent}}" maxlength="2000" class="input-xlarge "/>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>备注：</label></td>
							<td>
								<textarea id="tblRollInPersonsList{{idx}}_remarks" name="tblRollInPersonsList[{{idx}}].remarks" rows="2" maxlength="255" class="input-xlarge ">{{row.remarks}}</textarea>
							</td>
						</tr><tr style="height:25px;"><td colspan="5"></td></tr>
					</tr>
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
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>