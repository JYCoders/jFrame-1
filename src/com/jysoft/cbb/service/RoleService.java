package com.jysoft.cbb.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jysoft.cbb.dao.RoleMapper;
import com.jysoft.cbb.dao.RoleMenuMapper;
import com.jysoft.cbb.vo.RoleMenuVo;
import com.jysoft.cbb.vo.RoleVo;
import com.jysoft.cbb.vo.UserVo;
import com.jysoft.framework.util.GUID;

/**
 * 
 * 类功能描述:角色服务类，处理角色增删改查都功能
 * 创建者： 管马舟
 * 创建日期：2016-01-19
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Service
public class RoleService {

	@Autowired(required = true)
	private RoleMapper mapper;

	@Autowired(required = true)
	private RoleMenuMapper roleMenumapper;

	/**
	 * 函数方法描述:获取所有角色
	 * 
	 * @return
	 */
	public List<RoleVo> getAll() {
		return mapper.getAll();
	}

	/**
	 * 函数方法描述:获取所有角色,带分页
	 * 
	 * @param 角色对象信息
	 * @return
	 */
	public Map<String, Object> getAllPage(RoleVo roleVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<RoleVo> list = mapper.getAllPage(roleVo);
		String total = mapper.getAllTotal();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 函数方法描述:通过角色id获取角色信息
	 * 
	 * @param 角色对象信息
	 * @return
	 */
	public RoleVo getRoleById(RoleVo vo) {
		RoleVo bean = mapper.getRoleById(vo);
		return bean;
	}

	/**
	 * 函数方法描述:根据角色名称获取所有角色
	 * 
	 * @param 角色对象信息
	 * @return
	 */
	public List<RoleVo> getRolesByName(RoleVo vo) {
		List<RoleVo> list = mapper.getRolesByName(vo);
		return list;
	}

	/**
	 * 函数方法描述:根据用户id获取用户拥有的所有角色
	 * 
	 * @param 用户对象信息
	 * @return
	 */
	public List<RoleVo> getRolesByUserId(UserVo vo) {
		List<RoleVo> list = mapper.getRolesByUserId(vo);
		return list;
	}

	/**
	 * 函数方法描述:保存角色所有的菜单
	 * 
	 * @param 角色id
	 * @param 角色拥有的菜单信息
	 * @return
	 */
	public boolean saveRoleMenus(String roleId, String menus) {
		// 清空菜单
		RoleVo dto = new RoleVo();
		dto.setRoleId(roleId);
		roleMenumapper.deleteByRoleId(dto);
		Set<String> set = new HashSet<String>();
		// 循环添加 关系菜单对象
		if (menus != null && !"".equals(menus)) {
			for (String menuid : menus.split(",")) {
				set.add(menuid);   //删除重复的菜单id
			}
			for (String menuid : set) {
				RoleMenuVo roleMenu = new RoleMenuVo();
				roleMenu.setId(GUID.getGuid());
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menuid);
				roleMenumapper.insert(roleMenu);
			}
		}
		return true;
	}
    
	/**
	 * 函数方法描述:增加角色
	 * 
	 * @param 角色对象信息
	 * @return
	 */
	public boolean insert(RoleVo role) {
		role.setRoleId(GUID.getGuid());
		mapper.insert(role);
		return true;
	}
    
	/**
	 * 函数方法描述:修改角色
	 * 
	 * @param 角色对象信息
	 * @return
	 */
	public boolean update(RoleVo role) {
		if (role != null && role.getRoleId() != null) {
			mapper.update(role);
			return true;
		}
		return false;
	}
    
	/**
	 * 函数方法描述:删除角色
	 * 
	 * @param 角色对象信息
	 * @return
	 */
	public boolean delete(RoleVo role) {
		if (role != null && role.getRoleId() != null) {
			roleMenumapper.deleteByRoleId(role);
			mapper.delete(role);
			return true;
		}
		return false;
	}
}
