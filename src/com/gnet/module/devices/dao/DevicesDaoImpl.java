package com.gnet.module.devices.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.PerSingleton;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gnet.utils.DateUtil;
import com.gnet.utils.UUIDUtil;
import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;

@Repository(value = "devicesDao")
public class DevicesDaoImpl implements IDevicesDao {

	protected final Logger logger = Logger.getLogger(getClass());

	@Resource(name="jdbcTemplate1")
	private JdbcTemplate jdbcTemplate1;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 绑定设备
	 */
	@Override
	public boolean addBindDevice(String userName, String nickName,
			String userPhone, String userType, String groupid,
			String userAddress, String description, String binderType,
			String uniqueId, String securityCode, String simNumber,
			String devicesTypeId, String linkman) {
		try {
			int count = jdbcTemplate
					.update("insert into tab_user_devices values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
							new Object[] { UUIDUtil.getUUID(), userName,
									nickName, userPhone, userType, groupid,
									userAddress, description, binderType,
									uniqueId, securityCode, simNumber,
									devicesTypeId, "1", "1", linkman,
									DateUtil.getCurrentDateTime(),userName });
			if (count == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("绑定设备异常：" + e.toString());
			return false;
		}
	}

	/**
	 * 查询设备与集团用户是否已绑定
	 */
	/*@Override
	public boolean checkIsExist(String companyId, String uniqueId) {
		try {
			List<Map<String, Object>> list = jdbcTemplate
					.queryForList(
							"select count(*) from users_devices where userName = ? and uniqueId = ?",
							new Object[] { companyId, uniqueId });
			int count = Integer.parseInt((String) list.get(0).get("count(*)"));
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("查询设备与集团用户是否绑定异常：" + e.toString());
			return false;
		}
	}*/

	/**
	 * 授权
	 */
	@Override
	public boolean addAuthorize(String uniqueId, String securityCode,
			String account, String binderType,String companyId) {
		try {
			int count = jdbcTemplate
					.update("insert into tab_user_devices(id,userName,nickName,binderType,uniqueId,devicesSecCode,permission_sign,devicesState,addDate,companyId) values(?,?,?,?,?,?,?,?,?,?)",
							new Object[] { UUIDUtil.getUUID(), account,"",binderType,uniqueId,
									securityCode,"0","1", DateUtil.getCurrentDateTime(),companyId });
			if (count == 1) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			logger.error("给账号授权异常:" + e.toString());
			return false;
		}
	}

	/**
	 * 更新本地设备与用户的绑定信息
	 */
	@Override
	public boolean updateBindDevice(String dev_userId, String companyId,
			String nickName, String userPhone, String userType, String groupid,
			String userAddress, String description, String uniqueId,
			String securityCode, String simNumber, String devicesTypeId,
			String linkman) {
		try {
			String sql = "update tab_user_devices set userName = ?, nickName = ?, userPhone = ?, userType = ?, groupId = ?, userAddress = ?, description = ?, uniqueId = ?, devicesSecCode = ?, simNumber = ?, devicesTypeId = ?, linkman = ? where id = ?";
			int count = jdbcTemplate.update(sql, new Object[] { companyId,
					nickName, userPhone, userType, groupid, userAddress,
					description, uniqueId, securityCode, simNumber,
					devicesTypeId, linkman, dev_userId });
			if (count == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("更新本地设备与用户绑定信息异常：" + e.toString());
			return false;
		}
	}

	/**
	 * 查询授权账号是否已经与设备绑定
	 */
	@Override
	public boolean checkIsExist(String accountType, String authAccount,
			String uniqueId) {
		try {
			List<Map<String, Object>> list = jdbcTemplate
					.queryForList(
							"select count(*) from tab_user_devices where binderType = ? and userName = ? and uniqueId = ?",
							new Object[] { accountType,authAccount, uniqueId });
			int count = Integer.parseInt(String.valueOf(list.get(0).get("count(*)")));
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("查询授权账号是否与设备绑定异常："+e.toString());
			return false;
		}
	}

	/**
	 * 查询当前用户有无授权的权限
	 */
	@Override
	public String selDevPermission_sign(String userName, String uniqueId) {
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select permission_sign from tab_user_devices where userName = ? and uniqueId = ?", new Object[]{userName,uniqueId});
			
			String permission_sign =  (String) list.get(0).get("permission_sign");
			
			if(!"".equals(permission_sign) && null != permission_sign){
				return permission_sign;
			}else{
				return "";
			}
		} catch (Exception e) {
			
			logger.error("查询用户权限异常："+ e.toString());
			return "";
		}
	}

	/**
	 * 查询当前设备的权限
	 */
	@Override
	public String checkPermission_sign(String id, String companyId) {
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select permission_sign from tab_user_devices where id = ? and userName = ?", new Object[]{id, companyId});
			
			if(list != null && list.size()>0){
				return list.get(0).get("permission_sign").toString();
			}else {
				return null;
			}
		} catch (Exception e) {
			logger.error("查询用户操作权限异常："+e.toString());
			return null;
		}
	}

