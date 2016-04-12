package com.gnet.module.test.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.gnet.module.test.service.ITestService;
import com.gnet.utils.AxisUtil;
import com.gnet.utils.PropertiesUtil;
import com.gnet.utils.RestfulUtil;

@Controller
@RequestMapping("/testController")
public class TestController {
	@Resource(name = "testService")
	private ITestService testService;
	
	@RequestMapping(value = "/getTasks", method = RequestMethod.GET)
	public void getTasks(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try{
			String resus = RestfulUtil.sendHttpRequest("http://localhost:8080/framework/services/WebServiceRest/check", "123");
			System.out.println(resus);	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List<Map<String, Object>> _list = testService.getTaskAll();
		Object json = JSON.toJSON(_list);
		PrintWriter out = response.getWriter();
		out.print(json);
	}
	
	@RequestMapping(value = "/ajax", method = RequestMethod.GET)
	public void ajax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try{
			String nameSpace = "http://webService.cn.com/";//PropertiesUtil.getKeyValue("IBD_NAMESPACE"); 
			String url = "http://localhost:8080/gnet/services/WebService?wsdl";//PropertiesUtil.getKeyValue("IBD_URL"); 
			
			String param = "{\"GII_HEAD\":{\"VERSION\": \"PC1.0\",\"SNID\": \"\",\"SIGN\": [{\"position/k\": \"\"},{\"RandomCode/v\": \"\"}],\"GZIP\": \"fase\"},\"GII_IBD\":{\"PAGEINDEX\":\"\",\"PAGESIZE\":\"\",\"ACTION\":\"\",\"ISCOUNT\":\"\",\"REQUEST\":{\"CLOUD_ID\":\"123\",\"USER_ID\":\"123\",\"MANAGER_ID\":\"\",\"INDUSTRY_ID\":\"123\",\"CLOUD_TYPE\":\"0\",\"APPLYMODE\":\"\",\"STATUS\":\"\",\"KEYWORDS\":\"\"}}}";

			String result = AxisUtil.callService(url, nameSpace, "check", "strJson", param);			
			System.out.println(result);	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List<Map<String, Object>> _list = testService.getTaskAll();
		Object json = JSON.toJSON(_list);
		PrintWriter out = response.getWriter();
		out.print(json);
	}
	
	@RequestMapping(value = "/updateTest", method = RequestMethod.POST)
	public void updateTest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try{
			testService.updateTest();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}

}
