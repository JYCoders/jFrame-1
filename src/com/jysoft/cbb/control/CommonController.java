package com.jysoft.cbb.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jysoft.cbb.service.CommonService;
import com.jysoft.cbb.vo.ComBoxSelectVo;

/**
 * 
 * 类功能描述: 公共控制层
 * 创建者： 吴立刚
 * 创建日期： 2016-1-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Controller
@RequestMapping("/comm")
public class CommonController {
	@Autowired
	private CommonService commonService; // 自动加载公共服务层

	/**
	 * 函数方法描述: 根据类型查询下拉框
	 * 
	 * @param comBoxSelectVo
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMiddleWareComList", method = {
			RequestMethod.GET, RequestMethod.POST })
	public List<ComBoxSelectVo> getMiddleWareComList(
			@ModelAttribute ComBoxSelectVo comBoxSelectVo,
			HttpServletRequest request, HttpServletResponse response) {
		List<ComBoxSelectVo> list = new ArrayList<ComBoxSelectVo>();
		list = commonService.getMiddleWareComList(comBoxSelectVo);
		return list;
	}
}
