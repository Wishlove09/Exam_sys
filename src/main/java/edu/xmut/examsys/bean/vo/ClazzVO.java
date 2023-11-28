package edu.xmut.examsys.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朔风
 * @date 2023-11-17 14:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClazzVO {
    /**
     * 班级id
     */
    private Long classId;

    /**
     * 班级名称
     */
    private String className;
    /**
     * 院系名称
     */
    private String deptName;
    /**
     * 人数统计
     */
    private Integer count;
}
