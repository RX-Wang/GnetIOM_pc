package com.gnet.module.devices.dao;

import java.util.List;
import java.util.Map;

public interface IDevicesDao {


	
	/**
	 * 绑定设备
	 * @param userName
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
	boolean addBindDevice(String userName, String nickName, String userPhone,
			String userType, String groupid, String userAddress,
			String description, String binderType, String uniqueId,
			String securityCode, String simNumber, String devicesTypeId,
			String linkman);


	/**
	 * 账号授权
	 * @param uniqueId
	 * @param securityCode
	 * @param authAccount
	 * @param binderType
	 * @return
	 */
	boolean addAuthorize(String uniqueId, String securityCode,
			String authAccount, String binderType,String companyId);

	/**
	 * 查询主控/非主控
	 * @param userName
	 * @param uniqueId
	 * @return
	 */
	String selPermissionSign(String userName, String uniqueId);

	/**
	 * 更新本地设备与用户的绑定信息
	 * @param dev_userId
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
	 * @return
	 */
	boolean updateBindDevice(String dev_userId, String companyId,
			String nickName, String userPhone, String userType, String groupid,
			String userAddress, String description, String uniqueId,
			String securityCode, String simNumber, String devicesTypeId,
			String linkman);

	/**
	 * 查询授权账号是否与设备绑定
	 * @param accountType
	 * @param authAccount
	 * @param uniqueId
	 * @return
	 */
	boolean checkIsExist(String accountType, String authAccount, String uniqueId);

	/**
	 * 查询当前用户有无授权的权限
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
	 * 删除设备与用户绑定
	 * @param id
	 * @param string
	 * @return
	 */
	boolean delDeviceUser(String id, String uniqueId);

	/**
	 * 根据设备id查询绑定的用户
	 * @param uniqueId
	 * @return
	 */
	List<Map<String, Object>> selBindUserByUniqueId(String uniqueId,String companyId);

	/**
	 * 查询集团是否存在
	 * @param account
	 * @return
	 */
	boolean checkCompanyIsExist(String account);

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
	 * 根据时间段查询历史轨迹记录
	 * @param beginTime
	 * @param endTime
	 * @param uniqueId
	 * @return
	 */
	List<Map<String, Object>> checkHistoryTrace(String beginTime,
			String endTime, String uniqueId);


	/**
	 * 根据用户名和设备串号查询设备信息
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
	 *  查询当前集团下已经开启追踪模式的设备
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
	List<Map<String, Object>> checkBatch(String uniqueId);

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
	 * 查询已经保存历史轨迹的设备信息
	 * @param companyId
	 * @return
	 */
	List<Map<String, Object>> selDevHasHistoryTrack(String companyId);
}
