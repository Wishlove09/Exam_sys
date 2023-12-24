package edu.xmut.examsys.service.impl;

import com.alibaba.fastjson2.JSONArray;
import edu.xmut.examsys.bean.dto.*;
import edu.xmut.examsys.bean.vo.ExamQuestionVO;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.*;
import edu.xmut.examsys.bean.vo.*;
import edu.xmut.examsys.constants.GroupName;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.job.ChangeExamStatusJob;
import edu.xmut.examsys.job.SynchronizeGradeResultsJob;
import edu.xmut.examsys.mapper.*;
import edu.xmut.examsys.service.ExamService;
import edu.xmut.examsys.service.JobService;
import edu.xmut.examsys.utils.DateTimeUtils;
import edu.xmut.examsys.utils.SnowflakeUtils;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import edu.xmut.examsys.utils.UserUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.mybatis.spring.SqlSessionTemplate;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-12-06 21:23
 */
@Service
public class ExamServiceImpl implements ExamService {

    private final SqlSessionTemplate sqlSessionTemplate;
    private final ExamInfoMapper examInfoMapper;
    private final ExamParticipantsMapper examParticipantsMapper;
    private final ClazzMapper clazzMapper;
    private final UserMapper userMapper;
    private final PaperInfoMapper paperInfoMapper;
    private final ExamRecordMapper examRecordMapper;
    private final PaperQuestionMapper paperQuestionMapper;
    private final QuestionMapper questionMapper;
    private final QuestionOptionMapper questionOptionMapper;
    private final ExamAnswerRecordMapper examAnswerRecordMapper;
    private final ScoreMapper scoreMapper;

    private final Logger logger = LoggerFactory.getLogger(ExamServiceImpl.class);

    private JobService jobService;

    public ExamServiceImpl() throws SchedulerException {
        sqlSessionTemplate = new SqlSessionTemplate(SqlSessionFactoryUtils.initSqlSessionFactory());
        examInfoMapper = sqlSessionTemplate.getMapper(ExamInfoMapper.class);
        userMapper = sqlSessionTemplate.getMapper(UserMapper.class);
        examParticipantsMapper = sqlSessionTemplate.getMapper(ExamParticipantsMapper.class);
        clazzMapper = sqlSessionTemplate.getMapper(ClazzMapper.class);
        paperInfoMapper = sqlSessionTemplate.getMapper(PaperInfoMapper.class);
        examRecordMapper = sqlSessionTemplate.getMapper(ExamRecordMapper.class);
        paperQuestionMapper = sqlSessionTemplate.getMapper(PaperQuestionMapper.class);
        questionMapper = sqlSessionTemplate.getMapper(QuestionMapper.class);
        questionOptionMapper = sqlSessionTemplate.getMapper(QuestionOptionMapper.class);
        examAnswerRecordMapper = sqlSessionTemplate.getMapper(ExamAnswerRecordMapper.class);
        scoreMapper = sqlSessionTemplate.getMapper(ScoreMapper.class);
        jobService = new JobServiceImpl();
    }

    @Override
    public PageVO pages(PageDTO pageDTO, Long userId) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<ExamInfo> page = examInfoMapper.pages(userId);

        List<ExamInfoVO> collect = page.getResult().stream()
                .map(examInfo -> {
                    ExamInfoVO examInfoVO = new ExamInfoVO();
                    BeanUtil.copyProperties(examInfo, examInfoVO);
                    examInfoVO.setCreator(userMapper.selectById(examInfo.getCreator()).getRealName());
                    examInfoVO.setPaper(paperInfoMapper.selectById(examInfo.getPaperId()).getTitle());
                    return examInfoVO;
                }).collect(Collectors.toList());


