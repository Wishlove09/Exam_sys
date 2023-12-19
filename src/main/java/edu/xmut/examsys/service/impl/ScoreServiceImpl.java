package edu.xmut.examsys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.ExamInfo;
import edu.xmut.examsys.bean.Score;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.ScoreQueryPageDTO;
import edu.xmut.examsys.bean.vo.*;
import edu.xmut.examsys.mapper.*;
import edu.xmut.examsys.service.ScoreService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-12-17 16:52
 */
@Service
public class ScoreServiceImpl implements ScoreService {


    private final ScoreMapper scoreMapper;
    private final UserMapper userMapper;
    private final ClazzMapper clazzMapper;
    private final ExamInfoMapper examInfoMapper;
    private final PaperInfoMapper paperInfoMapper;

    public ScoreServiceImpl() {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(SqlSessionFactoryUtils.initSqlSessionFactory());
        scoreMapper = sessionTemplate.getMapper(ScoreMapper.class);
        userMapper = sessionTemplate.getMapper(UserMapper.class);
        clazzMapper = sessionTemplate.getMapper(ClazzMapper.class);
        examInfoMapper = sessionTemplate.getMapper(ExamInfoMapper.class);
        paperInfoMapper = sessionTemplate.getMapper(PaperInfoMapper.class);
    }

    @Override
    public PageVO studentListPages(ScoreQueryPageDTO pageDTO) {
        String title = pageDTO.getTitle();
        Long userId = Long.valueOf(pageDTO.getUserId());
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<StudentScoreVO> studentScoreVOS = scoreMapper.selectByUserIdAndTitle(userId, title);
        for (StudentScoreVO studentScoreVO : studentScoreVOS) {
            User user = userMapper.selectById(studentScoreVO.getUserId());
            studentScoreVO.setRealName(user.getRealName());
        }

        return PageVO.builder()
                .pageNum(studentScoreVOS.getPageNum())
                .pageSize(studentScoreVOS.getPageSize())
                .total(studentScoreVOS.getTotal())
                .records(studentScoreVOS.getResult())
                .build();
    }

    @Override
    public StudentVO getStudentByUserId(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return StudentVO.builder()
                    .id(user.getId())
                    .realName(user.getRealName())
                    .phone(user.getPhone())
                    .sex(user.getSex())
                    .email(user.getEmail())
                    .build();
        }
        return null;
    }

    @Override
    public PageVO getAllExam(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<ExamInfo> examInfos = examInfoMapper.selectAllWithTheEnd();
        List<ExamScoreVO> collect = examInfos.stream()
                // .filter(examInfo -> examInfo.getEndTime().getTime() < System.currentTimeMillis())
                .map(examInfo -> {
                    ExamScoreVO examScoreVO = new ExamScoreVO();
                    examScoreVO.setExamId(examInfo.getExamId());
                    examScoreVO.setTitle(examInfo.getTitle());
                    examScoreVO.setStartTime(examInfo.getStartTime());
                    examScoreVO.setEndTime(examInfo.getEndTime());
                    examScoreVO.setTotalTime(examInfo.getTotalTime());
                    examScoreVO.setTotalScore(Integer.valueOf(examInfo.getTotalScore()));
                    examScoreVO.setPaperTitle(paperInfoMapper.selectById(examInfo.getPaperId()).getTitle());
                    return examScoreVO;
                }).collect(Collectors.toList());


        return PageVO.builder()
                .pageNum(pageDTO.getPageNum())
                .pageSize(pageDTO.getPageSize())
                .total(examInfos.getTotal())
                .records(collect)
                .build();
    }

    @Override
    public List<Score> statisticalScoreInterval(String examId) {
        return scoreMapper.statisticalScoreInterval(examId);
    }

    @Override
    public PageVO queryStudentScoreList(long userId, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<Score> scores = scoreMapper.selectByUserId(userId);
        List<ExamResultScoreQueryVO> collect = scores.stream()
                .map(score -> {
                    ExamResultScoreQueryVO examResultScoreQueryVO = new ExamResultScoreQueryVO();
                    BeanUtil.copyProperties(score, examResultScoreQueryVO);
                    ExamInfo examInfo = examInfoMapper.selectById(score.getExamId());
                    examResultScoreQueryVO.setExamTitle(examInfo.getTitle());
                    return examResultScoreQueryVO;
                }).collect(Collectors.toList());


        return PageVO.builder()
                .pageNum(scores.getPageNum())
                .pageSize(scores.getPageSize())
                .total(scores.getTotal())
                .records(collect)
                .build();
    }
}
