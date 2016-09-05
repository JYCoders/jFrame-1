package com.jysoft.cbb.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jysoft.cbb.service.QuartzLogDetailService;
import com.jysoft.cbb.service.QuartzLogService;
import com.jysoft.cbb.vo.QuartzLogDetailVo;
import com.jysoft.cbb.vo.QuartzLogVo;

/**
 * 类名称：日志信息查询控制
 * 类描述：负责处理日志信息查询。
 * 创建时间：2014年11月26日
 * 作者： 胡远成
 * 版本： 1.0
 * 修改人：
 * 修改原因：
 * 修改时间：
 */

@Controller
@RequestMapping("/logMessage")
public class QuartzLogController {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private QuartzLogService quartzLogservice;
	
	@Autowired
	private QuartzLogDetailService quartzLogDetailService;

	
	/**
	 * 获取调度日志列表
	 * 
	 * @param quartzLog
	 *            日志对象
	 * @param request
	 * @return 日志列表
	 */
	@ResponseBody
	@RequestMapping(value = "/queryQuartzLog", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object> searchTask(
			@ModelAttribute QuartzLogVo quartzLog, HttpServletRequest request,
			HttpServletResponse response) {
		// 根据条件获取日志信息
		Map<String, Object> map = new HashMap<String, Object>();
		if (quartzLog != null) {
			quartzLog.setTaskId(quartzLog.getTaskId().trim());
			map = quartzLogservice.getLogByTaskId(quartzLog);
		}
		return map;
	}
	
	/**
	 * 获取调度详细日志列表
	 * 
	 * @param quartzLog
	 *            日志对象
	 * @param request
	 * @return 日志列表
	 */
	@ResponseBody
	@RequestMapping(value = "/queryQuartzDetailLog", method = {
			RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> searchTask(
			@ModelAttribute QuartzLogDetailVo quartzLogDetail,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (quartzLogDetail != null) {
			quartzLogDetail.setTaskInstid(quartzLogDetail.getTaskInstid()
					.trim());
			map = quartzLogDetailService.getTaskLogById(quartzLogDetail);
		}
		return map;
	}
	
}
