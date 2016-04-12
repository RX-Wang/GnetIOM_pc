package com.gnet.module.system;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gnet.common.Constants;
import com.gnet.module.log.service.ILogService;
import com.gnet.module.user.service.IUserService;

@Controller
@RequestMapping("/sysController")
public class SysController {

	@Resource(name = "logService")
	private ILogService logService;
	
	@Resource(name = "userService")
	private IUserService userService;
	
	/**
	 * 锁定系统
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/lockSys")
	public void lockSys(HttpServletRequest request, HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		List<Map<String, Object>> oInfo =  (List<Map<String, Object>>) request.getSession().getAttribute(
				"operatorInfo");
	
		if (oInfo == null) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "没有用户登录");
		} else {
			String companyId = getCompanyId(request, response);
			String opName = (String) oInfo.get(0).get("opName");
			String opCode = (String) oInfo.get(0).get("opCode");
			
//			userService.updateLasLoginTime(opName);
			// 用户登录成功后更新最后一次退出系统时间
			int count = userService.updateLastQuitTime(opCode,companyId);
//			int count = userService.updateLasLoginTime(opCode);
			if(count>0){
				System.out.println("===========锁屏时更新最后一次退出时间成功=============");
			}else {
				System.out.println("===========锁屏时更新最后一次退出时间失败=============");
			}
			
			request.getSession()
					.removeAttribute("operatorInfo");
			logService.addLog("Operator", Constants.LOCK_SYSTEM, opName, opCode,companyId);
			
			jsonObj.put("DataResult", "pass");
			jsonObj.put("message", "注销成功");
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
			return "NOCOMPANYINFO";
		}

		String companyId = companyInfo.getString("companyId");
		if (!companyId.equals("") && null != companyId) {
			return companyId;
		} else {
			return "";
		}
	}
	
	
	
	
	
	
	
}
