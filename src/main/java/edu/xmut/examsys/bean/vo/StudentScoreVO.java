package edu.xmut.examsys.bean.vo;

import lombok.Data;

/**
 * @author 朔风
 * @date 2023-12-17 17:37
 */
@Data
public class StudentScoreVO {

    private String title;
    private Long examId;
    private Long userId;
    private String realName;
    private Integer resultScore;


}
