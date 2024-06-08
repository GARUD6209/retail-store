package com.amstech.retail.dao;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.amstech.retail.dto.OrderDTO;
import com.amstech.retail.util.DBUtil;

public class OrderDAO {
	private final String ORDER_DETAIL_INSERT_DATA = "INSERT INTO order_detail(order_number,total_amt, status, customer_name, customer_number, create_datetime, update_datetime) VALUES(?,?, ?, ?, ?, now(), now())";
	private final String ORDER_ITEM_INSERT_DATA = "INSERT INTO order_item(order_detail_id, item_id, price_at_order,quantity, create_datetime, update_datetime) VALUES(?, ?, ?,?, now(), now())";

	private final String FIND_ALL_ORDERS_BY_STORE_ID = "SELECT od.id, od.order_number, od.total_amt, od.status, od.customer_name, od.customer_number, od.create_datetime, od.update_datetime "
			+ "FROM order_detail od " + "JOIN order_item oi ON od.id = oi.order_detail_id "
			+ "JOIN item i ON oi.item_id = i.id " + "WHERE i.store_info_id = ? " + "GROUP BY od.id "
			+ "ORDER BY od.create_datetime DESC";

	private final String FIND_ORDER_BY_ORDER_NUMBER = "SELECT * FROM order_detail WHERE order_number = ?";

	private String FIND_ORDER_ITEM_TABLE_USING_ORDER_DETAIL_ID = "SELECT oi.item_id, oi.quantity, oi.price_at_order, i.name "
			+ "FROM order_item oi " + "JOIN item i ON oi.item_id = i.id " + "WHERE oi.order_detail_id = ?";

	private final String FIND_ORDERS_BY_DATE_RANGE = "SELECT * FROM order_detail WHERE create_datetime BETWEEN ? AND ? ORDER BY create_datetime DESC";

	private DBUtil dbUtil;

