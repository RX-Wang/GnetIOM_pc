package com.gnet.utils;

import hirondelle.date4j.DateTime;
import hirondelle.date4j.DateTime.DayOverflow;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

//import com.gnet.core.exception.GnetException;

/**
 * <table width="100%" border="1px">
 * <tr>
 * <td width="20%">作者</td><td width="80%" colspan="2">sam</td>
 * </tr>
 * <tr>
 * <td width="20%">版本</td><td width="80%" colspan="2">v1.0</td>
 * </tr>
 * <tr>
 * <td width="20%">创建日期</td><td width="80%" colspan="2">2013-06-24</td>
 * </tr>
 * <tr>
 * <td width="100%" colspan="3">修订记录:</td>
 * <tr>
 * <td width="20%">修改日期</td><td width="20%">修改人</td><td width="60%">修改记录</td>
 * </tr>
 * <tr>
 * <td width="20%">-------</td><td width="20%">-------</td><td width="60%">--------------</td>
 * </tr>
 * <tr>
 * <td width="20%">描述信息</td><td width="80%" colspan="2">日期的操作方法</td>
 * </tr>
 * </tr>
 * </table>
 */
public class DateUtil {

	/** 年月日模式字符串 */
	public static final String YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";
	/** 年月日模式字符串 */
	public static final String YEAR_MONTH_DAY_PATTERN_UPPER = "YYYY-MM-DD";
	/** 年模式字符串 */
	public static final String YEAR_PATTERN = "yyyy";
	/** 年模式字符串 */
	public static final String YEAR_PATTERN_UPPER = "YYYY";
	/** 日模式字符串 */
	public static final String DAY_PATTERN = "dd";
	/** 日模式字符串 */
	public static final String DAY_PATTERN_UPPER = "DD";
	/** 月模式字符串 */
	public static final String MONTH_PATTERN = "MM";
	/** 年月日模式字符串 */
	public static final String YEAR_MONTH_DAY = "yyyy.MM.dd";
	/** 年月日模式字符串 */
	public static final String YEAR_MONTH_DAY_UPPER = "YYYY.MM.DD";
	/** 时分秒模式字符串 */
	public static final String HOUR_MINUTE_SECOND_PATTERN = "HH:mm:ss";
	/** 时分秒模式字符串 */
	public static final String HOUR_MINUTE_SECOND_PATTERN_LOWER = "hh:mm:ss";
	/** 时分秒模式字符串 */
	public static final String HOUR_MINUTE_SECOND_PATTERN_FFF = "HH:mm:ss.fff";
	/** 时分秒模式字符串 */
	public static final String HOUR_MINUTE_SECOND_PATTERN_FFF_LOWER = "hh:mm:ss.fff";
	/** 年月日时分秒模式字符串 */
	public static final String YMDHMS_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/** 年月日时分秒模式字符串 */
	public static final String YMDHMS_PATTERN_UPPER = "YYYY-MM-DD hh:mm:ss";
	/** 年月日时分秒模式字符串 */
	public static final String YMDHMS_PATTERN_FFF = "yyyy-MM-dd HH:mm:ss.fff";
	/** 年月日时分秒模式字符串 */
	public static final String YMDHMS_PATTERN_FFF_UPPER = "YYYY-MM-DD hh:mm:ss";
	/** 年月日时分模式字符串 */
	public static final String YMDHM_PATTERN = "yyyy-MM-dd HH:mm";
	/** 年月日时分模式字符串 */
	public static final String YMDHM_PATTERN_UPPER = "YYYY-MM-DD hh:mm";
	/** 年月日时模式字符串 */
	public static final String YMDH_PATTERN = "yyyy-MM-dd HH";
	/** 年月日时模式字符串 */
	public static final String YMDH_PATTERN_UPPER = "YYYY-MM-DD hh";

	/**
	 * 描述信息:获取当前时间 格式为：YYYY-MM-DD
	 * 
	 * @return 符合格式的日期
	 */
	public static String getCurrentDate() {
		DateTime now = DateTime.now(TimeZone.getDefault());
		return now.format(YEAR_MONTH_DAY_PATTERN_UPPER);
	}

