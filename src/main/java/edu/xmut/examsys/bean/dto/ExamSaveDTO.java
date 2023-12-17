package edu.xmut.examsys.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-14 20:31
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ExamSaveDTO {

    private Long examId;
    private Long paperId;
    private Integer type;
    private String qId;
    private List<String> selectIds;

}
