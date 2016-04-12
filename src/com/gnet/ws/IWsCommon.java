package com.gnet.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@WebService(name = "ICommonService",targetNamespace="http://webService.cn.com/")
public interface IWsCommon {
	
	@WebMethod(operationName="check", action="http://webService.cn.com/check")
	@POST
	@Path("/check/")
	@Produces({ MediaType.TEXT_PLAIN })
	public String check(@WebParam(name = "strJson") @FormParam(value = "strJson") String psJson);
	
	
}
