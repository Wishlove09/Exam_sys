package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朔风
 * @date 2023-12-19 17:33
 */
@NoArgsConstructor
@Data
public class PageQuestionQueryDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String searchContent;
    private Integer searchType;
    private Integer searchSubject;
}
