package edu.xmut.examsys.bean.dto;

import lombok.Data;

/**
 * @author 朔风
 * @date 2023-11-13 19:05
 */
@Data
public class LoginDTO {
    private Long userId;
    private String password;
    private String role;

}
