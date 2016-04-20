package com.demo.vo;

import com.demo.model.User;

public class UserVo extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String token;
	private Long seId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "UserVo [token=" + token + ", seId=" + seId + ", getId()=" + getId() + ", getUserName()=" + getUserName() + ", getCreatedTime()=" + getCreatedTime()
				+ ", getUpdatedTime()=" + getUpdatedTime() + ", toString()=" + super.toString() + ", getEmail()=" + getEmail() + ", getPassword()=" + getPassword()
				+ ", getActived()=" + getActived() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

	public Long getSeId() {
		return seId;
	}

	public void setSeId(Long seId) {
		this.seId = seId;
	}
}
