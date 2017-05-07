var groupsCreated = null;
var myFriends = null;
var groupsJoined = null;
var myId = $("#myId").val();
var myUsername = $("#myUsername").val();
var projectPath = $("#projectPath").val();
var groupSelectedId = null;

//获取用户的创建群组、好友以及加入群组，并且使用回调函数进行初始化
getMyGroupsAndFriends(true,true,true,function(){
	initMyGroups();
	initMyFriends();
	initMyGroupsJoined();
});

//初始化websocket
initWebSocket(projectPath,myId);
//初始化表情按钮
initFace();

//初始化表情
function initFace(){
	//添加表情按钮
	$("#sendText").emoji({
	    button: "#emojiBtn",
	    showTab: false,
	    animation: 'slide',
	    icons: [{
	        name: "QQ表情",
	        path: "../images/qqFace/",
	        maxNum: 91,
	        excludeNums: [41, 45, 54],
	        file: ".gif"
	    }]
	});
}

//将表情代码转换为表情
function transferFace(){
	$("#chatContentUl li").emojiParse({
	    icons: [{
	        path: "../images/qqFace/",
	        file: ".gif",
	        placeholder: "#em1_{alias}#"
	    }]
	});
}

//获取本用户好友、创建的群组和加入的群组
function getMyGroupsAndFriends(ifGetMyGroups,ifGetMyFriends,ifGetJoinedGroups,callback){
	var url = $("#projectPath").val()+"/user/findFriendsAndGroups";
	var data = {
		ifGetMyGroups : ifGetMyGroups,
		ifGetMyFriends : ifGetMyFriends,
		ifGetJoinedGroups : ifGetJoinedGroups
	}
	ajaxRequest(url,data,function(res){
		var dataRes = JSON.parse(res);
		if(dataRes.code === "200"){
			console.log(dataRes);
			if(ifGetMyGroups){
				groupsCreated = dataRes.groupsCreated;
			}
			if(ifGetMyFriends){
				myFriends = dataRes.myFriends;
			}
			if(ifGetJoinedGroups){
				groupsJoined = dataRes.groupsJoined;
			}
			
			callback();
		}else{
			alert(dataRes.msg);
		}
	});
}

//初始化我的群组列表
function initMyGroups(){
	var myGroupHtml = "";
	console.log(groupsCreated);
	for(var i=0;i<groupsCreated.length;i++){
		myGroupHtml += 	"<li class=\"containner accordion-group\">"+ 
							"<div class=\"accordion-heading\" style=\"width: 100%;\">"+
								"<button id=\"myGroupListButton"+groupsCreated[i].id+"\" class=\"btn btn-default word-color col-md-9 accordion-toggle\" " +
									"data-toggle=\"collapse\" data-parent=\"#myGroupList\" href=\"#groupCreatedMembers"+groupsCreated[i].id+"\" " +
									"ondblclick=\"setChatTarget('group',"+groupsCreated[i].id+",'"+groupsCreated[i].groupname+"("+myUsername+")')\" style=\"border: 0px;text-align: left;\">"+groupsCreated[i].groupname+"</button>"+
								"<span class=\"col-md-3\" style=\"margin-top: 5px;\">"+
									"<input type=\"hidden\" value=\""+groupsCreated[i].id+"\">"+
									"<button onclick=\"getGroupSelectedId("+groupsCreated[i].id+")\" data-toggle=\"modal\" data-target=\"#groupUserAddOrDeleteModal\" class=\"btn btn-default\" style=\"border: 0px;\"><span class=\"glyphicon glyphicon-tasks\"></span></button>"+
								"</span>"+
							"</div>"+
							"<div id=\"groupCreatedMembers"+groupsCreated[i].id+"\" class=\"accordion-body collapse\" style=\"height: 0px; \">"+
								"<div class=\"accordion-inner\">"+
								"<ul  style=\"list-style: none;margin: 0px;padding: 0px;\">";
		if(groupsCreated[i].members.length == 0){
			myGroupHtml +="<li>"+
								"<button class=\"btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\">"+
									"<div class=\"word-color\">"+
										"NULL" +
									"</div>"+
								"</button>"+
							"</li>";
		}
		for(var j=0;j<groupsCreated[i].members.length;j++){
			myGroupHtml += 			"<li>"+
										"<input type=\"hidden\" value=\""+groupsCreated[i].members[j].id+"\">"+
										"<button class=\"btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\">"+
											"<div class=\"word-color\">"+
												"<img style=\"width: 25px;height: 25px;\" src=\""+ projectPath + groupsCreated[i].members[j].headImg +"\">&nbsp;"+
												groupsCreated[i].members[j].username +
											"</div>"+
										"</button>"+
									"</li>";
		}
		
		myGroupHtml += 			"</ul>"+
								"</div>"+
							"</div>"+
						"</li>";
		
//		myGroupHtml += 	"<li class=\"containner\">"+ 
//							"<div style=\"width: 100%;\">"+
//								"<button class=\"btn btn-default word-color col-md-8\" style=\"border: 0px;text-align: left;\">"+groupsCreated[i].groupname+"</button>"+
//								"<span class=\"col-md-4\" style=\"margin-top: 5px;\">"+
//									"<input type=\"hidden\" value=\""+groupsCreated[i].id+"\">"+
//									"<button onclick=\"getGroupSelectedId("+groupsCreated[i].id+")\" data-toggle=\"modal\" data-target=\"#groupUserAddOrDeleteModal\" class=\"btn btn-default\" style=\"border: 0px;\"><span class=\"glyphicon glyphicon-tasks\"></span></button>"+
//								"</span>"+
//							"</div>"+
//						"</li>";
	}
	$("#myGroupList").empty();
	$("#myGroupList").append(myGroupHtml);
}

