<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="input" type="java.lang.String" required="true" description="输入框"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="输入框"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="输入框"%>
<%@ attribute name="isBorder" type="java.lang.String" required="false" description="是否控制边框颜色"%>
<%@ attribute name="type" type="java.lang.String" required="true" description="files、images、flash、thumb"%>
<%@ attribute name="uploadPath" type="java.lang.String" required="true" description="打开文件管理的上传路径"%>
<%@ attribute name="selectMultiple" type="java.lang.Boolean" required="false" description="是否允许多选"%>
<%@ attribute name="readonly" type="java.lang.Boolean" required="false" description="是否查看模式"%>
<%@ attribute name="maxWidth" type="java.lang.String" required="false" description="最大宽度"%>
<%@ attribute name="maxHeight" type="java.lang.String" required="false" description="最大高度"%>
<%@ attribute name="text" type="java.lang.String" required="false" description="文字"%>
<style type="text/css">
    /*a  upload */
    .a-upload {
        padding: 4px 10px;
        height: 20px;
        line-height: 20px;
        position: relative;
        cursor: pointer;
        color: white;
        background: #e35827;;
        border: 1px solid #ddd;
        border-radius: 4px;
        overflow: hidden;
        display: inline-block;
        *display: inline;
        *zoom: 1
    }

    .a-upload  input {
        position: absolute;
        font-size: 100px;
        right: 0;
        top: 0;
        opacity: 0;
        filter: alpha(opacity=0);
        cursor: pointer
    }

    .a-upload:hover {
        color: white;
        background: #e35827;
        border-color: #e35827;
        text-decoration: none
    }

</style>
<script type="text/javascript">
    function ${input}FinderOpen(){

    }
    function ${input}SelectAction(obj){
    	var dqElement=$(obj);
    	var dqInput=$(obj).parent().prev(".${input}")
    	var dqOl=dqInput.prev("ol");
    	var dqA=$(obj).parent()
          $.ajaxFileUpload({
              url: '${ctx}/file/upFile', //用于文件上传的服务器端请求地址
              secureuri: false, //一般设置为false
              fileElementId: dqElement, //文件上传空间的id属性  <input type="file" id="file" name="file" />
              dataType: 'json', //返回值类型 一般设置为json
              type:"post",
              success: function(res){

                  if(res.success){
                      //返回路径
                      var returnPath=res.returnPath;
                      //展示名
                      var showName=res.showName;
                      dqInput.val(returnPath);
                      if('${isBorder}'==1){
                          dqA.parents('.benjieCon').css("border",
                          "2px solid red");
                      }
                      ${input}Preview(dqInput,dqOl,dqA);
                  }else{
                      var message = res.message;
                      alert(message);
                  }
              },
              error:function(data, status, e){

                flag=0;
                alert("上传文件失败！");
              }
          });
         return false;


    }
    function ${input}ThumbSelectAction(fileUrl, data, allFiles){
        var url="", files=ckfinderAPI.getSelectedFiles();
        for(var i=0; i<files.length; i++){
            url += files[i].getThumbnailUrl();
            if (i<files.length-1) url+="|";
        }//<c:if test="${selectMultiple}">
        $("#${input}").val($("#${input}").val()+($("#${input}").val(url)==""?url:"|"+url));//</c:if><c:if test="${!selectMultiple}">
        $("#${input}").val(url);//</c:if>
        ${input}Preview();
        //top.$.jBox.close();
    }
    function ${input}Callback(api){
        ckfinderAPI = api;
    }
    function ${input}Del(obj){
        var url = $(obj).prev().attr("url");
        var dqInput=$(obj).parent().parent().next();
        var dqOl=$(obj).parent().parent();
        var dqA=$(obj).parent().parent().next().next();
        if('${isBorder}'==1){
            dqA.parents('.benjieCon').css("border",
            "2px solid red");
        }
        dqInput.val(dqInput.val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));
        ${input}Preview(dqInput,dqOl,dqA);
    }
    function ${input}DelAll(){
        $("#${input}").val("");
        ${input}Preview();
    }
    function ${input}Preview(dqInput,dqOl,dqA){
    	var li, urls =dqInput.val().split("|");
        dqOl.children().remove();
        for (var i=0; i<urls.length; i++){
            if (urls[i]!=""){//<c:if test="${type eq 'thumb' || type eq 'images'}">
                li = "<li><img src=\""+urls[i]+"\" url=\""+urls[i]+"\" style=\"max-width:${empty maxWidth ? 200 : maxWidth}px;max-height:${empty maxHeight ? 200 : maxHeight}px;_height:${empty maxHeight ? 200 : maxHeight}px;border:0;padding:3px;\">";//</c:if><c:if test="${type ne 'thumb' && type ne 'images'}">
                li = "<li><a title='"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"' href=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";//</c:if>
                li += "&nbsp;<c:if test="${!readonly}"><a href=\"javascript:\" onclick=\"${input}Del(this);\">×</a></c:if></li>";
                dqOl.append(li);
                dqOl.children("li").children("a").css({
                    "max-width" : "80%",
                    "display" : "inline-block",
                    "overflow" : "hidden",
                    "vertical-align" : "middle",
                    "text-overflow" : "ellipsis",
                    "white-space" : "nowrap"
                });
                dqA.hide();
            }
        }
        if (dqOl.text() == ""){
        	dqA.show();
        }
    }
    //删除附件内容
    function delInputFile(input){
    	//debugger;
    	var divFile=$('.'+input+'AllFile').children(".a-upload").find("input");
    	var dqInput=divFile.parent().prev(".${input}")
        var dqOl=dqInput.prev("ol");
        var dqA=divFile.parent();
        ${input}Preview(dqInput,dqOl,dqA);
    }
    $(function(){
    	var divFile=$('.${input}AllFile').children(".a-upload").find("input");
    	var dqInput=divFile.parent().prev(".${input}")
        var dqOl=dqInput.prev("ol");
        var dqA=divFile.parent();
        ${input}Preview(dqInput,dqOl,dqA);
    });
</script>
<div class="${input}AllFile">
    <ol id="${input}Preview" style="list-style-type:none;margin:0px;"></ol>
    <input class="${input}"  type="hidden" name="${name}"  maxlength="255" value="${value}"/>
	<a href="javascript:;" class="a-upload" id="${input}ShowType" >
	    <input type="file" name="${input}zipFile" id="${input}zipFile"   onchange="${input}SelectAction(this)" />${text }
	</a>
</div>
