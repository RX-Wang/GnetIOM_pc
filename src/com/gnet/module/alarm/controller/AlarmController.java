package com.gnet.module.alarm.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gnet.common.Constants;
import com.gnet.module.alarm.service.IAlarmService;

@Controller
@RequestMapping("/alarmController")
public class AlarmController {

	@Resource(name = "alarmService")
	private IAlarmService alarmService;
	
	/**
	 * 查询所有消息，包含所有已处理，未处理的消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/findAllMsg")
	public void findAllMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String companyId = getCompanyId(request, response);
			
			if(companyId!=null && companyId!=""){
				List<Map<String, Object>> list = alarmService.findAllMsg(companyId);
				if(list!=null &&list.size()>0){
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", list);
					jsonObj.put("total", list.size());
				}else{
					jsonObj.put("DataResult", "nodata");
					jsonObj.put("message", "没有记录");
					jsonObj.put("total", "0");
				}
			}else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有集团账号登录");
				jsonObj.put("total", "0");
			}
		} catch (Exception e) {
			System.out.println("查询所有消息异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询异常");
			jsonObj.put("total", "0");
		}
		out.print(jsonObj);
	}
	
	/**
	 * 查询所有未处理的消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/findAllUntreatedMsg")
	public void findAllUntreatedMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> list = alarmService.findAllUntreatedMsg(companyId);
			if(list!=null && list.size()>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else {
				jsonObj.put("DataResult", "nodata");
				jsonObj.put("message", "没有记录");
			}
		} catch (Exception e) {
			System.out.println("查询所有未处理的消息异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询所有未处理的消息异常");
			e.printStackTrace();
		}
		out.print(jsonObj);
	}
	
	
	/**
	 * 查询所有未处理的消息的数目
	 */
	@RequestMapping(value="/findAllUntreatedNum",method=RequestMethod.POST)
	public void findAllUntreatedNum(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String companyId = getCompanyId(request, response);
			int count = alarmService.findAllUntreatedNum(companyId);
			
			jsonObj.put("DataResult", "pass");
			jsonObj.put("message", count);
		} catch (Exception e) {
			System.out.println("查询所有未处理的消息的数目异常 ："+e.toString());
			e.printStackTrace();
		}
		out.print(jsonObj);
	}
	
	/**
	 * 查询所有未处理的报警消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/findAlarmMsg",method=RequestMethod.POST)
	public void findAlarmMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> list = alarmService.findAlarmMsg(companyId);
			
			if(list!=null && list.size()>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else {
				jsonObj.put("DataResult", "nodata");
				jsonObj.put("message", "没有记录");
			}
		} catch (Exception e) {
			System.out.println("查询所有报警消息异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询异常");
			e.printStackTrace();
		}
		out.print(jsonObj);
	}
	
	/**
	 *查询所有未处理的报警消息条数 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/findAlarmMsgNum",method=RequestMethod.POST)
	public void findAlarmMsgNum(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String companyId = getCompanyId(request, response);
			int count = alarmService.findAlarmMsgNum(companyId);
			
			jsonObj.put("DataResult", "pass");
			jsonObj.put("message", count);
		} catch (Exception e) {
			System.out.println("查询所有未处理的报警消息条数异常 ："+e.toString());
			e.printStackTrace();
		}
		out.print(jsonObj);
	}
	
	/**
	 * 查询所有未处理的事件消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/findThingMsg")
	public void findThingMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> list = alarmService.findThingMsg(companyId);
			if(list!=null && list.size()>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else{
				jsonObj.put("DataResult", "nodata");
				jsonObj.put("message", "没有记录");
			}
		} catch (Exception e) {
			System.out.println("查询所有未处理的事件消息异常："+e.toString());
			e.printStackTrace();
		}
		out.print(jsonObj);
	}
	
	/**
	 * 查询所有未处理的事件消息的总条数
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/findThingMsgNum",method=RequestMethod.POST)
	public void findThingMsgNum(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String companyId = getCompanyId(request, response);
			int count = alarmService.findThingMsgNum(companyId);
			
			jsonObj.put("DataResult", "pass");
			jsonObj.put("message", count);
		} catch (Exception e) {
			System.out.println("查询所有未处理的事件消息的总条数异常："+e.toString());
			e.printStackTrace();
		}
		out.print(jsonObj);
	}
	
	/**
	 * 查询所有未处理的故障消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/findFaultMsg")
	public void findFaultMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> list = alarmService.findFaultMsg(companyId);
			if(list!=null && list.size()>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else{
				jsonObj.put("DataResult", "nodata");
				jsonObj.put("message", "没有记录");
			}
		} catch (Exception e) {
			System.out.println("查询所有未处理的故障消息异常："+e.toString());
			e.printStackTrace();
		}
		out.print(jsonObj);
	}
	
	/**
	 * 查询所有未处理的故障消息总条数
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/findFaultMsgNum",method=RequestMethod.POST)
	public void findFaultMsgNum(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String companyId = getCompanyId(request, response);
			int count = alarmService.findFaultMsgNum(companyId);
			
			jsonObj.put("DataResult", "pass");
			jsonObj.put("message", count);
		} catch (Exception e) {
			System.out.println("查询所有未处理的故障消息总条数异常："+e.toString());
			e.printStackTrace();
		}
		out.print(jsonObj);
	}
	
	/**
	 * 处理消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/dealWithMsg")
	public void dealWithMsg(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String id = request.getParameter("id");
			
			boolean flag = alarmService.dealWithMsg(id);
			
			if(flag){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "处理成功");
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "处理失败");
			}
		} catch (Exception e) {
			System.out.println("处理异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "处理异常");
		}
		
		out.print(jsonObj);
		
	}
	
	
	/**
	 * 忽略
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/ignoreMsg")
	public void ignoreMsg(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String id = request.getParameter("id");
			
			boolean flag = alarmService.ignoreMsg(id);
			
			if(flag){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "忽略成功");
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "忽略失败");
			}
			
		} catch (Exception e) {
			System.out.println("忽略异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "忽略异常");
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
	
}
