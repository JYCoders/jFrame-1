package com.jysoft.cbb.control;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jysoft.cbb.service.MastService;
import com.jysoft.cbb.service.QuartzSetService;
import com.jysoft.cbb.vo.MessageVo;
import com.jysoft.cbb.vo.QuartzLogVo;
import com.jysoft.cbb.vo.QuartzSetVo;
import com.jysoft.framework.scheduler.TaskControlScheduler;
import com.jysoft.framework.util.BusinessResult;

/**
 * 
 * 类功能描述: 调度任务管理控制层
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
@Controller
@RequestMapping("/task")
public class QuartzController {
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private QuartzSetService service;

	@Autowired
	private MastService masterSer;

	@Autowired
	private TaskControlScheduler taskScheduler;

	/**
	 * 初始化页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toTaskManager", method = RequestMethod.GET)
	public ModelAndView toTaskManager(Model model, HttpServletRequest request)
			throws Exception {
		try {
			String option_hh = masterSer.getOptionNum(23, "");
			String option_mm = masterSer.getOptionNum(59, "");
			String className = masterSer.getClassNameByPackage(
					"com.jysoft.exam.scheduler", "");
			ModelAndView mav = new ModelAndView(
					"pages/systemManager/quartzSetManager");
			mav.addObject("option_hh", option_hh);
			mav.addObject("option_mm", option_mm);
			mav.addObject("option_ss", option_mm);
			mav.addObject("className", className);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初始化任务
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/init", method = { RequestMethod.GET,
			RequestMethod.POST })
	public MessageVo init(HttpServletRequest request) {

		// 获取任务信息数据
		List<QuartzSetVo> jobList = service.getAllTask();

		// 初始化任务
		MessageVo msgVo = taskScheduler.init(jobList);
		return msgVo;
	}

	/**
	 * 动态添加任务
	 * 
	 * @param job
	 *            任务对象
	 * @param request
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value = "/dynamicAdd", method = { RequestMethod.GET,
			RequestMethod.POST })
	public MessageVo dynamicAdd(@ModelAttribute QuartzSetVo job,
			HttpServletRequest request) {
		// 根据任务ID获取任务信息
		job = service.getTaskById(job);

		// 动态配置任务
		MessageVo msgVo = taskScheduler.dynamicAdd(job);
		return msgVo;
	}

	/**
	 * 暂停执行任务
	 * 
	 * @param job
	 *            任务对象
	 * @param request
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value = "/pauseJob", method = { RequestMethod.GET,
			RequestMethod.POST })
	public MessageVo pauseJob(@ModelAttribute QuartzSetVo job,
			HttpServletRequest request) {
		// 根据任务ID获取任务信息
		job = service.getTaskById(job);

		// 暂停调度任务
		MessageVo msgVo = taskScheduler.pauseJob(job);
		return msgVo;
	}

	/**
	 * 恢复调度任务
	 * 
	 * @param job
	 *            任务对象
	 * @param request
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value = "/resumeJob", method = { RequestMethod.GET,
			RequestMethod.POST })
	public MessageVo resumeJob(@ModelAttribute QuartzSetVo job,
			HttpServletRequest request) {
		// 根据任务ID获取任务信息
		job = service.getTaskById(job);

		// 恢复调度任务
		MessageVo msgVo = taskScheduler.resumeJob(job);
		return msgVo;
	}

	/**
	 * 删除调度任务
	 * 
	 * @param job
	 *            任务对象
	 * @param request
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteJob", method = { RequestMethod.GET,
			RequestMethod.POST })
	public MessageVo deleteJob(@ModelAttribute QuartzSetVo job,
			HttpServletRequest request) {
		// 根据任务ID获取任务信息
		job = service.getTaskById(job);

		// 删除调度任务
		MessageVo msgVo = taskScheduler.deleteJob(job);
		return msgVo;
	}

	/**
	 * 检查任务是否有log日志
	 * 
	 * @param job
	 *            任务对象
	 * @param request
	 * @return 检查结果
	 */
	@ResponseBody
	@RequestMapping(value = "/checkTaskHaveDetail", method = {
			RequestMethod.GET, RequestMethod.POST })
	public MessageVo checkTaskHaveDetail(@ModelAttribute QuartzLogVo job,
			HttpServletRequest request) {
		MessageVo msgVo = new MessageVo();
		boolean flag = service.chekResultByTaskId(job);
		if (flag) {
			msgVo.setResultCode("1");
		} else {
			msgVo.setResultCode("0");
		}
		return msgVo;
	}

	/**
	 * 立即执行调度任务
	 * 
	 * @param job
	 *            任务对象
	 * @param request
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value = "/triggerJob", method = { RequestMethod.GET,
			RequestMethod.POST })
	public MessageVo triggerJob(@ModelAttribute QuartzSetVo job,
			HttpServletRequest request) {
		// 根据任务ID获取任务信息
		job = service.getTaskById(job);

		// 立即执行调度任务
		MessageVo msgVo = taskScheduler.triggerJob(job);
		return msgVo;
	}

	/**
	 * 获取调度任务列表
	 * 
	 * @param job
	 *            任务对象
	 * @param request
	 * @return 任务列表
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllTask", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object> getAllTask(
			@ModelAttribute QuartzSetVo quartzSetVo,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> taskMap = new HashMap<String, Object>(); // 返回DataGrid数据结构
		// 获取所有调度任务
		try {
			if (quartzSetVo.getTaskName() != null
					&& quartzSetVo.getTaskName().length() > 0) {
				quartzSetVo.setTaskName(URLDecoder.decode(quartzSetVo
						.getTaskName(), "utf-8"));// 汉字转码
			}
			taskMap = service.getAllTaskByPage(quartzSetVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskMap;
	}

	/**
	 * 新增调度任务信息
	 * 
	 * @param jobQuartzSet
	 *            任务对象
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/saveQuartzSetLog", method = { RequestMethod.GET,
			RequestMethod.POST })
	public BusinessResult insertQuartzSet(
			@ModelAttribute QuartzSetVo quartzSetVo, HttpServletRequest request) {
		boolean success = false;
		String msg = "";
		try {
			success = service.insert(quartzSetVo);
			msg = success ? "保存成功" : "保存失败";
		} catch (Exception e) {
			success = false;
			msg = "保存失败,错误原因：" + e.getMessage();
		}
		return new BusinessResult(success, (Object) msg);
	}

	/**
	 * 删除调度任务信息
	 * 
	 * @param job
	 *            任务对象
	 * @param request
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteById", method = { RequestMethod.GET,
			RequestMethod.POST })
	public BusinessResult delRoleModel(Model model,
			@ModelAttribute QuartzSetVo jobQuartzSet, HttpServletRequest request) {
		Boolean success = false;
		if (jobQuartzSet != null) {
			success = service.deleteById(jobQuartzSet);
		}
		String msg = success ? "删除成功！" : "删除失败！";
		return new BusinessResult(success, (Object) msg);
	}

	/**
	 * 根据ID查找调度任务信息
	 * 
	 * @param quartzSet
	 *            任务对象
	 * @param request
	 * @return 执行结果
	 */
	@RequestMapping(value = "/queryById", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	QuartzSetVo getTaskId(@ModelAttribute QuartzSetVo quartzSet,
			HttpServletRequest request) {
		if (quartzSet != null && quartzSet.getTaskId() != null) {
			QuartzSetVo dto = service.getTaskById(quartzSet);
			return dto;
		}
		return null;
	}

	/**
	 * 根据ID更新调度任务信息
	 * 
	 * @param quartzSet
	 *            任务对象
	 * @param request
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value = "/updateQuartzInfo", method = {
			RequestMethod.GET, RequestMethod.POST })
	public BusinessResult updateQuartzInfo(@ModelAttribute QuartzSetVo quartzSet,
			HttpServletRequest request) {
		Boolean success = false;
		if (quartzSet != null) {
			success = service.updateById(quartzSet);
		}
		String msg = success ? "保存成功！" : "保存失败！";
		return new BusinessResult(success, (Object) msg);
	}
}
