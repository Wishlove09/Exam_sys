package edu.xmut.examsys.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import edu.xmut.examsys.bean.ExamInfo;
import edu.xmut.examsys.mapper.ExamInfoMapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-09 21:28
 */
// 告诉Quartz保存JobDataMap中的数据使得该job（即JobDetail）在下一次执行的时候，
// JobDataMap中是更新后的数据，而不是更新前的旧数据
@PersistJobDataAfterExecution
@DisallowConcurrentExecution    // 禁止Quartz并发实例化
public class ChangeExamStatusJob implements Job {

    private Logger logger = LoggerFactory.getLogger(ChangeExamStatusJob.class);


    @Override
    public void execute(JobExecutionContext context) {
        logger.info("开始执行任务 当前时间{}", DateUtil.now());
        JobDataMap map = context.getJobDetail().getJobDataMap();
        ExamInfoMapper examInfoMapper = (ExamInfoMapper) map.get(ExamInfoMapper.class.getSimpleName());

        List<ExamInfo> examInfoList = examInfoMapper.selectAll();
        for (ExamInfo examInfo : examInfoList) {
            Date now = DateUtil.date();
            Date startTime = examInfo.getStartTime();
            Date endTime = examInfo.getEndTime();
            String status = "未知";
            if (now.compareTo(startTime) >= 0 && now.compareTo(endTime) < 0) {
                examInfo.setStatus(1);
                status = "进行中";
            } else if (now.compareTo(startTime) < 0) {
                examInfo.setStatus(0);
                status = "未开始";
            } else if (now.compareTo(endTime) > 0) {
                examInfo.setStatus(2);
                status = "已结束";
            }
            examInfoMapper.updateStatus(examInfo);
            logger.info("考试:{} - {} 当前状态:{}", examInfo.getExamId(), examInfo.getTitle(), status);
        }

    }
}
