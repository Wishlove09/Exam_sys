package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.dto.*;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.QuestionDetailsVO;
import edu.xmut.examsys.bean.vo.QuestionListVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-29 08:29
 */
public interface QuestionService {
    PageVO pages(PageDTO pageDTO);

    Boolean addSingleQuestion(SingleQuestionDTO singleQuestionDTO, HttpServletRequest request);

    Boolean addMultiQuestion(MultiQuestionDTO multiQuestionDTO, HttpServletRequest request);

    Boolean addJudgeQuestion(JudgeQuestionDTO judgeQuestionDTO, HttpServletRequest request);

    Boolean addFillQuestion(FillQuestionDTO fillQuestionDTO, HttpServletRequest request);

    QuestionDetailsVO getById(String id);

    QuestionListVO getByIdsBatch(List<String> questionBatchDTO);
}
