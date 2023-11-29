package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-29 11:34
 */
@NoArgsConstructor
@Data
public class SingleQuestionDTO {

    private Integer level;
    private String rightAnswer;
    private String content;
    private String analysis;
    private String image;
    private List<OptionList> optionList;

    @NoArgsConstructor
    @Data
    private static class OptionList {
        private String title;
        private String content;
        private String image;
    }
}
