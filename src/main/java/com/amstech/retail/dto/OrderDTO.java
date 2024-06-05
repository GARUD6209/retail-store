package com.amstech.retail.dto;

import java.sql.Timestamp;

public class OrderDTO {
	private int orderId;
	private double totalAmount;
	private String status;
	private String customerName;
	private String customerNumber;
	private Timestamp OCreateDateTime;
	private Timestamp OUpdateDateTime;
	private int orderDetailId;
	private int itemId;
	private String[] priceAtOrder;
	private Timestamp ItemCreateDateTime;
	private Timestamp ItemUpdateDateTime;
	private String[] orderItemIds;
	private String[] quantities;
	private String[] orderItemNames;
	 private String orderNumber; // New field for the unique order number
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public Timestamp getOCreateDateTime() {
		return OCreateDateTime;
	}
	public void setOCreateDateTime(Timestamp oCreateDateTime) {
		OCreateDateTime = oCreateDateTime;
	}
	public Timestamp getOUpdateDateTime() {
		return OUpdateDateTime;
	}
	public void setOUpdateDateTime(Timestamp oUpdateDateTime) {
		OUpdateDateTime = oUpdateDateTime;
	}
	public int getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String[] getPriceAtOrder() {
		return priceAtOrder;
	}
	public void setPriceAtOrder(String[] priceAtOrder) {
		this.priceAtOrder = priceAtOrder;
	}
	public Timestamp getItemCreateDateTime() {
		return ItemCreateDateTime;
	}
	public void setItemCreateDateTime(Timestamp itemCreateDateTime) {
		ItemCreateDateTime = itemCreateDateTime;
	}
	public Timestamp getItemUpdateDateTime() {
		return ItemUpdateDateTime;
	}
	public void setItemUpdateDateTime(Timestamp itemUpdateDateTime) {
		ItemUpdateDateTime = itemUpdateDateTime;
	}
	public String[] getOrderItemIds() {
		return orderItemIds;
	}
	public void setOrderItemIds(String[] orderItemIds) {
		this.orderItemIds = orderItemIds;
	}
	public String[] getQuantities() {
		return quantities;
	}
	public void setQuantities(String[] quantities) {
		this.quantities = quantities;
	}
	public String[] getOrderItemNames() {
		return orderItemNames;
	}
	public void setOrderItemNames(String[] orderItemNames) {
		this.orderItemNames = orderItemNames;
	}
	
	 public String getOrderNumber() {
	        return orderNumber;
	    }

	    public void setOrderNumber(String orderNumber) {
	        this.orderNumber = orderNumber;
	    }
	
	
	

}
