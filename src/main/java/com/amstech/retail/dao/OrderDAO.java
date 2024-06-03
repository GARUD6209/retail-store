package com.amstech.retail.dao;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amstech.retail.dto.OrderDTO;
import com.amstech.retail.util.DBUtil;

public class OrderDAO {
	private final String ORDER_DETAIL_INSERT_DATA = "INSERT INTO order_detail(total_amt, status, customer_name, customer_number, create_datetime, update_datetime) VALUES(?, ?, ?, ?, now(), now())";
	private final String ORDER_ITEM_INSERT_DATA = "INSERT INTO order_item(order_detail_id, item_id, price_at_order,quantity, create_datetime, update_datetime) VALUES(?, ?, ?,?, now(), now())";

	  private final String FIND_ALL_ORDERS_BY_STORE_ID = 
		        "SELECT od.id, od.total_amt, od.status, od.customer_name, od.customer_number, od.create_datetime, od.update_datetime " +
		        "FROM order_detail od " +
		        "JOIN order_item oi ON od.id = oi.order_detail_id " +
		        "JOIN item i ON oi.item_id = i.id " +
		        "WHERE i.store_info_id = ? " +
		        "GROUP BY od.id "+
		        "ORDER BY od.create_datetime DESC";

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
			connection.setAutoCommit(false); // Ensure autocommit is disabled

			// Step 2: Prepared statement for order detail
			orderDetailPstmt = connection.prepareStatement(ORDER_DETAIL_INSERT_DATA,
					PreparedStatement.RETURN_GENERATED_KEYS);
			orderDetailPstmt.setDouble(1, orderDTO.getTotalAmount());
			orderDetailPstmt.setString(2, orderDTO.getStatus());
			orderDetailPstmt.setString(3, orderDTO.getCustomerName());
			orderDetailPstmt.setString(4, orderDTO.getCustomerNumber());

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
					connection.setAutoCommit(true); // Reset autocommit to true before closing
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
