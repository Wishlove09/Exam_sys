package edu.xmut.examsys.bean.vo;

import lombok.Data;

/**
 * @author 朔风
 * @date 2023-12-04 22:27
 */
@Data
public class PaperInfoVO {
    /**
     * 试卷id
     */
    private Long id;

    /**
     * 试卷名称
     */
    private String title;

    /**
     * 试卷描述
     */
    private String desc;

    private Integer total;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 试卷状态（0-未启用，1-启用）
     */
    private Integer status;

}
