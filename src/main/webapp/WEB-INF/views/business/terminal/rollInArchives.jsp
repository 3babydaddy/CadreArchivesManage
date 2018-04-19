<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转入档案管理</title>
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
			//var h = parent.$("iframe").height();
			//$("#photoShowDiv").css("height",h + "px");
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
			$.jBox("iframe:${ctx}/rollin/tblRollIn/drowSigin?siginId="+obj.id, {  
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
			//this.parent.$(".zhuye")[0].click();
			window.history.back();
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
			height:8%;
		}
	</style>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid" id ="rowFluid1">
		<div id= "photoShowDiv" class="span4">
			<img id="photoShow" src="${ctxStatic}/images/quesheng.jpg" class="img-responsive center-block img-rounded" ><span></span>
		</div>
		<div id = "formInfo" class="span8">
			<div class="row-fluid" id="ulDiv">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/rollin/tblRollIn/form?id=${tblRollIn.id}">转入档案<shiro:hasPermission name="rollin:tblRollIn:edit">${not empty tblRollIn.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rollin:tblRollIn:edit">查看</shiro:lacksPermission></a></li>
				</ul>
			</div>
			<div class="row-fluid" id="rowFluid3">
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
						<input name="rollInTime" type="text" readonly="readonly" style="width:268px;" maxlength="20" class="input-medium Wdate "
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
						<sys:treeselect2 url="/sys/dict/treeDataPop" id="beforeUnit" name="beforeUnit" allowClear="true" value="${tblRollIn.beforeUnit}" 
											labelName="beforeUnitName" labelValue="${tblRollIn.beforeUnitName}" title="单位列表"></sys:treeselect2>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label">原存档单位电话：</label>
					<div class="controls">
						<form:input path="beforeUnitTel" htmlEscape="false" maxlength="11" class="input-xlarge "/>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label">现存档单位：</label>
					<div class="controls">
						<sys:treeselect2 url="/sys/dict/treeDataPop" id="saveUnit" name="saveUnit" allowClear="true" value="${tblRollIn.saveUnit}" 
											labelName="saveUnitName" labelValue="${tblRollIn.saveUnitName}" title="单位列表"></sys:treeselect2>
					</div>
				</div>
				<div class="control-group">
			<label class="control-label">借阅审批附件：</label>
			<div class="controls">
				<sys:upFIle input="rollApproveAttachment"  type="files"  name="rollApproveAttachment"  value="${tblRollIn.rollApproveAttachment}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="上传"/>
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
								<input id="tblRollInPersonsList{{idx}}_name" name="tblRollInPersonsList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
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
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="goBack()"/>
				</div>
			</form:form>
		</div>
		</div>
	</div>
	</div>
</body>
</html>