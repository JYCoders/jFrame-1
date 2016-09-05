package com.jysoft.cbb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jysoft.cbb.dao.OrgMapper;
import com.jysoft.cbb.dao.UserMapper;
import com.jysoft.cbb.dao.UserRoleMapper;
import com.jysoft.cbb.vo.AdminOptLogVo;
import com.jysoft.cbb.vo.AuditLogVo;
import com.jysoft.cbb.vo.OrgVo;
import com.jysoft.cbb.vo.UserRoleVo;
import com.jysoft.cbb.vo.UserVo;
import com.jysoft.framework.util.Constant;
import com.jysoft.framework.util.CookieUtil;
import com.jysoft.framework.util.GUID;

/**
 * 
 * 类功能描述:用户类，处理用户操作及审计等信息
 * 创建者： 管马舟
 * 创建日期： 2016-01-22
 * 
 * 修改内容：增加批量新增修改方法
 * 修改者：黄智强
 * 修改日期：2016-03-06
 */
@Service
public class UserService {
	@Autowired(required = true)
	UserRoleMapper userRoleMapper;
	@Autowired(required = true)
	UserMapper mapper;
	@Autowired(required = true)
	OrgMapper orgMapper;
	
	/**
	 * 函数方法描述:重置密码
	 * 
	 * @param 用户vo模型信息
	 * @return
	 */
	@Transactional
	public Integer resetUserPass(UserVo user) {
		return mapper.resetUserPass(user);
	}

