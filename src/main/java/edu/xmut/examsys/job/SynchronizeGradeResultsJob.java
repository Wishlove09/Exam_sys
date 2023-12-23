package edu.xmut.examsys.job;
import java.util.Date;

import cn.hutool.core.date.DateUtil;
import edu.xmut.examsys.bean.Score;
import edu.xmut.examsys.mapper.ScoreMapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 朔风
 * @date 2023-12-20 11:27
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class SynchronizeGradeResultsJob implements Job {

    private Logger logger = LoggerFactory.getLogger(SynchronizeGradeResultsJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        logger.info("开始执行任务 当前时间{}", DateUtil.now());
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        ScoreMapper scoreMapper = (ScoreMapper) jobDataMap.get(ScoreMapper.class.getSimpleName());
        Integer totalScore = (Integer) jobDataMap.get("totalScore");
        Long examId = (Long) jobDataMap.get("examId");
        Long userId = (Long) jobDataMap.get("userId");
        Date submitDate = (Date) jobDataMap.get("submitDate");

        Score score = new Score();
        score.setExamId(examId);
        score.setUserId(userId);
        score.setResultscore(totalScore);
        score.setSubmitData(submitDate);
        int result = scoreMapper.insert(score);
        if (result > 0) {
            logger.info("插入成功");
        } else {
            logger.info("插入失败");
        }
    }
}
