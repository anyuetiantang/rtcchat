<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 添加好友与添加群组 -->
<input type="hidden" id="groupId">
<div class="modal fade" id="groupUserAddOrDeleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            	<ul class="nav nav-tabs">
				    <li class="active groupUserAddOrDeleteTab">
				    	<input type="hidden" value="1">
				    	<a href="#groupUserAdd" data-toggle="tab">群组成员添加</a>
				    </li>
				    <li class="groupUserAddOrDeleteTab">
				    	<input type="hidden" value="2">
				    	<a href="#groupUserDelete" data-toggle="tab">群组成员踢出</a>
				    </li>
				</ul>
            	<div id="myGroupUserAddOrDelete" class="tab-content">
				    <div class="tab-pane fade in active" id="groupUserAdd">
						<div class="input-group col-md-12" style="margin-top:0px positon:relative"> 
					    	<input id="groupUserSearchInput" type="text" class="form-control"placeholder="请输入字段名" / >  
				            <span class="input-group-btn">
				               <button id="groupUserSearchButton" class="btn btn-info btn-search">search</button>  
				            </span> 
						 </div> 
						 <ul id="groupUserAddList" style="list-style: none;margin: 0px;padding: 0px;">
<!-- 							 <li> -->
<!-- 			                    <div class="checkbox checkbox-primary"> -->
<!-- 			                        <input type="radio" name="radio4" id="radio7" value="option1"> -->
<!-- 			                        <label for="radio7" style="width:100%;border: 0px;text-align: left;"> -->
<!-- 										<div class="word-color btn btn-default" style="width:100%;border: 0px;text-align: left;"> -->
<%-- 											<img style="width: 25px;height: 25px;" src="<%=request.getContextPath()%>/images/head.jpg">&nbsp; --%>
<!-- 											cy -->
<!-- 										</div> -->
<!-- 			                        </label> -->
<!-- 			                    </div> -->
<!-- 							 </li> -->
						 </ul>
				    </div>
				    <div class="tab-pane fade" id="groupUserDelete">
				    	<ul id="groupUserDeleteList" style="list-style: none;margin: 0px;padding: 0px;">
						</ul>
				    </div>
				</div>
            </div>
            <div class="modal-footer">
            	<button id="groupUserAddOrDeleteSubmit" type="button" class="btn btn-primary">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    <script>
    	$("#groupUserSearchButton").click(function(){
    		var targetStr = $("#groupUserSearchInput").val();
    		var data = {
    				targetStr : targetStr
    		}
    		var url = $("#projectPath").val()+"/user/fuzzySearch";
    		ajaxRequest(url,data,function(res){
    			var dataRes = JSON.parse(res);
    			if(dataRes.code === "200"){
    				console.log(dataRes.userList);
    				loadGroupUserAddList(dataRes.userList);
    			}else{
    				$("#groupUserAddList").empty();
    				alert(dataRes.msg);
    			}
    		});
    	});
    	
    	//用于根据user数组生成群组添加好友的搜索列表
    	function loadGroupUserAddList(userList){
    		var groupUserAddListHtml = "";
    		var projectPath = $("#projectPath").val();
    		for(var i=0;i<userList.length;i++){
    			groupUserAddListHtml += 	
					 "<li>"+
		                "<div class=\"checkbox checkbox-primary\">"+
		                    "<input type=\"radio\" name=\"groupUserAddList\" id=\"groupUserRadio" + userList[i].id +"\" value=\""+userList[i].id+"\">"+
		                    "<label for=\"groupUserRadio" + userList[i].id +"\" style=\"width:100%;border: 0px;text-align: left;\">"+
								"<div class=\"word-color btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\">"+
									"<img style=\"width: 25px;height: 25px;\" src=\""+ projectPath + userList[i].headImg +"\">&nbsp;"+
									userList[i].username + 
								"</div>"+
		                    "</label>"+
		                "</div>"+
					 "</li>";
					 
    		}
			$("#groupUserAddList").empty();
			$("#groupUserAddList").append(groupUserAddListHtml);
    	}
    	
    	//用于根据user数组生成群组删除好友的列表
       	function loadGroupUserDeleteList(userList){
    		var groupUserDeleteListHtml = "";
    		var projectPath = $("#projectPath").val();
    		for(var i=0;i<userList.length;i++){
    			groupUserDeleteListHtml += 	
					 "<li>"+
		                "<div class=\"checkbox checkbox-primary\">"+
		                    "<input type=\"radio\" name=\"groupUserDeleteList\" id=\"radio" + userList[i].id +"\" value=\""+userList[i].id+"\">"+
		                    "<label for=\"radio" + userList[i].id +"\" style=\"width:100%;border: 0px;text-align: left;\">"+
								"<div class=\"word-color btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\">"+
									"<img style=\"width: 25px;height: 25px;\" src=\""+ projectPath + userList[i].headImg +"\">&nbsp;"+
									userList[i].username + 
								"</div>"+
		                    "</label>"+
		                "</div>"+
					 "</li>";
					 
    		}
			$("#groupUserDeleteList").empty();
			$("#groupUserDeleteList").append(groupUserDeleteListHtml);
    	}
       	
    	//获取当前被点击的群组id，并且初始化要删除的群组成员列表
    	function getGroupSelectedId(groupId){
    		//获取群组Id
    		$("#groupId").val(groupId);
    		
    		var data = {
    				groupId : groupId
    		}
    		var url = $("#projectPath").val()+"/group/findGroupMembers";
    		ajaxRequest(url, data,function(res){
    			var dataRes = JSON.parse(res);
    			if(dataRes.code === "200"){
    				console.log(dataRes.members);
    				loadGroupUserDeleteList(dataRes.members);
    			}else{
    				alert(dataRes.msg);
    			}
    		})
    	}
    	
    	$("#groupUserAddOrDeleteSubmit").click(function(){
    		var tag = $(".groupUserAddOrDeleteTab.active")[0].childNodes[1].value;
    		if(tag == 1){
    			var targetId = parseInt($("input[name='groupUserAddList']:checked").val());
    			var myId = ${sessionScope.userid};
    			var groupId = $("#groupId").val();
    			if(!targetId){
    				return;
    			}else if(targetId == myId){
    				alert("不能邀请自己加入");
    				return;
    			}
    			
    			var socketData = {
    					type : "groupJoinReqFromGroup",
    					sourceId : myId,
    					targetId : targetId,
    					groupId : groupId
    			}
    			var socketDataStr = JSON.stringify(socketData);
    			sendMessage(socketDataStr);
    			
    		}else if(tag == 2){
    			var targetId = parseInt($("input[name='groupUserDeleteList']:checked").val());
    			var myId = ${sessionScope.userid};
    			var groupId = $("#groupId").val();
    			var socketData = {
    					type : "groupUserDelete",
    					sourceId : myId,
    					targetId : targetId,
    					groupId : groupId
    			}
    			var socketDataStr = JSON.stringify(socketData);
    			sendMessage(socketDataStr);
    			
    		}
    	});
    	
    </script>
</div>
    