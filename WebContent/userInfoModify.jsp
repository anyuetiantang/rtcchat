<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 个人信息修改 -->
<div class="modal fade" id="userInfoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            	<ul class="nav nav-tabs">
				    <li class="active userInfoTab">
				    	<input type="hidden" value="1">
				    	<a href="#headImgChange" data-toggle="tab">头像更改</a>
				    </li>
				    <li class="userInfoTab">
				    	<input type="hidden" value="2">
				    	<a href="#infoChange" data-toggle="tab">个人信息修改</a>
				    </li>
				    <li class="userInfoTab">
				    	<input type="hidden" value="3">
				    	<a href="#passwordChange" data-toggle="tab">密码修改</a>
				    </li>
				</ul>
            	<div id="myUserInfoContent" class="tab-content">
				    <div class="tab-pane fade in active" id="headImgChange">
						<form id="headImgForm" method="post" enctype="multipart/form-data" action="<%=request.getContextPath() %>/user/headImgChange">
							<div class="form-group">
                    			<input id="imageUpload" class="file" type="file">
                			</div>
						</form>
				    </div>
				    <div class="tab-pane fade" id="infoChange">
				  		<form role="form">
							<div class="form-group">
		            			<label for="usernameChange">用户名：</label>
		            			<input type="text" class="form-control" id="usernameChange" name="usernameChange" value="${sessionScope.username }" placeholder="username" required autofocus />
						  	</div>
						  	<div class="form-group">
		            			<label for="contactChange">联系方式：</label>
		            			<input type="text" class="form-control" id="contactChange" name="contactChange" value="${sessionScope.contact }" placeholder="contact" required />
						  	</div>
						</form>
				    </div>
				    <div class="tab-pane fade" id="passwordChange">
				    	<form role="form">
						  	<div class="form-group">
		            			<label for="passwordOld">原密码</label>
		            			<input type="password" class="form-control" id="passwordOld" name="passwordOld" placeholder="old password" required autofocus />
						  	</div>
						  	<div class="form-group">
		            			<label for="passwordNew">新密码：</label>
								<input type="password" class="form-control" id="passwordNew" name="passwordNew"  placeholder="new password" required />				  
							</div>
						  	<div class="form-group">
		            			<label for="passwordNewConfirm">确认新密码：</label>
								<input type="password" class="form-control" id="passwordNewConfirm" name="passwordNewConfirm"  placeholder="password confirmed" required />				 
							</div>
						</form>
				    </div>
				</div>
            </div>
            <div class="modal-footer">
            	<button id="userInfoSubmit" type="button" class="btn btn-primary" disabled="disabled">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    <script>
    //tab页切换
    $(".userInfoTab").click(function(){
    	var tag = $(this).children("input")[0].value;
    	if(tag == 1){
    		$("#userInfoSubmit").attr("disabled","disabled");
    	}else{
    		$("#userInfoSubmit").removeAttr("disabled");
    	}
    });
    
    //上传文件设置
    $("#imageUpload").fileinput({
	        language: 'zh', //设置语言
  	        allowedFileExtensions : ['jpg', 'png','gif'],//接收的文件后缀
  	        showUpload: true,//是否显示上传按钮
  	        showCaption: true,//是否显示标题
    });
    
    //文件上传由ajax控制
    $("#headImgForm").submit(function(event) {
    	var url = "<%=request.getContextPath() %>/user/headImgChange";
    	var file = $("#imageUpload")[0].files[0];
    	var filename = file.name;
    	var filesize = file.size;
        var formData = new FormData();
        formData.append("headImg",file);
        event.preventDefault(); //阻止当前提交事件，自行实现，否则会跳转
        console.log(formData.get("headImg"));//formData因其封装性，无法使用formData.headImg访问其属性

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
            		var headImg = "<%=request.getContextPath() %>"+dataRes.headImg;
            		$("#userInfoModal").modal("hide");
            		$("#myHeadImg").attr("src",headImg);
            	}else{
            		alert(dataRes.msg);
            	}
            },error : function(err) {
            	console.log(err);
            }
        });
    });
    
    $("#userInfoSubmit").click(function(){
    	var tag = $(".userInfoTab.active")[0].childNodes[1].value;
    	if(tag == 2){
    		var username = $("#usernameChange").val();
    		var contact = $("#contactChange").val();
    		
    		var url = "<%=request.getContextPath() %>/user/userInfoChange";
    		var data = {
    			username : username,
    			contact : contact
    		}
    		ajaxRequest(url,data,function(res){
    			var dataRes = JSON.parse(res);
    			if(dataRes.code === "200"){
    				$("#userInfoModal").modal("hide");
    				window.location.href = "<%=request.getContextPath() %>/start/loginSuccess?random="+${sessionScope.random}+"&username="+username;
    			}else{
    				alert(dataRes.msg);
    			}
    		});
    	}else if(tag == 3){
    		var passwordOld = $("#passwordOld").val();
    		var passwordNew = $("#passwordNew").val();
    		var passwordNewConfirm = $("#passwordNewConfirm").val();
    		
    		if(passwordNew !== passwordNewConfirm){
    			alert("新密码和确认密码不一致！");
    			return;
    		}
    		
    		var url = "<%=request.getContextPath() %>/user/passwordChange";
    		var data = {
    			passwordOld : hex_md5(passwordOld),
    			passwordNew : hex_md5(passwordNew)
    		}
    		ajaxRequest(url,data,function(res){
    			var dataRes = JSON.parse(res);
    			if(dataRes.code === "200"){
    				$("#userInfoModal").modal("hide");
    				alert("修改密码成功");
    			}else{
    				alert(dataRes.msg);
    			}
    		});
    	}
    });
    </script>
</div>
    