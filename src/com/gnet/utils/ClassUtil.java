package com.gnet.utils;

import java.util.List;

import org.apache.commons.lang3.ClassUtils;

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
 * <td width="20%">描述信息</td><td width="80%" colspan="2">对类的操作方法</td>
 * </tr>
 * </tr>
 * </table>
 */
@SuppressWarnings("rawtypes")
public class ClassUtil {	
	/**
	 * 描述信息:获取类实现的所有接口.
	 * 
	 * 作者 sam
	 * 
	 * @param clazz
	 * 				需要操作的类
	 * @return
	 * 				略
	 */
	public static List getAllInterfaces(Class clazz){
    	return ClassUtils.getAllInterfaces(clazz);
    }

    /**
     * 描述信息:获取类所有父类.
     * 
     * 作者 sam
     * 
     * @param clazz
	 * 				需要操作的类
	 * @return
	 * 				略
     */
    public static List getAllSuperclasses(Class clazz){
    	return ClassUtils.getAllSuperclasses(clazz);
    }

    /**
     * 描述信息:获取简单类名.
     * 
     * 作者 sam
     * 
     * @param clazz
	 * 				需要操作的类
	 * @return
	 * 				略
     */
    public static String getShortClassName(Class clazz){
    	return ClassUtils.getShortClassName(clazz);
    }

    /**
     * 描述信息:获取包名.
     * 
     * 作者 sam
     * 
     * @param clazz
	 * 				需要操作的类
	 * @return
	 * 				略
     */
    public static String getPackageName(Class clazz){
    	return ClassUtils.getPackageName(clazz);
    }

    /**
     * 描述信息:判断是否可以转型.
     * 
     * 作者 sam
     * 
     * @param clazz1
	 * 				需要操作的类
     * @param clazz2
	 * 				需要操作的类
	 * @return
	 * 				略
     */
    public static boolean isAssignable(Class clazz1, Class clazz2){
    	return ClassUtils.isAssignable(clazz1, clazz2);
    }
}
