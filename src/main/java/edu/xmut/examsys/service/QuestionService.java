package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.dto.MultiQuestionDTO;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.SingleQuestionDTO;
import edu.xmut.examsys.bean.vo.PageVO;

/**
 * @author 朔风
 * @date 2023-11-29 08:29
 */
public interface QuestionService {
    PageVO pages(PageDTO pageDTO);

    Boolean addSingleQuestion(SingleQuestionDTO singleQuestionDTO);

    Boolean addMultiQuestion(MultiQuestionDTO multiQuestionDTO);
}
