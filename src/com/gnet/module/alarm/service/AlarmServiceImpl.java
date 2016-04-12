package com.gnet.module.alarm.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnet.module.alarm.dao.IAlarmDao;

@Service("alarmService")
public class AlarmServiceImpl implements IAlarmService {

	@Resource(name = "alarmDao")
	private IAlarmDao alarmDao;

	/**
	 * 查询所有消息，包含已处理和未处理
	 */
	@Override
	public List findAllMsg(String companyId) {
		return alarmDao.findAllMsg(companyId);
	}

	/**
	 * 查询所有未处理的消息的总条数
	 */
	@Override
	public int findAllUntreatedNum(String companyId) {
		return alarmDao.findAllUntreatedNum(companyId);
	}

	/**
	 * 查询所有未处理的报警信息
	 */
	@Override
	public List findAlarmMsg(String companyId) {
		return alarmDao.findAlarmMsg(companyId);
	}

	/**
	 * 查询所有未处理的报警信息的总条数
	 */
	@Override
	public int findAlarmMsgNum(String companyId) {
		return alarmDao.findAlarmMsgNum(companyId);
	}

	/**
	 * 查询所有未处理的事件消息
	 */
	@Override
	public List findThingMsg(String companyId) {
		return alarmDao.findThingMsg(companyId);
	}

	/**
	 * 查询所有未处理的事件消息的总条数
	 */
	@Override
	public int findThingMsgNum(String companyId) {
		return alarmDao.findThingMsgNum(companyId);
	}

	/**
	 * 查询所有未处理的故障消息
	 */
	@Override
	public List findFaultMsg(String companyId) {
		return alarmDao.findFaultMsg(companyId);
	}

	/**
	 * 查询所有未处理的故障消息的的总条数
	 */
	@Override
	public int findFaultMsgNum(String companyId) {
		return alarmDao.findFaultMsgNum(companyId);
	}

	/**
	 * 查询所有未处理的消息
	 */
	@Override
	public List<Map<String, Object>> findAllUntreatedMsg(String companyId) {
		return alarmDao.findAllUntreatedMsg(companyId);
	}

	/**
	 * 处理消息
	 */
	@Override
	public boolean dealWithMsg(String id) {
		return alarmDao.dealWithMsg(id);
	}

	/**
	 * 忽略消息
	 */
	@Override
	public boolean ignoreMsg(String id) {
		return alarmDao.ignoreMsg(id);
	}
}
