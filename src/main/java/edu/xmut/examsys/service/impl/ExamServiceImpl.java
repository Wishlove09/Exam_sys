package edu.xmut.examsys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.ClazzStudent;
import edu.xmut.examsys.bean.ExamInfo;
import edu.xmut.examsys.bean.ExamParticipants;
import edu.xmut.examsys.bean.PaperInfo;
import edu.xmut.examsys.bean.dto.ExamAddDTO;
import edu.xmut.examsys.bean.dto.ExamInfoDTO;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.ExamDetailsVO;
import edu.xmut.examsys.bean.vo.ExamInfoVO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.constants.GroupName;
import edu.xmut.examsys.constants.SystemConstant;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.job.ChangeExamStatusJob;
import edu.xmut.examsys.mapper.*;
import edu.xmut.examsys.service.ExamService;
import edu.xmut.examsys.service.JobService;
import edu.xmut.examsys.utils.DateTimeUtils;
import edu.xmut.examsys.utils.JwtTokenUtil;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.ibatis.session.SqlSession;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.java2d.pipe.RenderingEngine;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-12-06 21:23
 */
@Service
public class ExamServiceImpl implements ExamService {

    private final SqlSession sqlSession;
    private final ExamInfoMapper examInfoMapper;
    private final ExamParticipantsMapper examParticipantsMapper;
    private final ClazzMapper clazzMapper;
    private final UserMapper userMapper;
    private final PaperInfoMapper paperInfoMapper;

    private final Logger logger = LoggerFactory.getLogger(ExamServiceImpl.class);

    private JobService jobService;

    public ExamServiceImpl() throws SchedulerException {
        sqlSession = SqlSessionFactoryUtils.openSession(true);
        examInfoMapper = sqlSession.getMapper(ExamInfoMapper.class);
        userMapper = sqlSession.getMapper(UserMapper.class);
        examParticipantsMapper = sqlSession.getMapper(ExamParticipantsMapper.class);
        clazzMapper = sqlSession.getMapper(ClazzMapper.class);
        paperInfoMapper = sqlSession.getMapper(PaperInfoMapper.class);
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

        String token = request.getHeader(SystemConstant.AUTHORIZATION);
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String userId = jwtTokenUtil.getUserId(token);
        examInfo.setCreator(Long.valueOf(userId));
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

        sqlSession.commit();

        return r > 0;
    }

    @Override
    public PageVO listByUser(PageDTO pageDTO, String userId) {

        // 查询学生所在的班级列表
        List<ClazzStudent> classList = clazzMapper.selectCStById(Long.valueOf(userId));

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

        Date now = DateUtil.date();
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
        sqlSession.commit();

        return result > 0;
    }

    @Override
    public Boolean deleteById(String id) {

        ExamInfo examInfo = examInfoMapper.selectById(Long.valueOf(id));
        if (examInfo.getStatus().equals(1)) {
            throw new GlobalException("删除失败，该考试正在进行中...");
        }

        Integer i = examInfoMapper.deleteById(id);

        sqlSession.commit();

        return i > 0;
    }
}
