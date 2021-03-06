var webSocket = null;

function initWebSocket(projectPath,userid){
	var socketUrl = "ws://localhost:8080"+projectPath+"/websocket/"+userid;
	webSocket = new WebSocket(socketUrl);
	
    webSocket.onerror = function(event) {
    	console.log("error");
    	console.log(event);
    };
    
  	webSocket.onopen = function(event) {
		console.log("websocket open");
  	};
  	
    webSocket.onmessage = function(event) {
    	onMessage(event)
    };
}

//发送消息
function sendMessage(msg){
	if(webSocket == null){
		console.log("Web Socket not connection");
		return;
	}
	webSocket.send(msg);
}

//接收消息
function onMessage(event){
	var socketData = JSON.parse(event.data);
	console.log(socketData);
	
	var type = socketData.type;
	switch(type){
		case "friendAddReq"	:
			friendAddReq(socketData);
			break;
		case "friendAddRes"	:
			friendAddRes(socketData);
			break;
		case "friendAddResSuc"	:
			friendAddResSuc(socketData);
			break;
		case "friendDeleteReq"	:
			friendDeleteReq(socketData);
			break;
		case "groupJoinReqFromGroup":
			groupJoinReqFromGroup(socketData);
			break;
		case "groupJoinResFromGroup":
			groupJoinResFromGroup(socketData);
			break;
		case "groupJoinResFromGroupSuc":
			groupJoinResFromGroupSuc(socketData);
			break;
		case "groupUserDelete":
			groupUserDelete(socketData);
			break;
		case "groupUserDeleteRes":
			groupUserDeleteRes(socketData);
			break;
		case "groupJoinReqFromUser":
			groupJoinReqFromUser(socketData);
			break;
		case "groupJoinResFromUser":
			groupJoinResFromUser(socketData);
			break;
		case "groupJoinResFromUserSuc":
			groupJoinResFromUserSuc(socketData);
			break;
		case "groupUserExit":
			groupUserExit(socketData);
			break;
		case "groupUserExitRes":
			groupUserExitRes(socketData);
			break;
		case "messageUser":
			messageUser(socketData);
			break;
		case "messageGroup":
			messageGroup(socketData);
			break;
		case "fileUser":
			fileUser(socketData);
			break;
		case "fileGroup":
			fileGroup(socketData);
			break;
//		case "videoUser":
//			videoUser(socketData);
//			break;
		default:
			alert(socketData.msg);
			break;
	}
}

//添加好友请求
function friendAddReq(socketData){
	var socketDataRes = {
			type : "friendAddRes",
			sourceId : parseInt(socketData.sourceId),
			sourceName : socketData.sourceName,
			targetId : parseInt(socketData.targetId),
			targetName : socketData.targetName,
			agree : false
		}
		if(confirm(socketData.sourceName+" 想要添加你为好友，请问是否答应!")){
			socketDataRes["agree"] = true;
			var socketDataResStr = JSON.stringify(socketDataRes);
			sendMessage(socketDataResStr);
			alert("你同意了"+socketData.sourceName+"的添加好友请求");
		}else{
			socketDataRes["agree"] = false;
			var socketDataResStr = JSON.stringify(socketDataRes);
			sendMessage(socketDataResStr);
			alert("你拒绝了"+socketData.sourceName+"的添加好友请求");
		}
}

//添加好友得到的回应
function friendAddRes(socketData){
	if(socketData.agree){
		$("#friendAddOrGroupJoinModal").modal("hide");
		getMyGroupsAndFriends(false,true,false,function(){
			initMyFriends();
		});
		alert(socketData.targetName+" 同意了你的请求");
	}else{
		alert(socketData.targetName+" 拒绝了你的请求");
	}
}

//同意添加好友并且成功添加之后的处理
function friendAddResSuc(socketData){
	getMyGroupsAndFriends(false,true,false,function(){
		initMyFriends();
	});
	alert("成功添加" + socketData.sourceName + "为好友");
}

