package com.jysoft.cbb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jysoft.cbb.dao.OrgMapper;
import com.jysoft.cbb.vo.EmpInfoVo;
import com.jysoft.cbb.vo.OrgTreeVo;
import com.jysoft.cbb.vo.OrgVo;
/**
 * 
 * 类功能描述:组织管理service层
 * 创建者： 黄智强
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Service
public class OrgService {
	@Autowired(required = true)
	private OrgMapper mapper;

	/**
	 * 函数方法描述:查询该员工下是否有用户账号存在
	 * 
	 * @param ev 员工信息
	 *            
	 * @return 用户账号列表
	 */
	public List<EmpInfoVo> findRelationWithUser(EmpInfoVo eiv) {
		return this.mapper.findRelationWithUser(eiv);
	}

	/**
	 * 函数方法描述:查询所有组织机构信息包涵人员信息
	 * 
	 * @return
	 */
	public List<OrgTreeVo> getAllOrgInfo() {
		return this.mapper.getOrgTree();
	}

	/**
	 * 函数方法描述:查询所有组织机构信息不包含人员信息
	 * 
	 * @return
	 */
	public List<OrgTreeVo> getAllOrgInfo2() {
		return this.mapper.getOrgTree2();
	}

	/**
	 * 函数方法描述:根据父ID查询子集
	 * 
	 * @param pId
	 * @return
	 */
	public List<OrgVo> getChildrenById(String pId) {
		return this.mapper.getChildrenById(pId);
	}

	/**
	 * 函数方法描述:根据ID查询组织机构信息
	 * 
	 * @param vo
	 * @return
	 */
	public OrgVo getOrgInfoByOrgId(OrgVo vo) {

		return this.mapper.getOrgInfoByOrgId(vo);
	}

	/**
	 * 函数方法描述:新增组织机构信息
	 * 
	 * @param ov
	 * @return
	 */
	public boolean addNewOrgInfo(OrgVo ov) {
		this.mapper.addNewOrgInfo(ov);
		return true;
	}

	/**
	 * 函数方法描述:修改组织机构信息
	 * 
	 * @param ov
	 * @return
	 */
	public boolean updateOrgInfo(OrgVo ov) {
		this.mapper.updateOrgInfo(ov);
		return true;
	}

	/**
	 * 函数方法描述:删除组织机构信息
	 * 
	 * @param ov
	 * @return
	 */
	public boolean deleteOrgInfo(OrgVo ov) {
		this.mapper.deleteOrgInfo(ov);
		return true;
	}

	/**
	 * 函数方法描述:查询所有员工信息
	 * 
	 * @return
	 */
	public List<EmpInfoVo> getAllEmpInfo() {
		return this.mapper.getAllEmpInfo();
	}

	/**
	 * 函数方法描述:获取某组织机构下员工信息
	 * 
	 * @param eiv
	 * @return
	 */
	public EmpInfoVo getEmpInfoByOrgId(EmpInfoVo eiv) {
		return this.mapper.getEmpInfoByOrgId(eiv);
	}

	/**
	 * 函数方法描述:新增员工信息
	 * 
	 * @param eiv
	 * @return
	 */
	public boolean addNewEmpInfo(EmpInfoVo eiv) {
		this.mapper.addNewEmpInfo(eiv);
		return true;
	}

	/**
	 * 函数方法描述:修改员工信息
	 * 
	 * @param eiv
	 * @return
	 */
	public boolean updateEmpInfo(EmpInfoVo eiv) {
		this.mapper.updateEmpInfo(eiv);
		return true;
	}

	/**
	 * 函数方法描述:删除员工信息
	 * 
	 * @param eiv
	 * @return
	 */
	public boolean deleteEmpInfo(String eiv) {
		this.mapper.deleteEmpInfo(eiv);
		return true;
	}

	/**
	 * 函数方法描述:根据员工ID查询员工信息
	 * 
	 * @param eiv
	 * @return
	 */
	public EmpInfoVo findEmpInfoByEmpId(EmpInfoVo eiv) {

		EmpInfoVo empInfo = this.mapper.findEmpInfoByEmpId(eiv);
		if (empInfo != null) {
			return empInfo;
		} else {
			EmpInfoVo emp = new EmpInfoVo();
			return emp;
		}
	}

	/**
	 * 函数方法描述:获得组织机构信息（用户生成下拉菜单中单位选项）
	 * 
	 * @return
	 */
	public List<OrgVo> getOrgCodeList() {
		return this.mapper.getOrgCodeList();
	}

	/**
	 * 函数方法描述:获得员工信息（用户生成下拉菜单中员工姓名选项）
	 * 
	 * @return
	 */
	public List<EmpInfoVo> getEmpInfoForCodeList() {
		return this.mapper.getEmpInfoForCodeList();
	}

	/**
	 * 函数方法描述:根据员工ID获得组织机构信息
	 * 
	 * @param vo
	 * @return
	 */
	public EmpInfoVo getOrgInfoByEmpId(EmpInfoVo vo) {
		return this.mapper.getOrgInfoByEmpId(vo);
	}

	/**
	 * 函数方法描述:获得业务系统编辑中需要的所属业务部门信息
	 * 
	 * @return
	 */
	public List<OrgVo> getBizOrgInfos() {
		return mapper.getBizOrgInfos();
	}

	/**
	 * 函数方法描述:检查组织编码是否重复
	 * 
	 * @param vo
	 * @return
	 */
	public List<OrgVo> checkOrgCodeUnique(OrgVo vo) {
		return mapper.checkOrgCodeUnique(vo);
	}
}
