package edu.xmut.examsys.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朔风
 * @date 2023-11-28 11:36
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ClazzDTO {

    private String className;
    private Long classId;
    private Integer deptId;
}
