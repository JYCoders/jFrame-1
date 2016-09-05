package com.jysoft.cbb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jysoft.cbb.dao.QuartzLogMapper;
import com.jysoft.cbb.dao.QuartzSetMapper;
import com.jysoft.cbb.vo.QuartzLogVo;
import com.jysoft.cbb.vo.QuartzSetVo;
import com.jysoft.framework.scheduler.TaskControlScheduler;
import com.jysoft.framework.util.GUID;

/**
 * 
 * 类功能描述: 调度任务管理服务层
 * 创建者： 吴立刚
 * 创建日期： 2014-11-15
 * 
 * 修改内容：增加方法chekResultByTaskId，判断被删除的任务是否含有log日志记录
 * 修改者：黄智强
 * 修改日期： 2015-9-16
 * 
 * 修改内容：根据安全检查结果调整调度任务分页查询
 * 修改者：吴立刚
 * 修改日期： 2015-11-04
 * 
 */
@Service
public class QuartzSetService {
	@Autowired(required = true)
	QuartzSetMapper mapper;
	
	@Autowired(required = true)
	QuartzLogMapper quartzLogMapper;
	
	@Autowired
	private TaskControlScheduler taskScheduler;

	/**
	 * 获取所有调度任务
	 * 
	 * @return 调度任务信息
	 */
	public List<QuartzSetVo> getAllTask() {
		// 任务状态判断：在不在容器里，在的话就是启动，否则就是停用
		List<QuartzSetVo> list = mapper.getAllTask();
		return list;
	}

	/**
	 * 获取所有分页调度任务
	 * 
	 * @return 调度任务信息
	 */
	public Map<String, Object> getAllTaskByPage(QuartzSetVo quartzSetVo) {
		Map<String, Object> taskMap = new HashMap<String, Object>(); // 返回DataGrid数据结构

		// 任务状态判断：在不在容器里，在的话就是启动，否则就是停用
		String total = mapper.getAllTaskTotal(quartzSetVo);
		List<QuartzSetVo> list = mapper.getAllTaskByPage(quartzSetVo);

		taskMap.put("total", total);
		taskMap.put("rows", list);
		return taskMap;
	}

	/**
	 * 根据任务ID获取任务信息
	 * 
	 * @param quartzSetVo
	 *            任务ID信息
	 * @return
	 */
	public QuartzSetVo getTaskById(QuartzSetVo quartzSetVo) {
		return mapper.getTaskById(quartzSetVo);

	}

	/**
	 * 更新任务状态
	 * 
	 * @param quartzSetVo
	 *            任务信息
	 */
	@Transactional
	public void setTaskStatus(QuartzSetVo quartzSetVo) {
		mapper.setTaskStatus(quartzSetVo);
	}

	/**
	 * 新增调度任务信息
	 * 
	 * @param quartzSetVo
	 *            任务信息
	 */
	@Transactional
	public boolean insert(QuartzSetVo quartzSetVo) {
		quartzSetVo.setTaskId(GUID.getRandomId());
		mapper.insert(quartzSetVo);

		// 启动方式“自动”状态，需加入定时容器
		if ("1".equals(quartzSetVo.getStartMode())) {
			// 动态配置任务
			taskScheduler.dynamicAdd(quartzSetVo);
		}
		
		return true;
	}

	/**
	 * 检查任务是否存在日志记录
	 * 
	 * @param quartzSetVo
	 */
	public boolean chekResultByTaskId(QuartzLogVo quartzLogVo) {
		boolean flag = false;
		Integer cResult = Integer.parseInt(quartzLogMapper
				.getLogByTaskIdTotal(quartzLogVo));
		if (cResult > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 删除调度任务信息
	 * 
	 * @param quartzSetVo
	 *            任务信息
	 */
	@Transactional
	public boolean deleteById(QuartzSetVo quartzSetVo) {
		if (quartzSetVo != null && quartzSetVo.getTaskId() != null) {
			mapper.deleteById(quartzSetVo);
			return true;
		}
		return false;
	}

	/**
	 * 更新调度任务信息
	 * 
	 * @param quartzSetVo
	 *            任务信息
	 */
	@Transactional
	public boolean updateById(QuartzSetVo quartzSetVo) {
		if (quartzSetVo != null && quartzSetVo.getTaskId() != null) {
			mapper.updateById(quartzSetVo);
			return true;
		}
		return false;
	}

	/**
	 * 函数方法描述: 更新调度任务执行状态
	 * 
	 * @param quartzSetVo
	 * @return
	 */
	public int updateTaskExeStatus(QuartzSetVo quartzSetVo) {
		return mapper.updateTaskExeStatus(quartzSetVo);
	}
}
