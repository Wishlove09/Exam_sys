package edu.xmut.examsys.listener;

import org.quartz.*;

/**
 * @author: Laity
 * @Project: JavaLaity
 * @Description: 全局监听器 - 接收所有的Trigger/Job的事件通知
 */
public class MyJobListener implements SchedulerListener {

    @Override
    public void jobScheduled(Trigger trigger) {
        // 用于部署JobDetail时调用
        System.out.println("用于部署JobDetail时调用==>" + trigger.getKey().getName());
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        // 用于卸载JobDetail时调用
        System.out.println(triggerKey + "=> 完成卸载");
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        // 当endTime到了就会执行
        System.out.println("触发器被移除：" + trigger.getKey());
        TriggerKey key = trigger.getKey();
        if (key.getName().equals("trigger1") && key.getGroup().equals("group1")) {
            System.out.println("执行发送短信任务");
        }

    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        System.out.println(triggerKey + "触发器正在被暂停");
    }

    @Override
    public void triggersPaused(String s) {
        // s = triggerGroup
        System.out.println("触发器组：" + s + "，正在被暂停");
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        System.out.println(triggerKey + "正在从暂停中恢复");
    }

    @Override
    public void triggersResumed(String s) {
        System.out.println("触发器组：" + s + "，正在从暂停中恢复");
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        System.out.println(jobDetail.getKey() + "=>已添加工作任务");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        System.out.println(jobKey + "=> 已删除该工作任务");
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        System.out.println(jobKey + "=> 工作任务正在被暂停");
    }

    @Override
    public void jobsPaused(String s) {
        System.out.println("工作任务组：" + s + ",正在被暂停");
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        System.out.println(jobKey + "=> 正在从暂停中恢复");
    }

    @Override
    public void jobsResumed(String s) {
        System.out.println("工作任务组：" + s + "，正在从暂停中恢复");
    }

    @Override
    public void schedulerError(String s, SchedulerException e) {

    }

    @Override
    public void schedulerInStandbyMode() {

    }

    @Override
    public void schedulerStarted() {
        System.out.println("=============================监听已开始===========================");
    }

    @Override
    public void schedulerStarting() {
        System.out.println("=============================开启监听===========================");
    }

    @Override
    public void schedulerShutdown() {

    }

    @Override
    public void schedulerShuttingdown() {

    }

    @Override
    public void schedulingDataCleared() {

    }
}
