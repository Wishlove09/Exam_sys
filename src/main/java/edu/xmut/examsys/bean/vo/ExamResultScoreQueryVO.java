package edu.xmut.examsys.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 朔风
 * @date 2023-12-19 01:55
 */
@Data
public class ExamResultScoreQueryVO {
    private Long examId;

    private String examTitle;


    /**
     * 得分
     */
    private Integer resultscore;

    /**
     * 提交日期
     */
    private Date submitData;
}
