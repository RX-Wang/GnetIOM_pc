package com.gnet.module.log.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gnet.module.log.dao.ILogDao;


@Service("logService")
public class LogServiceImpl implements ILogService {

	protected final Logger logger = Logger.getLogger(LogServiceImpl.class);
	
	
	
	@Resource(name = "logDao")
	private ILogDao logDao;



	/**
	 * 添加日志
	 */
	@Override
	public boolean addLog(String eventType, String description, String opName,
			String opCode,String companyId) {
		return logDao.addLog(eventType,description,opName,opCode,companyId);
	}



	/**
	 * 查询日志
	 */
	@Override
	public List<Map<String, Object>> selectLog(String companyId) {
		return logDao.selectLog(companyId);
	}



	/**
	 * 根据条件查询日志记录
	 */
	@Override
	public List<Map<String, Object>> selectLogByCondition(String sql,
			String companyId) {
		return logDao.selectLogByCondition(sql,companyId);
	}



	/**
	 * 查询所有用户
	 */
	@Override
	public List<Map<String, Object>> selectAllUser(String companyId) {
		return logDao.selectAllUser(companyId);
	}



	/**
	 * 查询所有事件
	 */
	@Override
	public List<Map<String, Object>> selectAllThings(String companyId) {
		return logDao.selectAllThings(companyId);
	}



	/**
	 * 根据条件查询事件
	 */
	@Override
	public List<Map<String, Object>> selectThingsByCondition(String sql,
			String companyId) {
		return logDao.selectThingsByCondition(sql,companyId);
	}



	/**
	 * 根据条件查询用户
	 */
	@Override
	public List<Map<String, Object>> selectUsersByCondition(String sql,
			String companyId) {
		return logDao.selectUsersByCondition(sql,companyId);
	}
}
