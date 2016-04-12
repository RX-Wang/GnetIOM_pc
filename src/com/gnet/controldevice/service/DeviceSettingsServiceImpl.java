package com.gnet.controldevice.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnet.controldevice.dao.DeviceSettingsDao;

@Service("devicesettingsService")
public class DeviceSettingsServiceImpl implements DeviceSettingsService {

	@Resource(name = "devicesettingsDao")
	private DeviceSettingsDao deviceSettingsDao;

	@Override
	public String findDeviceSettingsMsg(String typeID) {
		
		// TODO Auto-generated method stub
		return deviceSettingsDao.findDeviceSettingsMsg(typeID);
	}

	

	@Override
	public String addDeviceSettings(String uniqueId, String typeID,
			String houOffset, String startTime, String interTime,
			String moveThreshold, String floIterTim, String batLowThres,String generalTime) {
		
		return deviceSettingsDao.addDeviceSettings(uniqueId, typeID, houOffset, startTime, interTime, moveThreshold, floIterTim, batLowThres,generalTime);
	}

	@Override
	public List selectSettings(String uniqueId, String typeID) {
		
		return deviceSettingsDao.selectSettings(uniqueId, typeID);
	}

	@Override
	public String updateDeviceSettings(String uniqueId, String typeID, String houOffset, String startTime,
			String interTime, String moveThreshold, String floIterTim, String batLowThres,String generalTime) {
		return deviceSettingsDao.updateDeviceSettings(uniqueId, typeID, houOffset, startTime, interTime, moveThreshold, floIterTim, batLowThres,generalTime);
	}
}
