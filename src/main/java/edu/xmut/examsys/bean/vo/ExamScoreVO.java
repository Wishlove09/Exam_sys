package edu.xmut.examsys.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 朔风
 * @date 2023-12-17 22:45
 */
@Data
public class ExamScoreVO {

    private Long examId;
    private String title;
    private String paperTitle;
    private Date startTime;
    private Date endTime;
    private Integer totalScore;
    private Integer totalTime;

}
