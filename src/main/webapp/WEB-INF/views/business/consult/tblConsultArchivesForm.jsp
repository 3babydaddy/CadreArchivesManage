<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查阅档案管理</title>
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
			
			//绘制签名数据填充
			if(row != undefined && row.siginName != undefined && row.siginName != ""){
				document.getElementById("tblCheckPersonList"+idx+"_siginDiv").style.display='';
	    		document.getElementById("tblCheckPersonList"+idx+"_siginInput").style.display='none';
			}
		}
		function delRowTar(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().next().next().next().remove();
				$(obj).parent().parent().next().next().remove();
				$(obj).parent().parent().next().next().remove();
				$(obj).parent().parent().next().remove();
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				//$(obj).parent().parent().addClass("error");
				$(obj).parent()[0].bgColor='#fadfdd';
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				//$(obj).parent().parent().removeClass("error");
				$(obj).parent()[0].bgColor='';
			}
		}
		function delRowPer(obj, prefix){
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
				$(obj).parent()[0].bgColor='#fadfdd';
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				//$(obj).parent().parent().removeClass("error");
				$(obj).removeClass("error");
			}
		}
		
		function siginOption(obj){
			$.jBox("iframe:${ctx}/consult/tblConsultArchives/drowSigin?siginId="+obj.id, {  
			    title: "绘制签名",  
			    width: 900,  
			    height: 400,
			    showClose: false,
			    buttons: { '关闭': true }  
			});  
		}
		
		function delSigin(obj){
			var siginId = obj.id;
			var siginImg = siginId.replace('aline', 'siginImg');
    		var siginDiv = siginId.replace('aline', 'siginDiv');
    		var siginName = siginId.replace('aline', 'siginName');
    		var siginInput = siginId.replace('aline', 'siginInput');
    		
    		document.getElementById(siginInput).style.display='';
    		document.getElementById(siginDiv).style.display='none';
    		
    		document.getElementById(siginImg).src='';
    		document.getElementById(siginName).value='';
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
		input[readonly]{
			background-color: white;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/consult/tblConsultArchives/">查阅档案列表</a></li>
		<li class="active"><a href="#">查阅档案<c:if test="${empty tblConsultArchives.id }">新增</c:if><c:if test="${not empty tblConsultArchives.id }">编辑</c:if></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tblConsultArchives" action="${ctx}/consult/tblConsultArchives/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">查阅日期：</label>
			<div class="controls">
				<input name="borrowDate" type="text" readonly="readonly" style="width:268px;" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${tblConsultArchives.borrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查阅单位：</label>
			<div class="controls">
				<sys:treeselect url="/sys/dict/treeDataPop" id="consultUnit" name="consultUnit" allowClear="true" value="${tblConsultArchives.consultUnit}"  
									labelName="consultUnitName" labelValue="${tblConsultArchives.consultUnitName}" title="单位列表" cssClass="required" cssStyle="width:240px;" ></sys:treeselect>
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
						<tr><td colspan="9"><a href="javascript:" onclick="addRow('#tblCheckedTargetList', tblCheckedTargetRowIdx, tblCheckedTargetTpl);tblCheckedTargetRowIdx = tblCheckedTargetRowIdx + 1;" style="width:50px;" class="btn btn-primary"><i class="icon-plus"></i>新增</a></td></tr>
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
								<input id="tblCheckedTargetList{{idx}}_name" name="tblCheckedTargetList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small required"/>
							</td>
							<td style="text-align:right;"><label>政治面貌：</label></td><td>
								<input id="tblCheckedTargetList{{idx}}_politicalStatus" name="tblCheckedTargetList[{{idx}}].politicalStatus" type="text" value="{{row.politicalStatus}}" maxlength="32" class="input-small "/>
							</td>
							<td rowspan="3" class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRowTar(this, '#tblCheckedTargetList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>职务：</label></td>
							<td colspan="3">
								<input id="tblCheckedTargetList{{idx}}_duty" name="tblCheckedTargetList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input-xlarge "/>
							</td>
						</tr><tr>
							<td style="text-align:right;"><label>单位：</label></td>
							<td colspan="3">
								<sys:treeselect url="/sys/dict/treeDataPop" id="tblCheckedTargetList{{idx}}_unit" name="tblCheckedTargetList[{{idx}}].unit" allowClear="true" value="{{row.unit}}" 
									labelName="unitName" labelValue="{{row.unitName}}" title="单位列表" cssStyle="width:240px;"></sys:treeselect>
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
						<tr><td colspan="10"><a href="javascript:" onclick="addRow('#tblCheckPersonList', tblCheckPersonRowIdx, tblCheckPersonTpl);tblCheckPersonRowIdx = tblCheckPersonRowIdx + 1;" style="width:50px;" class="btn btn-primary"><i class="icon-plus"></i>新增</a></td></tr>
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
								<input id="tblCheckPersonList{{idx}}_name" name="tblCheckPersonList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small required"/>
							</td>
							<td rowspan="4" colspan="2" style="text-align:center;">
								<sys:upImg input="tblCheckPersonList{{idx}}_photo"  type="files"  name="tblCheckPersonList[{{idx}}].photo"  value="{{row.photo}}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="头像上传"/>
							</td>
							<td rowspan="5" class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRowPer(this, '#tblCheckPersonList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
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
								<sys:treeselect url="/sys/dict/treeDataPop" id="tblCheckPersonList{{idx}}_unit" name="tblCheckPersonList[{{idx}}].unit" allowClear="true" value="{{row.unit}}" 
									labelName="unitName" labelValue="{{row.unitName}}" title="单位列表" cssStyle="width:179px;"></sys:treeselect>
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
									<a id="tblCheckPersonList{{idx}}_aline" onclick="delSigin(this)">&times;</a>
       							 </div>  
								
								<input id="tblCheckPersonList{{idx}}_siginInput" name="tblCheckPersonList[{{idx}}].siginInput" type="text"   onclick=siginOption(this);  class="input-small "/>
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
				<sys:upImgMul input="approveAttachment"  type="files"  name="approveAttachment"  value="${tblConsultArchives.approveAttachment}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="上传快照"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>