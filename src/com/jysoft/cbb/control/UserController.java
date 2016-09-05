package com.jysoft.cbb.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jysoft.cbb.service.UserService;
import com.jysoft.cbb.vo.AuditLogVo;
import com.jysoft.cbb.vo.UserVo;
import com.jysoft.framework.util.BusinessResult;
import com.jysoft.framework.util.CookieUtil;

/**
 * 
 * 类功能描述: 用户登录控制器
 * 创建者： 管马舟
 * 创建日期：  2016-01-22
 * 
 * 修改内容：增加批量新增修改方法
 * 修改者：黄智强
 * 修改日期：2016-03-06
 */
@Controller
@RequestMapping("/user")
public class UserController {
	protected Logger logger = Logger.getLogger(this.getClass());
	private static String Page_UserMgr = "pages/systemManager/user";
	@Autowired
	private UserService service;

	/**
	 * 函数方法描述: 管理用户控制器
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/toManageUsers", method = RequestMethod.GET)
	public ModelAndView toManageUsers(HttpServletRequest request)
			throws Exception {
		ServletContext application = request.getSession().getServletContext();
		String loginStateColumn = "";
		List<HttpSession> userListInSession = (ArrayList<HttpSession>) application
				.getAttribute("userList");
		if (userListInSession == null) {
			loginStateColumn = "0";
		} else {
			loginStateColumn = "1";
		}
		String org = service.getOption("");
		ModelAndView mav = new ModelAndView(Page_UserMgr);
		mav.addObject("org", org);
		mav.addObject("loginStateColumn", loginStateColumn);
		return mav;
	}

	/**
	 * 函数方法描述:用户修改
	 * 
	 * @param 用户vo模型信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toUpdateUserModel", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	UserVo toUpdateUserModel(@ModelAttribute UserVo user,
			HttpServletRequest request) {
		if (user != null && user.getUserId() != null) {
			UserVo dto = service.getUserById(user.getUserId());
			user = dto;
			return user;
		}
		return null;
	}

	/**
	 * 函数方法描述:获取所有用户列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/allUsers", method = { RequestMethod.GET,
			RequestMethod.POST })
	public List<UserVo> allUsers(HttpServletRequest request) {
		List<UserVo> list = service.getAll();
		return list;
	}

	/**
	 * 函数方法描述:查询所有用户展示为GRID
	 * 
	 * @param 用户vo模型信息
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/allUsersForGrid", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object> allUsersForGrid(@ModelAttribute UserVo userVo,
			HttpServletRequest request, HttpServletResponse response) {
		List<UserVo> userListNew = new ArrayList<UserVo>();
		Map<String, Object> map = new HashMap<String, Object>();
		ServletContext application = request.getSession().getServletContext();
		List<HttpSession> userListInSession = (ArrayList<HttpSession>) application
				.getAttribute("userList");
		map = service.getAllPage(userVo);
		if (userListInSession != null) {
			List<UserVo> list = (List<UserVo>) map.get("rows");
			map.remove("rows");
			for (UserVo user : list) {
				user.setLoginOnTime("未登录");
				for (int i = 0; i < userListInSession.size(); i++) {
					UserVo userTemp = (UserVo) userListInSession.get(i);
					if (user.getLoginId().equals(userTemp.getLoginId())) {
						user.setLoginOnTime("已登录");
						userListNew.add(user);
					}
				}
			}
			map.put("rows", list);
		}
		return map;
	}

	/**
	 * 函数方法描述:保存用户及角色关系(然后刷新菜单)，传入角色ID集合及用户ID
	 * 
	 * @param 用户vo模型信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveUserRoles", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String saveUserRoles(@ModelAttribute UserVo user,
			HttpServletRequest request) {
		String msg = "";
		if (user != null) {
			try {
				String operate = request.getParameter("operate");
				service.saveUserRoles(user.getUserId(), operate);
				msg = "保存成功";
			} catch (Exception e) {
				msg = e.getMessage();
			}
		} else {
			msg = "无效的用户信息";
		}
		return msg;
	}

	/**
	 * 函数方法描述:添加用户
	 * 
	 * @param 用户vo模型信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveUserModel", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String saveUserModel( @ModelAttribute UserVo user,
			HttpServletRequest request) {
		String roleIds = request.getParameter("roleIds");
		String msg = "";
		if (user != null) {
			try {
				service.add(user, roleIds);
				msg = "新增成功";
			} catch (Exception e) {
				msg = e.getMessage();
			}
		} else {
			msg = "无效的用户信息";
		}
		return msg;
	}

	/**
	 * 函数方法描述:修改用户
	 * 
	 * @param 用户vo模型信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateUserModel", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String updateUserModel( @ModelAttribute UserVo user,
			HttpServletRequest request) {
		String roleIds = request.getParameter("roleIds");
		String msg = "";
		if (user != null) {
			try {
				service.update(user, roleIds);
				msg = "更新成功";
			} catch (Exception e) {
				msg = e.getMessage();
			}
		} else {
			msg = "无效的用户信息";
		}
		return msg;
	}

	/**
	 * 函数方法描述:删除用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delUserModel", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String delUserModel( HttpServletRequest request) {
		String actionId = request.getParameter("actionId");
		String msg = "";
		if (actionId == null || "".equals(actionId)) {
			msg = "无效的用户id";
		} else {
			String[] ids = actionId.split(",");
			try {
				service.batchDelete(ids);
				msg = "删除成功";
			} catch (Exception e) {
				msg = e.getMessage();
			}
		}
		return msg;
	}

	/**
	 * 函数方法描述:判断用户登陆基本信息
	 * 
	 * @param 用户vo模型信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/isLoginIdExists", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public BusinessResult isLoginIdExists(@ModelAttribute UserVo user,
			 HttpServletRequest request) {
		String oldId = request.getParameter("oldId");
		boolean existsFlag = false;
		boolean success = false;
		String msg = "";
		if (user != null) {
			try {
				existsFlag = service.isLoginIdExists(user.getLoginId(), oldId);
			} catch (Exception e) {
				msg = e.getMessage();
			}
			if (existsFlag) {
				msg = "用户登录名已存在！";
			}
		} else {
			msg = "用户登录名为空，无法校验是否重复！";
		}
		success = true;
		return new BusinessResult(success, (Object) msg);
	}
     /**
      * 函数方法描述:根据员工ID获取所对应的用户信息
      * 
      * @param request
      * @return
      */
	@RequestMapping(value = "/findUserById", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	UserVo findUserById(HttpServletRequest request) {
		String empsId = request.getParameter("empsId");
		if (empsId != null && !"".equals(empsId)) {
			UserVo user = service.getUserById(empsId);
			return user;
		}
		return null;
	}

	/**
	 * 函数方法描述:修改密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePw", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String updatePw(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("------------------updatePw---------------------");
		String yspw = request.getParameter("yspw");
		String pw1 = request.getParameter("pw1");
		String pw2 = request.getParameter("pw2");
		UserVo user = (UserVo) request.getSession().getAttribute("currUser");
		String upw = user.getUserPass();
		String message = null;
		// 判断当前输入密码和原始密码是否一致
		if (!yspw.equals(upw)) {
			System.out.println(upw + "-------" + yspw);
			message = "当前密码不正确!";
		} else {
			// 如果一致则修改密码，反之，返回错误信息
			int count = -1;
			if (pw1.equals(pw2) || pw1 == pw2) {
				System.out.println("----------------用户名：" + user.getUserName());
				if (pw1.equals(CookieUtil.getUserCookieMD5(user.getUserName()))) {
					message = "用户名和密码不能一样";
				} else {
					user.setUserPass(pw1);
					count = service.resetUserPass(user);
				}
			}
			if (count == 1) {
				message = "密码修改成功!";
			}
		}
		return message;
	}

	/**
	 * 函数方法描述:跳转到密码修改页面
	 * 
	 * @param request
	 * @return modifyPassword.jsp
	 */
	@RequestMapping(value = "//toModifyPasswordPage", method = RequestMethod.GET)
	public ModelAndView toModifyPasswordPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(
				"pages/systemManager/modifyPassword");
		return mav;
	}

