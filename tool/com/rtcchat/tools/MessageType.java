package com.rtcchat.tools;

/**
 * 
 * @author anyuetiantang
 *	������Ϣ���ͣ��Ƿ��Ѿ�����ȡ
 */

public enum MessageType {
	read,noread;
	
	public MessageType getRead(){
		return MessageType.read;
	}
	
	public MessageType getNoread(){
		return MessageType.noread;
	}
}
