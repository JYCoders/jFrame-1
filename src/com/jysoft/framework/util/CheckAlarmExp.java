package com.jysoft.framework.util;

import java.util.ArrayList;
import java.util.List;

import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

/**
 * 校验预告警表达式的正确性
 * 
 * @author Administrator
 * 
 */
public class CheckAlarmExp {

	/**
	 * 
	 * @param expression 表达式
	 * @return
	 */
	public static boolean checkExp(String expression) {
		List<String> list = new ArrayList<String>();
		list.add("INCR_ROW");
		list.add("INCR_STOR");
		list.add("INCR_STOR_PER");
		list.add("INCR_ROW_PER");
		list.add("DB_CONNECT_RATE");
		list.add("DB_SPC_INCREASE");
		list.add("DB_SPC_USED_PEC");
		boolean flag =true;
		// 给表达式中的变量附上下文的值
		List<Variable> variables = new ArrayList<Variable>();
		for (String list1 : list) {
			variables.add(Variable.createVariable(list1, 1.0));
		}
		// 执行表达式
		try{
			ExpressionEvaluator.evaluate(expression, variables);
		}catch(Exception e){
			flag = false;
			//System.out.println(e.getMessage());
		}
		return flag;
	}
}
