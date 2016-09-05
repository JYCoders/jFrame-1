package com.jysoft.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtil {
	
	public static Connection getDefaultConnection() throws SQLException{
		if(DBConfig.sqlDebug){
			return getNativeConnection();
		}else{
			return getContainerConnection(DBConfig.db_datasource);
		}
	}
	
	private static Connection getNativeConnection() throws SQLException{
		try {
			Class.forName(DBConfig.db_driver_String);
			DriverManager.setLoginTimeout(Constant.COMM_JDBC_LOGIN_TIMEOUT);
			return DriverManager.getConnection(DBConfig.db_url,DBConfig.db_user,DBConfig.db_password);
		} catch (Exception e) {
			throw new SQLException("获取本地数据源连接失败："+e.getMessage());
		}
	}
	
	private static Connection getContainerConnection(String dataSource) throws SQLException{
		try {
			Context context = (Context)new InitialContext().lookup("java:/comp/env");
			DataSource ds = (DataSource)context.lookup(dataSource);
			return ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("获取JNDI数据源连接失败："+e.getMessage());
		}
	}
//	
//	public static void  closeResultSet(ResultSet rs){
//		   try {
//			  if(rs != null){
//				rs.close();
//			  }
//		   } catch (Exception e) {
//			   e.printStackTrace();
////			   throw new SQLException("关闭ResultSet时发生异常。"+e.getMessage());
//		   }
//	    }
//		
//	public static void closeStatement(Statement st){
//			try {
//				if(st != null){
//					st.close();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
////				throw new SQLException("关闭Statement时发生异常。"+e.getMessage());
//			}
//		}
//		
	public static void closeConnection(Connection connection){
		try {
			if(connection !=null && !connection.isClosed()){
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
