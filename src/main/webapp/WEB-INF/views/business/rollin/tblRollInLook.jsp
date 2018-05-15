<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转入管理人员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/rollin/tblRollIn/">转入管理人员列表</a></li>
		<li class="active"><a href="#">转入管理人员查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tblRollIn" action="${ctx}/rollin/tblRollIn/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">批次：</label>
			<div class="controls">
				<div  style="float:left;">
					<form:input path="character" htmlEscape="false" readonly="true" style="width:105px;" maxlength="11" />字
				</div>
				
				<div  style="float:left;">
					<form:input path="number" htmlEscape="false" readonly="true" style="width:105px;margin-left:10px;" maxlength="11" />号
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转入时间：</label>
			<div class="controls">
				<input name="rollInTime" type="text" readonly="readonly" maxlength="20" style="width:268px;" class="input-medium Wdate "
					value="<fmt:formatDate value="${tblRollIn.rollInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
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
		<div class="control-group">
			<label class="control-label">原存档单位：</label>
			<div class="controls">
				<form:input path="beforeUnitName" htmlEscape="false" readonly="true" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原存档单位电话：</label>
			<div class="controls">
				<form:input path="beforeUnitTel" htmlEscape="false" readonly="true" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现存档单位：</label>
			<div class="controls">
				<form:input path="saveUnitName"  htmlEscape="false" readonly="true" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转递单附件：</label>
			<div class="controls">
				<c:if test="${not empty tblRollIn.rollApproveAttachment}">
					<sys:upImgMul input="rollApproveAttachment"  type="files"  name="rollApproveAttachment"  value="${tblRollIn.rollApproveAttachment}"  uploadPath="/file" readonly="true" selectMultiple="false" maxWidth="100" maxHeight="100" text="上传"/>
				</c:if>
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
						<tr><td colspan="12"></td></tr>
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
								<input id="tblRollInPersonsList{{idx}}_name" name="tblRollInPersonsList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
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
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>