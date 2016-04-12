package com.gnet.common;


import java.text.MessageFormat;
import java.util.Map;

import com.gnet.common.BaseException.ExceptionCode;
import com.gnet.common.Common;
import com.gnet.utils.JsonUtil;


public class GeneralResult
{
	public GeneralResult()
	{

		this.clear();
	}

	public void clear()
	{
		this.rescode="";
		this.snId="";
		this.zip="";
		this.version="";
		this.jsonResult = "";
		this.message = "";
		this.sign="";
	}



	/**
	 * 消息
	 */
	private String message;

	/**
	 * json返回数据
	 */
	private String jsonResult;

	/**
	 * exception异常
	 */
	private BaseException baseException;
	
	/**
	 * snId
	 */
	private String snId;
	/**
	 * rescode
	 */
	private String rescode;
	/**
	 * zip
	 */
	private String zip;
	/**
	 * version
	 */
	private String version;
	/**
	 * sign
	 */
	private String sign;


	public String getSign()
	{
	
		return sign;
	}

	public void setSign(String sign)
	{
	
		this.sign = sign;
	}

	public String getSnId()
	{
	
		return snId;
	}

	public void setSnId(String snId)
	{
	
		this.snId = snId;
	}

	public String getRescode()
	{
	
		return rescode;
	}

	public void setRescode(String rescode)
	{
	
		this.rescode = rescode;
	}

	public String getZip()
	{
	
		return zip;
	}

	public void setZip(String zip)
	{
	
		this.zip = zip;
	}

	public BaseException getBaseException()
	{

		return baseException;
	}

	public void setBaseException(BaseException baseException)
	{

		this.baseException = baseException;
	}


	public String getMessage()
	{

		return message;
	}

	public void setMessage(String message)
	{

		this.message = message;
	}

	public String getJsonResult()
	{

		return jsonResult;
	}

	public void setJsonResult(String jsonResult)
	{

		this.jsonResult = jsonResult;
	}
	

	public String getVersion()
	{
	
		return version;
	}

	public void setVersion(String version)
	{
	
		this.version = version;
	}

	/**
	 * 
	 * Description:设置内部异常
	 * 
	 * 2014年8月16日 上午8:05:16
	 * 
	 * @param e
	 */
	public void setInnerExcption(Exception e)
	{

		if (this.baseException == null)
		{
			this.baseException = new BaseException(ExceptionCode.CONTROLLER);
		}
		this.baseException.setInnerException(e);
	}

	/**
	 * Description: 返回json
	 * 
	 * @return json字符串
	 */
	public String returnJson()
	{

		String _sReturnJson = "";

		String _sMessage =this.message;
		

		if (Common.isNullOrBlank(this.message))
		{
			_sMessage = this.baseException != null ? this.baseException.get_exceptionCode().getName():"";//设置内部异常
		}
		String _sJsonData = Common.isNullOrBlank(this.jsonResult) ? "\"\"": this.jsonResult;
		
		_sReturnJson = "{\"GMSG_HEAD\":{"+MessageFormat.format(Constants.GMSG_HEAD_RES,this.zip,this.snId,this.sign,this.version,this.rescode,_sMessage,this.getExceptionJson())+"},\"GMSG_QM\":"+_sJsonData+"}";				
		
		return _sReturnJson;
	}

	/**
	 * Description: 获取异常json
	 * 
	 * @return json格式异常字符串
	 */
	public String getExceptionJson()
	{

		String _sJson = "\"\"";

		if (this.baseException != null)
		{

			String _sInnerMessage = "\"\"";
			if (this.baseException.getInnerException() != null)
			{
				_sInnerMessage = "{"+ MessageFormat.format("\"message\":\"{0}\",\"stackTrace\":{1}",
								baseException.getInnerException().getMessage(),JsonUtil.serializeObject(baseException
								.getInnerException().getStackTrace()))
						+ "}";
			}

			_sJson = "{"
					+ MessageFormat
							.format("\"code\":\"{0}\",\"name\":\"{1}\",\"message\":\"{2}\",\"source\":\"{3}\",\"innerException\":{4}",
									baseException.get_exceptionCode().getCode(),
									baseException.get_exceptionCode().getName(),
									baseException.getMessage(),
									baseException.get_sCallMethodName(),
									_sInnerMessage) + "}";
		}

		return _sJson;
	}
	
	/**
	 * Description: 获取异常json
	 * 
	 * @author wangzhao
	 * 
	 * @param  headMap 传递的头文件
	 * 
	 * @return 
	 */
	public void setRespHead(Map<String, Object> headMap)
	{
		if(headMap!=null){
			this.snId= (String)(headMap.containsKey("SNID")?headMap.get("SNID"):"");
			this.zip=(String)(headMap.containsKey("ZIP")?headMap.get("ZIP"):"");
			this.version=(String)(headMap.containsKey("VERSION")?headMap.get("VERSION"):"");
			this.sign=(String) (headMap.containsKey("SIGN")?headMap.get("SIGN"):"");
		}
	}

}
