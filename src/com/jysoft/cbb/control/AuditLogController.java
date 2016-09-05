package com.jysoft.cbb.control;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jysoft.cbb.service.UserService;
import com.jysoft.cbb.vo.AdminOptLogVo;
import com.jysoft.cbb.vo.AuditLogVo;
import com.jysoft.cbb.vo.UserVo;

/**
 * 
 * 类功能描述:用户审计
 * 创建者： 黄智强
 * 创建日期： 2016-1-18
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Controller
@RequestMapping("/audit")
public class AuditLogController {
	private static String Page_MenuMgrNew = "pages/systemManager/auditLog";
	@Autowired
	private UserService service;

	/**
	 * 函数方法描述:跳转到用户审计页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toAuditLogPage", method = RequestMethod.GET)
	public String toAuditLogPage() {
		return Page_MenuMgrNew;
	}

	/**
	 * 函数方法描述:加载audit
	 * 
	 * @param auditLogVo
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/auditUserQuery", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object> auditUserQuery(
			@ModelAttribute AuditLogVo auditLogVo, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (auditLogVo != null) {
				if (auditLogVo.getLoginState() != null
						&& !"".equals(auditLogVo.getLoginState())) {
					String loginState = URLDecoder.decode(
							auditLogVo.getLoginState(), "utf-8");
					auditLogVo.setLoginState(loginState);
				}
				if (auditLogVo.getLoginTime() != null
						&& !"".equals(auditLogVo.getLoginTime())) {
					String loginTime = URLDecoder.decode(
							auditLogVo.getLoginTime(), "utf-8");
					auditLogVo.setLoginTime(loginTime);
				} else {
					auditLogVo.setLoginTime(new SimpleDateFormat("yyyyMMdd")
							.format(new Date()));
				}
			}
			map = service.auditUserQuery(auditLogVo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 函数方法描述:解锁用户
	 * 
	 * @param auditLogVo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/openLockUser", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String openLockUser(@ModelAttribute AuditLogVo auditLogVo,
			HttpServletRequest request) {
		AdminOptLogVo adminOptLogVo  = new AdminOptLogVo();
		UserVo userVo = (UserVo) request.getSession().getAttribute("currUser");
		String result;
		if (!"D1843F1D0FCE504E310844F0F42F4F8827AB0E208BF6C03B22".equals(userVo
				.getRoleId())) {
			result = "只有系统管理员才能解锁";
		} else {
			adminOptLogVo.setAdminUser(userVo.getLoginId());
			adminOptLogVo.setOptUser(auditLogVo.getLoginId());
			result = service.openLockUser(adminOptLogVo);
		}
		return result;
	}

	/**
	 * 函数方法描述:加载当日所有用户
	 * 
	 * @param auditLogVo
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/auditUserDayQuery", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object> auditUserDayQuery(
			@ModelAttribute AuditLogVo auditLogVo, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (auditLogVo != null) {
				if (auditLogVo.getLoginState() != null
						&& !"".equals(auditLogVo.getLoginState())) {
					String loginState = URLDecoder.decode(
							auditLogVo.getLoginState(), "utf-8");
					auditLogVo.setLoginState(loginState);
				}
				if (auditLogVo.getLoginId() != null
						&& !"".equals(auditLogVo.getLoginId())) {
					String loginId = URLDecoder.decode(auditLogVo.getLoginId(),
							"utf-8");
					auditLogVo.setLoginId(loginId);
				}
				if (auditLogVo.getLoginTime() != null
						&& !"".equals(auditLogVo.getLoginTime())) {
					String loginTime = URLDecoder.decode(
							auditLogVo.getLoginTime(), "utf-8");
					auditLogVo.setLoginTime(loginTime);
				} else {
					auditLogVo.setLoginTime(new SimpleDateFormat("yyyyMMdd")
							.format(new Date()));
				}
			}
			map = service.auditUserDayQuery(auditLogVo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

}
