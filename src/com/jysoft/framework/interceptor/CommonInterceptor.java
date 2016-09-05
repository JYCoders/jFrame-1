package com.jysoft.framework.interceptor;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jysoft.cbb.vo.MenuVo;

/**
 * 
 * 类功能描述: 公共拦截器
 * 创建者： 吴立刚
 * 创建日期： 2016-1-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
	private List<String> excludedUrls;// 那些url不拦截

	public List<String> getExcludedUrls() {
		return excludedUrls;
	}

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();
		boolean beFilter = true;
		boolean isAcessPrv = true; // 是否具备访问权限
		for (String temp : excludedUrls) {
			if (url.endsWith(temp)) {
				beFilter = false;
				break;
			}
		}
		if (beFilter) {
			Object o = request.getSession().getAttribute("currUser");
			if (o == null || "".equals(o.toString())) {
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + path + "/";
				String loginPage = basePath + "control/login/toIndex" + "?etc="
						+ new Date().getTime();
				response.sendRedirect(loginPage);
				return false;
			} else {
				List<MenuVo> menuList = (List<MenuVo>) request.getSession()
						.getAttribute("menuList");
				if (menuList != null && menuList.size() > 0) {
					for (MenuVo menu : menuList) {
						if ((request.getContextPath() + "/" + menu.getLinkUrl())
								.contains(url)) {
							isAcessPrv = false;
							break;
						}
					}

					if (!isAcessPrv) {
						String path = request.getContextPath();
						String basePath = request.getScheme() + "://"
								+ request.getServerName() + ":"
								+ request.getServerPort() + path + "/";
						String loginPage = basePath + "control/login/toIndex"
								+ "?etc=" + new Date().getTime();
						response.sendRedirect(loginPage);
						return false;
					}
				}
			}
		}
		return super.preHandle(request, response, handler);
	}

	// 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e)
			throws Exception {

	}
}
