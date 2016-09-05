package com.jysoft.framework.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 类功能描述: 公共常量
 * 创建者： 吴立刚
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class Constant {

	/**
	 * 详细日志类型
	 */
	// 成功
	public static final String COMM_DETAILLOG_TYPE_SUCCESS = "0";

	// 失败
	public static final String COMM_DETAILLOG_TYPE_FAIL = "1";

	// 消息
	public static final String COMM_DETAILLOG_TYPE_MESSAGE = "9";

	/**
	 * 采集频率
	 */
	// 秒
	public static final String COMM_COLLECT_FREQ_SECOND = "0";

	// 分
	public static final String COMM_COLLECT_FREQ_MIN = "1";

	// 时
	public static final String COMM_COLLECT_FREQ_HOUR = "2";

	// 日
	public static final String COMM_COLLECT_FREQ_DAY = "3";

	// 周
	public static final String COMM_COLLECT_FREQ_WEEK = "4";

	// 月
	public static final String COMM_COLLECT_FREQ_MONTH = "5";

	// 季度
	public static final String COMM_COLLECT_FREQ_QUARTER = "7";

	// 半年
	public static final String COMM_COLLECT_FREQ_HALFYEAR = "7";

	// 年
	public static final String COMM_COLLECT_FREQ_YEAR = "6";
	/**
	 * 启动方式
	 */
	// 手动
	public static final String COMM_START_MODE_MANUAL = "0";

	// 自动
	public static final String COMM_START_MODE_AUTO = "1";

	/**
	 * 启动状态
	 */
	// 未启动
	public static final String COMM_START_STAT_INIT = "0";

	// 启动
	public static final String COMM_START_STAT_START = "1";

	// 停用
	public static final String COMM_START_STAT_DELETE = "2";

	// 暂停
	public static final String COMM_START_STAT_PAUSED = "3";

	/**
	 * 执行状态
	 */
	// 未执行状态
	public static final String COMM_TASK_STATUS_INIT = "0";

	// 执行中状态
	public static final String COMM_TASK_STATUS_EXECUTING = "1";

	// 执行完成状态
	public static final String COMM_TASK_STATUS_COMPLETE = "2";

	// 任务异常执行状态
	public static final String COMM_TASK_STATUS_ERROR = "3";

	/**
	 * 页面返回值
	 */
	// 正常
	public static final String COMM_PAGE_RETURN_NORMAL = "1";

	// 异常
	public static final String COMM_PAGE_RETURN_ERROR = "0";

	public static String exuser;
	
	// ORACLE数据库系统用户
	public static String sysUser;
	
	// 数据中心领导的角色ID
	public static String COMM_DC_LEADER_ROLEID;
	
	// 数据中心运维专责的角色ID
	public static String COMM_DC_ITMAN_ROLEID;
	
	// 业务部门信息化负责人的角色ID
	public static String COMM_BIZ_ITMAN_ROLEID;

	// 定时采集job启动类
	public static String SCHEDULER_CLASS;

	// 数据中心数据库ID
	public static String COMM_ODS_DB_ID;
	
	// 数据中心系统ID
	public static String COMM_ODS_SYS_ID;
	
	// COMPANY:360000Jiangxi 430000Hunan
	public static String COMM_COMPANY;
	
	// 数据库JDBC配置信息 BEG>>>>>>>>>URl
	public static String JDBC_URL;
	public static String JDBC_USER;
	public static String JDBC_PASSWORD;
	public static String JDBC_DRIVERCLASS;

	// 用户审计锁定时间
	public static String AUDIT_USERLOGIN_LOCKTIME;
	
	// 用户密码错误次数锁定阀值
	public static String AUDIT_USERLOGIN_LOCKLIMIT;
	public static String FILE_PATH;

	// 接口接入监控允许接口数据上传的误差时间
	public static String ADMISSIBLE_ERROR_TIME;

	/**
	 * 运监区域
	 */
	// 采集交换区
	public static String COMM_AREA_EXCHANGE = "1";

	// 采集交换区/业务数据缓存区
	public static String COMM_AREA_EXCHANGE_BUFF = "10";
	// 系统类型：dcms-数据中心辅助管控平台，asset-数据资产管理平台
	public static String COMM_SYSTEM;

	// jdbc创建数据库连接的超时时间，单位：秒。DriverManager.setLoginTimeout()
	public static int COMM_JDBC_LOGIN_TIMEOUT;
	// 允许多个客户端重复登录同一账号，COMM_USER_STATE为“0”，反之，为“1”
	public static String COMM_USER_STATE;
	// 导航条风格，left表示在左侧，top表示在上方
	public static String MENUSTYLE;

	/**
	 * 安全检查信息
	 */
	// 安全访问表达式
	public final static String COMM_SAFE_SQL_PATTERN = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
			+ "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

	// 平台中使用多个参数时 用到&符号
	public final static String COMM_SAFE_SCRIPT_PATTERN = "(\\||;|\\$|@|'|\\'|<|>|\\+|0x0d|0x0a|\\%27|\\%3D|\\%3B)";
	public static final String COMM_CHECK_SPECIAL = "^[\u4E00-\u9FA5A-Za-z_][\u4E00-\u9FA5A-Za-z0-9_]{0,}$";// 判断是否为数字

	/*
	 * [1] |（竖线符号） [2] & （& 符号） [3];（分号） [4] $（美元符号） [5] %（百分比符号） [6] @（at 符号）
	 * [7] '（单引号） [8] "（引号） [9] \'（反斜杠转义单引号） [10] \"（反斜杠转义引号） [11] <>（尖括号） [12]
	 * ()（括号） [13] +（加号） [14] CR（回车符，ASCII 0x0d） [15] LF（换行，ASCII 0x0a） [16]
	 * ,（逗号） [17] \（反斜杠）
	 */
	public static final String COMM_CNUMBER_PATTERN = "^/d*$";// 判断是否为数字

	private Constant() {

	}

	static {
		InputStream in = null;
		try {
			in = Constant.class.getClassLoader().getResourceAsStream(
					"conf/config.properties");
			Properties p = new Properties();
			p.load(in);
			in.close();
			exuser = p.getProperty("ods.db.exsysuser");
			sysUser = p.getProperty("ods.db.sysuser");
			COMM_DC_LEADER_ROLEID = p.getProperty("dc.leader.roleid");
			COMM_DC_ITMAN_ROLEID = p.getProperty("dc.itman.roleid");
			COMM_BIZ_ITMAN_ROLEID = p.getProperty("biz.itman.roleid");
			COMM_ODS_DB_ID = p.getProperty("ods.db.id");
			SCHEDULER_CLASS = p.getProperty("scheduler.class");
			COMM_ODS_SYS_ID = p.getProperty("ods.sys.id");
			COMM_COMPANY = p.getProperty("company");
			JDBC_URL = p.getProperty("jdbc.url");
			JDBC_PASSWORD = p.getProperty("jdbc_password");
			JDBC_USER = p.getProperty("jdbc_user");
			JDBC_DRIVERCLASS = p.getProperty("jdbc.driverClass");
			AUDIT_USERLOGIN_LOCKTIME = p
					.getProperty("audit.userlogin.locktime");
			AUDIT_USERLOGIN_LOCKLIMIT = p
					.getProperty("audit.userlogin.locklimit");
			FILE_PATH = p.getProperty("file_path");
			COMM_SYSTEM = p.getProperty("system");
			COMM_USER_STATE = p.getProperty("user.state");
			MENUSTYLE = p.getProperty("menuStyle");
			try {
				COMM_JDBC_LOGIN_TIMEOUT = Integer.parseInt(p.getProperty(
						"jdbcLoginTimeout").trim());
			} catch (Exception e) {
				COMM_JDBC_LOGIN_TIMEOUT = 10;
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ADMISSIBLE_ERROR_TIME = p.getProperty("admissible.error.time");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
