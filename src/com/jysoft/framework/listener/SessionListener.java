package com.jysoft.framework.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.jysoft.cbb.vo.UserVo;

/**
 * 
 * 类功能描述: 会话session监听器
 * 创建者： 吴立刚
 * 创建日期： 2015-10-13
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class SessionListener implements HttpSessionListener{
	
	/**
	 * 函数方法描述: session被创建时监听，并将新session存储在applicaiton范围的数组中
	 * 
	 * @param event
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		UserVo userSession = (UserVo) session.getAttribute("currUser");
		ServletContext application = session.getServletContext();

		// 在application范围由一个ArrayList集保存所有的session
		List<UserVo> sessionList = (ArrayList<UserVo>) application
				.getAttribute("allUser");
		if (sessionList == null) {
			sessionList = new ArrayList<UserVo>();
			application.setAttribute("allUser", sessionList);
		}

		// 新创建的session均添加到ArrayList集中
		if (userSession != null) {
			sessionList.add(userSession);
		}
	}

	/**
	 * 函数方法描述: session被销毁时监听，并将userSession从applicaiton范围中的数组移除
	 * 
	 * @param event
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		UserVo userSession = (UserVo) session.getAttribute("currUser");
		ServletContext application = session.getServletContext();
		List<HttpSession> userList = (ArrayList<HttpSession>) application
				.getAttribute("userList");

		if (userList != null && userList.size() > 0) {
			// 销毁的session均从ArrayList集中移除
			if (userSession != null) {
				userList.remove(userSession);
			}
		}
		application.setAttribute("userList",userList);
	}
}