//初始化我的好友列表
function initMyFriends(){
	var myFriendHtml = "";
	for(var i=0;i<myFriends.length;i++){
		myFriendHtml += 	"<li class=\"containner accordion-group\">"+
								"<input type=\"hidden\" value=\""+myFriends[i].id+"\">"+
						     	"<button id=\"myFriendListButton"+myFriends[i].id+"\" class=\"btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\" " +
						     		"ondblclick=\"setChatTarget('user',"+myFriends[i].id+",'"+myFriends[i].username+"')\">"+
									"<div class=\"word-color\">"+
										"<img style=\"width: 25px;height: 25px;\" src=\""+ projectPath + myFriends[i].headImg +"\">&nbsp;"+
										myFriends[i].username +
									"</div>"+
								"</button>"+
							"</li>";
	}
	$("#myFriendListUl").empty();
	$("#myFriendListUl").append(myFriendHtml);
}

//初始化我加入的群组列表
function initMyGroupsJoined(){
	var myGroupJoinedHtml = "";
	for(var i=0;i<groupsJoined.length;i++){
		myGroupJoinedHtml += 	"<li class=\"containner\">"+ 
									"<div class=\"accordion-heading\" style=\"width: 100%;\">"+
										"<button id=\"myGroupListButton"+groupsJoined[i].id+"\" class=\"btn btn-default word-color col-md-12 accordion-toggle\" data-toggle=\"collapse\" " +
												"data-parent=\"#myJoinedGroupListUl\" href=\"#groupJoinedMembers"+groupsJoined[i].id+"\" " +
												"ondblclick=setChatTarget('group',"+groupsJoined[i].id+",'"+groupsJoined[i].groupname+"("+groupsJoined[i].creator.username+")') " +
												"style=\"border: 0px;text-align: left;\">"+
											groupsJoined[i].groupname+"("+groupsJoined[i].creator.username+")"+
										"</button>"+
									"</div>"+
									"<div id=\"groupJoinedMembers"+groupsJoined[i].id+"\" class=\"accordion-body collapse\" style=\"height: 0px; \">"+
										"<div class=\"accordion-inner\">"+
											"<ul  style=\"list-style: none;margin: 0px;padding: 0px;\">";
		
		
		for(var j=0;j<groupsJoined[i].members.length;j++){
			myGroupJoinedHtml +=			 			"<li>"+
													"<input type=\"hidden\" value=\""+groupsJoined[i].members[j].id+"\">"+
													"<button class=\"btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\">"+
														"<div class=\"word-color\">"+
															"<img style=\"width: 25px;height: 25px;\" src=\""+ projectPath + groupsJoined[i].members[j].headImg +"\">&nbsp;"+
															groupsJoined[i].members[j].username +
														"</div>"+
													"</button>"+
												"</li>";
		}
		
		myGroupJoinedHtml += 				"</ul>"+
										"</div>"+
									"</div>"+
								"</li>";
		
		
		
//		myGroupJoinedHtml += 	"<li class=\"containner\">"+ 
//									"<div style=\"width: 100%;\">"+
//										"<button class=\"btn btn-default word-color col-md-12\" style=\"border: 0px;text-align: left;\">"+
//											groupsJoined[i].groupname+"("+groupsJoined[i].creator.username+")"+
//										"</button>"+
//									"</div>"+
//								"</li>";
	}
	$("#myJoinedGroupListUl").empty();
	$("#myJoinedGroupListUl").append(myGroupJoinedHtml);
	
}

