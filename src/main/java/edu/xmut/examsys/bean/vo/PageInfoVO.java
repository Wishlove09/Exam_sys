package edu.xmut.examsys.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 朔风
 * @date 2023-11-30 10:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoVO {
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

    /**
     * 创建人id
     */
    private Long creator;

    /**
     * 试卷状态（0-未启用，1-启用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

}
