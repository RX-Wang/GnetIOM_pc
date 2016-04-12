package com.gnet.module.user.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnet.module.user.dao.IUserDao;
import com.gnet.utils.RestfulUtil;



@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource(name = "userDao")
	private IUserDao userDao;
	
	/**
	 * 用户登录
	 */
	@Override
	public List<Map<String, Object>> userLogin(String userName, String passWord,String companyId) {
		return userDao.userLogin(userName,passWord,companyId);
//		return false;
	}

	/**
	 * 更新当前用户最后一次登录系统时间
	 */
	@Override
	public int updateLasLoginTime(String userName,String companyId) {
		return userDao.updateLasLoginTime(userName,companyId);
	}

	/**
	 * 注册本地集团信息
	 */
	@Override
	public boolean addCompanyInfo(String companyId, String companyName,
			String companyPwd, String companyInterAddr, String companyAddr,
			String companyPhone) {
		
		return userDao.addCompanyInfo(companyId, companyName, companyPwd, companyInterAddr, companyAddr, companyPhone);
	}

	/**
	 * 更新集团信息
	 * 
	 */
	@Transactional
	@Override
	public String updateCompanyInfo(String endpoint, String psJson,
			String companyId, String companyName, String companyInterAddr,
			String companyAddr, String companyPhone, String softName,
			String description, String companyPic, String softLogo) {
		try {
			RestfulUtil restfulUtil = new RestfulUtil();
			String result = restfulUtil.sendHttpRequest(endpoint, psJson);
			JSONObject checkResJson = JSONObject.fromObject(result);
			String obj = checkResJson.getJSONObject("GRES_HEAD")
					.getString("ResCode");
			if(obj.equals("1")){
				boolean flag = userDao.updateCompanyInfo(companyId,companyName,companyInterAddr,companyAddr,companyPhone,softName,description,companyPic,softLogo);
				if(flag){
					return result;
				}else{
					return null;
				}
			}else{
				return result;
			}
			
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 查询公司名称是否唯一
	 */
	@Override
	public boolean checkCompanyNameIsExist(String companyName) {
		return userDao.checkCompanyNameIsExist(companyName);
	}

	/**
	 * 根据集团id获取集团信息
	 */
	@Override
	public List<Map<String, Object>> getCompanyInfo(String cId) {
		return userDao.getCompanyInfo(cId);
	}

	/**
	 * 修改当前集团账号密码
	 */
	@Override
	public boolean updateCompanyPwd(String companyId, String companyPwd) {
		return userDao.updateCompanyPwd(companyId,companyPwd);
	}

	/**
	 * 更新最后一次退出系统时间
	 */
	@Override
	public int updateLastQuitTime(String opCode,String companyId) {
		return userDao.updateLastQuitTime(opCode,companyId);
	}

	/**
	 * 查询集团账号是否存在
	 */
	@Override
	public boolean checkCompanyIsExist(String userName) {
		return userDao.checkCompanyIsExist(userName);
	}

	/**
	 * 查询操作员是否存在
	 */
	@Override
	public boolean checkOperatorIsExist(String userName, String companyId2) {
		return userDao.checkOperatorIsExist(userName,companyId2);
	}
}
