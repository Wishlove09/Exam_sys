package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 试卷试题关系表
 * @TableName paper_question
 */
@Data
public class PaperQuestion implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 试题id
     */
    private String qid;

    /**
     * 试卷id
     */
    private Long pid;

    /**
     * 试题类型
     */
    private Integer questionType;

    /**
     * 单题分值
     */
    private Integer score;

    /**
     * 创建时间
     */
    private Date createrTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}