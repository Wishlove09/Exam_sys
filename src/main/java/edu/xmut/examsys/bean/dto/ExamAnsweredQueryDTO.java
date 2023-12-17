package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朔风
 * @date 2023-12-14 21:52
 */
@NoArgsConstructor
@Data
public class ExamAnsweredQueryDTO {

    private Long examId;
    private Long paperId;
    private String qId;
    private Integer type;
}
