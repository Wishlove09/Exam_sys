package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-07 02:02
 */
@NoArgsConstructor
@Data
public class ExamAddDTO {

    private String title;
    private String desc;
    private String attention;
    private Long paperId;
    private Integer totalTime;
    private Integer totalScore;
    private Boolean timeLimit;
    private List<Date> examDate;
}
