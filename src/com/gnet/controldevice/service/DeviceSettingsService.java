package com.gnet.controldevice.service;

import java.util.List;



public interface DeviceSettingsService {
	/**
	 * 查询G3设备设置的选项
	 * @return
	 */
	String findDeviceSettingsMsg(String typeID);

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
