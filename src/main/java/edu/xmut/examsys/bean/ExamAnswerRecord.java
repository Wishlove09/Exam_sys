package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 答题记录表
 * @TableName exam_answer_record
 */
@Data
public class ExamAnswerRecord implements Serializable {
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
     * 是否正确（0-不正确，1-正确）
     */
    private Integer isRight;

    /**
     * 选项id（针对选择题）
     */
    private String optionId;

    /**
     * 试题类型（0-单选题，1-多选题，2-判断题，3-填空题）
     */
    private Integer questionType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}