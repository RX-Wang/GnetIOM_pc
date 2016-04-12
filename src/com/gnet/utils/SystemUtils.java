package com.gnet.utils;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.lang.management.MonitorInfo;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 系统监控工具 management 和底层虚拟机的关系是非常紧密的。其实，有一些的是直接依靠虚拟机提供的公 开 API 实现的，比如
 * JVMTI；而另外一些则不然，很大一块都是由虚拟机底层提供某些不公开 的 API / Native Code 提供的。这样的设计方式，保证了
 * management 包可以提供足够的信 息，并且使这些信息的提供又有足够的效率；也使 management 包和底层的联系非常紧密。
 * 
 * @author longgangbai
 * 
 */
public class SystemUtils {

	public static void main(String[] args) {
		getVMClassInformation();
	}

	/**
	 * ClassLoadingMXBean ClassLoadMXBean 包括一些类的装载信息， 比如有多少类已经装载 / 卸载（unloaded），
	 * 虚拟机类装载的 verbose 选项（即命令行中的 Java – verbose:class 选项）是否打开， 还可以帮助用户打开 /
	 * 关闭该选项。
	 */
	public static void getVMClassInformation() {
		List<GarbageCollectorMXBean> gcMXBeanList = ManagementFactory.getGarbageCollectorMXBeans();
		// 相对于开放人员对 GC 的关注程度来说，该 mxbean 提供的信息十分有限，仅仅提供了 GC 的次数和 GC 花费总时间的近似值。
		for (GarbageCollectorMXBean gcMXBean : gcMXBeanList) {
			// 内存池名称
			String[] poolNames = gcMXBean.getMemoryPoolNames();
			for (String poolName : poolNames) {
				System.out.println("poolNames=" + poolName);
			}
		}
		// 提供了内存管理类和内存池（memory pool）的名字信息。
		List<MemoryManagerMXBean> memoryMgrMXBeanList = ManagementFactory.getMemoryManagerMXBeans();
		// 内存管理器的信息
		for (MemoryManagerMXBean memoryManagerMXBean : memoryMgrMXBeanList) {
			String[] poolNames = memoryManagerMXBean.getMemoryPoolNames();
			for (String poolName : poolNames) {
				System.out.println("poolNames=" + poolName);
			}
		}
		// 内存信息
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		// java堆得使用情况信息
		MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		long usaged = heapMemoryUsage.getUsed();
		System.out.println("java 内存堆使用内存：" + usaged);
		long maxUsage = heapMemoryUsage.getMax();
		System.out.println("java 内存堆最大使用内存：" + maxUsage);
		long initUsage = heapMemoryUsage.getInit();
		System.out.println("java 内存堆初始化时占用内存：" + initUsage);
		List<MemoryPoolMXBean> memoryPoolMXBeanList = ManagementFactory.getMemoryPoolMXBeans();
		// 该信息提供了大量的信息。在 JVM 中，可能有几个内存池，因此有对应的内存池信息，因此，在工厂类中
		// ，getMemoryPoolMXBean() 得到是一个 MemoryPoolMXBean 的 list。每一个
		// MemoryPoolMXBean 都包含
		// 了该内存池的详细信息，如是否可用、当前已使用内存 / 最大使用内存值、以及设置最大内存值等等。
		for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeanList) {
			// 内存池的名称
			String poolName = memoryPoolMXBean.getName();
			System.out.println("poolName " + poolName);
			// 内存管理器的名称
			String[] memoryMgrNames = memoryPoolMXBean.getMemoryManagerNames();
			for (String mgrName : memoryMgrNames) {
				System.out.println("内存管理器的名称：" + mgrName);
			}
			// java JVM最近内存的使用情况
			MemoryUsage memoryUsage = memoryPoolMXBean.getCollectionUsage();
			if (memoryUsage != null) {
				System.out.println(memoryUsage.getUsed());
				System.out.println("内存池的收集器内存使用率：" + memoryUsage.getUsed() / memoryUsage.getMax() + "%");
				memoryPoolMXBean.getCollectionUsageThreshold();

				memoryPoolMXBean.getCollectionUsageThresholdCount();
				MemoryType memoryType = memoryPoolMXBean.getType();
				System.out.println("内存的信息：" + memoryType.name());
				MemoryUsage memoryUage = memoryPoolMXBean.getUsage();
				System.out.println("内存池的内存使用率：" + memoryUage.getUsed() / memoryUage.getMax() + "%");
			}
		}

	}

}
