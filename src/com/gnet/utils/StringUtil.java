package com.gnet.utils;

import java.util.Random;

import org.apache.commons.lang3.CharSetUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

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
 * <td width="20%">描述信息</td><td width="80%" colspan="2">对字符的操作方法</td>
 * </tr>
 * </tr>
 * </table>
 */
public class StringUtil {

	/**
	 * 描述信息:缩短到某长度,用...结尾
	 * 
	 * @param psText
	 *            操作的字符串
	 * @param piLen
	 *            缩短的长度
	 * @return
	 * 			      处理后的字符串
	 */
	public static String abbreviate(String psText, int piLen) {
		return StringUtils.abbreviate(psText, piLen);
	}

	/**
	 * 描述信息:截去字符串为以指定字符串结尾的部分
	 * 
	 * @param psText
	 *            操作的字符串
	 * @param psChar
	 *            截取的字符串
	 * @return
	 * 			      处理后的字符串
	 */
	public static String chomp(String psText, String psChar) {
		return StringUtils.chomp(psText, psChar);
	}

	/**
	 * 描述信息:检查一字符串是否包含另一字符串
	 * 
	 * @param psText
	 *            操作的字符串
	 * @param psChar
	 * 			  包含的字符串
	 * @return
	 * 			      处理后的字符串
	 */
	public static boolean contains(String psText, String psChar) {
		return StringUtils.contains(psText, psChar);
	}

	/**
	 * 描述信息:检查一字符串是否不是另一字符串的子集
	 * 
	 * @param psText
	 *            操作的字符串
	 * @param psChar
	 * 			      包含的字符串
	 * @return
	 * 			      处理后的字符串
	 */
	public static boolean containsNone(String psText, String psChar) {
		return StringUtils.containsNone(psText, psChar);
	}

	/**
	 * 描述信息:检查一字符串是否为另一字符串的子集
	 * 
	 * @param psText
	 *            操作的字符串
	 * @param psChar
	 *            操作的字符串
	 * @return
	 * 			      处理结果
	 */
	public static boolean containsOnly(String psText, String psChar) {
		return StringUtils.containsOnly(psText, psChar);
	}

	/**
	 * 描述信息:计算字符串中包含某字符数
	 * 
	 * @param psText
	 *            操作的字符串
	 * @param psChar
	 *            操作的字符串
	 * @return
	 * 			      处理结果
	 */
	public static int count(String psText, String psChar) {
		return CharSetUtils.count(psText, psChar);
	}

	/**
	 * 描述信息:参数1为空时，返回参数2
	 * 
	 * @param pobj1
	 *            操作的对象
	 * @param pobj2
	 *            操作的对象
	 * @return
	 * 			      处理结果
	 */
	public static Object defaultIfNull(Object pobj1, Object pobj2) {
		return ObjectUtils.defaultIfNull(pobj1, pobj2);
	}

	/**
	 * 描述信息:返回可以处理null的toString()
	 * 
	 * @param psText
	 *            操作的字符串
	 * @return
	 * 			      处理后的字符串
	 */
	public static String defaultString(String psText) {
		return StringUtils.defaultString(psText);
	}

	/**
	 * 描述信息:删除字符串中某字符
	 * 
	 * @param psText
	 *            操作的字符串
	 * @param psChar
	 *            删除的字符
	 */
	public static void delete(String psText, String psChar) {
		CharSetUtils.delete(psText, psChar);
	}

	/**
	 * 描述信息:去除字符中的空格
	 * 
	 * @param psText
	 */
	public static void deleteWhitespace(String psText) {
		StringUtils.deleteWhitespace(psText);
	}

	/**
	 * 描述信息:返回两字符串不同处开始至结束
	 * 
	 * @param psText1
	 *            操作的字符串
	 * @param psText2
	 *            操作的字符串
	 * @return
	 * 			      处理后的字符串
	 */
	public static String difference(String psText1, String psText2) {
		return StringUtils.difference(psText1, psText2);
	}

	/**
	 * 描述信息:特殊字符的替换
	 * 
	 * @param psPath
	 *            操作的字符串
	 * @return
	 * 			      处理后的字符串
	 */
	public static String formatPath(String psPath) {
		String reg0 = "\\\\＋";
		String reg = "\\\\＋|/＋";
		String temp = psPath.trim().replaceAll(reg0, "/");
		temp = temp.replaceAll(reg, "/");
		if (temp.endsWith("/")) {
			temp = temp.substring(0, temp.length() - 1);
		}
		if (System.getProperty("file.separator").equals("\\")) {
			temp = temp.replace('/', '\\');
		}
		return temp;
	}

	/**
	 * 描述信息:替换地址中带有“\”转义的字符
	 * 
	 * @param psPath
	 *            操作的字符串
	 * @return
	 * 			      处理后的字符串
	 */
	public static String formatAddr(String psPath) {
		return psPath.trim().replaceAll("\\\\", "");
	}
	
	/**
	 * 描述信息:判断字符串是否全是字母
	 * 
	 * @param psText
	 *            操作的字符串
	 * @return
	 *            判断结果
	 */
	public static boolean isAlpha(String psText) {
		return StringUtils.isAlpha(psText);
	}

	/**
	 * 描述信息:判断字符串是否由字母和数字组成
	 * 
	 * @param psText
	 *            操作的字符串
	 * @return
	 *            判断结果
	 */
	public static boolean isAlphanumeric(String psText) {
		return StringUtils.isAlphanumeric(psText);
	}

