<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借阅管理管理</title>
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
			//绘制签名数据填充
			if(row != undefined && row.siginName != undefined && row.siginName != ""){
				document.getElementById("tblBorrowPersonList"+idx+"_siginDiv").style.display='';
	    		document.getElementById("tblBorrowPersonList"+idx+"_siginInput").style.display='none';
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
		<li><a href="${ctx}/borrow/tblBorrowArchives/">借阅管理列表</a></li>
		<li class="active"><a href="${ctx}/borrow/tblBorrowArchives/look?id=${tblBorrowArchives.id}">借阅管理查看<shiro:hasPermission name="borrow:tblBorrowArchives:edit">${not empty tblBorrowArchives.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="borrow:tblBorrowArchives:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tblBorrowArchives" action="${ctx}/borrow/tblBorrowArchives/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">借阅日期：</label>
			<div class="controls">
				<input name="borrowDate" type="text" readonly="readonly" style="width:268px;" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tblBorrowArchives.borrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借阅单位：</label>
			<div class="controls">
				<form:input path="consultUnitName" htmlEscape="false" value="${tblBorrowArchives.consultUnitName}"  maxlength="64" readonly="true" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借阅对象：</label>
			<div class="controls">
				<table id="contentTable" style="width:60%;" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th class="hide"></th>
							
						</tr>
					</thead>
					<tbody id="tblBorrowTargetList">
					</tbody>
					<tfoot>
						<tr><td colspan="9"></td></tr>
					</tfoot>
				</table>
				<script type="text/template" id="tblBorrowTargetTpl">//<!--
					<tr>
						<tr id="tblBorrowTargetList{{idx}}">
							<td class="hide">
								<input id="tblBorrowTargetList{{idx}}_id" name="tblBorrowTargetList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblBorrowTargetList{{idx}}_delFlag" name="tblBorrowTargetList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td style="text-align:right;width:120px;"><label>姓名：</label></td>
							<td>
								<input id="tblBorrowTargetList{{idx}}_name" name="tblBorrowTargetList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
							</td>
							<td style="text-align:right;width:120px;"><label>政治面貌：</label></td>
							<td>
								<input id="tblBorrowTargetList{{idx}}_politicalStatus" name="tblBorrowTargetList[{{idx}}].politicalStatus" type="text" value="{{row.politicalStatus}}" maxlength="32" class="input-small "/>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>职务：</label></td>
							<td colspan="3">
								<input id="tblBorrowTargetList{{idx}}_duty" name="tblBorrowTargetList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input-xlarge "/>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>单位：</label></td>
							<td colspan="3">
								<input id="tblBorrowTargetList{{idx}}_unitName" name="tblBorrowTargetList[{{idx}}].unitName" type="text" value="{{row.unitName}}" maxlength="32" class="input-xlarge "/>
							</td>
						</tr><tr style="height:25px;"><td colspan="5"></td></tr>
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
				<table id="contentTable" style="width:60%;" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th class="hide"></th>
							
						</tr>
					</thead>
					<tbody id="tblBorrowPersonList">
					</tbody>
					<tfoot>
						<tr><td colspan="10"></td></tr>
					</tfoot>
				</table>
				<script type="text/template" id="tblBorrowPersonTpl">//<!--
					<tr>
						<tr id="tblBorrowPersonList{{idx}}">
							<td class="hide">
								<input id="tblBorrowPersonList{{idx}}_id" name="tblBorrowPersonList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblBorrowPersonList{{idx}}_delFlag" name="tblBorrowPersonList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td style="text-align:right;width:120px;"><label>姓名：</label></td>
							<td>
								<input id="tblBorrowPersonList{{idx}}_name" name="tblBorrowPersonList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
							</td>
							<td rowspan="2" colspan="2" style="text-align:center;">
								<sys:upImg input="tblBorrowPersonList{{idx}}_photo"  type="files"  name="tblBorrowPersonList[{{idx}}].photo"  value="{{row.photo}}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="头像上传"/>
							</td>
						</tr><tr>
							<td style="text-align:right;width:120px;"><label>政治面貌：</label></td>
							<td>
								<input id="tblBorrowPersonList{{idx}}_politicalStatus" name="tblBorrowPersonList[{{idx}}].politicalStatus" type="text" value="{{row.politicalStatus}}" maxlength="32" class="input-small "/>
							</td>
						</tr><tr>
							<td style="text-align:right;width:120px;"><label>职务：</label></td>
							<td colspan="3">
								<input id="tblBorrowPersonList{{idx}}_duty" name="tblBorrowPersonList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input-xlarge "/>
							</td>
						</tr><tr>
							<td style="text-align:right;width:120px;"><label>单位：</label></td>
							<td colspan="3">
								<input id="tblBorrowPersonList{{idx}}_unitName" name="tblBorrowPersonList[{{idx}}].unitName" type="text" value="{{row.unitName}}" maxlength="32" class="input-xlarge "/>
							</td>
						</tr><tr>
							<td style="text-align:right;width:120px;"><label>联系电话：</label></td>
							<td>
								<input id="tblBorrowPersonList{{idx}}_telphone" name="tblBorrowPersonList[{{idx}}].telphone" type="text" value="{{row.telphone}}" maxlength="11" class="input-small "/>
							</td>
							<td style="text-align:right;width:120px;"><label>签字：</label></td>
							<td>
								<div class="td-order-one" id="tblBorrowPersonList{{idx}}_siginDiv" style="display:none;">  
									<img id="tblBorrowPersonList{{idx}}_siginImg" src="{{row.siginName}}" />
									<a id="tblBorrowPersonList{{idx}}_aline" onclick="delSigin(this)">&times;</a>
       							 </div>  
								
								<input id="tblBorrowPersonList{{idx}}_siginInput" name="tblBorrowPersonList[{{idx}}].siginInput" type="text"   class="input-small "/>
								<input id="tblBorrowPersonList{{idx}}_siginName" name="tblBorrowPersonList[{{idx}}].siginName" type="hidden"  value="{{row.siginName}}"  maxlength="120" class="input-small "/>
							</td>
						</tr><tr style="height:25px;"><td colspan="5"></td></tr>
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
		<div class="control-group">
			<label class="control-label">借阅事由：</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" rows="4" maxlength="2000" readonly="true" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查档内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="2000"  readonly="true" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借阅审批附件：</label>
			<div class="controls">
				<c:if test="${not empty tblBorrowArchives.approveAttachment}">
					<sys:upImg input="approveAttachment" type="files" name="approveAttachment" value="${tblBorrowArchives.approveAttachment}" readonly="true" uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="上传"/>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" readonly="true" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>