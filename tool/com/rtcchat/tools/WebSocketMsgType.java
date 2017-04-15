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
	FRIEND_DELETE_REQ("friendDeleteReq","ɾ����������"),
	GROUP_JOIN_REQ_FROM_GROUP("groupJoinReqFromGroup","����ĳ�˼���Ⱥ������"),
	GROUP_JOIN_RES_FROM_GROUP("groupJoinResFromGroup","����ĳ�˼���Ⱥ��ظ�"),
	GROUP_JOIN_RES_FROM_GROUP_SUC("groupJoinResFromGroupSuc","����ĳ�˼���Ⱥ��ظ�֮��Ĵ���"),
	GROUP_USER_DELETE("groupUserDelete","Ⱥ���û��߳�"),
	GROUP_USER_DELETE_RES("groupUserDelete","Ⱥ���û��߳��Ļ�Ӧ"),
	GROUP_JOIN_REQ_FROM_USER("groupJoinReqFromUser","ĳ���������Ⱥ������"),
	GROUP_JOIN_RES_FROM_USER("groupJoinResFromUser","ĳ���������Ⱥ������"),
	GROUP_JOIN_RES_FROM_USER_SUC("groupJoinResFromUserSuc","ĳ���������Ⱥ������ظ�֮��Ĵ���"),
	GROUP_USER_EXIT("groupUserExit","Ⱥ���û��˳�"),
	GROUP_USER_EXIT_RES("groupUserExitRes","Ⱥ���û��˳��Ļ�Ӧ");
	
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
