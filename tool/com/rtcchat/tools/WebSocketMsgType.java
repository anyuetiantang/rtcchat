package com.rtcchat.tools;

/**
 * 
 * @author anyuetiantang
 *
 *	����WebSocket��Ϣ����
 */

public enum WebSocketMsgType {
	ERROR("error","��������"),
	FRIEND_ADD_REQ("friendAddReq","��Ӻ�������"),
	FRIEND_ADD_RES("friendAddRes","��Ӻ��ѻظ�"),
	FRIEND_ADD_RES_SUC("friendAddResSuc","��Ӻ��ѻظ�֮��Ĵ���"),
	FRIEND_DELETE_REQ("friendDeleteReq","ɾ����������");
	
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
