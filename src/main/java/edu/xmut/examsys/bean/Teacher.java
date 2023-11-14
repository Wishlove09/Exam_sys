package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 教师表
 *
 * @TableName teacher
 */
@Data
@Builder
public class Teacher implements Serializable {
    /**
     * 主键
     */
    private Long tno;

    /**
     * 用户名
     */
    private String username;

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
    private String dept;

    /**
     * 生日
     */
    private Date birth;

    /**
     * 简介
     */
    private String desc;

    /**
     * 密码
     */
    private String password;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户状态（1，启用 0，禁用）
     */
    private Integer status;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}