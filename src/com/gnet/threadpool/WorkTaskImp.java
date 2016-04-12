package com.gnet.threadpool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.gnet.message.DBUtils;
import com.gnet.utils.QueueSend;


/**
 * 任务类1 正常执行的工作任务
 */
public class WorkTaskImp implements WorkTask {
	private static Logger logger = Logger.getLogger(WorkTaskImp.class);
	// protected String param;
	protected String uniqueId;

	public WorkTaskImp() {
	}

	public WorkTaskImp(String uniqueId) {

		this.uniqueId = uniqueId;
	}
	
	public boolean Send(String uniqueId,String atInstruction) {
		boolean result=true;
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("uniqueId", uniqueId);
			jsonObj.put("transportType", "TCP");
			jsonObj.put("content", atInstruction);
			result = QueueSend.produce(jsonObj.toString());
			System.out.println("回控指令发送结果" + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result=false;
			e.printStackTrace();
		}
		return result;

	}
	@Override
	public void runTask() {
		// final AsyncClientHttpExchange asyncClientHttpExchange = new
		// AsyncClientHttpExchange();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int count = DBUtils.findCatchzhuolistCount(uniqueId);
					if (count > 0) {
						List<String> Catchzhuolist = DBUtils.queryCatchzhuolist(uniqueId);
						for (int i = 0; i < Catchzhuolist.size(); i++) {
							String AT=Catchzhuolist.get(i);
							Thread.sleep(5000);
							boolean sendResult = Send(uniqueId,AT);
							System.out.println("sendResult=="+sendResult);
							if (sendResult) {
								int result = DBUtils.deleteCatchzhuolistCountAndType(uniqueId,AT);
								if (result>0) {
									System.out.println("设备上线了,读抓擢列表,发送命令成功!删除抓擢列表对应数据成功!!!!!");
									//Log.info("设备上线了,读抓擢列表,发送命令成功!删除抓擢列表对应数据成功!!!!!"+AT);
								}
							}else {
								System.out.println("设备上线了,读抓擢列表,发送命令失败!删除抓擢列表对应数据失败!!!!!");
							}
							Thread.sleep(5000);
						}
					}else {
						System.out.println("设备上线了,读抓擢列表,没有需要执行的命令");
						//Log.info("设备上线了,读抓擢列表,没有需要执行的命令00000");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void cancelTask() {
		// TODO
	}

	@Override
	public int getProgress() {
		return 0;
	}
}
