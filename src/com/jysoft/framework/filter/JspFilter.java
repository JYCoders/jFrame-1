package com.jysoft.framework.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jysoft.cbb.vo.UserVo;

/**
 * 
 * 类功能描述: 请求URL过滤器
 * 创建者： 吴立刚
 * 创建日期： 2016-1-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class JspFilter implements Filter {
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// 获得用户请求的URI
		String path = request.getRequestURI();
		String ppath = request.getHeader("Referer");
		String serverName = request.getServerName();
		if ((ppath != null && ppath.indexOf(serverName) > 0)
				|| path.contains("toIndex")) {
			filterChain.doFilter(request, response);
		} else {
			UserVo userSession = (UserVo) request.getSession().getAttribute(
					"currUser");
			ServletContext application = request.getSession()
					.getServletContext();
			List<HttpSession> userList = (ArrayList<HttpSession>) application
					.getAttribute("userList");

			if (userList != null && userList.size() > 0) {
				// 销毁的session均从ArrayList集中移除
				if (userSession != null) {
					userList.remove(userSession);
				}
			}
			String path1 = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path1 + "/";
			String loginPage = basePath + "control/login/toIndex" + "?etc="
					+ new Date().getTime();
			response.sendRedirect(loginPage);
		}
	}

	public void destroy() {

	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}