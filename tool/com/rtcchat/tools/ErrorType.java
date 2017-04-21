package com.rtcchat.tools;

public enum ErrorType {
	ERROR_SUCCESS("200","操作成功"),
	ERROR_PARAM("400","请求参数错误"),
	ERROR_UNKNOWN("404","发生未知错误"),
	ERROR_NONE("500","查询结果为空"),
	ERROR_USEREXIST("6000","用户已存在"),
	ERROR_USERNOEXIST("6001","用户不存在"),
	ERROR_LOGIN("6002","账号或密码错误"),
	ERROR_HEADIMG("6003","更新头像失败"),
	ERROR_PASSWORDERROR("6004","原密码不正确"),
	ERROR_GROUPEXIST("6005","同名群组已存在"),
	ERROR_GROUPNOEXIST("6006","此群组不存在"),
	ERROR_ONLINE("6007","用户已经在线");
	
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
