package edu.xmut.examsys.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-05 17:55
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PaperDetailsDTO {


    private Integer total;
    private String title;
    private String desc;

    private List<QuestionIdsDTO> questionIds;
    private Integer singleCount;
    private Integer multiCount;
    private Integer judgeCount;
    private Integer fillCount;

    private Integer singleScore;
    private Integer multiScore;
    private Integer judgeScore;
    private Integer fillScore;

    @NoArgsConstructor
    @Data
    public static class QuestionIdsDTO {
        private String id;
        private Integer type;
        private Integer score;
    }
}
