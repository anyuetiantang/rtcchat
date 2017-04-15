package com.rtcchat.tools;

/**
 * 
 * @author anyuetiantang
 *
 *	定义WebSocket消息类型
 */

public enum WebSocketMsgType {
	ERROR("error","发生错误"),
	FRIEND_ADD_REQ("friendAddReq","添加好友请求"),
	FRIEND_ADD_RES("friendAddRes","添加好友回复"),
	FRIEND_ADD_RES_SUC("friendAddResSuc","添加好友回复之后的处理"),
	FRIEND_DELETE_REQ("friendDeleteReq","删除好友请求"),
	GROUP_JOIN_REQ_FROM_GROUP("groupJoinReqFromGroup","邀请某人加入群组请求"),
	GROUP_JOIN_RES_FROM_GROUP("groupJoinResFromGroup","邀请某人加入群组回复"),
	GROUP_JOIN_RES_FROM_GROUP_SUC("groupJoinResFromGroupSuc","邀请某人加入群组回复之后的处理"),
	GROUP_USER_DELETE("groupUserDelete","群组用户踢出"),
	GROUP_USER_DELETE_RES("groupUserDelete","群组用户踢出的回应"),
	GROUP_JOIN_REQ_FROM_USER("groupJoinReqFromUser","某人请求加入群组请求"),
	GROUP_JOIN_RES_FROM_USER("groupJoinResFromUser","某人请求加入群组请求"),
	GROUP_JOIN_RES_FROM_USER_SUC("groupJoinResFromUserSuc","某人请求加入群组请求回复之后的处理"),
	GROUP_USER_EXIT("groupUserExit","群组用户退出"),
	GROUP_USER_EXIT_RES("groupUserExitRes","群组用户退出的回应");
	
	private String socketType;
	private String description;
	public String getSocketType() {
		return socketType;
	}
	public void setSocketType(String socketType) {
		this.socketType = socketType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	private WebSocketMsgType(String socketType,String description){
		this.socketType = socketType;
		this.description = description;
	}
}
