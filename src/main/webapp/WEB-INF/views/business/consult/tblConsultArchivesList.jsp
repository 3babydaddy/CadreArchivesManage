<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查阅档案管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			//全选或全取消
			$("#selected").click(function(){
				var flag = document.getElementById("selected").checked;
				var $tbr = $('table tbody input');  
				if(flag){
					for(var i = 0; i < $tbr.length; i++){
						$tbr[i].checked = true;
					}
				}else{
					for(var i = 0; i < $tbr.length; i++){
						$tbr[i].checked = false;
					}
				}
			})
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function delData(){
			var idStr = "";
			var rows = getRowData();
			if(rows.length == 0){
				alertx("请选择记录");
				return;
			}
			for(var i = 0; i < rows.length; i++){
				var mainId = rows[i].value.slice(0, rows[i].value.indexOf(','));
				if(idStr == ""){
					idStr = mainId; 
				}else{
					idStr += "," + mainId; 
				}
			}
			window.location.href = "${ctx}/consult/tblConsultArchives/delete?idStr="+idStr;
		}
		
		function editData(){
			var rows = getRowData();
			if(rows.length != 1){
				alertx("请选择一条记录");
				return;
			}
			var mainId = rows[0].value.slice(0, rows[0].value.indexOf(','));
			window.location.href = "${ctx}/consult/tblConsultArchives/form?id="+mainId;
		}
		
		//送审
		function censorship(){
			var idStr = "";
			var rows = getRowData();
			if(rows.length == 0){
				alertx("请选择记录");
				return;
			}
			for(var i = 0; i < rows.length; i++){
				var mainId = rows[i].value.slice(0, rows[i].value.indexOf(','));
				var status = rows[i].value.slice(rows[i].value.indexOf(',')+1);
				if(status == '1'){
					if(idStr == ""){
						idStr = mainId; 
					}else{
						idStr += "," + mainId; 
					}
				}else{
					alertx("请选择未审核的数据！！！");
					return;
				}
			}
			
			$.ajax({
                type: "post",
                url: "${ctx}/consult/tblConsultArchives/censorship",
                data: {'idStr':idStr},
                //dataType: "json",
                success: function (data) {
                    if(data.flag == 'success'){
                    	alertx(data.msg);
                    	//提示管理员有新的数据需要审核
            			<shiro:hasRole name="admin">
            			 	window.parent.alertTip('查阅管理有新的需要审核的数据', '/consult/tblConsultArchives/list');
            	        </shiro:hasRole>
                        window.location.reload();
                    }else{
                    	alertx(data.msg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    //alertx("error！");
                }
            });
		}
		//审核借阅数据
		function auditData(){
			var idStr = "";
			var status = "";
			var rows = getRowData();
			if(rows.length == 0){
				alertx("请选择记录");
				return;
			}
			for(var i = 0; i < rows.length; i++){
				var mainId = rows[i].value.slice(0, rows[i].value.indexOf(','));
				var status = rows[i].value.slice(rows[i].value.indexOf(',')+1);
				if(status == '2'){
					if(idStr == ""){
						idStr = mainId; 
					}else{
						idStr += "," + mainId; 
					}
				}else{
					alertx("请选择审核中的数据！！！");
					return;
				}
			}
			
			top.$.jBox.open("<div style='margin: 15px 0 0 48px;'>"+
								"<span class='jbox-icon jbox-icon-question' style='position: absolute; top: 55px; left: 15px; width: 32px; height: 32px;'></span>"+
								"<span>是否确定审核通过！！！</span></div>", 
				"系统提示", 350,  126,
			    { buttons:{"是":true,"否":false},
					submit:function(v, h, f){
						if(v){
							//审核通过
							status = "3";
						}else{
							//审核不通过
							status = "1";
						}
						window.location.href = "${ctx}/consult/tblConsultArchives/auditData?idStr="+idStr+"&status="+status;
					}
			    }
			);
		}
		
		function getRowData(){
			return $("table tbody input[type='checkbox']:checked");
		}
		
		function setNull(){
			$("input[type='text']").each(function(){
				$(this).val("");
			})
			$("input[type='hidden']").each(function(){
				$(this).val("");
			})
			$("select").val("");
			//$("select").each(function(){
			//	$(this).select2("val","");
			//})
		}
		
	</script>
	<style type="text/css">
		.table th, .table td{
			text-align : center;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">查阅档案列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tblConsultArchives" action="${ctx}/consult/tblConsultArchives/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>查阅日期：</label>
				<input name="startBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblConsultArchives.startBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					至
				<input name="endBorrowDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tblConsultArchives.endBorrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>查阅单位：</label>
				<sys:treeselect url="/sys/dict/treeDataPop" id="consultUnit" name="consultUnit" allowClear="true" value="${tblConsultArchives.consultUnit}" 
									labelName="consultUnitName" labelValue="${tblConsultArchives.consultUnitName}" title="单位列表"></sys:treeselect>
			</li>
			<li><label>何人档案：</label>
				<form:input path="tarStr" htmlEscape="false" maxlength="64" style="width:190px;" class="input-medium"/>
			</li>
			<li><label>查阅人：</label>
				<form:input path="perStr" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<div style="float:right;">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
			</div>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<div id="toolbar">
	    <ul class="nav nav-pills">
	        <li><a <a href="${ctx}/consult/tblConsultArchives/form"><i class="icon-plus"></i>&nbsp;新增</a></li>
	        <li><a onclick="editData();"><i class="icon-edit"></i>&nbsp;编辑</a></li>
	        <li><a onclick="delData();"><i class="icon-remove"></i>&nbsp;删除</a></li>
	        <shiro:hasAnyRoles  name="admin,user">
	        	<li><a onclick="censorship();"><i class=" icon-share"></i>&nbsp;送审</a></li>
	        </shiro:hasAnyRoles >
	        <shiro:hasRole name="admin">
	        	<li><a onclick="auditData();"><i class=" icon-legal"></i>&nbsp;审核</a></li>
	        </shiro:hasRole>
	    </ul>
	</div>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input id="selected" type="checkbox" /></th> 
				<th>查阅日期</th>
				<th>查阅单位</th>
				<th>何人档案</th>
				<th>查阅内容</th>
				<th>查阅理由</th>
				<th>查阅人</th>
				<td>状态</td>
				<th>备注信息</th>
				<shiro:hasPermission name="consult:tblConsultArchives:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tblConsultArchives">
			<tr>
				<td>
					<input type="checkbox" value="${tblConsultArchives.id},${tblConsultArchives.status}"/>
				</td>
				<td><a href="${ctx}/consult/tblConsultArchives/form?id=${tblConsultArchives.id}"></a>
					<fmt:formatDate value="${tblConsultArchives.borrowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tblConsultArchives.consultUnitName}
				</td>
				<td>
					${tblConsultArchives.tarStr}
				</td>
				<td>
					${tblConsultArchives.content}
				</td>
				<td>
					${tblConsultArchives.reason}
				</td>
				<td>
					${tblConsultArchives.perStr}
				</td>
				<td>
					${fns:getDictLabel(tblConsultArchives.status, 'audit_status', '')}
				</td>
				<td>
					${tblConsultArchives.remarks}
				</td>
				<shiro:hasPermission name="consult:tblConsultArchives:edit"><td>
    				<a href="${ctx}/consult/tblConsultArchives/form?id=${tblConsultArchives.id}">修改</a>
					<a href="${ctx}/consult/tblConsultArchives/delete?id=${tblConsultArchives.id}" onclick="return confirmx('确认要删除该查阅档案吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>