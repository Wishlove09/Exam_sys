package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 班级表
 * @TableName clazz
 */
@Data
public class Clazz implements Serializable {
    /**
     * 班级id
     */
    private Long id;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 系部id
     */
    private Integer deptId;

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