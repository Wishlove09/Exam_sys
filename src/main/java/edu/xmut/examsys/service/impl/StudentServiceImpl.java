package edu.xmut.examsys.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.xmut.examsys.bean.Clazz;
import edu.xmut.examsys.bean.Student;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.StudentVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.mapper.ClazzMapper;
import edu.xmut.examsys.mapper.StudentMapper;
import edu.xmut.examsys.service.StudentService;

import edu.xmut.examsys.utils.MD5Utils;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.commons.lang3.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shuofeng
 * @description 针对表【student(学生表)】的数据库操作Service实现
 * @createDate 2023-11-13 11:52:40
 */
@Service
public class StudentServiceImpl implements StudentService {
    private StudentMapper studentMapper = SqlSessionFactoryUtils.openSession(true).getMapper(StudentMapper.class);

    private ClazzMapper clazzMapper = SqlSessionFactoryUtils.openSession(true).getMapper(ClazzMapper.class);

    @Override
    public Student login(Long sno, String password) {
        // 校验数据是否为非空，否则直接返回空
        if (StringUtils.isAnyBlank(String.valueOf(sno), password)) {
            throw new GlobalException("账号或密码为空");
        }
        Student student = studentMapper.getBySno(sno);
        // 如果找不到学生对象
        if (Objects.isNull(student)) {
            throw new GlobalException("账号或密码错误");
        }
        String encrypted;
        try {
            // 使用MD5加密
            encrypted = MD5Utils.toMD5(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
        // 如果数据库中的密码和加密后的密码一致
        if (encrypted.equals(student.getPassword())) {
            return student;
        } else {
            throw new GlobalException("账号或密码错误");
        }
    }

    @Override
    public PageVO<StudentVO> page(Integer pageNum, Integer pageSize) {
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        // 从数据库中查询
        Page<Student> studentList = studentMapper.selectPage();
        List<StudentVO> studentVOList = studentList.stream().map(student -> {
            Clazz clazz = clazzMapper.getByClassId(Long.valueOf(student.getClazzId()));
            return StudentVO.builder()
                    .sno(student.getSno())
                    .sex(student.getSex())
                    .email(student.getEmail())
                    .phone(student.getPhone())
                    .birth(student.getBirth())
                    .realName(student.getRealName())
                    .status(student.getStatus())
                    .clazzName(clazz.getClassName())
                    .build();
        }).collect(Collectors.toList());
        return new PageVO<>(studentList.getPageNum(), studentList.getPageSize(), (int) studentList.getTotal(), studentVOList);
    }

    @Override
    public Boolean register(Student student) {
        // 判断
        if (Objects.isNull(student)) {
            throw new GlobalException("传入的参数为空");
        }
        Integer result = studentMapper.insertStudent(student);
        return result > 0;
    }
}




