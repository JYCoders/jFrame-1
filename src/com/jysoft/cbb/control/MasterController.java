package com.jysoft.cbb.control;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.jysoft.cbb.service.CommonService;
import com.jysoft.cbb.service.MastService;
import com.jysoft.cbb.vo.ComBoxSelectVo;
import com.jysoft.cbb.vo.MastBaseMessageVo;
import com.jysoft.cbb.vo.MastTypeVo;
import com.jysoft.cbb.vo.MastValVo;
import com.jysoft.cbb.vo.TreeVo;
import com.jysoft.framework.util.BusinessResult;

/**
 * 
 * 类功能描述:码表控制类
 * 创建者：黄智强
 * 创建日期： 2016-1-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Controller
@RequestMapping("/master")
public class MasterController {
	private static String Page_MastMgr = "pages/systemManager/mastIndex";
	protected Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MastService mastService;

	/**
	 * 函数方法描述:获取combox的下拉框内容
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/selCompany", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String selRelationSys(HttpServletRequest request) throws Exception {

		return mastService.getMastToOption("SYS_004", "");

	}

	/**
	 * 函数方法描述:跳转到新的码表管理页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toMastManagePage", method = RequestMethod.GET)
	public String toMastManagePage(HttpServletRequest request) {
		return Page_MastMgr;
	}

	/**
	 * 函数方法描述:获取码表树
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMastTree", method = { RequestMethod.GET,
			RequestMethod.POST })
	public List<TreeVo> getMastTree(MastBaseMessageVo mastBaseMessageVo,
			HttpServletRequest request, HttpServletResponse response) {
		List<MastBaseMessageVo> mastBaseList = mastService.getMastTree();
		TreeVo rootVo = new TreeVo();
		rootVo.setId("node"); 
		rootVo.setText("码表管理");
		try {
			rootVo = CommonService.buildTree(mastBaseList,rootVo,"node","getMaster_code", "getP_id", "getMaster_name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TreeVo> list2 = new ArrayList<TreeVo>();
		list2.add(rootVo);
		return list2;
	}
	/**
	 * 函数方法描述:获取异步加载树的数据
	 * 
	 * @param nodeId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSynocMastTree", method = { RequestMethod.GET,
			RequestMethod.POST })
	public List<Map<String, Object>> getSynocMastTree(String nodeId,
			HttpServletRequest request, HttpServletResponse response){
		String nodeIdResult = "";
		List<Map<String, Object>> itemsList = new ArrayList<Map<String, Object>>(); 
		 if(nodeId!=null&&!"".endsWith(nodeId)){
			 nodeIdResult = nodeId;
		 }else{
			 nodeIdResult = "node";
		 }
		 List<MastBaseMessageVo> synocTreeDataList = mastService.getSynocMastTree(nodeIdResult);
		 for(MastBaseMessageVo mastBaseMessageVoTemp : synocTreeDataList){
			 Map<String, Object> item = new HashMap<String, Object>();
			 item.put("id", mastBaseMessageVoTemp.getMaster_code());
			 item.put("text", mastBaseMessageVoTemp.getMaster_name());
			 item.put("pid", mastBaseMessageVoTemp.getP_id());
			 item.put("iconCls", "icon-coding");
			 itemsList.add(item);
		 }
		 return itemsList;
	}
	/**
	 * 函数方法描述:获取单个码表基本信息
	 * 
	 * @param mastBaseMessageVo
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSignleMastByID", method = { RequestMethod.GET,
			RequestMethod.POST })
	public MastBaseMessageVo getSignleMastByID(
			MastBaseMessageVo mastBaseMessageVo, HttpServletRequest request,
			HttpServletResponse response) {
		MastBaseMessageVo MastBaseAll =null;
		try {
			MastBaseAll = mastService
					.getSignleMastByID(mastBaseMessageVo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return MastBaseAll;
	}

	/**
	 * 根据用户Id 获取用户的表/视图信息
	 * 
	 * @param menuModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMastInfoList", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object> getMastInfoList(
			@ModelAttribute MastTypeVo mastTypeVo, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (mastTypeVo.getMasterName() != null
				&& !"".equals(mastTypeVo.getMasterName())) {
			try {
				mastTypeVo.setMasterName(URLDecoder.decode(
						mastTypeVo.getMasterName(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (mastTypeVo.getMasterCode() != null
				&& !"".equals(mastTypeVo.getMasterCode())) {
			try {
				mastTypeVo.setMasterCode(URLDecoder.decode(
						mastTypeVo.getMasterCode(), "utf-8").toUpperCase());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		try {
			map = mastService.getMastInfoList(mastTypeVo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return map;

	}

	/**
	 * 函数方法描述:增加码表值
	 * 
	 * @param mastTypeVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addMastTypeInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public BusinessResult addMastTypeInfo(
			@ModelAttribute MastTypeVo mastTypeVo, HttpServletRequest request,
			HttpServletResponse response) {
		mastTypeVo.setMasterCode(mastTypeVo.getMasterCode().substring(1,
				mastTypeVo.getMasterCode().length()));
		if ("Y".equals(mastTypeVo.getUseFlag())) {
			mastTypeVo.setUseFlag("1");
		} else if ("N".equals(mastTypeVo.getUseFlag())) {
			mastTypeVo.setUseFlag("0");
		}
		String msg = "";
		boolean success = false;
		if ("0".equals(mastTypeVo.getPid())) {
			mastTypeVo.setPid("");
		}
		try {
			// 主键存在,执行更新
			if (mastTypeVo.getId() != null && !"".equals(mastTypeVo.getId())) {
				success = mastService.updateMastTypeInfo(mastTypeVo);
				msg = success ? "更新成功" : "更新失败";
			}
			// 主键不存在,执行新增
			else {
				success = mastService.initCount(mastTypeVo); // 判断代码编号是否已存在，存在则返回提示，不存在则执行新增操作
				msg = success ? "该代码编码已经存在,不能再次新增" : (mastService
						.addMastTypeInfo(mastTypeVo) ? "保存成功" : "保存失败");
			}
			if ("保存成功".equals(msg)) {
				success = true;
			}
		} catch (Exception e) {
			success = false;
			if (msg.contains("更新")) {
				msg = "更新失败,错误原因：" + e.getMessage();
			} else {
				msg = "保存失败,错误原因：" + e.getMessage();
			}
		}
		return new BusinessResult(success, (Object) msg);
	}

	/**
	 * 函数方法描述:根据主键获取码表类型的信息
	 * 
	 * @param mastTypeVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMastInfoById", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public MastTypeVo getMastInfoById(@ModelAttribute MastTypeVo mastTypeVo,
			HttpServletRequest request, HttpServletResponse response) {
		MastTypeVo mastTypeVoBack = new MastTypeVo();
		try {
			mastTypeVo.setId(URLDecoder.decode(mastTypeVo.getId(), "utf-8"));
			mastTypeVoBack = mastService.getMastInfoById(mastTypeVo);
			if ("1".equals(mastTypeVoBack.getUseFlag())) {
				mastTypeVoBack.setUseFlag("Y");
			} else if ("0".equals(mastTypeVoBack.getUseFlag())) {
				mastTypeVoBack.setUseFlag("N");
			}
		} catch (Exception e) {
			e.getMessage();
		}

		return mastTypeVoBack;
	}

	/**
	 * 函数方法描述:根据主键获取码表值List
	 * 
	 * @param mastTypeVo
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMastValInfoList", method = {
			RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getMastValInfoList(
			@ModelAttribute MastValVo mastTypeVo, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (mastTypeVo.getTypeId() != null) {
			try {
				mastTypeVo.setTypeId(URLDecoder.decode(mastTypeVo.getTypeId(),
						"utf-8"));
			} catch (UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
			}

		}
		map = mastService.getMastInfoListById(mastTypeVo);
		return map;
	}

	/**
	 * 函数方法描述:根据主键获取码表值的信息
	 * 
	 * @param mastValVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMastValInfoById", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public MastValVo getMastValInfoById(@ModelAttribute MastValVo mastValVo,
			HttpServletRequest request, HttpServletResponse response) {
		MastValVo mast = new MastValVo();
		try {
			mast = mastService.getMastValInfoById(mastValVo);
			if ("1".equals(mast.getUseFlag())) {
				mast.setUseFlag("Y");
			} else if ("0".equals(mast.getMasterCode())) {
				mast.setUseFlag("N");
			}
		} catch (Exception e) {
			e.getMessage();
		}

		return mast;
	}

	/**
	 * 函数方法描述:更新/添加 码表值信息
	 * 
	 * @param mastValVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateMastValInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public BusinessResult updateMastValInfo(
			@ModelAttribute MastValVo mastValVo, HttpServletRequest request,
			HttpServletResponse response) {
		if ("Y".equals(mastValVo.getUseFlag())) {
			mastValVo.setUseFlag("1");
		} else if ("N".equals(mastValVo.getUseFlag())) {
			mastValVo.setUseFlag("0");
		}
		String typeId = mastService.getIdByMasterCode(mastValVo.getMasterCode());
		mastValVo.setTypeId(typeId);
		String msg = "";
		boolean success = false;
		try {
			mastValVo.setTypeId(URLDecoder.decode(mastValVo.getTypeId(),
					"utf-8"));
			if (mastValVo.getId() != null && !"".equals(mastValVo.getId())) {
				// 主键存在,执行更新
				success = mastService.updateMastValInfo(mastValVo);
				msg = success ? "更新成功" : "更新失败";
			} else {
				// 主键不存在,执行新增
				success = mastService.valCodeCount(mastValVo); // 判断是否重复
				msg = success ? "该值编码已经存在,不能再次新增" : (mastService
						.addMastValInfo(mastValVo) ? "新增成功" : "新增失败");
				if ("新增成功".equals(msg)) {
					success = true;
				}
			}
		} catch (Exception e) {
			success = false;
			msg = "操作失败,错误原因：" + e.getMessage();
		}
		return new BusinessResult(success, (Object) msg);
	}

	/**
	 * 函数方法描述:删除码表
	 * 
	 * @param mastValVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteMastType", method = { RequestMethod.POST,
			RequestMethod.GET })
	public BusinessResult deleteMastType(MastValVo mastValVo,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resMsg = "";
		boolean flag = false;
		try {
			if (mastValVo != null) {
				mastValVo.setId(URLDecoder.decode(mastValVo.getId(), "utf-8"));

			}
			flag = mastService.deleteMastType(mastValVo);
			if (flag)
				resMsg = "删除成功！";
			else
				resMsg = "删除失败";
		} catch (Exception e) {
			e.printStackTrace();
			resMsg = "删除失败：" + e.getMessage();
		}
		return new BusinessResult(flag, (Object) resMsg);
	}

	/**
	 * 函数方法描述:删除码表值
	 * 
	 * @param mastValVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteMastVal", method = { RequestMethod.POST,
			RequestMethod.GET })
	public BusinessResult deleteMastVal(MastValVo mastValVo,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resMsg = "";
		boolean flag = false;
		try {
			flag = mastService.deleteMastVal(mastValVo);
			if (flag)
				resMsg = "删除成功！";
			else
				resMsg = "删除失败";
		} catch (Exception e) {
			e.printStackTrace();
			resMsg = "删除失败：" + e.getMessage();
		}
		return new BusinessResult(flag, (Object) resMsg);
	}

	/**
	 * 函数方法描述:获取combox的下拉菜单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getParentComList", method = { RequestMethod.GET,
			RequestMethod.POST })
	public List<ComBoxSelectVo> getParentComList(HttpServletRequest request,
			HttpServletResponse response) {
		List<ComBoxSelectVo> list = mastService.getParentComList();
		return list;
	}
}