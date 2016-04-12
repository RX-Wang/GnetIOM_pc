package com.gnet.module.group.dao;

import java.util.List;
import java.util.Map;

public interface IGroupDao {

	
	/**
	 * 查询所有目录
	 * @return
	 */
	List<Map<String, Object>> selAllMenu(String companyId);

	/**
	 * 查询所有一级目录
	 * @return
	 */
	List<Map<String, Object>> selOneMenu(String companyId);

	/**
	 * 删除分组
	 * @param groupId
	 * @return
	 */
	boolean delGroup(String groupId);

	/**
	 * 修改分组名称
	 * @param groupId
	 * @return
	 */
	boolean updateGroup(String groupId,String newName);

	/**
	 * 添加分组
	 * @param groupId
	 * @param groupName
	 * @return
	 */
	boolean addGroup(String id,String groupId, String groupName,String companyId);

	/**
	 * 查询当前分组下的设备数量
	 * @param groupId
	 * @return
	 */
	int selDevNum(String groupId);

	/**
	 * 查询子节点
	 * @param groupId
	 * @return
	 */
	List<Map<String, Object>> selChildGroup(String groupId);

	/**
	 * 在保存分组前查询当前分组下是否有重名的分组
	 * @param groupId
	 * @param groupName
	 * @return
	 */
	boolean selGroupIsExist(String groupId, String groupName,String companyId);

	/**
	 *  查询分组下的设备
	 * @param groupId
	 * @return
	 */
	List<Map<String, Object>> selDevTheGroup(String groupId);

	/**
	 * 根据分组id查询分组名称
	 * @param id
	 * @return
	 */
	String selGroupNameById(String id);

	/**
	 * 查询分组下的设备数量
	 * @param id
	 * @return
	 */
	int selGroupDevNum(String id);

}
