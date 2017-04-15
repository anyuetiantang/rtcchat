<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 添加好友与添加群组 -->
<div class="modal fade" id="friendAddOrGroupJoinModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            	<ul class="nav nav-tabs">
				    <li class="active friendAddOrGroupJoinTab">
				    	<input type="hidden" value="1">
				    	<a href="#friendAdd" data-toggle="tab">好友添加</a>
				    </li>
				    <li class="friendAddOrGroupJoinTab">
				    	<input type="hidden" value="2">
				    	<a href="#groupJoin" data-toggle="tab">申请加入群组</a>
				    </li>
				</ul>
            	<div id="myFriendAddOrGroupJoin" class="tab-content">
				    <div class="tab-pane fade in active" id="friendAdd">
						<div class="input-group col-md-12" style="margin-top:0px positon:relative"> 
					    	<input id="friendSearchInput" type="text" class="form-control"placeholder="请输入字段名" / >  
				            <span class="input-group-btn">
				               <button id="friendSearchButton" class="btn btn-info btn-search">search</button>  
				            </span>  
						 </div> 
						 <ul id="friendAddList" style="list-style: none;margin: 0px;padding: 0px;">
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
				    <div class="tab-pane fade" id="groupJoin">
				    	 <div class="input-group col-md-12" style="margin-top:0px positon:relative"> 
					    	<input id="groupSearchInput" type="text" class="form-control"placeholder="请输入字段名" / >  
				            <span class="input-group-btn">
				               <button id="groupSearchButton" class="btn btn-info btn-search">search</button>  
				            </span>  
						 </div> 
						 <ul id="groupJoinList" style="list-style: none;margin: 0px;padding: 0px;">
<!-- 							 <li> -->
<!-- 			                    <div class="checkbox checkbox-primary"> -->
<!-- 			                        <input type="radio" name="radio4" id="radio7" value="option1"> -->
<!-- 			                        <label for="radio7" style="width:100%;border: 0px;text-align: left;"> -->
<!-- 										<div class="word-color btn btn-default" style="width:100%;border: 0px;text-align: left;"> -->
<!-- 											cy -->
<!-- 										</div> -->
<!-- 			                        </label> -->
<!-- 			                    </div> -->
<!-- 							 </li> -->
						 </ul>
				    </div>
				</div>
            </div>
            <div class="modal-footer">
            	<button id="friendAddOrGroupJoinSubmit" type="button" class="btn btn-primary">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    <script>
    	$("#friendSearchButton").click(function(){
    		var targetStr = $("#friendSearchInput").val();
    		var data = {
    				targetStr : targetStr
    		}
    		var url = $("#projectPath").val()+"/user/fuzzySearch";
    		ajaxRequest(url,data,function(res){
    			var dataRes = JSON.parse(res);
    			if(dataRes.code === "200"){
    				console.log(dataRes.userList);
    				loadFriendAddList(dataRes.userList);
    			}else{
    				$("#friendAddList").empty();
    				alert(dataRes.msg);
    			}
    		});
    	});
    	
    	$("#groupSearchButton").click(function(){
    		var targetStr = $("#groupSearchInput").val();
    		var data = {
    				targetStr : targetStr
    		}
    		var url = $("#projectPath").val()+"/group/fuzzySearch";
    		ajaxRequest(url,data,function(res){
    			var dataRes = JSON.parse(res);
    			if(dataRes.code === "200"){
    				console.log(dataRes.groupList);
    				loadGroupJoinList(dataRes.groupList);
    			}else{
    				$("#groupJoinList").empty();
    				alert(dataRes.msg);
    			}
    		});
    	});
    	
    	//用于根据user数组生成用户添加好友的搜索列表
    	function loadFriendAddList(userList){
    		var friendAddListHtml = "";
    		var projectPath = $("#projectPath").val();
    		for(var i=0;i<userList.length;i++){
    			friendAddListHtml += 	
					 "<li>"+
		                "<div class=\"checkbox checkbox-primary\">"+
		                    "<input type=\"radio\" name=\"friendAddList\" id=\"friendAddRadio" + userList[i].id +"\" value=\""+userList[i].id+"\">"+
		                    "<label for=\"friendAddRadio" + userList[i].id +"\" style=\"width:100%;border: 0px;text-align: left;\">"+
								"<div class=\"word-color btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\">"+
									"<img style=\"width: 25px;height: 25px;\" src=\""+ projectPath + userList[i].headImg +"\">&nbsp;"+
									userList[i].username + 
								"</div>"+
		                    "</label>"+
		                "</div>"+
					 "</li>";
					 
    		}
			$("#friendAddList").empty();
			$("#friendAddList").append(friendAddListHtml);
    	}
    	
    	//用于根据group数组生成用户加入群组的搜索列表
    	function loadGroupJoinList(groupList){
    		var groupJoinListHtml = "";
    		for(var i=0;i<groupList.length;i++){
    			groupJoinListHtml +=
					"<li>"+
		                "<div class=\"checkbox checkbox-primary\">"+
		                    "<input type=\"radio\" name=\"groupJoinList\" id=\"groupJoinRadio"+groupList[i].id+"\" value=\""+groupList[i].id+"\">"+
		                    "<label for=\"groupJoinRadio"+groupList[i].id+"\" style=\"width:100%;border: 0px;text-align: left;\">"+
								"<div class=\"word-color btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\">"+
									groupList[i].groupname+"("+groupList[i].creator.username+")"+
								"</div>"+
		                    "</label>"+
		                "</div>"+
					 "</li>";
    		}
			$("#groupJoinList").empty();
			$("#groupJoinList").append(groupJoinListHtml);
    	}
    	
    	$("#friendAddOrGroupJoinSubmit").click(function(){
    		var tag = $(".friendAddOrGroupJoinTab.active")[0].childNodes[1].value;
    		if(tag == 1){
    			var targetId = parseInt($("input[name='friendAddList']:checked").val());
    			var myId = ${sessionScope.userid};
    			if(!targetId){
    				return;
    			}else if(targetId == myId){
    				alert("不能添加自己为好友");
    				return;
    			}
    			
    			var socketData = {
    					type : "friendAddReq",
    					sourceId : myId,
    					targetId : targetId
    			}
    			var socketDataStr = JSON.stringify(socketData);
    			sendMessage(socketDataStr);
    			
    		}else if(tag == 2){
    			var groupId = parseInt($("input[name='groupJoinList']:checked").val());
    			var myId = ${sessionScope.userid};
    			var socketData = {
    					type : "groupJoinReqFromUser",
    					sourceId : myId,
    					groupId : groupId
    			}
    			var socketDataStr = JSON.stringify(socketData);
    			sendMessage(socketDataStr);
    		}
    	});
    </script>
</div>
    