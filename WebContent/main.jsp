<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>RTC Chat</title>
	
	<link href="<%=request.getContextPath() %>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/css/main.css" rel="stylesheet">
	
	<script src="<%=request.getContextPath() %>/js/jquery/jquery-3.1.1.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery/jquery.cookie.js"></script>
	<script src="<%=request.getContextPath() %>/js/bootstrap/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/base.js"></script>
</head>
<body>
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
						My Groups
						<button class="btn btn-default" style="border: 0px;width: 14px;height: 16px;"><span class="glyphicon glyphicon-plus"></span></button>
					</div>
					<ul style="list-style: none;margin: 0px;padding: 0px;overflow: auto;">
					     <li>
					     	<button class="btn btn-default" style="width:100%;border: 0px;text-align: left;">
								<div class="word-color">classmates</div>
							</button>
						</li>
						<li>
					     	<button class="btn btn-default" style="width:100%;border: 0px;text-align: left;">
								<div class="word-color">players</div>
							</button>
						</li>
					</ul>
				</div>
			</div>
			<div id="center" class="col-md-3">
				<div id="operation" class="part" style="text-align: right;">
					<button class="btn btn-default" style="border: 0px;"><span class="glyphicon glyphicon-plus"></span>&nbsp;add</button>
					<button class="btn btn-default" style="border: 0px;"><span class="glyphicon glyphicon-minus"></span>&nbsp;delete</button>
				</div>
				<div id="search" class="part">
					<input type="text" class="form-control">
				</div>
				<div id="list" class="part" style="overflow: auto;">
					<ul style="list-style: none;margin: 0px;padding: 0px;">
						<li>
					     	<button class="btn btn-default" style="width:100%;border: 0px;text-align: left;">
								<div class="word-color">
									<img style="width: 25px;height: 25px;" src="<%=request.getContextPath()%>/images/head.jpg">&nbsp;
									carlos
								</div>
							</button>
							<button class="btn btn-default" style="width:100%;border: 0px;text-align: left;">
								<div class="word-color">
									<img style="width: 25px;height: 25px;" src="<%=request.getContextPath()%>/images/head.jpg">&nbsp;
									cy
								</div>
							</button>
						</li>
					</ul>
				</div>
			</div>
			<div id="right" class="col-md-6">
				<div id="othersideName" class="word-color" style="font-size: 30px;">
					carlos
				</div>
				<div id="triangle"></div>
				<div id="othersideContent" style="overflow: auto;">
				
				</div>
			</div>
		</div>
	</div>

	<%@ include file="userInfoModify.jsp" %>

</body>
</html>