        return PageVO.builder()
                .pageNum(page.getPageNum())
                .pageSize(page.getPageSize())
                .total(page.getTotal())
                .records(collect)
                .build();
    }

    @Override
    public Boolean addExam(ExamAddDTO examAddDTO, HttpServletRequest request) {

        ExamInfo examInfo = new ExamInfo();
        BeanUtil.copyProperties(examAddDTO, examInfo);
        examInfo.setExamId(System.currentTimeMillis());

        Long userId = UserUtils.getUserId(request);
        examInfo.setCreator(userId);

        Date start = examAddDTO.getExamDate().get(0);
        Date end = examAddDTO.getExamDate().get(1);
        examInfo.setStartTime(start);
        examInfo.setEndTime(end);
        int rangeDate = DateTimeUtils.isRangeDate(start, end);
        if (rangeDate != -1) {
            examInfo.setStatus(DateTimeUtils.isRangeDate(start, end));
            if (rangeDate == 0) {
                try {
                    JobDataMap map = new JobDataMap();
                    map.put(ExamInfoMapper.class.getSimpleName(), examInfoMapper);
                    map.put("id", examInfo.getExamId());
                    // 设置考试开始更新状态
                    jobService.addJob(ChangeExamStatusJob.class,
                            String.valueOf(examInfo.getExamId()), GroupName.CHANGE_EXAM_STATUS,
                            map, start, null);
                    // 设置考试结束更新状态
                    jobService.addJob(ChangeExamStatusJob.class,
                            String.valueOf(examInfo.getExamId() + 1), GroupName.CHANGE_EXAM_STATUS,
                            map, end, null);
                } catch (SchedulerException e) {
                    logger.error(e.getMessage(), e);
                }
            } else if (rangeDate == 1) {
                JobDataMap map = new JobDataMap();
                map.put(ExamInfoMapper.class.getSimpleName(), examInfoMapper);
                map.put("id", examInfo.getExamId());
                try {
                    // 设置考试结束更新状态
                    jobService.addJob(ChangeExamStatusJob.class,
                            String.valueOf(examInfo.getExamId() + 1), GroupName.CHANGE_EXAM_STATUS,
                            map, end, null);
                } catch (SchedulerException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        Integer r = examInfoMapper.insert(examInfo);

        Integer r2 = examParticipantsMapper.insertBatch(examInfo.getExamId(), examAddDTO.getCrowds());

        // sqlSessionTemplate.commit();

        return r > 0 && r2 > 0;
    }

    @Override
    public PageVO listByUser(PageDTO pageDTO, Long userId) {

        // 查询学生所在的班级列表
        List<ClazzStudent> classList = clazzMapper.selectCStById(userId);

        if (classList.isEmpty()) {
            return PageVO.builder()
                    .pageNum(pageDTO.getPageNum())
                    .pageSize(pageDTO.getPageSize())
                    .total(0L)
                    .records(new ArrayList())
                    .build();
        }

        // 查询关联班级的考试id
        List<ExamParticipants> examParticipants = new ArrayList<>();
        for (ClazzStudent clazzStudent : classList) {
            examParticipants.addAll(examParticipantsMapper.selectByClassId(clazzStudent));
        }
        List<Long> examIds = examParticipants.stream()
                .map(ExamParticipants::getExamId)
                .collect(Collectors.toList());

        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        // 根据考试id查询所有考试
        Page<ExamInfo> page = examInfoMapper.selectByIds(examIds, pageDTO.getSearch());

        List<ExamInfoVO> collect = page.getResult().stream()
                .map(examInfo -> {
                    ExamInfoVO examInfoVO = new ExamInfoVO();
                    BeanUtil.copyProperties(examInfo, examInfoVO);
                    return examInfoVO;
                }).collect(Collectors.toList());

        return PageVO.builder()
                .pageNum(page.getPageNum())
                .pageSize(page.getPageSize())
                .total(page.getTotal())
                .records(collect)
                .build();
    }

    @Override
    public ExamDetailsVO getDetailsById(String examId) {

        ExamDetailsVO examDetailsVO = new ExamDetailsVO();

        ExamInfo examInfo = examInfoMapper.selectById(Long.valueOf(examId));
        BeanUtil.copyProperties(examInfo, examDetailsVO);

        PaperInfo paperInfo = paperInfoMapper.selectById(examInfo.getPaperId());
        examDetailsVO.setPaperInfo(paperInfo);

        return examDetailsVO;
    }

    @Override
    public ExamInfoVO getExamInfoById(String examId) {
        ExamInfoVO examInfoVO = new ExamInfoVO();
        ExamInfo examInfo = examInfoMapper.selectById(Long.valueOf(examId));
        BeanUtil.copyProperties(examInfo, examInfoVO);
        return examInfoVO;
    }

    @Override
    public Boolean update(ExamInfoDTO examInfoDTO) throws SchedulerException {
        ExamInfo examInfo = new ExamInfo();
        BeanUtil.copyProperties(examInfoDTO, examInfo);
        List<String> examDate = examInfoDTO.getExamDate();
        examInfo.setStartTime(DateUtil.parse(examDate.get(0)));
        examInfo.setEndTime(DateUtil.parse(examDate.get(1)));

        Date startTime = examInfo.getStartTime();
        Date endTime = examInfo.getEndTime();

        Long examId = examInfo.getExamId();
        int rangeDate = DateTimeUtils.isRangeDate(startTime, endTime);
        if (rangeDate == 0) {
            jobService.cancelJob(String.valueOf(examId), GroupName.CHANGE_EXAM_STATUS);
            jobService.cancelJob(String.valueOf(examId + 1), GroupName.CHANGE_EXAM_STATUS);
            JobDataMap map = new JobDataMap();
            map.put(ExamInfoMapper.class.getSimpleName(), examInfoMapper);
            map.put("id", examId);
            // 设置考试开始更新状态
            jobService.addJob(ChangeExamStatusJob.class,
                    String.valueOf(examId), GroupName.CHANGE_EXAM_STATUS,
                    map, startTime, null);
            // 设置考试结束更新状态
            jobService.addJob(ChangeExamStatusJob.class,
                    String.valueOf(examId + 1), GroupName.CHANGE_EXAM_STATUS,
                    map, endTime, null);
        } else if (rangeDate == 1) {
            jobService.cancelJob(String.valueOf(examId), GroupName.CHANGE_EXAM_STATUS);
            jobService.cancelJob(String.valueOf(examId + 1), GroupName.CHANGE_EXAM_STATUS);
            JobDataMap map = new JobDataMap();
            map.put("id", examId);
            map.put(ExamInfoMapper.class.getSimpleName(), examInfoMapper);
            // 设置考试结束更新状态
            jobService.addJob(ChangeExamStatusJob.class,
                    String.valueOf(examId + 1), GroupName.CHANGE_EXAM_STATUS,
                    map, endTime, null);
        }

        examInfo.setStatus(rangeDate);
        Integer result = examInfoMapper.update(examInfo);
        sqlSessionTemplate.clearCache();

        return result > 0;
    }

    @Override
    public Boolean deleteById(String id) {

        ExamInfo examInfo = examInfoMapper.selectById(Long.valueOf(id));
        if (examInfo.getStatus().equals(1)) {
            throw new GlobalException("删除失败，该考试正在进行中...");
        }

        Integer i = examInfoMapper.deleteById(id);

        // sqlSessionTemplate.commit();

        return i > 0;
    }

    @Override
    public Boolean checkExamPermission(Long id, Long userId) {
        ExamRecord examRecord = examRecordMapper.selectByExamIdAndUserId(id, userId);
        if (Objects.isNull(examRecord)) {
            return true;
        }

        Integer tryCount = examRecord.getTryCount();
        Integer passed = examRecord.getPassed();

        if (tryCount > 0) {
            return false;
        }
        if (passed == 1) {
            return false;
        }

        return true;
    }


    @Override
    public Integer checkExamIsStartOrEnd(long examId) {
        ExamInfo examInfo = examInfoMapper.selectById(examId);
        return examInfo.getStatus();
    }

    @Override
    public ExamQuestionDetailVO getQuestionDetailByQid(String qid) {

        ExamQuestionDetailVO examQuestionDetailVO = new ExamQuestionDetailVO();

        Question question = questionMapper.selectById(qid);
        examQuestionDetailVO.setId(question.getId());
        examQuestionDetailVO.setType(question.getType());
        examQuestionDetailVO.setLevel(question.getLevel());
        examQuestionDetailVO.setImage(question.getImage());
        if (question.getType().equals(3)) {
            String[] split = question.getContent().split("[()（）]");
            examQuestionDetailVO.setContents(Arrays.asList(split));
        } else {
            examQuestionDetailVO.setContent(question.getContent());
        }

        List<QuestionOption> optionList = questionOptionMapper.selectByQid(qid);
        List<OptionVO> collect = optionList.stream().map(questionOption -> {
            OptionVO optionVO = new OptionVO();
            if (question.getType().equals(3)) {
                List<String> strings = JSONArray.parseArray(questionOption.getContent(), String.class);
                optionVO.setSpaceNumber(strings.size());
            } else {
                optionVO.setContent(questionOption.getContent());
                optionVO.setId(questionOption.getId());
            }
            optionVO.setImage(questionOption.getImage());
            return optionVO;
        }).collect(Collectors.toList());

        examQuestionDetailVO.setOptionList(collect);

        return examQuestionDetailVO;
    }

    @Override
    public Boolean saveAnswer(ExamSaveDTO examSaveDTO, HttpServletRequest request) {
        // 得到已经存在的考试答案记录
        ExamAnswerRecord examAnswerRecordObject = examAnswerRecordMapper.selectByExamIdAndPaperIdAndUserIdAndQId(examSaveDTO.getExamId(),
                examSaveDTO.getPaperId(), examSaveDTO.getQId(), UserUtils.getUserId(request));
        // 如果不为空，代表已经答题就更新
        if (Objects.nonNull(examAnswerRecordObject)) {
            determineType(examSaveDTO, examAnswerRecordObject);
            return examAnswerRecordMapper.update(examAnswerRecordObject) > 0;
        }

        ExamAnswerRecord examAnswerRecord = new ExamAnswerRecord();
        examAnswerRecord.setId(SnowflakeUtils.nextId());
        examAnswerRecord.setQuestionType(examSaveDTO.getType());
        examAnswerRecord.setPaperId(examSaveDTO.getPaperId());
        examAnswerRecord.setQid(examSaveDTO.getQId());
        examAnswerRecord.setPaperId(examAnswerRecord.getPaperId());
        examAnswerRecord.setExamId(examSaveDTO.getExamId());
        examAnswerRecord.setUserId(UserUtils.getUserId(request));
        determineType(examSaveDTO, examAnswerRecord);
        Integer result = examAnswerRecordMapper.insert(examAnswerRecord);


        return result > 0;
    }

    @Override
    public ExamAnsweredVO getAnswered(ExamAnsweredQueryDTO examAnsweredQueryDTO, HttpServletRequest request) {
        ExamAnsweredVO examAnsweredVO = new ExamAnsweredVO();

        ExamAnswerRecord examAnswerRecord = examAnswerRecordMapper.selectByExamIdAndPaperIdAndUserIdAndQId(examAnsweredQueryDTO.getExamId(), examAnsweredQueryDTO.getPaperId(),
                examAnsweredQueryDTO.getQId(), UserUtils.getUserId(request));
        BeanUtil.copyProperties(examAnswerRecord, examAnsweredVO);
        if (Objects.isNull(examAnswerRecord)) {
            return null;
        }
        if (examAnswerRecord.getQuestionType().equals(1)) {
            List<String> ids = JSONArray.parseArray(examAnswerRecord.getOptionId(), String.class);
            examAnsweredVO.setOptionId(null);
            examAnsweredVO.setOptionIds(ids);
        }

        return examAnsweredVO;
    }

    @Override
    public boolean submitExam(ExamSubmitDTO examSubmitDTO, HttpServletRequest request) {

        Boolean b = checkExamPermission(examSubmitDTO.getExamId(), UserUtils.getUserId(request));
        Integer i = checkExamIsStartOrEnd(examSubmitDTO.getExamId());
        if (!b || !i.equals(1)) {
            throw new GlobalException("不允许提交考试");
        }

        ExamRecord examRecord = new ExamRecord();
        Long userId = UserUtils.getUserId(request);
        examRecord.setId(SnowflakeUtils.nextId());
        examRecord.setExamId(examSubmitDTO.getExamId());
        examRecord.setPaperId(examSubmitDTO.getPaperId());
        examRecord.setUserId(userId);
        examRecord.setTryCount(1);
        examRecord.setStartTime(examSubmitDTO.getStartTime());
        examRecord.setEndTime(DateUtil.date());

        int sum = calculateScore(examSubmitDTO, userId);

        // 得到最终成绩
        examRecord.setFinalGrade(sum);
        // 添加定时任务，同步最终成绩到成绩表中
        try {
            JobDataMap dataMap = new JobDataMap();
            dataMap.put("totalScore", sum);
            dataMap.put("examId", examSubmitDTO.getExamId());
            dataMap.put("userId", userId);
            dataMap.put("submitDate", DateUtil.date());
            dataMap.put(ScoreMapper.class.getSimpleName(), scoreMapper);
            jobService.addJob(SynchronizeGradeResultsJob.class,
                    String.valueOf(examRecord.getId()), GroupName.SYNC_GRADE_RESULT,
                    dataMap, DateUtil.offsetMinute(new Date(), 15), null);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }

        Integer result = examRecordMapper.insert(examRecord);

        return result > 0;
    }

    private int calculateScore(ExamSubmitDTO examSubmitDTO, Long userId) {
        // 得到所有试卷试题包括答案
        List<PaperQuestion> paperQuestions = paperQuestionMapper.selectQIdByPId(examSubmitDTO.getPaperId());
        // 定义总分
        int sum = 0;
        for (PaperQuestion paperQuestion : paperQuestions) {
            // 试题id
            String qid = paperQuestion.getQid();
            // 试题类型
            Integer type = paperQuestion.getQuestionType();
            // 单题分数
            Integer score = paperQuestion.getScore();
            // 得到该题的作答记录
            ExamAnswerRecord examAnswerRecord = examAnswerRecordMapper.selectByExamIdAndPaperIdAndUserIdAndQId(examSubmitDTO.getExamId(),
                    examSubmitDTO.getPaperId(), qid, userId);
            switch (type) {
                // 单选题和判断题
                case 0:
                case 2:
                    // 如果作答记录为空，代表没有作答
                    if (Objects.isNull(examAnswerRecord)) {
                        continue;
                    }
                    // 得到选项id
                    String optionId = examAnswerRecord.getOptionId();
                    // 如果选项id为空，代表没有作答
                    if (Objects.isNull(optionId)) {
                        continue;
                    }
                    // 根据选项id查询选项实体
                    QuestionOption questionOption = questionOptionMapper.selectById(optionId);
                    if (Objects.isNull(questionOption)) {
                        continue;
                    }
                    // 如果选项是正确的，加上分数
                    if (questionOption.getIsRight().equals(1)) {
                        sum += score;
                    }
                    break;
                // 多选题
                case 1:
                    // 如果作答记录为空，代表没有作答
                    if (Objects.isNull(examAnswerRecord)) {
                        continue;
                    }
                    // 得到作答的选项id（此时应该是一个JSON数组格式）
                    String optionIds = examAnswerRecord.getOptionId();
                    // 如果选项id为空，代表没有作答
                    if (Objects.isNull(optionIds)) {
                        continue;
                    }
                    // 解析JSON数组格式为List集合
                    List<String> ids = JSONArray.parseArray(optionIds, String.class);
                    // 根据多个选项id查询多个选项实体（学生作答的选项）
                    List<QuestionOption> answered = questionOptionMapper.selectByIds(ids);
                    // 根据试题id查询正确的多个选项（正确的选项）
                    List<QuestionOption> rightAnswered = questionOptionMapper.selectByQidWithRight(qid);
                    // 如果选项为空，代表没有作答
                    if (answered.isEmpty()) {
                        continue;
                    }
                    // 遍历选项，过滤出正确的选项
                    List<QuestionOption> collect = answered.stream()
                            .filter(questionOption1 -> questionOption1.getIsRight().equals(1))
                            .collect(Collectors.toList());
                    // 正确选项个数与作答选项个数相同，加上分数
                    if (collect.size() == rightAnswered.size()) {
                        sum += score;
                    }
                    break;
                // 填空题
                case 3:
                    // 如果作答记录为空，代表没有作答
                    if (Objects.isNull(examAnswerRecord)) {
                        continue;
                    }
                    String replyAnswer = examAnswerRecord.getReplyAnswer();
                    if (Objects.isNull(replyAnswer)) {
                        continue;
                    }
                    List<String> strings = JSONArray.parseArray(replyAnswer, String.class);
                    if (strings.isEmpty()) {
                        continue;
                    }
                    Question question = questionMapper.selectById(qid);
                    List<QuestionOption> questionOptions = questionOptionMapper.selectByQid(qid);
                    // String answer = question.getAnswer();
                    break;
            }

        }
        return sum;
    }

    private void determineType(ExamSaveDTO examSaveDTO, ExamAnswerRecord examAnswerRecord) {
        // 单选题和判断题
        if (examSaveDTO.getType().equals(0) || examSaveDTO.getType().equals(2)) {
            examAnswerRecord.setOptionId(examSaveDTO.getSelectIds().get(0));
            // 填空题
        } else if (examSaveDTO.getType().equals(3)) {
            JSONArray objects = JSONArray.copyOf(examSaveDTO.getSelectIds());
            examAnswerRecord.setReplyAnswer(objects.toJSONString());
        } else if (examSaveDTO.getType().equals(1)) {
            JSONArray objects = JSONArray.copyOf(examSaveDTO.getSelectIds());
            examAnswerRecord.setOptionId(objects.toJSONString());
        }
    }


    @Override
    public StartExamVO startExam(Long examId, Long userId) {
        StartExamVO startExamVO = new StartExamVO();
        startExamVO.setExamId(examId);

        ExamInfo examInfo = examInfoMapper.selectById(examId);
        startExamVO.setTotalTime(examInfo.getTotalTime() * 60);
        startExamVO.setTitle(examInfo.getTitle());
        // 得到试卷id
        Long paperId = examInfo.getPaperId();
        startExamVO.setPaperId(paperId);
        // 得到试卷实体
        PaperInfo paperInfo = paperInfoMapper.selectById(paperId);
        startExamVO.setRadioCount(paperInfo.getRadioCount());
        startExamVO.setRadioScore(paperInfo.getRadioScore());
        startExamVO.setMultiCount(paperInfo.getMultiCount());
        startExamVO.setMultiScore(paperInfo.getMultiScore());
        startExamVO.setJudgeCount(paperInfo.getJudgeCount());
        startExamVO.setJudgeScore(paperInfo.getJudgeScore());
        startExamVO.setFillCount(paperInfo.getFillCount());
        startExamVO.setFillScore(paperInfo.getFillScore());
        startExamVO.setTotalScore(paperInfo.getTotalScore());

        // 得到试卷关联试题列表
        List<PaperQuestion> questionList = paperQuestionMapper.selectQIdByPId(paperId);

        List<ExamQuestionVO> singleList = new ArrayList<>();
        List<ExamQuestionVO> multiList = new ArrayList<>();
        List<ExamQuestionVO> fillList = new ArrayList<>();
        List<ExamQuestionVO> judgeList = new ArrayList<>();

        int sort = 0;

        List<PaperQuestion> collect = questionList.stream()
                .sorted(Comparator.comparing(PaperQuestion::getQuestionType)).collect(Collectors.toList());

        for (PaperQuestion paperQuestion : collect) {
            Integer type = paperQuestion.getQuestionType();
            ExamQuestionVO examQuestionVO = new ExamQuestionVO();
            examQuestionVO.setId(paperQuestion.getQid());
            examQuestionVO.setType(paperQuestion.getQuestionType());
            examQuestionVO.setScore(paperQuestion.getScore());
            if (type.equals(0)) {
                examQuestionVO.setSort(sort++);
                singleList.add(examQuestionVO);
            } else if (type.equals(1)) {
                examQuestionVO.setSort(sort++);
                multiList.add(examQuestionVO);
            } else if (type.equals(2)) {
                examQuestionVO.setSort(sort++);
                judgeList.add(examQuestionVO);
            } else if (type.equals(3)) {
                examQuestionVO.setSort(sort++);
                fillList.add(examQuestionVO);
            }

        }
        startExamVO.setSingleList(singleList);
        startExamVO.setJudgeList(judgeList);
        startExamVO.setMultiList(multiList);
        startExamVO.setFillList(fillList);

        return startExamVO;
    }
}
