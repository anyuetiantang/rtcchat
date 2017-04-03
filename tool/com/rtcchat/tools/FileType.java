package com.rtcchat.tools;

/**
 * 
 * @author anyuetiantang
 *	定义文件类型，是群组文件还是用户私聊文件
 */

public enum FileType {
	FILE_TYPE_USER("user"),
	FILE_TYPE_GROUP("group");
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	private FileType(String type){
		this.type = type;
	}
}
