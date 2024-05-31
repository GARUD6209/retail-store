package com.amstech.retail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.amstech.retail.dto.OrderDetailDTO;
import com.amstech.retail.util.DBUtil;

public class OrderDetailDAO {
	private final String ORDER_DETAIL_INSERT_DATA = "Insert into order_detail(total_amt,status, customer_name, customer_number,create_datetime,update_datetime) values(?,?,?,?,now(),now()";

	private DBUtil dbUtil;

	public OrderDetailDAO(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	public int save(OrderDetailDTO orderDetailDTO) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		try {
			// step 1 making connection

			connection = dbUtil.getConnection();

			// step 2 prepared statement

			pstmt = connection.prepareStatement(ORDER_DETAIL_INSERT_DATA);

			pstmt.setDouble(1, orderDetailDTO.getTotalAmount());
			pstmt.setString(2, orderDetailDTO.getStatus());
			pstmt.setString(3, orderDetailDTO.getCustomerName());
			pstmt.setString(4, orderDetailDTO.getCustomerNumber());

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
