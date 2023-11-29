package edu.xmut.examsys.service.impl;

import cn.hutool.core.date.DateUtil;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.vo.TeacherVO;
import edu.xmut.examsys.mapper.UserMapper;
import edu.xmut.examsys.service.TeacherService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;

/**
 * @author 朔风
 * @date 2023-11-29 09:08
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private UserMapper userMapper;

    public TeacherServiceImpl() {
        userMapper = SqlSessionFactoryUtils.openSession(true).getMapper(UserMapper.class);
    }

    @Override
    public TeacherVO getById(Long id) {
        User user = userMapper.selectById(id);
        TeacherVO teacherVO = new TeacherVO();
        teacherVO.setId(user.getId());
        teacherVO.setSex(user.getSex());
        teacherVO.setRealName(user.getRealName());
        teacherVO.setEmail(user.getEmail());
        teacherVO.setBirth(DateUtil.format(user.getBirth(), "yyyy-MM-dd"));
        teacherVO.setPhone(user.getPhone());
        teacherVO.setStatus(user.getStatus());

        return teacherVO;
    }
}
