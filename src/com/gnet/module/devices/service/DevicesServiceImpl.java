package com.gnet.module.devices.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnet.module.devices.dao.IDevicesDao;
import com.gnet.utils.RestfulUtil;

@Service("devicesService")
public class DevicesServiceImpl implements IDevicesService {

	@Resource(name = "devicesDao")
	private IDevicesDao devicesDao;



	/**
	 * 删除用户
	 */
	@Transactional
	@Override
	public String deleteUser(String endpoint, String psJson, String permission_sign,String id,String uniqueId) {

		try {
			RestfulUtil restfulUtil = new RestfulUtil();
			String result = restfulUtil.sendHttpRequest(endpoint, psJson);
			JSONObject checkResJson = JSONObject.fromObject(result);
//			String obj = checkResJson.getJSONObject("GRES_HEAD").getString(
//					"ResCode");
			boolean flag = false;
			
			if(permission_sign.equals("0")){
				//非主控
				flag =devicesDao.delDeviceUser(id,"");
			}else {
				flag = devicesDao.delDeviceUser("",uniqueId);
			}
			if(flag){
				return checkResJson.toString();
			}else{
				return null; 
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 更新用户与设备绑定信息
	 */
	@Transactional
	@Override
	public String updateBindDevice(String endpoint, String psJson,
			String dev_userId, String companyId, String nickName, String oldNickName,
			String userPhone, String userType, String groupid,
			String userAddress, String description, String uniqueId,
			String securityCode, String simNumber, String devicesTypeId,
			String linkman) {
		try {
			String checkResJson = "";
			if(!nickName.trim().equals(oldNickName)){
				//更新后台的设备昵称
				RestfulUtil restfulUtil = new RestfulUtil();
				checkResJson = restfulUtil.sendHttpRequest(endpoint, psJson);
//				checkResJson = JSONObject.fromObject(result);
			}else{
				checkResJson = "{\"GRES_HEAD\":{\"Version\":\"1.0\", \"AccessType\":\"PC\",\"ResCode\":\"1\",\"Memo\":\"更新设备昵称成功\", \"Sign\":\"N_N_00001\"},\"GRES_IOT\":{\"INFOLIST\":[]}}";
			}
			
			//更新本地的设备绑定信息
			boolean flag = devicesDao.updateBindDevice(dev_userId, companyId, nickName,
					userPhone, userType, groupid, userAddress, description,
					uniqueId, securityCode, simNumber, devicesTypeId, linkman);
			if(flag && (checkResJson!=null && !"".equals(checkResJson))){
				return checkResJson;
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 绑定设备
	 */
	@Override
	public String addBindDevice(String endpoint, String psJson,
			String userName, String nickName, String userPhone,
			String userType, String groupid, String userAddress,
			String description, String binderType, String uniqueId,
			String securityCode, String simNumber, String devicesTypeId,
			String linkman) {
		try {

			RestfulUtil restfulUtil = new RestfulUtil();
			String result = restfulUtil.sendHttpRequest(endpoint, psJson);
			JSONObject checkResJson = JSONObject.fromObject(result);
			
			
			boolean flag = devicesDao.addBindDevice(userName, nickName,
					userPhone, userType, groupid, userAddress, description,
					binderType, uniqueId, securityCode, simNumber,
					devicesTypeId, linkman);
			if (flag) {
				System.out.println("绑定设备成功...");
				return checkResJson.toString();
			} else {
				System.out.println("绑定设备失败...");
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 账号授权
	 */
	@Override
	@Transactional
	public String addAuthorize(String endpoint, String psJson,
			String uniqueId, String devicesSecCode, String account,
			String accountType,String companyId) {
		try {
			RestfulUtil restfulUtil = new RestfulUtil();
			String result = restfulUtil.sendHttpRequest(endpoint, psJson);
			JSONObject checkResJson = JSONObject.fromObject(result);
			String resCode = checkResJson.getJSONObject("GRES_HEAD").getString(
					"ResCode");
			
			if(resCode.equals("1")){
				boolean flag = devicesDao.addAuthorize(uniqueId, devicesSecCode,
						account, accountType,companyId);
				if (flag) {
					System.out.println("授权成功...");
					return checkResJson.toString();
				} else {
					return null;
				}
			}else{
				return checkResJson.toString();
			}
		} catch (Exception e) {
			System.out.println("授权失败：" + e.toString());
			return null;
		}
	}

	/**
	 * 解除授权
	 */
	@Override
	@Transactional
	public boolean delAuthorize(String endpoint, String psJson,
			String id, String uniqueId) {
		try {
			RestfulUtil restfulUtil = new RestfulUtil();
			String result = restfulUtil.sendHttpRequest(endpoint, psJson);
			JSONObject checkResJson = JSONObject.fromObject(result);
			String obj = checkResJson.getJSONObject("GRES_HEAD").getString(
					"ResCode");
			if (obj.equals("1")) {
				System.out.println("服务器解除授权成功...");
				
				boolean flag = devicesDao.delDeviceUser(id, "");

				if (flag) {
					System.out.println("解除授权成功...");
					return true;
				} else {
					return false;
				}
			} else {
				System.out.println("服务器解除授权失败...");
				return false;
			}
		} catch (Exception e) {
			System.out.println("解除授权异常："+e.toString());
		}
		return false;
	}

	/**
	 * 查询设备是否与授权账号绑定
	 */
	@Override
	public boolean checkIsExist(String accountType, String authAccount,
			String uniqueId) {
		return devicesDao.checkIsExist(accountType,authAccount, uniqueId);
	}

	/**
	 * 查询当前用户有无授权的权限
	 */
	@Override
	public String selDevPermission_sign(String userName, String uniqueId) {
		return devicesDao.selDevPermission_sign(userName,uniqueId);
	}

	/**
	 * 查询当前用户的权限
	 */
	@Override
	public String checkPermission_sign(String id, String companyId) {
		return devicesDao.checkPermission_sign(id,companyId);
	}

	/**
	 * 查询设备信息
	 */
	@Override
	public List<Map<String, Object>> checkDeviceInfo(String id) {
		return devicesDao.checkDeviceInfo(id);
	}

	/**
	 * 根据设备id查询设备绑定的用户
	 */
	@Override
	public List<Map<String, Object>> selBindUserByUniqueId(String uniqueId,String companyId) {
		return devicesDao.selBindUserByUniqueId(uniqueId,companyId);
	}

	/**
	 * 授权前验证
	 * 0:集团账号不存在
	 * 1：可以授权
	 * 2：已授权
	 * 
	 */
	@Override
	public String checkAccount(String accountType, String account,
			String uniqueId) {
		////如果为集团账号，验证集团账号是否存在，不存在，给出提示，存在则验证是否已经授权，没有授权，则可以授权，已授权，给出提示
		if("集团账号".equals(accountType)){
			boolean flag = devicesDao.checkCompanyIsExist(account);
			if(flag){
				boolean  res = devicesDao.checkIsExist(accountType,account,uniqueId);
				if(res){
					return "2";
				}else{
					return "1";
				}
			}else{
				//集团不存在
				return "0";
			}
		}else{
			//验证是否已经授权
			boolean flag = devicesDao.checkIsExist(accountType,account,uniqueId);
			if(flag){
				return "2";
			}else{
				return "1";
			}
		}
		
	}

	/**
	 * 追踪
	 */
	@Override
	public List<Map<String, Object>> beginTrack(String uniqueId, String curTime) {
		return devicesDao.beginTrack(uniqueId,curTime);
	}

	/**
	 * 保存历史轨迹
	 */
	@Override
	public boolean saveTrice(String beginTime, String endTime, String uniqueId,String companyId) {
		return devicesDao.saveTrice(beginTime,endTime,uniqueId,companyId);
	}

	/**
	 * 根据设备id查询该设备的所有历史轨迹
	 */
	@Override
	public List<Map<String, Object>> selHistoryTrace(String uniqueId,String companyId) {
		return devicesDao.selHistoryTrace(uniqueId,companyId);
	}

	/**
	 * 根据记录id删除历史轨迹
	 */
	@Override
	public boolean delHistoryTrace(String id) {
		return devicesDao.delHistoryTrace(id);
	}

	/**
	 * 根据时间段查询历史轨迹
	 */
	@Override
	public List<Map<String, Object>> checkHistoryTrace(String beginTime,
			String endTime, String uniqueId) {
		return devicesDao.checkHistoryTrace(beginTime,endTime,uniqueId);
	}

	/**
	 * 根据用户名和设备串号查询设备信息
	 */
	@Override
	public List<Map<String, Object>> getDeviceInfoByUniqueId(String uniqueId,
			String companyId) {
		return devicesDao.getDeviceInfoByUniqueId(uniqueId,companyId);
	}

	/**
	 * 查询当前用户下是否有该设备
	 */
	@Override
	public boolean checkDeviceIsExist(String uniqueId, String companyId) {
		return devicesDao.checkDeviceIsExist(uniqueId,companyId);
	}

	@Override
	public String findTypeIdbyUniqueId(String uniqueId) {
		return devicesDao.findTypeIdbyUniqueId(uniqueId);
	}

	@Override
	public String findFloIterTimbyUniqueId(String uniqueId) {
		return devicesDao.findFloIterTimbyUniqueId(uniqueId);
	}

	@Override
	public String findGeneralTimebyUniqueId(String uniqueId) {
		return devicesDao.findGeneralTimebyUniqueId(uniqueId);
	}

	@Override
	public String findIsOnlinebyUniqueId(String uniqueId) {
		return devicesDao.findIsOnlinebyUniqueId(uniqueId);
	}

	/**
	 * 根据设备号或名称查询设备信息
	 */
	@Override
	public List<Map<String, Object>> selDevByIdOrName(String nameOrId,
			String companyId) {
		return devicesDao.selDevByIdOrName(nameOrId,companyId);
	}

	/**
	 * 查询当前是设备是否已经开启追踪模式
	 */
	@Override
	public List<Map<String, Object>> checkDevIsTrackByUniqueId(String uniqueId, String companyId) {
		return devicesDao.checkDevIsTrackByUniqueId(uniqueId,companyId);
	}

	/**
	 * 开启追踪模式
	 */
	@Override
	public boolean insertDevTrack(String uniqueId, String nickName,
			String curTime, String companyId) {
		return devicesDao.insertDevTrack(uniqueId,nickName,curTime,companyId);
	}

	/**
	 * 查询当前集团下已经开启追踪模式的设备
	 */
	@Override
	public List<Map<String, Object>> selAllDevTrack(String companyId) {
		return devicesDao.selAllDevTrack(companyId);
	}

	/**
	 * 关闭追踪模式
	 */
	@Override
	public boolean closeDevTrack(String uniqueId, String companyId) {
		return devicesDao.closeDevTrack(uniqueId,companyId);
	}

	@Override
	public String saveCatchzhuolist(String uniqueId, String atInstruction,
			 String type) {
		
		return devicesDao.saveCatchzhuolist(uniqueId, atInstruction, type);
	}
	
	@Override
	public List<Map<String, Object>> checkBatch(String uniqueId) {
		return devicesDao.checkBatch(uniqueId);
	}

	@Override
	public Integer delBatch(String uniqueId, String type) {
		 return devicesDao.delBatch(uniqueId,type);
	}

	/**
	 * 查询当前集团下所有的设备数量
	 */
	@Override
	public int selTotalDevNum(String companyId) {
		return devicesDao.selTotalDevNum(companyId);
	}

	/**
	 * 查询开启追踪模式的设备信息
	 */
	@Override
	public List<Map<String, Object>> selectTrackDev(String uniqueId,
			String companyId) {
		return devicesDao.selectTrackDev(uniqueId,companyId);
	}

	/**
	 * 查询已经保存历史轨迹的设备异常
	 */
	@Override
	public List<Map<String, Object>> selDevHasHistoryTrack(String companyId) {
		return devicesDao.selDevHasHistoryTrack(companyId);
	}

}
