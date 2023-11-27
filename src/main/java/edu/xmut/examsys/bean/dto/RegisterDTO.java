package edu.xmut.examsys.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朔风
 * @date 2023-11-14 17:29
 */
@NoArgsConstructor
@Data
public class RegisterDTO {
    private Long userId;
    private String realName;
    private String sex;
    private String phone;
    private String username;
    private String password;
    private String checkPassword;
    private String role;
}
