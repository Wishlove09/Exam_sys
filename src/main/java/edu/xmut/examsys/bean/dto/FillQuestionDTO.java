package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朔风
 * @date 2023-11-30 09:55
 */
@NoArgsConstructor
@Data
public class FillQuestionDTO {

    private Integer level;
    private String answer;
    private String content;
    private String analysis;
    private String image;
}
