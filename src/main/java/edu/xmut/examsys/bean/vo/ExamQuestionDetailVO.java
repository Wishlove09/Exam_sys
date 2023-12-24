package edu.xmut.examsys.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-13 08:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamQuestionDetailVO {

    private String id;
    private String content;
    private List<String> contents;
    private Integer type;
    private Integer level;
    private String image;

    private List<OptionVO> optionList;


}
