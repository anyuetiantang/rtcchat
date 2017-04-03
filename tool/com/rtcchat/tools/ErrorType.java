package com.rtcchat.tools;

public enum ErrorType {
	ERROR_SUCCESS("200","�����ɹ�"),
	ERROR_EXIST("6000","�û��Ѵ���"),
	ERROR_NOEXIST("6001","�û�������"),
	ERROR_LOGIN("6002","�˺Ż��������");
	
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
