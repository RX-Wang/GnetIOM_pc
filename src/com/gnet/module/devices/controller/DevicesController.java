package com.gnet.module.devices.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gnet.common.Common;
import com.gnet.common.Constants;
import com.gnet.message.GetPosition;
import com.gnet.module.devices.service.IDevicesService;
import com.gnet.module.log.service.ILogService;
import com.gnet.utils.DateUtil;
import com.gnet.utils.ExicelUtil;
import com.gnet.utils.FileUploadDownLoad;
import com.gnet.utils.QueueSend;
import com.gnet.utils.RestfulUtil;
import com.gnet.utils.UUIDUtil;
import com.hp.hpl.sparta.xpath.TrueExpr;

@Controller
@RequestMapping("/devicesController")
public class DevicesController {

	// String GNETIOT_ADDR =
	// "http://192.168.1.131:8080/MyGnetIOT/services/WebServiceRest/";
	String GNETIOT_ADDR = Common.GNETIOT_ADDR;

	@Resource(name = "devicesService")
	private IDevicesService devicesService;
	@Resource(name = "logService")
	private ILogService logService;

	/**
	 * 绑定设备
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBindDevice")
	public void addBindDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		JSONObject companyInfo = (JSONObject) request.getSession()
				.getAttribute("companyInfo");
		String companyId = companyInfo.getString("companyId");

		String nickName = request.getParameter("nickName");
		String userPhone = request.getParameter("userPhone");
		String userType = request.getParameter("userType");
		// String groupid = request.getParameter("groupId");
		String groupid = request.getParameter("gId");
		String userAddress = request.getParameter("userAddress");
		String description = request.getParameter("description");
		String binderType = request.getParameter("binderType");
		if (binderType == null || binderType == "") {
			binderType = "集团账号";
		}
		String uniqueId = request.getParameter("uniqueId");
		String securityCode = request.getParameter("securityCode");
		String simNumber = request.getParameter("simNumber");
		String devicesTypeId = request.getParameter("devicesTypeId");
		String linkman = request.getParameter("linkmen");

		String endpoint = GNETIOT_ADDR + "bindDevice/";// 绑定设备
		String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"userName\":\""
				+ companyId
				+ "\",\"nickName\":\""
				+ nickName
				+ "\",\"uniqueId\":\""
				+ uniqueId
				+ "\",\"securityCode\":\""
				+ securityCode + "\"}}";

		String checkResJson = devicesService.addBindDevice(endpoint, psJson,
				companyId, nickName, userPhone, userType, groupid, userAddress,
				description, binderType, uniqueId, securityCode, simNumber,
				devicesTypeId, linkman);
		JSONObject addResJson = JSONObject.fromObject(checkResJson);
		String obj = addResJson.getJSONObject("GRES_HEAD").getString("ResCode");
		if (!"".equals(obj) && null != obj) {
			if (obj.equals("0")) {
				System.out.println("绑定设备失败...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "绑定设备失败");
			} else if (obj.equals("1")) {
				List<Map<String, Object>> list = getOperatorInfo(request,response);
				String opName = (String) list.get(0).get("opName");
				String opCode = (String) list.get(0).get("opCode");
				boolean logFlag = logService.addLog("Operator",
						Constants.ADD_USER + ":" + nickName, opName, opCode,
						companyId);
				if (logFlag) {
					System.out.println("绑定设备成功...");
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", "绑定设备成功");
				} else {
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "绑定设备失败");
				}
			} else if (obj.equals("12")) {
				System.out.println("用户名不存在...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "用户名不存在");
			} else if (obj.equals("14")) {
				System.out.println("设备不存在...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "设备不存在");
			} else if (obj.equals("27")) {
				System.out.println("验证码不正确...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "验证码不正确");
			} else if (obj.equals("20")) {
				System.out.println("设备已经绑定...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "设备已经绑定,禁止重复绑定");
			} else if (obj.equals("30")) {
				System.out.println("设备昵称重复...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "设备昵称重复");
			} else {
				System.out.println("以上查询都不正确...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "以上查询都不正确");
			}
		} else {
			System.out.println("绑定设备失败...");
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "绑定设备失败");
		}
		out.print(jsonObj);
	}

	/**
	 * 查询当前用户是否是设备的主控用户(是：可以授权，否：无授权的权限)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/selDevPermission_sign")
	public void selDevPermission_sign(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			//
			JSONObject companyInfo = (JSONObject) request.getSession()
					.getAttribute("companyInfo");
			String userName = companyInfo.getString("companyId");
			String uniqueId = request.getParameter("uniqueId");

			String result = devicesService.selDevPermission_sign(userName,
					uniqueId);

			if ("1".equals(result)) {
				System.out.println("当前用户是主控用户,可以授权...");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "当前用户可以授权");
			} else if ("0".equals(result)) {
				System.out.println("当前用户不是设备主控用户，没有授权的权限...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "当前用户没有授权的权限");
			} else {
				System.out.println("查询用户权限异常...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "查询用户权限异常");
			}
		} catch (Exception e) {
			System.out.println("查询当前用户是否有授权的权限异常...");
		}
		out.print(jsonObj);
	}

	/**
	 * 查询当前账号(集团账号、个人账号)与设备是否已经绑定
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkDevIsExist")
	public void checkIsExist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {

			String authAccount = request.getParameter("authAccount");// 授权账号
			String accountType = request.getParameter("binderType");// 账号类型
			String uniqueId = request.getParameter("uniqueId");// 设备id

			boolean flag = devicesService.checkIsExist(accountType,
					authAccount, uniqueId);
			if (!flag) {
				System.out.println("当前账号与用户没有绑定...");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "可以绑定");
			} else {
				System.out.println("当前账号已经绑定...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "授权账号与设备已经绑定");
			}
		} catch (Exception e) {
			System.out.println("查询账号与设备是否绑定异常...");
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询账号与设备是否绑定异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 账号授权
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/accountAuthorize")
	public void addAuthorize(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			String accountType = request.getParameter("accountType");
			String account = request.getParameter("account");
			String id = request.getParameter("id");
			String companyId = getCompanyId(request, response);
			String permissionType = "AD";

			if (accountType.equals("集团账号")) {
				permissionType = "PC";
			}

			List<Map<String, Object>> list = checkDeviceInfoById(id);
			if (list != null && list.size() > 0) {

				String uniqueId = list.get(0).get("uniqueId").toString();
				String devicesSecCode = list.get(0).get("devicesSecCode")
						.toString();

				String endpoint = GNETIOT_ADDR + "addPermission/";// 账号授权
				String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"userName\":\""
						+ companyId
						+ "\",\"uniqueId\":\""
						+ uniqueId
						+ "\",\"permissionName\":\"" + account
						// <<<<<<< .mine
						+ "\",\"permissionType\":\"" + permissionType + "\"}}";
				//
				String checkResJson = devicesService.addAuthorize(endpoint,
						psJson, uniqueId, devicesSecCode, account, accountType,
						companyId);
				// =======
				// + "\",\"permissionType\":\"" + permissionType + "\"}}";

				// String checkResJson = devicesService.addAuthorize(endpoint,
				// psJson, uniqueId, devicesSecCode, account, accountType);
				// >>>>>>> .r420
				JSONObject addResJson = JSONObject.fromObject(checkResJson);
				String result = addResJson.getJSONObject("GRES_HEAD")
						.getString("ResCode");

				if (checkResJson != null && !checkResJson.equals("")) {
					if ("1".equals(result)) {
						System.out.println("授权成功...");
						jsonObj.put("DataResult", "pass");
						jsonObj.put("message", "授权成功");
					} else if ("0".equals(result)) {
						System.out.println("授权失败...");
						jsonObj.put("DataResult", "fail");
						jsonObj.put("message", "授权失败");
					}
				} else {
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "授权失败");
				}
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "授权失败");
			}
		} catch (Exception e) {
			System.out.println("授权异常...");
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "授权异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 解除授权
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delAuthorize")
	public void delAuthorize(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {

			String id = request.getParameter("id");
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> list = checkDeviceInfoById(id);
			String permissionType = "AD";
			if (list != null && list.size() > 0) {
				String uniqueId = list.get(0).get("uniqueId").toString();
				String account = list.get(0).get("userName").toString();
				String binderType = list.get(0).get("binderType").toString();

				if ("集团账号".equals(binderType)) {
					permissionType = "PC";
				}

				String endpoint = GNETIOT_ADDR + "deletePermission/";// 解除授权
				String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"userName\":\""
						+ companyId
						+ "\",\"uniqueId\":\""
						+ uniqueId
						+ "\",\"permissionName\":\""
						+ account
						+ "\",\"permissionType\":\"" + permissionType + "\"}}";

				boolean flag = devicesService.delAuthorize(endpoint, psJson,
						id, uniqueId);
				if (flag) {
					System.out.println("解除授权成功...");
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", "解除授权成功");
				} else {
					System.out.println("解除授权成功失败...");
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "解除授权成功失败");
				}
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "解除授权成功失败");
			}
		} catch (Exception e) {
			System.out.println("解除授权异常...");
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "解除授权异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 同步设备类型
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/synchroDevicesType")
	public void synchroDevicesType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		String endpoint = GNETIOT_ADDR + "synchroDevicesType/";// 同步设备类型
		String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{}}";
		RestfulUtil restfulUtil = new RestfulUtil();
		String result = restfulUtil.sendHttpRequest(endpoint, psJson);
		JSONObject checkResJson = JSONObject.fromObject(result);
		String obj = checkResJson.getJSONObject("GRES_HEAD").getString(
				"ResCode");

		JSONArray devicesType = checkResJson.getJSONObject("GRES_IOT")
				.getJSONArray("INFOLIST");

		if (obj.equals("1")) {
			System.out.println("同步设备类型成功...");
			jsonObj.put("DataResult", "pass");
			jsonObj.put("message", devicesType);
		} else {
			System.out.println("同步设备类型失败...");
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "同步设备类型失败");
		}
		out.print(jsonObj);
	}

	/**
	 * 绑定设备时查询当前集团账号下是否有同名昵称存在
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/checkSameNickName")
	public void checkSameNickName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		String endpoint = GNETIOT_ADDR + "checkSameNickName/";// 查询当前集团账号下是否有同名才昵称存在

		try {
			String nickName = request.getParameter("nickName");
			JSONObject companyInfo = (JSONObject) request.getSession()
					.getAttribute("companyInfo");
			String companyId = companyInfo.getString("companyId");
			String uniqueId = request.getParameter("uniqueId");
			if (companyId == "" || companyId == null) {
				companyId = "";
			}
			if (uniqueId == "" || uniqueId == null) {
				// uniqueId="860599000059923";
				uniqueId = "";
			}

			String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"companyId\":\""
					+ companyId
					+ "\",\"nickName\":\""
					+ nickName
					+ "\",\"uniqueId\":\"" + uniqueId + "\"}}";
			RestfulUtil restfulUtil = new RestfulUtil();
			String result = restfulUtil.sendHttpRequest(endpoint, psJson);
			JSONObject checkResJson = JSONObject.fromObject(result);
			String obj = checkResJson.getJSONObject("GRES_HEAD").getString(
					"ResCode");

			if (obj.equals("0")) {
				// 有同名的昵称存在
				System.out.println("验证失败...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "验证信息失败");
			} else if (obj.equals("1")) {
				JSONArray infoJson = checkResJson.getJSONObject("GRES_IOT")
						.getJSONArray("INFOLIST");
				if (infoJson.size() > 0) {
					System.out.println("该昵称可以使用...");
					jsonObj.put("DataResult", "success");
					jsonObj.put("message", infoJson.get(0));
				} else {
					System.out.println("该昵称可以使用...");
					jsonObj.put("DataResult", "suc");
					jsonObj.put("message", "信息验证正确");
				}
			} else if (obj.equals("12")) {
				System.out.println("集团账号不存在...");
				jsonObj.put("DataResult", "userNotExist");
				jsonObj.put("message", "集团账号不存在");
			} else if (obj.equals("14")) {
				System.out.println("设备号不正确...");
				jsonObj.put("DataResult", "devIdError");
				jsonObj.put("message", "设备号不正确");
			} else if (obj.equals("20")) {
				System.out.println("该设备已绑定...");
				jsonObj.put("DataResult", "hasBind");
				jsonObj.put("message", "设备已绑定");
			} else if (obj.equals("30")) {
				System.out.println("昵称重复...");
				jsonObj.put("DataResult", "nickNameRep");
				jsonObj.put("message", "昵称重复");
			} else {
				System.out.println("异常...");
				jsonObj.put("DataResult", "allError");
				jsonObj.put("message", "所有查询都不正确");
			}
		} catch (Exception e) {
			System.out.println("查询异常...");
			jsonObj.put("DataResult", "error");
			jsonObj.put("message", "查询异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 修改设备时查询设备昵称是否已经存在
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkNickName")
	public void checkNickName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		String endpoint = GNETIOT_ADDR + "checkNickName/";// 更新设备信息时查询是否有重复昵称
		// String endpoint = GNETIOT_ADDR + "modifyNickName/";//
		// 更新设备信息时查询是否有重复昵称

		try {
			String nickName = request.getParameter("nickName");
			JSONObject companyInfo = (JSONObject) request.getSession()
					.getAttribute("companyInfo");
			String companyId = companyInfo.getString("companyId");
			// String uniqueId = request.getParameter("uniqueId");
			if (companyId == "" || companyId == null) {
				companyId = "";
			}
			String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"companyId\":\""
					+ companyId + "\",\"nickName\":\"" + nickName + "\"}}";
			RestfulUtil restfulUtil = new RestfulUtil();
			String result = restfulUtil.sendHttpRequest(endpoint, psJson);
			JSONObject checkResJson = JSONObject.fromObject(result);
			String obj = checkResJson.getJSONObject("GRES_HEAD").getString(
					"ResCode");
			if (obj.equals("0")) {
				System.out.println("验证失败...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "验证信息失败");
			} else if (obj.equals("1")) {
				System.out.println("该昵称可以使用...");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "昵称可以使用");
			} else if (obj.equals("30")) {
				System.out.println("昵称重复...");
				jsonObj.put("DataResult", "nickNameRep");
				jsonObj.put("message", "昵称重复");
			} else {
				System.out.println("异常...");
				jsonObj.put("DataResult", "allError");
				jsonObj.put("message", "所有查询都不正确");
			}
		} catch (Exception e) {
			System.out.println("查询异常...");
			jsonObj.put("DataResult", "error");
			jsonObj.put("message", "查询异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteUser")
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			String id = request.getParameter("id");
			String nickName = "";
			String permission_sign = "0";
			String uniqueId = "";
			List<Map<String, Object>> list = checkDeviceInfoById(id);
			if (list != null && list.size() > 0) {
				nickName = list.get(0).get("nickName").toString();
				permission_sign = list.get(0).get("permission_sign").toString();
				uniqueId = list.get(0).get("uniqueId").toString();
			}

			// String nickName = request.getParameter("nickName");
			String endpoint = GNETIOT_ADDR + "deleteUserAndDevice/";// 删除用户
			JSONObject companyInfo = (JSONObject) request.getSession()
					.getAttribute("companyInfo");
			String companyId = companyInfo.getString("companyId");
			String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"companyId\":\""
					+ companyId + "\",\"nickName\":\"" + nickName + "\"}}";
			System.out.println("551:" + psJson);
			String checkResJson = devicesService.deleteUser(endpoint, psJson,
					permission_sign, id, uniqueId);
			JSONObject addResJson = JSONObject.fromObject(checkResJson);
			String obj = addResJson.getJSONObject("GRES_HEAD").getString(
					"ResCode");
			// <<<<<<< .mine

			if (obj.equals("1")) {

				List<Map<String, Object>> opList = getOperatorInfo(request,
						response);
				String opName = (String) opList.get(0).get("opName");
				String opCode = (String) opList.get(0).get("opCode");
				boolean logFlag = logService.addLog("Operator",
						Constants.DEL_USER + ":" + nickName, opName, opCode,
						companyId);
				if (logFlag) {
					// 用户删除成功
					System.out.println("设备删除成功...");
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", "删除设备成功");
				} else {
					System.out.println("删除设备失败...");
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "删除设备失败");
				}
				// =======
				//
				// if (obj.equals("1")) {
				// // 用户删除成功
				// System.out.println("设备删除成功...");
				// jsonObj.put("DataResult", "pass");
				// jsonObj.put("message", "删除设备成功");
				// >>>>>>> .r420
				// } else {
				// System.out.println("删除设备失败...");
				// jsonObj.put("DataResult", "fail");
				// jsonObj.put("message", "删除设备失败");
			}
		} catch (Exception e) {
			System.out.println("删除设备异常...");
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "删除设备异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 更新设备绑定信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDeviceUser")
	public void updateDeviceUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			JSONObject companyInfo = (JSONObject) request.getSession()
					.getAttribute("companyInfo");
			if (companyInfo == null || companyInfo.equals("")) {
				response.sendRedirect("../../web/pageController/loginPage");
			}
			String companyId = companyInfo.getString("companyId");

			// String id = request.getParameter("dev_userId");

			String dev_userId = request.getParameter("dev_userId");
			String nickName = request.getParameter("nickName");
			String oldNickName = request.getParameter("oldNickName");
			String userPhone = request.getParameter("userPhone");
			String userType = request.getParameter("userType");
			// String groupid = request.getParameter("groupId");
			String groupid = request.getParameter("gId");

			String userAddress = request.getParameter("userAddress");
			String description = request.getParameter("description");
			String uniqueId = request.getParameter("uniqueId");
			String securityCode = request.getParameter("securityCode");
			String simNumber = request.getParameter("simNumber");
			String devicesTypeId = request.getParameter("devicesTypeId");
			String linkmen = request.getParameter("linkmen");

			// 服务器修改设备昵称
			String endpoint = GNETIOT_ADDR + "modifyNickName/";// 修改设备昵称
			String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"userName\":\""
					+ companyId
					+ "\",\"nickName\":\""
					+ nickName
					+ "\",\"uniqueId\":\"" + uniqueId + "\"}}";

			// 本地修改设备、联系人、用户信息
			String checkResJson = devicesService.updateBindDevice(endpoint,
					psJson, dev_userId, companyId, nickName, oldNickName,
					userPhone, userType, groupid, userAddress, description,
					uniqueId, securityCode, simNumber, devicesTypeId, linkmen);

			JSONObject addResJson = JSONObject.fromObject(checkResJson);
			String obj = addResJson.getJSONObject("GRES_HEAD").getString(
					"ResCode");
			if (!"".equals(obj) && null != obj) {
				if (obj.equals("0")) {
					System.out.println("更新设备信息失败...");
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "更新设备信息失败");
				} else if (obj.equals("1")) {
					List<Map<String, Object>> list = getOperatorInfo(request,
							response);
					String opName = (String) list.get(0).get("opName");
					String opCode = (String) list.get(0).get("opCode");
					boolean logFlag = logService.addLog("Operator",
							Constants.UPDATE_USER + ":" + nickName, opName,
							opCode, companyId);
					if (logFlag) {
						System.out.println("更新设备信息成功...");
						jsonObj.put("DataResult", "pass");
						jsonObj.put("message", "更新设备信息成功");
					} else {
						jsonObj.put("DataResult", "fail");
						jsonObj.put("message", "更新设备失败");
					}
					// System.out.println("更新设备信息成功...");
					// jsonObj.put("DataResult", "pass");
					// jsonObj.put("message", "更新设备信息成功");
				}
			} else {
				System.out.println("更新设备失败...");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "更新设备失败");
			}
		} catch (Exception e) {
			System.out.println("更新设备异常...");
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "更新设备异常");

		}

		out.print(jsonObj);
	}

	/**
	 * 到授权的弹出层
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toShowquan")
	public ModelAndView toShowquan(HttpServletRequest request,
			HttpServletResponse response) {
		String idString = request.getParameter("id");
		System.out.println("id:" + idString);
		Map<String, String> map = new HashMap<String, String>();
		map.put("devId", idString);
		return new ModelAndView("shouquan", map);
	}

	/**
	 * 到添加的弹出层
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toAddDevice")
	public ModelAndView toAddDevice(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("addDevice");
	}

	/**
	 * 当添加设备成功时跳转到设备主页(layer框架的缺陷造成的)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toDevices")
	public ModelAndView toDevices(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("device");
	}

	/**
	 * 到修改设备的弹出层
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("modifyDevice")
	public ModelAndView modifyDevice(HttpServletRequest request,
			HttpServletResponse response) {
		String idString = request.getParameter("id");
		System.out.println(idString);
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", idString);
		return new ModelAndView("modifyDevice", map);
	}

	/**
	 * 生成 Exicel
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("creatExicel")
	public void creatExicel(HttpServletRequest request,HttpServletResponse response) {
		String headStr = request.getParameter("head");
		String listStr = request.getParameter("list");
		// 表格头
		List<String[]> list = new ArrayList<String[]>();
		JSONArray headL = JSONArray.fromObject(headStr);
		String[] head = new String[headL.size()];
		for (int a = 0; a < headL.size(); a++) {
			head[a] = (String) headL.get(a);
		}
		// 表格体
		JSONArray ll = JSONArray.fromObject(listStr);
		JSONArray llChild;
		for (int i = 0; i < ll.size(); i++) {
			llChild = JSONArray.fromObject(ll.get(i));
			String[] llChildString = new String[llChild.size()];
			for (int j = 0; j < llChild.size(); j++) {
				llChildString[j] = (String) llChild.get(j);
			}
			list.add(llChildString);
		}
		String fileName = UUIDUtil.getUUID().substring(0, 15);

		String filePath = ExicelUtil.createExcel("exicel", fileName, head,list, request);
		System.out.println("830生成的exicel的filePath:" + filePath);
		// Object filePathJSON = JSON.toJSON(filePath);
		StringBuffer buff = new StringBuffer("{\"filePath\":\"" + filePath+ "\"}");

		// Map<String, Object> buffMap = new HashMap<String, Object>();
		// buffMap.put("filePath", filePath);

		Object obj = JSON.toJSON(buff.toString());
		// Object obj = JSON.toJSON(buffMap.toString());
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	/**
	 * 查询当前用户的权限
	 */
	@RequestMapping(value = "/checkPermission_sign")
	public void checkPermission_sign(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String id = request.getParameter("id");
			JSONObject companyInfo = (JSONObject) request.getSession()
					.getAttribute("companyInfo");
			String companyId = companyInfo.getString("companyId");

			String sign = devicesService.checkPermission_sign(id, companyId);
			if (sign.equals("1")) {
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "1");
			} else if (sign.equals("0")) {
				jsonObj.put("DataResult", "notcontrol");
				jsonObj.put("message", "0");
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "0");
			}
		} catch (Exception e) {
			System.out.println("查询权限异常:" + e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "0");
		}
		out.print(jsonObj);
	}

	/**
	 * 在修改时查询设备信息进行数据回显
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkDeviceInfo")
	public void checkDeviceInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			String id = request.getParameter("id");

			List<Map<String, Object>> list = checkDeviceInfoById(id);
			System.out.println("744list:" + list);

			if (list != null && list.size() > 0) {
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
				System.out.println("查询设备信息成功");
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有找到设备信息");
				System.out.println("没有找到设备信息");
			}
		} catch (Exception e) {
			System.out.println("查询设备信息异常：" + e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询设备信息异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 下载Exicel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "downloadExicel")
	public void downloadExicel(HttpServletRequest request,
			HttpServletResponse response) {
		String filePath = request.getParameter("filePath");
		System.out.println(filePath);
		try {
			FileUploadDownLoad fileUpload = new FileUploadDownLoad();
			String filePathStr = filePath;
			System.out.println("930filePathStr:" + filePathStr);
			fileUpload.doDownload(request, response, filePathStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		;
		/* fileUploadDownLoad.download(filePath, response); */
	}

	/**
	 * 根据记录id查询设备信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> checkDeviceInfoById(String id)
			throws Exception {
		try {
			List<Map<String, Object>> list = devicesService.checkDeviceInfo(id);
			if (list != null && list.size() > 0) {
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("查询设备信息异常");
			return null;
		}
	}

	/**
	 * 根据id查询uniqueId,然后根据uniqueId查询绑定的用户
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping(value = "/selBindUser")
	public void selBindUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String id = request.getParameter("id");
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> list = checkDeviceInfoById(id);
			if (list != null && list.size() > 0) {
				String uniqueId = list.get(0).get("uniqueId").toString();
				// 根据设备id查询绑定的用户
				List<Map<String, Object>> userList = devicesService
						.selBindUserByUniqueId(uniqueId, companyId);

				if (userList != null && userList.size() > 0) {
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", userList);
				} else if (userList == null || userList.size() == 0) {
					jsonObj.put("DataResult", "notUser");
					jsonObj.put("message", "当前设备还没有绑定用户");
				} else {
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "查询设备绑定的用户失败");
				}
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "设备没有绑定用户");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询设备绑定的用户异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 在添加授权前验证授权账号是否可以授权
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkAccount")
	public void checkAccount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String accountType = request.getParameter("accountType");
			String account = request.getParameter("account");
			String id = request.getParameter("id");
			String companyId = getCompanyId(request, response);

			if (account.equals(companyId)) {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "不能为自己授权");
			} else {
				List<Map<String, Object>> list = checkDeviceInfoById(id);

				if (list != null && list.size() > 0) {
					String uniqueId = list.get(0).get("uniqueId").toString();

					String resCode = devicesService.checkAccount(accountType,
							account, uniqueId);

					if (resCode.equals("0")) {
						jsonObj.put("DataResult", "fail");
						jsonObj.put("message", "账号不存在");
					} else if (resCode.equals("1")) {
						jsonObj.put("DataResult", "pass");
						jsonObj.put("message", "账号可以授权");
					} else if (resCode.equals("2")) {
						jsonObj.put("DataResult", "fail");
						jsonObj.put("message", "账号已被授权");
					} else {
						jsonObj.put("DataResult", "fail");
						jsonObj.put("message", "查询异常");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("查询账号是否可用授权异常：" + e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询账号是否可授权异常...");
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
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getOperatorInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Map<String, Object>> operatorInfo = (List<Map<String, Object>>) request
				.getSession().getAttribute("operatorInfo");
		if (null != operatorInfo) {
			System.out.println("operatorInfo:" + operatorInfo);
			return operatorInfo;
		} else {
			return null;
		}
	}

	/**
	 * 根据设备号查询当前位置
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/selPositionByUniqueId")
	public void selPositionByUniqueId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String uniqueId = request.getParameter("uniqueId");
			String companyId = getCompanyId(request, response);

			// if ("NOCOMPANYINFO".equals(companyId)) {
			if (companyId == "" || companyId == null) {
				jsonObj.put("DataResult", "nocompanyInfo");
				jsonObj.put("message", "集团用户没有登录，请先登录集团用户");
			} else {
				// 查询当前用户下是否有该设备 nodevice
				boolean flag = devicesService.checkDeviceIsExist(uniqueId,
						companyId);
				if (flag) {
					String endpoint = GNETIOT_ADDR + "getCurrentPosition/";// 获取设备当前位置
					String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"userName\":\""
							+ companyId
							+ "\",\"uniqueId\":\""
							+ uniqueId
							+ "\"}}";

					RestfulUtil restfulUtil = new RestfulUtil();
					String result = restfulUtil.sendHttpRequest(endpoint,
							psJson);
					JSONObject checkResJson = JSONObject.fromObject(result);
					String obj = checkResJson.getJSONObject("GRES_HEAD")
							.getString("ResCode");
					JSONArray positionJson = checkResJson.getJSONObject(
							"GRES_IOT").getJSONArray("INFOLIST");

					if (obj.equals("1") && positionJson.size() == 0) {
						jsonObj.put("DataResult", "nohasposition");
						jsonObj.put("message", "没有此设备的当前位置信息");
					} else if (obj.equals("1") && positionJson.size() > 0) {
						JSONObject obj1 = (JSONObject) positionJson.get(0);
						String longitude = obj1.get("longitude") + "";
						String latitude = obj1.get("latitude") + "";
						String nickName = obj1.get("nick_name") + "";
						String address = obj1.get("address") + "";
						String time = obj1.get("time") + "";
						String unique_id = obj1.get("unique_id") + "";
						// }
						try {
							if (!"".equals(longitude) && !"".equals(latitude)
									&& "" != longitude && "" != latitude) {
								Double longitude3 = Double
										.parseDouble(longitude);// 经度
								Double latitude3 = Double.parseDouble(latitude);// 纬度
								String thisPostion1 = GetPosition.transgpsbd(
										longitude3, latitude3);
								longitude = thisPostion1.split(",")[0];
								latitude = thisPostion1.split(",")[1];
							}
						} catch (Exception e1) {
							System.out.println("坐标转换异常");
							e1.printStackTrace();
						}

						JSONObject js = new JSONObject();
						js.element("longitude", longitude);
						js.element("latitude", latitude);
						js.element("nick_name", nickName);
						js.element("address", address);
						js.element("time", time);
						js.element("unique_id", unique_id);

						JSONArray jar = new JSONArray();
						jar.add(js);
						jsonObj.put("DataResult", "pass");
						jsonObj.put("message", jar);
					} else {
						jsonObj.put("DataResult", "fail");
						jsonObj.put("message", "查询失败,请稍后重试");
					}

				} else {
					jsonObj.put("DataResult", "nodevice");
					jsonObj.put("message", "当前用户下没有该设备，请检查后重试");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 跳转到追踪页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toTrackPage")
	public void toTrackPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		Map<String, String> nowMap = new HashMap<String, String>();
		// // 保存已开启追踪模式的设备的uniqueId和nickName
		// List<Object> deviceList = new ArrayList<Object>();
		// // 保存已开启追踪模式的设备的uniqueId和curTime
		// List<Object> devInfoList = new ArrayList<Object>();

		try {
			String typeId = "";
			String uniqueId = request.getParameter("uniqueId");

			String curTime = DateUtil.getCurrentDateTime();
			// 查询当前设备是否已经开启追踪模式
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> devTrackInfoList = devicesService
					.checkDevIsTrackByUniqueId(uniqueId, companyId);
			if (devTrackInfoList != null && devTrackInfoList.size() > 0) {
				// 当前设备已经开启追踪模式,从数据库中取出设备开启追踪模式的时间
				String uniqueId1 = (String) devTrackInfoList.get(0).get(
						"uniqueId");
				String curTime1 = (String) devTrackInfoList.get(0).get(
						"beginTime");
				nowMap.put("uniqueId", uniqueId1);
				nowMap.put("curTime", curTime1);
				jsonObj.put("DataResult", "hasbegin");
				jsonObj.put("message", nowMap);
			} else {
				// 当前设备没有开启追踪模式，开启追踪模式
				typeId = devicesService.findTypeIdbyUniqueId(uniqueId);
				String openTrackStatusResult = openTrackStatus(uniqueId, typeId);
				// 获取到设备的昵称
				List<Map<String, Object>> list = devicesService
						.selDevByIdOrName(uniqueId, companyId);

				String nickName = (String) list.get(0).get("nickName");
				/**
				 * 11 : 开启追踪模式请求成功 12 : 开启追踪模式请求失败，请稍后重试 13 : 设备不在线,请稍后重试
				 * 
				 * */
				if (openTrackStatusResult == "11") {// 11 : 开启追踪模式请求成功
					// 开启追踪模式
					boolean flag = devicesService.insertDevTrack(uniqueId,
							nickName, curTime, companyId);
					if (flag) {
						nowMap.put("uniqueId", uniqueId);
						nowMap.put("curTime", curTime);
						jsonObj.put("DataResult", "pass");
						jsonObj.put("message", nowMap);
						jsonObj.put("openTrackStatusResult", "开启追踪模式请求成功");
					} else {
						jsonObj.put("DataResult", "fail");
						jsonObj.put("message", "");
						jsonObj.put("openTrackStatusResult", "开启追踪模式请求失败,请稍后重试");
					}
				} else if (openTrackStatusResult == "12") {
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "");
					jsonObj.put("openTrackStatusResult", "开启追踪模式请求失败,请稍后重试");
				} else if (openTrackStatusResult == "13") {
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "");
					jsonObj.put("openTrackStatusResult", "设备不在线,请稍后重试");
				} else {
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "");
					jsonObj.put("openTrackStatusResult", "开启追踪模式请求失败,请稍后重试");
				}
			}
		} catch (Exception e) {
			System.out.println("开启追踪模式异常：" + e.getMessage());
			e.printStackTrace();
			jsonObj.put("DataResult", "pass");
			jsonObj.put("message", "开启追踪模式异常");
			jsonObj.put("openTrackStatusResult", "开启追踪模式请求失败,请稍后重试");
		}
		/*
		 * try { String uniqueId = request.getParameter("uniqueId"); String
		 * curTime = DateUtil.getCurrentDateTime(); String typeId = ""; typeId =
		 * devicesService.findTypeIdbyUniqueId(uniqueId);
		 * //????????????????????????? String openTrackStatusResult =
		 * openTrackStatus(uniqueId, typeId);
		 * 
		 * // 获取session中保存开启追踪模式的设备的List deviceList = (List<Object>)
		 * request.getSession().getAttribute( "deviceList"); devInfoList =
		 * (List<Object>) request.getSession().getAttribute( "devInfoList");
		 * 
		 * // 如果List存在，则说明有设备开启追踪模式 if (deviceList != null && deviceList.size()
		 * > 0) { for (int i = 0; i < deviceList.size(); i++) { Map<String,
		 * Object> devMap = (Map<String, Object>) deviceList .get(i); if
		 * (!devMap.containsKey(uniqueId)) { // 当前设备还没有开启追踪模式
		 * devMap.put(uniqueId, ""); //<<<<<<< .mine //devInfoList.add(new
		 * HashMap<String, Object>().put(uniqueId, curTime)); Map<String,
		 * Object> map = new HashMap<String, Object>(); map.put(uniqueId,
		 * curTime); devInfoList.add(map);
		 * 
		 * 
		 * //======= // devInfoList.add(new HashMap<String, Object>().put( //
		 * uniqueId, curTime)); //>>>>>>> .r473 nowMap.put("uniqueId",
		 * uniqueId); nowMap.put("curTime", curTime); } else if
		 * (devMap.containsKey(uniqueId)) { // 当前设备已经开启追踪模式 for (int j = 0; j <
		 * devInfoList.size(); j++) { Map<String, Object> devInfoMap =
		 * (Map<String, Object>) devInfoList .get(j); String time = (String)
		 * devInfoMap.get(uniqueId); // nowMap.put(uniqueId, time);
		 * nowMap.put("uniqueId", uniqueId); nowMap.put("curTime", time); } } }
		 * request.getSession().removeAttribute("deviceList");
		 * request.getSession().setAttribute("deviceList", deviceList);
		 * request.getSession().removeAttribute("devInfoList");
		 * request.getSession().setAttribute("devInfoList", devInfoList);
		 * jsonObj.put("DataResult", "pass"); jsonObj.put("message", nowMap); }
		 * else { // 没有设备开启追踪模式 Map<String, Object> map = new HashMap<String,
		 * Object>(); Map<String, Object> map1 = new HashMap<String, Object>();
		 * map.put(uniqueId, ""); map1.put(uniqueId, curTime); List<Object> list
		 * = new ArrayList<Object>(); List<Object> list1 = new
		 * ArrayList<Object>(); list.add(map); list1.add(map1); //
		 * 已开启追踪模式的设备的uniqueId和nickName
		 * request.getSession().setAttribute("deviceList", list); //
		 * 已开启追踪模式的设备的uniqueId和curTime
		 * request.getSession().setAttribute("devInfoList", list1); //
		 * nowMap.put(uniqueId, curTime); nowMap.put("uniqueId", uniqueId);
		 * nowMap.put("curTime", curTime);
		 * 
		 * jsonObj.put("DataResult", "pass"); jsonObj.put("message", nowMap); }
		 * } catch (Exception e) { System.out.println("跳转到追踪页面异常：" +
		 * e.getMessage()); e.printStackTrace(); jsonObj.put("DataResult",
		 * "pass"); jsonObj.put("message", "跳转异常"); }
		 */
		out.print(jsonObj);
	}

	@RequestMapping(value = "/toPage")
	public ModelAndView toPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uniqueId = request.getParameter("uniqueId");
		Map<String, Object> map = new HashMap<String, Object>();
		String curTime = request.getParameter("curTime");
		map.put("uniqueId", uniqueId);
		map.put("curTime", curTime);
		return new ModelAndView("trackPage", map);

	}

	// 开启追踪模式后台
	public String openTrackStatus(String uniqueId, String typeID) {
		if ("G3".equals(typeID) || "G3B".equals(typeID) || "G3B" == typeID
				|| "G3" == typeID) {
			System.out.println("G3开启追踪模式。。。。。。。。。。。。");
			String atInstruction = "AT+GTFLW=" + uniqueId + ",G3,,,,FFFD$";
			String isOnline = devicesService.findIsOnlinebyUniqueId(uniqueId);
			if ("1".equals(isOnline) || "1" == isOnline) {
				System.out.println("atInstruction=" + atInstruction);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("uniqueId", uniqueId);
				jsonObj.put("transportType", "TCP");
				jsonObj.put("content", atInstruction);
				boolean result = QueueSend.produce(jsonObj.toString());
				System.out.println("回控指令发送结果" + result);
				if (result) {
					return "11";// 11 : 开启追踪模式请求成功

				} else {
					return "12"; // 12 : 开启追踪模式请求失败，请稍后重试
				}
			} else {
				System.out.println("设备不在线。。。。。。。。。。。。");
				return "13"; // 13 : 设备不在线,请稍后重试
			}

		} else {
			System.out.println("GL300开启追踪模式。。。。。。。。。。。。");
			String floIterTim = devicesService
					.findFloIterTimbyUniqueId(uniqueId);
			String msg = floIterTim + "," + floIterTim + "," + floIterTim + ","
					+ floIterTim + ",";
			String atInstruction = "AT+GTFRI=gl300,1,0,,,0000,0000," + msg
					+ "1F,1000,1000,0,5,50,1,50,,0000$";
			System.out.println("atInstruction=" + atInstruction);
			String isOnline = devicesService.findIsOnlinebyUniqueId(uniqueId);
			if ("1".equals(isOnline) || "1" == isOnline) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("uniqueId", uniqueId);
				jsonObj.put("transportType", "TCP");
				jsonObj.put("content", atInstruction);
				boolean result = QueueSend.produce(jsonObj.toString());
				System.out.println("回控指令发送结果" + result);
				if (result) {
					return "11"; // 11 : 开启追踪模式请求成功

				} else {
					return "12";// 12 : 开启追踪模式请求失败，请稍后重试
				}
			} else {
				System.out.println("设备不在线存抓擢列表。。。。。。。。。。。。");
				return "13";// 13 : 设备不在线,请稍后重试
			}

		}
	}

	/**
	 * 根据设备号和开启追踪模式的时间查询追踪轨迹
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             return "redirect:regirst.do";
	 */
	@RequestMapping(value = "/beginTrack")
	public void beginTrack(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			String uniqueId = request.getParameter("uniqueId");
			String curTime = request.getParameter("curTime");
			String companyId = getCompanyId(request, response);
			if (curTime == null || curTime == "") {
				curTime = DateUtil.getCurrentDateTime();
			}

			// 根据设备号和开启追踪模式时间来查询设备轨迹
			List<Map<String, Object>> list = devicesService.beginTrack(
					uniqueId, curTime);

			if (list != null && list.size() > 0) {
				// 更新设备的最后一次轨迹时间

				// 有轨迹,查出设备轨迹同时还要查出所有已经开启追踪模式的设备
				List<Map<String, Object>> devTrackList = devicesService
						.selAllDevTrack(companyId);
				// System.out.println("已经开启追踪模式 的设备devTrackList==" +
				// devTrackList);
				JSONArray obj = JSONArray.fromObject(devTrackList);
				System.out.println("有位置信息");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
				jsonObj.put("deviceList", obj);
			} else {
				// 没有轨迹
				List<Map<String, Object>> devTrackList = devicesService
						.selAllDevTrack(companyId);
				// System.out.println("已经开启追踪模式 的设备devTrackList==" +
				// devTrackList);
				jsonObj.put("DataResult", "noInfo");
				jsonObj.put("message", "没有当前设备的位置信息");
				jsonObj.put("deviceList", devTrackList);
			}
		} catch (Exception e) {
			System.out.println("查询当前设备的追踪轨迹异常：" + e.getMessage());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询设备的追踪轨迹异常");
		}

		// try {
		// String uniqueId = request.getParameter("uniqueId");
		// String curTime = request.getParameter("curTime");

		// if (curTime == null || curTime == "") {
		// List<Object> devInfoList = (List<Object>) request.getSession()
		// .getAttribute("devInfoList");
		// for (int j = 0; j < devInfoList.size(); j++) {
		// Map<String, Object> devInfoMap = (Map<String, Object>) devInfoList
		// .get(j);
		// if (devInfoMap.containsKey(uniqueId)) {
		// curTime = (String) devInfoMap.get(uniqueId);
		// } else {
		// devInfoMap.put(uniqueId, DateUtil.getCurrentDateTime());
		// curTime = DateUtil.getCurrentDateTime();
		// }
		// }
		// }
		//
		// List<Object> deviceList = (List<Object>) request.getSession()
		// .getAttribute("deviceList");
		// if (!deviceList.isEmpty()) {
		// List<Map<String, Object>> list = devicesService.beginTrack(
		// uniqueId, curTime);
		//
		// if (list != null && list.size() > 0) {
		// String nickName = (String) list.get(0).get("nickName");
		// for (int i = 0; i < deviceList.size(); i++) {
		// Map<String, Object> devMap = (Map<String, Object>) deviceList
		// .get(i);
		// devMap.put(uniqueId, nickName);
		// }
		// System.out.println("deviceList==" + deviceList);
		// JSONArray obj = JSONArray.fromObject(deviceList);
		// System.out.println("有位置信息");
		// jsonObj.put("DataResult", "pass");
		// jsonObj.put("message", list);
		// jsonObj.put("deviceList", obj);
		// } else {
		// String companyId = getCompanyId(request, response);
		// List<Map<String, Object>> list2 = devicesService
		// .getDeviceInfoByUniqueId(uniqueId, companyId);
		// if (list2 != null && list2.size() > 0) {
		// String nickName = (String) list2.get(0).get("nickName");
		//
		// if (nickName == null || nickName.equals("")) {
		// // nickName="设备"+uniqueId;
		// nickName = uniqueId;
		// }
		// for (int i = 0; i < deviceList.size(); i++) {
		// Map<String, Object> devMap = (Map<String, Object>) deviceList
		// .get(i);
		// devMap.put(uniqueId, nickName);
		// }
		// // JSONObject obj = JSONObject.fromObject(deviceList);
		// // JSON obj=(JSON) JSON.toJSON(deviceList);
		// JSONArray obj = JSONArray.fromObject(deviceList);
		//
		// jsonObj.put("DataResult", "noInfo");
		// jsonObj.put("message", "没有当前设备的位置信息");
		// jsonObj.put("deviceList", obj);
		// } else {
		// jsonObj.put("DataResult", "fail");
		// jsonObj.put("message", "没有找到当前设备的信息");
		// }
		// }
		// } else {
		// jsonObj.put("deviceList", "没有开启追踪模式的设备");
		// }
		// } catch (Exception e) {
		// System.out.println("开启追踪模式异常:" + e.getMessage());
		// jsonObj.put("DataResult", "fail");
		// jsonObj.put("message", "查询异常");
		// e.printStackTrace();
		// }
		out.print(jsonObj);
	}

	/**
	 * 关闭追踪模式
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/closeTrackStatus")
	public void closeTrackStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		// 返回给页面
		Map<String, String> nowMap = new HashMap<String, String>();

		try {

			/**
			 * 21 : 关闭追踪模式请求成功 22 : 关闭追踪模式请求失败，请稍后重试 23 : 设备不在线存抓擢列表成功 24
			 * :　设备不在线存抓擢列表失败
			 */
			String uniqueId = request.getParameter("uniqueId");
			String typeID = devicesService.findTypeIdbyUniqueId(uniqueId);
			String companyId = getCompanyId(request, response);
			String closeTrackStatusResult = closeTrackStatus(uniqueId, typeID);

			if (closeTrackStatusResult == "21") {
				// 关闭追踪(从数据库中删除)
				boolean flag = devicesService
						.closeDevTrack(uniqueId, companyId);
				// 查询所有开启追踪模式的设备
				List<Map<String, Object>> devTrackList = devicesService
						.selAllDevTrack(companyId);
				if (flag) {
					if (devTrackList != null && devTrackList.size() > 0) {
						String uniqueId1 = (String) devTrackList.get(0).get(
								"uniqueId");
						String curTime1 = (String) devTrackList.get(0).get(
								"beginTime");

						if (curTime1 == null || curTime1 == "") {
							curTime1 = DateUtil.getCurrentDateTime();
						}
						nowMap.put("uniqueId", uniqueId1);
						nowMap.put("curTime", curTime1);
					} else {
						nowMap.put("uniqueId", "");
						nowMap.put("curTime", "");
					}
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", nowMap);
					jsonObj.put("closeTrackStatusResult", "关闭追踪模式请求成功");
				} else {
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "关闭追踪模式失败");
					jsonObj.put("closeTrackStatusResult", "关闭追踪模式请求失败，请稍后重试");
				}
			} else if (closeTrackStatusResult == "22") {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "关闭追踪模式失败");
				jsonObj.put("closeTrackStatusResult", "关闭追踪模式请求失败，请稍后重试");
			} else if (closeTrackStatusResult == "23") {
				// 关闭追踪(从数据库中删除)
				boolean flag = devicesService
						.closeDevTrack(uniqueId, companyId);
				// 查询所有开启追踪模式的设备
				List<Map<String, Object>> devTrackList = devicesService
						.selAllDevTrack(companyId);
				if (flag) {
					if (devTrackList != null && devTrackList.size() > 0) {
						String uniqueId1 = (String) devTrackList.get(0).get(
								"uniqueId");
						String curTime1 = (String) devTrackList.get(0).get(
								"beginTime");

						if (curTime1 == null || curTime1 == "") {
							curTime1 = DateUtil.getCurrentDateTime();
						}
						nowMap.put("uniqueId", uniqueId1);
						nowMap.put("curTime", curTime1);
					} else {
						nowMap.put("uniqueId", "");
						nowMap.put("curTime", "");
					}
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", nowMap);
					jsonObj.put("closeTrackStatusResult", "设备不在线存抓擢列表成功");
				} else {
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "关闭追踪模式失败");
					jsonObj.put("closeTrackStatusResult", "关闭追踪模式请求失败，请稍后重试");
				}
				// jsonObj.put("DataResult", "fail");
				// jsonObj.put("message", "关闭追踪模式失败");
				// jsonObj.put("closeTrackStatusResult", "设备不在线存抓擢列表成功");
			} else if (closeTrackStatusResult == "24") {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "关闭追踪模式失败");
				jsonObj.put("closeTrackStatusResult", "设备不在线存抓擢列表失败");
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "关闭追踪模式失败");
				jsonObj.put("closeTrackStatusResult", "关闭追踪模式请求失败，请稍后重试");
			}
		} catch (Exception e) {
			System.out.println("关闭追踪模式异常：" + e.getMessage());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "关闭追踪模式异常");
			jsonObj.put("closeTrackStatusResult", "关闭追踪模式请求失败，请稍后重试");
		}

		// try {
		// String uniqueId = request.getParameter("uniqueId");
		// String typeID = devicesService.findTypeIdbyUniqueId(uniqueId);
		// closeTrackStatus(uniqueId, typeID);
		// List<Object> deviceList = (List<Object>) request.getSession()
		// .getAttribute("deviceList");
		// List<Object> devInfoList = (List<Object>) request.getSession()
		// .getAttribute("devInfoList");
		//
		// if (deviceList != null && deviceList.size() > 0) {
		// for (int i = 0; i < deviceList.size(); i++) {
		// Map<String, Object> devMap = (Map<String, Object>) deviceList
		// .get(i);
		// if (devMap.containsKey(uniqueId)) {
		// devMap.remove(uniqueId);
		// }
		// }
		//
		// List<String> list = new ArrayList<String>();
		// String uniqueId1 = "";
		// String curTime = "";
		// for (int j = 0; j < devInfoList.size(); j++) {
		// Map<String, Object> devInfoMap = (Map<String, Object>) devInfoList
		// .get(j);
		//
		// if (devInfoMap.containsKey(uniqueId)) {
		// devInfoMap.remove(uniqueId);
		// }
		//
		// if (!devInfoMap.isEmpty()) {
		// Set<Entry<String, Object>> devInfoSet = devInfoMap
		// .entrySet();
		//
		// for (Entry<String, Object> entry : devInfoSet) {
		// list.add(entry.getKey());
		// }
		//
		// if (list != null && list.size() > 0) {
		// uniqueId1 = list.get(0);
		// curTime = (String) devInfoMap.get(uniqueId1);
		// }
		// }
		// }
		// //<<<<<<< .mine
		// nowMap.put("uniqueId", uniqueId1);
		// nowMap.put("curTime", curTime);
		// // nowMap.put(uniqueId1, curTime);
		// //=======
		// //
		// // nowMap.put(uniqueId1, curTime);
		// //>>>>>>> .r473
		// } else {
		// // 已经没有设备开启追踪模式了
		// // nowMap.put("", "");
		// nowMap.put("uniqueId", "");
		// nowMap.put("curTime", "");
		// }
		// jsonObj.put("DataResult", "pass");
		// jsonObj.put("message", nowMap);
		// } catch (Exception e) {
		// System.out.println("关闭追踪模式异常...");
		// jsonObj.put("DataResult", "fail");
		// jsonObj.put("message", "关闭追踪模式异常..." + e.getMessage());
		// e.printStackTrace();
		// }
		out.print(jsonObj);
		// return new ModelAndView("trackPage", nowMap);
	}

	// 关闭追踪模式后台
	public String closeTrackStatus(String uniqueId, String typeID) {
		if ("G3".equals(typeID) || "G3B".equals(typeID) || "G3B" == typeID
				|| "G3" == typeID) {
			System.out.println("G3关闭追踪模式。。。。。。。。。。。。");
			String atInstruction = "AT+GTSTP=" + uniqueId + ",G3,,,,FFFD$";
			System.out.println("atInstruction=" + atInstruction);
			String isOnline = devicesService.findIsOnlinebyUniqueId(uniqueId);
			if ("1".equals(isOnline) || "1" == isOnline) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("uniqueId", uniqueId);
				jsonObj.put("transportType", "TCP");
				jsonObj.put("content", atInstruction);
				boolean result = QueueSend.produce(jsonObj.toString());
				System.out.println("回控指令发送结果" + result);
				if (result) {
					return "21";// 21 : 关闭追踪模式请求成功

				} else {
					return "22"; // 22 : 关闭追踪模式请求失败，请稍后重试
				}
			} else {
				System.out.println("设备不在线存抓擢列表。。。。。。。。。。。。");
				String saveCatchzhuolistResult = devicesService
						.saveCatchzhuolist(uniqueId, atInstruction,
								"closeTrackStatus");
				if ("1".equals(saveCatchzhuolistResult)) {
					return "23"; // 23 : 设备不在线存抓擢列表成功
				} else {
					return "24"; // 24 :　设备不在线存抓擢列表失败
				}
			}

		} else {
			System.out.println("GL300关闭追踪模式。。。。。。。。。。。。");
			String generalTime = devicesService
					.findGeneralTimebyUniqueId(uniqueId);
			String msg = generalTime + "," + generalTime + "," + generalTime
					+ "," + generalTime + ",";
			String atInstruction = "AT+GTFRI=gl300,1,0,,,0000,0000," + msg
					+ "1F,1000,1000,0,5,50,1,50,,FFFF$";
			System.out.println("atInstruction=" + atInstruction);
			String isOnline = devicesService.findIsOnlinebyUniqueId(uniqueId);
			if ("1".equals(isOnline) || "1" == isOnline) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("uniqueId", uniqueId);
				jsonObj.put("transportType", "TCP");
				jsonObj.put("content", atInstruction);
				boolean result = QueueSend.produce(jsonObj.toString());
				System.out.println("回控指令发送结果" + result);
				if (result) {
					return "21"; // 21 : 关闭追踪模式请求成功

				} else {
					return "22";// 22 : 关闭追踪模式请求失败，请稍后重试
				}
			} else {
				System.out.println("设备不在线存抓擢列表。。。。。。。。。。。。");
				String saveCatchzhuolistResult = devicesService
						.saveCatchzhuolist(uniqueId, atInstruction,
								"closeTrackStatus");
				if ("1".equals(saveCatchzhuolistResult)) {
					return "23";// 23 : 设备不在线存抓擢列表成功
				} else {
					return "24";// 24 :　设备不在线存抓擢列表失败
				}
			}

		}

	}

	/**
	 * 切换设备
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeDev")
	public void changeDev(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		Map<String, String> nowMap = new HashMap<String, String>();

		try {

			String companyId = getCompanyId(request, response);
			String uniqueId = request.getParameter("uniqueId");

			List<Map<String, Object>> list = devicesService.selectTrackDev(
					uniqueId, companyId);
			if (list != null && list.size() > 0) {
				// String nickName = (String) list.get(0).get("nickName");
				String curTime = (String) list.get(0).get("beginTime");

				if (curTime == null || curTime == "") {
					curTime = DateUtil.getCurrentDateTime();
				}
				nowMap.put("uniqueId", uniqueId);
				nowMap.put("curTime", curTime);
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", nowMap);
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "当前设备没有开启追踪模式");
			}
		} catch (Exception e) {
			System.out.println("切换设备异常：" + e.getMessage());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "切换设备异常");
		}

		// try {
		// String uniqueId = request.getParameter("uniqueId");
		//
		// List<Map<String, Object>> list =
		// devicesService.selDevByIdOrName(uniqueId, companyId);
		// //List<Object> devInfoList = (List<Object>)
		// request.getSession().getAttribute("devInfoList");
		//
		//
		// String curTime = "";
		//
		// if(!devInfoList.isEmpty()){
		// for(int j = 0; j<devInfoList.size();j++){
		// Map<String, Object> devInfoMap = (Map<String, Object>)
		// devInfoList.get(j);
		//
		// if(devInfoMap.containsKey(uniqueId)){
		// curTime = (String) devInfoMap.get(uniqueId);
		// break;
		// }else {
		// curTime = DateUtil.getCurrentDateTime();
		// }
		// }
		// nowMap.put("uniqueId", uniqueId);
		// nowMap.put("curTime", curTime);
		//
		// jsonObj.put("DataResult", "pass");
		// jsonObj.put("message", nowMap);
		// }else {
		// jsonObj.put("DataResult", "fail");
		// jsonObj.put("message", "没有开启追踪模式的设备");
		// }
		// } catch (Exception e) {
		// System.out.println("=================切换设备异常："+e.toString());
		// jsonObj.put("DataResult", "fail");
		// jsonObj.put("message", "切换设备异常");
		// }
		out.print(jsonObj);
	}

	/**
	 * 首页中进入追踪模式
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/toDevTrack")
	public ModelAndView toDevTrack(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);

		Map<String, Object> nowMap = new HashMap<String, Object>();
		String uniqueId = "";
		String curTime = "";
		try {
			System.out.println("==================从首页中进入追踪页面================");
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> devTrackList = devicesService
					.selAllDevTrack(companyId);

			if (devTrackList != null && devTrackList.size() > 0) {
				// 有开启追踪模式的设备
				uniqueId = (String) devTrackList.get(0).get("uniqueId");
				curTime = (String) devTrackList.get(0).get("beginTime");
				nowMap.put("uniqueId", uniqueId);
				nowMap.put("curTime", curTime);
			} else {
				// 没有开启追踪模式的设备
				nowMap.put("uniqueId", uniqueId);
				nowMap.put("curTime", curTime);
			}
		} catch (Exception e) {
			System.out.println("从首页进入追踪页面异常：" + e.getMessage());
		}

		// try {
		// System.out.println("从首页中进入追踪页面。。。。。。。。。。。。。。。。");
		// List<Object> devInfoList = (List<Object>) request.getSession()
		// .getAttribute("devInfoList");
		// if (devInfoList != null && devInfoList.size() > 0) {
		// for (int j = 0; j < devInfoList.size(); j++) {
		// Map<String, Object> devInfoMap = (Map<String, Object>) devInfoList
		// .get(j);
		// Set<Entry<String, Object>> devInfoSet = devInfoMap
		// .entrySet();
		//
		// for (Entry<String, Object> entry : devInfoSet) {
		// list.add(entry.getKey());
		// }
		//
		// if (list != null && list.size() > 0) {
		// uniqueId = (String) list.get(0);
		// curTime = (String) devInfoMap.get(uniqueId);
		// }
		// }
		//
		// // nowMap.put(uniqueId, curTime);
		// nowMap.put("uniqueId", uniqueId);
		// nowMap.put("curTime", curTime);
		// } else {
		// // nowMap.put("","");
		// nowMap.put("uniqueId", "");
		// nowMap.put("curTime", "");
		// }
		// } catch (Exception e) {
		//
		// System.out.println("从首页进入追踪页面异常：" + e.getMessage());
		// }

		return new ModelAndView("trackPage", nowMap);

	}
	
	/**
	 * 查询当前集团下有历史轨迹的设备
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selDevHasHistoryTrack")
	public void selDevHasHistoryTrack(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> list = devicesService.selDevHasHistoryTrack(companyId);
			
			if(list!=null && list.size()>0){
				System.out.println("当前集团下有保存历史轨迹的设备。。。。。。。。。。。。。。");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else {
				System.out.println("当前集团下没有已经保存历史轨迹的设备。。。。。。。。。。。。。。");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "");
			}
		} catch (Exception e) {
			System.out.println("查询当前集团下已经保存历史轨迹设备异常："+e.getMessage());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询已经保存历史轨迹的设备异常");
		}
		out.print(jsonObj);
	}
	

	/**
	 * 保存历史轨迹
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTrice")
	public void saveTrice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			String uniqueId = request.getParameter("uniqueId");

			String companyId = getCompanyId(request, response);
			if (beginTime == "" || beginTime == null) {
				beginTime = DateUtil.getCurrentDateTime();
			}
			if (endTime == "" || endTime == null) {
				endTime = DateUtil.getCurrentDateTime();
			}
			System.out.println("保存历史轨迹的设备串号=============：" + uniqueId);
			boolean flag = devicesService.saveTrice(beginTime, endTime,
					uniqueId,companyId);

			if (flag) {
				System.out.println("保存历史轨迹成功：" + uniqueId);
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "保存历史轨迹成功");
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("DataResult", "保存历史轨迹失败");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "保存历史轨迹异常");
		}
		out.print(jsonObj);

	}

	/**
	 * 查询历史轨迹
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/selHistoryTrace")
	public void selHistoryTrace(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			String uniqueId = request.getParameter("uniqueId");
			String companyId = getCompanyId(request, response);
			System.out.println("查询历史轨迹：=============" + uniqueId);
			List<Map<String, Object>> list = devicesService.selHistoryTrace(
					uniqueId, companyId);

			if (list != null && list.size() > 0) {
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
				System.out.println("找到============" + uniqueId
						+ "=================的历史轨迹");
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有找到该设备的历史轨迹");
				System.out.println("没有找到============" + uniqueId
						+ "=================的历史轨迹");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询设备历史轨迹异常");
			System.out.println("查询历史轨迹异常：" + e.toString());
		}
		out.print(jsonObj);
	}

	/**
	 * 根据记录id删除历史轨迹
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delHistoryTrace")
	public void delHistoryTrace(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			String id = request.getParameter("id");
			System.out
					.println("======================删除历史轨迹=======================");
			boolean flag = devicesService.delHistoryTrace(id);
			if (flag) {
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "删除成功");
				System.out
						.println("======================成功删除历史轨迹=======================");
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "删除失败");
				System.out.println("---------------删除历史轨迹失败---------------");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "删除异常");
			System.out.println("---------------删除历史轨迹异常：---------------"
					+ e.toString());
		}
		out.print(jsonObj);
	}

	/**
	 * 跳转到查看历史轨迹页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/toCheckHistoryTrace")
	public ModelAndView toCheckHistoryTrace(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String time = request.getParameter("time");
		String uniqueId = request.getParameter("uniqueId");
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", time);
		map.put("uniqueId", uniqueId);
		return new ModelAndView("historyMap", map);
	}

	/**
	 * 根据时间段查询历史轨迹 forHistoryId
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkHistoryTrace")
	public void checkHistoryTrace(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			String time = request.getParameter("time");
			String uniqueId = request.getParameter("uniqueId");

			String[] times = time.split("_");

			List<Map<String, Object>> list = devicesService.checkHistoryTrace(
					times[0], times[1], uniqueId);

			if (list != null && list.size() > 0) {
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "查询历史轨迹异常，没有找到对应的历史轨迹记录");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询历史轨迹异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 根据设备号或设备名称查询设备
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/selDevByIdOrName")
	public void selDevByIdOrName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			System.out.println("==========根据设备号或名称查询设备信息================");
			String nameOrId = request.getParameter("deviceName");
			String companyId = getCompanyId(request, response);

			List<Map<String, Object>> list = devicesService.selDevByIdOrName(
					nameOrId, companyId);

			if (list != null && list.size() > 0) {
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);

			} else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有找到符合条件的设备信息");
			}

		} catch (Exception e) {
			System.out.println("根据设备号或名称查询设备信息异常" + e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "根据设备号或名称查询设备信息异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 查询当前集团账号下所有设备数量
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/selTotalDevNum")
	public void selTotalDevNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();

		try {
			System.out.println("==========查询当前集团账号下所有的设备数量================");
			String companyId = getCompanyId(request, response);

			int count = devicesService.selTotalDevNum(companyId);

			jsonObj.put("DataResult", "pass");
			jsonObj.put("message", count);
		} catch (Exception e) {
			System.out.println("根据设备号或名称查询设备信息异常" + e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "0");
		}
		out.print(jsonObj);
	}

}
