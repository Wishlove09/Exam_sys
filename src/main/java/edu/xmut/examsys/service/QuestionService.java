package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.dto.*;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.QuestionDetailsVO;

/**
 * @author 朔风
 * @date 2023-11-29 08:29
 */
public interface QuestionService {
    PageVO pages(PageDTO pageDTO);

    Boolean addSingleQuestion(SingleQuestionDTO singleQuestionDTO);

    Boolean addMultiQuestion(MultiQuestionDTO multiQuestionDTO);

    Boolean addJudgeQuestion(JudgeQuestionDTO judgeQuestionDTO);

    Boolean addFillQuestion(FillQuestionDTO fillQuestionDTO);

    QuestionDetailsVO getById(String id);
}
