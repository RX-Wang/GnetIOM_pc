/**
 * 
 */
package com.gnet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringExtension
 * <p>
 * <Strong>Date: </Strong> 2014年11月5日 上午11:49:38
 * 
 * @author Spartacus
 */
public class StringExtension {

	/**
	 * 对象是否为null或空字符串""
	 * <p>
	 * <h1>created by Spartacus at 2015年4月3日 下午1:41:13</h1>
	 * <p>
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNullOrEmpty(Object string) {
		if (string == null) {
			return true;
		} else if (string.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 对象是否为null或空白字符串
	 * <p>
	 * <h1>created by Spartacus at 2015年4月3日 下午1:41:58</h1>
	 * <p>
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isBlank(Object string) {
		if (string == null) {
			return true;
		} else if (string.toString().trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字符串格式化. 支持 {0},{1} 的patten
	 * <p>
	 * <h1>created by Spartacus at 2014年10月15日 下午4:44:13</h1>
	 * <p>
	 * 
	 * @param str
	 * @param args
	 * @return
	 */
	public static String format(String str, Object... args) {
		Pattern patten = Pattern.compile("\\{\\d+\\}");

		Matcher matcher = patten.matcher(str);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			String pos = matcher.group();
			String indexString = pos.substring(1, pos.length() - 1);
			Integer index = Integer.parseInt(indexString);
			// 判断参数索引是否存在
			if (index >= args.length) {
				throw new RuntimeException("在位置" + pos + "找不到参数数组对应索引的值。原始语句为:" + str);
			}
			matcher.appendReplacement(sb, toString(args[index]));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 将对象转为字符串，如果对象为null,则返回null
	 * <p>
	 * <h1>created by Spartacus at 2014年11月5日 下午12:07:14</h1>
	 * <p>
	 * 
	 * @param object
	 * @return
	 */
	public static String toString(Object object) {
		if (object == null) {
			return null;
		}
		return object.toString();
	}

	/**
	 * 转换为Boolean，如果原值为空，则返回空
	 * <p>
	 * <h1>created by Spartacus at 2014年10月24日 下午3:34:39</h1>
	 * <p>
	 * 
	 * @param obj
	 * @return
	 */
	public static Boolean parseBoolean(Object obj) {
		if (isBlank(obj)) {
			return null;
		} else {
			return Boolean.parseBoolean(obj.toString());
		}
	}

	/**
	 * 转换为Boolean。如果原值为空,则返回false
	 * <p>
	 * <h1>created by Spartacus at 2015年4月3日 下午1:49:53</h1>
	 * <p>
	 * 
	 * @param obj
	 * @return
	 */
	public static Boolean parseBooleanReturnFalseIfNull(Object obj) {
		if (isBlank(obj)) {
			return false;
		} else {
			return Boolean.parseBoolean(obj.toString());
		}
	}

	/**
	 * 转换为Integer,如果原值为空，则返回空
	 * <p>
	 * <h1>created by Spartacus at 2014年10月24日 下午3:36:11</h1>
	 * <p>
	 * 
	 * @param obj
	 * @return
	 */
	public static Integer parseInteger(Object obj) {
		if (isBlank(obj)) {
			return null;
		} else {
			return Integer.parseInt(obj.toString());
		}
	}

	/**
	 * 字符串是否为正整数
	 * <p>
	 * <h1>created by Spartacus at 2015年1月18日 上午9:51:01</h1>
	 * <p>
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNumber(String string) {
		if (isBlank(string)) {
			return false;
		}
		for (Character c : string.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 移除字符串末尾指定的字符串
	 * <p>
	 * <h1>created by Spartacus at 2015年4月7日 上午10:40:18</h1>
	 * <p>
	 * 
	 * @param str
	 * @param remove
	 * @return
	 */
	public static String removeTail(String str, String remove) {
		return str.endsWith(remove) ? str.substring(0, str.lastIndexOf(remove)) : str;
	}

	/**
	 * 移除字符串开头指定的字符串
	 * <p>
	 * <h1>created by Spartacus at 2015年4月7日 上午10:42:39</h1>
	 * <p>
	 * 
	 * @param str
	 * @param remove
	 * @return
	 */
	public static String removeHead(String str, String remove) {
		return str.startsWith(remove) ? str.substring(str.indexOf(remove) + 1) : str;
	}

	public static void main(String[] args) {
		System.out.println(StringExtension.removeHead("abac", "abc"));
	}
}
