package com.gnet.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

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
 * <td width="20%">描述信息</td><td width="80%" colspan="2">系统配置文件操作工具</td>
 * </tr>
 * </tr>
 * </table>
 */
public class PropertyUtil {

	private static String WEBINFO_PATH = PathUtil.getWebInfPath();
	
	private static String DEFAULT_PROP_PATH = WEBINFO_PATH+"/WEB-INF/classes/config/global.properties";
	
	/**
	 * 获取默认的全部配置文件global。properties
	 * 
	 * @return	global属性文件的Properties对象
	 */
	public Properties getProperties() {
		Properties _props = new Properties();
		try {
			InputStream _in = new BufferedInputStream(new FileInputStream(DEFAULT_PROP_PATH));
			_props.load(_in);
			_in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _props;
	}
	
	/**
	 * 获取默认的全部配置文件global。properties
	 * 
	 * @param psProfilepath
	 *            需要获取的自定义属性文件的路径
	 * 
	 * @return	自定义属性文件的Properties对象
	 */
	public Properties getProperties(String psProfilepath) {
		Properties _props = new Properties();
		try {
			InputStream _in = new BufferedInputStream(new FileInputStream(WEBINFO_PATH+"/WEB-INF/classes/"+psProfilepath));
			_props.load(_in);
			_in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _props;
	}

	/**
	 * 读取属性文件中相应键的值
	 * 
	 * @param psKey
	 *            属性文件的KEY
	 * @return 属性文件中键对应的值
	 */
	public String getKeyValue(String psKey) {
		return getProperties().getProperty(psKey);
	}

	/**
	 * 读取属性文件中相应键的值
	 * 
	 * @param psKey
	 *            属性文件的KEY
	 * @param psProfilepath
	 *            需要获取的自定义属性文件的路径
	 *            
	 * @return 属性文件中键对应的值
	 */
	public String getKeyValue(String psProfilepath, String psKey) {
		return getProperties(psProfilepath).getProperty(psKey);
	}
	
	/**
	 * 读取属性文件中相应键的值
	 * 
	 * @param psKeys
	 *            属性文件中要读取的所有KEY
	 *            
	 * @return 属性文件中相应键对应的值
	 */
	public String[] getKeyValues(String[] psKeys) {
		Properties _props = getProperties();
		String[] _sArgValues = new String[psKeys.length];
		for (int i=0; i<psKeys.length; i++) {
			_sArgValues[i] = _props.getProperty(psKeys[i]);
		}
		return _sArgValues;
	}
	
	/**
	 * 读取属性文件中相应键的值
	 * 
	 * @param psProfilepath
	 *            需要获取的自定义属性文件的路径
	 * @param psKeys
	 *            属性文件中要读取的所有KEY
	 *            
	 * @return 属性文件中相应键对应的值
	 */
	public String[] getKeyValues(String psProfilepath, String[] psKeys) {
		Properties _props = getProperties(psProfilepath);
		String[] _sArgValues = new String[psKeys.length];
		for (int i=0; i<psKeys.length; i++) {
			_sArgValues[i] = _props.getProperty(psKeys[i]);
		}
		return _sArgValues;
	}
	
	/**
	 * 更新properties文件的键值对 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 * 
	 * @param profilepath
	 *				属性文件路径
	 * @param psKey
	 *            属性文件的KEY
	 * @param psValue
	 *				键值
	 */
	public void updateProperties(String psKey, String psValue) {
		try {
			Properties props = getProperties();
			OutputStream _fos = new FileOutputStream(DEFAULT_PROP_PATH);
			props.setProperty(psKey, psValue);
			props.store(_fos, "Update '" + psKey + "' value");
			closeStream(_fos);
		} catch (IOException e) {
			System.err.println("属性文件更新错误");
		}
	}

	/**
	 * 更新properties文件的键值对 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 * 
	 * @param psProfilepath
	 *            需要获取的自定义属性文件的路径
	 * @param psKey
	 *            属性文件的KEY
	 * @param psValue
	 *				键值
	 */
	public void updateProperties(String psProfilepath, String psKey, String psValue) {
		try {
			Properties props = getProperties(psProfilepath);
			OutputStream _fos = new FileOutputStream(psProfilepath);
			props.setProperty(psKey, psValue);
			props.store(_fos, "Update '" + psKey + "' value");
			closeStream(_fos);
		} catch (IOException e) {
			System.err.println("属性文件更新错误");
		}
	}
	
	/**
	 * 关闭流信息
	 */
	public void closeStream(OutputStream fos) {
		if (fos != null) {
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