//删除好友
function friendDeleteReq(socketData){
	$("#friendDeleteOrGroupExitModal").modal("hide");
	getMyGroupsAndFriends(false,true,false,function(){
		initMyFriends();
	});
}

//群组申请添加某人
function groupJoinReqFromGroup(socketData){
	var socketDataRes = {
			type : "groupJoinResFromGroup",
			sourceId : parseInt(socketData.sourceId),
			sourceName : socketData.sourceName,
			targetId : parseInt(socketData.targetId),
			targetName : socketData.targetName,
			groupId : parseInt(socketData.groupId),
			groupname : socketData.groupname,
			agree : false
		}
	if(confirm(socketData.sourceName + "邀请你成为群组"+socketData.groupname+"的成员，请问是否同意!")){
		socketDataRes["agree"] = true;
		var socketDataResStr = JSON.stringify(socketDataRes);
		sendMessage(socketDataResStr);
		alert("你同意了群组"+socketData.sourceName+"的邀请，成为"+socketData.groupname+"的成员");
	}else{
		socketDataRes["agree"] = false;
		var socketDataResStr = JSON.stringify(socketDataRes);
		sendMessage(socketDataResStr);
		alert("你拒绝了群组"+socketData.sourceName+"的邀请，成为"+socketData.groupname+"的成员");
	}
}

//群组添加某成员的回应
function groupJoinResFromGroup(socketData){
	if(socketData.agree){
		$("#friendAddOrGroupJoinModal").modal("hide");
		getMyGroupsAndFriends(false,true,false,function(){
			initMyGroups();
		});
		alert(socketData.targetName+" 同意了你的请求");
	}else{
		alert(socketData.targetName+" 拒绝了你的请求");
	}
}

//群组添加成员成功之后的处理
function groupJoinResFromGroupSuc(socketData){
	getMyGroupsAndFriends(false,false,true,function(){
		initMyGroupsJoined();
	});
	alert("成功加入群组 "+socketData.groupname);
}

//群组踢出用户请求
function groupUserDelete(socketData){
	$("#groupUserAddOrDeleteModal").modal("hide");
	getMyGroupsAndFriends(false,false,true,function(){
		initMyGroupsJoined();
	});
	alert("你已经被移除出"+socketData.groupname+"群组");
}

//群组踢出用户回应
function groupUserDeleteRes(socketData){
	$("#groupUserAddOrDeleteModal").modal("hide");
	alert("成功将"+socketData.targetName+"从群组"+socketData.groupname+"中移除");
}

//用户申请加入某群组的请求
function groupJoinReqFromUser(socketData){
	var socketDataRes = {
			type : "groupJoinResFromUser",
			sourceId : parseInt(socketData.sourceId),
			sourceName : socketData.sourceName,
			targetId : parseInt(socketData.targetId),
			targetName : socketData.targetName,
			groupId : parseInt(socketData.groupId),
			groupname : socketData.groupname,
			agree : false
		}
	if(confirm(socketData.sourceName + "申请成为群组"+socketData.groupname+"的成员，请问是否同意!")){
		socketDataRes["agree"] = true;
		var socketDataResStr = JSON.stringify(socketDataRes);
		sendMessage(socketDataResStr);
		alert("你同意了用户"+socketData.sourceName+"成为"+socketData.groupname+"成员的请求");
	}else{
		socketDataRes["agree"] = false;
		var socketDataResStr = JSON.stringify(socketDataRes);
		sendMessage(socketDataResStr);
		alert("你拒绝了用户"+socketData.sourceName+"成为"+socketData.groupname+"成员的请求");
	}
}

//用户申请加入某群组的回复
function groupJoinResFromUser(socketData){
	if(socketData.agree){
		$("#friendAddOrGroupJoinModal").modal("hide");
		getMyGroupsAndFriends(false,false,true,function(){
			initMyGroupsJoined();
		});
		alert(socketData.targetName+" 同意了你的请求");
	}else{
		alert(socketData.targetName+" 拒绝了你的请求");
	}
}

