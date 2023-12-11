package edu.xmut.examsys.service.impl;

import edu.xmut.examsys.listener.MyJobListener;
import edu.xmut.examsys.service.JobService;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;

/**
 * @author 朔风
 * @date 2023-12-11 11:01
 */
@Service
public class JobServiceImpl implements JobService {

    private SchedulerFactory schedulerFactory;

    private Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);


    public JobServiceImpl() throws SchedulerException {
        schedulerFactory = new StdSchedulerFactory();
    }


    /**
     * @param jobClass  具体的job字节码对象
     * @param jobName   job名称
     * @param groupName group名称
     * @param data      数据类型
     * @param start     任务开始时间
     * @param end       任务结束时间
     * @return
     */
    @Override
    public Boolean addJob(Class<? extends Job> jobClass, String jobName, String groupName, JobDataMap data, Date start, Date end) {

        try {
            // 判断job是否已存在
            JobKey jobKey = new JobKey(jobName, groupName);
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (Objects.nonNull(scheduler.getJobDetail(jobKey))) {
                logger.info("任务：{}已存在", jobKey.getName());
                // 取消job
                this.cancelJob(jobName, groupName);
            }
            logger.info("开始构建任务：{}，{}，{},{}", jobName, groupName, start, end);
            // jobDetail
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName, groupName)
                    .usingJobData(data)
                    .build();

            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(1)   // 每3秒执行一次
                    // .repeatForever();
            .withRepeatCount(3);    // 总共执行3次

            // trigger
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(jobName, groupName);
            triggerBuilder.withSchedule(simpleScheduleBuilder);
            if (Objects.nonNull(start)) {
                triggerBuilder.startAt(start);
            }
            if (Objects.nonNull(end)) {
                triggerBuilder.endAt(end);
            }
            if (Objects.nonNull(data)) {
                triggerBuilder.usingJobData(data);
            }
            Trigger trigger = triggerBuilder.build();
            // 加入到调度器中
            scheduler.scheduleJob(jobDetail, trigger);
            // 判断调度器状态
            if (!scheduler.isShutdown()) {
                scheduler.getListenerManager().addSchedulerListener(new MyJobListener());
                scheduler.start();
            }
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean addCronJob(Class<? extends Job> jobClass, String jobName, String cron, JobDataMap data) {
        return null;
    }

    @Override
    public Boolean modifyJobTime(String jobName, String groupName, Date start, Date end) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            // 得到key
            TriggerKey key = TriggerKey.triggerKey(jobName, groupName);
            // 得到旧的trigger
            Trigger oldTrigger = scheduler.getTrigger(key);
            // 如果不存在触发器，则代表修改失败
            if (Objects.isNull(oldTrigger)) {
                return false;
            }
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(key);
            if (start != null) {
                triggerBuilder.startAt(start);   // 任务开始时间设定
            } else if (oldTrigger.getStartTime() != null) {
                triggerBuilder.startAt(oldTrigger.getStartTime()); // 没有传入新的开始时间就不变
            }
            if (end != null) {
                triggerBuilder.endAt(end);   // 任务结束时间设定
            } else if (oldTrigger.getEndTime() != null) {
                triggerBuilder.endAt(oldTrigger.getEndTime()); // 没有传入新的结束时间就不变
            }
            Trigger newTrigger = triggerBuilder.build();
            // 更新触发器
            scheduler.rescheduleJob(key, newTrigger);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean cancelJob(String jobName, String groupName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, groupName));
            scheduler.deleteJob(JobKey.jobKey(jobName, groupName));
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}
