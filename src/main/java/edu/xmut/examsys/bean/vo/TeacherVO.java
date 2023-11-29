package edu.xmut.examsys.bean.vo;

import lombok.Data;

/**
 * @author 朔风
 * @date 2023-11-29 09:10
 */
@Data
public class TeacherVO {

    private Long id;

    /**
     * 性别（0-女，1-男）
     */
    private Integer sex;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;


    /**
     * 生日
     */
    private String birth;


    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户状态（1，启用 0，禁用）
     */
    private Integer status;

    /**
     * 院系名称
     */
    private String deptName;


}
