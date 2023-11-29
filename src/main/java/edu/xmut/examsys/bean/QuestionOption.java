package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 试题选项表
 * @TableName question_option
 */
@Data
public class QuestionOption implements Serializable {
    /**
     * 选项id
     */
    private String id;

    /**
     * 试题id
     */
    private String qid;

    /**
     * 是否为正确选项（0-否，1-是）
     */
    private Integer isRight;

    /**
     * 选项图片
     */
    private String image;

    /**
     * 选项内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除（默认为0）
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}