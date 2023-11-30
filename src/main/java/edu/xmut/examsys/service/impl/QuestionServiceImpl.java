package edu.xmut.examsys.service.impl;

import java.util.Date;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.Question;
import edu.xmut.examsys.bean.QuestionOption;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.dto.*;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.QuestionVO;
import edu.xmut.examsys.mapper.QuestionMapper;
import edu.xmut.examsys.mapper.QuestionOptionMapper;
import edu.xmut.examsys.mapper.UserMapper;
import edu.xmut.examsys.service.QuestionService;
import edu.xmut.examsys.utils.SnowflakeUtils;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-11-29 08:29
 */
@Service
public class QuestionServiceImpl implements QuestionService {


    private final SqlSession sqlSession;
    private QuestionMapper questionMapper;
    private QuestionOptionMapper questionOptionMapper;
    private UserMapper userMapper;

    public QuestionServiceImpl() {
        sqlSession = SqlSessionFactoryUtils.openSession(false);
        questionMapper = sqlSession.getMapper(QuestionMapper.class);
        questionOptionMapper = sqlSession.getMapper(QuestionOptionMapper.class);
        userMapper = sqlSession.getMapper(UserMapper.class);
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
    public Boolean addSingleQuestion(SingleQuestionDTO singleQuestionDTO) {
        Question question = new Question();
        // 雪花算法生成试题ID
        String qid = SnowflakeUtils.nextIdStr();

        // 得到正确答案
        String rightAnswer = singleQuestionDTO.getRightAnswer();
        List<OptionDTO> optionList = singleQuestionDTO.getOptionList();
        // 1.添加试题各个选项
        optionList.stream()
                .map(option -> {
                    QuestionOption questionOption = new QuestionOption();
                    // 雪花算法生成选项ID
                    String questionOId = SnowflakeUtils.nextIdStr();
                    questionOption.setId(questionOId);
                    questionOption.setQid(qid);
                    questionOption.setIsRight(0);
                    questionOption.setContent(option.getContent());
                    questionOption.setImage(option.getImage());
                    if (option.getTitle().equals(rightAnswer)) {
                        question.setOptionId(questionOId);
                        questionOption.setIsRight(1);
                    }
                    return questionOption;
                })
                .peek(questionOption -> {
                    questionOptionMapper.addOption(questionOption);
                }).forEach(System.out::println);

        // 2.添加试题主体
        question.setId(qid);
        question.setType(0);
        question.setLevel(singleQuestionDTO.getLevel());
        question.setImage(singleQuestionDTO.getImage());
        question.setContent(singleQuestionDTO.getContent());
        question.setCreator(1111111L);
        question.setAnalysis(singleQuestionDTO.getAnalysis());
        Integer result = questionMapper.insert(question);

        sqlSession.commit();
        return result > 0;
    }

    @Override
    public Boolean addMultiQuestion(MultiQuestionDTO multiQuestionDTO) {
        List<String> rightAnswer = multiQuestionDTO.getRightAnswer();

        // 雪花算法生成唯一试题ID
        String qid = SnowflakeUtils.nextIdStr();
        Question question = new Question();
        question.setId(qid);

        // 1.添加选项
        List<OptionDTO> optionList = multiQuestionDTO.getOptionList();
        optionList.stream()
                .map(option -> {
                    QuestionOption questionOption = new QuestionOption();
                    String questionOid = SnowflakeUtils.nextIdStr();
                    questionOption.setId(questionOid);
                    questionOption.setQid(qid);
                    questionOption.setIsRight(0);
                    questionOption.setImage(option.getImage());
                    questionOption.setContent(option.getContent());
                    if (rightAnswer.contains(option.getTitle())) {
                        questionOption.setIsRight(1);
                    }
                    return questionOption;
                })
                .peek(questionOption -> {
                    questionOptionMapper.addOption(questionOption);
                }).forEach(System.out::println);

        // 2.添加试题主体
        question.setType(1);
        question.setLevel(multiQuestionDTO.getLevel());
        question.setImage(multiQuestionDTO.getImage());
        // question.setOptionId();
        question.setContent(multiQuestionDTO.getContent());
        question.setCreator(22222222L);
        question.setAnalysis(multiQuestionDTO.getAnalysis());
        Integer result = questionMapper.insert(question);

        sqlSession.commit();
        return result > 0;
    }

    @Override
    public Boolean addJudgeQuestion(JudgeQuestionDTO judgeQuestionDTO) {
        // 生成试题ID
        String qid = SnowflakeUtils.nextIdStr();
        Question question = new Question();
        question.setId(qid);
        // 添加试题主题
        question.setType(2);
        question.setLevel(judgeQuestionDTO.getLevel());
        question.setImage(judgeQuestionDTO.getImage());
        question.setContent(judgeQuestionDTO.getContent());
        question.setCreator(3333333L);
        question.setAnalysis(judgeQuestionDTO.getAnalysis());
        // 答案
        String answer = judgeQuestionDTO.getAnswer();
        for (int i = 0; i < 2; i++) {
            QuestionOption questionOption = new QuestionOption();
            String id = SnowflakeUtils.nextIdStr();
            questionOption.setId(id);
            questionOption.setQid(qid);
            questionOption.setIsRight(0);
            if (i == 0) {
                questionOption.setContent("T");
            } else {
                questionOption.setContent("F");
            }
            if (answer.equals(questionOption.getContent())) {
                question.setOptionId(id);
                questionOption.setIsRight(1);
            }
            questionOptionMapper.addOption(questionOption);
        }

        Integer result = questionMapper.insert(question);
        // 提交事务
        sqlSession.commit();
        return result > 0;
    }

    @Override
    public Boolean addFillQuestion(FillQuestionDTO fillQuestionDTO) {

        String qid = SnowflakeUtils.nextIdStr();
        Question question = new Question();
        question.setType(3);
        question.setLevel(fillQuestionDTO.getLevel());
        question.setImage(fillQuestionDTO.getImage());
        question.setOptionId(fillQuestionDTO.getAnalysis());
        question.setContent(fillQuestionDTO.getContent());
        question.setCreator(444444L);
        question.setAnalysis(fillQuestionDTO.getAnalysis());
        question.setId(qid);

        String oid = SnowflakeUtils.nextIdStr();
        QuestionOption questionOption = new QuestionOption();
        questionOption.setId(oid);
        questionOption.setQid(qid);
        questionOption.setIsRight(1);
        questionOption.setContent(fillQuestionDTO.getAnswer());
        question.setOptionId(oid);

        // 插入数据库
        Integer r1 = questionOptionMapper.addOption(questionOption);
        Integer r2 = questionMapper.insert(question);

        sqlSession.commit();
        return r1 > 0 && r2 > 0;
    }
}
