package edu.xmut.examsys.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-12 09:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartExamVO {
    private Long examId;
    private Long paperId;
    /**
     * 考试标题
     */
    private String title;

    /**
     * 单选题数量
     */
    private Integer radioCount;

    /**
     * 单选题分数
     */
    private Integer radioScore;

    /**
     * 多选题数量
     */
    private Integer multiCount;

    /**
     * 多选题分数
     */
    private Integer multiScore;

    /**
     * 判断题数量
     */
    private Integer judgeCount;

    /**
     * 判断题分数
     */
    private Integer judgeScore;

    /**
     * 填空题数量
     */
    private Integer fillCount;

    /**
     * 填空题分数
     */
    private Integer fillScore;

    /**
     * 试卷总分
     */
    private Integer totalScore;
    /**
     * 总时间
     */
    private Integer totalTime;

    private List<ExamQuestionVO> singleList;
    private List<ExamQuestionVO> multiList;
    private List<ExamQuestionVO> fillList;
    private List<ExamQuestionVO> judgeList;

}
