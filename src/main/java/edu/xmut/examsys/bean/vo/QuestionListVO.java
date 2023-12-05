package edu.xmut.examsys.bean.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-05 15:50
 */
@Data
@Builder
public class QuestionListVO {

    List<QuestionVO> singleList;
    List<QuestionVO> multiList;
    List<QuestionVO> judgeList;
    List<QuestionVO> fillList;

}
