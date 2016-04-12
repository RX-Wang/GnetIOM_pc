package com.gnet.controldevice.dao;

import java.util.List;
import java.util.Map;

public interface DeviceSettingsDao {
	/**
	 * 查询G3设备设置的选项
	 * @return
	 */
	String findDeviceSettingsMsg(String typeID);

	/**
	 * 查询1A设备设置的选项
	 * @return
	 */
	String find1ADeviceSettingsMsg();
	
	
	/**
	 * G3设备设置信息插入接口
	 * @return
	 */
	 String addDeviceSettings(String uniqueId, String typeID,
				String houOffset, String startTime, String interTime,
				String moveThreshold, String floIterTim, String batLowThres,String generalTime);
	 /**
	 * G3设备设置信息查看接口
	 * @return
	 */
	 List selectSettings(String uniqueId, String typeID);
	 /**
		 * G3设备设置信息修改接口
		 * @return
		 */
	String updateDeviceSettings(String uniqueId, String typeID, String houOffset, String startTime, String interTime,
			String moveThreshold, String floIterTim, String batLowThres,String generalTime);

}
