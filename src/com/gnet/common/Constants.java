package com.gnet.common;

/**
 * @author chenf
 * 
 * @version v0.1
 * 
 *          Created on 2015-08-6
 * 
 *          Revision History: Date Reviser Description ---- -------
 *          ----------------------------------------------------
 * 
 *          Description:定义一些静态值
 */
public final class Constants
{
	public static final String USER_IN_SESSION = "USER_IN_SESSION";
	
	/**前台传过来的表头**/
	public static final String GMSG_HEAD_REQ  = "GMSG_HEAD_REQ";
	
	/**前台传过来的参数**/
	public static final String GMSG_QM_REQ = "GMSG_QM_REQ";
	
	/**前台传过来的json名称**/
	public static final String JSON_PARAM = "JSON_PARAM";
	
	/**页面切换方式**/
	public static final String SWITCH_PARALLEL = "PARALLEL";
	
	public static final String ENCODE = "UTF-8";
	
	/**集团登录系统**/
	public static final String OP_LOGIN_SYSTEM = "操作员登录";
	
	/**系统登录**/
	public static final String SYS_LOGIN = "系统登录";
	
	/**操作员登录**/
	public static final String COM_LOGIN_SYSTEM = "操作员登录";
	
	/**退出系统**/
	public static final String LOGOUT_SYSTEM = "退出系统";
	
	/**锁定系统**/
	public static final String LOCK_SYSTEM = "锁定系统";
	
	/**添加操作员**/
	public static final String ADD_OPERATOR = "添加操作员";
	
	/**删除操作员**/
	public static final String DEL_OPERATOR = "删除操作员";
	
	/**修改操作员信息**/
	public static final String UPDATE_OPERATOR = "修改操作员信息";
	
	/**添加用户**/
	public static final String ADD_USER = "添加用户";
	
	
	/**删除用户**/
	public static final String DEL_USER = "删除用户";
	
	/**修改操作员信息**/
	public static final String UPDATE_USER = "修改用户信息";
	
	
	/**返回json的head**/	
	public static final String GMSG_HEAD_RES = "\"ZIP\": \"{0}\",\"SNID\":\"{1}\",\"SIGN\": \"{2}\",\"VERSION\": \"{3}\",\"RESCODE\": \"{4}\",\"MESSAGE\": \"{5}\",\"EXCEPTION\": {6}";
	
	public static class RESCODE
	{	
		public static final String SUCCESS = "0000";//执行成功
		
		public static final String EXCEPTION ="0001";//出现异常
		
		public static final String FAIL ="1000";//失败
	}
	
	public static class PAGE_TYPE
	{
		public static final String START_PAGE="START_PAGE";//起始页
		public static final String MAIN_PAGE="MAIN_PAGE";//首页
		public static final String LIST_PAGE="LIST_PAGE";//列表页
		public static final String CONTENT_PAGE="CONTENT_PAGE";//内容页
	}
	
	
	/**
	 * 首页布局
	 */
	public static final String MAIN_LAYOUT_PIC = "MAIN_LAYOUT_PIC";//带图片
	
	public static final String MAIN_LAYOUT_UNPIC = "MAIN_LAYOUT_UNPIC";//不带图片
	
	/**
	 * 步骤编码
	 */
	public static class STEP_CODE 
	{	//步骤
		public static final String  BASIC ="_basic"; //选择模板
		
		public static final String  TEM_SELECT ="_tmp_select"; //选择模板
		
		public static final String TEM_SELECT_STYLE ="_tmp_select_style";//选择模板样式
		
		public static final String DEP_MAIN ="_depmain";//设置模板主页
		
		public static final String DEP_LIST = "_dep_list";//设置模板列表
		
		public static final String DEP_LOAD = "_dep_load";//生成
		
	}
	
	
	public static class ASSEMBLY_TYPE
	{
		public static final String QM_SCROLLMAP = "GNET_QM_SCROLLMAP";
	
		public static final String QM_BUTTON_TXTIMAGE = "QM_BUTTON_TXTIMAGE";
		
		public static final String QM_BUTTONCONTAINER = "GNET_QM_BUTTONCONTAINER";//按钮容器
		
		public static final String QM_BUTTON_TXT = "QM_BUTTON_TXT";
		
		public static final String QM_BRIEFNEWSCONTAINER = "GNET_QM_BRIEFNEWSCONTAINER";
	}
}
