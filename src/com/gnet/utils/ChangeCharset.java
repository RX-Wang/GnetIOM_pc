package com.gnet.utils;

import java.io.UnsupportedEncodingException;

/**
 * <table width="100%" border="1px">
 * <tr>
 * <td width="20%">作者</td><td width="80%" colspan="2">sam</td>
 * </tr>
 * <tr>
 * <td width="20%">版本</td><td width="80%" colspan="2">v1.0</td>
 * </tr>
 * <tr>
 * <td width="20%">创建日期</td><td width="80%" colspan="2">2014-04-18</td>
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
 * <td width="20%">描述信息</td><td width="80%" colspan="2">转换字符串的编码工具类</td>
 * </tr>
 * </tr>
 * </table>
 */
public class ChangeCharset {
	/** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
	public static final String US_ASCII = "US-ASCII";

	/** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
	public static final String ISO_8859_1 = "ISO-8859-1";

	/** 8 位 UCS 转换格式 */
	public static final String UTF_8 = "UTF-8";

	/** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
	public static final String UTF_16BE = "UTF-16BE";

	/** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
	public static final String UTF_16LE = "UTF-16LE";

	/** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
	public static final String UTF_16 = "UTF-16";

	/** 中文超大字符集 */
	public static final String GBK = "GBK";

	/**
	 * 描述信息:将字符编码转换成US-ASCII码
	 * 
	 * @param psTxt
	 * 				转换的字符串
	 * @return
	 * 				转换后的字符串
	 * @throws UnsupportedEncodingException
	 * 				不支持编码异常
	 */
	public String toASCII(String psTxt) throws UnsupportedEncodingException {
		return this.changeCharset(psTxt, US_ASCII);
	}

	/**
	 * 描述信息:将字符编码转换成ISO-8859-1码
	 * 
	 * @param psTxt
	 * 				转换的字符串
	 * @return
	 * 				转换后的字符串
	 * @throws UnsupportedEncodingException
	 * 				不支持编码异常
	 */
	public String toISO_8859_1(String psTxt) throws UnsupportedEncodingException {
		return this.changeCharset(psTxt, ISO_8859_1);
	}

	/**
	 * 描述信息:将字符编码转换成UTF-8码
	 * 
	 * @param psTxt
	 * 				转换的字符串
	 * @return
	 * 				转换后的字符串
	 * @throws UnsupportedEncodingException
	 * 				不支持编码异常
	 */
	public String toUTF_8(String psTxt) throws UnsupportedEncodingException {
		return this.changeCharset(psTxt, UTF_8);
	}

	/**
	 * 描述信息:将字符编码转换成UTF-16BE码
	 * 
	 * @param psTxt
	 * 				转换的字符串
	 * @return
	 * 				转换后的字符串
	 * @throws UnsupportedEncodingException
	 * 				不支持编码异常
	 */
	public String toUTF_16BE(String psTxt) throws UnsupportedEncodingException {
		return this.changeCharset(psTxt, UTF_16BE);
	}

	/**
	 * 描述信息:将字符编码转换成UTF-16LE码
	 * 
	 * @param psTxt
	 * 				转换的字符串
	 * @return
	 * 				转换后的字符串
	 * @throws UnsupportedEncodingException
	 * 				不支持编码异常
	 */
	public String toUTF_16LE(String psTxt) throws UnsupportedEncodingException {
		return this.changeCharset(psTxt, UTF_16LE);
	}

	/**
	 * 描述信息:将字符编码转换成UTF-16码
	 * 
	 * @param psTxt
	 * 				转换的字符串
	 * @return
	 * 				转换后的字符串
	 * @throws UnsupportedEncodingException
	 * 				不支持编码异常
	 */
	public String toUTF_16(String psTxt) throws UnsupportedEncodingException {
		return this.changeCharset(psTxt, UTF_16);
	}

