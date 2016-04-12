package com.gnet.threadpool;


/**
 * 任务检测线程类 检测空闲线程并检测待运行任务
 */
public final class TaskMonitorThread extends Thread {
  
    public TaskMonitorThread() { }
    
    @Override
    public void run() {
        while (true) {
            try {
                WorkTask task = TaskManager.getTask();
                if (task == null) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Worker t = PoolManager.getInstance().getIdleThread();// 获取空闲线程
                    if (t == null)
                        break;
                     t.setWorkTask(task);// 设置线程任务
                     t.setIsRunning(true);//激活空闲线程
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
