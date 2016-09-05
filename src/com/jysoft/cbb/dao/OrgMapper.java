package com.jysoft.cbb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jysoft.cbb.vo.EmpInfoVo;
import com.jysoft.cbb.vo.OrgTreeVo;
import com.jysoft.cbb.vo.OrgVo;

/**
 * 
 * 类功能描述:组织机构、用户维护页面的查询接口
 * 创建者：黄智强
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public interface OrgMapper {

	/**
	 * 函数方法描述:查询该员工下是否有用户账号存在
	 * 
	 * @param ev
	 *            员工信息
	 * 
	 * @return 用户账号列表
	 */
	public List<EmpInfoVo> findRelationWithUser(EmpInfoVo eiv);

	/**
	 * 函数方法描述:获取所有组织机构信息
	 * 
	 * @return
	 */
	public List<OrgTreeVo> getOrgTree();

	/**
	 * 函数方法描述:获取组织结构树
	 * 
	 * @return
	 */
	public List<OrgTreeVo> getOrgTree2();

	/**
	 * 函数方法描述:根据组织机构ID查询组织机构信息
	 * 
	 * @param orgvo
	 * @return
	 */
	public OrgVo getOrgInfoByOrgId(OrgVo orgvo);

	/**
	 * 函数方法描述:新增组织机构
	 * 
	 * @param orgvo
	 * @return
	 */
	public Integer addNewOrgInfo(OrgVo orgvo);

	/**
	 * 函数方法描述:修改组织机构
	 * 
	 * @param orgvo
	 * @return
	 */
	public Integer updateOrgInfo(OrgVo orgvo);

	/**
	 * 函数方法描述:删除组织机构
	 * 
	 * @param orgvo
	 * @return
	 */
	public Integer deleteOrgInfo(OrgVo orgvo);

	/**
	 * 函数方法描述:获取所有员工信息
	 * 
	 * @return
	 */
	public List<EmpInfoVo> getAllEmpInfo();

	/**
	 * 函数方法描述:根据组织机构获取该组织机构下所有员工信息
	 * 
	 * @param eiv
	 * @return
	 */
	public EmpInfoVo getEmpInfoByOrgId(EmpInfoVo eiv);

	/**
	 * 函数方法描述:新增用户信息
	 * 
	 * @param eiv
	 * @return
	 */
	public Integer addNewEmpInfo(EmpInfoVo eiv);

	/**
	 * 函数方法描述:修改用户信息
	 * 
	 * @param eiv
	 * @return
	 */
	public Integer updateEmpInfo(EmpInfoVo eiv);

	/**
	 * 函数方法描述: 删除用户信息
	 * 
	 * @param emp_id
	 */
	public void deleteEmpInfo(@Param("emp_id") String emp_id);

	/**
	 * 函数方法描述:根据员工ID查询员工信息
	 * 
	 * @param eiv
	 * @return
	 */
	public EmpInfoVo findEmpInfoByEmpId(EmpInfoVo eiv);

	/**
	 * 函数方法描述:获得组织机构信息（用户生成下拉菜单中单位选项）
	 * 
	 * @return
	 */
	public List<OrgVo> getOrgCodeList();

	/**
	 * 函数方法描述:获得员工信息（用户生成下拉菜单中员工姓名选项）
	 * 
	 * @return
	 */
	public List<EmpInfoVo> getEmpInfoForCodeList();

	/**
	 * 函数方法描述:根据员工ID获得员工对应组织机构信息
	 * 
	 * @param vo
	 * @return
	 */
	public EmpInfoVo getOrgInfoByEmpId(EmpInfoVo vo);

	/**
	 * 函数方法描述:获取所有部门名称
	 * 
	 * @return
	 */
	public List<OrgVo> getOrgNameList();

	/**
	 * 函数方法描述:获得业务系统编辑中需要的所属业务部门信息
	 * 
	 * @return
	 */
	public List<OrgVo> getBizOrgInfos();

	/**
	 * 函数方法描述:根据父ID查询子集
	 * 
	 * @param pId
	 * @return
	 */
	public List<OrgVo> getChildrenById(String pId);

	/**
	 * 函数方法描述:检查组织编码是否重复
	 * 
	 * @param vo
	 * @return
	 */
	public List<OrgVo> checkOrgCodeUnique(OrgVo vo);

}
