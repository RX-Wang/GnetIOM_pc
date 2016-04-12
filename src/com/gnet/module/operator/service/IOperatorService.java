package com.gnet.module.operator.service;

import java.util.List;
import java.util.Map;

public interface IOperatorService {

	/**
	 * 查询操作员账号是否可用
	 * @param opCode
	 * @return
	 */
	public int checkOpCodeIsExist(String opCode,String companyId);

	/**
	 * 添加操作员
	 * @param opCode
	 * @param opPwd
	 * @param opName
	 * @param opRule
	 * @return
	 */
	public boolean addOperator(String opCode, String opPwd, String opName,
			String opRule,String companyId,String opName1,String opCode1);

	/**
	 * 修改操作员信息
	 * @param opCode
	 * @param opName
	 * @param opRule
	 * @return
	 */
	public boolean updateOperator(String id,String opCode, String opName, String opRule,String opName1,String opCode1,String companyId);

	/**
	 * 删除操作员
	 * @param opCode
	 * @return
	 */
	public boolean delOperator(String id,String opCode,String opName1,String opCode1,String companyId);

	/**
	 * 根据操作员账号查询操作员信息
	 * @param opCode
	 * @return
	 */
	public List<Map<String, Object>> selOperatorByOpCode(String opCode);

	/**
	 * 查询所哟操作员信息
	 * @return
	 */
	public List<Map<String, Object>> selAllOperatorInfo(String companyId);

	/**
	 * 根据名称查询操作员信息
	 * @param opName
	 * @return
	 */
	public List<Map<String, Object>> selOperatorByOpName(String sql,String companyId);

	/**
	 * 修改操作员密码
	 * @param opCode
	 * @param opPwd
	 * @return
	 */
	public boolean updateOpPwd(String opCode, String opPwd,String companyId);


}
