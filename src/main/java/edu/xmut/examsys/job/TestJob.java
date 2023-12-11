package edu.xmut.examsys.job;

import org.quartz.*;

/**
 * @author 朔风
 * @date 2023-12-11 15:16
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("--------任务被执行----------");
    }
}
