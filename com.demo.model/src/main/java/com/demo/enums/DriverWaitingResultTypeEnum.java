package com.demo.enums;

public enum DriverWaitingResultTypeEnum implements ResultType {

	COOKIE_CELL_IS_BLANK(10001, "请登录再执行操作"), CELL_IS_NOT_EXIST(10002, "该用户不存在"), DRIVER_WAITING_IS_NOT_EXIST(10003, "司机无等待信息"), DRIVER_WAITING_IS_EXIST(10004,
			"司机有等待信息"), DRIVER_WAITING_IS_PENDING(10005, "您的信息正在审核中");
	private DriverWaitingResultTypeEnum(final int code, final String msg) {
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
