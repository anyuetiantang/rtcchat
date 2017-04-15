<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 添加好友与添加群组 -->
<div class="modal fade" id="friendDeleteOrGroupExitModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            	<ul class="nav nav-tabs">
				    <li class="active friendDeleteOrGroupExitTab">
				    	<input type="hidden" value="1">
				    	<a href="#friendDelete" data-toggle="tab">好友删除</a>
				    </li>
				    <li class="friendDeleteOrGroupExitTab">
				    	<input type="hidden" value="2">
				    	<a href="#groupExit" data-toggle="tab">退出群组</a>
				    </li>
				</ul>
            	<div id="myfriendDeleteOrGroupExit" class="tab-content">
				    <div class="tab-pane fade in active" id="friendDelete">
						 <ul id="friendDeleteList" style="list-style: none;margin: 0px;padding: 0px;">
						 	
						 </ul>
				    </div>
				    <div class="tab-pane fade" id="groupExit">
				    	<ul id="groupExitList" style="list-style: none;margin: 0px;padding: 0px;">
						 	
						</ul>
				    </div>
				</div>
            </div>
            <div class="modal-footer">
            	<button id="friendDeleteOrGroupExitSubmit" type="button" class="btn btn-primary">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    <script>
    $('#friendDeleteOrGroupExitModal').on('shown.bs.modal', function () {
    	if(myFriends){
    		loadFriendDeleteList(myFriends);
    		loadGroupExitList(groupsJoined);
    	}
    })
    
    	//用于根据user数组生成用户删除好友的搜索列表
    	function loadFriendDeleteList(userList){
    		var friendDeleteListHtml = "";
    		var projectPath = $("#projectPath").val();
    		for(var i=0;i<userList.length;i++){
    			friendDeleteListHtml += 	
					 "<li>"+
		                "<div class=\"checkbox checkbox-primary\">"+
		                    "<input type=\"radio\" name=\"friendDeleteList\" id=\"friendDeleteRadio" + userList[i].id +"\" value=\""+userList[i].id+"\">"+
		                    "<label for=\"friendDeleteRadio" + userList[i].id +"\" style=\"width:100%;border: 0px;text-align: left;\">"+
								"<div class=\"word-color btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\">"+
									"<img style=\"width: 25px;height: 25px;\" src=\""+ projectPath + userList[i].headImg +"\">&nbsp;"+
									userList[i].username + 
								"</div>"+
		                    "</label>"+
		                "</div>"+
					 "</li>";
					 
    		}
			$("#friendDeleteList").empty();
			$("#friendDeleteList").append(friendDeleteListHtml);
    	}
    
    	//用于根据group数组生成用户退出群组列表
    	function loadGroupExitList(groupsJoined){
    		var groupExitListHtml = "";
    		for(var i=0;i<groupsJoined.length;i++){
    			groupExitListHtml += 
					 "<li>"+
		                "<div class=\"checkbox checkbox-primary\">"+
		                    "<input type=\"radio\" name=\"groupExitList\" id=\"groupExitRadio" + groupsJoined[i].id +"\" value=\""+groupsJoined[i].id+"\">"+
		                    "<label for=\"groupExitRadio" + groupsJoined[i].id +"\" style=\"width:100%;border: 0px;text-align: left;\">"+
								"<div class=\"word-color btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\">"+
									groupsJoined[i].groupname + "("+groupsJoined[i].creator.username+")"
								"</div>"+
		                    "</label>"+
		                "</div>"+
					 "</li>";
    		}
			$("#groupExitList").empty();
			$("#groupExitList").append(groupExitListHtml);
    	}
    	
    	$("#friendDeleteOrGroupExitSubmit").click(function(){
    		var tag = $(".friendDeleteOrGroupExitTab.active")[0].childNodes[1].value;
    		if(tag == 1){
    			var targetId = parseInt($("input[name='friendAddList']:checked").val());
    			var myId = ${sessionScope.userid};
    			if(!targetId){
    				return;
    			}
    			
    			var socketData = {
    					type : "friendDeleteReq",
    					sourceId : myId,
    					targetId : targetId
    			}
    			var socketDataStr = JSON.stringify(socketData);
    			sendMessage(socketDataStr);
    		}else if(tag == 2){
    			var groupId = parseInt($("input[name='groupExitList']:checked").val());
    			var myId = ${sessionScope.userid};
    			if(!groupId){
    				return;
    			}
    			
    			var socketData = {
    					type : "groupUserExit",
    					sourceId : myId,
    					groupId : groupId
    			}
    			var socketDataStr = JSON.stringify(socketData);
    			sendMessage(socketDataStr);
    		}
    	});
    </script>
</div>
    