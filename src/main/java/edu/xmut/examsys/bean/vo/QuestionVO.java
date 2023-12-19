package edu.xmut.examsys.bean.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 朔风
 * @date 2023-11-22 10:38
 */
@Data
@Builder
public class QuestionVO {
    private String id;
    private String subject;
    private String content;
    private Integer type;
    private String realName;
    private String updateTime;
}
