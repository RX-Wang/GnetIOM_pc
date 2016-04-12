package com.gnet.controldevice.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gnet.utils.DateUtil;
import com.gnet.utils.UUIDUtil;

@Repository(value="devicesettingsDao")
public class DeviceSettingsDaoImpl implements DeviceSettingsDao {

	protected final Logger logger = Logger.getLogger(getClass());
	
	@Resource(name="jdbcTemplate1")
	private JdbcTemplate jdbcTemplate1;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Override
	public String findDeviceSettingsMsg(String typeID) {
		String DeviceSettingsMsg ="";
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate1.queryForList("SELECT `pc_devices_prop` FROM `devices_prop` WHERE `devices_type_id` ='"+typeID+"'");
			 DeviceSettingsMsg = String.valueOf(list.get(0).get("pc_devices_prop"));
		} catch (Exception e) {
			logger.error("findDeviceSettingsMsg异常"+e.toString());
			DeviceSettingsMsg = "";
		}
		return DeviceSettingsMsg;
		
	}

	@Override
	public String find1ADeviceSettingsMsg() {
		
		String gl300DeviceSettingsMsg ="";
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate1.queryForList("SELECT `pc_devices_prop` FROM `devices_prop` WHERE `devices_type_id` ='1A'");
			gl300DeviceSettingsMsg = String.valueOf(list.get(0).get("pc_devices_prop"));
		} catch (Exception e) {
			logger.error("find1ADeviceSettingsMsg异常"+e.toString());
			gl300DeviceSettingsMsg = "";
		}
		return gl300DeviceSettingsMsg;
	}

	@Override
	public String addDeviceSettings(String uniqueId, String typeID,
			String houOffset, String startTime, String interTime,
			String moveThreshold, String floIterTim, String batLowThres,String generalTime) {
		try {
			int count = jdbcTemplate
					.update("INSERT INTO `tab_devicesettings_msg` (`uniqueId`, `typeId`, `houOffset`, `startTime`, `interTime`, `moveThreshold`, `floIterTim`, `batLowThres`,`generalTime`) VALUES (?,?,?,?,?,?,?,?,?)",
							new Object[] {uniqueId,
							typeID, houOffset, startTime, interTime,
							moveThreshold, floIterTim, batLowThres,generalTime});
			if (count == 1) {
				return "ok";
			} else {
				return "no";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入G3设备设置信息异常：" + e.toString());
			return "no";
		}
//		/return null;
	}

	@Override
	public List selectSettings(String uniqueId, String typeID) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("SELECT *FROM `tab_devicesettings_msg` WHERE `uniqueId` ='"+uniqueId+"'  AND `typeId`='"+typeID+"'");
		} catch (Exception e) {
			logger.error("查询所有消息异常"+e.toString());
			return null;
		}
		return list;
	}

	@Override
	public String updateDeviceSettings(String uniqueId, String typeID, String houOffset, String startTime,
			String interTime, String moveThreshold, String floIterTim, String batLowThres,String generalTime) {
		try {
			int count = jdbcTemplate
					.update("UPDATE `tab_devicesettings_msg` SET `uniqueId` = ?, `typeId` = ?, `houOffset` = ?, `startTime` = ?, `interTime` = ?, `moveThreshold` = ?, `floIterTim` = ?, `batLowThres` = ?, `generalTime` = ?  WHERE `uniqueId` = ? ",
							new Object[] {uniqueId,
							typeID, houOffset, startTime, interTime,
							moveThreshold, floIterTim, batLowThres,generalTime,uniqueId});
			if (count == 1) {
				return "ok";
			} else {
				return "no";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改G3设备设置信息异常：" + e.toString());
			return "no";
		} 
	}
	
}
