package com.rtcchat.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.rtcchat.entity.GroupMessage;
import com.rtcchat.entity.User;
import com.rtcchat.entity.UserMessage;
import com.rtcchat.service.GroupService;
import com.rtcchat.service.MessageService;
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
		MessageService messageService = (MessageService)ApplicationContextRegister.getApplicationContext().getBean("messageService");
		
		JSONObject jsonObjInput = JSONObject.fromObject(jsonMessage);
		String type = jsonObjInput.getString("type");
		
		//switch case����޷�ƥ�����
		if(type.equals(WebSocketMsgType.FRIEND_ADD_REQ.getSocketType())){
			friendAddReq(userService,messageService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.FRIEND_ADD_RES.getSocketType())){
			friendAddRes(userService,jsonObjInput,jsonMessage);
		}else if(type.equals(WebSocketMsgType.FRIEND_DELETE_REQ.getSocketType())){
			friendDeleteReq(userService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.GROUP_JOIN_REQ_FROM_GROUP.getSocketType())){
			groupJoinReqFromGroup(userService,groupService,messageService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.GROUP_JOIN_RES_FROM_GROUP.getSocketType())){
			groupJoinResFromGroup(userService,groupService,jsonObjInput,jsonMessage);
		}else if(type.equals(WebSocketMsgType.GROUP_USER_DELETE.getSocketType())){
			groupUserDelete(userService,groupService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.GROUP_JOIN_REQ_FROM_USER.getSocketType())){
			groupJoinReqFromUser(userService,groupService,messageService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.GROUP_JOIN_RES_FROM_USER.getSocketType())){
			groupJoinResFromUser(userService,groupService,jsonObjInput,jsonMessage);
		}else if(type.equals(WebSocketMsgType.GROUP_USER_EXIT.getSocketType())){
			groupUserExit(userService,groupService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.MESSAGE_USER.getSocketType())){
			sendUserMessage(userService,messageService,jsonObjInput);
		}else if(type.equals(WebSocketMsgType.MESSAGE_GROUP.getSocketType())){
			sendGroupMessage(userService,groupService,messageService,jsonObjInput);
		}
		
	}
	
	//�û���Ӻ�������
	private void friendAddReq(UserService userService,MessageService messageService,JSONObject jsonObjInput) throws IOException{
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
		
		//��������Ϣ�������ݿ�
		UserMessage userMessage = new UserMessage();
		userMessage.setType(WebSocketMsgType.FRIEND_ADD_REQ.getSocketType());
		userMessage.setSendTime(new Date());
		userMessage.setFromUser(sourceUser);
		userMessage.setToUser(targetUser);
		
		if(sessionOther == null){//�û�������
			map.put("type",WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", "���û������ߣ���������ʱ�����ܵ���������");
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			session.getBasicRemote().sendText(jsonObjOutput.toString());
			
			userMessage.setIfread(false);
		}else{//�û�����
			userMessage.setIfread(true);
			
			map.put("type", jsonObjInput.getString("type"));
			map.put("sourceId", sourceId);
			map.put("sourceName", sourceUser.getUsername());
			map.put("targetId", targetId);
			map.put("targetName", targetUser.getUsername());
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			sessionOther.getBasicRemote().sendText(jsonObjOutput.toString());
		}
		messageService.save(userMessage);
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
		if(sessionSource != null)
			sessionSource.getBasicRemote().sendText(jsonMessage);
	}
	
	//�û�ɾ����������
	private void friendDeleteReq(UserService userService,JSONObject jsonObjInput) throws IOException{
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
	private void groupJoinReqFromGroup(UserService userService,GroupService groupService,MessageService messageService,JSONObject jsonObjInput) throws IOException{
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
		
		//��������Ϣ�������ݿ�
		UserMessage userMessage = new UserMessage();
		userMessage.setType(WebSocketMsgType.GROUP_JOIN_REQ_FROM_GROUP.getSocketType());
		userMessage.setSendTime(new Date());
		userMessage.setFromUser(sourceUser);
		userMessage.setToUser(targetUser);
		userMessage.setRelatedGroup(group);
		
		if(sessionOther == null){//�û�������
			map.put("type",WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", "���û������ߣ���������ʱ�����ܵ���������");
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			session.getBasicRemote().sendText(jsonObjOutput.toString());
			
			userMessage.setIfread(false);
		}else{//�û�����
			userMessage.setIfread(true);
			
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
		messageService.save(userMessage);
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
		if(sessionSource!=null)
			sessionSource.getBasicRemote().sendText(jsonMessage);
	}
	
	//Ⱥ���û�ɾ��
	private void groupUserDelete(UserService userService,GroupService groupService,JSONObject jsonObjInput) throws IOException{
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
	private void groupJoinReqFromUser(UserService userService,GroupService groupService,MessageService messageService,JSONObject jsonObjInput) throws IOException {
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
		
		//��������Ϣ�������ݿ�
		UserMessage userMessage = new UserMessage();
		userMessage.setType(WebSocketMsgType.GROUP_JOIN_REQ_FROM_USER.getSocketType());
		userMessage.setSendTime(new Date());
		userMessage.setFromUser(sourceUser);
		userMessage.setToUser(targetUser);
		userMessage.setRelatedGroup(group);
		
		if(sessionOther == null){//�û�������
			map.put("type",WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", "Ⱥ�鴴���߲����ߣ���������ʱ�����ܵ���������");
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			session.getBasicRemote().sendText(jsonObjOutput.toString());
			
			userMessage.setIfread(false);
		}else{//�û�����
			userMessage.setIfread(true);
			
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
		messageService.save(userMessage);
	}
	
	//�û��������ĳȺ��Ļظ�
	private void groupJoinResFromUser(UserService userService,GroupService groupService,JSONObject jsonObjInput,String jsonMessage) throws IOException{
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
		if(sessionSource != null)
			sessionSource.getBasicRemote().sendText(jsonMessage);
	}
	
	//�û��˳�Ⱥ��
	private void groupUserExit(UserService userService,GroupService groupService,JSONObject jsonObjInput) throws IOException{
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
	
	//˽����Ϣ
	private void sendUserMessage(UserService userService,MessageService messageService,JSONObject jsonObjInput) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int targetId = jsonObjInput.getInt("targetId");
		String text = jsonObjInput.getString("text");
		
		User sourceUser = userService.findById(User.class, sourceId);
		User targetUser = userService.findById(User.class, targetId);
		
		//����Ϣ�������ݿ�
		UserMessage userMessage = new UserMessage();
		userMessage.setType(WebSocketMsgType.MESSAGE_USER.getSocketType());
		userMessage.setText(text);
		userMessage.setSendTime(new Date());
		userMessage.setFromUser(sourceUser);
		userMessage.setToUser(targetUser);
		userMessage.setIfread(false);
		
		Session sessionTarget = Storage.socketMap.get(targetId);
		if(sessionTarget == null){	//�û�������
			map.put("type",WebSocketMsgType.ERROR.getSocketType());
			map.put("msg", "���û������ߣ��������߻��յ������Ϣ");
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			session.getBasicRemote().sendText(jsonObjOutput.toString());
		}else{
			map.put("type", WebSocketMsgType.MESSAGE_USER.getSocketType());
			map.put("sourceId", sourceId);
			map.put("sourceName", sourceUser.getUsername());
			map.put("targetId", targetId);
			map.put("targetName", targetUser.getUsername());
			map.put("sendTime", new Date());
			map.put("text", text);
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			sessionTarget.getBasicRemote().sendText(jsonObjOutput.toString());
		}
		messageService.save(userMessage);
	}
	
	//����Ⱥ����Ϣ
	private void sendGroupMessage(UserService userService,GroupService groupService,MessageService messageService,JSONObject jsonObjInput) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		int sourceId = jsonObjInput.getInt("sourceId");
		int groupId = jsonObjInput.getInt("targetId");
		String text = jsonObjInput.getString("text");
		
		User sourceUser = userService.findById(User.class, sourceId);
		Group targetGroup = groupService.findById(Group.class, groupId);
		
		map.put("type", WebSocketMsgType.MESSAGE_GROUP.getSocketType());
		map.put("sourceId", sourceId);
		map.put("sourceName", sourceUser.getUsername());
		map.put("groupId", groupId);
		map.put("groupName", targetGroup.getGroupname());
		map.put("sendTime", new Date());
		map.put("text", text);
		JSONObject jsonObjOutput = JSONObject.fromObject(map);
		
		Set<User> members = groupService.findMembersById(groupId);
		User creator = groupService.findCreator(groupId);
		members.add(creator);
		for(User member : members){
			Session sessionOther = Storage.socketMap.get(member.getId());
			if(member.getId() == sourceId)
				continue;
			
			if(sessionOther != null){
				sessionOther.getBasicRemote().sendText(jsonObjOutput.toString());
			}
		}
		
		//�����ݴ������ݿ�
		GroupMessage groupMessage = new GroupMessage();
		groupMessage.setType(WebSocketMsgType.MESSAGE_GROUP.getSocketType());
		groupMessage.setText(text);
		groupMessage.setBelongToGroup(targetGroup);
		groupMessage.setFromUser(sourceUser);
		groupMessage.setSendTime(new Date());
		messageService.save(groupMessage);
	}
	
	
	@OnOpen
	public void onOpen(Session session,@PathParam(value="userid")int userid) throws IOException {
		this.session = session;
		this.userid = userid;
		Storage.socketMap.put(userid, this.session);
		System.out.println(userid+" Client connected");
		
		MessageService messageService = (MessageService)ApplicationContextRegister.getApplicationContext().getBean("messageService");
		
		List<UserMessage> messageList = messageService.getMessageNotRead(userid);
		for(UserMessage message : messageList){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", message.getType());
			map.put("sourceId", message.getFromUser().getId());
			map.put("sourceName", message.getFromUser().getUsername());
			map.put("targetId", message.getToUser().getId());
			map.put("targetName", message.getToUser().getUsername());
			if(message.getType().equals(WebSocketMsgType.FRIEND_ADD_REQ.getSocketType())){
				//ûʲô�ر�Ҫ����
			}else if(message.getType().equals(WebSocketMsgType.GROUP_JOIN_REQ_FROM_GROUP.getSocketType())){
				map.put("groupId", message.getRelatedGroup().getId());
				map.put("groupname", message.getRelatedGroup().getGroupname());
			}else if(message.getType().equals(WebSocketMsgType.GROUP_JOIN_REQ_FROM_USER.getSocketType())){
				map.put("groupId", message.getRelatedGroup().getId());
				map.put("groupname", message.getRelatedGroup().getGroupname());
			}else if(message.getType().equals(WebSocketMsgType.MESSAGE_USER.getSocketType())){
				map.put("sendTime", new Date());
				map.put("text", message.getText());
			}
			JSONObject jsonObjOutput = JSONObject.fromObject(map);
			session.getBasicRemote().sendText(jsonObjOutput.toString());
		}
	}
 
	@OnClose
	public void onClose() {
		Storage.socketMap.remove(userid);
		System.out.println("Connection closed");
	}
}
