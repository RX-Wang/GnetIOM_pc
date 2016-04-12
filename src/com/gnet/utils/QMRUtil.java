package com.gnet.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;





import org.apache.commons.codec.digest.DigestUtils;
import com.gnet.common.BaseException;
import com.gnet.common.BaseException.ExceptionCode;
import com.gnet.common.Common;
import com.gnet.common.Constants;
/**
 * @author wangzhao
 * 
 * @version v2.0
 * 
 *          Created on 2014年8月26日 上午9:33:46
 * 
 *          Revision History: Date Reviser Description
 * 
 *          ----------------------------------------------------
 * 
 *          Description: quickmake业务代码的工具类
 */
public class QMRUtil
{
	/**
	 * Description: 获取前台传过来的json
	 * 
	 * @param request
	 * 		           请求 			
	 * @param type
	 *         GMSG_HEAD获取表头 GMSG_QM 获取参数
	 * @return map
	 * 		         根据根据穿过来的json转化为map
	 * @throws BaseException
	 * 		         抛出异常
	 */
	public static Map<String, Object> getParameters(HttpServletRequest request,
			String type) throws BaseException
	{

		String json = request.getParameter(Constants.JSON_PARAM);
		if (Common.isNullOrEmpty(json))
		{
			throw new BaseException(ExceptionCode.NULLPARAMETER,"JSON_PARAM__参数是空的，请检查详细!");
		}
		else
		{
			Map<String, Object> map = (Map<String, Object>) JsonUtil
					.parse(json);
			if (Constants.GMSG_HEAD_REQ.equals(type) && map != null)
			{
				return (Map<String, Object>) map.get("GMSG_HEAD");
			}
			else
			{
				return (Map<String, Object>) map.get("GMSG_QM");
			}
		}
	}

	/**
	 * Description: map进行解析，取值
	 * @param name
	 * 			 获取值的在map里面存放的名称
	 * @param isNeed
	 *          这个值是否必须
	 * @param paramMap
	 * 			 封装参数的map
	 * @return String 取出来的值
	 * @throws BaseException
	 * 			抛出异常 
	 */
	public static String getParam2String(Map<String, Object> paramMap,
			String name, Boolean isNeed) throws BaseException
	{

		if (paramMap.get(name) == null||"".equals(paramMap.get(name).toString().trim()))
		{
			if(isNeed){
				throw new BaseException(ExceptionCode.NULLPARAMETER,name+"__参数没有获取到，请检查详细!");
			}else{
				return "";
			}
		}
		else
		{
			return paramMap.get(name).toString();
		}
	}
	/**
	 * Description: map进行解析，取值
	 * @param name
	 * 			 获取值的在map里面存放的名称
	 * @param isNeed
	 *          这个值是否一定要有
	 * @param paramMap
	 * 			 封装参数的map
	 * @return Integer
	 * 
	 * @throws BaseException
	 * 			抛出异常 
	 */
	public static Integer getParam2Int(Map<String, Object> paramMap,
			String name, Boolean isNeed) throws BaseException
	{

		if (paramMap.get(name) == null||"".equals(paramMap.get(name).toString().trim()))
		{
			if(isNeed){
				throw new BaseException(ExceptionCode.NULLPARAMETER,name+"__参数没有获取到，请检查详细!");
			}else{
				return null;
			}
		}
		else
		{
			return Integer.parseInt(paramMap.get(name).toString());
		}
	}

	
	/**
	 * Description: 参数转化为json格式
	 * @author WANGZHAO
	 * @param  Object...arg0  第一个参数 为json的key值，用逗号分开，第二个参数为json的value值的集合
	 * @return json
	 * @throws BaseException 抛出异常
	 */
	public static String param2MapJson(Object...arg0) throws BaseException{
		try
		{
			String[] paramName = arg0[0].toString().split(",");
			
			Object[] paramValue = (Object[]) arg0[1];
			
			StringBuffer _sb = new StringBuffer();
			_sb.append("{");
			for(int i =0;i<paramName.length;i++){
				if(i==0)
				{
					_sb.append("\""+paramName[i]+"\":\""+paramValue[i].toString()+"\"");
					
				}else{
					_sb.append(",\""+paramName[i]+"\":\""+paramValue[i].toString()+"\"");
				}	
			}
			_sb.append("}");
			return _sb.toString();
		}
		catch (Exception e)
		{
			throw new BaseException(ExceptionCode.CONTROLLER,"调用pram2MapJson方法出现错误，请查看源码.");
		}
	}
	
	/**
	  * Description:MD5加密
	  * 
	  * @param plain
	  *     加密数据
	  * @return
	  *     加密后数据
	  */
	 public static String Md5(String plain) {
	  String str = DigestUtils.md5Hex(plain);
	  return str;
	 }

}
