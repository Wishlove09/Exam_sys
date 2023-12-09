package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-13 23:48
 */
@NoArgsConstructor
@Data
public class ExamInfoDTO {


    private String attention;
    private String creator;
    private String desc;
    private List<String> examDate;
    private Long examId;
    private Integer timeLimit;
    private String title;
    private Integer totalScore;
    private Integer totalTime;


}
