import edu.xmut.examsys.job.ChangeExamStatusJob;
import edu.xmut.examsys.job.TestJob;
import edu.xmut.examsys.service.JobService;
import edu.xmut.examsys.service.impl.JobServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

import java.util.Date;

/**
 * @author 朔风
 * @date 2023-12-11 15:07
 */
public class QuartzTest {


    private static JobService jobService;

    static {
        try {
            jobService = new JobServiceImpl();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws SchedulerException {
        System.out.println(jobService.addJob(TestJob.class,
                "1", "group1",
                new JobDataMap(),
                new Date(System.currentTimeMillis() + 3 * 1000),
                new Date(System.currentTimeMillis() + 10 * 1000)
        ));
        System.out.println(jobService.addJob(TestJob.class,
                "2", "group2",
                new JobDataMap(),
                new Date(System.currentTimeMillis() + 3 * 1000),
                new Date(System.currentTimeMillis() + 10 * 1000)
        ));
        System.out.println(jobService.addJob(TestJob.class,
                "3", "group3",
                new JobDataMap(),
                new Date(System.currentTimeMillis() + 3 * 1000),
                new Date(System.currentTimeMillis() + 10 * 1000)
        ));
        System.out.println(jobService.addJob(TestJob.class,
                "4", "group4",
                new JobDataMap(),
                new Date(System.currentTimeMillis() + 3 * 1000),
                new Date(System.currentTimeMillis() + 10 * 1000)
        ));
    }

}
