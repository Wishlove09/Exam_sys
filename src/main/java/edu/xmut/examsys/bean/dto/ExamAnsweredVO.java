package edu.xmut.examsys.bean.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-14 22:22
 */
@Data
public class ExamAnsweredVO {
    /**
     * 用户答案id
     */
    private Long id;

    /**
     * 题目id
     */
    private String qid;

    /**
     * 试卷id
     */
    private Long paperId;

    /**
     * 考试id
     */
    private Long examId;

    /**
     * 学号
     */
    private Long userId;

    /**
     * 回复答案（针对简答题）
     */
    private String replyAnswer;


    /**
     * 选项id（针对单选题）
     */
    private String optionId;

    /**
     * 选项ids（针对多选题）
     */
    private List<String> optionIds;

    /**
     * 试题类型（0-单选题，1-多选题，2-判断题，3-填空题）
     */
    private Integer questionType;
}
