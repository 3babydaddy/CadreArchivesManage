<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>jQuery-webcam-js</title>
<meta name="decorator" content="default" />
<script type="text/javascript"
	src="${ctxStatic}/jquery-webcam/jquery.webcam.js"></script>

<script type="text/javascript">
	var w = 320, h = 240; //摄像头配置,创建canvas
	var pos = 0, ctx = null, saveCB, image = [];
	var canvas = document.createElement("canvas");
	$("body").append(canvas);
	canvas.setAttribute('width', w);
	canvas.setAttribute('height', h);
	ctx = canvas.getContext("2d");
	image = ctx.getImageData(0, 0, w, h);
	$(document).ready(function() {
		$("#webcam").webcam({
			width : w,
			height : h,
			mode : "callback", //stream,save，回调模式,流模式和保存模式
			swffile : '${ctxStatic}' + '/jquery-webcam/jscam_canvas_only.swf',
			onTick : function(remain) {
				if (0 == remain) {
					$("#status").text("拍照成功!");
				} else {
					$("#status").text("倒计时" + remain + "秒钟...");
				}
			},
			onSave : function(data) { //保存图像
				var col = data.split(";");
				var img = image;
				for (var i = 0; i < w; i++) {
					var tmp = parseInt(col[i]);
					img.data[pos + 0] = (tmp >> 16) & 0xff;
					img.data[pos + 1] = (tmp >> 8) & 0xff;
					img.data[pos + 2] = tmp & 0xff;
					img.data[pos + 3] = 0xff;
					pos += 4;
				}
				if (pos >= 4 * w * h) {
					ctx.putImageData(img, 0, 0); //转换图像数据，渲染canvas
					pos = 0;
					var i = new Image();
					i.src = canvas.toDataURL();
					$("#picView").empty();
					$(i).appendTo($("#picView"));
					// TODO: 图片上传
					/* var imagedata = canvas.toDataURL().substring(22); //上传给后台的图片数据
					$.post('${ctx}/', {
						imgBase64Str : imagedata
						},function (data){
							
					}); */
				}
			},
			onCapture : function() { //捕获图像
				webcam.save();
			},
			debug : function(type, string) { //控制台信息
				//console.log(type + ": " + string);
			},
			onLoad : function() { //flash 加载完毕执行
				//console.log('加载完毕！')
				var cams = webcam.getCameraList();
				for ( var i in cams) {
					$("body").append("<p>" + cams[i] + "</p>");
				}
			}
		});

		$(".play").click(function() {
			webcam.capture(5); //拍照，参数5是倒计时
		});
		$(".play-x").click(function() {
			webcam.capture(); //拍照，参数5是倒计时
		});
	});
</script>
<style type="text/css">
#webcam>object {
	border: 1px solid #ccc;
}
iframe {
	height:90% !important;
}
</style>

</head>
<body>
	<div id="webcam"></div>
	<button class="play">5s拍照</button>
	<button class="play-x">拍照</button>
	<div id="status">倒计时</div>
	<div id="picView"
		style="width: 304px; height: 240px; border: 5px solid #ccc; padding: 4px; background-color: #ccc;">
</body>

</html>