package edu.xmut.examsys.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朔风
 * @date 2023-12-12 17:04
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamQuestionVO {
    private String id;
    private Integer type;
    private Integer score;
    private Integer sort;
}
