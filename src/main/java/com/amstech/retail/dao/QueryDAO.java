package com.amstech.retail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.amstech.retail.dto.QueryDTO;

import com.amstech.retail.util.DBUtil;

public class QueryDAO {
            private final String QUERY_INSERT_DATA = "insert into query (store_info_id,name,mobile_number,description,create_datetime,update_datetime) values(?,?,?,?,now(),now()";
            
            
            private DBUtil dbUtil;

        	public QueryDAO(DBUtil dbUtil) {

        		this.dbUtil = dbUtil;

        	}
            
            
            public int save(QueryDTO queryDTO) throws Exception {
        		Connection connection = null;

        		PreparedStatement pstmt = null;

        		try {
        			// step 1 making connection

        			connection = dbUtil.getConnection();

        			// step 2 prepared statement

        			pstmt = connection.prepareStatement(QUERY_INSERT_DATA);
        			
        			pstmt.setInt(1, queryDTO.getStoreInfoID());
        			pstmt.setString(2, queryDTO.getName());
        			
        			pstmt.setString(3, queryDTO.getMobileNumber());
        			pstmt.setString(4, queryDTO.getDescription());
        			

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
