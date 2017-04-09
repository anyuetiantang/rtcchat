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

