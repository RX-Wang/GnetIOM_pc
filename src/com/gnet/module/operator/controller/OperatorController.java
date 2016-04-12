package com.gnet.module.operator.controller;

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
import com.gnet.module.operator.service.IOperatorService;


@Controller
@RequestMapping("/operatorController")
public class OperatorController {
	
	
	@Resource(name = "operatorService")
	private IOperatorService operatorService;
	
	@Resource(name = "logService")
	private ILogService logService;
	
	
	/**
	 * 查询操作员代码是否可用
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkOpCodeIsExist")
	public void checkOpCodeIsExist(HttpServletRequest request, HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String opCode = request.getParameter("opCode");
			String companyId = getCompanyId(request, response);
			if (companyId!=null && companyId!="") {
				int count = operatorService.checkOpCodeIsExist(opCode,companyId);
				if(count == 1){
					System.out.println("操作员账号已被占用");
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "操作员账号已被占用");
				}else{
					System.out.println("操作员账号可用");
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", "操作员账号可用");
				}
			}else{
				System.out.println("当前没有集团账号登录");
			}
		} catch (Exception e) {
			System.out.println("查询操作员账号是否可用异常"+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询操作员账号是否可用异常");
		}
		out.print(jsonObj);
	}
	
	/**
	 *  添加操作员
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOperator")
	public void addOperator(HttpServletRequest request,HttpServletResponse response)throws Exception{		
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String opCode = request.getParameter("opCode");
			String opPwd = request.getParameter("opPwd");
			String opName = request.getParameter("opName");
			String opRule = request.getParameter("opRule");
			String companyId = getCompanyId(request, response);
			
			List<Map<String, Object>> list = getOperatorInfo(request, response);
			String opName1 = (String) list.get(0).get("opName");
			String opCode1 = (String) list.get(0).get("opCode");
			
			boolean flag = operatorService.addOperator(opCode,opPwd,opName,opRule,companyId,opName1,opCode1);
			
			if(flag){
				System.out.println("添加操作员成功");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "添加操作员成功");
			}else{
				System.out.println("添加操作员失败");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "添加操作员失败");
			}
		} catch (Exception e) {
			System.out.println("添加操作员异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "添加操作员异常");
		}
		out.print(jsonObj);
	}
	
	/**
	 * 修改操作员信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateOperator")
	public void updateOperator(HttpServletRequest request, HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String id = request.getParameter("id");
			String opCode = request.getParameter("opCode");
//			String opCode = request.getParameter("id");
//			String opPwd = request.getParameter("opPwd");
			String opName = request.getParameter("opName");
			String opRule = request.getParameter("opRule");
			
			List<Map<String, Object>> list1 = getOperatorInfo(request, response);
			String opName2 = (String) list1.get(0).get("opName");
			String opCode2 = (String) list1.get(0).get("opCode");
			String companyId = getCompanyId(request, response);
			boolean flag = operatorService.updateOperator(id,opCode,opName,opRule,opName2,opCode2,companyId);
			
			if(flag){
					// 将用户信息存放到会话中
					List<Map<String, Object>> oInfo =  (List<Map<String, Object>>) request.getSession().getAttribute(
							"operatorInfo");
					
					if (oInfo != null && oInfo.size()>0) {
						String opCode1 = (String) oInfo.get(0).get("opCode");
						if(opCode.equals(opCode1)){
							List<Map<String, Object>> list = selOperatorByOpCode(opCode);
							request.getSession().removeAttribute("operatorInfo");
							request.getSession().setAttribute("operatorInfo",list);
						}
						System.out.println("修改操作员信息成功");
						jsonObj.put("DataResult", "pass");
						jsonObj.put("message", "修改成功");
					}
//					System.out.println("查询操作员信息异常");
//					jsonObj.put("DataResult", "fail");
//					jsonObj.put("message", "查询操作员信息异常");
			}else{
				System.out.println("修改操作员信息失败");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "修改失败");
			}
		} catch (Exception e) {
			System.out.println("修改操作员信息异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "修改异常");
		}
		out.print(jsonObj);
	}
	
	/**
	 * 删除操作员(标记删除)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delOperator")
	public void delOperator(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String id = request.getParameter("id");
			String opCode = request.getParameter("opCode");
			
			
			List<Map<String, Object>> list = getOperatorInfo(request, response);
			String opName1 = (String) list.get(0).get("opName");
			String opCode1 = (String) list.get(0).get("opCode");
			String companyId = getCompanyId(request, response);
			
			boolean flag = operatorService.delOperator(id,opCode,opName1,opCode1,companyId);
			
			if(flag){
				System.out.println("删除操作员成功");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", "删除成功");
			}else{
				System.out.println("删除失败");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "删除失败");
			}
		} catch (Exception e) {
			System.out.println("删除操作员异常："+ e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "删除操作员异常");
		}
		out.print(jsonObj);
	}
	
	/**
	 * 根据操作员账号查询操作员信息
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	public List<Map<String, Object>> selOperatorByOpCode(String opCode)throws Exception{
		
		List<Map<String, Object>> list = operatorService.selOperatorByOpCode(opCode);
		
		if(list != null && list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 查询当前集团下所有操作员信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/selAllOperatorInfo")
	public void selAllOperatorInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String companyId = getCompanyId(request, response);
			List<Map<String, Object>> list = operatorService.selAllOperatorInfo(companyId);
			
			if(list != null && list.size()>0){
				System.out.println("查询所有操作信息成功");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else{
				System.out.println("没有操作员信息");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有操作员信息");
			}
		} catch (Exception e) {
			System.out.println("查询操作员信息异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "查询所有操作员信息异常");
		}
		out.print(jsonObj);
	}
	
	
	/**
	 * 根据操作员名字或账号查询操作员信息
	 * @param request
	 * @param response
	 * @throws Exception
	 * select id,opCode,opName,opRule,date_format(opDate,'%Y-%c-%d %k:%i:%s') as opDate,opFlag from tab_operator where companyId = ? and opFlag = '1'
	 */
	@RequestMapping(value = "/selectOperatorByCondition")
	public void selectOperatorByCondition(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		try {
			String opNameOrCode = request.getParameter("opNameOrCode"); 
			String companyId = getCompanyId(request, response);
			String sql = "select id,opCode,opName,opRule,date_format(opDate,'%Y-%c-%d %k:%i:%s') as opDate,opFlag from tab_operator where companyId = ? and opFlag = '1' and 1=1 ";
			if(opNameOrCode!="" && opNameOrCode!=null){
				sql += " and opCode = '"+opNameOrCode+"' or opName = '"+opNameOrCode+"'";
			}
			
			
			List<Map<String, Object>> list = operatorService.selOperatorByOpName(sql,companyId);
			
			if(list != null && list.size()>0){
				System.out.println("根据名称或账号查询操作员信息成功");
				jsonObj.put("DataResult", "pass");
				jsonObj.put("message", list);
			}else{
				System.out.println("没有符合条件的操作员信息");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "没有符合条件的操作员信息");
			}
		} catch (Exception e) {
			System.out.println("查询异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "根据名称或账号查询操作员信息异常");
		}
		out.print(jsonObj);
	}
	
