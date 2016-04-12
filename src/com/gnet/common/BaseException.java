package com.gnet.common;

public class BaseException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseException(ExceptionCode pCode, String psMessage)
	{
		this.clear();
		this._exceptionCode=pCode;
		this._sMessage = psMessage;
		this.set_sCallMethodName();
	}
	public BaseException(ExceptionCode pCode){
		this.clear();
		this.set_exceptionCode(pCode);
		this.set_sCallMethodName();
	}

	private String _sMessage;

	private ExceptionCode _exceptionCode;

	private String _sCallMethodName;
	
	/**
	 * 内部实际异常
	 */
	private Exception innerException;

	public BaseException(){
		this.clear();
		
	}
	public void clear()
	{

		this._sMessage = "";
		this._exceptionCode = null;
		this._sCallMethodName = "";
	}

	public String get_sMessage()
	{

		return _sMessage;
	}

	public void set_sMessage(String _sMessage)
	{

		this._sMessage = _sMessage;
	}

	public ExceptionCode get_exceptionCode()
	{

		return _exceptionCode;
	}

	public void set_exceptionCode(ExceptionCode _exceptionCode)
	{

		this._exceptionCode = _exceptionCode;
	}

	public String get_sCallMethodName()
	{

		return _sCallMethodName;
	}

	
	public void set_sCallMethodName()
	{
		String _methodName = "";

		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		for (StackTraceElement ste : stack) {
			if (ste.getClassName().indexOf(this.getClass().getName()) != -1
					|| ste.getClassName().startsWith("sun")
					|| ste.getClassName().startsWith("java")
					|| ste.getClassName().startsWith("org")) {
				continue;
			}
			_methodName += ste.getClassName() + "." + ste.getMethodName()
					+ "   line:" + ste.getLineNumber() + "*-newLine-*  ";
		}
		this._sCallMethodName = _methodName;
	}

	public Exception getInnerException()
	{
	
		return innerException;
	}

	public void setInnerException(Exception e)
	{

		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @author wangzhao
	 * 
	 * @version v1.0
	 * 
	 *          Created on 2014年8月16日 下午2:09:11
	 * 
	 *          Revision History: Date Reviser Description
	 * 
	 *          ----------------------------------------------------
	 * 
	 *          Description:异常编码定义
	 */
	public enum ExceptionCode
	{

		DAO("dao000", "数据处理层异常"), SERVICE("service000", "逻辑处理层异常"), CONTROLLER(
				"controller000", "页面请求处理层异常"), DATAEXIST("data001", "主键或唯一字段重复"), NULLPARAMETER(
				"parameter001", "参数为空"), SESSIONTIMEOUT("session001",
				"Session 过期"), NOREQUESTMETHOD("controller002", "不存在请求的方法"), UNDEFINEDENUM(
				"enum001", "未定义的枚举成员"), WS("ws001", "webService调用异常"), USERNOTEXIST(
				"system001", "用户不存在");

		/**
		 * 编码
		 */
		private String code;

		/**
		 * 描述
		 */
		private String name;

		private ExceptionCode(String psCode, String psName)
		{

			this.code = psCode;
			this.name = psName;
		}

		public String getCode()
		{

			return code;
		}

		public void setCode(String code)
		{

			this.code = code;
		}

		public String getName()
		{

			return name;
		}

		public void setName(String name)
		{

			this.name = name;
		}

	}

}