	/**
	 * 描述信息:当参数为空,长度为零或者仅由空白字符(whitespace)组成时,返回True;否则返回False
	 * 
	 * @param psText
	 *            操作的字符串
	 * @return
	 *            判断结果
	 */
	public static boolean isBlank(String psText) {
		return StringUtils.isBlank(psText);
	}

	/**
	 * 描述信息:返回两字符串不同处索引号
	 * 
	 * @param psText1
	 *            操作的字符串
	 * @param psText2
	 *            操作的字符串
	 * @return
	 *            索引数
	 */
	public static int indexOfDifference(String psText1, String psText2) {
		return StringUtils.indexOfDifference(psText1, psText2);
	}

	/**
	 * 描述信息:判断字符串是否全由数字组成
	 * 
	 * @param psText
	 *            操作的字符串
	 * @return
	 *            判断结果
	 */
	public static boolean isNumeric(String psText) {
		return StringUtils.isNumeric(psText);
	}

	/**
	 * 描述信息:保留字符串中某字符
	 * 
	 * @param psText
	 *            操作的字符串
	 * @param psChar
	 *            操作的字符
	 */
	public static void keep(String psText, String psChar) {
		CharSetUtils.keep(psText, psChar);
	}

	/**
	 * 描述信息:得到参数中字符颠倒后的字符串
	 * 
	 * @param psText
	 *            操作的字符串
	 * @return
	 * 			      处理后的字符串
	 */
	public static String reverse(String psText) {
		return StringUtils.reverse(psText);
	}

	/**
	 * 描述信息:对字符的拆分
	 * 
	 * @param psText
	 *            操作的字符串
	 * @param psChar
	 *            操作的字符
	 * @return
	 * 			      处理后的字符串
	 */
	public static String[] split(String psText, String psChar) {
		return StringUtils.split(psText, psChar);
	}

	/**
	 * 描述信息:合并重复的字符
	 * 
	 * @param psText
	 *            操作的字符串
	 * @param psChar
	 *            操作的字符
	 */
	public static void squeeze(String psText, String psChar) {
		CharSetUtils.squeeze(psText, psChar);
	}

	/**
	 * 描述信息:清除掉参数首尾的空白字符,如果参数全由空白字符组成则返回null
	 * 
	 * @param psText
	 *            操作的字符串
	 * @return
	 * 			      处理后的字符串
	 */
	public static String trimToNull(String psText) {
		return StringUtils.trimToNull(psText);
	}
	
	//java的转码操作 --satrt
	/**
	 * 描述信息:将ascii转换为字符串,按照字符串中返回的字符位置，对字符串进行解码
	 * 
	 * @param psMsg
	 *            操作的字符串
	 * @return
	 *            解码后的字符串
	 */
	public static String decode(String psMsg) {
		String[] _sArg = psMsg.split(" ");
		StringBuilder _sbdTmp = new StringBuilder();
		String[] _sArgPos = null;
		if(_sArg.length == 2){
			_sbdTmp.append(_sArg[0]);
			if(_sArg[1].indexOf(",")>=0){
				_sArgPos = _sArg[1].split(",");
			}
		}
		int _posLen = _sArgPos==null ? 0 :_sArgPos.length-1;
		for (int i = _posLen; i >= 0; i--) {
			_sbdTmp.deleteCharAt(Integer.parseInt(_sArgPos[i]));
		}
		//System.out.println("转换回："+_sbdTmp);
		
		StringBuilder _sbdChar = new StringBuilder();
		String[] chars = _sbdTmp.toString().split(",");
		for (int i = 0; i < chars.length; i++) {
			_sbdChar.append((char) Integer.parseInt(chars[i]));
		}
		return _sbdChar.toString();
		//System.out.println(_sbdChar);
	}

	/**
	 * 字符串转换为ASCII码
	 * 
	 * @param psMsg
	 *            操作的字符串
	 * @return
	 *            编码后的字符串
	 */
	public static String encode(String psMsg) {
		System.out.println(psMsg);
		char[] chars = psMsg.toCharArray(); // 把字符中转换为字符数组
		StringBuilder _sbdChars = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {// 输出结果
			_sbdChars.append((int) chars[i]+",");
		}
		_sbdChars.deleteCharAt(_sbdChars.length()-1);//去除最后一个“,”字符
		//System.out.println("原ASCII："+_sbdChars);
		//获取加入混淆的随机数
		Random _randNum =new Random(System.currentTimeMillis());
		//获取加入混淆的随机数插入位置
		Random _randPos =new Random(System.currentTimeMillis());
		
		int[] _rsNum = new int[_sbdChars.length()/5];
		int[] _rsPos = new int[_sbdChars.length()/5];
		for (int i = 0; i < _rsNum.length; i++) {
			_rsNum[i] = _randNum.nextInt(10);
		}
		int _iRandSeed = _sbdChars.length();
		for (int i = 0; i < _rsPos.length; i++) {
			_rsPos[i] = _randPos.nextInt(_iRandSeed);
		}
		//插入混淆的字符
		_sbdChars.append(" ");//返回的字符分为两部分，前一部分为混淆字符，后一部分为混淆位置，两部分中间用空格间隔
		for (int i = 0; i < _rsNum.length; i++) {
			_sbdChars.insert(_rsPos[i], _rsNum[i]);
			_sbdChars.append(_rsPos[i]);
			_sbdChars.append(",");
		}
		_sbdChars.deleteCharAt(_sbdChars.length()-1);//去除最后一个“,”字符
		//System.out.println("混淆字符："+_sbdChars);
		return _sbdChars.toString();
	}
	
	//java的转码操作 --end
}
