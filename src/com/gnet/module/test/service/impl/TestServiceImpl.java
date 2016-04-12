package com.gnet.module.test.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnet.module.test.dao.ITestDao;
import com.gnet.module.test.service.ITestService;


@Service(value = "testService")
public class TestServiceImpl implements ITestService {

	 private static Logger logger = Logger.getLogger(TestServiceImpl.class);
	
	@Resource(name = "testDao")
	private ITestDao testDao;

	//获取数据库所有任务
	public List<Map<String, Object>> getTaskAll() {
		String s = null;
		try{
			if(s.equals("")){
				
			}
		}catch(Exception e){
			logger.error("This is info message.");
		}
		return testDao.getTaskAll("id");
	}
	
	@Transactional(rollbackFor = { Exception.class })
	public void updateTest() throws Exception {
		testDao.updateTaskExpressionById("", "");
		testDao.updateTaskExpressionById("", "");
	}
	
}