	/**
	 * 描述信息:将字符编码转换成GBK码
	 * 
	 * @param psTxt
	 * 				转换的字符串
	 * @return
	 * 				转换后的字符串
	 * @throws UnsupportedEncodingException
	 * 				不支持编码异常
	 */
	public String toGBK(String psTxt) throws UnsupportedEncodingException {
		return this.changeCharset(psTxt, GBK);
	}

	/**
	 * 描述信息:字符串编码转换的实现方法
	 * 
	 * @param psTxt
	 *            待转换编码的字符串
	 * @param newCharset
	 *            目标编码
	 * @return
	 *            目标编码
	 *            
	 * @throws UnsupportedEncodingException
	 */
	public String changeCharset(String psTxt, String newCharset)
			throws UnsupportedEncodingException {
		if (psTxt != null) {
			// 用默认字符编码解码字符串。
			byte[] bs = psTxt.getBytes();
			// 用新的字符编码生成字符串
			return new String(bs, newCharset);
		}
		return null;
	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param psTxt
	 *            待转换编码的字符串
	 * @param oldCharset
	 *            原编码
	 * @param newCharset
	 *            目标编码
	 * @return
	 *            目标编码
	 *            
	 * @throws UnsupportedEncodingException
	 */
	public String changeCharset(String psTxt, String oldCharset, String newCharset)
			throws UnsupportedEncodingException {
		if (psTxt != null) {
			// 用旧的字符编码解码字符串。解码可能会出现异常。
			byte[] bs = psTxt.getBytes(oldCharset);
			// 用新的字符编码生成字符串
			return new String(bs, newCharset);
		}
		return null;
	}

	private static void main(String[] args) throws UnsupportedEncodingException {
		ChangeCharset test = new ChangeCharset();
		String _sTxt = "This is a 中文的 String!";
		System.out.println("str: " + _sTxt);
		String gbk = test.toGBK(_sTxt);
		System.out.println("转换成GBK码: " + gbk);
		System.out.println();
		String ascii = test.toASCII(_sTxt);
		System.out.println("转换成US-ASCII码: " + ascii);
		gbk = test.changeCharset(ascii, ChangeCharset.US_ASCII,
				ChangeCharset.GBK);
		System.out.println("再把ASCII码的字符串转换成GBK码: " + gbk);
		System.out.println();
		String iso88591 = test.toISO_8859_1(_sTxt);
		System.out.println("转换成ISO-8859-1码: " + iso88591);
		gbk = test.changeCharset(iso88591, ChangeCharset.ISO_8859_1,
				ChangeCharset.GBK);
		System.out.println("再把ISO-8859-1码的字符串转换成GBK码: " + gbk);
		System.out.println();
		String utf8 = test.toUTF_8(_sTxt);
		System.out.println("转换成UTF-8码: " + utf8);
		gbk = test.changeCharset(utf8, ChangeCharset.UTF_8, ChangeCharset.GBK);
		System.out.println("再把UTF-8码的字符串转换成GBK码: " + gbk);
		System.out.println();
		String utf16be = test.toUTF_16BE(_sTxt);
		System.out.println("转换成UTF-16BE码:" + utf16be);
		gbk = test.changeCharset(utf16be, ChangeCharset.UTF_16BE,
				ChangeCharset.GBK);
		System.out.println("再把UTF-16BE码的字符串转换成GBK码: " + gbk);
		System.out.println();
		String utf16le = test.toUTF_16LE(_sTxt);
		System.out.println("转换成UTF-16LE码:" + utf16le);
		gbk = test.changeCharset(utf16le, ChangeCharset.UTF_16LE,
				ChangeCharset.GBK);
		System.out.println("再把UTF-16LE码的字符串转换成GBK码: " + gbk);
		System.out.println();
		String utf16 = test.toUTF_16(_sTxt);
		System.out.println("转换成UTF-16码:" + utf16);
		gbk = test.changeCharset(utf16, ChangeCharset.UTF_16LE,
				ChangeCharset.GBK);
		System.out.println("再把UTF-16码的字符串转换成GBK码: " + gbk);
		String s = new String("中文".getBytes("UTF-8"), "UTF-8");
		System.out.println(s);
	}
}
