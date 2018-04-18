<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${fns:getConfig('productName')}登录</title>
<meta name="decorator" content="blank" />
<link href="${ctxStatic}/modules/login/css/login.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	// 如果在框架或在对话框中，则弹出提示并跳转到首页
	var a = window.top.location.href;
	var b = location.href;
	if (self.frameElement && self.frameElement.tagName == "IFRAME"
			|| $('#left').length > 0 || $('.jbox').length > 0
			|| window.top.location.href != location.href) {
		alertx('未登录或登录超时。请重新登录，谢谢！');
		top.location = "${ctx}";
	}
	$(function() {
		var screenHeight = document.documentElement.clientHeight;
		var screenWidth = document.documentElement.clientWidth;
		var loginWrap = $(".loginWrap");
		loginWrap.width(screenWidth + "px");
		loginWrap.height(screenHeight + "px");
		
		$(".header-div").height($(".header").height() + "px");
		$(".header-div").css("display", "table-cell");
		$(".header-div").css("vertical-align", "middle");
		//debugger;
		$(".hr-login-bottom").find("p").css("position","relative");
		$(".hr-login-bottom").find("p").css("top","30%");
		

		$('#username').val("");
		$('#password').val("");
		if ('${not empty message}' && '${message}' != "") {
			alertx('${message}', closed);
		}
	});
	function check() {
		if ($('#username').val() == "" || $('#username').val() == null) {
			alertx("用户名不能为空");
			return false;
		}
		if ($('#psdword').val() == "" || $('#psdword').val() == null) {
			alertx("密码不能为空");
			return false;
		}
	}
</script>
</head>
<body>
	<div class="loginWrap">
		<div class="main">
			<div class="header">
				<div class="header-div">
					<span class="header-text">${fns:getConfig('loginHeaderSystem')}</span>
				</div>
			</div>
			<div class="content">
				<div class="contentInner">
					<div class="conLeft">
						<img alt="" src="${ctxStatic}/modules/login/img/u25.png">
					</div>
					<div class="conRight">
						<div class="conRightTop">
							<p class="loginTit">请登录您的账户</p>
							<hr>
						</div>
						<ul>
							<li>
								<form id="loginForm" action="${ctx}/login" method="post" class="loginForm" onsubmit="return check()">
									<div class="right-login">
										<div>
											<label>用户名</label> <input type="text" placeholder="请输入用户名" id="username" class="username" value="" name="username">
										</div>
										<div>
											<label>密 码</label> <input type="password" placeholder="请输入密码" id="psdword" class="psdword" value="" name="password">
										</div>
										<div class="groupBtns">
											<input type="submit" value="登  录" class="loginbtn" id="loginbtn">
										</div>
									</div>
								</form>
							</li>
						</ul>
					</div>
				</div>

			</div>
			<div class="hr-login-bottom">
				<!-- copyright版权信息 -->
				<p class="footer-p1">${fns:getConfig('loginFooter_line1')}</p>
				<p class="footer-p2">${fns:getConfig('loginFooter_line3')}</p>
			</div>
		</div>
	</div>
</body>
</html>