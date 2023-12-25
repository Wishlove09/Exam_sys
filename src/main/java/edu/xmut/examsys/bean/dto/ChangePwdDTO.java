package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朔风
 * @date 2023-12-25 19:17
 */
@NoArgsConstructor
@Data
public class ChangePwdDTO {

    private String pwd;
    private String userId;
}
