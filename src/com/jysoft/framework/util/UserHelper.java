package com.jysoft.framework.util;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;


public class UserHelper implements Serializable {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * 获取当前登录用户
//	 * @return
//	 */
//	public static User getCurrentUser(HttpServletRequest requst){
//		Object obj = requst.getSession().getAttribute("currUser");
//		return (User)obj;
//	}
//	/**
//	 * 获取当前登录用户的部门
//	 * @return
//	 */
////	public static Depts getCurrentDept(){
////		Object obj = ActionContext.getContext().getSession().get("currDept");
////		return (Depts)obj;
////	}
//
//	/**
//	 * 获取当前登录用户ID，有则返回ID，无则返回null
//	 * @return
//	 */
//	public static String getCurrentUserId(HttpServletRequest requst){
//		User user = getCurrentUser(requst);
//		if(user!=null&&user.getUserId()!=null)
//			return user.getUserId().toString();
//		else
//			return null;
//	}
//
//	/**
//	 * 获取当前登录用户名称，有则返回name，无则返回null
//	 * @return
//	 */
//	public static String getCurrentUserName(HttpServletRequest requst){
//		User user = getCurrentUser(requst);
//		if(user!=null&&user.getUserName()!=null)
//			return user.getUserName();
//		else
//			return null;
//	}
//		
//	/**
//	 * 获取当前登录用户的部门ID，使用用户ID查询员工对象，再使用员工所属部门ID做返回值
//	 * @return
//	 */
//	public static String getCurrentUserDeptId(HttpServletRequest requst){
//		String deptId=null;
//		
//		User user = getCurrentUser(requst);
//		if(user!=null&&user.getUserId()!=null){//用户不为空
//			deptId=user.getDeptId();
//		}
//		return deptId;
//	}
//	
//	/**
//	 * 获取当前登录用户的部门名称
//	 * @return
//	 */
//	public static String getCurrentUserDeptName(HttpServletRequest requst){
//		String deptName=null;
//		User user = getCurrentUser(requst);
//		if(user!=null&&user.getUserId()!=null){//用户不为空
//			deptName=user.getDeptName();
//		}
//		return deptName;
//	}	
	
	
}
