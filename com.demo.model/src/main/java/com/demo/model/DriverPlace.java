package com.demo.model;

import java.io.Serializable;

public class DriverPlace implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String aName;

	private String aNameDetail;

	private Short aCityId;

	private String aCityName;

	private String bName;

	private String bNameDetail;

	private Short bCityId;

	private String bCityName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public String getaNameDetail() {
		return aNameDetail;
	}

	public void setaNameDetail(String aNameDetail) {
		this.aNameDetail = aNameDetail;
	}

	public Short getaCityId() {
		return aCityId;
	}

	public void setaCityId(Short aCityId) {
		this.aCityId = aCityId;
	}

	public String getaCityName() {
		return aCityName;
	}

	public void setaCityName(String aCityName) {
		this.aCityName = aCityName;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getbNameDetail() {
		return bNameDetail;
	}

	public void setbNameDetail(String bNameDetail) {
		this.bNameDetail = bNameDetail;
	}

	public Short getbCityId() {
		return bCityId;
	}

	public void setbCityId(Short bCityId) {
		this.bCityId = bCityId;
	}

	public String getbCityName() {
		return bCityName;
	}

	public void setbCityName(String bCityName) {
		this.bCityName = bCityName;
	}

	@Override
	public String toString() {
		return "DriverPlace [id=" + id + ", aName=" + aName + ", aNameDetail=" + aNameDetail + ", aCityId=" + aCityId + ", aCityName=" + aCityName + ", bName=" + bName
				+ ", bNameDetail=" + bNameDetail + ", bCityId=" + bCityId + ", bCityName=" + bCityName + "]";
	}

}
