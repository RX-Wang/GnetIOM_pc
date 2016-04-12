package com.gnet.threadpool;


/**
 * 调用工具类
 * @author admin
 *
 */

public class ThreadPoolUtils {
	private static PoolManager pool;
	
	static{
		pool=new PoolManager();
		pool.init();
	}
	
	public ThreadPoolUtils() {}

	public static void testPool(String uniqueId){
        WorkTask task=new WorkTaskImp(uniqueId); 
        TaskManager.addTask(task);
	}
}