//用户申请加入某群组成功之后的处理
function groupJoinResFromUserSuc(socketData){
	$("#friendAddOrGroupJoinModal").modal("hide");
	getMyGroupsAndFriends(false,false,true,function(){
		initMyGroupsJoined();
	});
}

//用户退出群组
function groupUserExit(socketData){
	$("#friendDeleteOrGroupExitModal").modal("hide");
	alert(socketData.sourcename + "退出"+socketData.groupname+"群组");
}

//用户退出群组的回应
function groupUserExitRes(socketData){
	$("#friendDeleteOrGroupExitModal").modal("hide");
	getMyGroupsAndFriends(false,false,true,function(){
		initMyGroupsJoined();
	});
	alert("你已经退出群组"+socketData.groupname);
}

//获取用户私聊信息
function messageUser(socketData){
	if($("#chatTargetType").val()==="user" && $("#chatTargetId").val()==socketData.sourceId){
		var chatContentHtml = "";
			chatContentHtml += 	"<li style=\"list-style: none;\">"+
									"<div class=\"word-color\">"+socketData.sourceName+
									" "+transferTime(socketData.sendTime)+"</div>"+
									"<div>"+socketData.text+"</div>"+
								"</li>";
		$("#chatContentUl").append(chatContentHtml);
		transferFace();
	}else{
		var sourceId = socketData.sourceId;
		$("#myFriendListButton"+sourceId).addClass("hasMessage");
	}
}

//获取用户的群组信息
function messageGroup(socketData){
	if($("#chatTargetType").val()==="group" && $("#chatTargetId").val()==socketData.groupId){
		var chatContentHtml = "";
			chatContentHtml += 	"<li style=\"list-style: none;\">"+
									"<div class=\"word-color\">"+socketData.sourceName+
									" "+transferTime(socketData.sendTime)+"</div>"+
									"<div>"+socketData.text+"</div>"+
								"</li>";
		$("#chatContentUl").append(chatContentHtml);
		transferFace();
	}else{
		var groupId = socketData.groupId;
		$("#myGroupListButton"+groupId).addClass("hasMessage");
	}
}

//获取私人文件
function fileUser(socketData){
	var projectPath =  $("#projectPath").val();
	if($("#chatTargetType").val()==="user" && $("#chatTargetId").val()==socketData.sourceId){
		var chatContentHtml = "";
			chatContentHtml += 	"<li style=\"list-style: none;\">"+
									"<div class=\"word-color\">"+socketData.sourceName+
									" "+transferTime(socketData.sendTime)+"</div>"+
									"<div><a href=\""+projectPath + socketData.file.filepath+"\">"+socketData.file.originName+"</a></div>"+
								"</li>";
		$("#chatContentUl").append(chatContentHtml);
		transferFace();
	}else{
		var sourceId = socketData.sourceId;
		$("#myFriendListButton"+sourceId).addClass("hasMessage");
	}
}

//获取群组文件
function fileGroup(socketData){
	console.log(socketData);
	var projectPath =  $("#projectPath").val();
	if($("#chatTargetType").val()==="group" && $("#chatTargetId").val()==socketData.groupId){
		var chatContentHtml = "";
			chatContentHtml += 	"<li style=\"list-style: none;\">"+
									"<div class=\"word-color\">"+socketData.sourceName+
									" "+transferTime(socketData.sendTime)+"</div>"+
									"<div><a href=\""+projectPath + socketData.file.filepath+"\">"+socketData.file.originName+"</a></div>"+
								"</li>";
		$("#chatContentUl").append(chatContentHtml);
		transferFace();
	}else{
		var groupId = socketData.groupId;
		$("#myGroupListButton"+groupId).addClass("hasMessage");
	}
}

////建立视频通信
//function videoUser(socketData){
//	if(confirm(socketData.sourceName + "申请与你视频，请问是否同意")){
//		webrtc.joinRoom(socketData.room);
//	}else{
//		alert("你拒绝了"+socketData.sourceName+"的视频请求")
//	}
//}
