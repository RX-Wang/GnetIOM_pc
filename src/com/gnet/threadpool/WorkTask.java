package com.gnet.threadpool;

/**
 * 任务接口 
 * 继承它来定义自己具体的工作任务
 */
public interface WorkTask {
     public void runTask();//执行工作任务
     public void cancelTask();
     public int getProgress();
}
