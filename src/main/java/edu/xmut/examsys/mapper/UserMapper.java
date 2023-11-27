package edu.xmut.examsys.mapper;

import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.dto.LoginDTO;

/**
 * @author shuofeng
 * @description 针对表【user(用户表)】的数据库操作Mapper
 * @createDate 2023-11-27 17:48:47
 * @Entity edu.xmut.examsys.bean.User
 */
public interface UserMapper {

    User selectById(Long userId);

    Integer insert(User user);

    Page<User> selectByRoleUser(Integer role);


    Integer update(User user);
}




