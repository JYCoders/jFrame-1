package com.jysoft.cbb.dao;

import java.util.List;

import com.jysoft.cbb.vo.ComBoxSelectVo;

/**
 * 
 * 类功能描述: 公共持久层接口
 * 创建者： 吴立刚
 * 创建日期： 2016-1-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public interface CommonMapper {

	/**
	 * 函数方法描述: 根据类型获取下拉框信息
	 * 
	 * @param comBoxVo
	 *            下拉框信息
	 * @return 下拉框结果集
	 */
	public List<ComBoxSelectVo> getMiddleWareComList(ComBoxSelectVo comBoxVo);
}
