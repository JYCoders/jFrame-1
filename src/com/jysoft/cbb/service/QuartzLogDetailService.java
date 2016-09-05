package com.jysoft.cbb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jysoft.cbb.dao.QuartzLogDetailMapper;
import com.jysoft.cbb.vo.QuartzLogDetailVo;

/**
 * 调度任务详细日志管理服务层
 * 创建人： 吴立刚
 * 创建时间：2014年11月24日
 */
@Service
public class QuartzLogDetailService {
	@Autowired(required = true)
	QuartzLogDetailMapper mapper;

	/**
	 * 获取详细日志信息
	 * 
	 * @param quartzLogDetailVo
	 *            任务信息
	 * @return 任务详细日志
	 */
	public Map<String, Object>   getTaskLogById(QuartzLogDetailVo quartzLogDetailVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<QuartzLogDetailVo> list = mapper.getTaskLogDetailById(quartzLogDetailVo);
		String total = mapper.getTaskLogDetailByIdTotal(quartzLogDetailVo);
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 插入日志信息
	 * 
	 * @param quartzLogDetailVo
	 *            任务执行日志
	 */
	@Transactional
	public void insertDetailLog(QuartzLogDetailVo quartzLogDetailVo) {
		mapper.insertLog(quartzLogDetailVo);
	}
}
