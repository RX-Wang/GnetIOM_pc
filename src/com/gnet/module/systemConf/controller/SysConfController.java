package com.gnet.module.systemConf.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.gnet.common.Constants;

@Controller
@RequestMapping("/SysConfController")
public class SysConfController {
	/**文件上传
	 * @param request
	 * @param response
	 * @param companyPicFile
	 * @param softLogoFile
	 */
	@RequestMapping(value="uploadFile",method=RequestMethod.POST)
	public void uploadFile(HttpServletRequest request,
							HttpServletResponse response,
							@RequestParam(value="companyPic",required=false)MultipartFile companyPicFile,
							@RequestParam(value="softLogo",required=false)MultipartFile softLogoFile){
		MultipartFile file = null;
		if(null!=companyPicFile){
			file = companyPicFile;
		}else if(null!=softLogoFile){
			file = softLogoFile;
		}
        /**构建图片保存的目录**/    
        String savePath = "/img/";     
        /**图片保存目录的真实路径,用于创建文件夹目录**/
        String savePathRoot = request.getSession().getServletContext().getRealPath(savePath);     
        /**根据真实路径创建目录**/    
        File logoSaveFile = new File(savePathRoot);     
        if(!logoSaveFile.exists())     
            logoSaveFile.mkdirs();           
        /**获取文件的后缀**/
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFileName = com.gnet.utils.UUIDUtil.getUUID() + suffix;
        /**拼成完整的文件保存路径加文件**/    
        String fileName = savePathRoot + File.separator   + newFileName;                
        File Newfile = new File(fileName);          
        StringBuffer buff = new StringBuffer();
        //用于存在数据库和返回前台的路径
        String forJsonPath = savePath + newFileName;
        buff.append("{\"success\":\"true\",\"filePath\":\""+forJsonPath+"\"}");
        Object json = JSON.toJSON(buff.toString());
        PrintWriter out = null;
        try {
        	file.transferTo(Newfile);
        	out = response.getWriter();
			out.print(json);
        } catch (IllegalStateException e) {
            e.printStackTrace();     
        } catch (IOException e) {
            e.printStackTrace();     
        }finally{
        	out.close();
        }
        System.out.println("文件上传路径-----" + fileName);
	}
	
	/**保存数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="sysCofgSave",method=RequestMethod.POST)
	public void sysCofgSave(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding(Constants.ENCODE);
			response.setCharacterEncoding(Constants.ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String companyName = request.getParameter("companyName");
		String companyInterAddr = request.getParameter("companyInterAddr");
		String softName = request.getParameter("softName");
		String companyAddr = request.getParameter("companyAddr");
		String companyDescription = request.getParameter("companyDescription");
		String companyPhone = request.getParameter("companyPhone");
		String companyPic = request.getParameter("companyPic");
		String softLogo = request.getParameter("softLogo");
		System.out.println("companyName:"+companyName +
				";companyInterAddr:"+companyInterAddr +
				";softName:"+softName +
				";companyAddr:"+companyAddr +
				";companyDescription:"+companyDescription +
				";companyPhone:"+companyPhone +
				";companyPic:"+companyPic + ";softLogo:"+softLogo);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(JSON.toJSON(new StringBuffer("{\"success\":\"true\"}").toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**保存操作员
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="saveOperator",method=RequestMethod.POST)
	public void saveOperator(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding(Constants.ENCODE);
			response.setCharacterEncoding(Constants.ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String opCode = request.getParameter("opCode");
		String opPwd = request.getParameter("opPwd");
		String opName = request.getParameter("opName");
		String opRule = request.getParameter("opRule");
		System.out.println("opCode:"+opCode +
				";opPwd:"+opPwd +
				";opName:"+opName +
				";opRule:"+opRule);
		PrintWriter out = null; 
		try {
			out = response.getWriter();
			out.print(JSON.toJSON(new StringBuffer("{\"success\":\"true\"}").toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**删除操作员
	 * @param request
	 * @param response
	 */
	@RequestMapping("delOperator")
	public void delOperator(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding(Constants.ENCODE);
			response.setCharacterEncoding(Constants.ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String opId = request.getParameter("id");
		System.out.println("opId:" + opId);
		PrintWriter out = null; 
		try {
			out = response.getWriter();
			out.print(JSON.toJSON(new StringBuffer("{\"success\":\"true\"}").toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**修改操作员
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="modiOperator",method=RequestMethod.POST)
	public void modiOperator(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding(Constants.ENCODE);
			response.setCharacterEncoding(Constants.ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String opId = request.getParameter("id");//被修改的记录员的ID
		String opName = request.getParameter("opName");//被修改的记录员的姓名
		String opRule = request.getParameter("opRule");//被修改的记录员的角色
		System.out.println("opId:" + opId +
				"----opName:" + opName +
				"----opRule:" + opRule );
		PrintWriter out = null; 
		try {
			out = response.getWriter();
			out.print(JSON.toJSON(new StringBuffer("{\"success\":\"true\"}").toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**判断操作员账号是否唯一
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="opCodeIsOnly",method=RequestMethod.POST)
	public void opCodeIsOnly(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding(Constants.ENCODE);
			response.setCharacterEncoding(Constants.ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String opCode = request.getParameter("opCode");//操作员账号
		System.out.println("opCode:" + opCode );
		PrintWriter out = null; 
		try {
			out = response.getWriter();
			//如果可用：success:true
			//如果不可用：success：false
			out.print(JSON.toJSON(new StringBuffer("{\"success\":true}").toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**获取当前登录操作员的账户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="getManagerOpCode",method=RequestMethod.GET)
	public void getManagerOpCode(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding(Constants.ENCODE);
			response.setCharacterEncoding(Constants.ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//从session中拿到 当前登录的操作员的账号
		request.getSession().getAttribute("operatorInfo");
	//	String opCode = request.getParameter("opCode");//操作员账号
		PrintWriter out = null; 
		try {
			out = response.getWriter();
			out.print(JSON.toJSON(new StringBuffer("{\"success\":true}").toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**判断当前登录操作员输入的旧密码是否正确
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="oldOpCodeIsRight")
	public void oldOpCodeIsRight(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding(Constants.ENCODE);
			response.setCharacterEncoding(Constants.ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			String opCode = request.getParameter("opCode");//操作员账号
			String opPwd = request.getParameter("opPwd");//旧密码
			System.out.println("opCode:" + opCode + "---opPwd:" + opPwd);
		PrintWriter out = null; 
		try {
			out = response.getWriter();
			//如果旧密码正确返回：success：true,反之返回：success:false
			out.print(JSON.toJSON(new StringBuffer("{\"success\":true}").toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**修改当前登录操作员的密码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="modifyOpPwd")
	public void modifyOpPwd(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding(Constants.ENCODE);
			response.setCharacterEncoding(Constants.ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String opCode = request.getParameter("opCode");//操作员账号
		String opPwd = request.getParameter("opPwd");//新密码
		System.out.println("opCode:" + opCode + "---opPwd:" + opPwd);
		PrintWriter out = null; 
		try {
			out = response.getWriter();
			out.print(JSON.toJSON(new StringBuffer("{\"success\":true}").toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
















