package com.amstech.retail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.amstech.retail.dto.UserDTO;
import com.amstech.retail.util.DBUtil;

public class UserDAO {
	
	//signup

	private final String STORE_INFO_INSERT_DATA = "insert into store_info (city_id,name,mobile_number,email,gst_number,address,password,create_datetime,update_datetime) values(?,?,?,?,?,?,?,now(),now())";


	//login
	
	private final String STORE_INFO_FIND_BY_MOBILE_EMAIL_PASSWORD = "select * from store_info where (mobile_number=? or email=?) ";
	
	//update store_info	
	
	private final String STORE_INFO_UPDATE_DATA = "update store_info set name = ?, mobile_number = ?, email = ?, gst_number = ?, address = ?,update_datetime = now() where id = ?";

    private final String STORE_INFO_FIND_BY_ID = "select * from store_info where id = ?";
    
    //reset password

	private final String STORE_INFO_UPDATE_PASSWORD = "UPDATE store_info SET password = ?, update_datetime = NOW() WHERE email = ? AND otp = ?";
	
	private final String STORE_INFO_SAVE_OTP = "UPDATE store_info SET otp = ?, otp_creation_time = NOW() WHERE email = ?";
	
	private final String STORE_INFO_VERIFY_OTP = "SELECT otp_creation_time FROM store_info WHERE email = ? AND otp = ?";

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
				userDTO.setEmail(rs.getString("email"));

			}

			return userDTO;

		} catch (Exception e) {
			throw e;
		} finally {

			dbUtil.getClose(connection, pstmt);
		}

	}
	
	
	 public int updatePassword(String email, String otp, String newPassword) throws Exception {
	        Connection connection = null;
	        PreparedStatement pstmt = null;

	        try {
	            connection = dbUtil.getConnection();
	            pstmt = connection.prepareStatement(STORE_INFO_UPDATE_PASSWORD);
	            pstmt.setString(1, newPassword);
	            pstmt.setString(2, email);
	            pstmt.setString(3, otp);

	            int count = pstmt.executeUpdate();
	            return count;
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            dbUtil.getClose(connection, pstmt);
	        }
	    }

	    public void saveOTP(String email, String otp) throws Exception {
	        Connection connection = null;
	        PreparedStatement pstmt = null;

	        try {
	            connection = dbUtil.getConnection();
	            pstmt = connection.prepareStatement(STORE_INFO_SAVE_OTP);
	            pstmt.setString(1, otp);
	            pstmt.setString(2, email);

	            pstmt.executeUpdate();
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            dbUtil.getClose(connection, pstmt);
	        }
	    }

	    public boolean verifyOTP(String email, String otp) throws Exception {
	        Connection connection = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;

	        try {
	            connection = dbUtil.getConnection();
	            pstmt = connection.prepareStatement(STORE_INFO_VERIFY_OTP);
	            pstmt.setString(1, email);
	            pstmt.setString(2, otp);
	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	                Timestamp otpCreationTime = rs.getTimestamp(1);
	                if (otpCreationTime != null) {
	                    long currentTime = System.currentTimeMillis();
	                    long otpTime = otpCreationTime.getTime();
	                    long difference = currentTime - otpTime;

	                    // Check if OTP is within the valid period (15 minutes)
	                    if (difference <= 15 * 60 * 1000) {
	                        return true;
	                    }
	                }
	            }
	            return false;
	        } catch (Exception e) {
	            throw e;
	        } finally {
	        	if(rs !=null) {
	        	rs.close();
	        	}
	            dbUtil.getClose(connection, pstmt);
	        }
	    }

}
