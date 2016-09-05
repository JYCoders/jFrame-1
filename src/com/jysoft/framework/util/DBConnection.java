package com.jysoft.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


//import cn.sgzxnew.po.Connect;


public class DBConnection {
	

	private static String url=DBConfig.db_url;
	private static String driverClassName = DBConfig.db_driver_String;
	private static String password = DBConfig.db_password;
	private static String username = DBConfig.db_user;
	
	/**
	 * 创建数据库连接
	 */
	
	public static Connection getConnection(){
		Connection conn=null;
		try {
			Class.forName(driverClassName);
			DriverManager.setLoginTimeout(Constant.COMM_JDBC_LOGIN_TIMEOUT);
			conn = DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

//	public static Connection getConnection(Connect connect){
//		Connection conn=null;
//		try {
//			Class.forName(connect.getDriverClassName());
//			conn = DriverManager.getConnection(connect.getUrl(),connect.getUserName(),connect.getPassWord());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return conn;
//	}
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs){
		try {
			if(ps!=null)
			{
				ps.close();
			}
			if(rs!=null)
			{
				rs.close();
			}
			if(conn != null){	
				conn.close();
			}
			ps=null;
			rs=null;
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
