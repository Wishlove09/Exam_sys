package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 考试信息表
 * @TableName exam_info
 */
@Data
public class ExamInfo implements Serializable {
    /**
     * 考试id
     */
    private Long examId;

    /**
     * 考试名称
     */
    private String title;

    /**
     * 考试简介
     */
    private String desc;

    /**
     * 注意事项
     */
    private String attention;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 考试状态（0-未开始，1-进行中，2-结束）
     */
    private Integer status;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 截止日期
     */
    private Date endTime;

    /**
     * 总时长（分钟）
     */
    private Integer totalTime;

    /**
     * 是否限时（0-不限时，1-限时）
     */
    private Integer timeLimit;

    /**
     * 总分
     */
    private String totalScore;

    /**
     * 扩展参数
     */
    private String params;

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