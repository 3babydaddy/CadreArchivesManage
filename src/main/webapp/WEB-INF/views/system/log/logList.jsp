<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function setNull(){
			$("input[type='text']").each(function(){
				$(this).val("");
			});
			$("input[type='hidden']").each(function(){
				$(this).val("");
			});
			$("select").val("");
			//$("select").each(function(){
			//	$(this).select2("val","");
			//})
		}
	</script>
	<style type="text/css">
		.table th, .table td{
			text-align : center;
			max-width: 310px;
			min-width: 60px;
		}
		.ul-form li label{
			width: 155px !important;
		}
		body {
			font-family: "微软雅黑";
			font-size:120%;
		}
		#btnCancel,#btnSubmit{
			font-size : 150%;
			white-space:nowrap;
			overflow:hidden;
		}
		a,th,td,label,select{
			font-size : 120%;
			overflow:hidden;
		}
		input[readonly]{
			background-color: white;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">系统日志列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="log" action="${ctx}/sys/log/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>日志标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<div style="float:right;margin-right:4px;">
				<li class="btns"><input id="btnSubmit" class="btn btn-lg btn-primary" type="submit" value="查询"/></li>
				<li class="btns"><input id="btnCancel" class="btn btn-primary" type="button" onclick="setNull();" value="重置"/></li>
			</div>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>操作内容</th>
				<th>类型</th>
				<th>操作路径</th>
				<th>提交方式</th>
				<th>操作者IP</th>
				<th>操作者</th>
				<th>操作时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysLog">
			<tr>
				<td><a href="${ctx}/syslog/sysLog/form?id=${sysLog.id}"></a>
					${sysLog.title}
				</td>
				<td>
					${fns:getDictLabel(sysLog.type, 'logType', '')}
				</td>
				<td title="${sysLog.requestUri}">
					${sysLog.requestUri}
				</td>
				<td>
					${sysLog.method}
				</td>
				<td>
					${sysLog.ip}
				</td>
				<td>
					${sysLog.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${sysLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>