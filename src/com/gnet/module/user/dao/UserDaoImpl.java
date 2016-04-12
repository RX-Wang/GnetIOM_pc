package com.gnet.module.user.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gnet.utils.DateUtil;
import com.gnet.utils.UUIDUtil;


@Repository(value="userDao")
public class UserDaoImpl implements IUserDao {

	protected final Logger logger = Logger.getLogger(getClass());
	
	
	@Resource
	private JdbcTemplate jdbcTemplate;


	/**
	 * 用户登录
	 */
	@Override
	public List<Map<String, Object>> userLogin(String userName, String passWord,String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			list = jdbcTemplate.queryForList("select * from tab_operator where opCode = ? and opPwd = ? and companyId = ?", new Object[]{userName , passWord,companyId});
		} catch (Exception e) {
			logger.error("用户登录异常");
			return null;
		}
		logger.info("用户登录成功");
		return list;
	}


	/**
	 * 更新当前用户最后一次登录系统时间
	 */
	@Override
	public int updateLasLoginTime(String userName,String companyId) {
		int count = 0;
		try {
			count = jdbcTemplate.update("update tab_operator set opLastLoginTime = ? where opCode = ? and companyId = ?", new Object[]{DateUtil.getCurrentDateTime() , userName,companyId});
		} catch (Exception e) {
			logger.error("更新用户最后一次登录时间异常");
			count = 0;
		}
		logger.info("更新用户最后一次登录时间成功");
		return count;
	}


	/**
	 * 注册本地集团信息
	 */
	@Override
	public boolean addCompanyInfo(String companyId, String companyName,
			String companyPwd, String companyInterAddr, String companyAddr,
			String companyPhone) {
		int count = 0;
		try {
			count = jdbcTemplate
					.update("insert into tab_company(id, companyId, companyName, companyPwd, companyInterAddr, companyAddr, companyPhone, addDate) values (?,?,?,?,?,?,?,?)",
							new Object[] { UUIDUtil.getUUID(),companyId, companyName, companyPwd,
									companyInterAddr, companyAddr, companyPhone,
									DateUtil.getCurrentDateTime() });
		} catch (Exception e) {
			count = 0;
		}
		
		if(count>0){
			return true;
		}else{
			return false;
		}
	}


	/**
	 * 更新本地集团信息
	 */
	@Override
	public boolean updateCompanyInfo(String companyId, String companyName,
			String companyInterAddr, String companyAddr, String companyPhone,
			String softName, String description, String companyPic,
			String softLogo) {
		try {
			String sql = "update tab_company set companyName = ?, companyInterAddr = ?, companyAddr = ?, companyPhone = ?, softName = ?, companyDescription = ?, companyPic = ?, softLogo = ? where companyId = ?";
			int count = jdbcTemplate.update(sql, new Object[]{companyName,companyInterAddr,companyAddr,companyPhone,softName,description,companyPic,softLogo,companyId});
			
			if(count == 1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error("更新集团信息异常：" + e.toString());
			return false;
		}
	}


	/**
	 * 查询公司名称是否唯一
	 */
	@Override
	public boolean checkCompanyNameIsExist(String companyName) {
		boolean flag = false;
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select count(*) from tab_company where companyName = ?", new Object[]{companyName});
			int count = Integer.parseInt((String.valueOf(list.get(0).get("count(*)"))));
			
			if(count == 1){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("查询公司名称是否唯一异常："+e.toString());
		}
		return flag;
	}


	/**
	 * 根据集团id获取集团信息
	 */
	@Override
	public List<Map<String, Object>> getCompanyInfo(String cId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			list = jdbcTemplate.queryForList("select * from tab_company where companyId = ?", new Object[]{cId});
			
		} catch (Exception e) {
			logger.error("根据集团id查询集团信息异常："+e.toString());
		}
		return list;
	}


	/**
	 * 修改当前集团账号密码
	 */
	@Override
	public boolean updateCompanyPwd(String companyId, String companyPwd) {
		boolean flag = false;
		try {
			int count = jdbcTemplate.update("update tab_company set companyPwd = ? where companyId = ?", new Object[]{companyPwd,companyId});
			
			if(count >0){
				flag = true;
			}
			
		} catch (Exception e) {
			logger.error("修改当前集团账号密码异常："+e.toString());
		}
		return flag;
	}


	/**
	 * 更新最后一次退出系统时间
	 */
	@Override
	public int updateLastQuitTime(String opCode,String companyId) {
		int count = 0;
		try {
			count = jdbcTemplate.update("update tab_operator set opLastQuitTime = ? where opCode = ? and companyId = ?", new Object[]{DateUtil.getCurrentDateTime() , opCode,companyId});
		} catch (Exception e) {
			logger.error("更新用户最后一次退出系统时间异常");
			count = 0;
		}
		logger.info("更新用户最后一次退出系统时间成功");
		return count;
	}


	/**
	 * 查询集团账号是否存在
	 */
	@Override
	public boolean checkCompanyIsExist(String userName) {
		boolean flag = false;
		
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select count(*) from tab_company where companyId = ?", new Object[]{userName});
			int count = Integer.parseInt(String.valueOf(list.get(0).get("count(*)")));
			if(count>0){
				flag = true;
			}
			
		} catch (Exception e) {
			logger.error("登录前查询集团账号是否存在异常："+e.getMessage());
		}
		return flag;
	}


	/**
	 * 查询操作员账号是否存在
	 */
	@Override
	public boolean checkOperatorIsExist(String userName, String companyId2) {
boolean flag = false;
		
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select count(*) from tab_operator where opCode = ? and companyId = ?", new Object[]{userName,companyId2});
			int count = Integer.parseInt(String.valueOf(list.get(0).get("count(*)")));
			if(count>0){
				flag = true;
			}
			
		} catch (Exception e) {
			logger.error("登录前查询操作员账号是否存在异常："+e.getMessage());
		}
		return flag;
	}
}
