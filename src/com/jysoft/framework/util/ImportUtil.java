package com.jysoft.framework.util;

import java.io.InputStream;
import java.util.Properties;

public class ImportUtil {
	private static String IMPORT_REPORT;
	private static String START_DAY;
	
	public ImportUtil(){
		
	}
	
	static{
		InputStream in=null;
		try{
		   in = DBConfig.class.getClassLoader().getResourceAsStream("importconfig.properties");
		   Properties p = new Properties();
		   p.load(in);
		   in.close();
		   IMPORT_REPORT = p.getProperty("import_replace").trim();
		   START_DAY = p.getProperty("start_day").trim();
		   
		}catch (Exception e) {
			System.out.println("已获取到BO属性配置文件失败,错误信息:"+e.getMessage());
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
	
	public static String getImportReplace(){
			return IMPORT_REPORT;
	}
	
	public static String getStartDay(){
		return START_DAY;
}
}
