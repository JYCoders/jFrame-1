package com.jysoft.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
{ 
	public static final String Y_M_DH_MI_S="yyyy-MM-dd HH:mm:ss";
	public static final String Y_M_D="yyyy-MM-dd";
	public static final String YMD_ZH="yyyy年MM月dd日";
	public static final String YMDH_ZH="yyyy年MM月dd日HH时";
	public static final String MD_ZH="MM月dd日";
	public static final String MDH_ZH="MM月dd日HH时";
	public static final String MDHM_ZH="MM月dd日HH时mm分";
	public static final String DHS_ZH="dd日HH时ss分";
	public static final String Y_M_D_H="yyyy-MM-dd HH";
	public static final String Y_M_D_H_MI="yyyy-MM-dd HH:mm";
	public static final String YMDH="yyyyMMddHH";
	public static final String YMD="yyyyMMdd";
	public static  final SimpleDateFormat FORMAT_Y_M_DH_MI_S=new SimpleDateFormat(Y_M_DH_MI_S);
	public static  final SimpleDateFormat FORMAT_Y_M_D = new SimpleDateFormat(Y_M_D);
	public static  final SimpleDateFormat FORMAT_YMD_ZH = new SimpleDateFormat(YMD_ZH);
	public static  final SimpleDateFormat FORMAT_MD_ZH = new SimpleDateFormat(MD_ZH);
	public static  final SimpleDateFormat FORMAT_MDH_ZH = new SimpleDateFormat(MDH_ZH);
	public static  final SimpleDateFormat FORMAT_MDHM_ZH = new SimpleDateFormat(MDHM_ZH);
	public static  final SimpleDateFormat FORMAT_DHS_ZH = new SimpleDateFormat(DHS_ZH);
	public static  final SimpleDateFormat FORMAT_Y_M_D_H=new SimpleDateFormat(Y_M_D_H);
	public static  final SimpleDateFormat FORMAT_Y_M_D_H_MI=new SimpleDateFormat(Y_M_D_H_MI);
	public static final SimpleDateFormat FORMAT_YMDH = new SimpleDateFormat(YMDH);
	public static final SimpleDateFormat FORMAT_YMD = new SimpleDateFormat(YMD);
	
	public static String now()
	{
		return FORMAT_Y_M_DH_MI_S.format(new Date());
	}
	
	public static String now(SimpleDateFormat format)
	{
		return format.format(new Date());
	}
	/**
	 * 获取昨天日期
	 * @return YYYY-MM-DD
	 */
	public static String yesterday()
	{
		return getDateByDaysAndYeas(FORMAT_Y_M_D.format(new Date()),-1,0,FORMAT_Y_M_D);
	}
	
	/**
	 * 获取昨天日期
	 * @return YYYY-MM-DD
	 */
	public static String yesterday(SimpleDateFormat format)
	{
		return getDateByDaysAndYeas(format.format(new Date()),-1,0,format);
	}
	/**
	 * 获取第二天日期
	 * @return YYYY-MM-DD String
	 */
	public static String nextDay(String tringdateString,SimpleDateFormat format)
	{
		return getDateByDaysAndYeas(tringdateString,1,0,format);
	}
	/**
	 * 获取下一周日期
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static String nextWeekDay(String dateString,
			SimpleDateFormat format) {
		return getDateByDaysAndYeas(dateString,7,0,format);
	}
	
	/**
	 * 获取日期
	 * @return YYYY-MM-DD
	 */
	public static String getDateString(Date date,SimpleDateFormat format)
	{
		if(date==null)return null;
		return format.format(date);
	}
	
	
	/**
	 * 获取其他时间日期
	 * @param date format格式日期
	 * @param days	相差天数
	 * @param years	相差年数
	 * @param format 日期格式 如YYYY-MM-DD
	 * @return
	 */
	public static String getDateByDaysAndYeas(String date, int days, int years,
			SimpleDateFormat format) {
		String result=null;
		Date d;
		Calendar c=null;
		try {
			d = format.parse(date);
			c=Calendar.getInstance();
			c.setTime(d);
			if(days!=0)c.add(Calendar.DATE, days);
			if(years!=0)c.add(Calendar.YEAR, years);
			result=format.format(c.getTime());
		}  catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取其他时间日期
	 * @param date 日期
	 * @param days	相差天数
	 * @return
	 */
	public static Date getDateByDays(Date date, int days) {
		Date d=null;
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		if(days!=0)
			c.add(Calendar.DATE, days);
		d=c.getTime();
		return d;
	}
	/**
	 * 获取其他时间日期
	 * @param date 日期
	 * @param years	相差年数
	 * @return
	 */
	public static Date getDateByYear(Date date, int year) {
		Date d=null;
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		if(year!=0)
			c.add(Calendar.YEAR, year);
		d=c.getTime();
		return d;
	}
	
	/**
	 * 获取时间差(已经被日期格式的替代)
	 * @param rq2
	 * @param rq1
	 * @param format
	 * @return
	 */
	@Deprecated
	public static int getDayPass(String rq2, String rq1,SimpleDateFormat format) {
		Date d1,d2;
		int days=0;
		try {
			d2 = format.parse(rq2);
			d1 = format.parse(rq1);
			long diff=d2.getTime()-d1.getTime();
			days=(int)(diff/(24*60*60*1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}
	/**
	 * 获取时间差
	 * @param date1 起始日期
	 * @param date2 结束日期
	 * @return
	 */
	public static int getDayPass(Date date1, Date date2) {
		int days=0;
		long diff=date2.getTime()-date1.getTime();
		days=(int)(diff/(24*60*60*1000));
		return days;
	}
	// 获得本周一的日期
	public static String getMondayOFWeek(SimpleDateFormat format) {
		Calendar currentDate = Calendar.getInstance();
		int dayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK); //取得一周的第几天（第一天为周日）
		if(dayOfWeek==1)//为周日时，取6天前做周一
		{
			dayOfWeek=dayOfWeek-7;
		}
		else//不为周日时，取dayOfWeek-1天为周一，如周六为7，取7-1=6天前为周一
		{
			dayOfWeek--;
		}
		currentDate.add(Calendar.DATE, dayOfWeek);
		Date monday = currentDate.getTime();
		String preMonday = format.format(monday);
		return preMonday;
	}
	// 获取当月第一天
	public static String getFirstDayOfMonth(SimpleDateFormat format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = format.format(lastDate.getTime());
		return str;
	}
	
	// 计算当月最后一天,返回字符串
	public static String getLastDayOfMonth(SimpleDateFormat format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		str = format.format(lastDate.getTime());
		return str;
	}
	// 下月某天
	public static String getNextMonthDay(String dateString,SimpleDateFormat format) {
		String str = "";
		Calendar c = Calendar.getInstance();
		c.setTime(getDateByString(dateString,format));
		c.add(Calendar.MONTH, 1);// 增一个月
		str = format.format(c.getTime());
		return str;
	}
	public static Date getDateByString(String dateString,SimpleDateFormat format){
		Date d=null;
		Calendar c=null;
		try {
			d = format.parse(dateString);
		}  catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	// 上月第一天
	public static String getPreviousMonthFirst(SimpleDateFormat format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		str = format.format(lastDate.getTime());
		return str;
	}
	// 获得上月最后一天的日期
	public static String getPreviousMonthEnd(SimpleDateFormat format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.add(Calendar.DATE, -1);// 减一天，也就是上月最后一天
		str = format.format(lastDate.getTime());
		return str;
	}
	/**
	 * 获得系统当前年度第一天的日期
	 * @param SimpleDateFormat format
	 */
	public static String getCurrentYearFirst(SimpleDateFormat format) {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		Date d=cd.getTime();
		String str = format.format(d);
		return str;
	}
	/**
	 * 获得当前日期所属年度第一天的日期
	 * @param SimpleDateFormat format
	 */
	public static Date getYearFirstDate(Date date1) {
		Calendar c = Calendar.getInstance();
		c.setTime(date1);
		c.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		return c.getTime();
	}
	
	public static void main(String [] arg){
//		System.out.println(getMondayOFWeek(DateUtil.FORMAT_YMD));
//		System.out.println(getPreviousMonthFirst(DateUtil.FORMAT_YMD));
		//System.out.println(nextDay("20110303",DateUtil.FORMAT_YMD));
		//System.out.println(getNextMonthDay("20110328",DateUtil.FORMAT_YMD));
		
		System.out.println("20110101".substring(0,4)
				+"20110101".substring(4,6)
				+"20110101".substring(6,8));
		//System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		//System.out.println(Calendar.getInstance().get(Calendar.HOUR)==0);
		
	}

	public static Date nextDay(Date date) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}
	/**
	 * 获取下一周日期
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Date nextWeekDay(Date date) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 7);
		return c.getTime();
	}
	public static Date WeekDay(Date date) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE,0);
		return c.getTime();
	}
	/**
	 * 获取上周日期
	 * */
	public static Date lastWeekDay(Date date) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -7);
		return c.getTime();
	}
    
	public static Date passHours(Date date, int hour) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, hour);
		return c.getTime();
	}
	// 下月某天
	public static Date nextMonthDay(Date date) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		return c.getTime();
	}
	
	public static long getSecondDiff(Date d1,Date d2){
		long diff=Math.abs(d1.getTime()-d2.getTime());//无论谁先谁后，计算差值。
		return diff/1000;
	}
	
	public static long getMinuteDiff(Date d1,Date d2){
		long diff=Math.abs(d1.getTime()-d2.getTime());//无论谁先谁后，计算差值。
		return diff/(1000*60);
	}
	
	
}
