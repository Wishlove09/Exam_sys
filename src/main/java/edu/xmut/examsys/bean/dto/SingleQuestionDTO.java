package edu.xmut.examsys.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-29 11:34
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SingleQuestionDTO {

    private Integer level;
    private String rightAnswer;
    private String content;
    private String analysis;
    private String image;
    private List<OptionListDTO> optionList;


}
