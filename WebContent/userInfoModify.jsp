<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 个人信息修改 -->
<div class="modal fade" id="userInfoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
<!--             <div class="modal-header"> -->
<!--                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button> -->
<!--               	<div class="word-color" data-toggle="modal" data-target="#userInfoModal" > -->
<%-- 					<img style="width: 30px;height: 30px;" src="<%=request.getContextPath()%>${sessionScope.headImg}">&nbsp;&nbsp;${sessionScope.username } --%>
<!-- 				</div> -->
<!--             </div> -->
            <div class="modal-body">
            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            	<ul class="nav nav-tabs">
				    <li class="active">
				    	<a href="#headImgChange" data-toggle="tab">头像更改</a>
				    </li>
				    <li>
				    	<a href="#infoChange" data-toggle="tab">个人信息修改</a>
				    </li>
				    <li>
				    	<a href="#passwordChange" data-toggle="tab">密码修改</a>
				    </li>
				</ul>
            	<div id="myTabContent" class="tab-content">
				    <div class="tab-pane fade in active" id="headImgChange">
    	            	<div id="picPreview"></div>
            			<input type="file" name="headImg" onchange="preview(this)">
				    </div>
				    <div class="tab-pane fade" id="infoChange">
				    	个人信息修改
				    </div>
				    <div class="tab-pane fade" id="passwordChange">
				    	密码修改
				    </div>
				</div>
            </div>
            <div class="modal-footer">
            	<button id="userInfoSubmit" type="button" class="btn btn-primary">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    <script>
    //预览上传图片的函数
    function preview(file)  {  
        var prevDiv = document.getElementById('picPreview');  
        if (file.files && file.files[0]){  
            var reader = new FileReader();  
            reader.onload = function(evt){ prevDiv.innerHTML = '<img style="width:150px;height:150px;" src="' + evt.target.result + '" />'; }    
            reader.readAsDataURL(file.files[0]);  
        } else { prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>'; }  
    }
    
    $("#userInfoSubmit").click(function(){
    	var url = "<%=request.getContextPath() %>/user/headModify"
    	ajaxRequest(url,null,function(res){
    		console.log(res);
    	});
    });
    </script>
</div>
    