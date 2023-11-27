package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.vo.QuestionVO;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-22 10:19
 */
public interface FillQuestionService {

    List<QuestionVO> getAllWithContentAndType();

}
