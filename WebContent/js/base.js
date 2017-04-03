function ajaxRequest(url,data,suncessTunc){
	$.ajax({
		type:"post",
		url : url,
		data : data,
		success:function(res){
			suncessTunc(res);
		},
		error:function(err){
			console.log("error");
			console.log(err);
		}
	})
}

function getCookie(target){
	var cookies = document.cookie.split(";"); 
	var target = target;
	var len = target.length;
	for(var i=0;i<cookies.length;i++){
		var cookie = cookies[i].replace(/(^\s*)|(\s*$)/g, "");
		if(cookie.substring(0,len) === target){
			var value = cookie.split("=")[1];
			return value;
		}
	}
}