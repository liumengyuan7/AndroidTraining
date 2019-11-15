package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	private final String driver_name = "com.mysql.jdbc.Driver";
	private final String conn_str = "jdbc:mysql://localhost:3306/avatar?useUnicode=true&characterEncoding=utf-8";
	private final String db_user = "root";
	private final String db_pwd = "";
	private Connection conn;
	private static DBUtil dbManager;

	public static DBUtil getInstance(){
		 if(null == dbManager) {
			 dbManager = new DBUtil();
		 }
		return dbManager;
	}

	public Connection getConnection() {
		try {
			if(null == conn || conn.isClosed()) {
				Class.forName(driver_name);
				conn = (Connection) DriverManager.getConnection(conn_str,db_user,db_pwd);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void closeConnection() {
		try {
			if(null != conn && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
