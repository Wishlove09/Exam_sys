package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 朔风
 * @date 2023-12-14 22:56
 */
@NoArgsConstructor
@Data
public class ExamSubmitDTO {

    private Long paperId;
    private Long examId;
    private Date startTime;
}
