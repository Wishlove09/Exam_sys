package edu.xmut.examsys.bean.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OptionVO {

    private String id;
    private String content;
    private String image;

    private Integer spaceNumber;
}