	/**
	 * 函数方法描述:如果用户非正常退出，解除已登录用户的会话
	 * 
	 * @param userVo
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/killLoginUser", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String killLoginUser(@ModelAttribute UserVo userVo,
			HttpServletRequest request, HttpServletResponse response) {
		String resultMessage;
		try {
			resultMessage = "";
			ServletContext application = request.getSession()
					.getServletContext();
			List<HttpSession> userListInSession = (ArrayList<HttpSession>) application
					.getAttribute("userList");
			for (int i = 0; i < userListInSession.size(); i++) {
				UserVo userTemp = (UserVo) userListInSession.get(i);
				if (userTemp.getLoginId().equals(userVo.getLoginId())) {
					userListInSession.remove(i);
				}
			}
			application.removeAttribute("userList");
			application.setAttribute("userList", userListInSession);
			resultMessage = "关闭已登录用户成功";
		} catch (Exception e) {
			resultMessage = "关闭已登录用户失败";
		}
		return resultMessage;
	}
	
	/**
	 * 函数方法描述:批量新增修改Demo
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/batchHandleDemo", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String,String> batchHandleDemo(){
		String flag = "false";
		String msg ="";
		Map<String,String> resultMap = new HashMap<String,String>();
		try{
			//demo数据
			List<AuditLogVo> insertList = new ArrayList<AuditLogVo>();
			List<AuditLogVo> updateList = new ArrayList<AuditLogVo>();
			for(int i=0;i<7;i++){
				AuditLogVo auditLogVo = new AuditLogVo();
				auditLogVo.setId(""+i+i+i);
				auditLogVo.setLoginId("test"+i);
				auditLogVo.setUserName("测试新增数据"+i);
				auditLogVo.setLoginState("1");
				auditLogVo.setAddMac("FC-0T-A3-QC-T6-TT");
				auditLogVo.setAddIp("192.169.70.198");
				insertList.add(auditLogVo);
			}
			for(int i=0;i<7;i++){
				AuditLogVo auditLogVo = new AuditLogVo();
				auditLogVo.setId(""+i+i+i);
				auditLogVo.setLoginId("testUpdate"+i);
				auditLogVo.setUserName("测试更行数据"+i);
				auditLogVo.setLoginState("1");
				auditLogVo.setAddMac("FC-0T-A3-QC-T6-TT");
				auditLogVo.setAddIp("192.169.70.198");
				updateList.add(auditLogVo);
			}
			//批量增加
			service.batchInsertDemo(insertList);
			//批量更新
			service.bachUpdateDemo(updateList);
			//删除测试数据
			AuditLogVo vo = new AuditLogVo();
			vo.setAddIp("192.169.70.198");
			service.deleteBatchData(vo);
			flag = "true";
		}catch(Exception e){
			System.out.println(e.getMessage());//后台打印异常信息
			msg = e.getMessage();
		}finally{
			resultMap.put("flag", flag);
			resultMap.put("msg", msg);
		}
		return resultMap;
	}
}
