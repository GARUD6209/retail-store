package com.amstech.retail.service;

import com.amstech.retail.dao.OrderItemDAO;
import com.amstech.retail.dto.OrderItemDTO;

public class OrderItemService {
	
	private OrderItemDAO orderItemDAO;
	
	public OrderItemService(OrderItemDAO orderItemDAO) {
		this.orderItemDAO = orderItemDAO;
	}

	public int save(OrderItemDTO orderItemDTO) throws Exception {
		return orderItemDAO.save(orderItemDTO);
		
	}
}
