package com.gnet.module.group.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.Flags;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value="groupDao")
public class GroupDaoImpl implements IGroupDao {

	protected final Logger logger = Logger.getLogger(getClass());
	
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;


	/**
	 * 查询所有目录
	 */
	@Override
	public List<Map<String, Object>> selAllMenu(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			
			list = jdbcTemplate.queryForList("select * from tab_group where companyId = ?",new Object[]{companyId});
		} catch (Exception e) {
			logger.error("查询所有目录异常："+e.toString());
			return null;
		}
		return list;
	}


	/**
	 * 查询所有一级目录
	 */
	@Override
	public List<Map<String, Object>> selOneMenu(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("select * from tab_group where companyId = ? and (parId is null or parId = '') ",new Object[]{companyId});
		} catch (Exception e) {
			logger.error("查询所有一级目录异常："+ e.toString());
			return null;
		}
		return list;
	}


 
	/**
	 * 删除分组
	 */
	@Override
	public boolean delGroup(String groupId) {
		try {
			int count = jdbcTemplate.update("delete from tab_group where id = ?", new Object[]{groupId});
			
			if(count > 0){
				return true;
			}else {
				return false;
			}
			
		} catch (Exception e) {
			logger.error("删除分组异常：" + e.toString());
			return false;
		}
	}


	/**
	 * 修改分组名称
	 */
	@Override
	public boolean updateGroup(String groupId,String newName) {
		
		try {
			int count = jdbcTemplate.update("update tab_group set groupName = ? where id = ?", new Object[]{newName,groupId});
			if(count == 1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error("修改分组名称异常："+ e.toString());
			return false;
		}
	}


	/**
	 * 添加分组
	 */
	@Override
	public boolean addGroup(String id,String parentId,String groupName,String companyId) {
		try {
			int count = jdbcTemplate.update("insert into tab_group values(?,?,?,?)", new Object[]{id,groupName,parentId,companyId});
			
			if(count == 1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error("添加分组异常："+ e.toString());
			return false;
		}
		
	}


	/**
	 * 查询当前分组下的设备数量
	 */
	@Override
	public int selDevNum(String groupId) {
		try {
			jdbcTemplate.queryForList("select count(*) from tab_group", new Object[]{groupId});
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}


	/**
	 * 查询子节点
	 */
	@Override
	public List<Map<String, Object>> selChildGroup(String groupId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("select * from tab_group where parId = ?", new Object[]{groupId});
			if(list != null && list.size()>0){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("查询子节点异常："+e.toString());
			return null;
		}
	}
	/**
	 * 在保存分组前查询当前分组下是否有重名的分组
	 */
	@Override
	public boolean selGroupIsExist(String groupId, String groupName,String companyId) {
		boolean flag = false;
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select count(*) from  tab_group where parId = ? and groupName = ? and companyId = ?", new Object[]{groupId,groupName,companyId});
			int count = Integer.parseInt((String.valueOf(list.get(0).get("count(*)"))));
			
			if(count > 0){
				flag = true; 
			}
			
		} catch (Exception e) {
			logger.error("查询当前分组下是否有重名的分组异常："+e.toString());
		}
		return flag;
	}


	/**
	 *  查询分组下的设备
	 */
	@Override
	public List<Map<String, Object>> selDevTheGroup(String groupId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		try {
			
			list = jdbcTemplate.queryForList("select * from tab_user_devices where groupId = ?", new Object[]{groupId});
			
			if(list != null && list.size()>0){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("查询分组下的设备异常："+e.toString());
			return null;
		}
		
	}


	/**
	 * 根据分组id查询分组名称
	 */
	@Override
	public String selGroupNameById(String id) {
		String result = "";
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select groupName from tab_group where id = ?", new Object[]{id});
			
			result = list.get(0).get("groupName").toString();
			
		} catch (Exception e) {
			logger.error("根据分组id查询分组名称异常："+e.toString());
		}
		return result;
	}


	/**
	 * 查询分组下的设备数量
	 */
	@Override
	public int selGroupDevNum(String id) {
		int count = 0;
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select count(*) from tab_user_devices where groupId = ?", new Object[]{id});
			count = Integer.parseInt(String.valueOf(list.get(0).get("count(*)")));
		} catch (Exception e) {
			logger.error("查询分组下的设备异常："+e.toString());
		}
		return count;
	}
}
