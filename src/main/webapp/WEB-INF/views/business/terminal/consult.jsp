<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查阅档案管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/common/index.css" rel="stylesheet" type="text/css">
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
		function delTarRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
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
				//$(obj).parent().parent().removeClass("error");
			}
		}
		
		function delPerRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
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
				//$(obj).parent().parent().removeClass("error");
			}
		}
		
		function siginOption(obj){
			var h = $(document.body).height();
			var w = $(document.body).width();
			$.jBox("iframe:${ctx}/consult/tblConsultArchives/drowSigin?siginId="+obj.id, {  
			    title: "绘制签名",  
			    width: (w*2/3),  
			    height: (h/2+20),
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
		
		function addConsult() {
			if(validateDate()){
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
	    }
		
		function validateDate(){
			var borrowDate = $("#borrowDate").val();
			if(borrowDate == ""){
				alertx("查阅日期不能为空！");
				return false;
			}
			var consultUnitId = $("#consultUnitId").val();
			if(consultUnitId == ""){
				alertx("查阅单位不能为空！");
				return false;
			}
			for(var i = 0; i < tblCheckedTargetRowIdx; i++){
				var tarName = $("#tblCheckedTargetList"+i+"_name").val()
				if(tarName == ""){
					alertx("查档对象的姓名不能为空！");
					return false;
					break;
				}
			}
			for(var i = 0; i < tblCheckPersonRowIdx; i++){
				var perName = $("#tblCheckPersonList"+i+"_name").val()
				if(perName == ""){
					alertx("查档人员的姓名不能为空！");
					return false;
					break;
				}
			}
			return true;
		}
		
		var TimeFn = null;
		function uploadImg(obj){
			// 取消上次延时未执行的方法
		    clearTimeout(TimeFn);
		    //执行延时
		    TimeFn = setTimeout(function(){
				/**调整页面自适应*/
				//var h = parent.$("iframe").height();
				//$("#photoShowDiv").css("height",h + "px");
				var h = $(document.body).height();
				var w = $(document.body).width();
				//$("#photoShowDiv").css("height",h + "px");
				$.jBox("get:${ctx}/terminal/camera", {  
				    title: "图像采集",  
				    width: (w/2),  
				    height: (h*3/4),
				    showClose: false,
				    icon: 'info',
				    showSpeed:'fast',
				    buttons: { '关闭': true } /* 窗口的按钮 */
				    /* loaded: function (h) { 
				    	h.find("iframe").css("height","90%");
				    } */
				});
		    },300);
		}
		
		function removeImg(obj){
			// 取消上次延时未执行的方法
			clearTimeout(TimeFn);
			var num = $("#photoShowDiv img").length;
            var src = document.getElementById("photoShow").src;
            var suffice = src.substring((src.indexOf('.')-4), src.indexOf('.'));
            if(num == 1 && suffice != 'scan'){
            	document.getElementById("photoShow").src = '${ctxStatic}/images/terminal/scan.png';
            	$("#photoShowDiv img")[0].style.height= '15%';
                document.getElementById("approveAttachmentId").value = '';
                $("#photoShowDiv").append('<p style="font-size:30px;">请将您的文件放置于扫描区</p>');
                $("#photoShowDiv").addClass('right_text');
            }else if(num > 1){
            	//删除的文件名
            	var fileName = obj.src.substring(obj.src.lastIndexOf('/')+1)
            	$(obj).remove();
            	//重新计算高度
            	var height = $("#photoShowDiv").height();
                for(var i = 0; i < (num-1); i++){
                	$("#photoShowDiv img")[i].style.height= (height/(num-1)) +'px';
                }
                var approveAttachmentId = "";
                var attArray = $("#approveAttachmentId").val().split(",");
                for(var j = 0; j < attArray.length; j++){
                	if(attArray[j].indexOf(fileName) == -1){
                		if(approveAttachmentId == ""){
                			approveAttachmentId = attArray[j];
                		}else{
                			approveAttachmentId += "," + attArray[j];
                		}
                	}
                }
                $("#approveAttachmentId").val(approveAttachmentId);
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
		
		#photoShowDiv>span{ 
			display:inline-block; vertical-align:middle;
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
		html,#rowFluid1,#formInfo,#photoShowDiv{
			height: 100%
		}
		#ulDiv{
			height: 9%;
		}
		a,th,label{
			font-size : 150%;
		}
		#photoShowDiv{
		    background-color: rgba(255, 255, 255, 0.5);
    		filter: progid:DXImageTransform.Microsoft.Gradient(startColorstr=#40000000,endColorstr=#40000000);
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
		<img src="${ctxStatic}/images/terminal/top.png" style="width:100%;">
	</div>
	<div class="content">
		<form:form id="inputForm" modelAttribute="tblConsultArchives" action="${ctx}/consult/tblConsultArchives/saveTerminal" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<div style="display:none;">
				<sys:message content="${message}" />
			</div>
			<div class="left fl">
				<div class="search">
					<div class="fl">
						<span class="fl" style="font-size:30px;" >查阅日期：</span>
						<input class="input_1" id="borrowDate" name="borrowDate" value="${tblConsultArchives.borrowDate}" type="date"/>
					</div>
					<div class="fr">
						<span class="fl" style="font-size:30px;" >查阅单位：</span>
						<sys:treeselect3 url="/sys/dict/treeDataPop" id="consultUnit" name="consultUnit" allowClear="true" value="${tblConsultArchives.consultUnit}" 
															labelName="consultUnitName" labelValue="${tblConsultArchives.consultUnitName}" title="单位列表"></sys:treeselect3>
					</div>
					<div class="clear"></div>
				</div>
				<div class="overflow">
					<div class="object">
						<div class="obj_head">
							<div class="title fl">查档对象</div>
							<div class="add fr">
								<a href="javascript:void(0)"  onclick="addRow('#tblCheckedTargetList', tblCheckedTargetRowIdx, tblCheckedTargetTpl);tblCheckedTargetRowIdx = tblCheckedTargetRowIdx + 1;" ><img src="${ctxStatic}/images/terminal/add.png"> 新增</a>
							</div>
							<div class="clear"></div>
						</div>
						<div id="add_content_obj">
							<div class="obj_con">
								<table id="contentTable" class="">
									<colgroup>
										<col width="38%"/>
								 		<col width="57%"/>
								 		<col width="4%"/>
							 		</colgroup>
									<tbody id="tblCheckedTargetList">
									</tbody>
								</table>
								<script type="text/template" id="tblCheckedTargetTpl">//<!--
									<tr id="tblCheckedTargetList{{idx}}">
										<td class="hide">
											<input id="tblCheckedTargetList{{idx}}_id" name="tblCheckedTargetList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
											<input id="tblCheckedTargetList{{idx}}_delFlag" name="tblCheckedTargetList[{{idx}}].delFlag" type="hidden" value="0"/>
										</td>
										<td>
											<span class="fr"><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名：</label>
											<input id="tblCheckedTargetList{{idx}}_name" name="tblCheckedTargetList[{{idx}}].name" type="text" value="{{row.name}}" class="input1"/></span>
										</td>
										<td>
											<span class="fr"><label>单位：</label>
											<sys:treeselect3 url="/sys/dict/treeDataPop" id="tblCheckedTargetList{{idx}}_unit" name="tblCheckedTargetList[{{idx}}].unit" allowClear="true" value="{{row.unit}}" 
												labelName="unitName" labelValue="{{row.unitName}}" title="单位列表"></sys:treeselect3>
										</td>
										<td rowspan="2" class="text-center" style="background:#00000017;" width="2">
											{{#delBtn}}<span class="close" onclick="delTarRow(this, '#tblCheckedTargetList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
										</td>
									</tr><tr>
										<td>
											<span class="fr"><label>职务：</label>
											<input id="tblCheckedTargetList{{idx}}_duty" name="tblCheckedTargetList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input1"/></span>
										</td>
										<td>
											<span class="fr"><label>政治面貌：</label>
											<input id="tblCheckedTargetList{{idx}}_politicalStatus" name="tblCheckedTargetList[{{idx}}].politicalStatus" value="{{row.politicalStatus}}" type="text" class="input3"/></span>
										</td>
									</tr><tr style="height:25px;"><td colspan="5"></td></tr>
											
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
					</div>
					<div class="object">
						<div class="obj_head">
							<div class="title fl">查档人员</div>
							<div class="add fr">
								<a href="javascript:void(0)" onclick="addRow('#tblCheckPersonList', tblCheckPersonRowIdx, tblCheckPersonTpl);tblCheckPersonRowIdx = tblCheckPersonRowIdx + 1;"><img src="${ctxStatic}/images/terminal/add.png"> 新增</a>
							</div>
							<div class="clear"></div>
						</div>
						<div id="add_content_obj">
							<div class="obj_con">
								<table id="contentTable" class="">
									<colgroup>
										<col width="38%"/>
								 		<col width="57%"/>
								 		<col width="4%"/>
							 		</colgroup>
									<tbody id="tblCheckPersonList">
									</tbody>
								</table>
								<script type="text/template" id="tblCheckPersonTpl">//<!--
											<tr>
												<tr id="tblCheckPersonList{{idx}}">
													<td class="hide">
														<input id="tblCheckPersonList{{idx}}_id" name="tblCheckPersonList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
														<input id="tblCheckPersonList{{idx}}_delFlag" name="tblCheckPersonList[{{idx}}].delFlag" type="hidden" value="0"/>
													</td>
													<td>
														<span class="fr"><label>姓名：</label>
														<input id="tblCheckPersonList{{idx}}_name" name="tblCheckPersonList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input1"/></span>
													</td>
													<td>
														<span class="fr"><label>政治面貌：</label>
														<input id="tblCheckPersonList{{idx}}_politicalStatus" name="tblCheckPersonList[{{idx}}].politicalStatus" type="text" value="{{row.politicalStatus}}" maxlength="32" class="input3"/></span>
													</td>
													<td rowspan="3" colspan="2" style="text-align:center;border:1px solid #19181840;display:none;">
														<sys:upImg input="tblCheckPersonList{{idx}}_photo"  type="files"  name="tblCheckPersonList[{{idx}}].photo"  value="{{row.photo}}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="头像上传"/>
													</td>
													<td rowspan="3" class="text-center" style="background:#00000017;" width="2">
														{{#delBtn}}<span class="close" onclick="delPerRow(this, '#tblCheckPersonList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
													</td>
												</tr><tr>
													<td>
														<span class="fr"><label>职务：</label>
														<input id="tblCheckPersonList{{idx}}_duty" name="tblCheckPersonList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input1"/></span>
													</td>
													<td>
														<span class="fr"><label>单位：</label>
														<sys:treeselect3 url="/sys/dict/treeDataPop" id="tblCheckPersonList{{idx}}_unit" name="tblCheckPersonList[{{idx}}].unit" allowClear="true" value="{{row.unit}}" 
															labelName="unitName" labelValue="{{row.unitName}}" title="单位列表"></sys:treeselect3>
													</td>
												</tr><tr>
													<td>
														<span class="fr"><label>联系电话：</label>
														<input id="tblCheckPersonList{{idx}}_telphone" name="tblCheckPersonList[{{idx}}].telphone" type="text" value="{{row.telphone}}" maxlength="11" class="input1"/></span>
													</td>
													<td>
														<span class="fr"><label>签字：</label>
														<input id="tblCheckPersonList{{idx}}_siginInput" name="tblCheckPersonList[{{idx}}].siginInput" type="text" onclick=siginOption(this); class="input3"/></span>
														<input id="tblCheckPersonList{{idx}}_siginName" name="tblCheckPersonList[{{idx}}].siginName" type="hidden"  value="{{row.siginName}}"  maxlength="120" class="input3"/></span>
														
														<div class="td-order-one" id="tblCheckPersonList{{idx}}_siginDiv" style="display:none;">  
															<img id="tblCheckPersonList{{idx}}_siginImg" src="{{row.siginName}}" />
															<a id="tblCheckPersonList{{idx}}_aline" onclick="delSigin(this)">&times;</a>
       							 						</div>  
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
					</div>
					<div class="textarea">
						<div class="cause fl">
							<div class="title">查档事由</div>
							<form:textarea path="reason" htmlEscape="false" rows="4" maxlength="2000" class="input-xlarge "/>
						</div>
						<div class="cause fr">
							<div class="title">查档内容</div>
							<form:textarea path="content" htmlEscape="false" rows="4" maxlength="2000" class="input-xlarge "/>
						</div>
						<div class="clear"></div>
					</div>
				</div>
				<input type="hidden" name="approveAttachment" id="approveAttachmentId"/>
				<input type="button" class="save_btn fr" onclick="addConsult();" value="保 存" />
			</div>
		</form:form>
		<div class="right fr">
			<div class="right_text" id="photoShowDiv" onclick="uploadImg(this);">
				<img id="photoShow" ondblclick="removeImg(this);" src="${ctxStatic}/images/terminal/scan.png">
				<p style="font-size:30px;">请将您的文件放置于扫描区</p>
			</div>
		</div>
	</div>
	<div class="footer">中共天津市委组织部信息管理处&nbsp;&nbsp;&nbsp;&nbsp;天津市天房科技发展股份有限公司&nbsp;&nbsp;&nbsp;&nbsp;联合开发</div>
</body>
</html>