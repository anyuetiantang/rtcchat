<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
   
<!-- 注册模块 -->
<div class="modal fade" id="myGroupModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
           		<ul class="nav nav-tabs">
				    <li class="active myGroupTab">
				    	<input type="hidden" value="1">
				    	<a href="#groupCreated" data-toggle="tab">创建群组</a>
				    </li>
				    <li class="myGroupTab">
				    	<input type="hidden" value="2">
				    	<a href="#groupDeleted" data-toggle="tab">删除群组</a>
				    </li>
				</ul>
                <div id="myGroupContent" class="tab-content">
				    <div class="tab-pane fade in active" id="groupCreated">
						<form id="groupCreatedForm" method="post" action="<%=request.getContextPath() %>/user/headImgChange">
							<div class="form-group">
                    			<input type="text" class="form-control" id="myGroupName" name="usernameChange" placeholder="group name" required autofocus />
                			</div>
						</form>
				    </div>
				    <div class="tab-pane fade" id="groupDeleted">
				    	<ul id="groupDeletedList" style="list-style: none;margin: 0px;padding: 0px;">
<!-- 							 <li> -->
<!-- 			                    <div class="checkbox checkbox-primary"> -->
<!-- 			                        <input type="radio" name="radio4" id="radio7" value="option1"> -->
<!-- 			                        <label for="radio7" style="width:100%;border: 0px;text-align: left;"> -->
<!-- 										<div class="word-color btn btn-default" style="width:100%;border: 0px;text-align: left;"> -->
<!-- 											friends -->
<!-- 										</div> -->
<!-- 			                        </label> -->
<!-- 			                    </div> -->
<!-- 							 </li> -->
						 </ul>
				    </div>
				</div>
            </div>
            <div class="modal-footer">
            	<button id="myGroupRequest" type="button" class="btn btn-primary">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    
    <script>
	
		function initMyGroupsToDelete(){
			var groupDeletedListHtml = "";
			var projectPath = $("#projectPath").val();
			for(var i=0;i<groupsCreated.length;i++){
				groupDeletedListHtml += 	
					 "<li>"+
		                "<div class=\"checkbox checkbox-primary\">"+
		                    "<input type=\"radio\" name=\"groupDeletedList\" id=\"groupDeletedRadio" + groupsCreated[i].id +"\" value=\""+groupsCreated[i].id+"\">"+
		                    "<label for=\"groupDeletedRadio" + groupsCreated[i].id +"\" style=\"width:100%;border: 0px;text-align: left;\">"+
								"<div class=\"word-color btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\">"+
									groupsCreated[i].groupname + 
								"</div>"+
		                    "</label>"+
		                "</div>"+
					 "</li>";
					 
			}
			$("#groupDeletedList").empty();
			$("#groupDeletedList").append(groupDeletedListHtml);
		}
    
    	$("#myGroupRequest").click(function(){
    		var tag = $(".myGroupTab.active")[0].childNodes[1].value;
    		if(tag == 1){
    			var groupName = $("#myGroupName").val();
    			if(!groupName){
    				alert("群组名称不能为空");
    				return;
    			}
    	     	var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
    	   		if(reg.test(groupName)){
    	   			alert("文件名不可包含汉字！"); 
    	   			return;
    	   		}  
    	   		
    	   		var url = "<%=request.getContextPath() %>/group/groupCreated";
    	   		var data = {
    	   				groupName : groupName
    	   		}
    	   		ajaxRequest(url,data,function(res){
    	   			var dataRes = JSON.parse(res);
    	   			if(dataRes.code === "200"){
    	   				$("#myGroupModal").modal("hide");
    	   				alert("创建成功");
    	   				getMyGroupsAndFriends(true,false,false,function(){
    	   					initMyGroups();
    	   				});
    	   			}else{
    	   				alert(dataRes.msg);
    	   			}
    	   		});
    			
    		}else if(tag == 2){
    			var groupId = parseInt($("input[name='groupDeletedList']:checked").val());
    			var myId = ${sessionScope.userid};
    			
    			var url = $("#projectPath").val()+"/group/delete";
    			var data = {
    					groupId : groupId
    			}
    			ajaxRequest(url,data,function(res){
        			var dataRes = JSON.parse(res);
        			if(dataRes.code === "200"){
        				getMyGroupsAndFriends(true,false,false,function(){
        					initMyGroups();
        					$("#myGroupModal").modal("hide");
        					alert("删除群组成功");
        				});
        			}else{
        				alert(dataRes.msg);
        			}
    			});
    			
    		}
    	});
    	
    </script>
</div>