package com.gnet.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JavaUtil {

	public static Map<String, Long> getMemoryInfo(){
		Map<String, Long> _mapResult = new HashMap<String, Long>();
		Runtime _runtime = Runtime.getRuntime();
		_mapResult.put("totalMemory", _runtime.totalMemory()/1024/1024);
		_mapResult.put("freeMemory", _runtime.freeMemory()/1024/1024);
		_mapResult.put("maxMemory", _runtime.maxMemory()/1024/1024);
		return _mapResult;
	}

	public static void main(String[] args) throws Exception {
		MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
		MemoryUsage usage = memorymbean.getHeapMemoryUsage();

		System.out.println("INIT HEAP: " + usage.getInit()/1024/1024); 
		System.out.println("MAX HEAP: " + usage.getMax()/1024/1024); 
		System.out.println("USE HEAP: " + usage.getUsed()/1024/1024); 
		
		usage = memorymbean.getNonHeapMemoryUsage();
		System.out.println("INIT HEAP: " + usage.getInit()/1024/1024); 
		System.out.println("MAX HEAP: " + usage.getMax()/1024/1024); 
		System.out.println("USE HEAP: " + usage.getUsed()/1024/1024); 
		
		List<MemoryPoolMXBean> _lstMemoryPoolMXBean = ManagementFactory.getMemoryPoolMXBeans();
		for (MemoryPoolMXBean memoryPoolMXBean : _lstMemoryPoolMXBean) {
			usage = memoryPoolMXBean.getCollectionUsage();
			if(usage!=null)
				System.out.println(usage.getInit());
		}
	}
}
