<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="shiro-tag" uri="http://shiro.apache.org/tags" %>

<html>
<head>
<title>${fns:getConfig('productName')}</title>
<meta name="decorator" content="blank" />
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

body {
	font-family: "微软雅黑";
	width: 100%
}

a {
	text-decoration: none;
}

ul li {
	list-style: none;
}

#main .container-fluid {
	padding: 0 4px 0 6px;
}

#header {
	width: 100%;
	background-color: #e20001;
	height: 60px;
/* 	overflow: hidden; */
/* 滤镜可使1920分屏正常显示背景图大小，IE下会导致某些功能无法正常使用 */
/*     background-size:cover; */
/*     filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${ctxStatic}/images/new_head_bg.png',sizingMethod='scale'); */
}

#tit {
	width: 50%;
}

.logo {
	margin-left: -17px;
	width: auto;
}

.head-content {
	font-size: 15px;
	color: #fff;
	vertical-align: middle;
	font-weight: bold;
	display: block;
	height: 30px;
	line-height: 30px;
}

.zaixian{
	font-size: 15px;
	vertical-align: middle;
	color: #fff;
	display: inline-block;
	font-weight: bold;
}

.head-right {
	float: right;
	height: 60px;
	line-height:60px;
	vertical-align: middle;
	margin-right: 3%;
	padding-right:13px;
}

.head-right a {
	display: inline-block;
	width: 22px;
	height: 23px;
	margin-left: 20px;
	vertical-align: middle;
	background-image: url(${ctxStatic}/images/spirt_icon.png);
	background-repeat: no-repeat;
}

.zhuye {
	background-position: -16px -7px;
}

.help {
	background-position: -52px -7px;
}

.loginout {
	background-position: -90px -7px;
}

#footer {
	width: 100%;
	background-image: url(${ctxStatic}/images/foot_bg.jpg);
	background-repeat: repeat-x;
	height: 39px;
	line-height: 39px;
	/* 滤镜可使1920分屏正常显示背景图大小，IE下会导致某些功能无法正常使用 */
/* 	background-size:cover; */
/* 	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${ctxStatic}/images/foot_bg.jpg',sizingMethod='scale'); */
}

.copyright {
	margin: 0 auto;
	text-align: center;
	color: white;
	font-size: 14px;
	font-family:楷体_GB2312;
}

#title {
	display: none;
}

.accordion-body {
	background-color: #E9ECF1;
}
.btn-group a{
	background-image: none;
}
.dropdown-menu{
	text-align: center;
}
.dropdown-menu .divider{
	margin: 6px 1px;
}
.dropdown-menu li a{
	display: inline-block;
	padding:0;
	margin: 0;
}
.u0{
            font-family: 华文行楷;
            font-weight: 400;
            font-style: normal;
            font-size: 72px;
            color: white;
            text-align: left;
            margin-top: 20px;
            padding-left:10px;
            display: inline-block;
    }
    .u0 p span{
            font-size: 45px;
    }
@media screen and (max-width: 1200px) and (min-width: 1140px){
	.head-right {
		float: right;
		height: 63px;
		line-height: 97px;
		vertical-align: middle;
		margin-right: 3%;
	}
}
@media screen and (max-width: 1140px){
	.head-right {
		float: right;
		height: 54px;
		line-height: 78px;
		vertical-align: middle;
		margin-right: 0%;
	}
}
.header-text {
	font-family: 华文行楷;
	font-weight: 400;
	font-style: normal;
	font-size: 50px;
	color: white;
	text-align: center;
	margin-top: 25px;
	white-space: nowrap;
}
	
