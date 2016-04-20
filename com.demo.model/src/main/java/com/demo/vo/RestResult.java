package com.demo.vo;

import java.io.Serializable;

import com.demo.enums.RestResultTypeEnum;

public class RestResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code = RestResultTypeEnum.SUCCESS.getCode();

	private String msg = RestResultTypeEnum.SUCCESS.getMsg();

	private Object data;

	public RestResult() {

	}

	public RestResult(RestResultTypeEnum restResultTypeEnum) {
		this.code = restResultTypeEnum.getCode();
		this.msg = restResultTypeEnum.getMsg();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "AjaxResult [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

	public static void main(String[] args) {
		RestResult ajaxResult = new RestResult();
		ajaxResult.setCode(RestResultTypeEnum.SUCCESS.getCode());
		ajaxResult.setMsg("woo!");
		ajaxResult.setData("haha");
		System.out.println(ajaxResult);

		RestResult ajaxResult1 = new RestResult(RestResultTypeEnum.ERROR);
		System.out.println(ajaxResult1);
	}
}
