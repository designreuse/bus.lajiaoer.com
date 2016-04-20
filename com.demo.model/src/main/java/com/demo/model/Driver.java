package com.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Driver implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String cell;

	private String password;

	private Short actived;

	private Short used;

	private String sfzA;

	private String sfzB;

	private String jszA;

	private String xszA;

	private String licensePlate;

	private Long driverPlaceId;

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

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
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
		return "Driver [id=" + id + ", name=" + name + ", cell=" + cell + ", password=" + password + ", actived=" + actived + ", used=" + used + ", sfzA=" + sfzA + ", sfzB=" + sfzB
				+ ", jszA=" + jszA + ", xszA=" + xszA + ", licensePlate=" + licensePlate + ", driverPlaceId=" + driverPlaceId + ", createdTime=" + createdTime + ", updatedTime="
				+ updatedTime + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Short getActived() {
		return actived;
	}

	public void setActived(Short actived) {
		this.actived = actived;
	}

	public Short getUsed() {
		return used;
	}

	public void setUsed(Short used) {
		this.used = used;
	}

	public String getJszA() {
		return jszA;
	}

	public void setJszA(String jszA) {
		this.jszA = jszA;
	}

	public String getSfzB() {
		return sfzB;
	}

	public void setSfzB(String sfzB) {
		this.sfzB = sfzB;
	}

	public String getSfzA() {
		return sfzA;
	}

	public void setSfzA(String sfzA) {
		this.sfzA = sfzA;
	}

	public Long getDriverPlaceId() {
		return driverPlaceId;
	}

	public void setDriverPlaceId(Long driverPlaceId) {
		this.driverPlaceId = driverPlaceId;
	}

	public String getXszA() {
		return xszA;
	}

	public void setXszA(String xszA) {
		this.xszA = xszA;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

}
