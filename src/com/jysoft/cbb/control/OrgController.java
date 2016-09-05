package com.jysoft.cbb.control;

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
import com.jysoft.cbb.service.OrgService;
import com.jysoft.cbb.vo.EmpInfoVo;
import com.jysoft.cbb.vo.OrgTreeVo;
import com.jysoft.cbb.vo.OrgVo;
import com.jysoft.cbb.vo.TreeVo;
import com.jysoft.framework.util.BusinessResult;

/**
 * 
 * 类功能描述:组织机构管理
 * 创建者：黄智强
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Controller
@RequestMapping("/org")
public class OrgController {
	private static String Page_OrgMgr = "pages/systemManager/orgManagePage";
	protected Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private OrgService service;

	/**
	 * 函数方法描述:跳转到组织机构管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toOrgManagePage", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String toOrgManagePage() {
		return Page_OrgMgr;
	}

	/**
	 * 函数方法描述:获取组织机构树
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/OrgTree", method = { RequestMethod.GET,
			RequestMethod.POST })
	public List<TreeVo> OrgTree(HttpServletRequest request, HttpServletResponse response) {
		String flag= request.getParameter("flag");
		List<OrgTreeVo> orgTreeVolist = null;
		try {
			orgTreeVolist = service.getAllOrgInfo();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		List<OrgTreeVo> orgTreeVolist2 = new ArrayList<OrgTreeVo>();
		if("accout".equals(flag)){
			for(OrgTreeVo vo:orgTreeVolist){
				if("0".equals(vo.getOrg_id())||"0".equals(vo.getP_id())){
					orgTreeVolist2.add(vo);
				}
			}
		}
		TreeVo rootVo = new TreeVo();
		rootVo.setId("jx01");
		rootVo.setText("国网湖南省电力公司");
		try {
			if("accout".equals(flag)){
			rootVo = CommonService.buildTree(orgTreeVolist2,rootVo,"jx01","getOrg_id","getP_id","getOrg_name");
			}else{
			rootVo = CommonService.buildTree(orgTreeVolist,rootVo,"jx01","getOrg_id","getP_id","getOrg_name");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<TreeVo> list = new ArrayList<TreeVo>();
		list.add(rootVo);
		return list;
	}

	/**
	 * 函数方法描述:依据ID获取组织节点信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrgInfoByOrgId")
	public OrgVo getNodeMessageByID(OrgVo orgVo, HttpServletRequest request,
			HttpServletResponse response) {
		OrgVo originalVo = null;
		originalVo = service.getOrgInfoByOrgId(orgVo);
		return originalVo;
	}

	/**
	 * 函数方法描述:通过org查询组织机构相关信息
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findOrgInfoByOrgId", method = {
			RequestMethod.GET, RequestMethod.POST })
	public OrgVo findOrgInfoByOrgId(@ModelAttribute OrgVo vo,
			HttpServletRequest request) {
		OrgVo org = service.getOrgInfoByOrgId(vo);
		return org;
	}

	/**
	 * 通过员工Id查询员工相关信息 函数方法描述:
	 * 
	 * @param eiv
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findEmpInfoByEmpId", method = {
			RequestMethod.GET, RequestMethod.POST })
	public EmpInfoVo findEmpInfoByEmpId(@ModelAttribute EmpInfoVo eiv,
			HttpServletRequest request) {
		EmpInfoVo empInfo = service.findEmpInfoByEmpId(eiv);
		if (empInfo.getMob_tel() == null || "".equals(empInfo.getMob_tel())) {
			empInfo.setMob_tel("");
		}
		if (empInfo.getFix_tel() == null || "".equals(empInfo.getFix_tel())) {
			empInfo.setFix_tel("");
		}
		if (empInfo.getEmail() == null || "".equals(empInfo.getEmail())) {
			empInfo.setEmail("");
		}
		return empInfo;
	}

	/**
	 * 函数方法描述:插入员工信息
	 * 
	 * @param eiv
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/insertEmpInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public BusinessResult insertEmpInfo(@ModelAttribute EmpInfoVo eiv,
			HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
		boolean success = false;
		try {
			if (eiv != null) {
				// System.out.println(eiv.getEmail()+eiv.getEmp_code()+eiv.getEmp_name());
				success = service.addNewEmpInfo(eiv);
			} else
				success = false;
			msg = success ? "插入成功！" : "插入失败！";
		} catch (Exception e) {
			success = false;
			msg = "插入失败,错误原因：" + e.getMessage();
		}
		return new BusinessResult(success, (Object) msg);
	}

	/**
	 * 函数方法描述:更新员工信息
	 * 
	 * @param eiv
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateEmpInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public BusinessResult updateEmpInfo(@ModelAttribute EmpInfoVo eiv,
			HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
		boolean success = false;
		try {
			if (eiv != null) {
				success = service.updateEmpInfo(eiv);
			} else
				success = false;
			msg = success ? "更新成功 ！" : "更新失败！";
		} catch (Exception e) {
			success = false;
			msg = "更新失败,错误原因：" + e.getMessage();
		}
		return new BusinessResult(success, (Object) msg);
	}

	/**
	 * 函数方法描述:删除员工信息
	 * 
	 * @param eiv
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteEmpInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public BusinessResult deleteEmpInfo(@ModelAttribute EmpInfoVo eiv,
			HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
		boolean success = false;
		try {
			List<EmpInfoVo> list = service.findRelationWithUser(eiv);
			if (eiv != null && list != null && list.size() == 0) {
				success = service.deleteEmpInfo(eiv.getEmp_id().toString());
			} else
				success = false;
			msg = success ? "删除成功" : list.size() == 0 ? "删除失败"
					: "该员工下有用户信息请先删除所有用户信息";
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			msg = "删除失败,错误原因：" + e.getMessage();
		}
		return new BusinessResult(success, (Object) msg);
	}

	/**
	 * 函数方法描述:插入组织机构信息
	 * 
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/insertOrgInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public BusinessResult insertOrgInfo(@ModelAttribute OrgVo vo,
			HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
		boolean success = false;
		try {
			if (vo != null) {
				success = service.addNewOrgInfo(vo);
			} else
				success = false;
			msg = success ? "保存成功 ！" : "插入失败 ！";
		} catch (Exception e) {
			success = false;
			msg = "插入失败,错误原因：" + e.getMessage();
		}
		return new BusinessResult(success, (Object) msg);
	}

	/**
	 * 函数方法描述:更新组织机构信息
	 * 
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateOrgInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public BusinessResult updateOrgInfo(@ModelAttribute OrgVo vo,
			HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
		boolean success = false;
		try {
			if (vo != null) {
				success = service.updateOrgInfo(vo);
			} else
				success = false;
			msg = success ? "更新成功！" : "更新失败 ！";
		} catch (Exception e) {
			success = false;
			msg = "更新失败,错误原因：" + e.getMessage();
		}
		return new BusinessResult(success, (Object) msg);
	}

	/**
	 * 函数方法描述:删除组织机构信息
	 * 
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteOrgInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public BusinessResult deleteOrgInfo(@ModelAttribute OrgVo vo,
			HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
		boolean success = false;
		try {
			if (vo != null) {
				success = service.deleteOrgInfo(vo);
			} else
				success = false;
			msg = success ? "删除成功" : "删除失败";
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			msg = "删除失败,错误原因：" + e.getMessage();
		}
		return new BusinessResult(success, (Object) msg);
	}

	/**
	 * 函数方法描述:获取组织机构信息（生成下来菜单时使用）
	 * 
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getOrgCodeList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<OrgVo> getOrgCodeList(@ModelAttribute OrgVo vo,
			HttpServletRequest request, HttpServletResponse response) {
		List<OrgVo> list = this.service.getOrgCodeList();
		return list;
	}

	/**
	 * 函数方法描述:获得用户基本信息（生成下拉菜单时使用）
	 * 
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getEmpInfoForCodeList", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<EmpInfoVo> getEmpInfoForCodeList(@ModelAttribute OrgVo vo,
			HttpServletRequest request, HttpServletResponse response) {
		List<EmpInfoVo> list = this.service.getEmpInfoForCodeList();
		return list;
	}

	/**
	 * 函数方法描述:获取组织基本信息
	 * 
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getOrgInfoByEmpId", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public EmpInfoVo getOrgInfoByEmpId(@ModelAttribute EmpInfoVo vo,
			HttpServletRequest request, HttpServletResponse response) {
		EmpInfoVo list = this.service.getOrgInfoByEmpId(vo);
		return list;
	}

	/**
	 * 函数方法描述:获得业务系统编辑中需要的所属业务部门信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBizOrgInfos", method = { RequestMethod.GET,
			RequestMethod.POST })
	public List<OrgVo> getBizOrgInfos() {
		List<OrgVo> list = null;
		try {
			list = service.getBizOrgInfos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 函数方法描述:检查组织便面是否重复
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkOrgCodeUnique", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public BusinessResult checkOrgCodeUnique(@ModelAttribute OrgVo vo,
			HttpServletRequest request) {
		List<OrgVo> list = null;
		boolean success = false;
		Map<String, String> resultValue = new HashMap<String, String>();
		if (vo.getOrg_code() == null || "".equals(vo.getOrg_code())) {
			resultValue.put("msg", "无效的组织结构编号");
		} else {
			try {
				list = service.checkOrgCodeUnique(vo);
				if (list.size() > 0)
					resultValue.put("countNo", String.valueOf(list.size()));
				success = true;
			} catch (Exception e) {
				resultValue.put("msg", "组织机构编号唯一性校验失败！原因" + e.getMessage());
			}
		}
		return new BusinessResult(success, (Object) resultValue);
	}
}
