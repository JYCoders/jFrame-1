package com.jysoft.framework.util;

public enum CharsetEnum {
	UTF8_US("AMERICAN_AMERICA.AL32UTF8",3),UTF8_CN("SIMPLIFIED CHINESE_CHINA.AL32UTF8",3),UTF16("UTF16",3),GB2312("GB2312",1),GBK("GBK",1);
	
	private String name;
	private int perLength;
	private CharsetEnum(String name,int perLength){
		this.name = name;
		this.perLength = perLength;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setPerLength(int perLength) {
		this.perLength = perLength;
	}
	public int getPerLength() {
		return perLength;
	}
}
