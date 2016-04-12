package com.gnet.threadpool;

import java.util.ArrayList;

/*
 *  线程池管理器（PoolManager）:用于创建并管理线程池，采用单例模式
 * 
 */
public class PoolManager {
    public static PoolManager mPool;
    public final int max_pool =5;
    public final int max_Tasks = 15;
    public static ArrayList<Worker> init_pools;
    private TaskMonitorThread mainThread;//任务监测线程

    static {
        init_pools = new ArrayList(1);
    }

    public static PoolManager getInstance() {
        if (mPool == null) {
            mPool = new PoolManager();
        }

        return mPool;
    }
    
    
    
    //获取空闲线程
    public  Worker getIdleThread(){
        Worker working =null;
        while(true){
            synchronized(init_pools){
                for (int i = 0; i < max_pool; i++) {
                    working = init_pools.get(i);
                    if (!working.isrunning) {
                        return working;
                    }
                }
            }
            try {
                Thread.sleep(5000);//放弃CPU,若干时间后重新获取空闲线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }        
    }
    public void init() {
        Worker worker = null;
        for (int i = 0; i < this.max_pool; i++) {
            worker = new Worker("initThread"+i);
            init_pools.add(worker);
            worker.start();
        }
        mainThread=new  TaskMonitorThread();
        mainThread.start();
    }
}