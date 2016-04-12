package com.gnet.module.test.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;




import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gnet.module.test.dao.ITestDao;

@Repository(value = "testDao")
public class TestDaoImpl implements ITestDao {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	//修改方法
	public void updateTaskExpressionById(String jobId,String expression) throws Exception {
		jdbcTemplate.update("update task_job set cron_expression = ? where job_id = ? ", new Object[] {expression,jobId});
	}
	
	//查询方法
	public List<Map<String, Object>> getTaskAll(String id) {
		List<Map<String, Object>> list = new ArrayList();
		list = jdbcTemplate.queryForList("select * from task_job",new Object[] {});
		return list;
	}


}
