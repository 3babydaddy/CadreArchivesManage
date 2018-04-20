<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查阅档案管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(function(){ 
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
			/**调整页面自适应*/
			//var h = $(document.body).height();
			//$("#photoShowDiv").css("height",h + "px");
			$("#photoShowDiv").click(function(){
				$.jBox("get:${ctx}/terminal/camera", {  
				    title: "图像采集",  
				    width: 400,  
				    height: 380,
				    showClose: false,
				    icon: 'info',
				    showSpeed:'fast',
				    buttons: { '关闭': true } /* 窗口的按钮 */
				    /* loaded: function (h) { 
				    	h.find("iframe").css("height","90%");
				    } */
				});  
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
		
		function siginOption(obj){
			$.jBox("iframe:${ctx}/scattereds/tblScatteredFiles/drowSigin?siginId="+obj.id, {  
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
		
		/* 返回按钮事件 */
		function goBack(){
			window.location.href = "${ctx}";
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
		#photoShowDiv{
			border: 10px solid #ccc;
		}
		
		#photoShowDiv>span{ 
			display:inline-block; height:100%; vertical-align:middle;
		}
		.img-responsive{
			vertical-align:middle;
		} 
		
		body {
			font-family: "微软雅黑";
			font-size:120%;
			width: 100%;
			height: 100%;
		}
		
		.container-fluid{
			height: 100%
		}
		html,#rowFluid1,#formInfo,#photoShowDiv,#inputForm,#photoShow{
			height: 100%
		}
		#rowFluid3{
			height: 80%;
			position:relative
		}
		#ulDiv{
			height: 9%;
		}
		.control-group{
			height:15%;
			
		}
		#scatteredId{
			height:auto;
			margin-bottom: 3%;
		}
		#scatteredA{
			width:8%;
			height:15%
		}
	</style>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid" id ="rowFluid1">
		<%-- <div id= "photoShowDiv" class="span4">
			<img id="photoShow" src="${ctxStatic}/images/quesheng.jpg" class="img-responsive center-block img-rounded" ><span></span>
		</div> --%>
		<div id = "formInfo" class="span12">
			<div class="row-fluid" id="ulDiv">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/scattereds/tblScatteredFiles/form?id=${tblConsultArchives.id}">零散材料<shiro:hasPermission name="scattereds:tblScatteredFiles:edit">${not empty tblConsultArchives.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="scattereds:tblScatteredFiles:edit">查看</shiro:lacksPermission></a></li>
				</ul>
			</div>
		<div class="row-fluid" id="rowFluid3">
			<form:form id="inputForm" modelAttribute="tblScatteredFiles" action="${ctx}/scattereds/tblScatteredFiles/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div style="display:none;">
			<sys:message content="${message}" />
		</div>			
		<div class="control-group">
			<label class="control-label">移交单位：</label>
			<div class="controls">
				<sys:treeselect2 url="/sys/dict/treeDataPop" id="handOverUnit" name="handOverUnit" allowClear="true" value="${tblScatteredFiles.handOverUnit}" 
									labelName="handOverUnitName" labelValue="${tblScatteredFiles.handOverUnitName}" title="单位列表"></sys:treeselect2>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">移交时间：</label>
			<div class="controls">
				<input name="handOverDate" type="text" readonly="readonly" style="width:268px;" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tblScatteredFiles.handOverDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经手人：</label>
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
		
			<div class="control-group" id="scatteredId">
				<label class="control-label">移交材料：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
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
							<tr><td colspan="9"><a id = "scatteredA" href="javascript:" onclick="addRow('#tblHandOverFilesList', tblHandOverFilesRowIdx, tblHandOverFilesTpl);tblHandOverFilesRowIdx = tblHandOverFilesRowIdx + 1;"  class="btn btn-primary"><i class="icon-plus"></i>新增</a></td></tr>
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
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#tblHandOverFilesList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
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
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="goBack()"/>
		</div>
	</form:form>
		</div>
		</div>
	</div>
	</div>
</body>
</html>