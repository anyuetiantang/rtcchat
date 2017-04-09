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
	FRIEND_DELETE_REQ("friendDeleteReq","删除好友请求");
	
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
