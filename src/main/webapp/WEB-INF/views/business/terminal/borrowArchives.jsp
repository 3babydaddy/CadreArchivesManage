<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借阅档案管理</title>
	<meta name="decorator" content="default"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
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
				$(obj).parent().parent().removeClass("error");
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
			$.jBox("iframe:${ctx}/consult/tblConsultArchives/drowSiginByUser?siginId="+obj.id, {  
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
    		var siginName = siginId.replace('aline', 'siginName');
    		var siginInput = siginId.replace('aline', 'siginInput');
    		
    		//展示签名的input
    		$("#"+siginInput).show();
    		//清空签名img的路径
    		$("#"+siginImg)[0].src = '';
    		//清空隐藏得实体类字段的值
    		$("#"+siginName).val('');
    		//隐藏img标签
    		$("#"+siginImg).hide();
    		//隐藏img删除按钮
    		$("#"+siginId).hide();
		}
		
		function addBorrow() {
			if(validateDate()){
				top.$.jBox.confirm("<span style='font-size:24px;'>确定提交吗？</span>",'系统提示',function(v,h,f){
					if(v=='ok'){
						$.ajax({
        	                type: "POST",//方法类型
        	               // dataType: "json",//预期服务器返回的数据类型
        	                 url: "${ctx}/borrow/tblBorrowArchives/saveTerminal",//url
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
			var borrowDate = $("#borrowDate").val();
			if(borrowDate == ""){
				alertx("借阅日期不能为空！");
				return false;
			}
			var consultUnitId = $("#consultUnitId").val();
			if(consultUnitId == ""){
				alertx("接阅单位不能为空！");
				return false;
			}
			for(var i = 0; i < tblBorrowTargetRowIdx; i++){
				var tarName = $("#tblBorrowTargetList"+i+"_name").val()
				if(tarName == ""){
					alertx("借阅对象的姓名不能为空！");
					return false;
					break;
				}
			}
			for(var i = 0; i < tblBorrowPersonRowIdx; i++){
				var perName = $("#tblBorrowPersonList"+i+"_name").val()
				if(perName == ""){
					alertx("借阅人员的姓名不能为空！");
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
			width: 100%;
			height: 100%;
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
		input[readonly]{
			background-color: white;
		}
	</style>
</head>
<body>
	<div class="header">
		<a href="${ctx}"><img src="${ctxStatic}/images/terminal/goback.png"></a>
		<img src="${ctxStatic}/images/terminal/top1.png" style="width:100%;">
	</div>
	<div class="content">
		<form:form id="inputForm" modelAttribute="tblBorrowArchives" action="${ctx}/borrow/tblBorrowArchives/saveTerminal" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<div style="display:none;">
				<sys:message content="${message}" />
			</div>
			<div class="left fl">
				<div class="search">
					<div class="fl">
						<span class="fl" style="font-size:30px;" >借阅日期：</span>
						<input id="borrowDate" name="borrowDate" type="text" readonly="readonly" maxlength="20" class="input_1 Wdate"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					</div>
					<div class="fr">
						<span class="fl" style="font-size:30px;" >借阅单位：</span>
						<sys:treeselect3 url="/sys/dict/treeDataPop" id="consultUnit" name="consultUnit" allowClear="true" value="${tblConsultArchives.consultUnit}" 
															labelName="consultUnitName" labelValue="${tblBorrowArchives.consultUnitName}" title="单位列表"></sys:treeselect3>
					</div>
					<div class="clear"></div>
				</div>
				<div class="overflow">
					<div class="object">
						<div class="obj_head">
							<div class="title fl">借阅对象</div>
							<div class="add fr">
								<a href="javascript:void(0)"  onclick="addRow('#tblBorrowTargetList', tblBorrowTargetRowIdx, tblBorrowTargetTpl);tblBorrowTargetRowIdx = tblBorrowTargetRowIdx + 1;" ><img src="${ctxStatic}/images/terminal/add.png"> 新增</a>
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
									<tbody id="tblBorrowTargetList">
									</tbody>
								</table>
								<script type="text/template" id="tblBorrowTargetTpl">//<!--
									<tr id="tblBorrowTargetList{{idx}}">
										<td class="hide">
											<input id="tblBorrowTargetList{{idx}}_id" name="tblBorrowTargetList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
											<input id="tblBorrowTargetList{{idx}}_delFlag" name="tblBorrowTargetList[{{idx}}].delFlag" type="hidden" value="0"/>
										</td>
										<td>
											<span class="fr"><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名：</label>
											<input id="tblBorrowTargetList{{idx}}_name" name="tblBorrowTargetList[{{idx}}].name" type="text" value="{{row.name}}" class="input1"/></span>
										</td>
										<td>
											<span class="fr"><label>单位：</label>
											<sys:treeselect3 url="/sys/dict/treeDataPop" id="tblBorrowTargetList{{idx}}_unit" name="tblBorrowTargetList[{{idx}}].unit" allowClear="true" value="{{row.unit}}" 
												labelName="unitName" labelValue="{{row.unitName}}" title="单位列表"></sys:treeselect3>
										</td>
										<td rowspan="2" class="text-center" style="background:#00000017;" width="2">
											{{#delBtn}}<span class="close" onclick="delTarRow(this, '#tblBorrowTargetList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
										</td>
									</tr><tr>
										<td>
											<span class="fr"><label>职务：</label>
											<input id="tblBorrowTargetList{{idx}}_duty" name="tblBorrowTargetList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input1"/></span>
										</td>
										<td>
											<span class="fr"><label>政治面貌：</label>
											<input id="tblBorrowTargetList{{idx}}_politicalStatus" name="tblBorrowTargetList[{{idx}}].politicalStatus" value="{{row.politicalStatus}}" type="text" class="input3"/></span>
										</td>
									</tr><tr style="height:25px;"><td colspan="5"></td></tr>
											
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
					</div>
					<div class="object">
						<div class="obj_head">
							<div class="title fl">借阅人员</div>
							<div class="add fr">
								<a href="javascript:void(0)" onclick="addRow('#tblBorrowPersonList', tblBorrowPersonRowIdx, tblBorrowPersonTpl);tblBorrowPersonRowIdx = tblBorrowPersonRowIdx + 1;"><img src="${ctxStatic}/images/terminal/add.png"> 新增</a>
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
									<tbody id="tblBorrowPersonList">
									</tbody>
								</table>
								<script type="text/template" id="tblBorrowPersonTpl">//<!--
											<tr>
												<tr id="tblBorrowPersonList{{idx}}">
													<td class="hide">
														<input id="tblBorrowPersonList{{idx}}_id" name="tblBorrowPersonList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
														<input id="tblBorrowPersonList{{idx}}_delFlag" name="tblBorrowPersonList[{{idx}}].delFlag" type="hidden" value="0"/>
													</td>
													<td>
														<span class="fr"><label>姓名：</label>
														<input id="tblBorrowPersonList{{idx}}_name" name="tblBorrowPersonList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="64" class="input1"/></span>
													</td>
													<td>
														<span class="fr"><label>政治面貌：</label>
														<input id="tblBorrowPersonList{{idx}}_politicalStatus" name="tblBorrowPersonList[{{idx}}].politicalStatus" type="text" value="{{row.politicalStatus}}" maxlength="32" class="input3"/></span>
													</td>
													<td rowspan="4" colspan="2" style="text-align:center;border:1px solid #19181840;display:none;">
														<sys:upImg input="tblBorrowPersonList{{idx}}_photo"  type="files"  name="tblBorrowPersonList[{{idx}}].photo"  value="{{row.photo}}"  uploadPath="/file" selectMultiple="false" maxWidth="100" maxHeight="100" text="头像上传"/>
													</td>
													<td rowspan="3" class="text-center" style="background:#00000017;" width="2">
														{{#delBtn}}<span class="close" onclick="delPerRow(this, '#tblBorrowPersonList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
													</td>
												</tr><tr>
													<td>
														<span class="fr"><label>职务：</label>
														<input id="tblBorrowPersonList{{idx}}_duty" name="tblBorrowPersonList[{{idx}}].duty" type="text" value="{{row.duty}}" maxlength="32" class="input1"/></span>
													</td>
													<td>
														<span class="fr"><label>单位：</label>
														<sys:treeselect3 url="/sys/dict/treeDataPop" id="tblBorrowPersonList{{idx}}_unit" name="tblBorrowPersonList[{{idx}}].unit" allowClear="true" value="{{row.unit}}" 
															labelName="unitName" labelValue="{{row.unitName}}" title="单位列表"></sys:treeselect3>
													</td>
												</tr><tr>
													<td>
														<span class="fr"><label>联系电话：</label>
														<input id="tblBorrowPersonList{{idx}}_telphone" name="tblBorrowPersonList[{{idx}}].telphone" type="text" value="{{row.telphone}}" maxlength="11" class="input1"/></span>
													</td>
													<td>
														<div class="fr"><label>签字：</label>
															<input id="tblBorrowPersonList{{idx}}_siginInput" name="tblBorrowPersonList[{{idx}}].siginInput" type="text" onclick=siginOption(this); class="input3"/></span>
															<input id="tblBorrowPersonList{{idx}}_siginName" name="tblBorrowPersonList[{{idx}}].siginName" type="hidden"  value="{{row.siginName}}"  maxlength="120" class="input3"/>
														
															<img id="tblBorrowPersonList{{idx}}_siginImg" style="display:none;width:383px;" class="input3" src="{{row.siginName}}" />
															<a id="tblBorrowPersonList{{idx}}_aline" style="display:none;" onclick="delSigin(this)">&times;</a>
       							 						</div>
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
				<input type="button" class="save_btn fr" onclick="addBorrow();" value="保 存" />
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