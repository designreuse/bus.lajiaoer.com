package com.demo.model.admin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 后台管理登录用户
 * 
 * @author xuzhongliang
 *
 */
public class AdminUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String realName;

	private String password;

	private short enabled;

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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public short getEnabled() {
		return enabled;
	}

	public void setEnabled(short enabled) {
		this.enabled = enabled;
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
		return "User [id=" + id + ", name=" + name + ", realName=" + realName + ", password=" + password + ", enabled=" + enabled + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + "]";
	}
}