</style>
<script type="text/javascript">
	$(function() {
		//ajax缓存清除
		$.ajaxSetup({ cache: false });
		// 绑定菜单单击事件
		$("#menu a.menu").click(function(){
			// 一级菜单焦点
			$("#menu li.menu").removeClass("active");
			$(this).parent().addClass("active");
			// 左侧区域隐藏
			if ($(this).attr("target") == "mainFrame"){
				//TODO: 暂时不隐藏
				//$("#left,#openClose").hide();
				wSizeWidth();
				$("#mainFrame").show();
				return true;
			}
			// 左侧区域显示
			$("#left,#openClose").show();
			if(!$("#openClose").hasClass("close")){
				$("#openClose").click();
			}
			// 显示二级菜单
			var menuId = "#menu-" + $(this).attr("data-id");
			if ($(menuId).length > 0){
				$("#left .accordion").hide();
				$(menuId).show();
				// 初始化点击第一个二级菜单
				if (!$(menuId + " .accordion-body:first").hasClass('in')){
					$(menuId + " .accordion-heading:first a").click();
				}
				if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")){
					$(menuId + " .accordion-body a:first i").click();
				}
				// 初始化点击第一个三级菜单
				$(menuId + " .accordion-body li:first li:first a:first i").click();
				//debugger;
			}else{
				// 获取二级菜单数据
				$.get($(this).attr("data-href"), function(data){
					if (data.indexOf("id=\"loginForm\"") != -1){
						alert('未登录或登录超时。请重新登录，谢谢！');
						top.location = "${ctx}";
						return false;
					}
					//debugger;
					$("#left .accordion").hide();
					$("#left").append(data);
					// 链接去掉虚框
					$(menuId + " a").bind("focus",function() {
						if(this.blur) {this.blur()};
					});
					// 二级标题
					$(menuId + " .accordion-heading a").click(function(){
						$(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
						if(!$($(this).attr('data-href')).hasClass('in')){
							$(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
						}
					});
					// 二级内容
					$(menuId + " .accordion-body a").click(function(){
						$(menuId + " li").removeClass("active");
						$(menuId + " li i").removeClass("icon-white");
						$(this).parent().addClass("active");
						$(this).children("i").addClass("icon-white");
					});
					// 展现三级
					$(menuId + " .accordion-inner a").click(function(){
						var href = $(this).attr("data-href");
						if($(href).length > 0){
							$(href).toggle().parent().toggle();
							return false;
						}
					});
					// 默认选中第一个菜单
					$(menuId + " .accordion-body a:first i").click();
					$(menuId + " .accordion-body li:first li:first a:first i").click();
				});
			}
			// 大小宽度调整
			wSizeWidth();
			return false;
		});
		// 初始化点击第一个一级菜单
		$("#menu a.menu:first span").click();
		// 鼠标移动到边界自动弹出左侧菜单
		$("#openClose").mouseover(function(){
			if($(this).hasClass("open")){
				$(this).click();
			}
		});
		//点击任何地方在线交流都收回下拉菜单
		$("#main").bind("click",function(){
			$('.dropdown-menu').css('display','none');
		})
		$("iframe").load(function(){
			$(this).contents().click(function () {
	            $('.dropdown-menu').trigger('click');
	        });
		})
	});
	
	//提示管理员有新的数据需要审核
    <shiro:hasRole name="admin">
    	//setInterval(function(){
    	setTimeout(function(){
    		//$.jBox.closeMessager();
			//系统提供的不好用
    		var len = $(".jbox-close").length;
			for(var s = 0; s < len; s++){
				$(".jbox-close")[s].click();
			}
			//防止弹窗没有完全关闭，就执行下面的js
			setTimeout(function(){
				$.ajax({
		            type: "post",
		            url: "${ctx}/sys/noticeToDo/queryNoticeData",
		            //data: {},
		            //dataType: "json",
		            success: function (data) {
		            	for(var i = 0; i < data.length; i++){
		            		$.jBox.messager('<div style="margin: 18px 0 0 48px;"><span class="jbox-icon jbox-icon-info" style="position: absolute; top: 55px; left: 15px; width: 32px; height: 32px;"></span>'
	            					+'<a href="${ctx}/'+data[i].url+'" target="mainFrame" ><font style="font-size:120%;">'+data[i].msg+'</font></a></div>', '提示', 0, 
	            					{ height: 110,
	            					  closed: function () { //手动关闭回调函数
	            						  var tem = $(".jbox-close").length;
            							  for(var i = 0; i < tem; i++){
            			            		  var height = 110 * (tem-i-1);
            			            		  $(".jbox-container")[i].style.marginBottom = height+"px";
            			            	  }
	            					  }
	           					});
		            	}
		            	//手动计算弹窗的位置
		            	var num = data.length
		            	for(var j = 0; j < num; j++){
		            		var height = 110 * (num-j-1);
		            		$(".jbox-container")[j].style.marginBottom = height+"px";
		            	}
		            },
		            error: function (XMLHttpRequest, textStatus, errorThrown) {
		                //alertx("error！");
		            }
		        });
			}, 1000);
		}, 19000);
	</shiro:hasRole>
</script>
</head>
<body>
	<div id="main">
		<div id="header">
            <div style=" display:  inline-block; margin-top:  20px;">
				<span class="header-text">${fns:getConfig('loginHeaderSystem')}</span>
            </div>
    		<div class="head-right">
				<div style="height: 45px">
				    <span style="color: white;font-size: 17px;font-family: 华文行楷;">当前用户：</span>
				    <span style="color: white;font-size: 17px;font-family: 华文行楷;">${fns:getUser().showname}</span>
                          <h2 class="zaixian" id="onlineNumber"></h2>
					<a href="${ctx}/" title="主页" class="zhuye"></a>
					<a href="${ctx}/logout" class="loginout" title="登出" ></a>
				</div>
			</div>
			<div id="title">
				<div id="dropdown">
					
					<ul id="menu" class="nav" style="*white-space: nowrap; float: none;">
						<c:set var="firstMenu" value="true" />
						<c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
							<c:if test="${menu.showFlag eq '1'}">
								<li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
										<a id="parentMenu" class="menu" href="javascript:" data-href="${ctx}/sys/menu/tree?parentId=${menu.id}&number=${fns:getCurrentTimeMillis()}" data-id="${menu.id}"><span>${menu.name}</span></a>
								</li>
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
				<div id="left"></div>
				<div id="openClose" class="close">&nbsp;</div>
				<div id="right">
					<iframe id="mainFrame" name="mainFrame" src="" style="overflow: visible;" scrolling="yes" frameborder="no" width="100%" height="650">
					</iframe>
				</div>
			</div>
		</div>
		<div id="footer">
			<p class="copyright">${fns:getConfig('indexFooter')} ${fns:getConfig('version')}</p>
		</div>
	</div>
	<script type="text/javascript">
		var leftWidth = 180; // 左侧窗口大小
		var tabTitleHeight = 30; // 页签的高度
		var htmlObj = $("html"), mainObj = $("#main");
		var headerObj = $("#header"), footerObj = $("#footer");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var minHeight = 500, minWidth = 980;
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":strs[1] < minWidth ? "auto" : "hidden", "overflow-y":strs[0] < minHeight ? "auto" : "hidden"});
			mainObj.css("width",strs[1] < minWidth ? minWidth - 10 : "auto");
			frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height());
			$("#openClose").height($("#openClose").height() - 5);
			wSizeWidth();
		}
		function wSizeWidth(){
			if (!$("#openClose").is(":hidden")){
				var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
				$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			}else{
				$("#right").width("100%");
			}
		}
	</script>


	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>