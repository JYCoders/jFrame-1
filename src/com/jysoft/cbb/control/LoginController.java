package com.jysoft.cbb.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jysoft.cbb.service.LoginService;
import com.jysoft.cbb.vo.AuditLogVo;
import com.jysoft.cbb.vo.MenuVo;
import com.jysoft.cbb.vo.UserVo;
import com.jysoft.framework.util.AESUtils;
import com.jysoft.framework.util.ClientCompMessUtil;
import com.jysoft.framework.util.Constant;


/**
 * 
 * 类功能描述: 登录控制类
 * 创建者： 吴立刚
 * 创建日期： 2016-1-18
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	protected Logger logger = Logger.getLogger(this.getClass());
	private static String Page_Login = "pages/login"; // 登录页面
	private static String Page_IndexLeft = "pages/index_leftmode"; // 系统首页面:导航条在左侧
	private static String Page_IndexTop = "pages/index_topmode"; // 系统首页面:导航条在上方
	public static final String USER_IN_SESSION = "currUser"; // session用户ID
	public static final String AES_KEY = "c32ad1415f6c89fee76d8457c31efb4b"; //对称加密码
	public static final String USER_STATE = "1"; // 代表不允许多个客户端登录同一账号
	
	@Autowired
	private LoginService service; // 自动加载登录服务
	
	@Autowired
	private ClientCompMessUtil clientCompMessUtil; // 自动加载获取客户端机器配置的工具类

	/**
	 * 函数方法描述: 转向登录页面
	 * 
	 * @param request
	 *            访问请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toIndex", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String toIndex(HttpServletRequest request) throws Exception {
		Cookie[] cookies = request.getCookies();// 获取cookie
		if (null != cookies && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);// 让cookie过期
			}
		}
		String company = Constant.COMM_COMPANY;
		String system = Constant.COMM_SYSTEM;
		request.getSession().setAttribute("COMPANY", company);
		request.getSession().setAttribute("SYSTEM", system);

		return Page_Login;
	}
	
	/**
	 * 函数方法描述: 登录成功后转向首页,根据配置文件配置menustyle为left
	 * 
	 * @param request
	 *            访问请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/publish", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String publish(HttpServletRequest request) throws Exception {
		if("left".equals(Constant.MENUSTYLE.trim())){
			return Page_IndexLeft;
		}
		if("top".equals(Constant.MENUSTYLE.trim())){
			return Page_IndexTop;
		}
		return null;
	}

	/**
	 * 函数方法描述: 验证用户填写的登录信息是否正确
	 * 
	 * @param user 用户信息
	 * @param request 访问请求
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String doLogin(@ModelAttribute UserVo user,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String msg;
		AuditLogVo auditLogVo = new AuditLogVo();
		String ip = clientCompMessUtil.getIpAddr(request);
		String mac = clientCompMessUtil.getMacAddress(ip);
		auditLogVo.setAddIp(ip);
		auditLogVo.setAddMac(mac);
		String data = request.getParameter("data1");
		if (data == null) {
			data = "";
		}
		data = AESUtils.decryptForJS(data, AES_KEY);
		user.setUserPass(data.split("&&")[1]);
		user.setLoginId(data.split("&&")[0]);
		auditLogVo.setLoginId(data.split("&&")[0]);
		
		Cookie[] cookies = request.getCookies();// 获取cookie
		if (null != cookies && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);// 让cookie过期
			}
		}
		UserVo u = null;
		try {
			u = service.getUserByLoginId(user.getLoginId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 验证是否为正确连接
		if (u == null || user.getUserPass() == null
				|| "".equals(user.getUserPass())) {
			msg = "登录失败！";
			request.setAttribute("msg", msg);
			return Page_Login;
		}

		// 检查登陆用户的审计状态
		Double lockTime = Double.parseDouble(Constant.AUDIT_USERLOGIN_LOCKTIME) / 60 / 60 / 24;
		int lockLimit = Integer.parseInt(Constant.AUDIT_USERLOGIN_LOCKLIMIT);
		u.setLockTime(lockTime + "");
		int limit = service.checkUserAud(u);
		if (limit >= lockLimit) {
			auditLogVo.setLoginState("2");
			auditLogVo.setAudState("0");// 未处理
			try {
				service.saveAuditLog(auditLogVo);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			msg = "用户已锁定，如需立即解锁，请联系系统管理员";
			request.setAttribute("msg", msg);
			return Page_Login;
		}
		
		// 登录名和密码都相符
		if (AESUtils.decryptForJS(user.getUserPass(), AES_KEY).equals(
				u.getUserPass())) {
			if (Constant.COMM_USER_STATE != null
					&& Constant.COMM_USER_STATE.equals(USER_STATE)) {
				List<UserVo> userList = (ArrayList<UserVo>) request
						.getSession().getServletContext().getAttribute(
								"userList");
				UserVo oldUser = (UserVo) request.getSession().getAttribute(
						USER_IN_SESSION);
				if (oldUser != null) {
					// request.getSession().invalidate();
					ServletContext application = request.getSession()
							.getServletContext();
					List<HttpSession> userList1 = (ArrayList<HttpSession>) application
							.getAttribute("userList");

					if (userList1 != null && userList1.size() > 0) {
						// 销毁的session均从ArrayList集中移除
						if (oldUser != null) {
							userList.remove(oldUser);
						}
					}
				}
				if (userList != null && userList.size() > 0) {
					for (UserVo userLogin : userList) {
						if (user.getLoginId().equals(userLogin.getLoginId())) {
							msg = "该用户已登陆，请选择其他用户登录";
							request.setAttribute("msg", msg);
							return Page_Login;
						}
					}
				}

				request.getSession().setAttribute(USER_IN_SESSION, u);
				if (userList == null) {
					userList = new ArrayList<UserVo>();
				}
				userList.add(u);
				request.getSession().getServletContext().setAttribute(
						"userList", userList);
			} else {
				request.getSession().setAttribute(USER_IN_SESSION, u);
			}
			try {
				List<MenuVo> menuList = service.getMenuList(u);
				request.getSession().setAttribute("menuList", menuList);
				response.sendRedirect("publish");
			} catch (Exception e) {
				e.printStackTrace();
			}
			auditLogVo.setLoginState("1");
			auditLogVo.setAudState("");// 无状态
			service.saveAuditLog(auditLogVo);
			
			// 登录成功后将业务部门信息化负责人的角色ID记录在会话中
			request.getSession().setAttribute("bizItmanRoleId",
					Constant.COMM_BIZ_ITMAN_ROLEID);
			if("left".equals(Constant.MENUSTYLE)){
				return Page_IndexLeft;
			}
			if("top".equals(Constant.MENUSTYLE)){
				return Page_IndexTop;
			}
			return null;
		} else {
			// 记录审计日志表
			auditLogVo.setLoginState("0");
			auditLogVo.setAudState("0");// 未处理
			service.saveAuditLog(auditLogVo);
			if (lockLimit - limit - 1 > 0) {
				msg = "用户名密码错误" + (limit + 1) + "次，还有"
						+ (lockLimit - limit - 1) + "次机会，该用户将被锁定";
			} else {
				msg = "该用户已被锁定";
			}
			request.setAttribute("msg", msg);
			return Page_Login;
		}
	}

	/**
	 * 函数方法描述: 处理用户退出系统的请求，清楚当前登录用户在会话信息，并转向登录页面
	 * 
	 * @param request
	 *            访问请求
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exitApp", method = { RequestMethod.POST,
			RequestMethod.POST })
	public String exitApp(HttpServletRequest request,
			HttpServletResponse response) {
		// 主动注销则清除COOKIE
		// CookieUtil.clearCookie(response);
		UserVo user = (UserVo) request.getSession().getAttribute(
				USER_IN_SESSION);
		
		// 记录退出系统时间
		service.updateAuditLog(user);
		request.getSession().invalidate();
		String company = Constant.COMM_COMPANY;
		String system = Constant.COMM_SYSTEM;
		request.getSession().setAttribute("COMPANY", company);
		request.getSession().setAttribute("SYSTEM", system);
		
		return Page_Login;
	}
}
