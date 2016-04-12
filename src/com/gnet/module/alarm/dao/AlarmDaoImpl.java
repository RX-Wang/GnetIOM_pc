package com.gnet.module.alarm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value="alarmDao")
public class AlarmDaoImpl implements IAlarmDao {

	protected final Logger logger = Logger.getLogger(getClass());
	
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;


	/**
	 * 查询所有消息，包含已处理和未处理的
	 */
	@Override
	public List findAllMsg(String companyId) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			list = jdbcTemplate.queryForList("select date_format(sendTime,'%Y-%c-%d %k:%i:%s') as sendTime,id,devicesName,uniqueId,alarmType,type,address,longitude,latitude,handle,alarmTypeMsg from tab_message where uniqueId in(select us.uniqueId from tab_user_devices us where us.userName = ?) order by sendTime desc",new Object[]{companyId});
		} catch (Exception e) {
			logger.error("查询所有消息异常"+e.toString());
			return null;
		}
		return list;
		
	}


	/**
	 * 查询所有未处理的消息总条数
	 */
	@Override
	public int findAllUntreatedNum(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int count = 0;
		
		try {
			
			list = jdbcTemplate.queryForList("select count(*) from tab_message where handle='0' and uniqueId in(select us.uniqueId from tab_user_devices us where us.userName = ?)",new Object[]{companyId});
			String tempCount = String.valueOf(list.get(0).get("count(*)"));
			count = (tempCount==null)? 0 : Integer.parseInt(tempCount);
			
		} catch (Exception e) {
			logger.error("查询未处理消息总条数异常"+e.toString());
			count = 0;
		}
		return count;
	}


	/**
	 * 查询所有报警消息
	 */

	public List<Map<String, Object>> findAlarmMsg(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
//			list = jdbcTemplate.queryForList("select * from tab_message where alarmType = '报警'");
			list = jdbcTemplate.queryForList("select date_format(sendTime,'%Y-%c-%d %k:%i:%s') as sendTime,id,devicesName,uniqueId,alarmType,type,address,longitude,latitude,handle,alarmTypeMsg from tab_message where alarmType = '报警' and uniqueId in(select us.uniqueId from tab_user_devices us where us.userName = ?) order by sendTime desc",new Object[]{companyId});
		} catch (Exception e) {
			logger.error("查询未处理的报警消息异常"+e.toString());
			return null;
		}
		return list;
	}


	/**
	 * 查询所有的未处理的报警消息总条数
	 */
	@Override
	public int findAlarmMsgNum(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int count = 0;
		
		try {
			
			list = jdbcTemplate.queryForList("select count(*) from tab_message where alarmType='报警' and handle='0' and uniqueId in(select us.uniqueId from tab_user_devices us where us.userName = ?)",new Object[]{companyId});
			String tempCount = String.valueOf(list.get(0).get("count(*)"));
			count = (tempCount==null)? 0 : Integer.parseInt(tempCount);
			
		} catch (Exception e) {
			logger.error("查询未处理的报警消息总条数异常"+e.toString());
			count = 0;
		}
		return count;
	}


	/**
	 * 查询所有的事件消息
	 */
	@Override
	public List findThingMsg(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			
//			list = jdbcTemplate.queryForList("select * from tab_message where alarmType='事件'");
			list = jdbcTemplate.queryForList("select date_format(sendTime,'%Y-%c-%d %k:%i:%s') as sendTime,id,devicesName,uniqueId,alarmType,type,address,longitude,latitude,handle,alarmTypeMsg from tab_message where alarmType='事件' and uniqueId in(select us.uniqueId from tab_user_devices us where us.userName = ?) order by sendTime desc",new Object[]{companyId});
		} catch (Exception e) {
			logger.error("查询未处理的事件消息异常"+e.toString());
			return null;
		}
		return list;
	}


	/**
	 * 查询所有未处理的事件消息的总条数
	 */
	@Override
	public int findThingMsgNum(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int count = 0;
		
		try {
			
			list = jdbcTemplate.queryForList("select count(*) from tab_message where alarmType='事件' and handle='0' and uniqueId in(select us.uniqueId from tab_user_devices us where us.userName = ?)",new Object[]{companyId});
			String tempCount = String.valueOf(list.get(0).get("count(*)"));
			count = (tempCount==null)? 0 : Integer.parseInt(tempCount);
			
		} catch (Exception e) {
			logger.error("查询未处理的事件消息的总条数异常"+e.toString());
			count = 0;
		}
		return count;
	}


	/**
	 * 查询所有故障消息
	 */
	@Override
	public List findFaultMsg(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			
//			list = jdbcTemplate.queryForList("select * from tab_message where alarmType='故障'");
			list = jdbcTemplate.queryForList("select date_format(sendTime,'%Y-%c-%d %k:%i:%s') as sendTime,id,devicesName,uniqueId,alarmType,type,address,longitude,latitude,handle,alarmTypeMsg from tab_message where alarmType='故障' and uniqueId in(select us.uniqueId from tab_user_devices us where us.userName = ?) order by sendTime desc",new Object[]{companyId});
		} catch (Exception e) {
			logger.error("查询未处理的故障消息异常"+e.toString());
			return null;
		}
		return list;
	}


	/**
	 * 查询所有未处理的故障消息的总条数
	 */
	@Override
	public int findFaultMsgNum(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int count = 0;
		
		try {
			
			list = jdbcTemplate.queryForList("select count(*) from tab_message where alarmType='故障' and handle='0' and uniqueId in(select us.uniqueId from tab_user_devices us where us.userName = ?)",companyId);
			String tempCount = String.valueOf(list.get(0).get("count(*)"));
			count = (tempCount==null)? 0 : Integer.parseInt(tempCount);
			
		} catch (Exception e) {
			logger.error("查询未未处理的故障消息的总条数异常"+e.toString());
			count = 0;
		}
		return count;
	}


	/**
	 * 查询所有未处理的消息
	 */
	@Override
	public List<Map<String, Object>> findAllUntreatedMsg(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			
			list = jdbcTemplate.queryForList("select date_format(sendTime,'%Y-%c-%d %k:%i:%s') as sendTime,id,devicesName,uniqueId,alarmType,type,address,longitude,latitude,handle,alarmTypeMsg from tab_message where handle='0' and uniqueId in(select us.uniqueId from tab_user_devices us where us.userName = ?) order by sendTime desc",new Object[]{companyId});
		} catch (Exception e) {
			logger.error("查询所有未处理的消息异常"+e.toString());
			return null;
		}
		return list;
	}


	/**
	 * 处理消息
	 */
	@Override
	public boolean dealWithMsg(String id) {
		boolean flag = false;
		try {
			int count = jdbcTemplate.update("update tab_message set handle = '1' where id = ?", new Object[]{id});
			if(count>0){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("忽略消息异常："+e.toString());
		}
		return flag;
	}


	/**
	 * 忽略消息
	 */
	@Override
	public boolean ignoreMsg(String id) {
		boolean flag = false;
		try {
			int count = jdbcTemplate.update("update tab_message set handle = '2' where id = ? ", new Object[]{id});
			if(count>0){
				flag = true;
			}
		} catch (DataAccessException e) {
			logger.error("忽略消息异常："+e.toString());
		}
		return flag;
	}
}
