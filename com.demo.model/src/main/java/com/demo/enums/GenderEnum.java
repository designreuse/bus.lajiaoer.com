package com.demo.enums;

public enum GenderEnum {

	MALE(1), FEMALE(2);

	private GenderEnum(final int flag) {
		this.setFlag(flag);
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	private int flag;

}
