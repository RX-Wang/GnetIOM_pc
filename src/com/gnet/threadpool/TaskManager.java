package com.gnet.threadpool;

import java.util.LinkedList;


/**
 *  任务管理器
 *  1、添加任务
 *  2、监测是否有新任务
 */
public class TaskManager {
    public static LinkedList<WorkTask> workqueue =new LinkedList<WorkTask>();// 缓冲队列
    /**
     * 向工作队列中加入一个任务，由工作线程去执行该任务
     * 
     * @param task
     */
    public synchronized static void addTask(WorkTask worktask) {
        if (worktask != null &&workqueue.size()<15) {
            workqueue.add(worktask);
        }
    }
   

    /**
     * 从工作队列中取出一个任务
     * 
     * @return
     * @throws InterruptedException
     */
    public synchronized static WorkTask getTask() throws InterruptedException {
        while (workqueue.size() >0) {
            return (WorkTask) workqueue.removeFirst();
        }
        return null;
    }
}