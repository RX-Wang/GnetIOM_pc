package com.gnet.module.group.controller;

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
import com.gnet.module.group.service.IGroupService;
import com.gnet.utils.UUIDUtil;

/**
 * 分组controller
 * @author admin
 *
 */


@Controller
@RequestMapping("/groupController")
public class GroupController {

	@Resource(name = "groupService")
	private IGroupService groupService;
	
	/**
	 * 查询第一级目录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selOneGroup")
	public void selOneGroup(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> list = groupService.selOneMenu(companyId);
			
			if(list.size()>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else {
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "没有目录");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "pass");
			jsonObj.put("message", "");
		}
		out.print(jsonObj);
	}
	
	
	/**
	 * 查询所有目录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selAllGroup")
	public void selAllGroup(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> list = groupService.selAllMenu(companyId);
			
			if(list.size()>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有目录");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "");
		}
		out.print(jsonObj);
	}
	
	
	/**
	 * 添加分组
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addGroup")
	public void addGroup(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String companyId = getCompanyId(request, response);
			String groupId = request.getParameter("groupId");
			String groupName = request.getParameter("groupName");
			String id = UUIDUtil.getUUID();
			
			
			boolean flag = groupService.addGroup(id,groupId,groupName,companyId);
			
			if(flag){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "添加分组成功");
				jsonObj.put("id", id);
				
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "添加分组失败");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "添加分组异常");
		}
		out.print(jsonObj);
	}
	
	/**
	 * 删除分组
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="delGroup")
	public void delGroup(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String groupId = request.getParameter("groupId");
			boolean flag = groupService.delGroup(groupId);
			if(flag){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "删除成功");
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "删除失败");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "删除异常");
		}
		out.print(jsonObj);
	}
	
	/**
	 * 修改分组名称
	 */
	@RequestMapping(value="/updateGroup")
	public void updateGroup(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String groupId = request.getParameter("groupId");
			String newGroupName = request.getParameter("newName");
			boolean flag = groupService.updateGroup(groupId,newGroupName);
			if(flag){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "修改组名称成功");
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "修改组名称失败");
			}
		} catch (Exception e) {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "修改组名称异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 查询分组下的设备数量
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selDevNum")
	public void selDevNum(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		request.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String groupId = request.getParameter("groupId");
			
			int devCount = groupService.selDevNum(groupId);
			
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	
	/**
	 * 查询当前分组下的设备数量
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selGroupDevNum")
	public void selGroupDevNum(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String id = request.getParameter("groupId");
			
			int num = groupService.selGroupDevNum(id);
			if(num>0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", num);
			}else if(num==0){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "0");
			}else{
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "查询失败");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询分组下的设备数量异常");
		}
		out.print(jsonObj);
		
	}
	
	/**
	 * 查询子节点
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selChildGroup")
	public void selChildGroup(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String groupId = request.getParameter("groupId");
			//查询分组下的设备
			List<Map<String, Object>> deviceList = groupService.selDevTheGroup(groupId);
			if(deviceList!= null && deviceList.size()>0){
				System.out.println("有设备");
				jsonObj.put("Result", "hasdev");
				jsonObj.put("msg", deviceList);
			}else{
				System.out.println("当前分组下没有设备");
				jsonObj.put("Result", "nodev");
				jsonObj.put("msg", "当前分组下无设备");
			}
			//查询子分组
			List<Map<String, Object>> list = groupService.selChildGroup(groupId);
			if(list != null && list.size()>0){
				System.out.println("查询子节点成功");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else {
				System.out.println("没有子节点");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "当前节点没有子节点");
			}
		} catch (Exception e) {
			System.out.println("查询子节点异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询子节点异常");
		}
		out.print(jsonObj);
	}
	
	/**
	 * 在保存分组前查询当前分组下是否有重名的分组
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selGroupIsExist")
	public void selGroupIsExist( HttpServletRequest request ,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String groupId = request.getParameter("groupId");
			String groupName = request.getParameter("groupName");
			String companyId = getCompanyId(request, response);
			
			boolean flag = groupService.selGroupIsExist(groupId,groupName,companyId);
			
			if(flag){
				jsonObj.put("DataResult", "hasexist");
				jsonObj.put("message", "当前分组下已存在该分组");
			}else{
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "当前分组下不存在该组，可以添加");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询分组是否可用异常");
		}
		out.print(jsonObj);
	}

	/**
	 * 根据分组id查询分组名称
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/selGroupNameById")
	public void selGroupNameById(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String id = request.getParameter("id");
			
			String groupName = groupService. selGroupNameById(id);
			if(!groupName.equals("") && null != groupName){
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", groupName);
			}else {
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "");
			}
		} catch (Exception e) {
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "");
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
