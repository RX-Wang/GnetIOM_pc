package com.gnet.module.operator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gnet.utils.DateUtil;
import com.gnet.utils.UUIDUtil;



@Repository(value = "operatorDao")
public class OperatorDaoImpl implements IOperatorDao {
	
	protected final Logger logger = Logger.getLogger(getClass());
	
	@Resource(name= "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 查询操作员账号是否可用
	 */
	@Override
	public int checkOpCodeIsExist(String opCode,String companyId) {
		
		int count = 0;
		
		try {
			count = Integer.parseInt(String.valueOf(jdbcTemplate.queryForList("select count(*) from tab_operator where opCode = ? and companyId = ?", new Object[]{opCode,companyId}).get(0).get("count(*)")));
		} catch (Exception e) {
			logger.error("查询操作员账号是否可用异常："+e.toString());
		}
		return count;
	}

	/**
	 * 添加操作员
	 */
	@Override
	public boolean addOperator(String opCode, String opPwd, String opName,
			String opRule,String companyId) {
		
		boolean flag = false;
		
		try {
			int count = jdbcTemplate.update("insert into tab_operator(id,opCode,opPwd,opName,opRule,opDate,opFlag,companyId) values(?,?,?,?,?,?,?,?)", new Object[]{UUIDUtil.getUUID(),opCode,opPwd,opName,opRule,DateUtil.getCurrentDateTime(),"1",companyId});
			
			if(count == 1){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("添加操作员异常："+e.toString());
		}
		return flag;
	}

	/**
	 * 修改操作员信息
	 */
	@Override
	public boolean updateOperator(String id, String opName, String opRule) {
		
		boolean flag = false;
		
		try {
			int count = jdbcTemplate.update("update tab_operator set opName = ?, opRule = ? where id = ?", new Object[]{opName,opRule,id});
			
			if(count == 1){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("修改操作员信息异常："+e.toString());
		}
		return flag;
	}

	/**
	 * 删除操作员
	 */
	@Override
	public boolean delOperator(String id) {
		boolean flag = false;
		try {
			int count = jdbcTemplate.update("update tab_operator set opFlag = '0' where id = ?", new Object[]{id});
			
			if(count == 1){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("删除操作员异常："+e.toString());
		}
		return flag;
	}

	/**
	 * 根据操作员账号查询操作员信息
	 */
	@Override
	public List<Map<String, Object>> selOperatorByOpCode(String opCode) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("select * from tab_operator where opCode = ?", new Object[]{opCode});
			
			if(list!= null && list.size()>0){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("查询操作员信息异常："+e.toString());
			return null;
		}
	}

	/**
	 * 查询当前集团下的所有操作员信息
	 */
	@Override
	public List<Map<String, Object>> selAllOperatorInfo(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("select id,opCode,opName,opRule,date_format(opDate,'%Y-%c-%d %k:%i:%s') as opDate,opFlag from tab_operator where companyId = ? and opFlag = '1'", new Object[]{companyId});
			
			if(list!= null && list.size()>0){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("查询所有操作员信息异常："+e.toString());
			return null;
		}
	}

	/**
	 * 根据名称或账号查询操作员信息
	 */
	@Override
	public List<Map<String, Object>> selOperatorByOpName(String sql,String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			list = jdbcTemplate.queryForList(sql, new Object[]{companyId});
			
			if(list != null && list.size()>0){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("根据名称查询操作员信息异常："+e.toString());
			return null;
		}
	}

	/**
	 * 修改操作员密码
	 */
	@Override
	public boolean updateOpPwd(String opCode, String opPwd,String companyId) {
		boolean flag = false;
		
		try {
			int count = jdbcTemplate.update("update tab_operator set opPwd = ? where opCode = ? and companyId = ?", new Object[]{opPwd,opCode,companyId});
			
			if(count == 1){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("修改操作员密码异常："+e.toString());
		}
		return flag;
	}
}
	
	


