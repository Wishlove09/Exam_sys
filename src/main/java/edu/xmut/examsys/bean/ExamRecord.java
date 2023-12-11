package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 考试记录表
 * @TableName exam_record
 */
@Data
public class ExamRecord implements Serializable {
    /**
     * 记录id
     */
    private Long id;

    /**
     * 考试id
     */
    private Long examId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 考试次数
     */
    private Integer tryCount;

    /**
     * 是否通过（0-未通过，1-通过）
     */
    private Integer passed;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 提交时间
     */
    private Date endTime;

    /**
     * 最终成绩
     */
    private Integer finalGrade;

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