package com.jysoft.framework.filter;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jysoft.framework.util.StringUtil;


/**
 * 
 * 类功能描述: 安全过滤器，过滤不安全的链接以及地址的参数
 * 创建者： 吴立刚
 * 创建日期： 2016-1-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class SecureFilter implements Filter {

	static Logger logger = Logger.getLogger(SecureFilter.class.getName());

	private String errorMsg = "请求非法";

	private String rnd = null;

	private static Map<String, List<Double>> requestLogMap = null;

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		// 同地址请求验证
		// 第一段，referer 验证 主要是防止跨站点攻击
		// referer 要求强制必须有UAP_OMEA/UAP_APP
		// 安全参数验证
		// 登陆验证
		// 权限验证
		// 错误信息提示及输出

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		request.setCharacterEncoding("UTF-8");
		XssRequestWrapper requestWrapper = new XssRequestWrapper(request);// 检查有无跨栈攻击

		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setContentType("text/html; charset=utf-8");
		requestWrapper.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String requestUrl = requestWrapper.getRequestURI();
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		/**
		 * 跨站点攻击
		 */
		String referer = request.getHeader("Referer");
		boolean checkXss = checkXss(referer, requestUrl);
		if (!checkXss) {
			PrintWriter out = response.getWriter();
			out.print(errorMsg);
			return;
		}

		/**
		 * 获取所有跳转路径参数，保留传入下个界面
		 */
		Map<String, String[]> map = requestWrapper.getParameterMap();// 检查参数
		List<String> paramval = new ArrayList<String>();
		boolean checkParameterMap = checkParameterMap(map, requestUrl, paramval);
		if (!checkParameterMap) {
			PrintWriter out = response.getWriter();
			out.write(getOutPrintErrorMsg());
			return;
		}

		/**
		 * 同地址请求验证,重复请求
		 */
		String currentRequest = request.getRemoteAddr() + ":" + requestUrl;
		if (paramval.size() > 0) {
			
		}
		boolean checkSameUrl = checkSameUrl(currentRequest, requestUrl,
				requestWrapper, paramval);
		boolean trueorfalse = true;
		if (request.getRequestURI().toString().contains("login")
				|| request.getRequestURI().toString().endsWith("dcms")
				|| request.getRequestURI().toString().endsWith("dcmshn")
				|| request.getRequestURI().toString().endsWith("dcms/")
				|| request.getRequestURI().toString().endsWith("dcmshn/")
				|| request.getRequestURI().toString().endsWith(".")) {
			trueorfalse = false;
		}
		if (!checkSameUrl && trueorfalse) {
			PrintWriter out = response.getWriter();
			// out.print(getOutPrintErrorMsg());
			out.print(requestUrl + paramval);
			return;
		}

		/**
		 * 检查数据流参数
		 */
		String readerParam = requestWrapper.getReaderParam();
		String pathInfo = request.getPathInfo();
		boolean docType = (pathInfo == null ? true : (pathInfo
				.contains("import") ? true : pathInfo.contains("upload") ? true
				: pathInfo.contains("assessSave")));
		if (pathInfo != null && pathInfo.contains("saveEditProcess")) {
			docType = true;
		}
		if (!Boolean.parseBoolean(request.getParameter("isFileUpload"))
				&& !docType) {// 判断是否是文件上传，是不对流参数进行验证

			/**
			 * 白名单中不验证参数
			 */
			boolean doFilter = true;
			/*
			 * System.out.println("pathInfo==="+pathInfo);
			 * System.out.println("requestUrl==="+requestUrl);
			 * System.out.println("doFilter==="+doFilter);
			 */
			boolean checkReader = checkReader(readerParam, requestUrl);
			if (doFilter && !checkReader && !docType) {
				PrintWriter out = response.getWriter();
				out.print(getOutPrintErrorMsg());
				return;
			}
		}
		response.setStatus(HttpServletResponse.SC_OK);
		filterChain.doFilter(requestWrapper, response);
		return;
	}

	/**
	 * 通过rnd随机参数判断相同url是否多次请求访问
	 * 
	 * @param currentRequest
	 * @param requestUrl
	 * @param requestWrapper
	 * @return
	 */
	private boolean checkSameUrl(String currentRequest, String requestUrl,
			XssRequestWrapper requestWrapper, List<String> val) {
		/**
		 * 获取登录页面
		 */
		if (rnd != null && rnd.length() > 0) {
			try {
				int iii = 10;
				double newRnd1 = Double.parseDouble(rnd);
				long temp = Long.parseLong(rnd) / iii;
				double newRnd = (double) temp;
				/**
				 * 当为空时，新增请求时间记录，第一次请求，放行，允许查询
				 */
				HttpSession session = requestWrapper.getSession();
				Object obj = session.getAttribute("requestLogMap");
				if (obj == null) {
					requestLogMap = new HashMap<String, List<Double>>();
				} else {
					requestLogMap = (Map<String, List<Double>>) obj;
				}
				List<Double> list = requestLogMap.get(currentRequest
						+ val.toString());
				if (list == null) {
					list = new ArrayList<Double>();
				} else {
					for (Double oldRnd : list) {
						long temp1 = (long) ((oldRnd) / iii);
						double temp2 = ((double) temp1);
						if (temp2 == newRnd) {
							/*
							 * if(list.size()==1) { }else
							 */{
								logger.info("重复请求失败:\n" + currentRequest
										+ newRnd);
								return true;
							}
						}
					}
				}
				if (list.size() != 1) {
					list.clear();
				}
				list.add(newRnd1);
				requestLogMap.put(currentRequest + val.toString(), list);
				session.setAttribute("requestLogMap", requestLogMap);
			} catch (NumberFormatException e) {
				logger.info("etc参数格式化错误 请检查");
				return false;
			}
		} else {
			// logger.info("请求失败,etc参数不存在!请重新登录！您的请求地址：\n" + currentRequest);
			// return false;
			return true;
		}
		return true;
	}

	/**
	 * 简单的xss跨站点检查
	 * 
	 * @param referer
	 * @param requestUrl
	 * @return
	 */
	private boolean checkXss(String referer, String requestUrl) {
		if (referer == null) {
			referer = "";
		}

		// 如果请求地址为空则直接返回false
		if (requestUrl == null
				|| (requestUrl.lastIndexOf(".") > 0
						&& requestUrl.indexOf("imgs") < 0
						&& requestUrl.lastIndexOf("control") > 0 && !requestUrl
						.endsWith("action"))) {
			logger.info("当前请求地址不存在");
			return false;
		}

		// 逻辑： 判断SERVICE_TYPES数组中 所有元素 是否有一个能够通过验证
		// 循环SERVICE_TYPES
		// 判断当前变量是否存在于referer，判断当前变量在不存在于referer，是否存在于requestUrl
		// 存在则返回true,结束循环
		// 不存在，返回false，提示当前来源不是本站请求
		return true;
	}

	/**
	 * 检查所有页面输入的参数是否安全 request.getParameterMap()
	 * 
	 * @param map
	 * @param requestUrl
	 */
	private boolean checkParameterMap(Map<String, String[]> map,
			String requestUrl, List<String> list) {
		rnd = null;
		if (map != null && map.size() > 0) {
			Iterator<Entry<String, String[]>> iterator = map.entrySet()
					.iterator();
			String value = "";// 参数值
			while (iterator.hasNext()) {
				value = "";
				Entry<String, String[]> entry = iterator.next();
				String pname = (String) entry.getKey(); // 参数名
				Object objValue = entry.getValue(); // 数组
				if (null == objValue) {
					value = "";
				} else if (objValue instanceof String[]) {
					String[] values = (String[]) objValue;
					for (int i = 0; i < values.length; i++) {
						value = values[i] + ",";
						list.add(values[i]);
					}
					if (value.length() > 0) {
						value = value.substring(0, value.length() - 1);
					}
				}
				if (pname != null && "etc".equals(pname)) {
					rnd = value;
					if (!isNumeric(rnd)) {
						return false;
					}
				}
				if (StringUtil.checkSafeSql(value)) {
					logger.info("请求失败,当前请求参数不安全!请求地址：\n" + requestUrl
							+ "\n不安全参数：" + pname + ":" + value);
					return false;
				}
			}
		}
		return true;
	}

	// 判断是否全是数字
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查所有页面输入的参数是否安全 request.getReader()
	 * 
	 * @param map
	 * @param requestUrl
	 */
	private boolean checkReader(String readerParam, String requestUrl) {
		if (StringUtil.checkScript(readerParam)) {
			logger.info("请求失败,当前请求参数不安全!请求地址：\n" + requestUrl + "\n不安全参数：数据流:"
					+ readerParam);
			return false;
		}
		return true;
	}

	/**
	 * 获取错误信息
	 */
	private String getOutPrintErrorMsg() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"successful\":false").append(
				",\"resultValue\":{\"items\":[],\"itemCount\":0,\"dicts\":[]}")
				.append(",\"resultHint\":\"").append(errorMsg).append(
						"\",\"errorPage\":\"").append(
						System.currentTimeMillis()).append("\",\"type\":\"\"}");
		return sb.toString();
	}

	public static Map<String, List<Double>> getRequestLogMap() {
		return requestLogMap;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("安全[SecureFilter]过滤器初始化==================init");
	}

	public void destroy() {
		logger.info("安全[SecureFilter]过滤器销毁===================destroy");
	}
}
