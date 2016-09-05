package com.jysoft.cbb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jysoft.cbb.dao.MastValMapper;
import com.jysoft.cbb.vo.ComBoxSelectVo;
import com.jysoft.cbb.vo.MastBaseMessageVo;
import com.jysoft.cbb.vo.MastTypeVo;
import com.jysoft.cbb.vo.MastValVo;
import com.jysoft.framework.util.Constant;
import com.jysoft.framework.util.JsonUtil;

/**
 * 
 * 类功能描述:负责处理码表的读写
 * 创建者： 黄智强
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Component
public class MastService {
	@Autowired
	private MastValMapper mastValMapper;

	/**
	 * 函数方法描述:获取MastValVo属性字符串集合
	 * 
	 * @return
	 */
	public List<String> getMastValVoFieldList() {
		List<String> list = new ArrayList<String>();
		list.add("ID");
		list.add("VAL_CODE");
		list.add("VAL_NAME");
		list.add("MASTER_NAME");
		list.add("REMARK");
		return list;
	}

	/**
	 * 函数方法描述:根据codeType查询出MastValVo 集合
	 * 
	 * @param codeType
	 * @return
	 */
	public List<MastValVo> getMasterList(String codeType) {
		return mastValMapper.getMastValByMasterCode(codeType);
	}

	/**
	 * 函数方法描述:根据codeType查询出MastValVo,组装成select控件的<option>选项
	 * 
	 * @param codeType
	 * @param selectedValue
	 * @return
	 */
	public String getMastToOption(String codeType, String selectedValue) {
		List<MastValVo> list = getMasterList(codeType);
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (MastValVo vo : list) {
				if (vo.getValCode().equals(selectedValue)) {
					sb.append("<option value=\"" + vo.getValCode()
							+ "\" selected = \"selected\" >" + vo.getValName()
							+ "</option>");
				} else {
					sb.append("<option value=\"" + vo.getValCode() + "\" >"
							+ vo.getValName() + "</option>");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 函数方法描述:
	 * 
	 * @param max
	 * @param selValue
	 * @return
	 */
	public String getOptionNum(int max, String selValue) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= max; i++) {
			String t = String.valueOf(i);
			// if(t.length() == 1){
			// t = "0" + t;
			// }
			sb.append("<option value='");
			sb.append(t);
			if (String.valueOf(i).equals(selValue)) {
				sb.append("' selected = 'selected'>");
			} else {
				sb.append("'>");
			}
			sb.append(t);
			sb.append("</option>");
		}
		return sb.toString();
	}

	/**
	 * 函数方法描述: 根据类型、名称获取下拉框值
	 * 
	 * @param codeType
	 * @param name
	 * @return
	 */
	public String getValueByName(String codeType, String name) {
		List<MastValVo> list = mastValMapper.getMastValByMasterCode(codeType);
		if (list != null && list.size() > 0) {
			for (MastValVo vo : list) {
				if (vo.getValName().toUpperCase().equals(name.toUpperCase())) {
					return vo.getValCode();
				}
			}
		}
		return null;
	}

	/**
	 * 函数方法描述:获取所有的系统码表
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public Map<String, Object> getMastInfoList(MastTypeVo mastTypeVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MastTypeVo> list = mastValMapper.getMastInfoList(mastTypeVo);
		MastTypeVo mast = new MastTypeVo();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				mast = list.get(i);
				if (mast.getUseFlag().equals("1")) {
					mast.setUseFlag("使用");
				} else
					mast.setUseFlag("不使用");
			}
		}
		String total = mastValMapper.getMastInfoListTotal(mastTypeVo);
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 函数方法描述: 获取所有的系统码表
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public Boolean addMastTypeInfo(MastTypeVo mastTypeVo) {
		boolean flag = false;
		int num = mastValMapper.addMastTypeInfo(mastTypeVo);
		if (num == 1)
			flag = true;
		return flag;
	}

	/**
	 * 函数方法描述: 更新系统码表基本信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public Boolean updateMastTypeInfo(MastTypeVo mastTypeVo) {
		boolean flag = false;
		int num = mastValMapper.updateMastTypeInfo(mastTypeVo);
		if (num == 1)
			flag = true;
		return flag;
	}

	/**
	 * 函数方法描述: 根据主键获取码表类型的信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public MastTypeVo getMastInfoById(MastTypeVo mastTypeVo) {

		return mastValMapper.getMastInfoById(mastTypeVo);
	}

	/**
	 * 函数方法描述:根据主键获取码表值的信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public Map<String, Object> getMastInfoListById(MastValVo mastValeVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		MastValVo mast = new MastValVo();
		List<MastValVo> list = mastValMapper.getMastInfoListById(mastValeVo);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				mast = list.get(i);
				if (mast.getUseFlag().equals("1")) {
					mast.setUseFlag("使用");
				} else
					mast.setUseFlag("不使用");
			}
		}
		String total = mastValMapper.getMastInfoListByIdTotal(mastValeVo);
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 函数方法描述: 根据主键获取码表类型的信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public MastValVo getMastValInfoById(MastValVo mastValVo) {

		return mastValMapper.getMastValInfoById(mastValVo);
	}

	/**
	 * 函数方法描述:添加系统码表基本信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public Boolean addMastValInfo(MastValVo mastValVo) {
		boolean flag = false;
		int num = mastValMapper.addMastValInfo(mastValVo);
		if (num == 1)
			flag = true;
		return flag;
	}

	/**
	 * 函数方法描述:更新系统码表z值基本信息
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public Boolean updateMastValInfo(MastValVo mastValVo) {
		boolean flag = false;
		int num = mastValMapper.updateMastValInfo(mastValVo);
		if (num == 1)
			flag = true;
		return flag;
	}

	/**
	 * 函数方法描述:验证mastCode 是否存在
	 * 
	 * @param mastTypeVo
	 * @return
	 */
	public boolean initCount(MastTypeVo mastTypeVo) {
		boolean flag = false;
		int num = mastValMapper.initCount(mastTypeVo);
		if (num != 0)
			flag = true;
		return flag;
	}

	/**
	 * 函数方法描述：验证值编码是否已经存在
	 * 
	 * @param mastValVo
	 * @return
	 */
	public boolean valCodeCount(MastValVo mastValVo) {
		boolean flag = false;
		int num = mastValMapper.valCodeCount(mastValVo);
		if (num != 0)
			flag = true;
		return flag;
	}

	/**
	 * 函数方法描述:删除码表
	 * 
	 * @param mastValVo
	 * @return
	 */
	public boolean deleteMastType(MastValVo mastValVo) {
		boolean flag = false;
		mastValMapper.deleteVal(mastValVo);
		int num = mastValMapper.deleteMastType(mastValVo);
		if (num != 0)
			flag = true;
		return flag;
	}

	/**
	 * 函数方法描述:删除码表值
	 * 
	 * @param mastValVo
	 * @return
	 */
	public boolean deleteMastVal(MastValVo mastValVo) {
		boolean flag = false;
		int num = mastValMapper.deleteMastVal(mastValVo);
		if (num != 0)
			flag = true;
		return flag;
	}

	/**
	 * 函数方法描述:获取码表列表信息并拼装成树
	 * 
	 * @param mastBaseMessageVo
	 * @return
	 */
	public List<MastBaseMessageVo> getMastTree() {
		// 获取码表汇总信息
		List<MastBaseMessageVo> mastBaseList = mastValMapper.getMastTree();
		return mastBaseList;
	}

	/**
	 * 函数方法描述:查询单个码表值
	 * 
	 * @param mastBaseMessageVo
	 * @return
	 */
	public MastBaseMessageVo getSignleMastByID(
			MastBaseMessageVo mastBaseMessageVo) {
		MastBaseMessageVo tree = mastValMapper
				.getSignleMastByID(mastBaseMessageVo);
		return tree;
	}

	/**
	 * 函数方法描述:获取上级代码下拉框代码
	 * 
	 * @param comBoxVo
	 * @return
	 */
	public List<ComBoxSelectVo> getParentComList() {
		List<ComBoxSelectVo> list = null;
		try {
			list = mastValMapper.getParentComList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	/**
	 * 函数方法描述: 获取调度任务启动类下拉框
	 * 
	 * @param packageName 启动类所在包
	 * @param selValue 
	 * @return
	 */
	public String getClassNameByPackage(String packageName, String selValue) {
		String strClass = Constant.SCHEDULER_CLASS;
		String arr[] = strClass.split("#");
		List<Object> optionsList = new ArrayList<Object>();
		Map<String, String> optionsMap = new HashMap<String, String>();
		optionsMap.put("id", "");
		optionsMap.put("text", "--请选择启动类--");
		optionsList.add(optionsMap);
		for (int i = 0; i < arr.length; i++) {
			String option = packageName + "." + (String) arr[i];
			optionsMap = new HashMap<String, String>();
			optionsMap.put("id", option);
			optionsMap.put("text", option);
			optionsList.add(optionsMap);
		}

		return JsonUtil.parseListToJson(optionsList);
	}
	/**
	 * 函数方法描述:根据码表编码获取对应id
	 * 
	 * @param mastCode
	 * @return
	 */
	public String getIdByMasterCode(String mastCode){
		return mastValMapper.getIdByMasterCode(mastCode);
	}
	/**
	 * 函数方法描述:获取异步加载树的数据
	 * 
	 * @param nodeId
	 * @return
	 */
	public List<MastBaseMessageVo> getSynocMastTree(String nodeId){
		return mastValMapper.getSynocMastTree(nodeId);
	}
}
