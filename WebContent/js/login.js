
$(function(){
	
	cookie_init();
	
	function cookie_init(){
		var cookie_username = getCookie("username");
		var cookie_headImg = getCookie("headImg");
		var headImgPath  = $("#projectPath").val()+cookie_headImg;
		$("#username").val(cookie_username);
		$("#password").val("");
		if(cookie_headImg){
			$("#loginPic").attr("src",headImgPath);
		}
	}
	
	$("#username").blur(function(){
		if($("#username").val() == "") {
			$(this).parent().find(".msg").remove();
			$(this).parent().append('<span class="text-danger msg">请输入用户名</span>');
		}
		if(($("#username").val() != "") && ($("#password").val() != "")) {
			$(this).parent().find(".msg").remove();
		}
	});

	$("#password").blur(function(){
		if($("#password").val() == ""){
			$(this).parent().find(".msg").remove();
			$(this).parent().append('<span class="text-danger msg">请输入密码</span>');
		}
		if(($("#username").val() != "") && ($("#password").val() != "")) {
			$(this).parent().find(".msg").remove();
		}
	});

	
	$("#submitBtn").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		var remember = $("#remember").is(":checked");
		
		var path = $("#projectPath").val()+"/start/loginRequest"
		ajaxRequest(path,{
			username : username,
			password : password,
		},function(res){
			var data = JSON.parse(res);
			console.log(data);
			if(data.code === "200"){
				window.location.href = $("#projectPath").val()+"/start/loginSuccess?random="+data.random+"&username="+username+"&remember="+remember;
			}else{
				alert(data.msg);
			}
		});
//			$("#loginForm").submit();
		
	});
	
	$("#registerBtn").click(function(){
		var username = $.cookie("username");
		console.log(username);
	});
	
	$("#registerRequest").click(function(){
		var username = $("#usernameResgister").val();
		var password = $("#passwordRegister").val();
		var passwordConfirm = $("#passwordConfirm").val();
		var contact = $("#contactRegister").val();
		
		if(password !== passwordConfirm){
			alert(" 密码与确认密码不符");
			return;
		}
		
		var path = $("#projectPath").val()+"/start/register";
		ajaxRequest(path,{
			username : username,
			password : password,
			contact : contact
		},function(res){
			var data = JSON.parse(res);
			if(data.code === "200"){
				alert("注册成功");
				$("#myModal").modal("hide");
			}else{
				alert(data.msg);
			}
		});
		
	});
	
});