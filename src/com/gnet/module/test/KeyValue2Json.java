package com.gnet.module.test;

import java.util.Iterator;

import net.sf.json.JSONObject;

public class KeyValue2Json {

	public static void main(String[] args) {
		String data = "{id=4566542248551, opCode=gyd, opPwd=123456, opName=苟亚东, opRule=超级管理员, opDate=2015-11-23 15:31:56.0, opLastQuitTime=null, opLastLoginTime=2015-11-23 15:31:24.0, opFlag=1}";
		JSONObject object = JSONObject.fromObject(data);
		
		Iterator it = object.keys();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
		
	}
}
