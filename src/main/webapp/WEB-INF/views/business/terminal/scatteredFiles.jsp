<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查阅档案管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/common/index.css" rel="stylesheet" type="text/css">
	<script src="${ctxStatic}/My97DatePicker/WdatePickerByUser.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){ 
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					//form.submit();
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
		
		var addScattered  =  function() {
			if(validateDate()){
				$.ajax({
	                type: "POST",//方法类型
	                dataType: "json",//预期服务器返回的数据类型
	                url: "${ctx}/scattereds/tblScatteredFiles/saveTerminal",//url
	                data: $('#inputForm').serialize(),
	                success: function (result) {
	                    //alert(result);
	                    //debugger;
	                    
	                    if(result){
	                    	var submit = function (v, h, f) {
	                    	    if (v == 'ok')
	                    	    	window.location.href="${ctx}";
	                    	    return true; //close
	                    	};
	                    	$.jBox.confirm("确定吗？", "提示", submit);
	                    	
	                    };
	                },
	                error : function() {
	                    alert("异常！");
	                }
	            }); 
			}
        }
		
		function validateDate(){
			var handOverUnitId = $("#handOverUnitId").val();
			if(handOverUnitId == ""){
				alertx("移交单位不能为空！");
				return false;
			}
			var handOverDate = $("#handOverDate").val();
			if(handOverDate == ""){
				alertx("移交日期不能为空！");
				return false;
			}
			var operator = $("#operator").val();
			if(operator == ""){
				alertx("经办人不能为空！");
				return false;
			}
			var recipient = $("#recipient").val();
			if(recipient == ""){
				alertx("接收人不能为空！");
				return false;
			}
			for(var i = 0; i < tblHandOverFilesRowIdx; i++){
				var name = $("#tblHandOverFilesList"+i+"_name").val()
				if(name == ""){
					alertx("移交人员的姓名不能为空！");
					return false;
					break;
				}
			}
			return true;
		}
	</script>
	<style type="text/css">
		body {
			font-family: "微软雅黑";
			font-size:120%;
			width: 100%;
			height: 100%;
		}
		html,#rowFluid1,#formInfo,#inputForm{
			height: 100%
		}
		.overflow{
			height: 622px;
		}
		a,th,label{
			font-size : 150%;
		}
		.table th, .table td, .table input{
			text-align : center;
		}
		.table input{
			height: 10% !important;
			width: 75% !important;
		}
		.close{
			font-size: 35px;
			left: -10%;
    		position: relative;
		}
	</style>
</head>
<body>
	<div class="header">
		<a href="${ctx}"><img src="${ctxStatic}/images/terminal/goback.png"></a>
		<img src="${ctxStatic}/images/terminal/top3.png" style="width:100%;">
	</div>
	<div class="content">
		<form:form id="inputForm" modelAttribute="tblScatteredFiles" action="${ctx}/scattereds/tblScatteredFiles/saveTerminal" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<div style="display:none;">
				<sys:message content="${message}" />
			</div>
			<div class="left fl" style="width:100%;">
				<div class="search">
					<div class="fl">
						<span class="fl" style="font-size:30px;" >移交单位：</span>
						<sys:treeselect3 url="/sys/dict/treeDataPop" id="handOverUnit" name="handOverUnit" allowClear="true" value="${tblScatteredFiles.handOverUnit}" 
															labelName="handOverUnitName" labelValue="${tblScatteredFiles.handOverUnitName}" title="单位列表"></sys:treeselect3>
					</div>
					<div class="fr">
						<span class="fl" style="font-size:30px;" >移交日期：</span>
						<input id="handOverDate" name="handOverDate" type="text" maxlength="20" class="input_2 Wdate"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="overflow">
					<div class="object">
						<div class="obj_head">
							<div class="title fl">移交材料</div>
							<div class="add fr">
								<a href="javascript:void(0)"  onclick="addRow('#tblHandOverFilesList', tblHandOverFilesRowIdx, tblHandOverFilesTpl);tblHandOverFilesRowIdx = tblHandOverFilesRowIdx + 1;" ><img src="${ctxStatic}/images/terminal/add.png"> 新增</a>
							</div>
							<div class="clear"></div>
						</div>
						<div id="add_content_obj">
							<div class="obj_con">
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
								</table>
								<script type="text/template" id="tblHandOverFilesTpl">//<!--
									<tr id="tblHandOverFilesList{{idx}}">
										<td class="hide">
											<input id="tblHandOverFilesList{{idx}}_id" name="tblHandOverFilesList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
											<input id="tblHandOverFilesList{{idx}}_delFlag" name="tblHandOverFilesList[{{idx}}].delFlag" type="hidden" value="0"/>
										</td>
										<td>
											<input id="tblHandOverFilesList{{idx}}_name" name="tblHandOverFilesList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input_1"/>
										</td>
										<td>
											<input id="tblHandOverFilesList{{idx}}_duty" name="tblHandOverFilesList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input_1"/>
										</td>
										<td>
											<input id="tblHandOverFilesList{{idx}}_filesNames" name="tblHandOverFilesList[{{idx}}].filesNames" type="text" value="{{row.filesNames}}" maxlength="2000" class="input_1"/>
										</td>
										<td>
											<input id="tblHandOverFilesList{{idx}}_originalNo" name="tblHandOverFilesList[{{idx}}].originalNo" type="text" value="{{row.originalNo}}" maxlength="11" class="input_1 digits"/>
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
					</div>
				</div>
				<div class="search">
					<div class="fl">
						<span class="fl" style="font-size:30px;" >&nbsp;&nbsp;&nbsp;经手人：</span>
						<input class="input_2" style="width:404px;" id="operator" name="operator" value="${tblScatteredFiles.operator}" type="text"/>
					</div>
					<div class="fr">
						<span class="fl" style="font-size:30px;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;接收人：</span>
						<input class="input_2" id="recipient" name="recipient" value="${tblScatteredFiles.recipient}" type="text"/>
					</div>
					<div class="clear"></div>
				</div>
				<input type="submit" class="save_btn fr" onclick="addScattered()" value="保 存" />
			</div>
		</form:form>
	</div>
	<div class="footer">中共天津市委组织部信息管理处&nbsp;&nbsp;&nbsp;&nbsp;天津市天房科技发展股份有限公司&nbsp;&nbsp;&nbsp;&nbsp;联合开发</div>
</body>
</html>