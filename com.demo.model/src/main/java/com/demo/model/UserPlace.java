package com.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class UserPlace implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Double lat;
	private Double lon;
	private Date createdTime;
	private Timestamp updatedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
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
		return "UserPlace [id=" + id + ", name=" + name + ", lat=" + lat + ", lon=" + lon + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + "]";
	}
}
