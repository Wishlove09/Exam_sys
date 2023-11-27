package edu.xmut.examsys.service.impl;

import cn.hutool.core.date.DateUtil;
import edu.xmut.examsys.bean.FillQuestion;
import edu.xmut.examsys.bean.JudgeQuestion;
import edu.xmut.examsys.bean.Teacher;
import edu.xmut.examsys.bean.vo.QuestionVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.mapper.FillQuestionMapper;
import edu.xmut.examsys.mapper.JudgeQuestionMapper;
import edu.xmut.examsys.mapper.TeacherMapper;
import edu.xmut.examsys.service.JudgeQuestionService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-11-22 10:19
 */
@Service
public class JudgeQuestionServiceImpl implements JudgeQuestionService {

    JudgeQuestionMapper judgeQuestionMapper = SqlSessionFactoryUtils.openSession(true).getMapper(JudgeQuestionMapper.class);
    TeacherMapper teacherMapper = SqlSessionFactoryUtils.openSession(true).getMapper(TeacherMapper.class);


    @Override
    public List<QuestionVO> getAllWithContentAndType() {
        List<JudgeQuestion> judgeQuestions = judgeQuestionMapper.selectAll();
        if (Objects.isNull(judgeQuestions)) {
            throw new GlobalException("判断题为空");
        }
        List<QuestionVO> collect = judgeQuestions.stream()
                .map(judgeQuestion -> {
                    // 获取教师实体
                    Teacher teacher = teacherMapper.getByTno(judgeQuestion.getCreator());
                    return QuestionVO.builder()
                            .qid(judgeQuestion.getQid())
                            .content(judgeQuestion.getContent())
                            .type(judgeQuestion.getType())
                            .updateTime(DateUtil.format(judgeQuestion.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"))
                            .realName(teacher.getRealName())
                            .build();
                })
                .collect(Collectors.toList());

        return collect;

    }
}
