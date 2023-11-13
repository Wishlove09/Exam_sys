package edu.xmut.examsys.bean.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 朔风
 * @date 2023-11-13 19:28
 */
@Data
@Builder
public class LoginVO {
    private String username;

    private Long userId;

    private String role;

    private String avatar;

}
