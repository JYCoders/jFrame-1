package com.jysoft.framework.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class CharsetUtil {

	//@Autowired(required = true)
	//static TableMapper tableMapper;
	//private String name = "name"; 
	//private String perLength = "perLength";
	private static String charsetDB;
	private static Map charsetMap;
	private static int perCharLength;
	static{
		//charsetDB = getDBCharset();
		charsetMap = getCharsetMap();
		//perCharLength = getPerCharLength();
		
	}
	/*private static String getDBCharset() {
		//String sql = "SELECT USERENV('language') FROM dual";
		String charset = tableMapper.getCharset();
		return charset;
	}*/
	private static Map getCharsetMap() {
		Map map = new HashMap();
		for(CharsetEnum c : CharsetEnum.values()){
			map.put(c.getName(),c.getPerLength());
		}
		return map;
	}
	
	static int getPerCharLength(String charsetDB){
		perCharLength = (Integer) charsetMap.get(charsetDB);
		return perCharLength;
		
	}
	
	/**
	 * 字符串所占位数
	 */
	public static int getStringLength(String str){
		int re = 0;
		if(str != null){
			String reg = "[\u4e00-\u9fa5]";
			Pattern pat = Pattern.compile(reg);
			Matcher mat = pat.matcher(str);
			String repickStr = mat.replaceAll("");
			re = (str.length()-repickStr.length())*perCharLength + repickStr.length();
		}
		return re;
	}
	/**
	 * 字符串所占位数
	 */
	public static int getStringLength(String chartsetDB,String str){
		int re = 0;
		perCharLength = getPerCharLength(chartsetDB);
		if(str != null){
			String reg = "[\u4e00-\u9fa5]";
			Pattern pat = Pattern.compile(reg);
			Matcher mat = pat.matcher(str);
			String repickStr = mat.replaceAll("");
			re = (str.length()-repickStr.length())*perCharLength + repickStr.length();
		}
		return re;
	}
}
