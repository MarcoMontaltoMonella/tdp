/**
 * 15 Apr 2015, 12:03:53
 */
package it.polito.tdp.mmm.anagrams.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	
	private static String url = "jdbc:mysql://localhost/dizionario?user=root";

	public static Connection getConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection(url);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
