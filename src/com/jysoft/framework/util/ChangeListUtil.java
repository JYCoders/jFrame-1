package com.jysoft.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 将list集合转换为Map
 * @author demo
 *
 */
public class ChangeListUtil {
	/**
	 * list转换为Map
	 * @param list集合
	 * @param type对象
	 * @param key要转换成map的key
	 * @param value要转换的map的value
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map<String, String> list2Map(List list, Class type,
			String key, String value) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Map<String, String> map = new HashMap<String, String>();
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				String getKey="get"+upperFirstLetter(key);//获得相应属性的getXXX方法名称
				String getValue="get"+upperFirstLetter(value);//获得相应属性的getXXX方法名称
				Method getMethodKey = type.getMethod(getKey, new Class[] {});
				Method getMethodValue = type.getMethod(getValue, new Class[] {});
				Object keyObject = getMethodKey.invoke(list.get(i));
				Object valueObject = getMethodValue.invoke(list.get(i));
				map.put(tranObj2Str(keyObject), tranObj2Str(valueObject));
			}
		}
		return map;
	}
	/**
	 * list转换为Map对象
	 * @param list集合
	 * @param type对象
	 * @param key要转换成map的key
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map<String, Object> list2Map(List list, Class type,String key) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				String getKey="get"+upperFirstLetter(key);//获得相应属性的getXXX方法名称
				Method getMethodKey = type.getMethod(getKey, new Class[] {});
				Object keyObject = getMethodKey.invoke(list.get(i));
				Object object = list.get(i);
				map.put(tranObj2Str(keyObject),object);
			}
		}
		return map;
	}
	/**
	 * 基本数据对象toString;
	 * 
	 * @param o
	 * @return
	 */
	public static String tranObj2Str(Object o) {
		if (o == null)
			return "";
		if (o instanceof String)
			return (String) o;
		if (o instanceof Date) {
			Date d = (Date) o;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(d);
		}
		if (o instanceof Double) {
			// return BigDecimal.valueOf((Double)o).toString();
			// return new BigDecimal((Double)o).toString();
			return padDoubleLeft((Double) o, 16, 4);
		} else
			return o.toString();
	}

	/**
	 * 解决科学计数法问题
	 * 
	 * @param d
	 * @param totalDigit
	 * @param fractionalDigit
	 * @return
	 */
	public static String padDoubleLeft(Double d, int totalDigit,
			int fractionalDigit) {
		if (d == 0)
			return "";
		String str = "";
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMinimumFractionDigits(fractionalDigit);
		decimalFormat.setMaximumFractionDigits(fractionalDigit);
		decimalFormat.setGroupingUsed(false);
		decimalFormat.setMaximumIntegerDigits(totalDigit - fractionalDigit - 1);
		decimalFormat.setMinimumIntegerDigits(totalDigit - fractionalDigit - 1);
		str = decimalFormat.format(d);
		/**
		 * 去掉前面的0（比如000123213，最后输出123213）
		 */
		String[] temp = str.split("\\.");
		String temp1 = temp[1];
		if ("0000".equals(temp1)) {
			str = temp[0];
		} else {
			while (temp1.endsWith("0")) {
				temp1 = temp1.substring(0, temp1.lastIndexOf("0"));
			}
			str = temp[0] + "." + temp1;
		}
		if (str.startsWith("-")) {
			str = str.substring(1);
			while (str.startsWith("0")) {
				str = str.substring(1);
				if (str.startsWith(".")) {
					str = "0" + str;
					break;
				}
			}
			return "-" + str;
		}
		while (str.startsWith("0")) {
			str = str.substring(1);
			if (str.startsWith(".")) {
				str = "0" + str;
				break;
			}
		}
		return str;
	}
	/**
	 * 将首字母转大写
	 * @param str
	 * @return
	 */
	public static String upperFirstLetter(String str){
		return str.substring(0, 1).toUpperCase()+str.substring(1);
	}
}
