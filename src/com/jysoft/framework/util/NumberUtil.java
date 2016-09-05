package com.jysoft.framework.util;

public class NumberUtil {
	public  static String  IntTo2ScaleCode(int i) {
		if(i<0||i>100){
			return null;
		}else if(i>=10){
			return i+"";
		}else
			return "0"+i;
	}
	public static int DoubleToInt(Object o){
		if ( o instanceof String) {
			return (int)Double.parseDouble((String)o);
		}else if ( o instanceof Double||o instanceof Float) {
			return (int)((Double)o).doubleValue();
		}
		else 
			return -1;
	}
	public static String DoubleTo2ScaleCode(Object o){
		return IntTo2ScaleCode(DoubleToInt(o));
	}
}
