package com.iss.team1.safe.checkin.model;

public class SafeResponse {
	public static final int RESPONSE_CODE_SUCCESS = 0;

	private int code;

	private String msg;
	private Object result;

	public SafeResponse() {
		super();
	}

	public SafeResponse(int code, String msg, Object result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public SafeResponse(String msg) {
		this.msg = msg;
	}

	public static SafeResponse responseSuccess(String msg){
		return new SafeResponse(RESPONSE_CODE_SUCCESS, msg, null);
	}

	public static SafeResponse responseFail(int code, String msg){
		return new SafeResponse(code, msg, null);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
