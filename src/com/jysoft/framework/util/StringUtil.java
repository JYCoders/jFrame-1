package com.jysoft.framework.util;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 类功能描述: 共通字符串函数
 * 创建者： 吴立刚
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class StringUtil {
	// 安全路径
	private static HashMap<String, String> MAP_SAFE_PATH;

	/**
	 * 函数方法描述: 将parameter转换成适合sql语句条件 in （'','',''）
	 * 
	 * @param parameter
	 *            字符串
	 * @return 转换后的字符串
	 */
	public static String coventParameter(String parameter) {
		if (parameter == null || "".equals(parameter)) {
			return parameter;
		}
		String temp = parameter.replace(",", "','");
		temp = "'" + temp + "'";
		return temp;
	}

	/**
	 * 函数方法描述: 将字符串数组转换成需要的格式
	 * 
	 * @param strings
	 *            字符串数组
	 * @return 转换后的字符串
	 */
	public static String convertStrings(String[] strings) {
		StringBuilder sb = new StringBuilder();
		sb.append("'");
		for (int i = 0; i < strings.length; i++) {
			sb.append(strings[i] + "','");
		}
		if (sb.toString().length() > 2) {// 排除strings为空的情况；
			return sb.toString().substring(0, sb.toString().length() - 2);
		} else {
			return "''";
		}

	}

	/**
	 * 函数方法描述: 判断是否为整数
	 * 
	 * @param string
	 *            字符串
	 * @return True：整数，False：非整数
	 */
	public static boolean isNumber(String string) {
		String regex = "^[0-9]*$";
		if (string != null && !"".equals(string)) {
			return string.matches(regex);
		} else {
			return false;
		}
	}

	/**
	 * 函数方法描述: 判断是否为数值型
	 * 
	 * @param string
	 *            字符串
	 * @return True：数值型，False：非数值型
	 */
	public static boolean isNumberc(String string) {
		String regex = "^[0-9]+(\\.[0-9]+)?$";
		if (string != null && !"".equals(string)) {
			if (string.indexOf("-") > 0) {
				String[] strings = string.split("-");
				if (strings.length > 1) {
					String tempString1 = "";
					if (strings[0] != null && !"".equals(strings[0])) {
						tempString1 = strings[0];
					}
					String tempString2 = strings[1];
					if (tempString1 != null && !"".equals(tempString1)
							&& tempString2 != null && !"".equals(tempString2)) {
						boolean flag = tempString1.trim().matches(regex);
						boolean flag2 = tempString2.trim().matches(regex);
						if (flag && flag2) {
							return true;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return string.matches(regex);
			}
			return string.matches(regex);
		} else {
			return false;
		}
	}

	/**
	 * 函数方法描述: 判断是否为数值型编码， 当字符串为空时也返回TRUE
	 * 
	 * @param string
	 *            字符串
	 * @return True：数值型，False：非数值型
	 */
	public static boolean isNumbericCode(String string) {
		String regex = "^[0-9]{0,}$";
		if (string != null && !"".equals(string)) {
			return string.matches(regex);
		} else {
			return true;
		}
	}

	// 设置路径关键信息
	static {
		MAP_SAFE_PATH = new HashMap<String, String>();
		MAP_SAFE_PATH.put("a", "a");
		MAP_SAFE_PATH.put("b", "b");
		MAP_SAFE_PATH.put("c", "c");
		MAP_SAFE_PATH.put("d", "d");
		MAP_SAFE_PATH.put("e", "e");
		MAP_SAFE_PATH.put("f", "f");
		MAP_SAFE_PATH.put("g", "g");
		MAP_SAFE_PATH.put("h", "h");
		MAP_SAFE_PATH.put("i", "i");
		MAP_SAFE_PATH.put("j", "j");
		MAP_SAFE_PATH.put("k", "k");
		MAP_SAFE_PATH.put("l", "l");
		MAP_SAFE_PATH.put("m", "m");
		MAP_SAFE_PATH.put("n", "n");
		MAP_SAFE_PATH.put("o", "o");
		MAP_SAFE_PATH.put("p", "p");
		MAP_SAFE_PATH.put("q", "q");
		MAP_SAFE_PATH.put("r", "r");
		MAP_SAFE_PATH.put("s", "s");
		MAP_SAFE_PATH.put("t", "t");
		MAP_SAFE_PATH.put("u", "u");
		MAP_SAFE_PATH.put("v", "v");
		MAP_SAFE_PATH.put("w", "w");
		MAP_SAFE_PATH.put("x", "x");
		MAP_SAFE_PATH.put("y", "y");
		MAP_SAFE_PATH.put("z", "z");

		MAP_SAFE_PATH.put("A", "A");
		MAP_SAFE_PATH.put("B", "B");
		MAP_SAFE_PATH.put("C", "C");
		MAP_SAFE_PATH.put("D", "D");
		MAP_SAFE_PATH.put("E", "E");
		MAP_SAFE_PATH.put("F", "F");
		MAP_SAFE_PATH.put("G", "G");
		MAP_SAFE_PATH.put("H", "H");
		MAP_SAFE_PATH.put("I", "I");
		MAP_SAFE_PATH.put("J", "J");
		MAP_SAFE_PATH.put("K", "K");
		MAP_SAFE_PATH.put("L", "L");
		MAP_SAFE_PATH.put("M", "M");
		MAP_SAFE_PATH.put("N", "N");
		MAP_SAFE_PATH.put("O", "O");
		MAP_SAFE_PATH.put("P", "P");
		MAP_SAFE_PATH.put("Q", "Q");
		MAP_SAFE_PATH.put("R", "R");
		MAP_SAFE_PATH.put("S", "S");
		MAP_SAFE_PATH.put("T", "T");
		MAP_SAFE_PATH.put("U", "U");
		MAP_SAFE_PATH.put("V", "V");
		MAP_SAFE_PATH.put("W", "W");
		MAP_SAFE_PATH.put("X", "X");
		MAP_SAFE_PATH.put("Y", "Y");
		MAP_SAFE_PATH.put("Z", "Z");

		MAP_SAFE_PATH.put(":", ":");
		MAP_SAFE_PATH.put("/", File.separator);
		MAP_SAFE_PATH.put("\\", File.separator);
	}

	/**
	 * 函数方法描述: 判断字符串是否为空
	 * 
	 * @param s
	 *            字符串
	 * @return True：空，False：非空
	 */
	public static boolean isEmptyString(String s) {
		if (s == null || "null".equals(s))
			return true;
		if ("".equals(s))
			return true;
		return false;
	}

	/**
	 * 函数方法描述: 拼接字符串
	 * 
	 * @param arr
	 *            字符串数组
	 * @param split
	 *            分隔符
	 * @param suffix
	 *            字符串外包装字符
	 * @return 拼接后的字符串
	 */
	public static String join(String[] arr, String split, String suffix) {
		StringBuffer buffer = new StringBuffer("");
		for (String s : arr) {
			buffer.append(suffix);
			buffer.append(s);
			buffer.append(suffix);
			buffer.append(split);
		}
		if (buffer.length() > 0) {
			buffer.deleteCharAt(buffer.length() - 1);
		}
		return buffer.toString();
	}

	/**
	 * 函数方法描述: 拼接字符串
	 * 
	 * @param arr
	 *            拼接字符串
	 * @param split
	 *            分隔符
	 * @return
	 */
	public static String join(String[] arr, String split) {
		return join(arr, split, "");
	}

	/**
	 * 函数方法描述: 拼接字符串
	 * 
	 * @param arrStr
	 *            字符串数组字符串
	 * @param split
	 *            分隔符
	 * @param suffix
	 *            字符串外包装字符
	 * @return 拼接后的字符串
	 */
	public static String join(String arrStr, String split, String suffix) {
		if (arrStr != null) {
			return join(arrStr.split(","), split, suffix);
		} else
			return "";
	}

	/**
	 * 函数方法描述: 去掉制表符号 回车换行符号 回车 换行
	 * 
	 * @param param
	 *            字符串
	 * @return 替换后的字符串
	 */
	public static String replaceString(String param) {
		return param.replaceAll("\n", "").replaceAll("\t", "").replaceAll(
				"\r\n", "");
	}

	/**
	 * 函数方法描述: 验证SQL脚本是否包含表达式中的特殊字符
	 * 
	 * @param mark
	 *            SQL脚本
	 * @return True：包含，False：不包含
	 */
	public static boolean checkSafeSql(String mark) {
		if (mark != null && !"".equals(mark)) {
			return match(Constant.COMM_SAFE_SQL_PATTERN, mark.toLowerCase()
					.trim());
		}
		return false;
	}

	/**
	 * 函数方法描述: 验证SQL脚本是否包含表达式中的特殊字符
	 * 
	 * @param mark
	 *            SQL脚本
	 * @return True：包含，False：不包含
	 */
	public static boolean checkSpecial(String mark) {
		if (mark != null && !"".equals(mark)) {
			return match(Constant.COMM_SAFE_SQL_PATTERN, mark.toLowerCase()
					.trim());
		}
		return false;
	}

	/**
	 * 函数方法描述: 验证特殊页面脚本字符
	 * 
	 * @param mark
	 *            页面脚本
	 * @return True：包含，False：不包含
	 */
	public static boolean checkScript(String mark) {
		if (mark != null && !"".equals(mark)) {
			return match(Constant.COMM_SAFE_SCRIPT_PATTERN, mark.toLowerCase()
					.trim());
		}
		return false;
	}

	/**
	 * 函数方法描述: 验证是不是数字(没有小数点)
	 * 
	 * @param str
	 *            字符串
	 * @return True：包含，False：不包含
	 */
	public static boolean isInteger(String str) {
		if (str != null && !"".equals(str)) {
			return match(Constant.COMM_CNUMBER_PATTERN, str);
		}
		return true;
	}

	/**
	 * 函数方法描述: 执行正则表达式
	 * 
	 * @param pattern
	 *            表达式
	 * @param str
	 *            待验证字符串
	 * @return True：包含，False：不包含
	 */
	private static boolean match(String pattern, String str) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 函数方法描述: 执行正则表达式
	 * 
	 * @param pattern
	 *            表达式
	 * @param str
	 *            待验证字符串
	 * @return True：包含，False：不包含
	 */
	private static boolean isMatch(String regex, String orginal) {
		if (orginal == null || orginal.trim().equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher isNum = pattern.matcher(orginal);
		return isNum.matches();
	}

	/**
	 * 函数方法描述: 判断是否包括0-9数字
	 * 
	 * @param orginal
	 *            表达式
	 * @return True：包含，False：不包含
	 */
	public static boolean isPositiveInteger(String orginal) {
		return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
	}

	public static boolean isNegativeInteger(String orginal) {
		return isMatch("^-[1-9]\\d*", orginal);
	}

	public static boolean isWholeNumber(String orginal) {
		return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal)
				|| isNegativeInteger(orginal);
	}

	public static boolean isPositiveDecimal(String orginal) {
		return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
	}

	public static boolean isNegativeDecimal(String orginal) {
		return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
	}

	public static boolean isDecimal(String orginal) {
		return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
	}

	public static boolean isRealNumber(String orginal) {
		return isWholeNumber(orginal) || isDecimal(orginal);
	}

	/**
	 * 函数方法描述: 判断表达式字符串中是否包含了除零情况
	 * 
	 * @param str
	 *            字符串
	 * @return True：包含，False：不包含
	 */
	public static boolean isContainsZeroDiv(String str) {
		if (str.indexOf("/0") > -1) {
			// 获取当前下标
			int index = str.indexOf("/0");
			if (!(index != str.length() - 2 && str.charAt(index + 2) == '.')) {// 判断是否为最后一位，判断是否下个字符为.
				return true;
			} else {
				return isContainsZeroDiv(str.substring(index + 2));
			}
		}
		return false;
	}

	/**
	 * 函数方法描述: 解决Path Manipulation问题，确保路径字符串是合法的
	 * 
	 * @param path
	 *            路径
	 * @return 替换后的路径
	 */
	public static String saftPath(String path) {
		String temp = "";
		for (int i = 0; i < path.length(); i++) {

			if (MAP_SAFE_PATH.get(path.charAt(i) + "") != null) {
				temp += MAP_SAFE_PATH.get(path.charAt(i) + "");
			}
		}
		return temp;
	}
}
