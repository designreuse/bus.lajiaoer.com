package com.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.ws.rs.FormParam;

/**
 * 司机审核
 * 
 * @author xuzhongliang
 *
 */
public class DriverAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uuid;

	@FormParam("name")
	private Short name;

	@FormParam("namereject")
	private String nameReject;

	@FormParam("cell")
	private Short cell;

	@FormParam("cellreject")
	private String cellReject;

	@FormParam("sfza")
	private Short sfzA;

	@FormParam("sfzareject")
	private String sfzAReject;

	@FormParam("sfzb")
	private Short sfzB;

	@FormParam("sfzbreject")
	private String sfzBReject;

	@FormParam("jsza")
	private Short jszA;

	@FormParam("jszareject")
	private String jszAReject;

	@FormParam("xsza")
	private Short xszA;

	@FormParam("xszareject")
	private String xszAReject;

	@FormParam("licenseplate")
	private Short licensePlate;

	@FormParam("licenseplatereject")
	private String licensePlateReject;

	@FormParam("driverplaceid")
	private Short driverPlaceId;

	@FormParam("dId")
	private Long driverId;

	private Date createdTime;

	private Timestamp updatedTime;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Short getName() {
		return name;
	}

	public void setName(Short name) {
		this.name = name;
	}

	public Short getCell() {
		return cell;
	}

	public void setCell(Short cell) {
		this.cell = cell;
	}

	public Short getSfzA() {
		return sfzA;
	}

	public void setSfzA(Short sfzA) {
		this.sfzA = sfzA;
	}

	public Short getSfzB() {
		return sfzB;
	}

	public void setSfzB(Short sfzB) {
		this.sfzB = sfzB;
	}

	public Short getJszA() {
		return jszA;
	}

	public void setJszA(Short jszA) {
		this.jszA = jszA;
	}

	public Short getXszA() {
		return xszA;
	}

	public void setXszA(Short xszA) {
		this.xszA = xszA;
	}

	public Short getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(Short licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Short getDriverPlaceId() {
		return driverPlaceId;
	}

	public void setDriverPlaceId(Short driverPlaceId) {
		this.driverPlaceId = driverPlaceId;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
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
		return "DriverAudit [uuid=" + uuid + ", name=" + name + ", nameReject=" + nameReject + ", cell=" + cell + ", cellReject=" + cellReject + ", sfzA=" + sfzA + ", sfzAReject="
				+ sfzAReject + ", sfzB=" + sfzB + ", sfzBReject=" + sfzBReject + ", jszA=" + jszA + ", jszAReject=" + jszAReject + ", xszA=" + xszA + ", xszAReject=" + xszAReject
				+ ", licensePlate=" + licensePlate + ", licensePlateReject=" + licensePlateReject + ", driverPlaceId=" + driverPlaceId + ", driverId=" + driverId + ", createdTime="
				+ createdTime + ", updatedTime=" + updatedTime + "]";
	}

	public String getNameReject() {
		return nameReject;
	}

	public void setNameReject(String nameReject) {
		this.nameReject = nameReject;
	}

	public String getCellReject() {
		return cellReject;
	}

	public void setCellReject(String cellReject) {
		this.cellReject = cellReject;
	}

	public String getSfzAReject() {
		return sfzAReject;
	}

	public void setSfzAReject(String sfzAReject) {
		this.sfzAReject = sfzAReject;
	}

	public String getSfzBReject() {
		return sfzBReject;
	}

	public void setSfzBReject(String sfzBReject) {
		this.sfzBReject = sfzBReject;
	}

	public String getJszAReject() {
		return jszAReject;
	}

	public void setJszAReject(String jszAReject) {
		this.jszAReject = jszAReject;
	}

	public String getXszAReject() {
		return xszAReject;
	}

	public void setXszAReject(String xszAReject) {
		this.xszAReject = xszAReject;
	}

	public String getLicensePlateReject() {
		return licensePlateReject;
	}

	public void setLicensePlateReject(String licensePlateReject) {
		this.licensePlateReject = licensePlateReject;
	}

	public static short approve() {
		return AuditStatus.APPROVE.getCode();
	}

	public static short reject() {
		return AuditStatus.REJECT.getCode();
	}

	private static enum AuditStatus {
		APPROVE(Short.valueOf("1")), REJECT(Short.valueOf("2")), WAITING(Short.valueOf("0"));
		private AuditStatus(short code) {
			this.code = code;
		}

		private short code;

		public short getCode() {
			return code;
		}
	}
}
