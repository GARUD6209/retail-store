package com.amstech.retail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.amstech.retail.dto.UserDTO;
import com.amstech.retail.util.DBUtil;

public class UserDAO {

	private final String STORE_INFO_INSERT_DATA = "insert into store_info (city_id,name,mobile_number,email,gst_number,address,password,create_datetime,update_datetime) values(?,?,?,?,?,?,?,now(),now())";

	private final String STORE_INFO_UPDATE_DATA = "update store_info set name = ?, mobile_number = ?, email = ?, gst_number = ?, address = ?,update_datetime = now() where id = ?";

	private final String STORE_INFO_FIND_BY_MOBILE_EMAIL_PASSWORD = "select * from store_info where (mobile_number=? or email=?) ";

	private final String STORE_INFO_FIND_BY_ID = "select * from store_info where id = ?";

	private DBUtil dbUtil;

	public UserDAO(DBUtil dbUtil) {

		this.dbUtil = dbUtil;

	}

	public int save(UserDTO userDTO) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		try {
			// step 1 making connection

			connection = dbUtil.getConnection();

			// step 2 prepared statement

			pstmt = connection.prepareStatement(STORE_INFO_INSERT_DATA);

			pstmt.setInt(1, userDTO.getCityID());
			pstmt.setString(2, userDTO.getName());

			pstmt.setString(3, userDTO.getMobile_number());
			pstmt.setString(4, userDTO.getEmail());
			pstmt.setString(5, userDTO.getGstNumber());

			pstmt.setString(6, userDTO.getAddress());

			pstmt.setString(7, userDTO.getPassword());

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

	public int update(UserDTO userDTO) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(STORE_INFO_UPDATE_DATA);

			pstmt.setString(1, userDTO.getName());

			pstmt.setString(2, userDTO.getMobile_number());
			pstmt.setString(3, userDTO.getEmail());
			pstmt.setString(4, userDTO.getGstNumber());

			pstmt.setString(5, userDTO.getAddress());

			pstmt.setInt(6, userDTO.getId());

			return pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.getClose(connection, pstmt);
		}

	}

	public UserDTO findById(int id) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(STORE_INFO_FIND_BY_ID);

			UserDTO userDTO = null;

			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				userDTO = new UserDTO();
				userDTO.setId(rs.getInt("id"));
				userDTO.setName(rs.getString("name"));
				userDTO.setMobile_number(rs.getString("mobile_number"));
				userDTO.setAddress(rs.getString("address"));
				userDTO.setEmail(rs.getString("email"));
				userDTO.setGstNumber(rs.getString("gst_number"));

			}

			return userDTO;

		} catch (Exception e) {
			throw e;
		} finally {

			dbUtil.getClose(connection, pstmt);
		}

	}

	public UserDTO findByUsernameAndPassword(String username) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(STORE_INFO_FIND_BY_MOBILE_EMAIL_PASSWORD);

			UserDTO userDTO = null;

			pstmt.setString(1, username);
			pstmt.setString(2, username);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				userDTO = new UserDTO();

				userDTO.setId(rs.getInt("id"));
				userDTO.setName(rs.getString("name"));
				userDTO.setMobile_number(rs.getString("mobile_number"));
				userDTO.setPassword(rs.getString("password"));

			}

			return userDTO;

		} catch (Exception e) {
			throw e;
		} finally {

			dbUtil.getClose(connection, pstmt);
		}

	}

}
