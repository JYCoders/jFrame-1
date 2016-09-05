package com.jysoft.framework.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * 类功能描述: 跨站请求过滤
 * 创建者： 吴立刚
 * 创建日期： 2016-1-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

	private byte[] body;

	private String queryString;

	private String streamParam;

	public XssRequestWrapper(HttpServletRequest request) {
		super(request);
		getParameterMap();
		BufferedReader reader;
		try {
			reader = request.getReader();
			StringBuilder sb = new StringBuilder();
			char[] buf = new char[1024];
			int rd;
			while ((rd = reader.read(buf)) != -1) {
				sb.append(buf, 0, rd);
			}
			reader.close();

			streamParam = xssClean(sb.toString());
			body = streamParam.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}

		queryString = xssClean(request.getQueryString());
	}

	@Override
	public String getQueryString() {
		return queryString;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {

		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				int read = bais.read();
				bais.close();
				return read;
			}
		};
	}

	@SuppressWarnings("rawtypes")
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> request_map = super.getParameterMap();
		Iterator iterator = request_map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry me = (Map.Entry) iterator.next();
			String[] values = (String[]) me.getValue();
			for (int i = 0; i < values.length; i++) {
				if (values[i] != null) {
					values[i] = xssClean(values[i]);
				}
			}
		}
		return request_map;
	}

	public String[] getParameterValues(String paramString) {
		String[] arrayOfString1 = super.getParameterValues(paramString);
		if (arrayOfString1 == null)
			return null;
		int i = arrayOfString1.length;
		String[] arrayOfString2 = new String[i];

		for (int j = 0; j < i; j++) {
			if (arrayOfString1[j] != null) {
				arrayOfString2[j] = xssClean(arrayOfString1[j]);
				/*
				 * if(!checkMessage(arrayOfString1[j])){ arrayOfString2[j]=""; }
				 */
			}
		}
		return arrayOfString2;
	}

	public String getParameter(String paramString) {
		String str = super.getParameter(paramString);
		if (str == null)
			return null;
		return xssClean(str);
	}

	public String getHeader(String paramString) {
		String str = super.getHeader(paramString);
		if (str == null)
			return null;
		return xssClean(str);
	}

	private String xssClean(String value) {
		if (value == null) {
			return value;
		}
		value = value.replaceAll("", "");

		Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>",
				Pattern.CASE_INSENSITIVE);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		value = scriptPattern.matcher(value).replaceAll("");
		scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern.compile("<script(.*?)>",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern.compile("<img(.*?)>", Pattern.CASE_INSENSITIVE
				| Pattern.MULTILINE | Pattern.DOTALL);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern.compile("alert(.*?)", Pattern.CASE_INSENSITIVE
				| Pattern.MULTILINE | Pattern.DOTALL);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern.compile("eval\\((.*?)\\)",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern
				.compile("javascript:", Pattern.CASE_INSENSITIVE);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern.compile("onload(.*?)=",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		value = scriptPattern.matcher(value).replaceAll("");
		return value;
	}

	/*
	 * public boolean checkMessage(String value){ boolean flag = true;
	 * if(value.matches("(%[a-f0-9A-F][a-f0-9A-F])+") && value.length()%9==0 &&
	 * value.length()!=0){ return flag; } //判断是否包含特殊字符
	 * if(value.indexOf("|")!=-1||value.indexOf("&")!=-1||
	 * value.indexOf(";")!=-1||value.indexOf("$")!=-1||
	 * value.indexOf("%")!=-1||value.indexOf("'")!=-1||
	 * value.indexOf("\'")!=-1||value.indexOf("\"")!=-1||
	 * value.indexOf("<")!=-1||value.indexOf(">")!=-1||
	 * value.indexOf("(")!=-1||value.indexOf(")")!=-1||
	 * value.indexOf("+")!=-1||value.indexOf(",")!=-1||
	 * value.indexOf("\n")!=-1||value.indexOf("\t")!=-1||
	 * value.indexOf("\\")!=-1||value.indexOf("..")!=-1){ flag = false; } return
	 * flag;
	 * 
	 * }
	 */
	public String getReaderParam() {
		return streamParam;
	}
}
