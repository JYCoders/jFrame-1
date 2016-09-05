package com.jysoft.framework.util;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

public final class DBConfig implements Serializable{

	public static boolean sqlDebug;
	public static String db_driver_String;
	public static String db_url;
	public static String db_password;
	public static String db_user;
	public static String db_datasource;
	
	private DBConfig(){
		
	}
	static{
		InputStream in=null;
		try{
		   in = DBConfig.class.getClassLoader().getResourceAsStream("conf/dbconfig.properties");
		   Properties p = new Properties();
		   p.load(in);
		   in.close();
		   sqlDebug = Boolean.parseBoolean(p.getProperty("sqlDebug"));
		   db_driver_String = p.getProperty("db_driver_String");
		   db_url = p.getProperty("db_url");
		   db_password = p.getProperty("db_password");
		   db_user = p.getProperty("db_user");
		   db_datasource = p.getProperty("db_datasource");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			   try{
				   if(in!=null){
					   in.close();
				   }
			   }catch(Exception  e){
				   e.printStackTrace();
			   }
		   }
		
	}
	
}
