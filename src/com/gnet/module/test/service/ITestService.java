package com.gnet.module.test.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.SchedulerException;


public interface ITestService {

	//获取数据库所有任务
	public List<Map<String, Object>> getTaskAll();
	public void updateTest() throws Exception;
}
