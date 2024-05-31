package com.amstech.retail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.amstech.retail.dto.OrderItemDTO;

import com.amstech.retail.util.DBUtil;

public class OrderItemDAO {
	
	private final String ORDER_ITEM_INSERT_DATA = "Insert into order_item(order_detail_id,item_id,price_at_order,create_datetime,update_datetime) values(?,?,?,now(),now()";

	
	private DBUtil dbUtil;

  	public OrderItemDAO(DBUtil dbUtil) {
  		this.dbUtil = dbUtil;
  	}
      
      
      public int save(OrderItemDTO orderItemDTO) throws Exception {
  		Connection connection = null;

  		PreparedStatement pstmt = null;

  		try {
  			// step 1 making connection

  			connection = dbUtil.getConnection();

  			// step 2 prepared statement

  			pstmt = connection.prepareStatement(ORDER_ITEM_INSERT_DATA);
  			
  			pstmt.setInt(1, orderItemDTO.getOrderDetailId());
  			pstmt.setInt(2, orderItemDTO.getItemId());  			
  			pstmt.setDouble(3, orderItemDTO.getPriceAtOrder());
  			
  			

  			// step 3 execution

  			int count = pstmt.executeUpdate();

  			return count;
  		} catch (Exception e) {
  			throw e;
  		} finally {
  			// step 4 connection close

  			dbUtil.getClose(connection, pstmt);

  		}
  		
  		
  	}
}