	/**
	 * 修改操作员密码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateOpPwd")
	public void updateOpPwd(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		try {
			String opCode = request.getParameter("opCode");
			String opPwd = request.getParameter("newPwd");
			String companyId = getCompanyId(request, response);
			boolean flag = operatorService.updateOpPwd(opCode,opPwd,companyId);
			
			if(flag){
				List<Map<String, Object>> list = selOperatorByOpCode(opCode);
				if(list != null && list.size()>0){
					// 将用户信息存放到会话中
					List<Map<String, Object>> oInfo =  (List<Map<String, Object>>) request.getSession().getAttribute(
							"operatorInfo");
					if (oInfo == null) {
						request.getSession().setAttribute("operatorInfo",
								list);
					} else {
						request.getSession()
								.removeAttribute("operatorInfo");
						request.getSession().setAttribute("operatorInfo",
								list);
					}
					System.out.println("修改操作员密码成功");
					jsonObj.put("DataResult", "pass");
					jsonObj.put("message", "修改成功");
				}else{
					System.out.println("查询操作员信息异常");
					jsonObj.put("DataResult", "fail");
					jsonObj.put("message", "查询操作员信息异常");
				}
			}else{
				System.out.println("修改操作员密码失败");
				jsonObj.put("DataResult", "fail");
				jsonObj.put("message", "修改失败");
			}
		} catch (Exception e) {
			System.out.println("修改密码异常："+e.toString());
			jsonObj.put("DataResult", "fail");
			jsonObj.put("message", "修改密码异常");
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
			return "";
		}
		
		String companyId = companyInfo.getString("companyId");
		if(!companyId.equals("") && null != companyId){
			return companyId;
		}else{
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
			System.out.println("operatorInfo:" + operatorInfo);
			return operatorInfo;
		} else {
			return null;
		}
	}
	
	
	
	
}
