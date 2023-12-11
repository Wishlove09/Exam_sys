package edu.xmut.examsys.service;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

import java.util.Date;

/**
 * @author 朔风
 * @date 2023-12-11 11:00
 */
public interface JobService {

    /**
     * 添加一个job
     *
     * @param jobClass  具体的job字节码对象
     * @param jobName   job名称
     * @param groupName group名称
     * @param data      数据类型
     * @param start     任务开始时间
     * @param end       任务结束时间
     * @return 添加是否成功
     */
    Boolean addJob(Class<? extends Job> jobClass, String jobName, String groupName, JobDataMap data, Date start, Date end) throws SchedulerException;

    Boolean addCronJob(Class<? extends Job> jobClass, String jobName, String cron, JobDataMap data);

    Boolean modifyJobTime(String jobName, String groupName, Date start, Date end);


    /**
     * 取消job工作任务
     *
     * @param jobName
     * @param groupName
     * @return
     */
    Boolean cancelJob(String jobName, String groupName);

}
