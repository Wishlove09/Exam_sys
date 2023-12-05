package edu.xmut.examsys.bean.dto;

import lombok.Data;

/**
 * @author 朔风
 * @date 2023-12-05 21:26
 */
@Data
public class PageInfoDTO {
    private Long id;

    /**
     * 试卷名称
     */
    private String title;

    /**
     * 试卷描述
     */
    private String desc;

    /**
     * 创建人id
     */
    private Long creator;

    /**
     * 试卷状态（0-未启用，1-启用）
     */
    private Integer status;
}
