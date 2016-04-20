package com.demo.model;

import java.io.Serializable;
import java.util.Date;

public class DriverWaiting implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long driverId;

	private Long driverPlaceId;

	private Date startTime;

	private Date endTime;

	private short isStop;

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DriverWaiting [id=" + id + ", driverId=" + driverId + ", driverPlaceId=" + driverPlaceId + ", startTime=" + startTime + ", endTime=" + endTime + ", isStop="
				+ isStop + "]";
	}

	public short getIsStop() {
		return isStop;
	}

	public void setIsStop(short isStop) {
		this.isStop = isStop;
	}

	public Long getDriverPlaceId() {
		return driverPlaceId;
	}

	public void setDriverPlaceId(Long driverPlaceId) {
		this.driverPlaceId = driverPlaceId;
	}

}
