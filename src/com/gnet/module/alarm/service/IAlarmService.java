package com.gnet.module.alarm.service;

import java.util.List;
import java.util.Map;

public interface IAlarmService {
	/**
	 * 查询所有消息(包含已处理和未处理)
	 * @return
	 */
	List findAllMsg(String companyId);

	/**
	 * 查询所有未处理的消息总条数
	 * @return
	 */
	int findAllUntreatedNum(String companyId);

	/**
	 * 查询所有未处理的报警消息
	 * @return
	 */
	List findAlarmMsg(String companyId);

	/**
	 * 查询所有未处理的报警消息条数 
	 * @return
	 */
	int findAlarmMsgNum(String companyId);

	/**
	 * 查询所有为处理的事件消息
	 * @return
	 */
	List findThingMsg(String companyId);

	/**
	 * 查询所有未处理的事件消息的总条数
	 * @return
	 */
	int findThingMsgNum(String companyId);

	/**
	 * 查询所有未处理的故障消息
	 * @return
	 */
	List findFaultMsg(String companyId);

	/**
	 * 查询所有未处理的故障消息总条数
	 * @return
	 */
	int findFaultMsgNum(String companyId);

	/**
	 * 查询所有未处理的消息
	 * @return
	 */
	List<Map<String, Object>> findAllUntreatedMsg(String companyId);

	/**
	 * 处理消息
	 * @param id
	 * @return
	 */
	boolean dealWithMsg(String id);

	/**
	 * 忽略消息
	 * @param id
	 * @return
	 */
	boolean ignoreMsg(String id);
}