	/**
	 * 函数方法描述:更新用户
	 * 
	 * @param 用户vo模型信息
	 * @param 角色id字符串集合，集合以逗号分隔
	 */
	@Transactional
	public void update(UserVo user, String roleIds) {
		if (user.getUserPass() == null || "".equals(user.getUserPass())) {
			user.setUserPass(null);
		} else {
			user.setUserPass(CookieUtil.getUserCookieMD5(user.getUserPass()));
		}
		mapper.update(user);
		if (roleIds != null && !"".equals(roleIds)) {
			// 先清除所有指定用户的角色关系数据
			userRoleMapper.deleteByUserId(user.getUserId());
			// 批量添加用户角色关系数据
			for (String roleId : roleIds.split(",")) {
				UserRoleVo userRole = new UserRoleVo();
				userRole.setId(GUID.getGuid());
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getUserId());
				userRoleMapper.insert(userRole);
			}
		}
	}

	/**
	 * 函数方法描述:根据ID获取用户信息
	 * 
	 * @param 用户id
	 * @return
	 */
	public UserVo getUserById(String userId) {
		UserVo userModel = mapper.getUserById(userId);
		return userModel;
	}

	/**
	 * 函数方法描述:获得所有菜单
	 * 
	 * @return
	 */
	public List<UserVo> getAll() {
		List<UserVo> list = mapper.getAll();
		return list;
	}

	/**
	 * 函数方法描述:获取用户列表，并根据其神奇状态对其锁定状态进行赋值
	 * 
	 * @param 用户vo模型信息
	 * @return
	 */
	public Map<String, Object> getAllPage(UserVo userVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserVo> list = mapper.getAllPage(userVo);
		List<UserVo> userList = new ArrayList<UserVo>();
		// 检查用户的锁定状态
		for (UserVo user : list) {
			int lockTime = Integer.parseInt(Constant.AUDIT_USERLOGIN_LOCKTIME);
			int lockLimit = Integer
					.parseInt(Constant.AUDIT_USERLOGIN_LOCKLIMIT);
			user.setLockTime(lockTime + "");
			int limit = mapper.checkUserAud(user);
			if (limit >= lockLimit) {
				user.setLockState("解锁");// 已锁定
			} else {
				user.setLockState("未锁定");
			}
			userList.add(user);
		}
		String total = mapper.getAllTotal();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 函数方法描述:保存用户及角色关系(然后刷新菜单)，传入角色ID集合及用户ID
	 * 
	 * @param 用户id
	 * @param roleIds角色id字符串集合，集合以逗号分隔
	 */
	@Transactional
	public void saveUserRoles(String userId, String roleIds) {
		// 先清除所有指定用户的角色关系数据
		userRoleMapper.deleteByUserId(userId);
		// 批量添加用户角色关系数据
		for (String roleId : roleIds.split(",")) {
			UserRoleVo userRole = new UserRoleVo();
			userRole.setId(GUID.getGuid());
			userRole.setRoleId(roleId);
			userRole.setUserId(userId);
			userRoleMapper.insert(userRole);
		}
	}

	/**
	 * 函数方法描述:增加用户
	 * 
	 * @param 用户vo模型信息
	 * @param roleIds角色id字符串集合，集合以逗号分隔
	 */
	@Transactional
	public void add(UserVo user, String roleIds) {
		user.setUserId(GUID.getGuid());
		user.setUserPass(CookieUtil.getUserCookieMD5(user.getUserPass()));

		mapper.insert(user);
		if (roleIds != null && !"".equals(roleIds)) {
			for (String roleId : roleIds.split(",")) {
				UserRoleVo userRole = new UserRoleVo();
				userRole.setId(GUID.getGuid());
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getUserId());
				userRoleMapper.insert(userRole);
			}
		}
	}

	/**
	 * 函数方法描述:删除用户
	 * 
	 * @param 用户id集合
	 * @return
	 */
	@Transactional
	public boolean batchDelete(String[] ids) {
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				userRoleMapper.deleteByUserId(id);
				mapper.delete(id);
			}
		}
		return true;
	}

	/**
	 * 函数方法描述:判断新建用户是否已存在
	 * 
	 * @param 登录id
	 * @param 用户id
	 * @return
	 */
	public boolean isLoginIdExists(String loginId, String oldId) {
		UserVo dto = new UserVo();
		dto.setUserId(oldId);
		dto.setLoginId(loginId);
		Integer i = mapper.isLoginIdExists(dto);
		if (i > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 函数方法描述:根据部门名称获取所有的部门名称
	 * 
	 * @param 部门名称
	 * @return
	 */
	public String getOption(String selectedValue) {
		List<OrgVo> list = orgMapper.getOrgNameList();
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (OrgVo vo : list) {
				if (vo.getOrg_name().equals(selectedValue)) {
					sb.append("<option value=\"" + vo.getOrg_name()
							+ "\" selected = \"selected\" >" + vo.getOrg_name()
							+ "</option>");
				} else {
					sb.append("<option value=\"" + vo.getOrg_name() + "\" >"
							+ vo.getOrg_name() + "</option>");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 函数方法描述:获取auditUser
	 * 
	 * @param 审计auditLogVo对象信息
	 * @return
	 */
	public Map<String, Object> auditUserQuery(AuditLogVo auditLogVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AuditLogVo> list = mapper.auditUserQuery(auditLogVo);
		int total = mapper.auditUserQueryTotal(auditLogVo);
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 函数方法描述:解锁用户
	 * 
	 * @param 审计AdminOptLogVo对象信息
	 * @return
	 */
	public String openLockUser(AdminOptLogVo adminOptLogVo) {
		String result = "解锁失败";
		UserVo userVo = new UserVo();
		userVo.setLoginId(adminOptLogVo.getOptUser());
		Double lockTime = Double.parseDouble(Constant.AUDIT_USERLOGIN_LOCKTIME) / 60 / 60 / 24;
		userVo.setLockTime(lockTime + "");
		int openCount = mapper.openLockUser(userVo);// 更新监控状态
		if (openCount > 0) {
			result = "解锁成功";
			mapper.insertAdminOptLog(adminOptLogVo);// 记录用户管理操作表
		}
		return result;
	}

	/**
	 * 函数方法描述:获取用户登陆日志
	 * 
	 * @param 审计auditLogVo对象信息
	 * @return
	 */
	public Map<String, Object> auditUserDayQuery(AuditLogVo auditLogVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AuditLogVo> list = mapper.auditUserDayQuery(auditLogVo);
		int total = mapper.auditUserDayQueryTotal(auditLogVo);
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	
	/**
	 * 函数方法描述:批量新增Demo
	 * 
	 * @param insertList
	 */
	public void batchInsertDemo(List<AuditLogVo> insertList){
		mapper.batchInsertDemo(insertList);
	}
	
	/**
	 * 函数方法描述:批量更新Demo
	 * 
	 * @param updateList
	 */
	public void bachUpdateDemo(List<AuditLogVo> updateList){
		mapper.bachUpdateDemo(updateList);
	}
	
	/**
	 * 函数方法描述:删除Demo数据
	 * 
	 * @param auditLogVo
	 */
	public void deleteBatchData(AuditLogVo auditLogVo){
		mapper.deleteBatchData(auditLogVo);
	}
}
