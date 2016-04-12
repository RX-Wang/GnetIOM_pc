package com.gnet.module.devices.service;

import java.util.List;
import java.util.Map;



public interface IDevicesService {


	/**
	 * 删除用户
	 * @param endpoint
	 * @param psJson
	 * @param nickName
	 * @return
	 */
	String deleteUser(String endpoint, String psJson, String permission_sign,String id,String uniqueId);

	/**
	 * 更新设备绑定信息
	 * @param endpoint
	 * @param psJson
	 * @param companyId
	 * @param nickName
	 * @param userPhone
	 * @param userType
	 * @param groupid
	 * @param userAddress
	 * @param description
	 * @param uniqueId
	 * @param securityCode
	 * @param simNumber
	 * @param devicesTypeId
	 * @param linkman
	 * @param userCode
	 * @return
	 */
	String updateBindDevice(String endpoint, String psJson,String dev_userId, String companyId,
			String nickName, String oldNickName, String userPhone, String userType, String groupid,
			String userAddress, String description, String uniqueId,
			String securityCode, String simNumber, String devicesTypeId,
			String linkman);

	/**
	 * 绑定设备与
	 * @param endpoint
	 * @param psJson
	 * @param companyId
	 * @param nickName
	 * @param userPhone
	 * @param userType
	 * @param groupid
	 * @param userAddress
	 * @param description
	 * @param binderType
	 * @param uniqueId
	 * @param securityCode
	 * @param simNumber
	 * @param devicesTypeId
	 * @param linkman
	 * @return
	 */
	String addBindDevice(String endpoint, String psJson, String companyId,
			String nickName, String userPhone, String userType, String groupid,
			String userAddress, String description, String binderType,
			String uniqueId, String securityCode, String simNumber,
			String devicesTypeId, String linkman);

	/**
	 * 添加账号授权
	 * @param uniqueId
	 * @param securityCode
	 * @param authAccount
	 * @param binderType
	 * @return
	 */
	String addAuthorize(String endpoint,String psJson,String uniqueId, String securityCode,
			String authAccount, String binderType,String companyId);

	/**
	 * 解除授权
	 * @param endpoint
	 * @param psJson
	 * @param userName
	 * @param uniqueId
	 * @return
	 */
	boolean delAuthorize(String endpoint, String psJson, String id,
			String uniqueId);

	/**
	 * 查询授权账号是否已经绑定
	 * @param accountType
	 * @param authAccount
	 * @param uniqueId
	 * @return
	 */
	boolean checkIsExist(String accountType, String authAccount, String uniqueId);

	/**
	 * 查询当前用户是否是设备的主控，有无授权的权限
	 * @param userName
	 * @param uniqueId
	 * @return
	 */
	String selDevPermission_sign(String userName, String uniqueId);

	/**
	 * 查询当前用户的权限
	 * @param id
	 * @param companyId
	 * @return
	 */
	String checkPermission_sign(String id, String companyId);

	/**
	 * 查询设备信息
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> checkDeviceInfo(String id);

	/**
	 * 根据设备id查询设备绑定的用户
	 * @param uniqueId
	 * @return
	 */
	List<Map<String, Object>> selBindUserByUniqueId(String uniqueId,String companyId);

	/**
	 * 授权前验证
	 * @param accountType
	 * @param account
	 * @param companyId
	 * @return
	 */
	String checkAccount(String accountType, String account, String uniqueId);

	/**
	 * 追踪
	 * @param uniqueId
	 * @param curTime
	 * @return
	 */
	List<Map<String, Object>> beginTrack(String uniqueId, String curTime);

	/**
	 * 保存历史轨迹
	 * @param beginTime
	 * @param endTime
	 * @param uniqueId
	 * @return
	 */
	boolean saveTrice(String beginTime, String endTime, String uniqueId,String companyId);

	/**
	 * 根据设备id查询该设备的所有历史轨迹
	 * @param uniqueId
	 * @return
	 */
	List<Map<String, Object>> selHistoryTrace(String uniqueId,String companyId);

