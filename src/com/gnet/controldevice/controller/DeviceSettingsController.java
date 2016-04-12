package com.gnet.controldevice.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gnet.common.Constants;
import com.gnet.controldevice.service.DeviceSettingsService;
import com.gnet.module.devices.service.IDevicesService;
import com.gnet.utils.QueueSend;

@Controller
@RequestMapping("/devicesettingsController")
public class DeviceSettingsController {

	@Resource(name = "devicesettingsService")
	private DeviceSettingsService dSettingsService;
	@Resource(name = "devicesService")
	private IDevicesService devicesService;
	/**
	 * findDeviceSettingsMsg
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/findDeviceSettingsMsg")
	public void findDeviceSettingsMsg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		String uniqueId = request.getParameter("uniqueId");
		String typeID = devicesService.findTypeIdbyUniqueId(uniqueId);
		// 查看已保存的数据
		java.util.List savedData = dSettingsService.selectSettings(uniqueId,
				typeID);
		// 写一个假的 模板 JSON
		// String G3DeviceSettingsMsg =
		// "{\"时区\":\"例:00-24\",\"定时唤醒开始时间\":\"例:HHMM1200\",\"定时唤醒时间间隔\":\"例000-168小时\",\"震动唤醒阈值\":\"例:00-0F\",\"追踪发送位置时间间隔\":\"例:000-999秒\",\"电池电量低报警阈值\":\"例:00-99\"}";
		String DeviceSettingsMsg = dSettingsService
				.findDeviceSettingsMsg(typeID);
		jsonObj.put("DataResult", "pass");
		jsonObj.put("message", DeviceSettingsMsg);
		jsonObj.put("savedData", savedData);
		jsonObj.put("typeID", typeID);
		out.print(jsonObj);
	}
	// Setting后台
	public String Setting(String uniqueId, String typeID, String houOffset,
			String startTime, String interTime, String moveThreshold,
			String floIterTim, String batLowThres, String generalTime) {
		if ("G3".equals(typeID) || "G3B".equals(typeID) || "G3B" == typeID
				|| "G3" == typeID) {
			System.out.println("G3设备设置。。。。。。。。。。。。");
			String atInstruction = "AT+GTGBC=,," + "" + "," + "" + "," + ""
					+ "," + "" + "," + "" + "," + houOffset + "," + startTime
					+ "," + interTime + "," + moveThreshold + "," + floIterTim
					+ "," + batLowThres + ",,,FFFF$";
			System.out.println("atInstruction="+atInstruction);
			String isOnline = devicesService.findIsOnlinebyUniqueId(uniqueId);
			if ("1".equals(isOnline) || "1" == isOnline) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("uniqueId", uniqueId);
				jsonObj.put("transportType", "TCP");
				jsonObj.put("content", atInstruction);
				boolean result = QueueSend.produce(jsonObj.toString());
				System.out.println("回控指令发送结果" + result);
				if (result) {
					return "设备设置请求成功";
				}else {
					return "设备设置请求失败";
				}
			}else {
				System.out.println("设备不在线存抓擢列表。。。。。。。。。。。。");
				String saveCatchzhuolistResult = devicesService.saveCatchzhuolist(uniqueId, atInstruction,"DeviceSettings");
				if ("1".equals(saveCatchzhuolistResult)) {
					return "设备不在线存抓擢列表成功";
				}else {
					return "设备不在线存抓擢列表失败";
				}
			}
			
		} else {
			System.out.println("GL300设备设置。。。。。。。。。。。。");
			String msg = generalTime + "," + generalTime + "," + generalTime
					+ "," + generalTime + ",";
			String atInstruction = "AT+GTFRI=gl300,1,0,,,0000,0000," + msg
					+ "1F,1000,1000,0,5,50,1,50,,1111$";
			System.out.println("atInstruction="+atInstruction);
			String isOnline = devicesService.findIsOnlinebyUniqueId(uniqueId);
			if ("1".equals(isOnline) || "1" == isOnline) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("uniqueId", uniqueId);
				jsonObj.put("transportType", "TCP");
				jsonObj.put("content", atInstruction);
				boolean result = QueueSend.produce(jsonObj.toString());
				System.out.println("回控指令发送结果" + result);
				if (result) {
					return "设备设置请求成功";
				}else {
					return "设备设置请求失败";
				}
			}else {
				System.out.println("设备不在线存抓擢列表。。。。。。。。。。。。");
				String saveCatchzhuolistResult = devicesService.saveCatchzhuolist(uniqueId, atInstruction,"DeviceSettings");
				if ("1".equals(saveCatchzhuolistResult)) {
					return "设备不在线存抓擢列表成功";
				}else {
					return "设备不在线存抓擢列表失败";
				}
			}
			
			
		}

	}

	/**
	 * 保存或修改设置
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/settingSaveOrUpdate")
	public void settingSaveOrUpdate(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		String uniqueId = request.getParameter("uniqueId");
		String typeID = request.getParameter("typeID");
		String houOffset = request.getParameter("houOffset");
		String startTime = request.getParameter("startTime");
		String interTime = request.getParameter("interTime");
		String moveThreshold = request.getParameter("moveThreshold");
		String floIterTim = request.getParameter("floIterTim");
		String batLowThres = request.getParameter("batLowThres");
		String generalTime = request.getParameter("generalTime");
		String saveOrUpdate = request.getParameter("saveOrUpdate");
		String  result  = Setting(uniqueId,typeID,houOffset,startTime,interTime,moveThreshold,floIterTim,batLowThres,generalTime);
		String saved = null;
		String updated = null;

		if(saveOrUpdate.equals("save")){
			saved = dSettingsService.addDeviceSettings(uniqueId, typeID, houOffset, startTime, interTime, moveThreshold, floIterTim, batLowThres,generalTime);
		}else if(saveOrUpdate.equals("update")){
			updated = dSettingsService.updateDeviceSettings(uniqueId, typeID, houOffset, startTime, interTime, moveThreshold, floIterTim, batLowThres,generalTime);

		}
		System.out.println("saved----" + saved);
		System.out.println("updated----" + updated);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("saved", saved);
		jsonObj.put("updated", updated);
		jsonObj.put("result", result);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonObj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**根据设备号，查询抓擢列表
	 * @param uniqueId
	 * @return
	 */
	@RequestMapping(value="checkBatch",method=RequestMethod.POST)
	public void checkBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		String uniqueId = request.getParameter("uniqueId");
		JSONObject jsonObject = new JSONObject();
		List<Map<String,Object>> list = devicesService.checkBatch(uniqueId);
		if(list.size()>0){
			jsonObject.put("success", true);
			jsonObject.put("result",list);
		}else{
			jsonObject.put("success", false);
		}
		PrintWriter out;
		out = response.getWriter();
		out.print(jsonObject);
	}
	/**根据设备号删除抓擢列表
	 * @param uniqueId
	 * @return
	 */
	@RequestMapping(value="delBatch",method=RequestMethod.POST)
	public void delBatch(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding(Constants.ENCODE);
		response.setCharacterEncoding(Constants.ENCODE);
		String uniqueId = request.getParameter("uniqueId");
		String type = request.getParameter("type");
		JSONObject jsonObject = new JSONObject();
		Integer i = devicesService.delBatch(uniqueId,type);
		if(null != i){
			jsonObject.put("success", true);
		}else{
			jsonObject.put("success", false);
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
