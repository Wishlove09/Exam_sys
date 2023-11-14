package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 成绩表
 * @TableName score
 */
@Data
public class Score implements Serializable {
    /**
     * 成绩id
     */
    private Long sid;

    /**
     * 考试id
     */
    private Long examId;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 学号
     */
    private Long sno;

    /**
     * 得分
     */
    private Integer resultscore;

    /**
     * 提交日期
     */
    private Date submitData;

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