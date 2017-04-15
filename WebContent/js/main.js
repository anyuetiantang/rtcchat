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
	for(var i=0;i<groupsCreated.length;i++){
		myGroupHtml += 	"<li class=\"containner\">"+ 
							"<div style=\"width: 100%;\">"+
								"<button class=\"btn btn-default word-color col-md-8\" style=\"border: 0px;text-align: left;\">"+groupsCreated[i].groupname+"</button>"+
								"<span class=\"col-md-4\" style=\"margin-top: 5px;\">"+
									"<input type=\"hidden\" value=\""+groupsCreated[i].id+"\">"+
									"<button onclick=\"getGroupSelectedId("+groupsCreated[i].id+")\" data-toggle=\"modal\" data-target=\"#groupUserAddOrDeleteModal\" class=\"btn btn-default\" style=\"border: 0px;\"><span class=\"glyphicon glyphicon-tasks\"></span></button>"+
								"</span>"+
							"</div>"+
						"</li>";
	}
	$("#myGroupList").empty();
	$("#myGroupList").append(myGroupHtml);
}

//初始化我的好友列表
function initMyFriends(){
	var myFriendHtml = "";
	for(var i=0;i<myFriends.length;i++){
		myFriendHtml += 	"<li>"+
								"<input type=\"hidden\" value=\""+myFriends[i].id+"\">"+
						     	"<button class=\"btn btn-default\" style=\"width:100%;border: 0px;text-align: left;\">"+
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
									"<div style=\"width: 100%;\">"+
										"<button class=\"btn btn-default word-color col-md-12\" style=\"border: 0px;text-align: left;\">"+
											groupsJoined[i].groupname+"("+groupsJoined[i].creator.username+")"+
										"</button>"+
									"</div>"+
								"</li>";
	}
	$("#myJoinedGroupListUl").empty();
	$("#myJoinedGroupListUl").append(myGroupJoinedHtml);
	
}

