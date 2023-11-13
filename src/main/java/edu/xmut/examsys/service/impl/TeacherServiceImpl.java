package edu.xmut.examsys.service.impl;


import edu.xmut.examsys.bean.Teacher;
import edu.xmut.examsys.mapper.TeacherMapper;
import edu.xmut.examsys.service.TeacherService;
import edu.xmut.examsys.utils.MD5Utils;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

/**
 * @author shuofeng
 * @description 针对表【teacher(教师表)】的数据库操作Service实现
 * @createDate 2023-11-13 16:57:55
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private final SqlSession sqlSession = SqlSessionFactoryUtils.openSession(true);
    TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);

    @Override
    public List<Teacher> getAll() {

        return teacherMapper.getAll();
    }

    @Override
    public Teacher login(Long tno, String password) {
        // 校验数据是否为非空，否则直接返回空
        if (StringUtils.isAnyBlank(String.valueOf(tno), password)) {
            return null;
        }
        String encrypted;
        Teacher teacher = teacherMapper.getByTno(tno);
        // 如果找不到教师对象
        if (Objects.isNull(teacher)) {
            return null;
        }
        try {
            // 使用MD5加密
            encrypted = MD5Utils.toMD5(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // 如果数据库中的密码和加密后的密码一致
        if (encrypted.equals(teacher.getPassword())) {
            return teacher;
        } else {
            return null;
        }
    }

}




