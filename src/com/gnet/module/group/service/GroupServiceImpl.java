package com.gnet.module.group.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnet.module.group.dao.IGroupDao;

@Service("groupService")
public class GroupServiceImpl implements IGroupService {

	@Resource(name = "groupDao")
	private IGroupDao groupDao;

	
	/**
	 * 查询所有目录
	 */
	@Override
	public List<Map<String, Object>> selAllMenu(String companyId) {
		return groupDao.selAllMenu(companyId);
	}


	/**
	 * 查询所有一级目录
	 */
	@Override
	public List<Map<String, Object>> selOneMenu(String companyId) {
		return groupDao.selOneMenu(companyId);
	}


	/**
	 * 删除分组
	 */
	@Override
	public boolean delGroup(String groupId) {
		return groupDao.delGroup(groupId);
	}


	/**
	 * 修改分组名称
	 */
	@Override
	public boolean updateGroup(String groupId, String newName) {
		return groupDao.updateGroup(groupId,newName);
	}


	/**
	 * 添加分组
	 */
	@Override
	public boolean addGroup(String id,String groupId, String groupName,String companyId) {
		return groupDao.addGroup(id,groupId,groupName,companyId);
	}


	/**
	 * 查询当前分组下的设备数量
	 */
	@Override
	public int selDevNum(String groupId) {
		return groupDao.selDevNum(groupId);
	}


	/**
	 * 查询子节点
	 */
	@Override
	public List<Map<String, Object>> selChildGroup(String groupId) {
		return groupDao.selChildGroup(groupId);
	}


	/**
	 * 在保存分组前查询当前分组下是否有重名的分组
	 */
	@Override
	public boolean selGroupIsExist(String groupId, String groupName,String companyId) {
		return groupDao.selGroupIsExist(groupId,groupName,companyId);
	}


	/**
	 * 查询分组下的设备
	 */
	@Override
	public List<Map<String, Object>> selDevTheGroup(String groupId) {
		return groupDao.selDevTheGroup(groupId);
	}


	/**
	 * 根据分组id查询分组名称
	 */
	@Override
	public String selGroupNameById(String id) {
		return groupDao.selGroupNameById(id);
	}


	/**
	 * 查询分组下的设备数量
	 */
	@Override
	public int selGroupDevNum(String id) {
		return groupDao.selGroupDevNum(id);
	}
}
