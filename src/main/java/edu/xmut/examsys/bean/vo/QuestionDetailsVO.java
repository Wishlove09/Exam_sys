package edu.xmut.examsys.bean.vo;

import edu.xmut.examsys.bean.dto.OptionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-22 10:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDetailsVO {

    private String id;
    private String content;
    private Integer type;
    private Integer level;
    private List<String> rightAnswer;
    private String image;
    private String analysis;

    private List<OptionVO> optionList;


}
