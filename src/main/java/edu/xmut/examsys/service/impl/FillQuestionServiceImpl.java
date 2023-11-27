package edu.xmut.examsys.service.impl;

import cn.hutool.core.date.DateUtil;
import edu.xmut.examsys.bean.FillQuestion;
import edu.xmut.examsys.bean.Teacher;
import edu.xmut.examsys.bean.vo.QuestionVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.mapper.FillQuestionMapper;
import edu.xmut.examsys.mapper.TeacherMapper;
import edu.xmut.examsys.service.FillQuestionService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-11-22 10:20
 */
@Service
public class FillQuestionServiceImpl implements FillQuestionService {

    FillQuestionMapper fillQuestionMapper = SqlSessionFactoryUtils.openSession(true).getMapper(FillQuestionMapper.class);
    TeacherMapper teacherMapper = SqlSessionFactoryUtils.openSession(true).getMapper(TeacherMapper.class);


    @Override
    public List<QuestionVO> getAllWithContentAndType() {
        List<FillQuestion> fillQuestions = fillQuestionMapper.selectAll();
        if (Objects.isNull(fillQuestions)) {
            throw new GlobalException("填空题为空");
        }
        List<QuestionVO> collect = fillQuestions.stream()
                .map(fillQuestion -> {
                    // 获取教师实体
                    Teacher teacher = teacherMapper.getByTno(fillQuestion.getCreator());
                    return QuestionVO.builder()
                            .qid(fillQuestion.getQid())
                            .content(fillQuestion.getContent())
                            .type(fillQuestion.getType())
                            .updateTime(DateUtil.format(fillQuestion.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"))
                            .realName(teacher.getRealName())
                            .build();
                })
                .collect(Collectors.toList());

        return collect;
    }
}
