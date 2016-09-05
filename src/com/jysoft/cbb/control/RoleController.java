package com.jysoft.cbb.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jysoft.cbb.service.RoleService;
import com.jysoft.cbb.vo.RoleVo;
import com.jysoft.cbb.vo.UserVo;
import com.jysoft.framework.util.TreeJson;
/**
 * 
 * 类功能描述:角色控制类
 * 创建者： 管马舟
 * 创建日期：  2016-01-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	
	private static String Page_RoleMgr="pages/systemManager/role";
	@Autowired
	private RoleService service;
	
	@RequestMapping(value="/toManageRoles",method=RequestMethod.GET)   
	public String toManageRolesPage() {
		return Page_RoleMgr;
	}
	
	/**
	 * 函数方法描述:查询所有角色
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/allRoles", method = {RequestMethod.GET, RequestMethod.POST })
	public List<RoleVo> allRoles(HttpServletRequest request) {
		List<RoleVo> list  = service.getAll();
		return list;
	}
	
	/**
	 * 函数方法描述:获取角色树
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/getRoleTree", method = {RequestMethod.GET, RequestMethod.POST })
	public void getRoleTree(HttpServletRequest request,HttpServletResponse response) {
		String jsonResult = null;
		List<RoleVo> list  = service.getAll();
		jsonResult = TreeJson.formatListToTreeJsonString(list, "getRoleId", "getRoleId", "getRoleName");
		try {
			response.getWriter().write(jsonResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
    /**
     * 函数方法描述:按照easyui中的datagrid数据格式查询所有角色
     * 
     * @param 角色信息vo
     * @param request
     * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/allRolesForGridNoCheckBox", method = {RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> allRolesForGridNoCheckBox(@ModelAttribute RoleVo roleVo, HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = service.getAllPage(roleVo);
		return map;
	}
	
	/** 通过用户ID查询出相关联的所有角色
	 * 函数方法描述:
	 * 
	 * @param request
	 * @param 角色信息vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRolesByUserId", method = {RequestMethod.GET, RequestMethod.POST })
	public List<RoleVo> getRolesByUserId(HttpServletRequest request,@ModelAttribute UserVo vo) {
		List<RoleVo> list;
		list = service.getRolesByUserId(vo);
		return list;
	}
	
	/**
	 * 函数方法描述:保存角色所拥有的菜单
	 * 
	 * @param 角色信息vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveRoleMenus", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public String saveRoleMenus(@ModelAttribute RoleVo roleModel,HttpServletRequest request) {
		boolean success = false;
		String menuIdsTemp = request.getParameter("menuIdsTemp");
		if (roleModel != null && roleModel.getRoleId() != null) {
			success = service.saveRoleMenus(roleModel.getRoleId(),menuIdsTemp);
		}
		String msg = success ? "保存成功！" : "保存失败！";		
		return msg;
	}
	
	/**
	 * 函数方法描述:添加角色
	 * 
	 * @param 角色信息vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveRoleModel", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public String saveRoleModel(@ModelAttribute RoleVo roleModel,HttpServletRequest request) {
		
		List<RoleVo> existRole = service.getRolesByName(roleModel);
		if(existRole != null && existRole.size()>0){
			return "角色名称已存在，不能重复添加";
		}else{
			return service.insert(roleModel) ? "保存成功" : "保存失败";
		}
	}
	
	/**
	 * 函数方法描述:更新角色
	 * 
	 * @param 角色信息vo
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/updateRoleModel", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public String updateRoleModel(@ModelAttribute RoleVo roleModel,HttpServletRequest request) {

		List<RoleVo> existRole = service.getRolesByName(roleModel);
		if(existRole != null && existRole.size()>0){
			return "修改后的角色名称已存在，不能重复添加";
		}else{
			return service.update(roleModel) ? "修改成功！" : "修改失败！";
		}
	}

    /**
     * 函数方法描述:角色删除
     * 
     * @param 角色信息vo
     * @param request
     * @return
     */
	@RequestMapping(value = "/delRoleModel", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public String delRoleModel(@ModelAttribute RoleVo roleModel,HttpServletRequest request) {
		return service.delete(roleModel) ? "删除成功！" : "删除失败！";
	}
}
