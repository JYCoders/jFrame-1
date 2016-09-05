package com.jysoft.cbb.control;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 
 * 类功能描述:首页
 * 创建者： 黄智强
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Controller
@RequestMapping("/mainPage")
public class MainPageController {
	private static String Page_MenuMgr = "pages/mainPage/mainPage";
	protected Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 函数方法描述: 转向访问页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toManageMainPage", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String index(HttpServletRequest request) {
		return Page_MenuMgr;
	}

}
