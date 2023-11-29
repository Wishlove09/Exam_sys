package edu.xmut.examsys.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.Question;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.SingleQuestionDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.QuestionVO;
import edu.xmut.examsys.mapper.QuestionMapper;
import edu.xmut.examsys.mapper.UserMapper;
import edu.xmut.examsys.service.QuestionService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-11-29 08:29
 */
@Service
public class QuestionServiceImpl implements QuestionService {


    private QuestionMapper questionMapper;
    private UserMapper userMapper;

    public QuestionServiceImpl() {
        questionMapper = SqlSessionFactoryUtils.openSession(true).getMapper(QuestionMapper.class);
        userMapper = SqlSessionFactoryUtils.openSession(true).getMapper(UserMapper.class);
    }

    @Override
    public PageVO pages(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<Question> questionPage = questionMapper.pages(pageDTO.getSearch());

        // mysql区分大小写吗？ 在windows 不区分 在linux区分
        List<QuestionVO> collect = questionPage.getResult().stream().map(question -> {
            User user = userMapper.selectById(question.getCreator());
            return QuestionVO.builder()
                    .id(question.getId())
                    .type(question.getType())
                    .content(question.getContent())
                    .updateTime(DateUtil.format(question.getUpdateTime(), "yyyy-MM-dd"))
                    .realName(user.getRealName())
                    .build();
        }).collect(Collectors.toList());

        return PageVO.builder()
                .pageSize(questionPage.getPageSize())
                .pageNum(questionPage.getPageNum())
                .total(questionPage.getTotal())
                .records(collect)
                .build();
    }

    @Override
    public Integer adSingleQuestion(SingleQuestionDTO singleQuestionDTO) {
        

        return null;
    }
}
