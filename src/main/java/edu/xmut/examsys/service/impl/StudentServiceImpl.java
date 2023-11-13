package edu.xmut.examsys.service.impl;


import edu.xmut.examsys.bean.Student;
import edu.xmut.examsys.mapper.StudentMapper;
import edu.xmut.examsys.service.StudentService;

import edu.xmut.examsys.utils.MD5Utils;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.commons.lang3.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author shuofeng
 * @description 针对表【student(学生表)】的数据库操作Service实现
 * @createDate 2023-11-13 11:52:40
 */
@Service
public class StudentServiceImpl implements StudentService {
    private StudentMapper studentMapper = SqlSessionFactoryUtils.openSession(true).getMapper(StudentMapper.class);


    @Override
    public Student login(Long sno, String password) {
        // 校验数据是否为非空，否则直接返回空
        if (StringUtils.isAnyBlank(String.valueOf(sno), password)) {
            return null;
        }
        Student student = studentMapper.getBySno(sno);
        // 如果找不到学生对象
        if (Objects.isNull(student)) {
            return null;
        }
        String encrypted;
        try {
            // 使用MD5加密
            encrypted = MD5Utils.toMD5(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // 如果数据库中的密码和加密后的密码一致
        if (encrypted.equals(student.getPassword())) {
            return student;
        } else {
            return null;
        }
    }
}




