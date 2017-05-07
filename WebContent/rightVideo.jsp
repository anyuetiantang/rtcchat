<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <style>
        .myVideo{
            height:170px;
            width: 190px;
            margin: 10px;
            border: 2px solid #000;
        }
        
        .video, .videoContainer video {
            height:100px;
            width: 100px;
            margin: 10px;
            border: 2px solid #000;
        }
    </style>

<!-- 右侧视频区域 -->    
 <div id="rightVideo">
     <div class="drawer-contents">
        <div class="drawer-heading">
            <h2 class="drawer-title">Video</h2>
            <button id="btn_open" class="btn btn-primary">开启</button>
            <button id="btn_close" class="btn btn-primary">关闭</button>
            <button id="btn_leave" class="btn btn-primary">离开</button>
            <input id="roomName" type="text" style="margin-top:10px;">
            <button id="btn_build" class="btn btn-primary">建立</button>
        </div>
        <div class="drawer-body list-group" style="padding: 0px;">
        	<video id="localVideo" class = "myVideo"></video>
        	<div>当前房间：<span id="roomText"></span></div>
        	<div id="remoteVideos"></div>
      	</div>
    </div>
</div>

<script>
	$('#rightVideo').BootSideMenu({
		side : "right",
		duration : 200,
		remember : true,
		autoClose : true,
		closeOnClick : true,
		width : "15%"
		});
	
	var webrtc = null;
	init_webrtc();
	
	function init_webrtc(){
		 webrtc = new SimpleWebRTC({
		        // the id/element dom element that will hold "our" video,   ++++注意是EL!! E和小写的Ｌ++++
		        localVideoEl: 'localVideo',
		        // the id/element dom element that will hold remote videos
		        remoteVideosEl: '',
		        // immediately ask for camera access
		        autoRequestMedia: true,
		        debug: false,
		        detectSpeakingEvents: true,
		        autoAdjustMic: false
		    });
	        // grab the room from the URL
	        var room = location.search && location.search.split('?')[1];
		    
		    //we got access to the camera
		    webrtc.on('localStream',function(stream) {
		        console.log('localStream'); 
		    });

		     //local screen obtained
		     webrtc.on('localScreenAdded', function (video)  {
		        console.log('localScreenAdded');
		     });

		     //when it's ready, join if we got a room from the URL
		    webrtc.on('readyToCall',function(){
		        if(room) {
		        	webrtc.joinRoom(room);
		        }
		    });
		     
		     
		    //有视频加入时
		    webrtc.on('videoAdded',function (video,peer){
		        console.log("Video added !! PEER: ",peer);

		        //增加一个元素加入视频
		        var remoteVideos = document.getElementById("remoteVideos");
		        if(remoteVideos) {
		            var container = document.createElement("div");
		            container.className = 'videoContainer';
		            container.id = 'container_' + webrtc.getDomId(peer);    //~~~!!
		            container.appendChild(video);
		        }
		        //显示该视频的信息
		        if( peer && peer.pc ) {
		            var connstate = document.createElement('div');
		            //connstate.className = 'connectionState';
		            //ice connection状态改变时
		            container.appendChild(connstate);
		            peer.pc.on( 'iceConnectionStateChange',function(event) {
		                console.log( 'iceConnectionStateChange !! EVENT: ', event );
		                switch (peer.pc.iceConnectionState) {
		                    case 'checking':
		                        connstate.innerText = 'Connecting to peer ...';
		                        break;
		                    case 'connected':
		                    case 'completed': // on caller side
		                        connstate.innerText = 'Connecting established.';
		                        break;
		                    case 'disconnected':
		                        connstate.innerText = 'Disconnected.';
		                        var remoteVideos = document.getElementById('remoteVideos');
		                        var el = document.getElementById( peer ? 'container_' + webrtc.getDomId(peer)  : 'localScreenContainer' );   //移除远程或自己的视频．注意判断
		                        if( remoteVideos && el )
		                            remoteVideos.removeChild(el);
		                        break;
		                    case 'failed':
		                        connstate.innerText = 'Failed.';
		                        break;
		                    case 'closed':
		                        connstate.innerText = 'Closed.';
		                        break;
		                }
		            } );
		        }
		         remoteVideos.appendChild(container);
		    });
		    
		    //视频流被删除时
		    webrtc.on('videoRemoved',function(video,peer){
		        console.log("Video Removed !! PEER: ",peer);
		        var remoteVideos = document.getElementById('remoteVideos');
		        var el = document.getElementById( peer ? 'container_' + webrtc.getDomId(peer)  : 'localScreenContainer' );   //移除远程或自己的视频．注意判断
		        if( remoteVideos && el )
		            remoteVideos.removeChild(el);   //删除被removed的视频元素
		    });
		    
		    //we did not get access to the camera
		    webrtc.on('localMediaError',function(err){
		        console.log('视频发生错误，请刷新页面');
		    });
//		     webrtc.joinRoom("ccc");
			$("#btn_open").click(function(){
				webrtc.resume();
			});
			
			$("#btn_close").click(function(){
				webrtc.pause();
			});
			
			$("#btn_leave").click(function(){
				webrtc.leaveRoom();
				$("#roomText").text("");
			});	
			
			$("#btn_build").click(function(){
				var roomName = $("#roomName").val();
				if(roomName == null || roomName === ""){
					return;
				}
				webrtc.joinRoom(roomName);
				$("#roomText").text(roomName);
			});	
	}
</script>