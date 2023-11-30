package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 试卷信息表
 * @TableName paper_info
 */
@Data
public class PaperInfo implements Serializable {
    /**
     * 试卷id
     */
    private Long id;

    /**
     * 试卷名称
     */
    private String title;

    /**
     * 试卷描述
     */
    private String desc;

    /**
     * 创建人id
     */
    private Long creator;

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
     * 试卷状态（0-未启用，1-启用）
     */
    private Integer status;

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