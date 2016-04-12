package com.gnet.module.log.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gnet.common.Constants;
import com.gnet.module.log.service.ILogService;

@Controller
@RequestMapping("/logController")
public class LogController {
	protected final Logger logger = Logger.getLogger(LogController.class);
	
	@Resource(name = "logService")
	private ILogService logService;
	
	
	
	/**
	 * 查询日志
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selectLog")
	public void selectLog(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			
			String companyId = getCompanyId(request, response);
			if(companyId!=null && companyId !=""){
				List<Map<String, Object>> list = logService.selectLog(companyId);
				if (list !=null && list.size()>0) {
					logger.info("查询日志成功");
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", list);
					System.out.println("查询日志成功");
				}else {
					logger.info("没有查询到相关的日志");
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "没有查询到相关的日志");
					System.out.println("没有查询到相关的日志");
				}
			}else{
				System.out.println("没有集团账号登录");
			}
		} catch (Exception e) {
			System.out.println("查询日志异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询日志异常");
			logger.error("查询日志异常："+e.toString());
		}
		out.print(jsonObj);
	}
	
	/**
	 * 根据条件查询日志
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selectLogByCondition")
	public void selectLogByCondition(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String _isAllDate = request.getParameter("_isAllDate");//0未选中  1选中
			String _isAllUsers = request.getParameter("_isAllUsers");//0未选中  1选中
			
			String companyId = getCompanyId(request, response);
			String sql = "select id, date_format(createdate,'%Y-%c-%d') as createdate,date_format(createTime,'%H:%i:%s') as createTime,eventType,description,opName,opCode from tab_log where companyId = ? and 1=1";
			
			if(_isAllDate.equals("0")){
				String _startDate = request.getParameter("_startDate");
				String _endDate = request.getParameter("_endDate");
				if((_startDate !=null && _startDate!="")&&(_endDate!=null && _endDate!="")){
					String[] strs = _startDate.split("/");
					String startDate = strs[2]+"-"+strs[1]+"-"+strs[0];
					String[] split = _endDate.split("/");
					String endDate = split[2]+"-"+split[1]+"-"+split[0];
					
					sql += " and createdate between '"+startDate+"' and '"+endDate+"' ";
				}
			}
			if(_isAllUsers.equals("0")){
				String _sysLogUser = request.getParameter("_sysLogUser");
				if(_sysLogUser!="" && _sysLogUser!=null){
					sql += " and opName='"+_sysLogUser+"'";
				}
			}
			sql+=" order by createdate desc,createTime desc";
			
			List<Map<String, Object>> list = logService.selectLogByCondition(sql,companyId);
			if(list!=null && list.size()>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有找到符合条件的记录");
			}
		} catch (Exception e) {
			System.out.println("根据条件查询记录异常："+e.toString());
			logger.error("根据条件查询记录异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "根据条件查询记录异常");
		}
		out.print(jsonObj);
	}
	
	/**
	 * 查询所有事件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selectAllThings")
	public void selectAllThings(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String companyId = getCompanyId(request, response);
			
			List<Map<String, Object>> list = logService.selectAllThings(companyId);
			if(list!=null && list.size()>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有找到相关的记录");
			}
		} catch (Exception e) {
			System.out.println("查询所有事件记录异常："+e.toString());
			logger.error("查询所有事件记录异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询所有事件记录异常");
		}
		out.print(jsonObj);
	}
	
	
	/**
	 * 根据条件查询事件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selectThingsByCondition")
	public void selectThingsByCondition(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String _isAllDate = request.getParameter("_isAllDate");//0未选中  1选中
//			String _isAllUsers = request.getParameter("_isAllUsers");//0未选中  1选中
			String _DeviceName = request.getParameter("_DeviceName");//设备名称
			String _DeviceType = request.getParameter("_DeviceType");//报警类型
			
			String companyId = getCompanyId(request, response);
			String sql = "select id,devicesName,uniqueId,alarmType,type,address,date_format(sendTime,'%Y-%m-%d %H:%i:%s') as sendTime,longitude,latitude,handle,alarmTypeMsg from tab_message where uniqueId in (select us.uniqueId from tab_user_devices us where us.userName = ? ) and 1=1 ";
			
			if(_isAllDate.equals("0")){
				String _startDate = request.getParameter("_startDate");
				String _endDate = request.getParameter("_endDate");
				if((_startDate !=null && _startDate!="")&&(_endDate!=null && _endDate!="")){
					String[] strs = _startDate.split("/");
					String startDate = strs[2]+"-"+strs[1]+"-"+strs[0]+" 00:00:00";
					String[] split = _endDate.split("/");
					String endDate = split[2]+"-"+split[1]+"-"+split[0]+" 23:59:59";
					
					sql += " and  (sendTime between '"+startDate+"' and '"+endDate+"') ";
				}
			}
			if(_DeviceName!="" && _DeviceName!=null){
				sql += " and devicesName='"+_DeviceName+"'";
			}
			
			if(_DeviceType!="" && _DeviceType!=null){
				sql += " and alarmTypeMsg='"+_DeviceType+"'";
			}
			sql+=" order by sendTime desc";
			
			List<Map<String, Object>> list = logService.selectThingsByCondition(sql,companyId);
			if(list!=null && list.size()>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有找到符合条件的记录");
			}
		} catch (Exception e) {
			System.out.println("根据条件查询记录异常："+e.toString());
			logger.error("根据条件查询记录异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "根据条件查询记录异常");
		}
		out.print(jsonObj);
		
	}


	
	/**
	 * 查询所有的用户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selectAllUser")
	public void selectAllUser(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String companyId = getCompanyId(request, response);
			
			List<Map<String, Object>> list = logService.selectAllUser(companyId);
			
			if(list!=null && list.size()>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有找到相关的记录");
			}
		} catch (Exception e) {
			System.out.println("查询所有用户异常："+e.toString());
			logger.error("查询所有用户异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询所有用户异常");
		}
		out.print(jsonObj);
	}
	
	
	/**
	 * 根据条件查询用户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selectUsersByCondition")
	public void selectUsersByCondition(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String _isAllDate =request.getParameter("_isAllDate"); 
			
			String userMenuName =request.getParameter("userMenuName"); 
			String userMenuType =request.getParameter("userMenuType"); 
			String userMenuTel =request.getParameter("userMenuTel"); 
			String userMenuAddr =request.getParameter("userMenuAdd"); 
			String companyId = getCompanyId(request, response);
			String sql = "select us.id,us.userName,us.nickName,us.userPhone,us.userType,us.groupId,us.userAddress,us.description,us.binderType,us.uniqueId,us.devicesSecCode,us.simNumber,us.devicesTypeId,us.permission_sign,us.devicesState,us.linkman,date_format(us.addDate,'%Y-%m-%d %H:%i:%s') as addDate,g.groupName from tab_user_devices us,tab_group g where us.groupId=g.id and us.companyId = ? and 1=1 ";
			
			if(_isAllDate.equals("0")){
				String _startDate =request.getParameter("_startDate"); 
				String _endDate =request.getParameter("_endDate"); 
				if((_startDate !=null && _startDate!="")&&(_endDate!=null && _endDate!="")){
					String[] strs = _startDate.split("/");
					String startDate = strs[2]+"-"+strs[1]+"-"+strs[0]+" 00:00:00";
					String[] split = _endDate.split("/");
					String endDate = split[2]+"-"+split[1]+"-"+split[0]+" 23:59:59";
					
					sql += " and us.addDate between '"+startDate+"' and '"+endDate+"' ";
				}
			}
			
			if(userMenuName!="" && userMenuName!=null){
				sql += " and us.nickName = '"+userMenuName+"'";
			}
			
			if(userMenuType !="" && userMenuType!=null){
				sql += " and us.userType = '"+userMenuType+"'";
			}
			
			if(userMenuTel!="" && userMenuTel!=null){
				sql += " and us.userPhone = '"+userMenuTel+"'";
			}
			
			if(userMenuAddr !="" && userMenuAddr!=null){
				sql += " and us.userAddress = '"+userMenuAddr+"'";
			}
			
			sql+=" order by us.addDate desc";
			
			List<Map<String, Object>> list = logService.selectUsersByCondition(sql,companyId);
			
			if(list!=null && list.size()>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有找到相关的记录");
			}
		} catch (Exception e) {
			System.out.println("根据条件查询用户异常："+e.toString());
			logger.error("根据条件查询用户异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "根据条件查询用户异常");
		}
		out.print(jsonObj);
	}
	
	
	
	
	/**
	 * 获取会话中保存的集团账号
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getCompanyId(HttpServletRequest request,HttpServletResponse response)throws Exception{
//		String companyId = "";
		JSONObject companyInfo = (JSONObject) request.getSession().getAttribute("companyInfo");
		if(companyInfo==null || companyInfo.equals("")){
			return "NOCOMPANYINFO";
		}
		
		String companyId = companyInfo.getString("companyId");
		if(!companyId.equals("") && null != companyId){
			return companyId;
		}else{
			return "";
		}
	}
	
}
