package edu.xmut.examsys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.mapper.UserMapper;
import edu.xmut.examsys.service.StudentService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-27 20:36
 */
@Service
public class StudentServiceImpl implements StudentService {


    private UserMapper userMapper;

    public StudentServiceImpl() {
        userMapper = SqlSessionFactoryUtils.openSession(true).getMapper(UserMapper.class);
    }

    @Override
    public PageVO getAllStudent(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<User> userPage = userMapper.selectByRoleUser(1);

        return PageVO.builder()
                .pageNum(userPage.getPageNum())
                .pageSize(userPage.getPageSize())
                .total(userPage.getTotal())
                .records(userPage.getResult())
                .build();
    }
}
