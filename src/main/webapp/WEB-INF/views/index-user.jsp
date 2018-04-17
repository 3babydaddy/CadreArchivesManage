<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="shiro-tag" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<title>${fns:getConfig('productName')}</title>
<meta name="decorator" content="blank" />
<link href="${ctxStatic}/modules/index/index-user.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	$(function(){
		debugger;
		var h = $("#header").height();
		var w = $("#header").width();
		$("video").css("width",w);
		$("video").css("height",h);
	});
</script>

</head>
<body>
	<div id="main">
		<div id="header">
			<video autoplay="autoplay" loop="loop" style="object-fit: fill;">
				<source src="${ctxStatic}/mp4/bg.mp4" type="video/mp4" />
			</video>
			<img src="${ctxStatic}/mp4/logo.png">
		</div>
		<div id="footer">
			<div class="container-fluid">
				<div id="content" class="row-fluid">
					<div class="row-fluid span3" id="" data-url="${ctx}/terminal/consult">
						<img class="row-fluid-img" src="${ctxStatic}/mp4/1.png">
						<img src="${ctxStatic}/mp4/shadow.png">
					</div>
					<div class="row-fluid span3" id="" data-url="${ctx}/terminal/consult">
						<img class="row-fluid-img" src="${ctxStatic}/mp4/2.png">
						<img src="${ctxStatic}/mp4/shadow.png">
					</div>
					<div class="row-fluid span3" id="" data-url="${ctx}/terminal/consult">
						<img class="row-fluid-img" src="${ctxStatic}/mp4/3.png">
						<img src="${ctxStatic}/mp4/shadow.png">
					</div>
					<div class="row-fluid span3" id="" data-url="${ctx}/terminal/consult">
						<img class="row-fluid-img" src="${ctxStatic}/mp4/4.png">
						<img src="${ctxStatic}/mp4/shadow.png">
					</div>
				</div>
			</div>
			<p class="copyright">${fns:getConfig('indexFooter')}${fns:getConfig('version')}</p>
		</div>
	</div>
</body>
</html>