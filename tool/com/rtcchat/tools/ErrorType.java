package com.rtcchat.tools;

public enum ErrorType {
	ERROR_SUCCESS("200","�����ɹ�"),
	ERROR_PARAM("400","�����������"),
	ERROR_UNKNOWN("404","����δ֪����"),
	ERROR_NONE("500","��ѯ���Ϊ��"),
	ERROR_USEREXIST("6000","�û��Ѵ���"),
	ERROR_USERNOEXIST("6001","�û�������"),
	ERROR_LOGIN("6002","�˺Ż��������"),
	ERROR_HEADIMG("6003","����ͷ��ʧ��"),
	ERROR_PASSWORDERROR("6004","ԭ���벻��ȷ"),
	ERROR_GROUPEXIST("6005","ͬ��Ⱥ���Ѵ���"),
	ERROR_GROUPNOEXIST("6006","��Ⱥ�鲻����"),
	ERROR_ONLINE("6007","�û��Ѿ�����");
	
	private String code;
	private String msg;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private ErrorType(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
