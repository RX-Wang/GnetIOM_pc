package com.gnet.module.log.service;

import java.util.List;
import java.util.Map;

public interface ILogService {

	/**
	 * 添加日志
	 * @param eventType
	 * @param description
	 * @param opName
	 * @param opCode
	 * @return
	 */
	public boolean addLog(String eventType,String description,String opName,String opCode,String companyId);

	/**
	 * 查询日志
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> selectLog(String companyId);

	/**
	 * 根据条件查询记录
	 * @param sql
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> selectLogByCondition(String sql,
			String companyId);

	/**
	 * 查询所有的用户
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> selectAllUser(String companyId);

	/**
	 * 查询所有事件
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> selectAllThings(String companyId);

	/**
	 * 根据条件查询事件
	 * @param sql
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> selectThingsByCondition(String sql,
			String companyId);

	/**
	 * 根据条件查询用户
	 * @param sql
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> selectUsersByCondition(String sql,
			String companyId);
	
	
}
