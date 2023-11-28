package edu.xmut.examsys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.Clazz;
import edu.xmut.examsys.bean.ClazzStudent;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.StudentDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.StudentVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.mapper.ClazzMapper;
import edu.xmut.examsys.mapper.UserMapper;
import edu.xmut.examsys.service.StudentService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-11-27 20:36
 */
@Service
public class StudentServiceImpl implements StudentService {


    private UserMapper userMapper;
    private ClazzMapper clazzMapper;

    public StudentServiceImpl() {
        userMapper = SqlSessionFactoryUtils.openSession(true).getMapper(UserMapper.class);
        clazzMapper = SqlSessionFactoryUtils.openSession(true).getMapper(ClazzMapper.class);
    }

    @Override
    public PageVO getAllStudent(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<User> userPage = userMapper.selectByRoleUser(1);

        List<User> userList = userPage.getResult();
        List<StudentVO> collect = userList.stream()
                .map(user -> {
                    StudentVO studentVO = new StudentVO();
                    studentVO.setId(user.getId());
                    studentVO.setSex(user.getSex());
                    studentVO.setRealName(user.getRealName());
                    studentVO.setEmail(user.getEmail());
                    studentVO.setBirth(DateUtil.format(user.getBirth(), "yyyy-MM-dd"));
                    studentVO.setPhone(user.getPhone());
                    studentVO.setStatus(user.getStatus());

                    ClazzStudent clazzStudent = clazzMapper.selectCStById(user.getId());
                    if (Objects.nonNull(clazzStudent)) {
                        Clazz clazz = clazzMapper.selectClazzById(clazzStudent.getClazzId());
                        studentVO.setClazzName(clazz.getClassName());
                    }
                    return studentVO;
                }).collect(Collectors.toList());

        return PageVO.builder()
                .pageNum(userPage.getPageNum())
                .pageSize(userPage.getPageSize())
                .total(userPage.getTotal())
                .records(collect)
                .build();
    }

    @Override
    public StudentVO getById(Long id) {
        User user = userMapper.selectById(id);
        StudentVO studentVO = new StudentVO();
        studentVO.setId(user.getId());
        studentVO.setSex(user.getSex());
        studentVO.setRealName(user.getRealName());
        studentVO.setEmail(user.getEmail());
        ClazzStudent clazzStudent = clazzMapper.selectCStById(user.getId());
        if (Objects.nonNull(clazzStudent)) {
            studentVO.setClazzId(clazzStudent.getClazzId());
        }
        studentVO.setBirth(DateUtil.format(user.getBirth(), "yyyy-MM-dd"));
        studentVO.setPhone(user.getPhone());

        return studentVO;
    }

    @Override
    public Boolean update(StudentDTO studentDTO) {
        if (Objects.isNull(studentDTO)) {
            throw new GlobalException("学生实体为空");
        }
        User user = new User();
        BeanUtil.copyProperties(studentDTO, user);
        // 更新用户
        Integer r1 = userMapper.update(user);
        // 更新班级
        Long clazzId = studentDTO.getClazzId();
        Integer r2;
        if (Objects.isNull(clazzMapper.selectCStById(studentDTO.getId()))) {
            r2 = clazzMapper.insertCS(clazzId, studentDTO.getId());
        } else {
            r2 = clazzMapper.updateCS(clazzId, studentDTO.getId());
        }

        return r1 > 0 && r2 > 0;
    }

    @Override
    public Boolean updateStatus(Long id) {
        User user = userMapper.selectById(id);
        User u = new User();
        u.setId(id);
        if (user.getStatus().equals(1)) {
            u.setStatus(0);
        } else {
            u.setStatus(1);
        }
        Integer result = userMapper.update(u);
        return result > 0;
    }


}
