<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转入档案管理</title>
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
				$(obj).parent().parent().next().next().next().next().next().remove();
				$(obj).parent().parent().next().next().next().next().remove();
				$(obj).parent().parent().next().next().next().remove();
				$(obj).parent().parent().next().next().remove();
				$(obj).parent().parent().next().remove();
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
		
		var addRollIn  =  function() {
			if(validateDate()){
				top.$.jBox.confirm("<span style='font-size:24px;'>确定提交吗？</span>",'系统提示',function(v,h,f){
					if(v=='ok'){
						$.ajax({
        	                type: "POST",//方法类型
        	               // dataType: "json",//预期服务器返回的数据类型
        	                url: "${ctx}/rollin/tblRollIn/saveTerminal",//url
        	                data: $('#inputForm').serialize(),
        	                success: function (result) {
        	                	if(result){
        	                		top.$.jBox.tip("<span style='font-size:32px;'>保存成功</span>", "", {
        	            				width: 200,
        	            				persistent : true,
        	            				opacity : 0
        	            			});
        	                    	setTimeout(function(){window.location.href="${ctx}"}, 200);
        	                    };
        	                },
        	                error : function() {
        	                	alertx("异常！");
        	                }
        	            });
					}
				});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			}
		}
		
		function validateDate(){
			var character = $("#character").val();
			if(character == ""){
				alertx("批次的字不能为空！");
				return false;
			}
			var number = $("#number").val();
			if(number == ""){
				alertx("批次的号不能为空！");
				return false;
			}
			var rollInTime = $("#rollInTime").val();
			if(rollInTime == ""){
				alertx("转入日期不能为空！");
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
			var beforeUnitId = $("#beforeUnitId").val();
			if(beforeUnitId == ""){
				alertx("原存档单位不能为空！");
				return false;
			}
			var saveUnitId = $("#saveUnitId").val();
			if(saveUnitId == ""){
				alertx("现存档单位不能为空！");
				return false;
			}
			for(var i = 0; i < tblRollInPersonsRowIdx; i++){
				var name = $("#tblRollInPersonsList"+i+"_name").val()
				if(name == ""){
					alertx("转入人员的姓名不能为空！");
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
		
		html,#rowFluid1,#formInfo,#photoShowDiv{
			height: 100%
		}
		.overflow{
			height: 485px;
		}
		
		a,th,label{
			font-size : 150%;
		}
		span{
			font-size:30px;
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
		input[readonly]{
			background-color: white;
		}
	</style>
</head>
<body>
	<div class="header">
		<a href="${ctx}"><img src="${ctxStatic}/images/terminal/goback.png"></a>
		<img src="${ctxStatic}/images/terminal/top2.png" style="width:100%;">
	</div>
	<div class="content">
		<form:form id="inputForm" modelAttribute="tblRollIn" action="${ctx}/rollin/tblRollIn/saveTerminal" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<div style="display:none;">
				<sys:message content="${message}" />
			</div>
			<div class="left fl">
					<div class="search">
						<table cellpadding="5" border="0" cellspacing="0">
							<colgroup>
						 		<col width="200"/>
						 		<col width="160"/>
						 		<col width="200"/>
						 		<col width="160"/>
						 	</colgroup>
						 	<tr>
						 		<td style="text-align:right;">
									<span>转入批次：</span>
						 		</td>
						 		<td style="text-align:left;" colspan="3">
						 			<input class="input_1" style="width:80px;" id="character" name="character" value="${tblRollIn.character}" type="text"/><span>字</span>
						 			<input class="input_1" style="width:80px;" id="number" name="number" value="${tblRollIn.number}" type="text"/><span>号</span>
						 		</td>
						 	</tr>
						 	<tr style="height:5px;"></tr>
							<tr>
								<td style="text-align:right;">
									<span>转入时间：</span>
								</td>
								<td style="text-align:left;">
									<input id="rollInTime" name="rollInTime" type="text" readonly="readonly" maxlength="20" class="input_1 Wdate"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
								</td>
								<td style="text-align:right;">
									<span>经办人：</span>
								</td>
								<td style="text-align:left;">
									<input class="input_1" id="operator" name="operator" style="width:400px;" value="${tblRollIn.operator}" type="text"/>
								</td>
							</tr>
							<tr style="height:5px;"></tr>
							<tr>
								<td style="text-align:right;">
									<span>接收人：</span>
								</td>
								<td style="text-align:left;">
									<input class="input_1" id="recipient" name="recipient" value="${tblRollIn.recipient}" type="text"/>
								</td>
								<td style="text-align:right;">
									<span>原存档单位：</span>
								</td>
								<td style="text-align:left;">
									<sys:treeselect3 url="/sys/dict/treeDataPop" id="beforeUnit" name="beforeUnit" allowClear="true" value="${tblRollIn.beforeUnit}" 
																	labelName="beforeUnitName" labelValue="${tblRollIn.beforeUnitName}" title="单位列表"></sys:treeselect3>
								</td>
							</tr>
							<tr style="height:5px;"></tr>
							<tr>
								<td style="text-align:right;">
									<span>原存档电话：</span>
								</td>
								<td style="text-align:left;">
									<input class="input_1" id="beforeUnitTel" name="beforeUnitTel" value="${tblRollIn.beforeUnitTel}" type="text"/>
								</td>
								<td style="text-align:right;">
									<span>现存档单位：</span>
								</td>
								<td style="text-align:left;">
									<sys:treeselect3 url="/sys/dict/treeDataPop" id="saveUnit" name="saveUnit" allowClear="true" value="${tblRollIn.saveUnit}" 
																labelName="saveUnitName" labelValue="${tblRollIn.saveUnitName}" title="单位列表"></sys:treeselect3>
								</td>
							</tr>
						</table>
					</div>
				<div class="overflow">
					<div class="object">
						<div class="obj_head">
							<div class="title fl">相关信息</div>
							<div class="add fr">
								<a href="javascript:void(0)"  onclick="addRow('#tblRollInPersonsList', tblRollInPersonsRowIdx, tblRollInPersonsTpl);tblRollInPersonsRowIdx = tblRollInPersonsRowIdx + 1;" ><img src="${ctxStatic}/images/terminal/add.png"> 新增</a>
							</div>
							<div class="clear"></div>
						</div>
						<div id="add_content_obj">
							<div class="obj_con">
								<table id="contentTable" class="">
									<colgroup>
										<col width="94%"/>
								 		<col width="4%"/>
							 		</colgroup>
									<tbody id="tblRollInPersonsList">
									</tbody>
								</table>
								<script type="text/template" id="tblRollInPersonsTpl">//<!--
									<tr id="tblRollInPersonsList{{idx}}">
										<td class="hide">
											<input id="tblRollInPersonsList{{idx}}_id" name="tblRollInPersonsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
											<input id="tblRollInPersonsList{{idx}}_delFlag" name="tblRollInPersonsList[{{idx}}].delFlag" type="hidden" value="0"/>
										</td>
										<td>
											<span class="fl"><label style="margin-left:120px;">姓名：</label>
											<input id="tblRollInPersonsList{{idx}}_name" name="tblRollInPersonsList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input3"/></span>
										</td>
										<td rowspan="5" class="text-center" style="background:#00000017;height:280px;" width="2">
											{{#delBtn}}<span class="close" onclick="delRow(this, '#tblRollInPersonsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
										</td>
									</tr><tr>
										<td><label>正本：</label> 	
											<input id="tblRollInPersonsList{{idx}}_originalNo" style="width:150px;" name="tblRollInPersonsList[{{idx}}].originalNo" type="text" value="{{row.originalNo}}" maxlength="11" class="input3  digits"/>
											<label>卷&nbsp;</label>
											<label>副本：</label>
											<input id="tblRollInPersonsList{{idx}}_viceNo" style="width:150px;" name="tblRollInPersonsList[{{idx}}].viceNo" type="text" value="{{row.viceNo}}" maxlength="11" class="input3  digits"/>
											<label>卷&nbsp;</label>
											<label>档案材料：</label>
											<input id="tblRollInPersonsList{{idx}}_filesNo" style="width:150px;" name="tblRollInPersonsList[{{idx}}].filesNo" type="text" value="{{row.filesNo}}" maxlength="11" class="input3  digits"/>
											<label>卷</label>
										</td>
									</tr><tr>
										<td>
											<span class="fl"><label>工作单位及职务：</label>
											<input id="tblRollInPersonsList{{idx}}_duty" name="tblRollInPersonsList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="64" class="input3"/></span>
										</td>
									</tr><tr>
										<td >
											<span class="fl"><label style="margin-left:73px;">转入原因：</label>
											<input id="tblRollInPersonsList{{idx}}_reasonContent" name="tblRollInPersonsList[{{idx}}].reasonContent" type="text" value="{{row.reasonContent}}" maxlength="64" class="input3"/></span>
										</td>
									</tr><tr>
										<td>
											<span class="fl"><label style="margin-left:120px;">备注：</label>
											<input id="tblRollInPersonsList{{idx}}_remarks" name="tblRollInPersonsList[{{idx}}].remarks" type="text" value="{{row.remarks}}" maxlength="128" class="input3"/></span>
										</td>
									</tr><tr style="height:25px;"><td colspan="2"></td></tr>
											
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
					</div>
				</div>
				<input type="hidden" name="rollApproveAttachment" id="approveAttachmentId"/>
				<input type="submit" class="save_btn fr" onclick="addRollIn()" value="保 存" />
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