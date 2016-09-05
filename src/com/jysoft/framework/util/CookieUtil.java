package com.jysoft.framework.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 2010-08-10
 * 
 * @author demo
 * 
 */
public class CookieUtil {
	// 保存cookie时的cookieName
	private final static String cookieDomainName = "jysoft.sams";
	// 加密cookie时的网站自定码
	private final static String webKey = "samsManager";
	/**
	 * 获取USERCOOKIE信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserCookieValue(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookieDomainName.equals(cookies[i].getName())) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	// 用户注销时,清除Cookie,在需要时可随时调用------------------------------------------------------------
	public static void clearCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieDomainName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	// 用户登录的COOKIE追加一个WEBKEY防止被破解
	public static String getUserCookieMD5(String value) {
		//return getMD5(value + ":" + webKey);
		return getMD5(value);
	}

	// 获取Cookie组合字符串的MD5码的字符串----------------------------------------------------------------------------
	public static String getMD5(String value) {
		String result = null;
		try {
			byte[] valueByte = value.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(valueByte);
			result = toHex(md.digest());
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	// 将传递进来的字节数组转换成十六进制的字符串形式并返回
	private static String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();
	}

	/**
	 * 获取到用户COOKIE值信息解析后的字符数组
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String[] getUserCookieValues(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取WEBWORK值栈
		String cookieValue = getUserCookieValue(request);
		if (cookieValue == null) {
//			stack.setValue("msg", "请先登录！");
			return null;
		}
		// 先得到的CookieValue进行Base64解码
		String cookieValueAfterDecode;
		try {
			cookieValueAfterDecode = new String(Base64.decode(cookieValue),
					"utf-8");
		} catch (UnsupportedEncodingException e) {
//			stack.setValue("msg", "您的cookie解码失败！");
			return null;
		}
		// 对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
		String[] cookieValues = cookieValueAfterDecode.split(":");
		if (cookieValues.length != 3) {
//			stack.setValue("msg", "您正在使用非法方式登录网站！");
			return null;
		}
		// 判断是否在有效期内,过期就删除Cookie
		long validTimeInCookie = new Long(cookieValues[1]);
		if (validTimeInCookie < System.currentTimeMillis()) {
			// 删除Cookie
			clearCookie(response);
//			stack.setValue("msg", "您的cookie已过期！");
			return null;
		}
		return cookieValues;
	}
}
