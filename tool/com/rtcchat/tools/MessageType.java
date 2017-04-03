package com.rtcchat.tools;

/**
 * 
 * @author anyuetiantang
 *	定义消息类型，是否已经被读取
 */

public enum MessageType {
	MESSAGE_TYPE_READ("read"),
	MESSAGE_TYPE_NOREAD("noread");
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	private MessageType(String type){
		this.type = type;
	}
}
