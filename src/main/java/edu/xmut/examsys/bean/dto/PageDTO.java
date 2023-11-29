package edu.xmut.examsys.bean.dto;

import lombok.Data;

/**
 * @author 朔风
 * @date 2023-11-13 23:57
 */
@Data
public class PageDTO {
    Integer pageNum;
    Integer pageSize;
    String search;
}
