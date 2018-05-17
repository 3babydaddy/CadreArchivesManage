<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查阅档案管理</title>
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
			
			//绘制签名数据填充
			if(row != undefined && row.siginName != undefined && row.siginName != ""){
				document.getElementById("tblCheckPersonList"+idx+"_siginDiv").style.display='';
	    		document.getElementById("tblCheckPersonList"+idx+"_siginInput").style.display='none';
			}
		}
	</script>
	<style type="text/css">
		.td-order-one{
			margin-left: 10px;
			margin-right: 10px;
			float: left;
			white-space: nowrap;
		}
		
		.td-order-one img{
			max-width: 120px;
			max-heigth: 30px;
			border-radius: 2px;
			margin-top: -6px;
			-webkit-border-radius:2px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/consult/tblConsultArchives/">查阅档案列表</a></li>
		<li class="active"><a href="#">查阅档案查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tblConsultArchives" action="${ctx}/consult/tblConsultArchives/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">查阅日期：</label>
			<div class="controls">
				<input name="borrowDate" type="text" readonly="readonly" style="width:268px;" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tblConsultArchives.borrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查阅单位：</label>
			<div class="controls">
				<form:input path="consultUnitName" htmlEscape="false" value="${tblConsultArchives.consultUnitName}" maxlength="64" readonly="true" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查档对象：</label>
			<div class="controls">
				<table id="contentTable" style="width:60%;" class="table table-striped table-bordered table-condensed">
					<colgroup>
				 		<col width="15%"/>
				 		<col width="48%"/>
				 		<col width="20%"/>
				 		<col width="15%"/>
				 		<col width="5%"/>
				 	</colgroup>
					<tbody id="tblCheckedTargetList">
					</tbody>
					<tfoot>
						<tr><td colspan="9"></td></tr>
					</tfoot>
				</table>
				<script type="text/template" id="tblCheckedTargetTpl">//<!--
					<tr>	
						<tr id="tblCheckedTargetList{{idx}}">
							<td class="hide">
								<input id="tblCheckedTargetList{{idx}}_id" name="tblCheckedTargetList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblCheckedTargetList{{idx}}_delFlag" name="tblCheckedTargetList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td style="text-align:right;"><label>姓名：</label></td><td>
								<input id="tblCheckedTargetList{{idx}}_name" name="tblCheckedTargetList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
							</td>
							<td style="text-align:right;"><label>政治面貌：</label></td><td>
								<input id="tblCheckedTargetList{{idx}}_politicalStatus" name="tblCheckedTargetList[{{idx}}].politicalStatus" type="text" value="{{row.politicalStatus}}" maxlength="32" class="input-small "/>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>职务：</label></td>
							<td colspan="3">
								<input id="tblCheckedTargetList{{idx}}_duty" name="tblCheckedTargetList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input-xlarge "/>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>单位：</label></td>
							<td colspan="3">
								<input id="tblCheckedTargetList{{idx}}_unitName" name="tblCheckedTargetList[{{idx}}].unitName" type="text" value="{{row.unitName}}" maxlength="32" class="input-xlarge "/>
							</td>
						</tr><tr style="height:25px;"><td colspan="5"></td></tr>
					</tr>//-->
					</script>
				<script type="text/javascript">
					var tblCheckedTargetRowIdx = 0, tblCheckedTargetTpl = $("#tblCheckedTargetTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					$(document).ready(function() {
						var data = ${fns:toJson(tblConsultArchives.tblCheckedTargetList)};
						for (var i=0; i<data.length; i++){
							addRow('#tblCheckedTargetList', tblCheckedTargetRowIdx, tblCheckedTargetTpl, data[i]);
							tblCheckedTargetRowIdx = tblCheckedTargetRowIdx + 1;
						}
					});
				</script>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查档人员：</label>
			<div class="controls">
				<table id="contentTable" style="width:60%;" class="table table-striped table-bordered table-condensed">
					<colgroup>
				 		<col width="15%"/>
				 		<col width="48%"/>
				 		<col width="20%"/>
				 		<col width="15%"/>
				 		<col width="5%"/>
				 	</colgroup>
					<tbody id="tblCheckPersonList">
					</tbody>
					<tfoot>
						<tr><td colspan="10"></td></tr>
					</tfoot>
				</table>
				<script type="text/template" id="tblCheckPersonTpl">//<!--
					<tr>
						<tr id="tblCheckPersonList{{idx}}">
							<td class="hide">
								<input id="tblCheckPersonList{{idx}}_id" name="tblCheckPersonList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblCheckPersonList{{idx}}_delFlag" name="tblCheckPersonList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td style="text-align:right;"><label>姓名：</label></td>
							<td>
								<input id="tblCheckPersonList{{idx}}_name" name="tblCheckPersonList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-large "/>
							</td>
							<td rowspan="4" colspan="2" style="text-align:center;">
								<sys:upImg input="tblCheckPersonList{{idx}}_photo"  type="files" readonly="true" name="tblCheckPersonList[{{idx}}].photo"  value="{{row.photo}}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="头像上传"/>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>政治面貌：</label></td>
							<td>
								<input id="tblCheckPersonList{{idx}}_politicalStatus" name="tblCheckPersonList[{{idx}}].politicalStatus" type="text" value="{{row.politicalStatus}}" maxlength="32" class="input-large "/>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>职务：</label></td>
							<td colspan="1">
								<input id="tblCheckPersonList{{idx}}_duty" name="tblCheckPersonList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input-large "/>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>单位：</label></td>
							<td colspan="1">
								<input id="tblCheckPersonList{{idx}}_unitName" name="tblCheckPersonList[{{idx}}].unitName" type="text" value="{{row.unitName}}" maxlength="11" class="input-large "/>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>联系电话：</label></td>
							<td>
								<input id="tblCheckPersonList{{idx}}_telphone" name="tblCheckPersonList[{{idx}}].telphone" type="text" value="{{row.telphone}}" maxlength="11" class="input-large "/>
							</td>
							<td style="text-align:right;"><label>签字：</label></td>
							<td>
								<div class="td-order-one" id="tblCheckPersonList{{idx}}_siginDiv" style="display:none;">  
									<img id="tblCheckPersonList{{idx}}_siginImg" src="{{row.siginName}}" />
       							 </div>  
								
								<input id="tblCheckPersonList{{idx}}_siginInput" name="tblCheckPersonList[{{idx}}].siginInput" type="text"  class="input-small "/>
								<input id="tblCheckPersonList{{idx}}_siginName" name="tblCheckPersonList[{{idx}}].siginName" type="hidden"  value="{{row.siginName}}"  maxlength="120" class="input-small "/>
							</td>
						</tr><tr style="height:25px;"><td colspan="5"></td></tr>
					</tr>//-->
					</script>
				<script type="text/javascript">
					var tblCheckPersonRowIdx = 0, tblCheckPersonTpl = $("#tblCheckPersonTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					$(document).ready(function() {
						var data = ${fns:toJson(tblConsultArchives.tblCheckPersonList)};
						for (var i=0; i<data.length; i++){
							addRow('#tblCheckPersonList', tblCheckPersonRowIdx, tblCheckPersonTpl, data[i]);
							tblCheckPersonRowIdx = tblCheckPersonRowIdx + 1;
						}
					});
				</script>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查档事由：</label>
			<div class="controls">
				<form:textarea path="reason" readonly="true" htmlEscape="false" rows="4" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查档内容：</label>
			<div class="controls">
				<form:textarea path="content" readonly="true" htmlEscape="false" rows="4" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借阅审批附件：</label>
			<div class="controls">
				<c:if test="${not empty tblConsultArchives.approveAttachment}">
					<sys:upImgMul input="approveAttachment"  type="files" readonly="true" name="approveAttachment"  value="${tblConsultArchives.approveAttachment}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="上传快照"/>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" readonly="true" rows="4" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>