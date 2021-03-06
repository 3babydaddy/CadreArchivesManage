<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查阅档案管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/modules/terminal/terminal-common.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${ctxStatic}/jSignature/jSignature.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
	        var h = parent.$("iframe").height();
			$("#photoShowDiv").css("height",h + "px");
			$(".img-polaroid").css("height",h-10);
			//初始化插件
	        $("#signature").jSignature();
		});
		
		//输出签名图片
	    function jSignatureTest(){
	        var $sigdiv = $("#signature");
//	        $sigdiv.jSignature() // inits the jSignature widget.
	        // after some doodling...
//	        $sigdiv.jSignature("reset") // clears the canvas and rerenders the decor on it.
	        //var datapair = $sigdiv.jSignature("getData", "svgbase64") 
	        var datapair = $sigdiv.jSignature("getData") 
	        console.log(datapair);
	        var i = new Image();
	        i.src = datapair;
	        //i.src = "data:" + datapair[0] + "," + datapair[1] 
	        //$(i).appendTo($("#image")) // append the image (SVG) to DOM.
	        
	        $.post('${ctx}/terminal/createImg/', {
	        	imgBase64Str : datapair
	        },function (data){
	        	if(data.result){
	        		var siginId = $("#siginId").val();
	        		var siginImg = siginId.replace('siginInput', 'siginImg');
	        		var siginName = siginId.replace('siginInput', 'siginName');
	        		var aline = siginId.replace('siginInput', 'aline');
	        		//隐藏签名的input
	        		window.parent.$("#"+siginId).hide();
	        		//给父页面的img赋图片路径
	        		window.parent.$("#"+siginImg)[0].src=data.path;
	        		//给父页面隐藏的实体类的字段赋值
	        		window.parent.$("#"+siginName).val(data.path);
	        		//展示签名的img标签
	        		window.parent.$("#"+siginImg).show();
	        		//展示签名的删除按钮
	        		window.parent.$("#"+aline).show();
	        		
	        		window.parent.window.jBox.close();
	        	}else{
	        		alertx("保存失败！！！");
	        	}
	        });
	    }
		
	    function reset(){
	        var $sigdiv = $("#signature");
	        $sigdiv.jSignature("reset");
	    }
	</script>
</head>
<body>
	<div id="signature" style="height: 98%;"></div>
	<input type="hidden" id="siginId" name="siginId" value="${siginId}" />
	<div align="center" border="false" style="position:center;" id="returnDiv">
   		<div class="btn-group">
		  <button type="button" style="width:120px;height:40px;font-size:20px;" class="btn btn-primary" onclick="jSignatureTest()">保存</button>
		  <button type="button" style="width:120px;height:40px;font-size:20px;margin:0 0 0 20px;" class="btn btn-primary" onclick="reset()">清空</button>
		</div>
   	</div>
</body>
</html>