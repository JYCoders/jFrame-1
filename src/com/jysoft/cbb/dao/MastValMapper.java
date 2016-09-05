package com.jysoft.cbb.dao;

import java.util.List;

import com.jysoft.cbb.vo.ComBoxSelectVo;
import com.jysoft.cbb.vo.MastBaseMessageVo;
import com.jysoft.cbb.vo.MastTypeVo;
import com.jysoft.cbb.vo.MastValVo;
/**
 * 
 * 类功能描述:码表管理持久层
 * 创建者： 黄智强
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public interface MastValMapper {
	/**
	 * 函数方法描述:根据类型代码查询 T_MAST_VAL 表
	 * 
	 * @param masterCode
	 * @return
	 */
	public List<MastValVo> getMastValByMasterCode(String masterCode);

	/**
	 * 函数方法描述:获取所有的系统码表
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public List<MastTypeVo> getMastInfoList(MastTypeVo mastTypeVo);

	/**
	 * 函数方法描述:获取所有的系统码表总数
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public String getMastInfoListTotal(MastTypeVo mastTypeVo);

	/**
	 * 函数方法描述:添加系统码表基本信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public int addMastTypeInfo(MastTypeVo mastTypeVo);

	/**
	 * 函数方法描述:根据主键获取码表类型的信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public MastTypeVo getMastInfoById(MastTypeVo mastTypeVo);

	/**
	 * 更新系统码表基本信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public int updateMastTypeInfo(MastTypeVo mastTypeVo);

	/**
	 * 根据主键获取码表值的信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public List<MastValVo> getMastInfoListById(MastValVo mastValVo);

	/**
	 * 根据主键获取码表值的信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public String getMastInfoListByIdTotal(MastValVo mastValVo);

	/**
	 * 根据主键获取码表值的信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public MastValVo getMastValInfoById(MastValVo mastValVo);

	/**
	 * 更新系统码表值基本信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public int updateMastValInfo(MastValVo mastValVo);

	/**
	 * 添加系统码表值基本信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public int addMastValInfo(MastValVo mastValVo);

	/**
	 * 验证mastCode 是否存在
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public int initCount(MastTypeVo mastTypeVo);

	/**
	 * 验证值编码是否已经存在
	 * 
	 * @param mastValVo
	 * @return
	 */
	public int valCodeCount(MastValVo mastValVo);

	/**
	 * 删除码表值
	 * 
	 * @param mastValVo
	 * @return
	 */
	public int deleteMastVal(MastValVo mastValVo);

	/**
	 * 删除码表
	 * 
	 * @param mastValVo
	 * @return
	 */
	public int deleteMastType(MastValVo mastValVo);

	/**
	 * 删除码表所有的值
	 * 
	 * @param mastValVo
	 * @return
	 */
	public int deleteVal(MastValVo mastValVo);

	/**
	 * 函数方法描述:获取码表基础汇总数据
	 * 
	 * @param mastBaseMessageVo
	 * @return
	 */
	public List<MastBaseMessageVo> getMastTree();

	/**
	 * 函数方法描述:获取单个mast的基本信息
	 * 
	 * @param mastBaseMessageVo
	 * @return
	 */
	public MastBaseMessageVo getSignleMastByID(
			MastBaseMessageVo mastBaseMessageVo);

	/**
	 * 函数方法描述:获取上级代码下拉框代码
	 * 
	 * @param comBoxVo
	 * @return
	 */
	public List<ComBoxSelectVo> getParentComList();
	/**
	 * 函数方法描述:根据masterCode的值获取其对应的ID
	 * 
	 * @param masterCode
	 * @return
	 */
	public String getIdByMasterCode(String masterCode);
	/**
	 * 函数方法描述:获取异步加载树的数据
	 * 
	 * @param nodeId
	 * @return
	 */
	public List<MastBaseMessageVo> getSynocMastTree(String nodeId);
}
