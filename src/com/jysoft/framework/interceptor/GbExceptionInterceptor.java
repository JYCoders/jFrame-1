package com.jysoft.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 类功能描述: 页面请求解析失败处理
 * 创建者： 吴立刚
 * 创建日期： 2016-1-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class GbExceptionInterceptor implements HandlerExceptionResolver {
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		System.out.println("页面请求出错了：" + request.getRequestURI());
		ModelAndView mav = new ModelAndView("errors");
		// mav.addObject("errorpath", request.getRequestURI());
		// mav.addObject("errorMsg", ex.getMessage());
		// mav.addObject("ex", ex);
		return mav;
	}
}