	/**
	 * 根据记录id删除历史轨迹
	 * @param id
	 * @return
	 */
	boolean delHistoryTrace(String id);

	/**
	 * 根据时间段查询历史轨迹
	 * @param string
	 * @param string2
	 * @param uniqueId
	 * @return
	 */
	List<Map<String, Object>> checkHistoryTrace(String beginTime, String endTime,
			String uniqueId);

	/**
	 * 查询设备信息
	 * @param uniqueId
	 * @param companyId
	 * @return
	 */
	List<Map<String, Object>> getDeviceInfoByUniqueId(String uniqueId,
			String companyId);

	/**
	 * 查询当前用户下是否有该设备
	 * @param uniqueId
	 * @param companyId
	 * @return
	 */
	boolean checkDeviceIsExist(String uniqueId, String companyId);
	/**
	 * 根据设备号，查设备类型
	 * @param uniqueId
	 * @param companyId
	 * @return
	 */
	String findTypeIdbyUniqueId(String uniqueId);
	/**
	 * 根据设备号，查询Gl300的开启追踪模式的值
	 * @param uniqueId
	 * @param companyId
	 * @return
	 */
	String findFloIterTimbyUniqueId(String uniqueId);
	/**
	 * 根据设备号，查询Gl300的关闭追踪模式的值
	 * @param uniqueId
	 * @param companyId
	 * @return
	 */
	String findGeneralTimebyUniqueId(String uniqueId);
	
	/**
	 * 根据设备号，查询设备的在线状态
	 * @param uniqueId
	 * @param companyId
	 * @return
	 */
	String findIsOnlinebyUniqueId(String uniqueId);

	/**
	 * 根据设备号或名称查询设备信息
	 * @param nameOrId
	 * @param companyId
	 * @return
	 */
	List<Map<String, Object>> selDevByIdOrName(String nameOrId, String companyId);

	/**
	 * 查询当前是设备是否已经开启追踪模式
	 * @param uniqueId
	 * @param companyId
	 * @return
	 */
	List<Map<String, Object>> checkDevIsTrackByUniqueId(String uniqueId, String companyId);

	/**
	 * 开启追踪模式
	 * @param uniqueId
	 * @param nickName
	 * @param curTime
	 * @param companyId
	 * @return
	 */
	boolean insertDevTrack(String uniqueId, String nickName, String curTime,
			String companyId);

	/**
	 * 查询当前集团下已经开启追踪模式的设备
	 * @param companyId
	 * @return
	 */
	List<Map<String, Object>> selAllDevTrack(String companyId);

	/**
	 * 关闭追踪模式
	 * @param uniqueId
	 * @param companyId
	 * @return
	 */
	boolean closeDevTrack(String uniqueId, String companyId);



	/**根据设备号，查询抓擢列表
	 * @param uniqueId
	 * @return
	 */
	List<Map<String,Object>> checkBatch(String uniqueId);
	/**根据设备号删除抓擢列表
	 * @param uniqueId
	 * @return
	 */
	Integer delBatch(String uniqueId, String type);
	
	
	/**
	 * 保存数据到抓擢列表
	 * @param beginTime
	 * @param endTime
	 * @param uniqueId
	 * @return
	 */
	String saveCatchzhuolist(String uniqueId,String atInstruction,String type);

	/**
	 * 查询当前集团账号下所有的设备数量
	 * @param companyId
	 * @return
	 */
	int selTotalDevNum(String companyId);

	/**
	 * 查询开启追踪模式的设备信息
	 * @param uniqueId
	 * @param companyId
	 * @return
	 */
	List<Map<String, Object>> selectTrackDev(String uniqueId, String companyId);

	/**
	 * 查询已经保存历史轨迹的设备异常
	 * @param companyId
	 * @return
	 */
	List<Map<String, Object>> selDevHasHistoryTrack(String companyId);
}
