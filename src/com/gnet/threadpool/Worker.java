package com.gnet.threadpool;

/**
 * 工作线程（WorkThread）: 线程池中线程
 *
 */
public class Worker extends Thread {
    public boolean isrunning=false;
    private WorkTask nowTask; // 当前任务
    private Object threadTag;// 线程标识
    //获取线程标识key
    public Object getThreadTag() {
        return threadTag;
    }      
    public synchronized void setWorkTask(WorkTask task) {
        this.nowTask = task;
    }

    public synchronized void setIsRunning(boolean flag) {
        this.isrunning = flag;
        if (flag) {
            this.notify();
        }
    }

    public Worker(Object key) {
        this.threadTag = key;       
    }

    public boolean getIsrunning() {
        return isrunning;
    }

    public synchronized void run() {
        while (true) {
            if (!isrunning) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                    nowTask.runTask();
                    setIsRunning(false);
            }
        }
    }
}
