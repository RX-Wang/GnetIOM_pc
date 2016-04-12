package com.gnet.message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gnet.utils.PropertiesUtil;
import com.gnet.utils.UUIDUtil;

/**
 * 将接收到的消息写入到数据库中
 * @author admin
 *
 */
public final class DBUtils {  
	
	
    private static String url = "";  
    private static String user = "";  
    private static String psw = "";  
    private static String driver = "";
     
	private static Logger logger = Logger.getLogger(DBUtils.class);
//    private static Connection  conn;  
      
    static {  
        try { 
        	url = PropertiesUtil.getKeyValue("URL");
        	user = PropertiesUtil.getKeyValue("USER");
        	psw = PropertiesUtil.getKeyValue("PSW");
        	driver = PropertiesUtil.getKeyValue("DRIVER");
            
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
      
    private DBUtils() {  
          
    }  
      
    /** 
     * 获取数据库的连接 
     * @return conn 
     */  
    public static Connection getConnection() {  
    	Connection conn = null;
        if(null == conn) {  
            try {  
            	Class.forName(driver); 
                conn = DriverManager.getConnection(url, user, psw);  
            } catch (Exception e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            }  
        }  
        return conn;  
    }  
      
    /** 
     * 释放资源 
     * @param conn 
     * @param pstmt 
     * @param rs 
     */  
    public static void closeResources(ResultSet rst,Connection conn,PreparedStatement pstmt) {  
        try {
        	if(rst!=null){
        		rst.close();
        	}
			if (pstmt!=null) {
				pstmt.close();
				
			}
			if (conn!=null) {
				conn.close();
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public static void insertMsg(String devicesName, String uniqueId,
			String alarmType, String type, String address, String sendTime,
			String longitude, String latitude,String handle,String alarmTypeMsg) {
    		PreparedStatement ps=null;
    		Connection conn = null;
			try {
				conn = DBUtils.getConnection();
				String sql = "insert into tab_message values(?,?,?,?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, UUIDUtil.getUUID());
				ps.setString(2, devicesName);
				ps.setString(3, uniqueId);
				ps.setString(4, alarmType);
				ps.setString(5, type);
				ps.setString(6, address);
				ps.setString(7, sendTime);
				ps.setString(8, longitude);
				ps.setString(9, latitude);
				ps.setString(10, handle);
				ps.setString(11, alarmTypeMsg);
				int count = ps.executeUpdate();
				if(count == 1){
					System.out.println("消息插入数据库成功");
					logger.info("消息写入数据库操作成功");
				}else{
					System.out.println("消息插入数据库失败");
					logger.info("消息写入数据库操作失败");
				}
			}catch(Exception e){
				System.out.println("消息插入数据库异常："+e.toString());
				logger.info("消息写入数据库操作异常："+e.getMessage());
			}finally{
				closeResources(null,conn, ps);
			}
    }

    /**
     * 将轨迹信息写入数据库
     * @param uniqueId
     * @param devicesName
     * @param type
     * @param address
     * @param sendTime
     * @param longitude
     * @param latitude
     * @param power
     */
	public static void insertTrace(String uniqueId, String devicesName,
			String type, String address, String sendTime, String longitude,
			String latitude, String power) {
		
		PreparedStatement ps=null;
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			String sql = "insert into tab_trace values(?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, UUIDUtil.getUUID());
			ps.setString(2, uniqueId);
			ps.setString(3, devicesName);
			ps.setString(4, type);
			ps.setString(5, address);
			ps.setString(6, sendTime);
			ps.setString(7, longitude);
			ps.setString(8, latitude);
			ps.setString(9, power);
			int count = ps.executeUpdate();
			if(count == 1){
				System.out.println("轨迹信息插入数据库成功");
				logger.info("轨迹写入数据库操作成功");
			}else{
				System.out.println("轨迹插入数据库失败");
				logger.info("轨迹写入数据库操作失败");
			}
		}catch(Exception e){
			System.out.println("轨迹插入数据库异常："+e.toString());
			logger.info("轨迹写入数据库操作异常："+e.getMessage());
		}finally{
			closeResources(null,conn, ps);
		}
	}
	
	 /**
     * 将是否在线情况写入数据库
     * @param uniqueId
     * @param isOnline
     */
	public static void insertisOnline(String uniqueId, String isOnline) {
		
		PreparedStatement ps=null;
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			String sql1 = "SELECT COUNT(1) FROM `isonline` WHERE `uniqueId` ='"+uniqueId +"'"; // 查询数据的sql语句
			Statement st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
			ResultSet rs = st.executeQuery(sql1); // 执行sql查询语句，返回查询数据的结果集
			rs.next();
			int count1 = rs.getInt(1);
			if (count1==0) {
				String sql = "insert into isonline(uniqueId,isOnline) values(?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, uniqueId);
				ps.setString(2, isOnline);
				int count = ps.executeUpdate();
				if(count == 1){
					System.out.println("是否在线信息插入数据库成功");
					logger.info("是否在线信息写入数据库操作成功");
				}else{
					System.out.println("是否在线信息插入数据库失败");
					logger.info("是否在线信息写入数据库操作失败");
				}
			}else {
				String sql ="UPDATE isonline SET `isOnline`='"+isOnline+"' WHERE `uniqueId` ='"+uniqueId+"'"; 
				st = (Statement) conn.createStatement();
				int count = st.executeUpdate(sql);
				if(count == 1){
					System.out.println("是否在线信息更新数据库成功");
					logger.info("是否在线信息更新数据库操作成功");
				}else{
					System.out.println("是否在线信息更新数据库失败");
					logger.info("是否在线信息更新数据库操作失败");
				}
			}
			
		}catch(Exception e){
			System.out.println("是否在线信息插入数据库异常："+e.toString());
			logger.info("是否在线信息写入数据库操作异常："+e.getMessage());
		}finally{
			closeResources(null,conn, ps);
		}
	}

	/**
	 * 设备自动开启追踪模式时写入追踪模式数据库
	 * @param uniqueId
	 * @param devicesName
	 * @param sendTime
	 */
	public static void insertDevTrack(String uniqueId, String devicesName,
			String sendTime) {

		PreparedStatement ps=null;
		Connection conn = null;
		try {
			//判断该设备是否已经开启追踪模式
			boolean flag = DBUtils.devIsTrack(uniqueId);
			
			if(flag){
				System.out.println("设备"+uniqueId+"已经开启追踪模式。。。。。。。。。");
				logger.info("设备"+uniqueId+"已经开启追踪模式。。。。。。。。。");
			}else{
				System.out.println("设备"+uniqueId+"还没有开启追踪模式，正在开启追踪模式。。。。。。。。。。。。。");
				String selCompanyId = DBUtils.selCompanyId(uniqueId);
				conn = DBUtils.getConnection(); 
				
				String sql = "insert into tab_devtrack values(?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql); 
				ps.setString(1, UUIDUtil.getUUID());
				ps.setString(2, uniqueId);
				ps.setString(3, devicesName);
				ps.setString(4, sendTime);
				ps.setString(5, "");
				ps.setString(6, selCompanyId);
				int count = ps.executeUpdate();
				if(count>0){
					logger.info("将自动开启追踪模式的设备写入数据库成功");
					System.out.println("将自动开启追踪模式的设备写入数据库成功");
				}else {
					logger.info("将自动开启追踪模式的设备写入数据库失败");
					System.out.println("将自动开启追踪模式的设备写入数据库失败");
					}
				}
		} catch (Exception e) {
			logger.error("将自动开启追踪模式的设备写入数据库异常："+e.getMessage());
			System.out.println("将自动开启追踪模式的设备写入数据库异常："+e.getMessage());
		}finally{
			closeResources(null,conn, ps);
		}
	}
	
	/**
	 * 查询设备是否已经开启追踪模式
	 * @param uniqueId
	 * @return
	 */
	private static boolean devIsTrack(String uniqueId) {
		Connection conn = null;
		PreparedStatement ps=null;
		try {
			System.out.println("============查询当前设备是否已经开启追踪模式============");
			conn = DBUtils.getConnection();
			String sql = "SELECT COUNT(1) FROM `tab_devtrack` WHERE `uniqueId` ='"+uniqueId +"'"; // 查询数据的sql语句
//			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
			ps = conn.prepareStatement(sql); 
			ResultSet rs = ps.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
			rs.next();
			int count1 = rs.getInt(1);
			if (count1>0) {
				//设备已经开启追踪模式
				System.out.println("==============当前设备已经开启追踪模式:true==========");
				return true;
			}else{
				System.out.println("==============当前设备已经没有开启追踪模式:false==========");
				return false;
			}
		} catch (Exception e) {
			System.out.println("===========查询设备是否已经开启追踪模式异常："+e.getMessage());
			logger.error("查询设备是否已经开启追踪模式异常："+e.getMessage());
			return false;
		}finally{
			closeResources(null,conn, ps);
		}
	}

//	public static void main(String[] args) {
////		for(int i=0;i<50;i++){
////			try {
//////				System.out.println(i);
//////				Thread.sleep(100);
//////				DBUtils.insertDevTrack("860599000057489","57489","2015-12-25 11:20:25");
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////			
////		}
//	}
	
	
	/**
	 * 根据设备号查询绑定该设备的集团账号id
	 * @param uniqueId
	 * @return
	 */
	public static String selCompanyId(String uniqueId){
		PreparedStatement ps=null;
		Connection conn = null;
		String companyId = "";
		try {
			conn = DBUtils.getConnection(); 
			String sql = "select companyId from tab_user_devices where uniqueId=? and permission_sign = '1'";
			ps = conn.prepareStatement(sql); 
			ps.setString(1, uniqueId);
			ResultSet rs = ps.executeQuery(); // 执行sql查询语句，返回查询数据的结果集
			while (rs.next()) {
				companyId = rs.getString(1);
			}
			System.out.println("设备===="+uniqueId+"的主控用户是："+companyId);
		} catch (Exception e) {
			logger.error("查询设备的主控用户异常："+e.getMessage());
			System.out.println("查询设备的主控用户异常："+e.getMessage());
		}finally{
			closeResources(null,conn, ps);
		}
		
		return companyId;
	}
	
	 /**
     * 
     * @param uniqueId
     * @param isOnline
     */
	public static int findCatchzhuolistCount(String uniqueId) {
		PreparedStatement ps=null;
		Connection conn = null;
		int count=0;
		try {
			conn = DBUtils.getConnection();
			String sql = "SELECT COUNT(1) FROM `catchzhuolist` WHERE `uniqueId` ='"+uniqueId +"'"; // 查询数据的sql语句
			Statement st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
			ResultSet rs = st.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
			rs.next();
			count = rs.getInt(1);
			
		}catch(Exception e){
			System.out.println("是否在线信息插入数据库异常："+e.toString());
			logger.info("是否在线信息写入数据库操作异常："+e.getMessage());
		}finally{
			closeResources(null,conn, ps);
		}
		return count;
	}
	/*public static void main(String[] args) {
		insertTrace("1236548995223", "我的设备", "8", "北京市海淀区学院路30号", "2015-12-08 18:34:20", "36.52365", "56.6547", "25");
		System.out.println("完成..");
	}*/

	public static List<String>  queryCatchzhuolist(String uniqueId) {
		Connection conn = null;
		PreparedStatement ps=null;
		Statement st;
		try {
			conn = DBUtils.getConnection();
			List<String> list=new ArrayList<String>();
			String sql = "SELECT atInstruction FROM `catchzhuolist`  WHERE `uniqueId` ='"+uniqueId +"'"; // 查询数据的sql语句
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
			ResultSet rs = st.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
			while (rs.next()) { // 判断是否还有下一个数据
				String atInstruction = rs.getString("atInstruction");
				//String type = rs.getString("type");
				list.add(atInstruction);
				//list.add(type);
			}
			
			return list;
		} catch (SQLException e) {
			System.out.println("queryCatchzhuolist失败");
			e.printStackTrace();
			return null;
		}finally{
			closeResources(null,conn, ps);
		}
	}
	
	public static int deleteCatchzhuolistCountAndType(String uniqueId,String atInstruction) {
		Connection conn = null;
		PreparedStatement ps=null;
		Statement st;
		try {
			conn = DBUtils.getConnection();
			String sql = "DELETE FROM `catchzhuolist` WHERE `uniqueId` ='"+uniqueId +"' AND `atInstruction` ='"+atInstruction +"'"; // 查询数据的sql语句
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象
			int count = st.executeUpdate(sql); // 执行插入操作的sql语句，并返回插入数据的个数
			return count;
		} catch (SQLException e) {
			System.out.println("deleteCatchzhuolistCountAndType失败");
			e.printStackTrace();
			return 0;
		}finally{
			closeResources(null,conn, ps);
		}
	}
}  
