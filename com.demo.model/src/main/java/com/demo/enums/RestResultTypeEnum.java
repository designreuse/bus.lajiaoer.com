package com.demo.enums;

public enum RestResultTypeEnum implements ResultType {

	SUCCESS(200, "成功"), ERROR(500, "内部错误");

	private RestResultTypeEnum(final int code, final String msg) {
		this.setCode(code);
		this.setMsg(msg);
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

	private int code;
	private String msg;
}
