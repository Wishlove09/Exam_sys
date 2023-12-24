package edu.xmut.examsys.listener;

import edu.xmut.examsys.job.ChangeExamStatusJob;
import edu.xmut.examsys.mapper.ExamInfoMapper;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author 朔风
 * @date 2023-12-09 21:04
 */
public class QuartzContextListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(QuartzContextListener.class);
    private Scheduler scheduler;
    private ExamInfoMapper examInfoMapper;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Quartz监听器开始实例化...");
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            initMapper();
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(ExamInfoMapper.class.getSimpleName(), examInfoMapper);

            // 具体任务
            JobDetail job = JobBuilder.newJob(ChangeExamStatusJob.class)
                    .withIdentity(ChangeExamStatusJob.class.getSimpleName())
                    .usingJobData(jobDataMap)
                    .build();

            // 触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1")
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule("0 0 0 * * ?")) // 表示每1分钟 执行任务
                    .build();
            // 交由Scheduler安排触发
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }


    }

    private void initMapper() {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSession(true);
        examInfoMapper = sqlSession.getMapper(ExamInfoMapper.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Quartz监听器销毁...");

        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }

    }
}
