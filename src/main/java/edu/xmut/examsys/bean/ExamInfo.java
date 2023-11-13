package edu.xmut.examsys.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 考试信息表
 * @TableName exam_info
 */
@TableName(value ="exam_info")
@Data
public class ExamInfo implements Serializable {
    /**
     * 考试id
     */
    @TableId(value = "exam_id")
    private Long examId;

    /**
     * 考试简介
     */
    @TableField(value = "exam_desc")
    private String examDesc;

    /**
     * 考试名称
     */
    @TableField(value = "exam_name")
    private String examName;

    /**
     * 创建人
     */
    @TableField(value = "creator_id")
    private Long creatorId;

    /**
     * 注意事项
     */
    @TableField(value = "attention")
    private String attention;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 截止日期
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 考试状态（0-未开始，1-进行中，2-结束）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 总分
     */
    @TableField(value = "total_score")
    private String totalScore;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}