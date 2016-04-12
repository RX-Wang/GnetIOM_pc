package com.gnet.module.test.dao;

import java.util.List;
import java.util.Map;


public interface ITestDao {
	//修改方法
	public void updateTaskExpressionById(String jobId,String expression) throws Exception;

	//获取所有定时任务
	public List<Map<String,Object>> getTaskAll(String id);
	
}