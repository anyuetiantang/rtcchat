<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>欢迎登陆rtcchat</title>

	<link href="<%=request.getContextPath() %>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/css/login.css" rel="stylesheet">

	<script src="<%=request.getContextPath() %>/js/jquery/jquery.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery/jquery.cookie.js"></script>
	<script src="<%=request.getContextPath() %>/js/bootstrap/bootstrap.min.js"></script>
</head>
<body>
	<input id="projectPath" type="hidden" value="<%=request.getContextPath() %>">
	
	<div class="row">
		<div class="userPicDiv">
			<img id="loginPic" style="width: 173px;height: 173px;">
		</div>
		<div class="mainDiv col-md-10 col-sm-10 col-xs-10">
			<div class="formDiv">
				<form id="loginForm" action="<%=request.getContextPath() %>/start/loginRequest" method="post" class="form-login" role="form">
					<input type="text" class="form-control myText" id="username" name="username" placeholder="username" required autofocus />
					<input type="password" class="form-control myText" id="password" name="password" placeholder="password" required />
					<div class="row chkBtnDiv">
						<div class="myCheckbox checkbox col-xs-6">
							<label>
								<input id="remember" name="remember" type="checkbox" checked  /><label>Remember me</label>
							</label>
						</div>
						<div class="btnDiv col-xs-6">
							<button type="button" class="btn btn-primary" id="submitBtn">Login</button>
							<button type="button" class="btn btn-primary" id="registerBtn" data-toggle="modal" data-target="#registerModal">Register</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="bottomDiv col-md-10 col-sm-10 col-xs-10">
			<span class="text-muted">Not a registered user yet?</span>
			<span class="text-info">Sign up now!</span>
		</div>
	</div>
	
	<%@ include file="register.jsp" %>
</body>

<script src="<%=request.getContextPath() %>/js/base.js"></script>
<script src="<%=request.getContextPath() %>/js/login.js"></script>
</html>