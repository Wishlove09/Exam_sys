package edu.xmut.examsys.bean.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author 朔风
 * @date 2023-11-13 23:59
 */
@Data
@Builder
public class StudentVO {

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
     * 班级
     */
    private String clazzName;

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


}
