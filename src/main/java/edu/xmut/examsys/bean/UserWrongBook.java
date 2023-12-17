package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 错题本
 * @TableName user_wrong_book
 */
@Data
public class UserWrongBook implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 题目id
     */
    private String qid;

    /**
     * 考试id
     */
    private Long examId;

    /**
     * 错误记录数
     */
    private Integer wrongCount;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 最近加入的时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}