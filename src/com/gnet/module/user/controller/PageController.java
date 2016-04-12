package com.gnet.module.user.controller;


import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gnet.common.Constants;
import com.gnet.module.log.service.ILogService;
import com.gnet.module.user.service.IUserService;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pageController")
public class PageController {
	
	@Resource(name = "userService")
	private IUserService userService;
	
	@Resource(name = "logService")
	private ILogService logService;
	
	/**
	 * 
	 * @Title: userLogin 
	 * @Description: 跳转到登录页面
	 * @param @param request
	 * @param @param response
	 * @param @return    设定文件 
	 * @return ModelAndView    返回类型 
	 * @throws
	 */
	@RequestMapping(value ="/loginPage")
	public ModelAndView loginPage(HttpServletRequest request,HttpServletResponse response){
//		System.out.println("跳转到登录页面!");
		return new ModelAndView("login");
	}
	
	/**
	 * Description:跳转至注册页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/registerPage")
	public ModelAndView register(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		return new ModelAndView("register");
	}
	
	@RequestMapping(value="/toIndex")
	public ModelAndView toIndex(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		return new ModelAndView("index");
	}
	
	/**去设备页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toDevice")
	public ModelAndView toDevice(HttpServletRequest request , HttpServletResponse response) throws Exception{
		
//		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uniqueId = request.getParameter("uniqueId");
			
			if(uniqueId!=null && uniqueId!=""){
//				map.put("uniqueId", uniqueId);
//				map.put("isCheck", "check");
				request.getSession().setAttribute("uniqueId", uniqueId);
			}else{
//				map.put("uniqueId", "");
//				map.put("isCheck", "noCheck");
			}
		} catch (Exception e) {
			System.out.println("跳转到设备页面异常："+e.toString());
		}
		
		return new ModelAndView("device");
	}
	/**从地图页面去设备页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toDeviceFromMap")
	public ModelAndView toDeviceFromMap(HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uniqueId = request.getParameter("uniqueId");
			
			if(uniqueId!=null && uniqueId!=""){
				map.put("uniqueId", uniqueId);
				map.put("isCheck", "check");
			}else{
				map.put("uniqueId", "");
				map.put("isCheck", "noCheck");
			}
		} catch (Exception e) {
			System.out.println("跳转到设备页面异常："+e.toString());
		}
		
		return new ModelAndView("device",map);
	}
	
	/**
	 * 跳转到地图页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toMap")
	public ModelAndView toMap(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView("map");
	}
	/**
	 * 跳转到系统设置页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toSystem")
	public ModelAndView toSystem(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView("system");
	}
	
	
	/**
	 * 跳转到全部消息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAllMsg")
	public ModelAndView toAllMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView("allmsg");
	}
	
	/**
	 * 跳转到报警消息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAlarm")
	public ModelAndView toAlarm(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView("alarm");
	}
	
	
	
	/**
	 * 跳转到事件消息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toThing")
	public ModelAndView toThing(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView("things");
	}
	
	/**
	 * 跳转到故障消息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toFault")
	public ModelAndView toFault(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView("fault");
	}

	/**
	 * 锁屏弹出层
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lockPage")
	public ModelAndView lockPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView("lockPage");
	}
	/**
	 * 查看单个历史查询页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/historyMap")
	public ModelAndView historyMap(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String _id = request.getParameter("_id");
		Map<String, String> map = new HashMap<String, String>();
		map.put("_id", _id);
		return new ModelAndView("historyMap",map);
	}
	/**
	 * 跳转到统计页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toStatistics")
	public ModelAndView toStatistics(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return new ModelAndView("statistics");
	}
	
	
	/**
	 * 获取会话中保存的集团账号
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getCompanyInfo")
	public void getCompanyId(HttpServletRequest request,HttpServletResponse response)throws Exception{
//		String companyId = "";
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			JSONObject companyInfo = (JSONObject) request.getSession().getAttribute("companyInfo");
			if(companyInfo!=null && !companyInfo.equals("")){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "集团账号已经登录");
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "当前没有集团账号登录");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询是否有集团账号登录异常");
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
	public String getCompanyId1(HttpServletRequest request,
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
	
	/**
	 * 注销(集团用户与操作员)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response)throws Exception{
		List<Map<String, Object>> oInfo = (List<Map<String, Object>>) request
				.getSession().getAttribute("operatorInfo");
		JSONObject cInfo = (JSONObject) request.getSession()
				.getAttribute("companyInfo");
		if (oInfo != null && oInfo.size()>0) {
			String companyId = getCompanyId1(request, response);
			String opName = (String) oInfo.get(0).get("opName");
			String opCode = (String) oInfo.get(0).get("opCode");
			
			// 用户登录成功后更新最后一次登录系统时间
			int count = userService.updateLastQuitTime(opCode,companyId);
			if(count>0){
				System.out.println("===========注销时更新最后一次退出系统时间成功=============");
			}else {
				System.out.println("===========注销时更新最后一次退出系统时间失败=============");
			}
			
			logService.addLog("Operator", Constants.LOGOUT_SYSTEM, opName, opCode,companyId);
			request.getSession()
					.removeAttribute("operatorInfo");
		}
		if (null != cInfo && cInfo.size()>0) {
			request.getSession().removeAttribute("companyInfo");
		}
		return new ModelAndView("login");
	}
	
}
