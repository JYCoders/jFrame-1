package com.jysoft.cbb.vo;


/**
 * 
 * 类功能描述: 页面返回消息
 * 创建者： 吴立刚
 * 创建日期： 2016-1-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class MessageVo {
	private String resultCode; // 返回结果编码
	private String resultMsg; // 返回结果信息
	
	/**
	 * 获取resultCode
	 * @return resultCode resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}
	/**
	 * 设置resultCode
	 * @param resultCode resultCode
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	/**
	 * 获取resultMsg
	 * @return resultMsg resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}
	/**
	 * 设置resultMsg
	 * @param resultMsg resultMsg
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
