package com.gnet.module.user.service;

import java.util.List;
import java.util.Map;

public interface IUserService {

	/**
	 * 用户登录
	 * @param userName
	 * @param passWord
	 * @return
	 */
	List<Map<String, Object>> userLogin(String userName, String passWord,String companyId);

	/**
	 * 更新当前用户最后一次登录系统时间
	 * @param userName
	 */
	int updateLasLoginTime(String userName,String companyId);

	/**
	 * 注册本地集团信息
	 * @param companyId
	 * @param companyName
	 * @param companyPwd
	 * @param companyInterAddr
	 * @param companyAddr
	 * @param companyPhone
	 * @return
	 */
	boolean addCompanyInfo(String companyId, String companyName,
			String companyPwd, String companyInterAddr, String companyAddr,
			String companyPhone);

	/**
	 * 更新集团信息
	 * @param endpoint
	 * @param psJson
	 * @param companyId
	 * @param companyName
	 * @param companyInterAddr
	 * @param companyAddr
	 * @param companyPhone
	 * @param softName
	 * @param description
	 * @param companyPic
	 * @param softLogo
	 * @return
	 */
	String updateCompanyInfo(String endpoint, String psJson, String companyId,
			String companyName, String companyInterAddr, String companyAddr,
			String companyPhone, String softName, String description,
			String companyPic, String softLogo);

	/**
	 * 查询公司名称是否唯一
	 * @param companyName
	 * @return
	 */
	boolean checkCompanyNameIsExist(String companyName);

	/**
	 * 根据集团id获取集团信息
	 * @param cId
	 * @return
	 */
	List<Map<String, Object>> getCompanyInfo(String cId);

	/**
	 * 修改当前集团账号密码
	 * @param companyId
	 * @param companyPwd
	 * @return
	 */
	boolean updateCompanyPwd(String companyId, String companyPwd);

	/**
	 * 更新最后一次退出系统的时间
	 * @param opCode
	 * @return
	 */
	int updateLastQuitTime(String opCode,String companyId);

	/**
	 * 查询集团账号是否存在
	 * @param userName
	 * @return
	 */
	boolean checkCompanyIsExist(String userName);

	/**
	 * 查询操作员是否存在
	 * @param userName
	 * @param companyId2
	 * @return
	 */
	boolean checkOperatorIsExist(String userName, String companyId2);
}
