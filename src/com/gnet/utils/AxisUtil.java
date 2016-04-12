package com.gnet.utils;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;

/**
 * 利用axis客户端调用测试webService
 */
public class AxisUtil {

	/**
	 * 
	 * @param endpoint
	 * 				接口地址
	 * @param nameSpace
	 * 				命名空间(如果为空，则默认为http://tempuri.org/)
	 * @param methodName
	 * 				方法名
	 * @param paramNames
	 * 				参数名 如：{"arg0","arg1"}
	 * @param paramValues
	 * 				参数值 如：{"value0","value1"}
	 * @return
	 * 			返回值为String类型
	 */
	public static String callService(String endpoint,String nameSpace,String methodName,String[] paramNames,Object[] paramValues){
		
		if(!nameSpace.endsWith("/")){
			nameSpace += "/";
		}
		org.apache.axis.client.Service service = new org.apache.axis.client.Service(); 
		Call call = null;
		try {
			call = (Call)service.createCall();
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		try {
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		call.setUseSOAPAction(true); 
		call.setSOAPActionURI(nameSpace+methodName); //设置调用的方法名 
		call.setOperationName(new javax.xml.namespace.QName(nameSpace, methodName));
		
		if(endpoint.endsWith(".asmx")){
			if(paramNames.length>0){
				for(int i=0;i< paramNames.length;i++){
					call.addParameter(new javax.xml.namespace.QName(nameSpace, paramNames[i]), XMLType.XSD_STRING,ParameterMode.IN);
				}
			}
		}else if(endpoint.endsWith("?wsdl")){
			if(paramNames.length>0){
				for(int i=0;i< paramNames.length;i++){
					call.addParameter(new javax.xml.namespace.QName( paramNames[i]), XMLType.XSD_STRING,ParameterMode.IN);
				}
			}
		}else{
			if(paramNames.length>0){
				for(int i=0;i< paramNames.length;i++){
					call.addParameter(new javax.xml.namespace.QName(nameSpace, paramNames[i]), XMLType.XSD_STRING,ParameterMode.IN);
				}
			}
		}
		
		
		call.setReturnType(XMLType.XSD_STRING); 
		try {
			Object[] objs =null;
			if(paramValues.length>0){
				objs =  new Object[paramValues.length];
				for(int i =0;i<paramValues.length;i++){
					objs[i]=paramValues[i];
				}
			}
			return (String) call.invoke(objs);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;	
	}
	/**
	 * 
	 * @param endpoint
	 * 				接口地址
	 * @param nameSpace
	 * 				命名空间(如果为空，则默认为http://tempuri.org/)
	 * @param methodName
	 * 				方法名
	 * @param paramName
	 * 				参数名 如：{"arg0","arg1"}
	 * @param paramValue
	 * 				参数值 如：{"value0","value1"}
	 * @return
	 * 			返回值为String类型
	 */
	/**
	 * 
	 * @param endpoint
	 * 				接口地址
	 * @param nameSpace
	 * 				命名空间(如果为空，则默认为http://tempuri.org/)
	 * @param methodName
	 * 				方法名
	 * @param paramName
	 * 				参数名 如："JsonString"
	 * @param paramValue
	 * 				参数值 如："[{\"Version\":\"WB1.0\",\"SNID\":\"20120309195611068\",\"Sign\":\"da32rr43re\"}]"
	 * @return
	 * 			返回值为String类型
	 * @throws Exception
	 */
	public static String callService(String endpoint,String nameSpace,String methodName,String paramName,String paramValue) throws Exception{
		
		if(!nameSpace.endsWith("/")){
			nameSpace += "/";
		}
		org.apache.axis.client.Service service = new org.apache.axis.client.Service(); 
		Call call = null;
		call = (Call)service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setUseSOAPAction(true); 
		call.setSOAPActionURI(nameSpace+methodName); //设置调用的方法名 
		call.setOperationName(new javax.xml.namespace.QName(nameSpace, methodName));
		if(endpoint.endsWith(".asmx")){//.net接口
			call.addParameter(new javax.xml.namespace.QName(nameSpace, paramName ), XMLType.XSD_STRING,ParameterMode.IN);
		}else if(endpoint.endsWith("?wsdl")){//java接口
			call.addParameter(new javax.xml.namespace.QName(paramName ), XMLType.XSD_STRING,ParameterMode.IN);
		}else{//其他接口
			call.addParameter(new javax.xml.namespace.QName(nameSpace, paramName ), XMLType.XSD_STRING,ParameterMode.IN);
		}
		call.setReturnType(XMLType.XSD_STRING); 
		try {
			return (String) call.invoke(new Object[]{paramValue});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
}
