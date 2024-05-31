package com.amstech.retail.dto;

import java.security.Timestamp;

public class ItemDTO {

	private int id;
	private int storeInfoId;
	private String name;
	private double current_price;
	private String description;
	private Timestamp createDateTime;
	private Timestamp updateDateTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStoreInfoId() {
		return storeInfoId;
	}
	public void setStoreInfoId(int storeInfoId) {
		this.storeInfoId = storeInfoId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(double current_price) {
		this.current_price = current_price;
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
