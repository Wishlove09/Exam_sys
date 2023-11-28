package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系部表
 * @TableName department
 */
@Data
public class Department implements Serializable {
    /**
     * 学院id
     */
    private Integer id;

    /**
     * 系部名称
     */
    private String deptName;

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