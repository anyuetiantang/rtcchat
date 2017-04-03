package com.rtcchat.tools;

public enum ErrorType {
	ERROR_SUCCESS("200","操作成功"),
	ERROR_EXIST("6000","用户已存在"),
	ERROR_NOEXIST("6001","用户不存在"),
	ERROR_LOGIN("6002","账号或密码错误");
	
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
