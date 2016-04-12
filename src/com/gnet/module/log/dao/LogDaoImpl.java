package com.gnet.module.log.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.xml.SqlXmlValue;
import org.springframework.stereotype.Repository;

import com.gnet.utils.DateUtil;
import com.gnet.utils.UUIDUtil;


@Repository(value = "logDao")
public class LogDaoImpl implements ILogDao {

	protected final Logger logger = Logger.getLogger(LogDaoImpl.class);

	@Resource
	private JdbcTemplate jdbcTemplate;

	/**
	 * 添加日志
	 */
	@Override
	public boolean addLog(String eventType, String description, String opName,
			String opCode,String companyId) {
		boolean flag = false;
		try {
			int count = jdbcTemplate.update("insert into tab_log values(?,?,?,?,?,?,?,?)", new Object[]{UUIDUtil.getUUID(),DateUtil.getCurrentDate(),DateUtil.getCurrentTime(),eventType,description,opName,opCode,companyId});
			if(count>0){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("添加日志异常："+e.toString());
		}
		return flag;
	}

	/**
	 * 查询日志
	 */
	@Override
	public List<Map<String, Object>> selectLog(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			list = jdbcTemplate.queryForList("select id, date_format(createdate,'%Y-%c-%d') as createdate,date_format(createTime,'%H:%i:%s') as createTime,eventType,description,opName,opCode from tab_log where companyId = ? order by createdate desc,createTime desc", new Object[]{companyId});
			
		} catch (Exception e) {
			logger.error("查询日志异常："+e.toString());
		}
		return list;
	}

	/**
	 * 根据条件查询日志记录
	 */
	@Override
	public List<Map<String, Object>> selectLogByCondition(String sql,
			String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList(sql, new Object[]{companyId});
		} catch (Exception e) {
			logger.error("根据条件查询日志记录异常："+e.toString());
		}
		return list;
	}

	/**
	 * 查询所有用户
	 */
	@Override
	public List<Map<String, Object>> selectAllUser(String companyId) {
		List< Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			String sql = "select us.id,us.userName,us.nickName,us.userPhone,us.userType,us.groupId,us.userAddress,us.description,us.binderType,us.uniqueId,us.devicesSecCode,us.simNumber,us.devicesTypeId,us.permission_sign,us.devicesState,us.linkman,date_format(us.addDate,'%Y-%m-%d %H:%i:%s') as addDate,g.groupName from tab_user_devices us,tab_group g where us.groupId=g.id and us.companyId = ? order by us.addDate desc";
			list = jdbcTemplate.queryForList(sql, new Object[]{companyId});
			
		} catch (Exception e) {
			logger.error("查询所有用户记录异常："+e.toString());
		}
		return list;
	}

	/**
	 * 查询所有事件
	 */
	@Override
	public List<Map<String, Object>> selectAllThings(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			String sql = "select id,devicesName,uniqueId,alarmType,type,address,date_format(sendTime,'%Y-%m-%d %H:%i:%s') as sendTime,longitude,latitude,handle,alarmTypeMsg from tab_message where uniqueId in (select us.uniqueId from tab_user_devices us where us.userName = ? ) order by sendTime desc";
			list = jdbcTemplate.queryForList(sql, new Object[]{companyId});
			
		} catch (Exception e) {
			logger.error("查询所有事件记录异常："+e.toString());
		}
		return list;
	}

	/**
	 * 根据条件查询事件
	 */
	@Override
	public List<Map<String, Object>> selectThingsByCondition(String sql,
			String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList(sql, new Object[]{companyId});
		} catch (Exception e) {
			logger.error("根据条件查询事件记录异常："+e.toString());
		}
		return list;
	}

	/**
	 * 根据条件查询用户
	 */
	@Override
	public List<Map<String, Object>> selectUsersByCondition(String sql,
			String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList(sql, new Object[]{companyId});
		} catch (Exception e) {
			logger.error("根据条件查询用户记录异常："+e.toString());
		}
		return list;
	}
}
