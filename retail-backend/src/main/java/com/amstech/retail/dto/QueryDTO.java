package com.amstech.retail.dto;

import java.sql.Timestamp;

public class QueryDTO {
	private int id;
	private int storeInfoID;
	private String name;
	private String mobileNumber;
	private String description;
	private Timestamp createDateTime;
	private Timestamp updateDateTime;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStoreInfoID() {
		return storeInfoID;
	}
	public void setStoreInfoID(int storeInfoID) {
		this.storeInfoID = storeInfoID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobile_number) {
		this.mobileNumber = mobile_number;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}
	public Timestamp getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	
	
}
