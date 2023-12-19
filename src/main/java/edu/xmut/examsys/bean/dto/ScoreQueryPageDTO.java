package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朔风
 * @date 2023-11-13 23:57
 */
@NoArgsConstructor
@Data
public class ScoreQueryPageDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String userId;
    private String title;
}
