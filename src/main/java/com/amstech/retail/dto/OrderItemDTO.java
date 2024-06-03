package com.amstech.retail.dto;

import java.sql.Timestamp;

public class OrderItemDTO {
	private int orderItemId;
	private int orderDetailId;
	private int itemId;
	private double priceAtOrder;
	private Timestamp ItemCreateDateTime;
	private Timestamp ItemUpdateDateTime;
	
	
	
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
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
	public double getPriceAtOrder() {
		return priceAtOrder;
	}
	public void setPriceAtOrder(double priceAtOrder) {
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
	
	
	
}
