package edu.xmut.examsys.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 试卷表
 * @TableName paper
 */
@Data
public class Paper implements Serializable {
    /**
     * 试卷id
     */
    private Long paperId;

    /**
     * 试卷名称
     */
    private String parerName;

    /**
     * 试题类型
     */
    private Integer questionType;

    /**
     * 试题id
     */
    private String qid;

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