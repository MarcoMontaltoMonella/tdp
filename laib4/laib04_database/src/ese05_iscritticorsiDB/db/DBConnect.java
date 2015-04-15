package ese05_iscritticorsiDB.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	
	private static String url = "jdbc:mysql://localhost/iscritticorsi?user=root";
	
	public static Connection getConnection(){
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
