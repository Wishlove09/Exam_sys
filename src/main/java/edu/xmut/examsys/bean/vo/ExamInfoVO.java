package edu.xmut.examsys.bean.vo;

import java.util.Date;

/**
 * @author 朔风
 * @date 2023-11-13 23:55
 */
public class ExamInfoVO {
    private Long examId;

    /**
     * 考试简介
     */
    private String examDesc;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 创建人
     */
    private Long creatorId;

    /**
     * 注意事项
     */
    private String attention;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 截止日期
     */
    private Date endTime;

    /**
     * 考试状态（0-未开始，1-进行中，2-结束）
     */
    private Integer status;

    /**
     * 总分
     */
    private String totalScore;
}
