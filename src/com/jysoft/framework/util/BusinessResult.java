package com.jysoft.framework.util;

import java.io.Serializable;
/**
 * 
 * @author Administrator
 *
 */
public class BusinessResult implements Serializable {
	private boolean successful;
	private Object resultValue;
	private String errorCode;
	private String resultHint;
	
	public BusinessResult(boolean successful,Object resultValue){
		this.successful=successful;
		this.resultValue=resultValue;
	}
	
	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	public Object getResultValue() {
		return resultValue;
	}
	public void setResultValue(Object resultValue) {
		this.resultValue = resultValue;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getResultHint() {
		return resultHint;
	}
	public void setResultHint(String resultHint) {
		this.resultHint = resultHint;
	}
}
