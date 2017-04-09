package com.rtcchat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.rtcchat.entity.User;
import com.rtcchat.service.UserService;
import com.rtcchat.tools.ApplicationContextRegister;
import com.rtcchat.tools.ErrorType;
import com.rtcchat.tools.Storage;
import com.rtcchat.tools.WebSocketMsgType;

import net.sf.json.JSONObject;

@Component
@ServerEndpoint(value="/websocket/{userid}")
public class WebSocketController extends TextWebSocketHandler{
	private Session session;
	private int userid;
	
	@OnMessage
	public void onMessage(String jsonMessage) throws IOException {
		UserService userService = (UserService)ApplicationContextRegister.getApplicationContext().getBean("userService");
		
		JSONObject jsonObjInput = JSONObject.fromObject(jsonMessage);
		String type = jsonObjInput.getString("type");
		
		//switch case语句无法匹配变量
		if(type.equals(WebSocketMsgType.FRIEND_ADD_REQ.getSocketType())){
			friendAddReq(userService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.FRIEND_ADD_RES.getSocketType())){
			friendAddRes(userService,jsonObjInput,jsonMessage);
		}else if(type.equals(WebSocketMsgType.FRIEND_DELETE_REQ.getSocketType())){
			friendDeleteReq(userService,jsonObjInput);
		}
		
	}
	
	//用户添加好友请求
	private void friendAddReq(UserService userService,JSONObject jsonObjInput) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int targetId = jsonObjInput.getInt("targetId");
		Session sessionOther = Storage.socketMap.get(targetId);
		User sourceUser = userService.findById(User.class, sourceId);
		User targetUser = userService.findById(User.class, targetId);
		Set<User> sourceFriends = userService.findFriendById(sourceId);
		
		for(User friend : sourceFriends){
			if(friend.getId() == targetId){
				map.put("type", "error");
				map.put("msg", "该用户已经是你的好友了");
				JSONObject jsonObjOutput = JSONObject.fromObject(map);
				session.getBasicRemote().sendText(jsonObjOutput.toString());
				return;
			}
		}
		
		if(sessionOther == null){//用户不在线
			map.put("type",WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", "该用户不在线，当他上线时将会受到您的请求");
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			session.getBasicRemote().sendText(jsonObjOutput.toString());
			
			//用户不在线，将消息放入历史消息
			
		}else{//用户在线
			map.put("type", jsonObjInput.getString("type"));
			map.put("sourceId", sourceId);
			map.put("sourceName", sourceUser.getUsername());
			map.put("targetId", targetId);
			map.put("targetName", targetUser.getUsername());
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			sessionOther.getBasicRemote().sendText(jsonObjOutput.toString());
		}
	}
	
	//用户添加好友回复
	private void friendAddRes(UserService userService,JSONObject jsonObjInput,String jsonMessage) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int targetId = jsonObjInput.getInt("targetId");
		boolean agree = jsonObjInput.getBoolean("agree");
		
		if(agree){
			userService.friendAdd(sourceId, targetId);
		}
		
		Session sessionSource = Storage.socketMap.get(sourceId);
		sessionSource.getBasicRemote().sendText(jsonMessage);
		
		map.put("type", WebSocketMsgType.FRIEND_ADD_RES_SUC.getSocketType());
		map.put("sourceId", sourceId);
		map.put("sourceName", jsonObjInput.getString("sourceName"));
		map.put("targetId", targetId);
		map.put("targetName", jsonObjInput.getString("targetName"));
		JSONObject jsonObjOutput = JSONObject.fromObject(map);
		Session sessionTarget = Storage.socketMap.get(targetId);
		sessionTarget.getBasicRemote().sendText(jsonObjOutput.toString());
	}
	
	//用户删除好友请求
	public void friendDeleteReq(UserService userService,JSONObject jsonObjInput) throws IOException{
		Map<String,String> map = new HashMap<String,String>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int targetId = jsonObjInput.getInt("targetId");
		
		ErrorType errMsg = userService.friendDelete(sourceId, targetId);
		if(errMsg.getCode().equals("200")){
			map.put("type",WebSocketMsgType.FRIEND_DELETE_REQ.getSocketType());
		}else{
			map.put("type",WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", errMsg.getMsg());
		}
		
		JSONObject jsonObjOutput = JSONObject.fromObject(map);
		Session sessionSource = Storage.socketMap.get(sourceId);
		Session sessionTarget = Storage.socketMap.get(targetId);
		sessionSource.getBasicRemote().sendText(jsonObjOutput.toString());
		sessionTarget.getBasicRemote().sendText(jsonObjOutput.toString());
	}
	
	@OnOpen
	public void onOpen(Session session,@PathParam(value="userid")int userid) {
		this.session = session;
		this.userid = userid;
		Storage.socketMap.put(userid, this.session);
		System.out.println(userid+" Client connected");
	}
 
	@OnClose
	public void onClose() {
		Storage.socketMap.remove(userid);
		System.out.println("Connection closed");
	}
}
