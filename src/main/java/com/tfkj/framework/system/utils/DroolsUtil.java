package com.tfkj.framework.system.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.tfkj.framework.core.utils.SpringContextHolder;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.system.dao.DictDao;

/**
 * @Description: 规则引擎工具类
 * @author gaowei
 * @date 2017年2月8日
 */
public class DroolsUtil extends org.apache.commons.lang3.StringUtils {

	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);

	/**
	 * @Title: getPostRemarks
	 * @Description: 获得职务层次码值对应的Remarks大小
	 * @param @param dictValue
	 * @param @return 参数
	 * @return Integer 返回类型
	 * @throws
	 */
	public static Integer getPostRemarks(String dictValue) {

		if (StringUtils.isBlank(dictValue)) {
			return null;
		}
		String dictRemarks = "";
		//String dictRemarks = dictDao.findDictRemarks("YW_ZJ", dictValue);
		if (StringUtils.isBlank(dictRemarks)) {
			return null;
		}
		return objectToInteger(dictRemarks);
	}

	/**
	 * @Title: getRankRemarks
	 * @Description: 获得职级码值对应的Remarks大小
	 * @param @param dictValue
	 * @param @return 参数
	 * @return Integer 返回类型
	 * @throws
	 */
	public static Integer getRankRemarks(String dictValue) {

		if (StringUtils.isBlank(dictValue)) {
			return null;
		}
		String dictRemarks = "";
		//String dictRemarks = dictDao.findDictRemarks("YW_JSDJ_ZJ", dictValue);
		if (StringUtils.isBlank(dictRemarks)) {
			return null;
		}
		return objectToInteger(dictRemarks);
	}

	/**
	 * 获取两个日期间工作日天数 加入了法定节假日和调休
	 *
	 * @param beginDay
	 * @param endDay
	 * @return -1表示endDay比beginDay小； 100表示无穷大，避免不必要的循环
	 */
	public static int getWorkingDay(Date beginDay, Date endDay) {

		int workingDay = WorkDayUtils.getWorkingDay(beginDay, endDay);
		return workingDay;
	}

	/**
	 * @Title: trim
	 * @Description: 字符串去除空格
	 * @param @param str 要处理的内容
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws
	 */
	public static String trim(final String str) {

		return str == null ? null : str.trim();
	}

	/**
	 * @Title: isBlank
	 * @Description: 判断传入字符串是否为空
	 * @param @param
	 *            cs
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws *
	 *
	 *             <pre>
	* StringUtils.isBlank(null)      = true
	* StringUtils.isBlank("")        = true
	* StringUtils.isBlank(" ")       = true
	* StringUtils.isBlank("bob")     = false
	* StringUtils.isBlank("  bob  ") = false
	 *             </pre>
	 */
	public static boolean isBlank(final CharSequence cs) {

		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @Title: isNotBlank
	 * @Description: 判断传入字符串是否不为空
	 * @param @param
	 *            cs
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws *
	 *
	 *             <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 *             </pre>
	 */
	public static boolean isNotBlank(final CharSequence cs) {

		return !isBlank(cs);
	}

	/**
	 * 得到日期字符串 格式为（yyyyMMdd）
	 */
	public static String dateToString(Date date) {

		if (date == null) {
			return "";
		}
		String formatDate = DateFormatUtils.format(date, "yyyyMMdd");
		return formatDate;
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String dateToString(Date date, Object... pattern) {

		if (date == null) {
			return "";
		}
		String formatDate = "";
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期数字 默认格式（yyyyMMdd)
	 */
	public static Integer dateToInteger(Date date) {

		Integer formatDate = null;
		formatDate = objectToInteger(DateFormatUtils.format(date, "yyyyMMdd"));
		return date == null ? 0 : formatDate;
	}

	/**
	 * @Title: stringToDate
	 * @Description: 字符串转成日期 格式为yyyyMMdd
	 * @param @param str
	 * @param @return
	 * @param @throws ParseException 参数
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date stringToDate(String str) throws ParseException {

		if (isBlank(str)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(str);
		return date;
	}

	/**
	 * @Title: stringToDate
	 * @Description: 字符串转成日期 (默认格式为yyyy-MM-dd)
	 * @param @param str
	 * @param @param pattern
	 * @param @return
	 * @param @throws ParseException 参数
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date stringToDate(String str, String pattern) throws ParseException {

		if (isBlank(str)) {
			return null;
		}
		SimpleDateFormat sdf = null;
		if (pattern != null && pattern.length() > 0) {
			sdf = new SimpleDateFormat(pattern);
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		Date date = sdf.parse(str);
		return date;
	}

	/**
	 * @Title: compareToTwoDate
	 * @Description:
	 * 返回值 <0:before小;
	 * 返回值 =0:传入两个参数相等或传入两个参数任意一个为null;
	 * 返回值 >0:before大
	 * @param @param before
	 * @param @param after
	 * @param @return 参数
	 * @return int 返回类型
	 * @throws
	 */
	public static int compareToTwoDate(Date before, Date after) {

		if (before == null) {
			return 0;
		}
		if (after == null) {
			return 0;
		}
		int days = before.compareTo(after);
		return days;
	}

	/**
	 * @Title: compareToTwoDateB
	 * @Description:
	 * 返回值 <0:before小或传入两个参数任意一个为null;
	 * 返回值 =0:传入两个参数相等
	 * 返回值 >0:before大
	 * @param @param before
	 * @param @param after
	 * @param @return 参数
	 * @return int 返回类型
	 * @throws
	 */
	public static int compareToTwoDateB(Date before, Date after) {

		if (before == null) {
			return -1;
		}
		if (after == null) {
			return -1;
		}
		int days = before.compareTo(after);
		return days;
	}

	/**
	 * @Title: compareToTwoDateC
	 * @Description:
	 * 返回值 <0:before小或传入两个参数任意一个为null;
	 * 返回值 =0:传入两个参数相等
	 * 返回值 >0:before大
	 * @param @param before
	 * @param @param after
	 * @param @return 参数
	 * @return int 返回类型
	 * @throws
	 */
	public static int compareToTwoDateC(Date before, Date after) {

		if (before == null) {
			return 1;
		}
		if (after == null) {
			return 1;
		}
		int days = before.compareTo(after);
		return days;
	}

	/**
	 * 获取两个日期之间的天数
	 *
	 * @param before
	 * @param after
	 * @return 当传入参数为空时得 -1
	 */
	public static int getDaysOfTwoDate(Date before, Date after) {

		if (before == null || after == null) {
			return -1;
		}
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		double twoDate = (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
		int days = (new Double(twoDate)).intValue();
		return days;
	}

	/**
	 * 获取两个日期之间的年数
	 *
	 * @param before
	 * @param after
	 * @return 当传入参数为空时得 -1
	 * @throws ParseException
	 */
	public static int getYearsOfTwoDate(Date before, Date after) throws ParseException {

		if (before == null || after == null) {
			return -1;
		}
		Calendar calBegin = Calendar.getInstance(); // 获取日历实例
		Calendar calEnd = Calendar.getInstance();
		calBegin.setTime(before);
		calEnd.setTime(after);
		return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
	}

	/**
	 * @Title: objectToInteger
	 * @Description: 转换为Integer类型 如果传入null返回-1
	 * @param @param val
	 * @param @return 参数
	 * @return Integer 返回类型
	 * @throws
	 */
	public static Integer objectToInteger(Object val) {

		if (val == null) {
			return -1;
		}
		return toLong(val).intValue();
	}

	/**
	 * @Title: isContains
	 * @Description: 判断对象属性中是否包含某个值
	 * @param @param attribute 对象中,连接
	 * @param @param val
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isContains(String attribute, String val) {

		if (isBlank(attribute) || isBlank(val)) {
			return false;
		}
		String[] attArray = attribute.split(",");
		List<String> attList = Arrays.asList(attArray);
		if (attList.contains(val)) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: isNotContains
	 * @Description: 判断对象属性中是否不包含某个值
	 * @param @param attribute
	 * @param @param val
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isNotContains(String attribute, String val) {

		if (isBlank(attribute) || isBlank(val)) {
			return true;
		}
		String[] attArray = attribute.split(",");
		List<String> attList = Arrays.asList(attArray);
		if (attList.contains(val)) {
			return false;
		}
		return true;
	}

	/**
	 * @Title: getAge
	 * @Description: 根据两个时间算年龄 如果传入参数为null,返回-1
	 * @param @param start
	 * @param @param end
	 * @param @return 参数
	 * @return Integer 返回类型
	 * @throws
	 */
	public static Integer getAge(Date start, Date end) {

		if (start == null || end == null) {
			return -1;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = sdf.format(start);
		String endStr = sdf.format(end);
		// 计算年龄
		int age = Integer.parseInt(endStr.substring(0, 4)) - Integer.parseInt(startStr.substring(0, 4)) - 1;
		if (Integer.parseInt(startStr.substring(5, 7)) < Integer.parseInt(endStr.substring(5, 7))) {
			age++;
		} else if (Integer.parseInt(startStr.substring(5, 7)) == Integer.parseInt(endStr.substring(5, 7)) && Integer.parseInt(startStr.substring(8, 10)) <= Integer.parseInt(endStr.substring(8, 10))) {
			age++;
		}
		return age;
	}

	/**
	 * 转换为Double类型
	 */
	public static Double objectToDouble(Object val) {

		if (val == null) {
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch ( Exception e ) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val) {

		return objectToDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val) {

		return objectToDouble(val).longValue();
	}
}
