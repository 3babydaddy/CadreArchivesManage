<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>jQuery-webcam-js</title>
<meta name="decorator" content="default" />
<script type="text/javascript"
	src="${ctxStatic}/jquery-webcam/jquery.webcam.js"></script>

<script type="text/javascript">
	var w = 400, h = 230; //摄像头配置,创建canvas
	var pos = 0, ctx = null, saveCB, image = [];
	var canvas = document.createElement("canvas");
	//$("body").append(canvas);
	canvas.setAttribute('width', w);
	canvas.setAttribute('height', h);
	ctx = canvas.getContext("2d");
	image = ctx.getImageData(0, 0, w, h);
	$(document).ready(function() {
		doInput("inputFile");
		$("#webcam").webcam({
			width : w,
			height : h,
			mode : "callback", //stream,save，回调模式,流模式和保存模式
			swffile : '${ctxStatic}' + '/jquery-webcam/jscam_canvas_only.swf',
			onTick : function() {
				/* if (0 == remain) {
					$("#status").text("拍照成功!");
				} else {
					$("#status").text("倒计时" + remain + "秒钟...");
				} */
				$("#status").text("拍照成功!");
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
// 					var i = new Image();
// 					i.src = canvas.toDataURL();
// 					$("#picView").empty();
// 					$(i).appendTo($("#picView"));
					// TODO: 图片上传
					/* var imagedata = canvas.toDataURL().substring(22); //上传给后台的图片数据
					$.post('${ctx}/', {
						imgBase64Str : imagedata
						},function (data){
							
					}); */
					$.post("${ctx}/terminal/createImg", {'imgBase64Str' : canvas.toDataURL("image/png")}, function (msg) {  
                        var picUrl = msg.path;
                        document.getElementById("photoShow").src = picUrl;
                    });  
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

		/* $(".play").click(function() {
			webcam.capture(5); //拍照，参数5是倒计时
		}); */
		$(".play-x").click(function() {
			webcam.capture(); //拍照
		});
	});
	
	//手动上传图片
	function doInput(id){
    	var inputObj = document.createElement('input');
    	$('.file').append(inputObj);
    	inputObj.addEventListener('change',readFile,false);
    	inputObj.type = 'file';
    	inputObj.accept = 'image/*';
    	inputObj.id = id;//inputFile
    	inputObj.click();
	}
	function readFile(){
	    var file = this.files[0];//获取input输入的图片
	    if(!/image\/\w+/.test(file.type)){
	        alert("请确保文件为图像类型");
	        return false;
	    }//判断是否图片，在移动端由于浏览器对调用file类型处理不同，虽然加了accept = 'image/*'，但是还要再次判断
	    var reader = new FileReader();
	    reader.readAsDataURL(file);//转化成base64数据类型
	    reader.onload = function(e){
	            drawToCanvas(this.result);
	        }
	    }
	
	function drawToCanvas(imgData){
	        var img = new Image;
	            img.src = imgData;
	            img.onload = function(){//必须onload之后再画
	                ctx.drawImage(img,0,0,w,h);
	                createImage();
	            }
	}
	
	function createImage(){
		$.post("${ctx}/terminal/createImg", { type: "data", imgBase64Str: canvas.toDataURL("image/png") }, function (msg) {  
            var picUrl = msg.path;
            //alert(picUrl);
           document.getElementById("photoShow").src = picUrl;
        });
	}

</script>
<style type="text/css">
#webcam>object {
	border: 1px solid #ccc;
}
iframe {
	height:90% !important;
}


.file {
    position: relative;
    display: inline-block;
    background: #D0EEFF;
    border: 1px solid #99D3F5;
    border-radius: 4px;
    padding: 4px 12px;
    overflow: hidden;
    color: #1E88C7;
    text-decoration: none;
    text-indent: 0;
    line-height: 18px;
}
.file input {
    position: absolute;
    font-size: 14px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    background: #AADFFD;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}
</style>

</head>
<body>

	<div class="row-fluid">
		<div id="webcam"></div>
	</div>
	<div class="row-fluid">
		<div class="span2 offset4">
			<button class="btn btn-lg btn-success play-x">拍照</button>
		</div>
		<div id="inputDiv"class="span4">
			<a href="javascript:;" class="file">选择文件
			</a>
		</div>
		<div id="status" class="span3 offset1"></div>
	</div>
	
	
	<!--<div id="picView"
		style="width: 304px; height: 240px; border: 5px solid #ccc; padding: 4px; background-color: #ccc;"> -->

</body>

</html>