package com.amstech.retail.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {
	private final String USERNAME = "root";
	private final String PASSWORD = "1234";
	private final String URL = "jdbc:mysql://localhost:3306/retail_store";
	private final String DRIVER = "com.mysql.cj.jdbc.Driver";

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		System.out.println("Connecting-database-restaurant-management...");

		Class.forName(DRIVER);

		Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		System.out.println("database connected.");

		return connection;
	}

	public void getClose(Connection connection, PreparedStatement pstmt) throws SQLException {
		if (pstmt != null) {
			pstmt.close();
		}
		if (connection != null) {
			connection.close();
		}

	}

}
