package com.jysoft.framework.util;

public class CheckUtil {
	/**
	 * 检查数值是否存在于数组中
	 * @param i
	 * @param arr
	 * @return
	 */
	public static boolean checkExistsInArray(int oldState,int []arr){
		int index = -1; //要查找的数值在数组中的位置
		if (arr!=null && arr.length>0) {
			for (int i = 0; i < arr.length; i++) {
				if (oldState==arr[i]) {
					index = i+1;
				}
			}
		}
		return index>-1?true:false;
	}
}
