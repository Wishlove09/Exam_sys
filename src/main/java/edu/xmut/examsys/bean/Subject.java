package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 学科表
 * @TableName subject
 */
@Data
public class Subject implements Serializable {
    /**
     * 学科id
     */
    private Long id;

    /**
     * 学科名称
     */
    private String title;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}