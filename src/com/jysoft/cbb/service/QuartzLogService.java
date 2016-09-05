package com.jysoft.cbb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jysoft.cbb.dao.QuartzLogMapper;
import com.jysoft.cbb.vo.QuartzLogVo;

/**
 * 调度任务日志管理服务层
 * 创建人： 吴立刚
 * 创建时间：2014年11月24日
 */
@Service
public class QuartzLogService {
	@Autowired(required = true)
	QuartzLogMapper mapper;

	/**
	 * 根据条件获取任务信息
	 * 
	 * @param quartzSetVo
	 *            任务信息
	 * @return 调度任务信息
	 */
	public List<QuartzLogVo> getTaskLogById(QuartzLogVo quartzLogVo) {
		return mapper.getTaskLogById(quartzLogVo);
	}

	/**
	 * 插入日志信息
	 * 
	 * @param quartzLogVo
	 *            任务执行日志
	 */
	@Transactional
	public void insertLog(QuartzLogVo quartzLogVo) {
		mapper.insertLog(quartzLogVo);
	}
	
	public void  updateLog(QuartzLogVo quartzLogVo) {
		mapper.updateLog(quartzLogVo);
	}
	
	/**
	 * 根据条件获取日志信息
	 * 
	 * @param quartzLogVo
	 *            任务信息
	 * @return 调度任务信息
	 */
	public Map<String, Object> getLogByTaskId(QuartzLogVo quartzLogVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<QuartzLogVo> list = mapper.getLogByTaskId(quartzLogVo);
		String total = mapper.getLogByTaskIdTotal(quartzLogVo);
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	public QuartzLogVo getLastTaskLog(QuartzLogVo lastLogVo) {
		return mapper.getLastTaskLog(lastLogVo);
	}
}
