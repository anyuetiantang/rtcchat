package com.rtcchat.tools;

/**
 * 
 * @author anyuetiantang
 *	�����ļ����ͣ���Ⱥ���ļ������û�˽���ļ�
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