	/**
	 * 描述信息:获取当前时间 格式为：hh:mm:ss
	 * 
	 * @return 符合格式的日期
	 */
	public static String getCurrentTime() {
		DateTime now = DateTime.now(TimeZone.getDefault());
		return now.format("hh:mm:ss");
	}

	/**
	 * 描述信息:获取当前时间 格式为：hh:mm:ss.fff
	 * 
	 * @return 符合格式的日期
	 */
	public static String getCurrentLongTime() {
		DateTime now = DateTime.now(TimeZone.getDefault());
		return now.format("hh:mm:ss.fff");
	}

	/**
	 * 描述信息:获取当前时间 格式为：YYYY-MM-DD hh:mm:ss
	 * 
	 * @return 符合格式的日期
	 */
	public static String getCurrentDateTime() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS_PATTERN);
		return sdf.format(dt);
	}

	/**
	 * 描述信息:获取当前时间 格式为：YYYY-MM-DD hh:mm:ss.fff
	 * 
	 * @return 符合格式的日期
	 */
	public static String getCurrentLongDateTime() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS_PATTERN + ".SSS");
		return sdf.format(dt);
	}

	/**
	 * 描述信息:获取自定义格式得当前时间
	 * 
	 * @param format
	 *            自定义格式
	 * @return 符合格式的日期
	 */
	public static String getFormatDateTime(String format) {
		DateTime now = DateTime.now(TimeZone.getDefault());
		return now.format(format);
	}

	/**
	 * 描述信息:获取一个人的真实年龄
	 * 
	 * @param piYear
	 *            出生年份
	 * @param piMonth
	 *            出生月份
	 * @param piDay
	 *            出生的天
	 * @return 符合格式的日期
	 */
	public static int getAge(int piYear, int piMonth, int piDay) {
		DateTime today = DateTime.today(TimeZone.getDefault());
		DateTime birthdate = DateTime.forDateOnly(piYear, piMonth, piDay);
		int age = today.getYear() - birthdate.getYear();
		// getDayOfYear获取距离年初的总天数
		if (today.getDayOfYear() < birthdate.getDayOfYear()) {
			age = age - 1;
		}
		return age;
	}

	/**
	 * 描述信息:得到两个日期相差的天数，前三个参数是开始得年月日，后三个参数是结束的年月日
	 * 
	 * @param piSYear
	 *            第一个日期的年
	 * 
	 * @param piSMonth
	 *            第一个日期的月
	 * 
	 * @param piSDay
	 *            第一个日期的日
	 * 
	 * @param piEYear
	 *            第二个日期的年
	 * 
	 * @param piEMonth
	 *            第二个日期的月
	 * 
	 * @param piEDay
	 *            第二个日期的日
	 * 
	 * @return 符合格式的日期
	 */
	public static int daysTillChristmas(int piSYear, int piSMonth, int piSDay,
			int piEYear, int piEMonth, int piEDay) {
		DateTime _startDate = DateTime.forDateOnly(piSYear, piSMonth, piSDay);
		DateTime _endDate = DateTime.forDateOnly(piEYear, piEMonth, piEDay);
		int result = 0;
		if (_startDate.lt(_endDate)) {
			result = _startDate.numDaysFrom(_endDate);
		} else if (_startDate.gt(_endDate)) {
			result = _endDate.numDaysFrom(_startDate);
		}
		return result;
	}

	/**
	 * 描述信息:获取从当前日期向后计算多少天后的日期
	 * @param piDays
	 * 			     推算的天数
	 * @param pattern
	 *            日期格式：如"YYYY-MM-DD"、"YYYY-MM-DD hh:mm:ss.fff"
	 * @return  符合格式的日期
	 */
	public static String getPlusDays(int piDays, String pattern) {
		DateTime today = DateTime.today(TimeZone.getDefault());
		return today.plusDays(piDays).format(pattern);
	}

	/**
	 * 描述信息:日期相差的计算
	 * 
	 * @param date
	 *            要计算的日期
	 * @param year
	 *            相差的年份
	 * @param month
	 *            相差的月份
	 * @param day
	 *            相差的天数
	 * @return 符合格式的日期
	 */
	public static String dateDifference(String date, int year, int month,
			int day) {
		DateTime dt = new DateTime(date);
		DateTime result = dt.plus(0, 1, 0, 0, 0, 0, 0, DayOverflow.Spillover);
		return result.toString();
	}

	/**
	 * 描述信息:从给定的日期到现在共有多少周
	 * 
	 * @param year
	 *            给定日期的年
	 * 
	 * @param month
	 *            给定日期的月
	 * 
	 * @param day
	 *            给定日期的日
	 * 
	 * @return 符合格式的日期
	 */
	public static int weeksSinceStart(int year, int month, int day) {
		DateTime today = DateTime.today(TimeZone.getDefault());
		DateTime startOfProject = DateTime.forDateOnly(year, month, day);
		return today.getWeekIndex() - startOfProject.getWeekIndex();
	}

	/**
	 * 描述信息:本周的第一天，计算的为周日
	 * 
	 * @return 符合格式的日期
	 */
	public static DateTime firstDayOfThisWeek() {
		DateTime today = DateTime.today(TimeZone.getDefault());
		DateTime firstDayThisWeek = today; // start value
		int todaysWeekday = today.getWeekDay();
		int SUNDAY = 1;
		if (todaysWeekday > SUNDAY) {
			int numDaysFromSunday = todaysWeekday - SUNDAY;
			firstDayThisWeek = today.minusDays(numDaysFromSunday);
		}
		return firstDayThisWeek;
	}

	/**
	 * 
	 * 描述信息:根据传入的日期格式化pattern将传入的日期格式化成字符串。
	 * 
	 * @param date
	 *            要格式化的日期对象
	 * @param pattern
	 *            日期格式化pattern
	 * @return 格式化后的日期字符串
	 */
	public static String format(final Date date, final String pattern) {
		if (date == null) {
			return "";
		}
		DateFormat df = null;
		if (StringUtils.isBlank(pattern)) {
			df = new SimpleDateFormat(YEAR_MONTH_DAY_PATTERN);
		} else {
			df = new SimpleDateFormat(pattern);
		}
		return df.format(date);
	}

	/**
	 * 
	 * 描述信息:根据传入的日期格式化patter将传入的字符串转换成日期对象
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @param pattern
	 *            日期格式化pattern
	 * @return 转换后的日期对象
	 * @throws ParseException
	 *             如果传入的字符串格式不合法
	 */
	public static Date parse(final String dateStr, final String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			//GnetException.info(e);
		}
		return null;
	}

	/**
	 * 
	 * 将传入的字符串按照默认格式转换为日期对象（yyyy-MM-dd）
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @return 转换后的日期对象
	 * @throws ParseException
	 *             如果传入的字符串格式不符合默认格式（如果传入的字符串格式不合法）
	 */
	public static Date parse(final String dateStr) throws ParseException {
		return parse(dateStr, YEAR_MONTH_DAY_PATTERN);
	}

	private static final char[] zeroArray = "0000000000000000000000000000000000000000000000000000000000000000"
			.toCharArray();

	/**
	 * Pads the supplied String with 0's to the specified length and returns
	 * the result as a new String. For example, if the initial String is
	 * "9999" and the desired length is 8, the result would be "00009999".
	 * This type of padding is useful for creating numerical values that need
	 * to be stored and sorted as character data. Note: the current
	 * implementation of this method allows for a maximum <tt>length</tt> of
	 * 64.
	 *
	 * @param string the original String to pad.
	 * @param length the desired length of the new padded String.
	 * @return a new String padded with the required number of 0's.
	 */
	public static String zeroPadString(String string, int length) {
		if (string == null || string.length() > length) {
			return string;
		}
		StringBuilder buf = new StringBuilder(length);
		buf.append(zeroArray, 0, length - string.length()).append(string);
		return buf.toString();
	}

	/**
	 * Formats a Date as a fifteen character long String made up of the Date's
	 * padded millisecond value.
	 * 
	 *
	 * @return a Date encoded as a String.
	 */
	public static String dateToMillis(Date date) {
		return zeroPadString(Long.toString(date.getTime()), 15);
	}
	
	@Test
	public void test1(){
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(getCurrentDateTime());
		}
	}
	
}
