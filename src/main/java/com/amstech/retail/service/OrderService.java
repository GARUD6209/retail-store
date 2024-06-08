package com.amstech.retail.service;

import java.util.Date;
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

	public OrderDTO findOrderDetailsByOrderNumber(String orderNumber) throws Exception {
        return orderDAO.findOrderDetailsByOrderNumber(orderNumber);
    }
	
	 public List<OrderDTO> findOrdersByDateRange(Date startDate, Date endDate) throws Exception {
	      
	        return orderDAO.findOrdersByDateRange(startDate, endDate);
	    }
}
