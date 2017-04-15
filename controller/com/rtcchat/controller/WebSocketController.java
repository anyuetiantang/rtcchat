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

import com.rtcchat.entity.Group;
import com.rtcchat.entity.User;
import com.rtcchat.service.GroupService;
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
		GroupService groupService = (GroupService)ApplicationContextRegister.getApplicationContext().getBean("groupService");
		
		JSONObject jsonObjInput = JSONObject.fromObject(jsonMessage);
		String type = jsonObjInput.getString("type");
		
		//switch case����޷�ƥ�����
		if(type.equals(WebSocketMsgType.FRIEND_ADD_REQ.getSocketType())){
			friendAddReq(userService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.FRIEND_ADD_RES.getSocketType())){
			friendAddRes(userService,jsonObjInput,jsonMessage);
		}else if(type.equals(WebSocketMsgType.FRIEND_DELETE_REQ.getSocketType())){
			friendDeleteReq(userService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.GROUP_JOIN_REQ_FROM_GROUP.getSocketType())){
			groupJoinReqFromGroup(userService,groupService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.GROUP_JOIN_RES_FROM_GROUP.getSocketType())){
			groupJoinResFromGroup(userService,groupService,jsonObjInput,jsonMessage);
		}else if(type.equals(WebSocketMsgType.GROUP_USER_DELETE.getSocketType())){
			groupUserDelete(userService,groupService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.GROUP_JOIN_REQ_FROM_USER.getSocketType())){
			groupJoinReqFromUser(userService,groupService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.GROUP_JOIN_RES_FROM_USER.getSocketType())){
			groupJoinResFromUser(userService,groupService,jsonObjInput,jsonMessage);
		}else if(type.equals(WebSocketMsgType.GROUP_USER_EXIT.getSocketType())){
			groupUserExit(userService,groupService,jsonObjInput);
		}
		
	}
	
	//�û���Ӻ�������
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
				map.put("type", WebSocketMsgType.ERROR.getSocketType());
				map.put("msg", "���û��Ѿ�����ĺ�����");
				JSONObject jsonObjOutput = JSONObject.fromObject(map);
				session.getBasicRemote().sendText(jsonObjOutput.toString());
				return;
			}
		}
		
		if(sessionOther == null){//�û�������
			map.put("type",WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", "���û������ߣ���������ʱ�����ܵ���������");
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			session.getBasicRemote().sendText(jsonObjOutput.toString());
			
			//�û������ߣ�����Ϣ������ʷ��Ϣ
			
		}else{//�û�����
			map.put("type", jsonObjInput.getString("type"));
			map.put("sourceId", sourceId);
			map.put("sourceName", sourceUser.getUsername());
			map.put("targetId", targetId);
			map.put("targetName", targetUser.getUsername());
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			sessionOther.getBasicRemote().sendText(jsonObjOutput.toString());
		}
	}
	
	//�û���Ӻ��ѻظ�
	private void friendAddRes(UserService userService,JSONObject jsonObjInput,String jsonMessage) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int targetId = jsonObjInput.getInt("targetId");
		boolean agree = jsonObjInput.getBoolean("agree");
		
		if(agree){
			userService.friendAdd(sourceId, targetId);
			
			map.put("type", WebSocketMsgType.FRIEND_ADD_RES_SUC.getSocketType());
			map.put("sourceId", sourceId);
			map.put("sourceName", jsonObjInput.getString("sourceName"));
			map.put("targetId", targetId);
			map.put("targetName", jsonObjInput.getString("targetName"));
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			Session sessionTarget = Storage.socketMap.get(targetId);
			sessionTarget.getBasicRemote().sendText(jsonObjOutput.toString());
		}
		
		Session sessionSource = Storage.socketMap.get(sourceId);
		sessionSource.getBasicRemote().sendText(jsonMessage);
	}
	
	//�û�ɾ����������
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
		if(sessionTarget != null)
			sessionTarget.getBasicRemote().sendText(jsonObjOutput.toString());
	}
	
	//Ⱥ���������ĳ���û�
	private void groupJoinReqFromGroup(UserService userService,GroupService groupService,JSONObject jsonObjInput) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int targetId = jsonObjInput.getInt("targetId");
		int groupId = jsonObjInput.getInt("groupId");
		
		if(sourceId == targetId){
			map.put("type", WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", "��������Լ�");
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			session.getBasicRemote().sendText(jsonObjOutput.toString());
			return;
		}
		
		Session sessionOther = Storage.socketMap.get(targetId);
		Group group = groupService.findById(Group.class, groupId);
		User sourceUser = userService.findById(User.class, sourceId);
		User targetUser = userService.findById(User.class, targetId);
		
		Set<User> members = groupService.findMembersById(groupId);
		for(User member : members){
			if(targetId == member.getId()){
				map.put("type", WebSocketMsgType.ERROR.getSocketType());
				map.put("msg", "���û��Ѿ���Ⱥ������");
				JSONObject jsonObjOutput = JSONObject.fromObject(map);
				session.getBasicRemote().sendText(jsonObjOutput.toString());
				return;
			}
		}
		
		if(sessionOther == null){//�û�������
			map.put("type",WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", "���û������ߣ���������ʱ�����ܵ���������");
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			session.getBasicRemote().sendText(jsonObjOutput.toString());
			
			//�û������ߣ�����Ϣ������ʷ��Ϣ
			
		}else{//�û�����
			map.put("type", jsonObjInput.getString("type"));
			map.put("sourceId", sourceId);
			map.put("sourceName", sourceUser.getUsername());
			map.put("targetId", targetId);
			map.put("targetName", targetUser.getUsername());
			map.put("groupId", groupId);
			map.put("groupname", group.getGroupname());
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			sessionOther.getBasicRemote().sendText(jsonObjOutput.toString());
		}
	}
	
	//Ⱥ����Ӻ��ѻظ�
	private void groupJoinResFromGroup(UserService userService,GroupService groupService,JSONObject jsonObjInput,String jsonMessage) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int targetId = jsonObjInput.getInt("targetId");
		int groupId = jsonObjInput.getInt("groupId");
		boolean agree = jsonObjInput.getBoolean("agree");
		
		if(agree){
			groupService.groupUserAdd(groupId, targetId);
			
			map.put("type", WebSocketMsgType.GROUP_JOIN_RES_FROM_GROUP_SUC.getSocketType());
			map.put("sourceId", sourceId);
			map.put("sourceName", jsonObjInput.getString("sourceName"));
			map.put("targetId", targetId);
			map.put("targetName", jsonObjInput.getString("targetName"));
			map.put("groupId", groupId);
			map.put("groupname", jsonObjInput.getString("groupname"));
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			Session sessionTarget = Storage.socketMap.get(targetId);
			sessionTarget.getBasicRemote().sendText(jsonObjOutput.toString());
		}
		
		Session sessionSource = Storage.socketMap.get(sourceId);
		sessionSource.getBasicRemote().sendText(jsonMessage);
	}
	
	//Ⱥ���û�ɾ��
	public void groupUserDelete(UserService userService,GroupService groupService,JSONObject jsonObjInput) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int targetId = jsonObjInput.getInt("targetId");
		int groupId = jsonObjInput.getInt("groupId");
		
		User targetUser = userService.findById(User.class,targetId);
		Session sessionSource = Storage.socketMap.get(sourceId);
		Session sessionTarget = Storage.socketMap.get(targetId);
		
		ErrorType errMsg = groupService.groupUserDelete(groupId, targetId);
		Group group = groupService.findById(Group.class, groupId);
		if(errMsg.getCode().equals("200")){
			map.put("type",WebSocketMsgType.GROUP_USER_DELETE.getSocketType());
			map.put("groupname",group.getGroupname());
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			if(sessionTarget != null)
				sessionTarget.getBasicRemote().sendText(jsonObjOutput.toString());
			
			
			map.put("type", WebSocketMsgType.GROUP_USER_DELETE_RES.getSocketType());
			map.put("groupname",group.getGroupname());
			map.put("targetName", targetUser.getUsername());
			jsonObjOutput = JSONObject.fromObject(map);
			sessionSource.getBasicRemote().sendText(jsonObjOutput.toString());
			
		}else{
			map.put("type",WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", errMsg.getMsg());
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			sessionSource.getBasicRemote().sendText(jsonObjOutput.toString());
		}
	}
	
	//�û��������ĳȺ�������
	public void groupJoinReqFromUser(UserService userService,GroupService groupService,JSONObject jsonObjInput) throws IOException {
		Map<String,Object> map = new HashMap<String,Object>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int groupId = jsonObjInput.getInt("groupId");
		User sourceUser = userService.findById(User.class, sourceId);
		User targetUser = groupService.findCreator(groupId);
		
		if(sourceId == targetUser.getId()){
			map.put("type", WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", "�������Լ���Ⱥ������");
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			session.getBasicRemote().sendText(jsonObjOutput.toString());
			return;
		}
		
		Session sessionOther = Storage.socketMap.get(targetUser.getId());
		Group group = groupService.findById(Group.class, groupId);
		
		Set<User> members = groupService.findMembersById(groupId);
		for(User member : members){
			if(targetUser.getId() == member.getId()){
				map.put("type", WebSocketMsgType.ERROR.getSocketType());
				map.put("msg", "���Ѿ���Ⱥ������");
				JSONObject jsonObjOutput = JSONObject.fromObject(map);
				session.getBasicRemote().sendText(jsonObjOutput.toString());
				return;
			}
		}
		
		if(sessionOther == null){//�û�������
			map.put("type",WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", "Ⱥ�鴴���߲����ߣ���������ʱ�����ܵ���������");
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			session.getBasicRemote().sendText(jsonObjOutput.toString());
			
			//�û������ߣ�����Ϣ������ʷ��Ϣ
			
		}else{//�û�����
			map.put("type", jsonObjInput.getString("type"));
			map.put("sourceId", sourceId);
			map.put("sourceName", sourceUser.getUsername());
			map.put("targetId", targetUser.getId());
			map.put("targetName", targetUser.getUsername());
			map.put("groupId", groupId);
			map.put("groupname", group.getGroupname());
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			sessionOther.getBasicRemote().sendText(jsonObjOutput.toString());
		}
	}
	
	//�û��������ĳȺ��Ļظ�
	public void groupJoinResFromUser(UserService userService,GroupService groupService,JSONObject jsonObjInput,String jsonMessage) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int targetId = jsonObjInput.getInt("targetId");
		int groupId = jsonObjInput.getInt("groupId");
		boolean agree = jsonObjInput.getBoolean("agree");
		
		if(agree){
			groupService.groupUserAdd(groupId, sourceId);
			
			map.put("type", WebSocketMsgType.GROUP_JOIN_RES_FROM_USER_SUC.getSocketType());
			map.put("sourceId", sourceId);
			map.put("sourceName", jsonObjInput.getString("sourceName"));
			map.put("targetId", targetId);
			map.put("targetName", jsonObjInput.getString("targetName"));
			map.put("groupId", groupId);
			map.put("groupname", jsonObjInput.getString("groupname"));
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			Session sessionTarget = Storage.socketMap.get(targetId);
			sessionTarget.getBasicRemote().sendText(jsonObjOutput.toString());
		}
		
		Session sessionSource = Storage.socketMap.get(sourceId);
		sessionSource.getBasicRemote().sendText(jsonMessage);
	}
	
	//�û��˳�Ⱥ��
	public void groupUserExit(UserService userService,GroupService groupService,JSONObject jsonObjInput) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int groupId = jsonObjInput.getInt("groupId");
		User sourceUser = userService.findById(User.class, sourceId);
		User targetUser = groupService.findCreator(groupId);
		
		Session sessionSource = Storage.socketMap.get(sourceId);
		Session sessionTarget = Storage.socketMap.get(targetUser.getId());
		
		ErrorType errMsg = groupService.groupUserDelete(groupId, sourceId);
		Group group = groupService.findById(Group.class, groupId);
		if(errMsg.getCode().equals("200")){
			map.put("type",WebSocketMsgType.GROUP_USER_EXIT.getSocketType());
			map.put("groupname",group.getGroupname());
			map.put("sourcename", sourceUser.getUsername());
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			if(sessionTarget != null)
				sessionTarget.getBasicRemote().sendText(jsonObjOutput.toString());
			
			map.put("type",WebSocketMsgType.GROUP_USER_EXIT_RES.getSocketType());
			jsonObjOutput = JSONObject.fromObject(map);
			sessionSource.getBasicRemote().sendText(jsonObjOutput.toString());
		}else{
			map.put("type",WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", errMsg.getMsg());
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			sessionSource.getBasicRemote().sendText(jsonObjOutput.toString());
		}
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