	@Override
	public String selPermissionSign(String userName, String uniqueId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据记录id查询设备信息
	 */
	@Override
	public List<Map<String, Object>> checkDeviceInfo(String id) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("select * from tab_user_devices where id = ?", new Object[]{id});
			
			if(list != null && list.size()>0){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("查询设备信息异常："+e.toString());
			return null;
		}
	}

	/**
	 * 删除设备与用户的绑定
	 */
	@Override
	public boolean delDeviceUser(String id, String uniqueId) {
		boolean flag = false;
		try {
			if(!"".equals(uniqueId) && null != uniqueId && (id.equals("")||null == id)){
				//删除主控
				int count = jdbcTemplate.update("delete from tab_user_devices where uniqueId = ?", new Object[]{uniqueId});
				if(count>0){
					flag = true;
				}
			}else{
				//删除非主控
				int count = jdbcTemplate.update("delete from tab_user_devices where id = ?", new Object[]{id});
				if(count == 1){
					flag = true;
				}
			}
		} catch (Exception e) {
			logger.error("删除设备与用户绑定异常："+e.toString());
		}
		return flag;
	}

	/**
	 *  根据设备id查询设备绑定的用户
	 */
	@Override
	public List<Map<String, Object>> selBindUserByUniqueId(String uniqueId,String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			list = jdbcTemplate.queryForList("select * from tab_user_devices where uniqueId = ? and userName <> ?", new Object[]{uniqueId,companyId});
//			list = jdbcTemplate.queryForList("select * from tab_user_devices where uniqueId = ?", new Object[]{uniqueId});
			
		} catch (Exception e) {
			logger.error("查询设备绑定的用户异常："+e.toString());
		}
		return list;
	}

	/**
	 * 查询集团是否存在
	 */
	@Override
	public boolean checkCompanyIsExist(String account) {
		boolean flag = false;
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select count(*) from tab_company where companyId = ?", new Object[]{account});
			
			int count = Integer.parseInt(String.valueOf(list.get(0).get("count(*)")));
			if(count == 1){
				flag = true;
			}else{
				flag = false;
			}
		} catch (Exception e) {
			logger.error("查询集团是否存在异常："+e.toString());
		}
		return flag;
	}

	/**
	 * 追踪
	 */
	@Override
	public List<Map<String, Object>> beginTrack(String uniqueId, String curTime) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("select * from tab_trace where uniqueId = ? and sendTime >= ? order by sendTime asc", new Object[]{uniqueId,curTime});
			
		} catch (Exception e) {
			logger.error("查询设备轨迹失败");
		}
		return list;
	}

	/**
	 * 保存历史轨迹
	 */
	@Override
	public boolean saveTrice(String beginTime, String endTime, String uniqueId,String companyId) {
		boolean flag = false;
		
		try {
			int count = jdbcTemplate.update("insert into tab_history_trace values (?,?,?,?,?)", new Object[]{UUIDUtil.getUUID(),uniqueId,beginTime,endTime,companyId});
			if(count == 1){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("保存历史轨迹异常："+e.toString());
		}
		return flag;
	}

	/**
	 * 根据设备id查询该设备的所有历史轨迹
	 */
	@Override
	public List<Map<String, Object>> selHistoryTrace(String uniqueId,String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			list = jdbcTemplate.queryForList("select * from tab_history_trace where uniqueId = ? and companyId = ?", new Object[]{uniqueId,companyId});

		} catch (Exception e) {
			logger.error("查询设备的所有历史轨迹异常："+e.toString());
		}
		return list;
	}

	/**
	 * 根据记录id删除历史轨迹
	 */
	@Override
	public boolean delHistoryTrace(String id) {
		boolean flag = false;
		
		try {
			int count = jdbcTemplate.update("delete from tab_history_trace where id = ?", new Object[]{id});
			
			if(count == 1){
				flag = true;
			}
			
		} catch (Exception e) {
			logger.error("删除历史轨迹记录异常:"+e.toString());
		}
		return flag;
	}


	/**
	 * 根据时间段查询历史轨迹记录
	 */
	@Override
	public List<Map<String, Object>> checkHistoryTrace(String beginTime,
			String endTime, String uniqueId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("select * from tab_trace where uniqueId = ? and sendTime between ? and ? order by sendTime asc", new Object[]{uniqueId,beginTime,endTime});
			
		} catch (Exception e) {
			logger.error("查询历史轨迹异常："+e.toString());
		}
		return list;
	}

	/**
	 * 根据用户名和设备串号查询设备信息
	 */
	@Override
	public List<Map<String, Object>> getDeviceInfoByUniqueId(String uniqueId,
			String companyId) {
		List< Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("select * from tab_user_devices where uniqueId=? and userName=?", new Object[]{uniqueId,companyId});
			
		} catch (Exception e) {
			logger.error("查询设备信息异常："+e.toString());
		}
		return list;
	}

	/**
	 * 查询当前用户下是否有该设备
	 */
	@Override
	public boolean checkDeviceIsExist(String uniqueId, String companyId) {
		boolean flag = false;
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select count(*) from tab_user_devices where userName = ? and uniqueId = ?", new Object[]{companyId,uniqueId});
			int count = Integer.parseInt(String.valueOf(list.get(0).get("count(*)")));
			if(count>0){
				flag = true;
			}
			
		} catch (Exception e) {
			logger.error("查询当前用户下是否有该设备异常："+e.toString());
		}
		return flag;
	}

	@Override
	public String findTypeIdbyUniqueId(String uniqueId) {
		String typeId ="";
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate1.queryForList("SELECT `devices_type_id` FROM `devices_info` WHERE `unique_id`='"+uniqueId+"'");
			typeId = String.valueOf(list.get(0).get("devices_type_id"));
		} catch (Exception e) {
			logger.error("findTypeIdbyUniqueId异常"+e.toString());
			typeId = "";
		}
		return typeId;
	}

	@Override
	public String findFloIterTimbyUniqueId(String uniqueId) {
		String floIterTim ="20";
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("SELECT `floIterTim` FROM `tab_devicesettings_msg` WHERE `uniqueId`='"+uniqueId+"'");
			floIterTim = String.valueOf(list.get(0).get("floIterTim"));
			if ("".equals(floIterTim)||null==floIterTim) {
				floIterTim ="20";
			}
		} catch (Exception e) {
			logger.error("findFloIterTimbyUniqueId异常"+e.toString());
			floIterTim = "20";
		}
		return floIterTim;
	}

	@Override
	public String findGeneralTimebyUniqueId(String uniqueId) {
		String generalTime ="90";
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("SELECT `generalTime` FROM `tab_devicesettings_msg` WHERE `uniqueId`='"+uniqueId+"'");
			generalTime = String.valueOf(list.get(0).get("generalTime"));
			if ("".equals(generalTime)||null==generalTime) {
				generalTime ="90";
			}
		} catch (Exception e) {
			logger.error("findGeneralTimebyUniqueId异常"+e.toString());
			generalTime = "90";
		}
		return generalTime;
	}

	@Override
	public String findIsOnlinebyUniqueId(String uniqueId) {
		String isOnline="1";
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("SELECT isonline FROM `isonline` WHERE `uniqueId` ='"+uniqueId +"'");
			isOnline = String.valueOf(list.get(0).get("isonline"));
			if ("".equals(isOnline)||null==isOnline) {
				isOnline ="0";
			}
		} catch (Exception e) {
			logger.error("findGeneralTimebyUniqueId异常"+e.toString());
			isOnline ="0";
		}
		return isOnline;
	}

	/**
	 * 根据设备号或名称查询设备信息
	 */
	@Override
	public List<Map<String, Object>> selDevByIdOrName(String nameOrId,
			String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			list = jdbcTemplate.queryForList("select * from tab_user_devices where userName=? and companyId = ? and (nickName = ? or uniqueId = ?)", new Object[]{companyId,companyId,nameOrId,nameOrId});
			
		} catch (Exception e) {
			logger.error("根据设备号或名称查询设备信息异常："+e.toString());
			System.out.println("根据设备号或名称查询设备信息异常："+e.toString());
		}
		return list;
	}

	/**
	 * 查询当前是设备是否已经开启追踪模式
	 */
	@Override
	public List<Map<String, Object>> checkDevIsTrackByUniqueId(String uniqueId, String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			
			list = jdbcTemplate.queryForList("select * from tab_devtrack where uniqueId=? and companyId=?", new Object[]{uniqueId,companyId});
			
		} catch (Exception e) {
			System.out.println("查询当前设备是否已经开启追踪模式异常："+e.getMessage());
			logger.error("查询当前设备是否已经开启追踪模式异常："+e.getMessage());
		}
		return list;
		
	}

	/**
	 * 开启追踪模式
	 */
	@Override
	public boolean insertDevTrack(String uniqueId, String nickName,
			String curTime, String companyId) {
		boolean flag = false;
		
		try {
			int count = jdbcTemplate.update("insert into tab_devtrack values(?,?,?,?,?,?)", new Object[]{UUIDUtil.getUUID(),uniqueId,nickName,curTime,"",companyId});
			if(count>0){
				flag = true;
			}
			
		} catch (Exception e) {
			System.out.println("开启设备追踪模式异常："+e.getMessage());
			logger.error("开启设备追踪模式异常："+e.getMessage());
			
		}
		return flag;
		
	}

	/**
	 *  查询当前集团下已经开启追踪模式的设备
	 */
	@Override
	public List<Map<String, Object>> selAllDevTrack(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			list = jdbcTemplate.queryForList("select * from tab_devtrack where companyId = ? order by beginTime desc", new Object[]{companyId});
		} catch (Exception e) {
			logger.error("查询当前集团下所有已经开启追踪模式的设备异常："+e.getMessage());
		}
		return list;
	}

	/**
	 * 关闭追踪模式
	 */
	@Override
	public boolean closeDevTrack(String uniqueId, String companyId) {
		boolean flag = false;
		try {
			int count = jdbcTemplate.update("delete from tab_devtrack where uniqueId=? and companyId=?", new Object[]{uniqueId,companyId});
			if(count>0){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("关闭追踪模式异常："+e.getMessage());
		}
		return flag;
	}

	@Override
	public List<Map<String, Object>> checkBatch(String uniqueId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			list = jdbcTemplate.queryForList("SELECT * FROM `catchzhuolist` WHERE uniqueId = ?",new Object[]{uniqueId});
		}catch(Exception e){
			logger.error("checkBatch 查询抓擢列表异常：" + e.toString());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer delBatch(String uniqueId, String type) {
		Integer i = null;
		try{
			i = jdbcTemplate.update("delete from `catchzhuolist` where uniqueId = ? and type = ?",new Object[]{uniqueId,type});
		}catch(Exception e ){
			logger.error("delBatch 删除抓擢列表异常：" + e.toString());
			e.printStackTrace();
		}
		return i ;
	}

	@Override
	public String saveCatchzhuolist(String uniqueId, String atInstruction,
			 String type) {
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select count(1) from catchzhuolist where type = ? and uniqueId = ?", new Object[]{type,uniqueId});
			int count = Integer.parseInt(String.valueOf(list.get(0).get("count(1)")));
			if(count>0){
				String sql = "update catchzhuolist set atInstruction = ? where type = ? and uniqueId = ?";
				int count1 = jdbcTemplate.update(sql, new Object[] { atInstruction,type,uniqueId});
				if (count1 == 1) {
					return "1";
				} else {
					return "0";
				}
			}else {
				int count1 = jdbcTemplate.update("insert into catchzhuolist(uniqueId,atInstruction,type) values (?,?,?)", new Object[]{uniqueId,atInstruction,type});
				if (count1 == 1) {
					return "1";
				} else {
					return "0";
				}
			}
			
		} catch (Exception e) {
			logger.error("saveCatchzhuolist异常："+e.toString());
			return "0";
		}
	}

	/**
	 * 查询当前集团账号下所有的设备数量
	 */
	@Override
	public int selTotalDevNum(String companyId) {
		int count = 0;
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select count(*) from tab_user_devices where userName = ?", new Object[]{companyId});	
			
			count = Integer.parseInt(String.valueOf(list.get(0).get("count(*)")));
			
		} catch (Exception e) {
			logger.error("查询当前集团账号下所有的设备数量异常： "+e.getMessage());
		}
		return count;
	}

	/**
	 * 查询开启追踪模式的设备信息
	 */
	@Override
	public List<Map<String, Object>> selectTrackDev(String uniqueId,
			String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			
			list = jdbcTemplate.queryForList("select * from tab_devtrack where uniqueId = ? and companyId = ?", new Object[]{uniqueId,companyId});
		} catch (Exception e) {
			logger.error("查询开启追踪模式的设备信息异常："+e.getMessage());
		}
		return list;
	}

	/**
	 * 查询已经保存历史轨迹的设备信息
	 */
	@Override
	public List<Map<String, Object>> selDevHasHistoryTrack(String companyId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = jdbcTemplate.queryForList("select distinct uniqueId from tab_history_trace where companyId=?", new Object[]{companyId});
			
		} catch (Exception e) {
			logger.error("查询已经保存历史轨迹的设备信息异常："+e.getMessage());
		}
		return list;
	}
}
