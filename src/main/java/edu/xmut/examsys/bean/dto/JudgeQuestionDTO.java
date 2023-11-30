package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朔风
 * @date 2023-11-30 09:19
 */
@NoArgsConstructor
@Data
public class JudgeQuestionDTO {

    private Integer level;
    private String content;
    private String analysis;
    private String image;
    private String answer;
}
