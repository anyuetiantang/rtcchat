<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 个人信息修改 -->
<div class="modal fade" id="fileRepertoryModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            	<ul class="nav nav-tabs">
				    <li class="active fileRepertoryTab">
				    	<input type="hidden" value="1">
				    	<a href="#fileRepertoryUpload" data-toggle="tab">文件上传</a>
				    </li>
				    <li class="fileRepertoryTab">
				    	<input type="hidden" value="2">
				    	<a href="#fileRepertoryDownload" data-toggle="tab">文件下载</a>
				    </li>
				</ul>
            	<div id="fileRepertoryContent" class="tab-content">
				    <div class="tab-pane fade in active" id="fileRepertoryUpload">
						<form id="fileUploadForm" method="post" enctype="multipart/form-data" action="<%=request.getContextPath() %>/file/fileUpload">
							<div class="form-group">
                    			<input id="fileUpload" class="file" type="file">
                			</div>
						</form>
				    </div>
				    <div class="tab-pane fade" id="fileRepertoryDownload">
				    </div>
				</div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    <script>
    //上传文件设置
    $("#fileUpload").fileinput({
	        language: 'zh', //设置语言
  	        allowedFileExtensions : ['zip'],//接收的文件后缀
  	        showUpload: true,//是否显示上传按钮
  	        showCaption: true,//是否显示标题
    });
    
    //文件上传由ajax控制
    $("#fileUploadForm").submit(function(event) {
    	event.preventDefault(); //阻止当前提交事件，自行实现，否则会跳转
    	if($("#chatTargetType").val() == 0){
    		alert("还未选中传送对象!");
    		return;
    	}
    	
    	var url = "<%=request.getContextPath() %>/file/fileUpload";
    	var file = $("#fileUpload")[0].files[0];
    	var filename = file.name;
    	var filesize = file.size;
        var formData = new FormData();
        formData.append("file",file);
        formData.append("type",$("#chatTargetType").val());
        formData.append("sourceId",myId);
        formData.append("targetId",$("#chatTargetId").val());
//         console.log(formData.get("file"));//formData因其封装性，无法使用formData.headImg访问其属性

        //如果有汉字则退出
     	var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
   		if(reg.test(filename)){
   			alert("文件名不可包含汉字！"); 
   			return;
   		}   
   		
   		if(filesize > 10*1024*1000){
   			alert("图片大小不能超过10M");
   			return;
   		}
        
        $.ajax({
            url : url,
            type : 'POST',
            data : formData,
            processData : false,  // 告诉jQuery不要去处理发送的数据
            contentType : false, // 告诉jQuery不要去设置Content-Type请求头
            success : function(res) {
            	var dataRes = JSON.parse(res);
            	if(dataRes.code === "200"){
            		$("#fileRepertoryModal").modal("hide");
            		var fileId = dataRes.fileId;
            		sendFileLink(fileId);
            		alert("上传文件成功");
            	}else{
            		alert(dataRes.msg);
            	}
            },error : function(err) {
            	console.log(err);
            }
        });
    });
    
    //发送文件链接
    function sendFileLink(fileId){
    	var type = $("#chatTargetType").val();
    	var targetId = $("#chatTargetId").val();
    	if(type == "user"){
			var socketData = {
					type : "fileUser",
					fileId : fileId,
					sourceId : myId,
					targetId : targetId
			}
    	}else if(type == "group"){
			var socketData = {
					type : "fileGroup",
					fileId : fileId,
					sourceId : myId,
					targetId : targetId
			}
    	}else{
    		return;
    	}
		var socketDataStr = JSON.stringify(socketData);
		sendMessage(socketDataStr);
    }
    
    //获取文件信息
   	function getFiles(){
   		var sourceId = myId;
   		var targetId = $("#chatTargetId").val();
   		var type = $("#chatTargetType").val()
   		
   		var url = "<%=request.getContextPath() %>/file/getFiles";
   		var data = {
   				sourceId : sourceId,
   				targetId : targetId,
   				type : type
   		}
   		ajaxRequest(url,data,function(res){
        	var dataRes = JSON.parse(res);
        	if(dataRes.code === "200"){
        		console.log(dataRes.fileList);
        		loadDownloadFile(dataRes.fileList);
        	}else{
        		alert(dataRes.msg);
        	}
   		});
   	}
   	
   	function loadDownloadFile(fileList){
   		var projectPath = $("#projectPath").val();
   		var fileListHtml = "";
   		for(var i=0;i<fileList.length;i++){
   			fileListHtml += 
   				"<a class=\"btn btn-primary\" target=\"view_window\""+
   				"href=\""+projectPath + fileList[i].filepath+"\">"+fileList[i].originName+"("+fileList[i].belongToUser.username+")</a>";
   		}
   		if(fileListHtml.length == 0){
   			fileListHtml += "<div class=\"word-color\" style=\"text-align: center;\">没有文件</div>";
   		}
   		$("#fileRepertoryDownload").empty();
   		$("#fileRepertoryDownload").append(fileListHtml);
   	}
    </script>
</div>
    