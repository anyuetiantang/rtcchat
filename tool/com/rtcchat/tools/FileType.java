package com.rtcchat.tools;

/**
 * 
 * @author anyuetiantang
 *	定义文件类型，是群组文件还是用户私聊文件
 */

public enum FileType {
	user,group;
	
	public FileType getUser(){
		return FileType.user;
	}
	
	public FileType getGroup(){
		return FileType.group;
	}
}
