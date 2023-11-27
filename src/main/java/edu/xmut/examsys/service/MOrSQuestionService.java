package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.MOrSQuestion;
import edu.xmut.examsys.bean.vo.QuestionVO;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-22 10:19
 */
public interface MOrSQuestionService {

    List<QuestionVO> getAllWithContentAndType();
}
