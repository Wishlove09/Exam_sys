package edu.xmut.examsys.service.impl;

import java.util.Date;

import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.dto.LoginDTO;
import edu.xmut.examsys.bean.dto.RegisterDTO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.mapper.UserMapper;
import edu.xmut.examsys.service.UserService;
import edu.xmut.examsys.utils.MD5Utils;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author 朔风
 * @date 2023-11-27 17:53
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl() {
        userMapper = SqlSessionFactoryUtils.openSession(true).getMapper(UserMapper.class);
    }

    @Override
    public User login(LoginDTO loginDTO) {
        Long userId = loginDTO.getUserId();
        String password = loginDTO.getPassword();
        Integer role = loginDTO.getRole();

        User user = userMapper.selectById(userId);
        if (Objects.isNull(user)) {
            throw new GlobalException("用户不存在");
        }
        if (!Objects.equals(role, user.getRole())) {
            throw new GlobalException("用户不存在");
        }
        if (user.getStatus().equals(0)) {
            throw new GlobalException("用户未启用");
        }
        String encrypt;
        try {
            encrypt = MD5Utils.toMD5(password);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        if (!encrypt.equals(user.getPassword())) {
            throw new GlobalException("用户名或密码不正确");
        }

        return user;
    }

    @Override
    public Boolean register(RegisterDTO registerDTO) {
        String password = registerDTO.getPassword();
        String encrypt;
        try {
            encrypt = MD5Utils.toMD5(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        User user = new User();
        user.setId(registerDTO.getUserId());
        user.setUsername(registerDTO.getUsername());
        user.setSex(Integer.valueOf(registerDTO.getSex()));
        user.setRealName(registerDTO.getRealName());
        user.setPassword(encrypt);
        user.setRole(Integer.valueOf(registerDTO.getRole()));
        user.setPhone(registerDTO.getPhone());

        return userMapper.insert(user) > 0;
    }
}
