package edu.xmut.examsys.web;

import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.dto.LoginDTO;
import edu.xmut.examsys.bean.dto.RegisterDTO;
import edu.xmut.examsys.bean.vo.LoginVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.UserService;
import edu.xmut.examsys.utils.JwtTokenUtil;
import edu.xmut.examsys.utils.MD5Utils;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author 朔风
 * @date 2023-11-13 18:21
 */
@Controller
public class CommonController {
    @Autowired
    private UserService userService;

    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();


    @RequestMapping(value = "/login", method = "post")
    public R login(String json, HttpServletRequest request) {
        if (StringUtils.isEmpty(json)) {
            return R.fail("账号密码不能为空");
        }
        LoginDTO loginDTO = JSONObject.parseObject(json, LoginDTO.class);
        User user = userService.login(loginDTO);
        // 生成token
        String token = jwtTokenUtil.generateToken(user);
        request.getSession().setAttribute("user", user);

        LoginVO loginVO = LoginVO.builder()
                .userId(user.getId())
                .token(token)
                .avatar(user.getAvatar())
                .role(user.getRole())
                .username(user.getUsername())
                .realName(user.getRealName())
                .sex(user.getSex())
                .build();
        return R.ok(loginVO).message("登录成功");
    }


    @RequestMapping(value = "/register", method = "post")
    public R register(String json) {
        if (Objects.isNull(json)) {
            throw new GlobalException("注册信息为空");
        }
        RegisterDTO registerDTO = JSONObject.parseObject(json, RegisterDTO.class);

        Boolean result = userService.register(registerDTO);

        return result ? R.ok("注册成功") : R.fail("注册失败");
    }
}
