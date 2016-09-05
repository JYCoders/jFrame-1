package com.jysoft.cbb.dao;

import java.util.List;

import com.jysoft.cbb.vo.AdminOptLogVo;
import com.jysoft.cbb.vo.AuditLogVo;
import com.jysoft.cbb.vo.MenuVo;
import com.jysoft.cbb.vo.UserVo;

/**
 * 
 * 类功能描述: 用户类持久层接口
 * 创建者： 管马舟
 * 创建日期： 2016-01-22
 * 
 * 修改内容：增加批量新增修改方法
 * 修改者：黄智强
 * 修改日期：2016-03-06
 */
public interface UserMapper {

	/**
	 * 函数方法描述: 获取所有用户信息
	 * 
	 * @return 用户信息结果集
	 */
	public List<UserVo> getAll();

	/**
	 * 函数方法描述: 分页获取用户信息
	 * 
	 * @param userVo
	 *            用户信息
	 * @return 用户信息结果集
	 */
	public List<UserVo> getAllPage(UserVo userVo);

	/**
	 * 函数方法描述: 获取用户总数
	 * 
	 * @return 用户总数
	 */
	public String getAllTotal();

	/**
	 * 函数方法描述: 通过用户ID获取用户信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户信息
	 */
	public UserVo getUserById(String userId);

	/**
	 * 函数方法描述: 通过登录ID获取用户信息
	 * 
	 * @param loginId
	 *            登录ID
	 * @return 用户信息
	 */
	public UserVo getUserByLoginId(String loginId);

	/**
	 * 函数方法描述: 更新用户信息
	 * 
	 * @param userVo
	 *            用户信息
	 * @return 更新成功条数
	 */
	public int update(UserVo userVo);

	/**
	 * 函数方法描述: 重置用户密码
	 * 
	 * @param userVo
	 *            用户信息
	 * @return 更新成功条数
	 */
	public int resetUserPass(UserVo userVo);

	/**
	 * 函数方法描述: 插入用户信息
	 * 
	 * @param userVo
	 *            用户信息
	 * @return 插入成功条数
	 */
	public int insert(UserVo userVo);

	/**
	 * 函数方法描述: 根据用户ID删除用户信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 删除条数
	 */
	public int delete(String userId);

	/**
	 * 函数方法描述: 根据登录ID获取用户条数
	 * 
	 * @param userVo
	 *            用户信息
	 * @return 用户条数
	 */
	public int isLoginIdExists(UserVo userVo);

	/**
	 * 函数方法描述: 通过登录ID和密码获取用户信息
	 * 
	 * @param loginId
	 *            登录ID
	 * @param password
	 *            登录密码
	 * @return 用户信息
	 */
	public UserVo getUserByLoginIdAndPass(String loginId, String password);

	/**
	 * 函数方法描述: 插入用户审计日志
	 * 
	 * @param auditLogVo
	 *            用户审计日志
	 * @return 插入成功条数
	 */
	public int saveAuditLog(AuditLogVo auditLogVo);

	/**
	 * 函数方法描述: 更新用户审计日志
	 * 
	 * @param userVo
	 *            用户信息
	 * @return 更新成功条数
	 */
	public int updateAuditLog(UserVo userVo);

	/**
	 * 函数方法描述: 获取用户输入密码错误次数
	 * 
	 * @param userVo
	 *            用户信息
	 * @return 用户输入密码错误次数
	 */
	public int checkUserAud(UserVo userVo);

	/**
	 * 函数方法描述: 获取用户审计记录
	 * 
	 * @param auditLogVo
	 *            用户审计信息
	 * @return 用户审计记录结果集
	 */
	public List<AuditLogVo> auditUserQuery(AuditLogVo auditLogVo);

	/**
	 * 函数方法描述: 获取用户审计记录条数
	 * 
	 * @param auditLogVo
	 *            用户审计信息
	 * @return 用户审计记录条数
	 */
	public int auditUserQueryTotal(AuditLogVo auditLogVo);

	/**
	 * 函数方法描述: 锁定用户解锁
	 * 
	 * @param userVo
	 *            用户信息
	 * @return
	 */
	public int openLockUser(UserVo userVo);

	/**
	 * 函数方法描述: 插入管理员操作日志
	 * 
	 * @param adminOptLogVo
	 *            管理员操作日志
	 * @return 插入条数
	 */
	public int insertAdminOptLog(AdminOptLogVo adminOptLogVo);

	/**
	 * 函数方法描述: 获取当前时间用户审计日志
	 * 
	 * @param auditLogVo
	 *            用户审计信息
	 * @return 用户审计日志结果集
	 */
	public List<AuditLogVo> auditUserDayQuery(AuditLogVo auditLogVo);

	/**
	 * 函数方法描述: 获取当前时间用户审计日志总条数
	 * 
	 * @param auditLogVo
	 *            用户审计信息
	 * @return 审计日志总条数
	 */
	public int auditUserDayQueryTotal(AuditLogVo auditLogVo);

	/**
	 * 函数方法描述: 根据用户获取权限菜单
	 * 
	 * @param userVo
	 *            用户信息
	 * @return 菜单结果集
	 */
	public List<MenuVo> getMenuList(UserVo userVo);
	
	/**
	 * 函数方法描述:批量新增Demo
	 * 
	 * @param insertList
	 */
	public void batchInsertDemo(List<AuditLogVo> insertList);
	
	/**
	 * 函数方法描述:批量更新Demo
	 * 
	 * @param updateList
	 */
	public void bachUpdateDemo(List<AuditLogVo> updateList);
	
	/**
	 * 函数方法描述:删除Demo数据
	 * 
	 * @param auditLogVo
	 */
	public void deleteBatchData(AuditLogVo auditLogVo);
}
