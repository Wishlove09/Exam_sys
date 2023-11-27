package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.dto.LoginDTO;
import edu.xmut.examsys.bean.dto.RegisterDTO;

/**
 * @author 朔风
 * @date 2023-11-27 17:53
 */
public interface UserService {
    User login(LoginDTO loginDTO);

    Boolean register(RegisterDTO registerDTO);
}
