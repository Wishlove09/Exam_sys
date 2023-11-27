package edu.xmut.examsys.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * 班级学生关联
 * @TableName clazz_student
 */
@Data
public class ClazzStudent implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 学号
     */
    private Long userId;

    /**
     * 班级id
     */
    private Long clazzId;

    private static final long serialVersionUID = 1L;
}