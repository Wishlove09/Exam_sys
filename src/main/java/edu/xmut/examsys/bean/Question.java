package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 试题主表
 * @TableName question
 */
@Data
public class Question implements Serializable {
    /**
     * 试题id
     */
    private String id;

    /**
     * 试题类型（0-单选题，1-多选题，2-判断题，3-填空题）
     */
    private Integer type;

    /**
     * 难度（1-5级别）
     */
    private Integer level;

    /**
     * 题目图片
     */
    private String image;

    /**
     * 正确项id
     */
    private String optionId;

    /**
     * 试题题目
     */
    private String content;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 解析
     */
    private String analysis;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 时间
     */
    private Date updateTime;

    /**
     * 是否删除（默认为0）
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}