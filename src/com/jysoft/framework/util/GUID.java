package com.jysoft.framework.util;

import java.util.Random;
import java.util.UUID;

/**
 * GUID生产类
 * @author demo
 *
 */
public class GUID {

	/**
	 * 50位
	 * @return
	 */
	public static String getGuid(){
		String guid = UUID.randomUUID().toString() +"-"+UUID.randomUUID().toString().substring(0, 13);
		return guid.toUpperCase().replaceAll("-", "0");
	}
	
	public static String getRandomId(){
		Random r = new Random();
		String str = System.currentTimeMillis()+""+r.nextLong();
		if(str.length()>32){
			return str.substring(0, 31);
		}
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(getGuid().length());
		System.out.println(getGuid());
	}
}
