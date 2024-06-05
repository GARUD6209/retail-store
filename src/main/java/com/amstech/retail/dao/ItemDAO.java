package com.amstech.retail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amstech.retail.dto.ItemDTO;

import com.amstech.retail.util.DBUtil;

public class ItemDAO {



	private final String ITEM_INSERT_DATA = "insert into item (store_info_id,name,current_price,description,create_datetime,update_datetime,status) value (?,?,?,?,now(),now(),true)";
	private final String ITEM_UPDATE_DATA = "update item set name=?,current_price=?,description=?,update_datetime=now() where id = ?";
	private final String ITEM_FIND_BY_ID = "select * from item where id =?";
	private final String ITEM_FIND_BY_STORE_INFO_ID = "select * from item where store_info_id =? and status = true";
	private final String ITEM_DELETE_BY_ID = "update item set status = false where id = ?";

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

			pstmt = connection.prepareStatement(ITEM_INSERT_DATA);

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

			pstmt = connection.prepareStatement(ITEM_UPDATE_DATA);

			pstmt.setString(1, itemDTO.getName());

			pstmt.setDouble(2, itemDTO.getCurrent_price());
			pstmt.setString(3, itemDTO.getDescription());
			pstmt.setInt(4, itemDTO.getId());

			return pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.getClose(connection, pstmt);
		}

	}

	public ItemDTO findByid(int id) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();

			ItemDTO itemDTO = null;

			pstmt = connection.prepareStatement(ITEM_FIND_BY_ID);

			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				itemDTO = new ItemDTO();
				itemDTO.setId(rs.getInt("id"));
				itemDTO.setName(rs.getString("name"));
				itemDTO.setDescription(rs.getString("description"));
				itemDTO.setCurrent_price(rs.getDouble("current_price"));
		
			}

			return itemDTO;

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.getClose(connection, pstmt);
		}

	}

	
	 //find all the the product related to the logged in user
	public List<ItemDTO> findByStoreInfoId(int storeInfoId) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;
		
		List<ItemDTO> itemDTOList = new ArrayList<>();

		try {
			
			
			connection = dbUtil.getConnection();

			ItemDTO itemDTO = null;

			pstmt = connection.prepareStatement(ITEM_FIND_BY_STORE_INFO_ID);

			pstmt.setInt(1, storeInfoId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				itemDTO = new ItemDTO();
				itemDTO.setId(rs.getInt("id"));
				itemDTO.setName(rs.getString("name"));
				itemDTO.setDescription(rs.getString("description"));
				itemDTO.setCurrent_price(rs.getDouble("current_price"));
				itemDTO.setStoreInfoId(rs.getInt("store_info_id"));
				itemDTOList.add(itemDTO);
				

			}

			return itemDTOList;

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.getClose(connection, pstmt);
		}

	}
	
	public int deleteById(ItemDTO itemDTO) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;		

		try {
			
			
			connection = dbUtil.getConnection();
			

			pstmt = connection.prepareStatement(ITEM_DELETE_BY_ID);

			pstmt.setInt(1, itemDTO.getId());
			int count = pstmt.executeUpdate();			

			return count;

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.getClose(connection, pstmt);
		}

	}

}