//textarea回车发送消息
function textareaKeyDown(){
	if(event.keyCode == 13){
		chatSendMessage();
	}
}

//点击按钮发送信息
function chatSendMessage(){
	var messageType = $("#chatTargetType").val();
	var targetId = parseInt($("#chatTargetId").val());
	var text = $("#sendText").val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g, "&quot;").replace(/'/g, "&#039;");
	var socketData=null;
	
	$("#sendText").val("");
	if(targetId == 0){
		alert("请选择对象");
		return;
	}
	
	if(!text){
		alert("发送信息不能为空！");
		return;
	}
	
	var tempHtml = "<li style=\"list-style: none;\">"+
						"<div class=\"word-color\">"+myUsername+
						" "+transferTime(new Date())+"</div>"+
						"<div>"+text+"</div>"+
					"</li>";
	$("#chatContentUl").append(tempHtml);
	transferFace();
	
	if(messageType == "user"){
		socketData = {
				type : "messageUser",
				sourceId : myId,
				targetId : targetId,
				text : text
			}
		var socketDataStr = JSON.stringify(socketData);
		sendMessage(socketDataStr);
	}else if(messageType === "group"){
		socketData = {
				type : "messageGroup",
				sourceId : myId,
				targetId : targetId,
				text : text
			}
		var socketDataStr = JSON.stringify(socketData);
		sendMessage(socketDataStr);
	}
}

//清空textarea数据
function chatTextClear(){
	$("#sendText").val("");
}

//设置聊天对象信息
function setChatTarget(type,id,name){
	$("#chatTargetName").text(name);
	$("#chatTargetType").val(type);
	$("#chatTargetId").val(id);
	
	var data = null;
	if(type === "user"){
		$("#myFriendListButton"+id).removeClass("hasMessage");
		data = {
				type : type,
				sourceId : id,
				targetId : myId
		}
	}else if(type === "group"){
		$("#myGroupListButton"+id).removeClass("hasMessage");
		data = {
				type : type,
				sourceId : myId,
				targetId : id
		}
	}
	$("#chatContentUl").empty();
	
	var url = $("#projectPath").val()+"/message/getMessage";
	ajaxRequest(url,data,function(res){
		var dataRes = JSON.parse(res);
		if(dataRes.code === "200"){
			console.log(dataRes);
			loadChatContent(dataRes.messageList);
		}else{
			alert(dataRes.msg);
		}
	});
}

//加载聊天信息
function loadChatContent(messageList){
	if(messageList == null || messageList.length == 0){
		return;
	}
	
	var chatContentHtml = "";
	for(var i=0;i<messageList.length;i++){
		chatContentHtml += 	"<li style=\"list-style: none;\">"+
								"<div class=\"word-color\">"+messageList[i].fromUser.username+
								" "+transferTime(messageList[i].sendTime)+"</div>"+
								"<div>"+messageList[i].text+"</div>"+
							"</li>";
	}
	$("#chatContentUl").empty();
	$("#chatContentUl").append(chatContentHtml);
	transferFace();
}

//获取历史消息
function getHistoryMessage(){
	var messageType = $("#chatTargetType").val();
	var targetId = parseInt($("#chatTargetId").val());
	var text = $("#sendText").val();
	
	$("#sendText").val("");
	if(targetId == 0){
		alert("请选择对象");
		return;
	}
	
	var url = $("#projectPath").val()+"/message/getHistoryMessage";
	var data = {
		type : messageType,
		sourceId : myId,
		targetId : targetId,
		number : 100
	}
	ajaxRequest(url,data,function(res){
		var dataRes = JSON.parse(res);
		if(dataRes.code === "200"){
			console.log(dataRes);
			loadChatContent(dataRes.messageList);
			var tempHtml = 							
				"<li style=\"list-style: none;\">"+
					"<div class=\"word-color\" style=\"text-align: center;\">以上是历史消息</div>"+
				"</li>";
			$("#chatContentUl").append(tempHtml);
		}else{
			alert(dataRes.msg);
		}
	});
}

//function videoBuild(){
//	var type = $("#chatTargetType").val();
//	var targetId = parseInt($("#chatTargetId").val());
//	
//	if(type == "user"){
//		socketData = {
//				type : "videoUser",
//				sourceId : myId,
//				targetId : targetId,
//				room : type+targetId
//			}
//		var socketDataStr = JSON.stringify(socketData);
//		sendMessage(socketDataStr);
//	}else if(type === "group"){
//		socketData = {
//				type : "messageGroup",
//				sourceId : myId,
//				targetId : targetId,
//				text : text
//			}
//		var socketDataStr = JSON.stringify(socketData);
//		sendMessage(socketDataStr);
//	}
//}