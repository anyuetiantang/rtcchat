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

function transferTime(time){
	var year = time.year + 1900 || time.getYear() + 1900;
	var month = time.month + 1 || time.getMonth();
	var day = time.date || time.getDate();
	var hour = time.hours || time.getHours();
	var minute = time.minutes || time.getMinutes();
	var second = time.seconds || time.getSeconds();
	
	if(month < 10){
		month = "0" + month;
	}
	if(day < 10){
		day = "0" + day;
	}
	if(hour < 10){
		hour = "0" + hour;
	}
	if(minute < 10){
		minute = "0" + minute;
	}
	if(second < 10){
		second = "0" + second;
	}
	
	var strTime = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
	return strTime;
}

