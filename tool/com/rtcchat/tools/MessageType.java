package com.rtcchat.tools;

/**
 * 
 * @author anyuetiantang
 *	定义消息类型，是否已经被读取
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
