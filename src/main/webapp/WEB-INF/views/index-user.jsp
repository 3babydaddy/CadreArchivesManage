<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="shiro-tag" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<title>${fns:getConfig('productName')}</title>
<meta name="decorator" content="blank" />
<link href="${ctxStatic}/modules/index/index-user.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
	$(function() {
		$("#mainFrame").hide();
		$("#navigation-div").find("div").on("click", function(e) {
			var url = e.currentTarget.getAttribute("data-url");
			$("iframe").attr("src", url);
			$("#mainFrame").show();
			$("#navigation-div").hide();
		});
	});
</script>
</head>
<body>
	<div id="main">
		<div id="header">
			<div style="display: inline-block; margin-top: 20px;">
				<span class="header-text">${fns:getConfig('loginHeaderSystem')}</span>
			</div>
			<div class="head-right">
				<div style="height: 45px">
					<span style="color: white; font-size: 17px; font-family: 华文行楷;">当前用户：</span> <span style="color: white; font-size: 17px; font-family: 华文行楷;">${fns:getUser().showname}</span>
					<h2 class="zaixian" id="onlineNumber"></h2>
					<a href="${ctx}/" title="主页" class="zhuye"></a> <a href="${ctx}/logout" class="loginout" title="登出"></a>
				</div>
			</div>
			<div id="title">
				<div id="dropdown">
					<ul id="menu" class="nav" style="*white-space: nowrap; float: none;">
						<c:set var="firstMenu" value="true" />
						<c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
							<c:if test="${menu.showFlag eq '1'}">
								<li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}"><a id="parentMenu" class="menu" href="javascript:" data-href="${ctx}/sys/menu/tree?parentId=${menu.id}&number=${fns:getCurrentTimeMillis()}" data-id="${menu.id}"><span>${menu.name}</span></a></li>
								<c:if test="${firstMenu}">
									<c:set var="firstMenuId" value="${menu.id}" />
								</c:if>
								<c:set var="firstMenu" value="false" />
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div id="content" class="row-fluid">
				<div id="right">
					<div id="navigation-div" class="row-fluid span4 offset4" style="top: 25%; position: absolute; height: 50%">
						<div class="row-fluid span6" id="col-1" data-url="${ctx}/terminal/consult">
							<span>查阅档案</span>
						</div>
						<div class="row-fluid span6" id="col-2" data-url="${ctx}/terminal/borrowArchives">
							<span>借阅档案</span>
						</div>
						<div class="row-fluid span6" id="col-3" data-url="${ctx}/terminal/consult">
							<span>转入档案</span>
						</div>
						<div class="row-fluid span6" id="col-4" data-url="${ctx}/terminal/consult">
							<span>零散材料</span>
						</div>
					</div>
					<!-- 页面展示区域 -->
					<div>
						<iframe id="mainFrame" name="mainFrame" src="" style="overflow: visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
					</div>
				</div>
			</div>
		</div>
		<div id="footer">
			<p class="copyright">${fns:getConfig('indexFooter')}${fns:getConfig('version')}</p>
		</div>
	</div>
	<script type="text/javascript">
		var leftWidth = 180; // 左侧窗口大小
		var tabTitleHeight = 30; // 页签的高度
		var htmlObj = $("html"), mainObj = $("#main");
		var headerObj = $("#header"), footerObj = $("#footer");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize() {
			var minHeight = 500, minWidth = 980;
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({
				"overflow-x" : strs[1] < minWidth ? "auto" : "hidden",
				"overflow-y" : strs[0] < minHeight ? "auto" : "hidden"
			});
			mainObj.css("width", strs[1] < minWidth ? minWidth - 10 : "auto");
			frameObj.height((strs[0] < minHeight ? minHeight : strs[0])
					- headerObj.height() - footerObj.height());
			$("#openClose").height($("#openClose").height() - 5);
			wSizeWidth();
		}
		function wSizeWidth() {
			if (!$("#openClose").is(":hidden")) {
				var leftWidth = ($("#left").width() < 0 ? 0 : $("#left")
						.width());
				$("#right").width(
						$("#content").width() - leftWidth
								- $("#openClose").width() - 5);
			} else {
				$("#right").width("100%");
			}
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>