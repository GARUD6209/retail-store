package com.amstech.retail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.amstech.retail.dto.ItemDTO;
import com.amstech.retail.dto.UserDTO;
import com.amstech.retail.util.DBUtil;

public class ItemDAO {

//	 Field           | Type          | Null | Key | Default | Extra          |
//	 +-----------------+---------------+------+-----+---------+----------------+
//	 | id              | int           | NO   | PRI | NULL    | auto_increment |
//	 | store_info_id   | int           | NO   | MUL | NULL    |                |
//	 | name            | varchar(45)   | NO   |     | NULL    |                |
//	 | current_price   | decimal(10,2) | NO   |     | NULL    |                |
//	 | description     | text          | YES  |     | NULL    |                |
//	 | create_datetime | datetime      | NO   |     | NULL    |                |
//	 | update_datetime | datetime      | NO   |     | NULL    |                |
//	

	private final String USER_INSERT_DATA = "insert into item (store_info_id,name,current_price,description,create_datetime,update_datetime) value (?,?,?,?,now(),now())";
	private final String USER_UPDATE_DATA = "update item set name=?,current_price=?,description=?,update_datatime=now()";

	private DBUtil dbUtil;

	public ItemDAO(DBUtil dbUtil) {
		this.dbUtil = dbUtil;

	}

	public int save(ItemDTO itemDTO) throws ClassNotFoundException, SQLException {

		Connection connection = null;

		PreparedStatement pstmt = null;

		try {
			// step 1 making connection

			connection = dbUtil.getConnection();

			// step 2 prepared statement

			pstmt = connection.prepareStatement(USER_INSERT_DATA);

			pstmt.setInt(1, itemDTO.getStoreInfoId());
			pstmt.setString(2, itemDTO.getName());

			pstmt.setDouble(3, itemDTO.getCurrent_price());
			pstmt.setString(4, itemDTO.getDescription());

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
	
	public int update(ItemDTO itemDTO) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(USER_UPDATE_DATA);

            pstmt.setString(1, itemDTO.getName());
			
			pstmt.setDouble(2, itemDTO.getCurrent_price());
			pstmt.setString(3, itemDTO.getDescription());
						
			
			
			return pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.getClose(connection, pstmt);
		}

	}

}
