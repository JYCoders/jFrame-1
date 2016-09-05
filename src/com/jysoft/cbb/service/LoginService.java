package com.jysoft.cbb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jysoft.cbb.dao.UserMapper;
import com.jysoft.cbb.vo.AuditLogVo;
import com.jysoft.cbb.vo.MenuVo;
import com.jysoft.cbb.vo.UserVo;

/**
 * 
 * 类功能描述: 负责处理登录控制器与登录DAO的交互
 * 创建者： 吴立刚
 * 创建日期： 2016-1-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Service
public class LoginService {
	@Autowired
	private UserMapper dao;

	/**
	 * 函数方法描述: 根据用户登录ID来获取用户信息
	 * 
	 * @param loginId
	 *            登录ID
	 * @return 用户信息
	 */
	public UserVo getUserByLoginId(String loginId) {
		return dao.getUserByLoginId(loginId);
	}

	/**
	 * 函数方法描述: 记录登陆审计日志表
	 * 
	 * @param auditLogVo
	 *            审计日志
	 * @return
	 */
	public Integer saveAuditLog(AuditLogVo auditLogVo) {
		return dao.saveAuditLog(auditLogVo);
	}

	/**
	 * 函数方法描述: 更新登陆审计日志表
	 * 
	 * @param user
	 *            用户信息
	 * @return
	 */
	public Integer updateAuditLog(UserVo user) {
		return dao.updateAuditLog(user);
	}

	/**
	 * 函数方法描述: 获取用户输错密码次数
	 * 
	 * @param user
	 *            用户信息
	 * @return
	 */
	public Integer checkUserAud(UserVo user) {
		return dao.checkUserAud(user);
	}

	/**
	 * 函数方法描述: 获取用户权限菜单
	 * 
	 * @param userVo
	 *            用户信息
	 * @return 菜单信息
	 */
	public List<MenuVo> getMenuList(UserVo userVo) {
		return dao.getMenuList(userVo);
	}
}
