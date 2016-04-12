package com.gnet.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author chenf
 * 
 * @version v0.1
 * 
 *          Created on 2013-10-28
 * 
 *          Revision History: Date Reviser Description
 * 
 *          ----------------------------------------------------
 *          Description:公共帮助类
 */
public class ConstantUtil {

	/**
	 * Description:用来读取配置文件
	 * 
	 * @param key
	 *            配置文件的key值
	 * 
	 * @return 配置文件的value值
	 */
	public String getPropertiesByName(String key) {
		String value = "";
		Properties prop = new Properties();
		InputStream is = getClass().getClassLoader().getResourceAsStream("global.properties");
		try {
			prop.load(is);
			value = new String(prop.getProperty(key).getBytes("ISO-8859-1"), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

}
