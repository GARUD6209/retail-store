package com.amstech.retail.service;

import java.util.List;

import com.amstech.retail.dao.OrderDAO;

import com.amstech.retail.dto.OrderDTO;

public class OrderService {

	private OrderDAO orderDAO;

	public OrderService(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public int save(OrderDTO orderDTO) throws Exception {
		return orderDAO.save(orderDTO);

	}

	public List<OrderDTO> findAllOrdersByStoreId(int storeId) throws Exception {
		return orderDAO.findAllOrdersByStoreId(storeId);
	}
}
