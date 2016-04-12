package com.gnet.module.user.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gnet.common.Constants;
import com.gnet.module.group.service.IGroupService;
import com.gnet.module.log.service.ILogService;
import com.gnet.module.operator.service.IOperatorService;
import com.gnet.module.user.service.IUserService;
import com.gnet.utils.PropertiesUtil;
import com.gnet.utils.RestfulUtil;
import com.gnet.utils.UUIDUtil;

@Controller
@RequestMapping("/userController")
public class UserController {

	@Resource(name = "userService")
	private IUserService userService;
	
	@Resource(name = "logService")
	private ILogService logService;
	
	@Resource(name = "groupService")
	private IGroupService groupService;
	
	@Resource(name = "operatorService")
	private IOperatorService operatorService;
	
	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);

	// 后台服务器接口地址
	String GNETIOT_ADDR = PropertiesUtil.getKeyValue("GNETIOT_ADDR");

	/**
	 * 查询集团账号是否可以注册
	 */
	@RequestMapping(value="/checkCompanyId", method = RequestMethod.POST)
	public void checkCompanyId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		String companyId = request.getParameter("companyId");

		String endpoint = GNETIOT_ADDR + "checkCompanyId/";// 判断是否可以注册
		String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"cid\":\""
				+ companyId + "\"}}";

		// 验证是否可以注册
		RestfulUtil restfulUtil = new RestfulUtil();
		String result = restfulUtil.sendHttpRequest(endpoint, psJson);
		JSONObject checkResJson = JSONObject.fromObject(result);
		String obj = checkResJson.getJSONObject("GRES_HEAD").getString(
				"ResCode");

		System.out.println("obj:" + obj);
		if (obj.equals("1")) {// 可以注册
			jsonObj.put("DataResult", "pass");
			jsonObj.put("message", "集团账号可以注册");
		} else if (obj.equals("11")) {// 集团账号已经被注册
			jsonObj.put("DataResult", "hasexist");
			jsonObj.put("message", "集团账号已经被注册");
		} else if (obj.equals("33")) {// 集团账号不正确
			jsonObj.put("DataResult", "errorno");
			jsonObj.put("message", "集团账号不正确");
		} else {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询异常");
		}
		System.out.println("执行结果result：" + result);
		out.print(jsonObj);
	}

	/**
	 * 查询集团名称是否重复
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkCompanyName", method = RequestMethod.POST)
	public void checkCompanyName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			String companyName = request.getParameter("compName");
			boolean flag = userService.checkCompanyNameIsExist(companyName);

			if (!flag) {
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "名称可用");
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "名称已存在");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 登录(包含集团账号登录和操作员登录)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateLoginUser", method = RequestMethod.POST)
	public void validateLoginUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		String userName = "";
		String passWord = "";
		try {
			String type = request.getParameter("type");// 判断是集团登录还是用户登录
			if (type.equals("1")) {
				System.out.println("用户");
				String companyId2 = getCompanyId(request, response);
				
				if(companyId2!=null && companyId2!=""){
					//集团账号已经登录
					userName = request.getParameter("userName");
					
					boolean exist = userService.checkOperatorIsExist(userName,companyId2);
						if(exist){
						passWord = request.getParameter("userPwd");
		
						List<Map<String, Object>> list = userService.userLogin(
								userName, passWord,companyId2);// 用户登录
						// for (int i = 0; i < list.size(); i++) {
						// Sring a=list.get(i).get("");
						// }
						// String obj = list.get(0).toString();
						// JSONObject obj = (JSONObject) list.get(0);
		
						// 如果用户登录成功，将用户信息取出来存放到会话中
						if (list != null && list.size() > 0) {
							System.out.println("用户登录成功");
							// 用户登录成功后更新最后一次登录系统时间
							int count = userService.updateLasLoginTime(userName,companyId2);
							
		
							if (count > 0) {
								// 将用户信息存放到会话中
								List<Map<String, Object>> oInfo = (List<Map<String, Object>>) request
										.getSession().getAttribute("operatorInfo");
								if (oInfo == null) {
									request.getSession().setAttribute("operatorInfo",
											list);
								} else {
									request.getSession()
											.removeAttribute("operatorInfo");
									request.getSession().setAttribute("operatorInfo",
											list);
								}
								String companyId = getCompanyId(request, response);
								String opName = (String) list.get(0).get("opName");
								String opCode = (String) list.get(0).get("opCode");
								
								boolean logFlag = false;
								if(opCode.equals("system")){
									logFlag = logService.addLog("Operator", Constants.SYS_LOGIN, opName, opCode,companyId);
								}else{
									logFlag = logService.addLog("Operator", Constants.OP_LOGIN_SYSTEM, opName, opCode,companyId);
								}
								
								if(logFlag){
									logger.info("操作员登录系统成功");
									jsonObj.put("DataResult", "pass");
									jsonObj.put("message", "登录成功");
								}else{
									logger.info("操作员登录系统失败");
									jsonObj.put("DataResult", "fail");
									jsonObj.put("message", "登录失败");
								}
								
							} else {
								logger.error("更新用户最后一次登录系统时间异常");
							}
						} else {
							logger.info("用户名或密码错误");
							System.out.println("用户名或密码错误");
							jsonObj.put("DataResult", "fail");
							jsonObj.put("message", "用户账号或密码错误");
						}
					}else{
						System.out.println("操作员不存在");
						jsonObj.put("DataResult", "noexist");
						jsonObj.put("message", "用户不存在");
					}
				}else{
					//没有登录集团账号
					System.out.println("===============没有集团账号登录==========");
					jsonObj.put("DataResult", "nocompexist");
					jsonObj.put("message", "请先登录集团账号");
				}
			} else if (type.equals("0")) {
				System.out.println("企业");
				userName = request.getParameter("companyId");
				
				boolean exist = userService.checkCompanyIsExist(userName);
					if(exist){
					passWord = request.getParameter("companyPwd");
					String endpoint = GNETIOT_ADDR + "companyLogin/";// 集团账号登录
					String endpoint1 = GNETIOT_ADDR + "updateLoginStatus/";// 更新登录状态
	
					String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"companyId\":\""
							+ userName + "\",\"companyPwd\":\"" + passWord + "\"}}";
					RestfulUtil restfulUtil = new RestfulUtil();
					String result = restfulUtil.sendHttpRequest(endpoint, psJson);
	
					System.out.println("result:" + result);
	
					JSONObject checkResJson = JSONObject.fromObject(result);
					String obj = checkResJson.getJSONObject("GRES_HEAD").getString(
							"ResCode");
	
					if (obj.equals("1")) {
						// 登录成功
	
						String psJson1 = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"userName\":\""
								+ userName
								+ "\",\"accessType\":\"PC\""
								+ ",\"status\":\"1\"}}";
						String result1 = restfulUtil.sendHttpRequest(endpoint1,
								psJson1);
						JSONObject checkResJson1 = JSONObject.fromObject(result1);
						String obj1 = checkResJson1.getJSONObject("GRES_HEAD")
								.getString("ResCode");
						if (obj1.equals("1")) {
							// 将集团账号存放到会话中
							JSONObject companyInfo = (JSONObject) checkResJson
									.getJSONObject("GRES_IOT")
									.getJSONArray("INFOLIST").get(0);
	
							JSONObject cInfo = (JSONObject) request.getSession()
									.getAttribute("companyInfo");
	
							if (null == cInfo) {
								request.getSession().setAttribute("companyInfo",
										companyInfo);
							} else {
								request.getSession().removeAttribute("companyInfo");
								request.getSession().setAttribute("companyInfo",
										companyInfo);
							}
							jsonObj.put("DataResult", "pass");
							jsonObj.put("message", "集团账号登录成功");
						} else {
							jsonObj.put("DataResult", "fail");
							jsonObj.put("message", "更新登录状态失败");
						}
					} else {
						jsonObj.put("DataResult", "fail");
						jsonObj.put("message", "集团账号或密码错误");
					}
				}else {
					System.out.println("集团账号不存在");
					jsonObj.put("DataResult", "noexist");
					jsonObj.put("message", "集团账号不存在");
				}
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "集团账号登录失败,请稍后重试");
		}

		out.print(jsonObj);
	}

	

	/**
	 * 注册集团用户 注册到DRF，同时要注册到本地、同时完成初始化
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void register(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		String companyId = request.getParameter("companyId");// 公司账号
		String companyPwd = request.getParameter("companyPwd");// 公司密码
		String companyName = request.getParameter("companyName");// 公司名称
		String companyAddr = request.getParameter("companyAddr");// 公司地址
		String companyInterAddr = request.getParameter("companyInterAddr");// 邮箱
		String companyPhone = request.getParameter("companyPhone");// 电话
		String endpoint = GNETIOT_ADDR + "registCompany/";// 注册集团账号
		String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"companyId\":\""
				+ companyId
				+ "\","
				+ "\"companyPwd\":\""
				+ companyPwd
				+ "\","
				+ "\"companyName\":\""
				+ companyName
				+ "\","
				+ "\"companyInterAddr\":\""
				+ companyInterAddr
				+ "\","
				+ "\"companyAddr\":\""
				+ companyAddr
				+ "\","
				+ "\"companyPhone\":\"" + companyPhone + "\"}}";

		RestfulUtil restfulUtil = new RestfulUtil();
		String result = restfulUtil.sendHttpRequest(endpoint, psJson);
		JSONObject checkResJson = JSONObject.fromObject(result);
		String obj = checkResJson.getJSONObject("GRES_HEAD").getString(
				"ResCode");
		// String obj = comapnyIdInfo.get(0).toString();
		if (obj.equals("1")) {
			/*
			 * String endpoint1 = GNETIOT_ADDR + "registerSign/";// 标记注册访问端的类型
			 * 
			 * String psJson1 =
			 * "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"userName\":\""
			 * + companyId + "\"}}"; String result1 =
			 * restfulUtil.sendHttpRequest(endpoint1, psJson1); JSONObject
			 * checkResJson1 = JSONObject.fromObject(result1); String obj1 =
			 * checkResJson1.getJSONObject("GRES_HEAD") .getString("ResCode");
			 * //String obj1 = comapnyIdInfo1.get(0).toString();
			 * if(obj1.equals("1")){ System.out.println("标记用户注册端类型成功!");
			 */

			boolean flag = userService.addCompanyInfo(companyId, companyName,
					companyPwd, companyInterAddr, companyAddr, companyPhone);
			if (flag) {
				//注册成功、进行初始化
				groupService.addGroup(UUIDUtil.getUUID(),"",companyName,companyId);
				operatorService.addOperator("system","123456","system","系统操作员",companyId,"系统","SYSTEM");
				
				System.out.println("注册本地集团信息成功...");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "注册集团信息成功");
			} else {
				System.out.println("注册本地集团信息失败...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "注册集团信息失败");
			}

			/*
			 * }else{ jsonObj.put("DataResult", "fail"); jsonObj.put("message",
			 * "标记用户注册端类型失败"); }
			 */
		} else if (obj.equals("注册集团信息失败")) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "集团信息注册失败");
		}
		System.out.println(result);
		out.print(jsonObj);
	}

	/**
	 * 更新集团信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCompanyInfo")
	public void updateCompanyInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		String endpoint = GNETIOT_ADDR + "updateCompanyInfo/";// 更新集团信息

		try {
			String companyId = request.getParameter("companyId");
			String companyName = request.getParameter("companyName");
			String companyInterAddr = request.getParameter("companyInterAddr");
			String companyPhone = request.getParameter("companyPhone");
			String softName = request.getParameter("softName");
			String companyAddr = request.getParameter("companyAddr");
			String description = request.getParameter("description");
			String companyPic = request.getParameter("companyPic");
			String softLogo = request.getParameter("softLogo");

			String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"companyId\":\""
					+ companyId
					+ "\","
					+ "\"companyName\":\""
					+ companyName
					+ "\","
					+ "\"companyInterAddr\":\""
					+ companyInterAddr
					+ "\","
					+ "\"companyAddr\":\""
					+ companyAddr
					+ "\","
					+ "\"companyPhone\":\""
					+ companyPhone
					+ "\","
					+ "\"softName\":\""
					+ softName
					+ "\","
					+ "\"description\":\""
					+ description
					+ "\","
					+ "\"companyPic\":\""
					+ companyPic
					+ "\","
					+ "\"softLogo\":\"" + softLogo + "\"}}";

			String result = userService.updateCompanyInfo(endpoint, psJson,
					companyId, companyName, companyInterAddr, companyAddr,
					companyPhone, softName, description, companyPic, softLogo);

			JSONObject checkResJson = JSONObject.fromObject(result);
			String obj = checkResJson.getJSONObject("GRES_HEAD").getString(
					"ResCode");
			if (obj.equals("1")) {
				String cId = getCompanyId(request, response);
				List<Map<String, Object>> list = userService
						.getCompanyInfo(cId);
				if (list != null && list.size() > 0) {
					JSONArray jArray = JSONArray.fromObject(list);
					JSONObject lst = (JSONObject) jArray.get(0);
					JSONObject cInfo = (JSONObject) request.getSession()
							.getAttribute("companyInfo");

					if (null == cInfo) {
						request.getSession().setAttribute("companyInfo", lst);
					} else {
						request.getSession().removeAttribute("companyInfo");
						request.getSession().setAttribute("companyInfo", lst);
					}
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", "集团信息更新成功");
				}

			} else if (obj.equals("集团信息更新失败")) {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "集团信息更新失败");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "集团信息更新异常");
		}
		out.print(jsonObj);

	}

	/**
	 * 修改集团密码
	 */
	@RequestMapping(value = "/updateCompanyPwd")
	public void updateCompanyPwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			String companyId = request.getParameter("companyId");
			String companyPwd = request.getParameter("companyPwd");

			String endpoint = GNETIOT_ADDR + "updateCompanyPwd/";// 更新集团密码
			String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"companyId\":\""
					+ companyId + "\",\"companyPwd\":\"" + companyPwd + "\"}}";

			RestfulUtil restfulUtil = new RestfulUtil();
			String result = restfulUtil.sendHttpRequest(endpoint, psJson);

			System.out.println("result:" + result);

			JSONObject checkResJson = JSONObject.fromObject(result);
			String obj = checkResJson.getJSONObject("GRES_HEAD").getString(
					"ResCode");

			if (obj.equals("1")) {
				boolean flag = userService.updateCompanyPwd(companyId,companyPwd);
				if(flag){
					String cId = getCompanyId(request, response);
					List<Map<String, Object>> list = userService
							.getCompanyInfo(cId);
					if (list != null && list.size() > 0) {
//						JSONObject lst = JSONObject.fromObject(list);
						JSONArray jArray = JSONArray.fromObject(list);
						JSONObject lst = (JSONObject) jArray.get(0);
//						JSONObject lst = JSONObject.fromObject("{"+list+"}");
						JSONObject cInfo = (JSONObject) request.getSession()
								.getAttribute("companyInfo");

						if (null == cInfo) {
							request.getSession().setAttribute("companyInfo", lst);
						} else {
							request.getSession().removeAttribute("companyInfo");
							request.getSession().setAttribute("companyInfo", lst);
						}
						jsonObj.put("DataResult", "pass");
						jsonObj.put("message", "修改集团账号密码成功");
					}
				}else{
					System.out.println("修改本地集团密码失败");
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "修改集团账号密码失败");
				}
			} else {
				System.out.println("修改后台集团密码失败");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "修改集团账号密码失败");
			}
		} catch (Exception e) {
			System.out.println("修改集团密码异常：" + e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "修改集团账号密码异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 获取session中的用户信息(集团用户和操作员)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
	public void getUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		String flag = request.getParameter("flag");// 0集团，1用户

		if ("0".equals(flag)) {
			// 集团
			JSONObject companyInfo = (JSONObject) request.getSession()
					.getAttribute("companyInfo");
			if (null != companyInfo) {
				System.out.println("companyInfo:" + companyInfo);
				// 有session
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", companyInfo);
			} else {
				// 没有session
				jsonObj.put("CompanyDataResult", "fail");
				jsonObj.put("message", "Keytec");
			}
		} else {
			// 用户
			List<Map<String, Object>> operatorInfo = (List<Map<String, Object>>) request
					.getSession().getAttribute("operatorInfo");
			if (null != operatorInfo) {
//				System.out.println("operatorInfo:" + operatorInfo);
				// 有session
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", operatorInfo);
			} else {
				// 没有session
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "Andy");
			}
		}
		out.print(jsonObj);
	}

	/**
	 * 查询集团名称是否唯一
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkCompanyNameIsExist")
	public void checkCompanyNameIsExist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String companyName = request.getParameter("companyName");

			boolean flag = userService.checkCompanyNameIsExist(companyName);

			if (flag) {
				System.out.println("公司名称已经存在");
				jsonObj.put("DataResult", "exist");
				jsonObj.put("message", "公司名称已被占用");
			} else {
				System.out.println("公司名称可用");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "公司名称可用");
			}
		} catch (Exception e) {
			System.out.println("查询公司名称是否唯一异常：" + e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询公司名称是否唯一异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 获取会话中保存的集团账号
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getCompanyId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// String companyId = "";
		JSONObject companyInfo = (JSONObject) request.getSession()
				.getAttribute("companyInfo");
		if (companyInfo == null || companyInfo.equals("")) {
			return "";
		}

		String companyId = companyInfo.getString("companyId");
		if (!companyId.equals("") && null != companyId) {
			return companyId;
		} else {
			return "";
		}
	}
	
	/**
	 * 获取登录的操作员信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getOperatorInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		List<Map<String, Object>> operatorInfo = (List<Map<String, Object>>) request
				.getSession().getAttribute("operatorInfo");
		if (null != operatorInfo) {
//			System.out.println("operatorInfo:" + operatorInfo);
			return operatorInfo;
		} else {
			return null;
		}
	}
	
	/**
	 * 查询当前登录的操作员的权限
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/checkPermission")
	public void checkPermission(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			List<Map<String, Object>> operatorInfo = (List<Map<String, Object>>) request
					.getSession().getAttribute("operatorInfo");
			if (null != operatorInfo) {
				String opRule = (String) operatorInfo.get(0).get("opRule");
				if(opRule != null && opRule != ""){
//					System.out.println("operatorInfo:" + operatorInfo);
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", opRule);
				}else{
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "没有找到权限");
				}
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "操作过时,请重新登录");
			}
		} catch (Exception e) {
			System.out.println("操作异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "操作异常");
		}
		out.print(jsonObj);
	}
}
