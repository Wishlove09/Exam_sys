package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-30 08:31
 */
@NoArgsConstructor
@Data
public class MultiQuestionDTO {

    private List<String> rightAnswer;
    private String content;
    private String analysis;
    private Integer level;
    private String image;
    private List<OptionDTO> optionList;

}
