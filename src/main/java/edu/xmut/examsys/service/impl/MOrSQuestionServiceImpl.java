package edu.xmut.examsys.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.mysql.cj.util.TimeUtil;
import edu.xmut.examsys.bean.MOrSQuestion;
import edu.xmut.examsys.bean.Teacher;
import edu.xmut.examsys.bean.vo.QuestionVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.mapper.FillQuestionMapper;
import edu.xmut.examsys.mapper.MOrSQuestionMapper;
import edu.xmut.examsys.mapper.TeacherMapper;
import edu.xmut.examsys.service.MOrSQuestionService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.commons.lang3.time.DateUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-11-22 10:19
 */
@Service
public class MOrSQuestionServiceImpl implements MOrSQuestionService {
    MOrSQuestionMapper mOrSQuestionMapper = SqlSessionFactoryUtils.openSession(true).getMapper(MOrSQuestionMapper.class);
    TeacherMapper teacherMapper = SqlSessionFactoryUtils.openSession(true).getMapper(TeacherMapper.class);

    @Override
    public List<QuestionVO> getAllWithContentAndType() {
        List<MOrSQuestion> mOrSQuestions = mOrSQuestionMapper.selectAll();
        if (Objects.isNull(mOrSQuestions)) {
            throw new GlobalException("单选题或多选题不存在");
        }
        List<QuestionVO> collect = mOrSQuestions.stream()
                .map(mOrSQuestion -> {
                    Teacher teacher = teacherMapper.getByTno(mOrSQuestion.getCreator());
                    return QuestionVO.builder()
                            .qid(mOrSQuestion.getQid())
                            .type(mOrSQuestion.getType())
                            .content(mOrSQuestion.getContent())
                            .realName(teacher.getRealName())
                            .updateTime(DateUtil.format(mOrSQuestion.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"))
                            .build();
                })
                .collect(Collectors.toList());

        return collect;
    }
}
