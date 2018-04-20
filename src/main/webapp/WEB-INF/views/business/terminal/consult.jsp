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
		
		/* 返回按钮事件 */
		function goBack(){
			window.location.href = "${ctx}";
		}
		var addConsult  =  function() {
	            $.ajax({
	                type: "POST",//方法类型
	                dataType: "json",//预期服务器返回的数据类型
	                url: "${ctx}/consult/tblConsultArchives/saveTerminal",//url
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
			height:13%;
		}
		#checkId{
		margin-top: 0%;
		height:auto;
		}
		#checkP{
		margin-top: 3%;
		height:auto;
		margin-bottom:3%
		}
		a,th,label,#btnCancel,#btnSubmit{
			font-size : 150%;
		}
	</style>
</head>
<body>
	<div class="row-fluid" id ="rowFluid1">
		<div id= "photoShowDiv" class="row-fluid span4">
			<img id="photoShow" src="${ctxStatic}/images/quesheng.jpg" class="img-responsive center-block img-rounded" ><span></span>
		</div>
		<div id = "formInfo" class="row-fluid span8">
			<div class="row-fluid" id="ulDiv">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/consult/tblConsultArchives/form?id=${tblConsultArchives.id}">查阅档案<shiro:hasPermission name="consult:tblConsultArchives:edit">${not empty tblConsultArchives.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="consult:tblConsultArchives:edit">查看</shiro:lacksPermission></a></li>
				</ul>
			</div>
			<div class="row-fluid" id="rowFluid3">
			<form:form id="inputForm" modelAttribute="tblConsultArchives" action="${ctx}/consult/tblConsultArchives/saveTerminal" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<div style="display:none;">
					<sys:message content="${message}" />
				</div>	
				<div class="control-group">
					<label class="control-label">查阅日期：</label>
					<div class="controls">
						<input name="borrowDate" type="text" readonly="readonly" style="width:268px;" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${tblConsultArchives.borrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">查阅单位：</label>
					<div class="controls">
						<sys:treeselect2 url="/sys/dict/treeDataPop" id="consultUnit" name="consultUnit" allowClear="true" value="${tblConsultArchives.consultUnit}" 
											labelName="consultUnitName" labelValue="${tblConsultArchives.consultUnitName}" title="单位列表"></sys:treeselect2>
					</div>
				</div>
				<div class="control-group" id="checkId">
						<label class="control-label">查档对象：</label>
						<div class="controls">
							<table id="contentTable" class="table table-striped table-bordered table-condensed">
								<thead>
									<tr>
										<th class="hide"></th>
										<th>姓名</th>
										<th>单位</th>
										<th>职务</th>
										<th>政治面貌</th>
										<shiro:hasPermission name="consult:tblConsultArchives:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
									</tr>
								</thead>
								<tbody id="tblCheckedTargetList">
								</tbody>
								<tfoot>
									<tr><td colspan="9">
											<button class="btn" type="button" onclick="addRow('#tblCheckedTargetList', tblCheckedTargetRowIdx, tblCheckedTargetTpl);tblCheckedTargetRowIdx = tblCheckedTargetRowIdx + 1;"><i class="icon-plus"></i>新增</button>
										</td>
									</tr>
								</tfoot>
							</table>
							<script type="text/template" id="tblCheckedTargetTpl">//<!--
						<tr id="tblCheckedTargetList{{idx}}">
							<td class="hide">
								<input id="tblCheckedTargetList{{idx}}_id" name="tblCheckedTargetList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblCheckedTargetList{{idx}}_delFlag" name="tblCheckedTargetList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="tblCheckedTargetList{{idx}}_name" name="tblCheckedTargetList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<sys:treeselect url="/sys/dict/treeDataPop" id="tblCheckedTargetList{{idx}}_unit" name="tblCheckedTargetList[{{idx}}].unit" allowClear="true" value="{{row.unit}}" 
									labelName="unitName" labelValue="{{row.unitName}}" title="单位列表"></sys:treeselect>
							</td>
							<td>
								<input id="tblCheckedTargetList{{idx}}_duty" name="tblCheckedTargetList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="tblCheckedTargetList{{idx}}_politicalStatus" name="tblCheckedTargetList[{{idx}}].politicalStatus" type="text" value="{{row.politicalStatus}}" maxlength="32" class="input-small "/>
							</td>
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#tblCheckedTargetList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
						</tr>
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
					<div class="control-group" id="checkP">
						<label class="control-label">查档人员：</label>
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
										<shiro:hasPermission name="consult:tblConsultArchives:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
									</tr>
								</thead>
								<tbody id="tblCheckPersonList">
								</tbody>
								<tfoot>
									<tr><td colspan="10">
										<button class="btn" type="button" onclick="addRow('#tblCheckPersonList', tblCheckPersonRowIdx, tblCheckPersonTpl);tblCheckPersonRowIdx = tblCheckPersonRowIdx + 1;"><i class="icon-plus"></i>新增</button>
										</td>
									</tr>
								</tfoot>
							</table>
							<script type="text/template" id="tblCheckPersonTpl">//<!--
						<tr id="tblCheckPersonList{{idx}}">
							<td class="hide">
								<input id="tblCheckPersonList{{idx}}_id" name="tblCheckPersonList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tblCheckPersonList{{idx}}_delFlag" name="tblCheckPersonList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="tblCheckPersonList{{idx}}_name" name="tblCheckPersonList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<sys:upFIle input="tblCheckPersonList{{idx}}_photo"  type="files"  name="tblCheckPersonList[{{idx}}].photo"  value="{{row.photo}}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="上传"/>
							</td>
							<td>
								<sys:treeselect url="/sys/dict/treeDataPop" id="tblCheckPersonList{{idx}}_unit" name="tblCheckPersonList[{{idx}}].unit" allowClear="true" value="{{row.unit}}" 
									labelName="unitName" labelValue="{{row.unitName}}" title="单位列表"></sys:treeselect>
							</td>
							<td>
								<input id="tblCheckPersonList{{idx}}_duty" name="tblCheckPersonList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="tblCheckPersonList{{idx}}_politicalStatus" name="tblCheckPersonList[{{idx}}].politicalStatus" type="text" value="{{row.politicalStatus}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="tblCheckPersonList{{idx}}_telphone" name="tblCheckPersonList[{{idx}}].telphone" type="text" value="{{row.telphone}}" maxlength="32" class="input-small "/>
							</td>
							<td>

								<div class="td-order-one" id="tblCheckPersonList{{idx}}_siginDiv" style="display:none;">  
									<img id="tblCheckPersonList{{idx}}_siginImg" src="{{row.siginName}}" />
									<a id="tblCheckPersonList{{idx}}_aline" onclick="delSigin(this)">&times;</a>
       							 </div>  
								
								<input id="tblCheckPersonList{{idx}}_siginInput" name="tblCheckPersonList[{{idx}}].siginInput" type="text"   onclick=siginOption(this);  class="input-small "/>
								<input id="tblCheckPersonList{{idx}}_siginName" name="tblCheckPersonList[{{idx}}].siginName" type="hidden"  value="{{row.siginName}}"  maxlength="120" class="input-small "/>
							</td>
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#tblCheckPersonList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
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
				<input type="hidden" name="approveAttachment" id="approveAttachmentId"/>
				<%-- <div class="control-group">
					<label class="control-label">借阅审批附件：</label>
					<div class="controls">
						<sys:upFIle input="approveAttachment"  type="files"  name="approveAttachment"  value="${tblConsultArchives.approveAttachment}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="上传"/>
					</div>
				</div> --%>
				<%-- <div class="control-group">
					<label class="control-label">备注信息：</label>
					<div class="controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/>
					</div>
				</div> --%>
					
				<div class="form-actions">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="addConsult()"/>&nbsp;
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="goBack()"/>
				</div>
			</form:form>
		</div>
		</div>
	</div>
</body>
</html>