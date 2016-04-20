package com.demo.enums;

public enum UserLoginResultTypeEnum {

	EMAIL_IS_BLANK(10001, "邮箱不能为空"), PASSWORD_IS_BLANK(10002, "密码不能为空"), EMAIL_IS_NOT_EXIST(10003, "邮箱不存在"), PASSWORD_IS_WRONG(10004, "密码不正确"), EMAIL_IS_EXIST(10005,
			"邮箱已存在"), USERNAME_IS_BLANK(10006, "用户名不能为空"), EMAIL_IS_NOT_ACTIVED(10007, "账号未激活"), EMAIL_IS_NOT_USED(10008, "账号被禁用");
	private UserLoginResultTypeEnum(final int code, final String msg) {
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
