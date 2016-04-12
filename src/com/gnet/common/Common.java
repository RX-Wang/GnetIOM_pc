package com.gnet.common;

import java.util.Map;

import com.gnet.utils.PropertiesUtil;



public class Common
{

	
	
	
	
	
	
	public static String GNETIOT_ADDR=PropertiesUtil.getKeyValue("GNETIOT_ADDR");
	
	
	
	

	/**
	 * Description: 判断字符串是否是空白区间或者null
	 *
	 * @param str 
	 * 			   需要验证的字符串
	 * @return Boolean  
	 * 			 true or false 
	 */
	public static Boolean isNullOrBlank(String str)
	{

		if (str == null || str.trim().equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * Description: 判断字符串是否是空或者null
	 *
	 * @param str  
	 * 			需要验证的字符串 
	 * @return Boolean   true or false 
	 */
	public static Boolean isNullOrEmpty(String str)
	{

		if (str == null || "".equals(str))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Description: 转化为字符串
	 *
	 * @param str  
	 * 			
	 * @return Boolean   true or false 
	 */
	public static String toString(Map<String, Object> map,String name) 
	{
		if (map!=null&&map.get(name)!=null)
		{
			return map.get(name).toString();
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * Description: 转化为字符串
	 *
	 * @param str  
	 * 			
	 * @return Boolean   true or false 
	 */
	public static Integer toInteger(Map<String, Object> map,String name) 
	{
		if (map!=null&&map.get(name)!=null)
		{
			return Integer.parseInt(map.get(name).toString());
		}
		else
		{
			return null;
		}
	}
	
	
}
