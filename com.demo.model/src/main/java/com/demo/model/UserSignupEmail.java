package com.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class UserSignupEmail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long userId;
	private String token;
	private Date createdTime;
	private Timestamp updatedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	@Override
	public String toString() {
		return "SignupEmail [id=" + id + ", userId=" + userId + ", token=" + token + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + "]";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
