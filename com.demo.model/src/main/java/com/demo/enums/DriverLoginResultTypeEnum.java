package com.demo.enums;

public enum DriverLoginResultTypeEnum implements ResultType {

	CELL_IS_BLANK(10001, "手机号码不能为空"), PASSWORD_IS_BLANK(10002, "密码不能为空"), CELL_IS_NOT_EXIST(10003, "手机号码不存在"), PASSWORD_IS_WRONG(10004, "密码不正确"), CELL_IS_EXIST(10005,
			"手机号码已存在"), NAME_IS_BLANK(10006, "姓名不能为空"), CELL_IS_NOT_ACTIVED(10007, "账号未激活"), CELL_IS_NOT_USED(10008, "账号被禁用"), SFZ_A_IS_BLANK(10009,
					"身份证正面图片不能为空"), SFZ_B_IS_BLANK(10010, "身份证背面图片不能为空"), JSZ_A_IS_BLANK(10011, "驾驶证正面图片不能为空"), CAPTCHA_IS_BLANK(10012, "验证码不能为空"), CAPTCHA_IS_WRONG(10013,
							"验证码不正确"), CAPTCHA_IS_EXPIRED(10014, "验证码已过期，重新获取"), XSZ_A_IS_BLANK(10015, "行驶证正面图片不能为空"), LICENSE_PLATE_IS_BLANK(10016,
									"车牌号不能为空"), DRIVER_EDIT_FAILURE(10017, "保存司机信息失败"), DRIVER_NOT_EDIT(10018, "司机信息无修改");
	private DriverLoginResultTypeEnum(final int code, final String msg) {
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
