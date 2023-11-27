package edu.xmut.examsys.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-14 00:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageVO {
    private Integer pageNum;

    private Integer pageSize;

    private Long total;

    private List records;
}
