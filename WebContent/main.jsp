<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>RTC Chat</title>
	
	<link href="<%=request.getContextPath() %>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/css/bootstrap/fileinput.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/css/bootstrap/font-awesome.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/css/bootstrap/build.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/css/bootstrap/BootSideMenu.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/css/bootstrap/bootstrap-drawer.css"  rel="stylesheet">
	<link href="<%=request.getContextPath() %>/css/jquery/jquery.mCustomScrollbar.min.css"  rel="stylesheet">
	<link href="<%=request.getContextPath() %>/css/jquery/jquery.emoji.css"  rel="stylesheet">
	<link href="<%=request.getContextPath() %>/css/jquery/railscasts.css"  rel="stylesheet">
	<link href="<%=request.getContextPath() %>/css/main.css" rel="stylesheet">
	
	<script src="<%=request.getContextPath() %>/js/jquery/jquery.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery/jquery.cookie.js"></script>
	<script src="<%=request.getContextPath() %>/js/bootstrap/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/bootstrap/fileinput.js"></script>
	<script src="<%=request.getContextPath() %>/js/bootstrap/BootSideMenu.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery/highlight.pack.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery/jquery.mousewheel-3.0.6.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery/jquery.mCustomScrollbar.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery/jquery.emoji.min.js"></script>
</head>
<body>
	<input id="myId" type="hidden" value="${sessionScope.userid }">
	<input id="myUsername" type="hidden" value="${sessionScope.username }">
	<input id="projectPath" type="hidden" value="<%=request.getContextPath() %>">

	<div id="all">
		<div id="top">
			<div id="topContent" class="containner">
				<div class="row">
				    <div class="col-md-8 text-left word-color" style="margin-top: 15px;">
<%-- 				    	<img style="width: 25px;height: 25px;" src="<%=request.getContextPath()%>/images/head.jpg">&nbsp;&nbsp;暗月天堂cy --%>
				    </div>
				    <div class="col-md-4 text-right word-color" style="margin-top: 15px;font-size: 15px;">
				    	此心拖泥带水，是人生最苦处
				    </div>
			  	</div>
			</div>
		</div>
		<div id="main">
			<div id="left" class="col-md-2">
				<div id="myUser">
					<div style="margin-bottom: 5px;font-size: 20px;font-weight: 500;">Me</div>
					<button class="btn btn-default" style="width:100%;border: 0px;text-align: left;">
						<div class="word-color" data-toggle="modal" data-target="#userInfoModal" >
							<img id="myHeadImg" style="width: 25px;height: 25px;" src="<%=request.getContextPath()%>${sessionScope.headImg}">&nbsp;&nbsp;${sessionScope.username }
						</div>
					</button>
				</div>
				<div id="myGroups" style="margin-top: 30px;">
					<div style="margin-bottom: 5px;font-size: 20px;font-weight: 500;">
						<label>My Groups</label>
						<button onclick="initMyGroupsToDelete()" data-toggle="modal" data-target="#myGroupModal" class="btn btn-default" style="border: 0px;float: right;margin-top: 5px;"><span class="glyphicon glyphicon-tasks"></span></button>
					</div>
					<ul style="list-style: none;margin: 0px;padding: 0px;">
						<div class="container-fluid">
							<div class="accordion" id="myGroupList">
							</div>
						</div>
					</ul>
				</div>
			</div>
			<div id="center" class="col-md-3">
				<div id="operation" class="part" style="text-align: right;">
					<button class="btn btn-default" data-toggle="modal" data-target="#friendAddOrGroupJoinModal" style="border: 0px;"><span class="glyphicon glyphicon-plus"></span>&nbsp;add</button>
					<button class="btn btn-default" data-toggle="modal" data-target="#friendDeleteOrGroupExitModal" style="border: 0px;"><span class="glyphicon glyphicon-minus"></span>&nbsp;delete</button>
				</div>
				<div id="search" class="part">
					<input type="text" class="form-control">
				</div>
				<div id="list" class="part" style="overflow: auto;">
					<ul class="nav nav-tabs">
					    <li class="active">
					    	<input type="hidden" value="1">
					    	<a href="#myFriendList" data-toggle="tab">我的好友</a>
					    </li>
					    <li>
					    	<input type="hidden" value="2">
					    	<a href="#myJoinedGroupList" data-toggle="tab">我加入的群组</a>
					    </li>
					</ul>
					<div id="myFriendsAndJoinedGroupList" class="tab-content">
					    <div class="tab-pane fade in active" id="myFriendList">
							<ul id="myFriendListUl" style="list-style: none;margin: 0px;padding: 0px;">
							</ul>
					    </div>
					    <div class="tab-pane fade" id="myJoinedGroupList">
					    	<ul style="list-style: none;margin: 0px;padding: 0px;">
   								<div class="container-fluid">
									<div class="accordion" id="myJoinedGroupListUl">
									</div>
								</div>
							</ul>
					    </div>
					</div>
				</div>
			</div>
			<div id="right" class="col-md-6">
				<div id="chatTarget">
					<div id="chatTargetName" class="word-color" style="font-size: 30px;">NULL</div>
					<input id="chatTargetType" type="hidden" value="0">
					<input id="chatTargetId" type="hidden" value="0">
				</div>
				<div id="triangle"></div>
				<div id="othersideContent">
					<div id="chatContent" style="width:100%;height:75%;overflow: auto;">
						<ul id="chatContentUl" style="margin-top: 20px;margin-bottom: 20px;margin-left:30px;padding: 0px;">
						</ul>
					</div>
					<div id="chatTool" style="width:100%;height:5%;border-top: 1px solid grey;">
						<button onclick="getFiles()" class="btn btn-default btn-lg" data-toggle="modal" data-target="#fileRepertoryModal" style="padding:0px;"><span class="glyphicon glyphicon-open-file"></span></button>
						<button onclick="getHistoryMessage()" class="btn btn-default btn-lg" style="padding:0px;"><span class="glyphicon glyphicon-book"></span></button>
						<button id="emojiBtn" class="btn btn-default btn-lg" style="padding:0px;">:)</button>
					</div>
					<div id="sendContent" style="width:100%;height:20%;padding: 0px;">
					  	<div class="form-group"> 
						    <textarea id="sendText" class="form-control" onkeydown="textareaKeyDown()"></textarea> 
						  	<button class="btn btn-primary" onclick="chatSendMessage()">发送</button>
						  	<button id="testBtn" class="btn btn-default" onclick="chatTextClear()">清空</button>
						</div> 
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="userInfoModify.jsp" %>
	<%@ include file="myGroup.jsp" %>
	<%@ include file="friendAddOrGroupJoin.jsp" %>
	<%@ include file="friendDeleteOrGroupExit.jsp" %>
	<%@ include file="GroupUserAddOrDelete.jsp" %>
	<%@ include file="rightVideo.jsp" %>
	<%@ include file="fileRepertory.jsp" %>

</body>

<script src="<%=request.getContextPath() %>/js/base.js"></script>
<script src="<%=request.getContextPath() %>/js/websocket.js"></script>
<script src="<%=request.getContextPath() %>/js/main.js"></script>
</html>