package com.rtcchat.tools;

/**
 * 
 * @author anyuetiantang
 *	������Ϣ���ͣ��Ƿ��Ѿ�����ȡ
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