	public OrderDAO(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	public int save(OrderDTO orderDTO) throws Exception {
		Connection connection = null;
		PreparedStatement orderDetailPstmt = null;
		PreparedStatement orderItemPstmt = null;

		try {
			// Step 1: Making connection
			connection = dbUtil.getConnection();
			connection.setAutoCommit(false); // Ensure auto commit is disabled

			// Step 2: Prepared statement for order detail
			orderDetailPstmt = connection.prepareStatement(ORDER_DETAIL_INSERT_DATA,
					PreparedStatement.RETURN_GENERATED_KEYS);
			orderDetailPstmt.setString(1, orderDTO.getOrderNumber());
			orderDetailPstmt.setDouble(2, orderDTO.getTotalAmount());
			orderDetailPstmt.setString(3, orderDTO.getStatus());
			orderDetailPstmt.setString(4, orderDTO.getCustomerName());
			orderDetailPstmt.setString(5, orderDTO.getCustomerNumber());

			// Step 3: Execute update
			int count = orderDetailPstmt.executeUpdate();

			// Get generated order detail ID
			int orderDetailId;
			try (var generatedKeys = orderDetailPstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					orderDetailId = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating order_detail failed, no ID obtained.");
				}
			}

			// Step 4: Prepared statement for order items
			orderItemPstmt = connection.prepareStatement(ORDER_ITEM_INSERT_DATA);
			for (int i = 0; i < orderDTO.getOrderItemIds().length; i++) {
				orderItemPstmt.setInt(1, orderDetailId);
				orderItemPstmt.setInt(2, Integer.parseInt(orderDTO.getOrderItemIds()[i]));
				orderItemPstmt.setDouble(3, Double.parseDouble(orderDTO.getPriceAtOrder()[i]));
				orderItemPstmt.setInt(4, Integer.parseInt(orderDTO.getQuantities()[i])); // Assuming quantities are
																							// stored as doubles for
																							// price
				orderItemPstmt.addBatch();
			}

			// Execute batch for order items
			orderItemPstmt.executeBatch();

			// Commit the transaction
			connection.commit();

			return count;
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException rollbackEx) {
					throw new Exception("Rollback failed", rollbackEx);
				}
			}
			throw e;
		} finally {
			// Step 5: Close connections
			if (connection != null) {
				try {
					connection.setAutoCommit(true); // Reset auto commit to true before closing
					connection.close();
				} catch (SQLException e) {
					throw new Exception("Closing connection failed", e);
				}
			}
			if (orderDetailPstmt != null) {
				try {
					orderDetailPstmt.close();
				} catch (SQLException e) {
					throw new Exception("Closing PreparedStatement failed", e);
				}
			}
			if (orderItemPstmt != null) {
				try {
					orderItemPstmt.close();
				} catch (SQLException e) {
					throw new Exception("Closing PreparedStatement failed", e);
				}
			}
		}
	}

	public List<OrderDTO> findAllOrdersByStoreId(int storeId) throws Exception {
		Connection connection = null;
		PreparedStatement findOrdersPstmt = null;
		ResultSet rs = null;
		List<OrderDTO> orders = new ArrayList<>();

		try {
			connection = dbUtil.getConnection();
			findOrdersPstmt = connection.prepareStatement(FIND_ALL_ORDERS_BY_STORE_ID);
			findOrdersPstmt.setInt(1, storeId);
			rs = findOrdersPstmt.executeQuery();

			while (rs.next()) {
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setOrderId(rs.getInt("id"));
				orderDTO.setOrderNumber(rs.getString("order_number"));
				orderDTO.setTotalAmount(rs.getDouble("total_amt"));
				orderDTO.setStatus(rs.getString("status"));
				orderDTO.setCustomerName(rs.getString("customer_name"));
				orderDTO.setCustomerNumber(rs.getString("customer_number"));
				orderDTO.setOCreateDateTime(rs.getTimestamp("create_datetime"));
				orderDTO.setOUpdateDateTime(rs.getTimestamp("update_datetime"));

				orders.add(orderDTO);
			}
			System.out.println("Orders retrieved from database: " + orders);
			return orders;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new Exception("Closing ResultSet failed", e);
				}
			}
			if (findOrdersPstmt != null) {
				try {
					findOrdersPstmt.close();
				} catch (SQLException e) {
					throw new Exception("Closing PreparedStatement failed", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new Exception("Closing connection failed", e);
				}
			}
		}
	}

	public OrderDTO findOrderDetailsByOrderNumber(String orderNumber) throws Exception {
		Connection connection = null;
		PreparedStatement findOrderPstmt = null;
		PreparedStatement findOrderItemsPstmt = null;
		ResultSet rsOrder = null;
		ResultSet rsOrderItems = null;

		try {
			connection = dbUtil.getConnection();

			// Query to find the order
			findOrderPstmt = connection.prepareStatement(FIND_ORDER_BY_ORDER_NUMBER);
			findOrderPstmt.setString(1, orderNumber);
			rsOrder = findOrderPstmt.executeQuery();

			if (rsOrder.next()) {
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setOrderId(rsOrder.getInt("id"));
				orderDTO.setOrderNumber(rsOrder.getString("order_number"));
				orderDTO.setTotalAmount(rsOrder.getDouble("total_amt"));
				orderDTO.setStatus(rsOrder.getString("status"));
				orderDTO.setCustomerName(rsOrder.getString("customer_name"));
				orderDTO.setCustomerNumber(rsOrder.getString("customer_number"));
				orderDTO.setOCreateDateTime(rsOrder.getTimestamp("create_datetime"));
				orderDTO.setOUpdateDateTime(rsOrder.getTimestamp("update_datetime"));

				// Query to find the order items
				findOrderItemsPstmt = connection.prepareStatement(FIND_ORDER_ITEM_TABLE_USING_ORDER_DETAIL_ID);
				findOrderItemsPstmt.setInt(1, orderDTO.getOrderId());
				rsOrderItems = findOrderItemsPstmt.executeQuery();

				List<String> itemIds = new ArrayList<>();
				List<String> itemNames = new ArrayList<>();
				List<String> quantities = new ArrayList<>();
				List<String> priceAtOrder = new ArrayList<>();

				while (rsOrderItems.next()) {
					itemIds.add(rsOrderItems.getString("item_id"));
					itemNames.add(rsOrderItems.getString("name"));
					quantities.add(rsOrderItems.getString("quantity"));
					priceAtOrder.add(rsOrderItems.getString("price_at_order"));
				}

				orderDTO.setOrderItemIds(itemIds.toArray(new String[0]));
				orderDTO.setOrderItemNames(itemNames.toArray(new String[0]));
				orderDTO.setQuantities(quantities.toArray(new String[0]));
				orderDTO.setPriceAtOrder(priceAtOrder.toArray(new String[0]));

				return orderDTO;
			} else {
				return null; // Order not found
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rsOrderItems != null) {
				try {
					rsOrderItems.close();
				} catch (SQLException e) {
					throw new Exception("Closing ResultSet for order items failed", e);
				}
			}
			if (rsOrder != null) {
				try {
					rsOrder.close();
				} catch (SQLException e) {
					throw new Exception("Closing ResultSet for order failed", e);
				}
			}
			if (findOrderItemsPstmt != null) {
				try {
					findOrderItemsPstmt.close();
				} catch (SQLException e) {
					throw new Exception("Closing PreparedStatement for order items failed", e);
				}
			}
			if (findOrderPstmt != null) {
				try {
					findOrderPstmt.close();
				} catch (SQLException e) {
					throw new Exception("Closing PreparedStatement for order failed", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new Exception("Closing connection failed", e);
				}
			}
		}
	}

	public List<OrderDTO> findOrdersByDateRange(Date startDate, Date endDate) throws Exception {
		// Check if the date range is between 1 day and 1 month
		long millisecondsInADay = 24 * 60 * 60 * 1000;
		long millisecondsInAMonth = 30L * millisecondsInADay;

		if (endDate.getTime() - startDate.getTime() < millisecondsInADay) {
			throw new IllegalArgumentException("The date range must be at least 1 day.");
		}
		if (endDate.getTime() - startDate.getTime() > millisecondsInAMonth) {
			throw new IllegalArgumentException("The date range must be at most 1 month.");
		}

		Connection connection = null;
		PreparedStatement findOrdersPstmt = null;
		ResultSet rs = null;
		List<OrderDTO> orders = new ArrayList<>();

		try {
			connection = dbUtil.getConnection();
			findOrdersPstmt = connection.prepareStatement(FIND_ORDERS_BY_DATE_RANGE);
			findOrdersPstmt.setDate(1, (java.sql.Date) startDate);
			findOrdersPstmt.setDate(2, (java.sql.Date) endDate);
			rs = findOrdersPstmt.executeQuery();

			while (rs.next()) {
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setOrderId(rs.getInt("id"));
				orderDTO.setOrderNumber(rs.getString("order_number"));
				orderDTO.setTotalAmount(rs.getDouble("total_amt"));
				orderDTO.setStatus(rs.getString("status"));
				orderDTO.setCustomerName(rs.getString("customer_name"));
				orderDTO.setCustomerNumber(rs.getString("customer_number"));
				orderDTO.setOCreateDateTime(rs.getTimestamp("create_datetime"));
				orderDTO.setOUpdateDateTime(rs.getTimestamp("update_datetime"));

				orders.add(orderDTO);
			}
			System.out.println("Orders retrieved from database: " + orders);
			return orders;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new Exception("Closing ResultSet failed", e);
				}
			}
			if (findOrdersPstmt != null) {
				try {
					findOrdersPstmt.close();
				} catch (SQLException e) {
					throw new Exception("Closing PreparedStatement failed", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new Exception("Closing connection failed", e);
				}
			}
		}
	}

}
