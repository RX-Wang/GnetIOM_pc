package com.gnet.module.operator.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnet.common.Constants;
import com.gnet.module.log.dao.ILogDao;
import com.gnet.module.operator.dao.IOperatorDao;


@Service("operatorService")
public class OperatorServiceImpl implements IOperatorService  {

	
	@Resource(name = "operatorDao")
	private IOperatorDao operatorDao;
	
	@Resource(name = "logDao")
	private ILogDao logDao;

	/**
	 * 查询操作员账号是否可用
	 */
	public int checkOpCodeIsExist(String opCode,String companyId) {
		return operatorDao.checkOpCodeIsExist(opCode,companyId);
	}

	/**
	 * 添加操作员
	 */
	@Override
	@Transactional
	public boolean addOperator(String opCode, String opPwd, String opName,
			String opRule,String companyId,String opName1,String opCode1) {
		boolean flag = operatorDao.addOperator(opCode,opPwd,opName,opRule,companyId);
		boolean logFlag = logDao.addLog("Operator", Constants.ADD_OPERATOR+":"+opName, opName1, opCode1,companyId);
		if(flag){
			if(logFlag){
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	/**
	 * 修改操作员信息
	 */
	@Override
	public boolean updateOperator(String id,String opCode, String opName, String opRule,String opName1,String opCode1,String companyId) {
		boolean flag =  operatorDao.updateOperator(id,opName,opRule);
		
//		boolean flag = operatorDao.addOperator(opCode,opPwd,opName,opRule,companyId);
		boolean logFlag = logDao.addLog("Operator", Constants.UPDATE_OPERATOR+":"+opName, opName1, opCode1,companyId);
		if(flag){
			if(logFlag){
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
		
	}

	/**
	 * 删除操作员
	 */
	@Override
	public boolean delOperator(String id,String opCode,String opName1,String opCode1,String companyId) {
//		return operatorDao.delOperator(opCode);
		boolean flag =  operatorDao.delOperator(id);
		boolean logFlag = logDao.addLog("Operator", Constants.DEL_OPERATOR+":"+opCode, opName1, opCode1,companyId);
		if(flag){
			if(logFlag){
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
		
		
		
	}

	/**
	 * 根据操作员账号查询操作员信息
	 */
	@Override
	public List<Map<String, Object>> selOperatorByOpCode(String opCode) {
		return operatorDao.selOperatorByOpCode(opCode);
	}

	/**
	 * 查询所有操作员信息
	 */
	@Override
	public List<Map<String, Object>> selAllOperatorInfo(String companyId) {
		return operatorDao.selAllOperatorInfo(companyId);
	}

	/**
	 * 根据名称查询操作员信息
	 */
	@Override
	public List<Map<String, Object>> selOperatorByOpName(String sql,String companyId) {
		return operatorDao.selOperatorByOpName(sql,companyId);
	}

	/**
	 * 修改操作员密码
	 */
	@Override
	public boolean updateOpPwd(String opCode, String opPwd,String companyId) {
		return operatorDao.updateOpPwd(opCode,opPwd,companyId);
	}
	
}
