package com.demo.model;

import java.io.Serializable;

public class UserReserve implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long userId;

	private Long placeId;

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

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	@Override
	public String toString() {
		return "UserReserve [id=" + id + ", userId=" + userId + ", placeId=" + placeId + "]";
	}
}
