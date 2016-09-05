package com.jysoft.cbb.control;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jysoft.cbb.service.CommonService;
import com.jysoft.cbb.service.MenuService;
import com.jysoft.cbb.vo.MenuVo;
import com.jysoft.cbb.vo.RoleVo;
import com.jysoft.cbb.vo.TreeVo;
import com.jysoft.cbb.vo.UserVo;


/**
 * 
 * 类功能描述:菜单控制类
 * 创建者： 管马舟
 * 创建日期： 2016-01-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
	private static String Page_MenuMgr="pages/systemManager/menuManager";
	
	protected Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MenuService service;
	
	@RequestMapping(value = "/toManageMenuData", method = RequestMethod.GET)
	public String toManageMenuPage() {
		return Page_MenuMgr;
	}
	
	/**
	 * 函数方法描述:返回所有菜单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/allMenus", method = {RequestMethod.GET, RequestMethod.POST })
	public List<MenuVo> allMenus() {
		List<MenuVo> list = null;
		try {
			list = service.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 函数方法描述:获取菜单树
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/getMenuTree", method = {RequestMethod.GET, RequestMethod.POST })
	public List<TreeVo> getMenuTree(HttpServletRequest request,HttpServletResponse response) {
		List<MenuVo> list =  service.getAll();
		TreeVo rootVo = new TreeVo();
		rootVo.setId("M"); 
		rootVo.setText("功能菜单");
		try {
			rootVo = CommonService.buildTree(list,rootVo,"M","getMenuId","getParentId","getMenuName");
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TreeVo> list2 = new ArrayList<TreeVo>();
		list2.add(rootVo);
		return list2;
	}
	
	
	/**
	 * 函数方法描述:
	 * 
	 * @param QueryVo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/allMenusByRoleId", method = {RequestMethod.GET, RequestMethod.POST })
	public List<MenuVo> allMenusByRoleId(@ModelAttribute RoleVo roleVo,HttpServletRequest request) {
		List<MenuVo> list = null;
		list = service.getMenusByRoleId(roleVo);
		return list;
	}
	
	/**
	 * 函数方法描述:获取当前用户 有权限的所有菜单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllMenusByCurrUser", method = {RequestMethod.GET, RequestMethod.POST })
	public List<MenuVo> getCurrMenus(HttpServletRequest request) {
		UserVo user=(UserVo)request.getSession().getAttribute("currUser");
		List<MenuVo> list = null;
		if(user!=null){
			list=service.getMenusByUserId(user.getUserId());
			return list;
		}
		return null;
	}
	
	
	/**
	 * 函数方法描述:按照easyui中的datagrid数据格式查询全部菜单数据
	 * 
	 * @param menuModel
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMenuDataForGrid", method = {RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> queryMenuDataForGrid(@ModelAttribute MenuVo menuModel,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (menuModel != null && menuModel.getMenuName() != null
					&& !"".equals(menuModel.getMenuName())) {
				String menuName = URLDecoder.decode(menuModel.getMenuName(),
						"utf-8");
				menuModel.setMenuName(menuName);

			}
			map = service.getMenusByCondition(menuModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 函数方法描述:更新菜单
	 * 
	 * @param menuModel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateMenuModel", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody public String updateMenuModel(@ModelAttribute MenuVo menuModel,HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		boolean success = false;
		try {
			if (menuModel != null) {
				success = service.update(menuModel);
			} else
				success = false;
			msg = success ? "更新成功" : "更新失败";
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			msg = "更新失败,错误原因：" + e.getMessage();
		}
		return msg;
	}
	/**
	 * 函数方法描述: 增加新菜单项
	 * 
	 * @param menuModel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addMenuModel", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody public String addMenuModel(@ModelAttribute MenuVo menuModel,HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		boolean success = false;
		try {
			if (menuModel != null) {
				success = service.addMenu(menuModel);
			} else
				success = false;
			msg = success ? "添加成功" : "添加失败";
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			success = false;
			msg = "添加失败,错误原因：" + e.getMessage();
		}
		return msg;
	}
	
	/**
	 * 函数方法描述:根据菜单Id获取菜单的相信信息
	 * 
	 * @param menuModel
	 * @param request
	 * @param response
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/queryMenuDataByMenuId", method = {RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> queryMenuDataByMenuId(@ModelAttribute MenuVo menuModel,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (menuModel != null && menuModel.getMenuName() != null
					&& !"".equals(menuModel.getMenuName())) {
				String menuName = URLDecoder.decode(menuModel.getMenuName(),
						"utf-8");
				menuModel.setMenuName(menuName);
			}
			map = service.queryMenuDataByMenuId(menuModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
	/**
	 * 函数方法描述:删除菜单
	 * 
	 * @param menuModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteMenuInfo", method = {RequestMethod.GET, RequestMethod.POST })
	public String deleteMenuInfo(@ModelAttribute MenuVo menuModel,HttpServletRequest request) {
		String msg = "";
		boolean success = false;
		try {
			if (menuModel != null) {
				success = service.deleteMenuInfo(menuModel);
			} else
				success = false;
			msg = success ? "删除成功" : "删除失败";
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			msg = "删除失败,错误原因：" + e.getMessage();
		}
		return msg;
	
	}
}
