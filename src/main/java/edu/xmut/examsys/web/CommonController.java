package edu.xmut.examsys.web;

import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.Student;
import edu.xmut.examsys.bean.Teacher;
import edu.xmut.examsys.bean.dto.LoginDTO;
import edu.xmut.examsys.bean.dto.RegisterDTO;
import edu.xmut.examsys.bean.vo.LoginVO;
import edu.xmut.examsys.service.StudentService;
import edu.xmut.examsys.service.TeacherService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author 朔风
 * @date 2023-11-13 18:21
 */
@Controller
public class CommonController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;


    @RequestMapping(value = "/login", method = "post")
    public R login(String json) {
        if (StringUtils.isEmpty(json)) {
            return R.fail("账号密码为空");
        }
        LoginDTO loginDTO = JSONObject.parseObject(json, LoginDTO.class);
        Long userId = loginDTO.getUserId();
        String password = loginDTO.getPassword();
        String role = loginDTO.getRole();

        Student student = null;
        Teacher teacher = null;
        // 判断当前请求登录的用户角色
        if ("0".equals(role)) {
            // 学生
            student = studentService.login(userId, password);
            // 如果返回空对象，则证明账号密码有误
            if (Objects.isNull(student)) {
                return R.fail("账号密码错误");
            }
            LoginVO loginVO = LoginVO.builder()
                    .userId(student.getSno())
                    .username(student.getUsername())
                    .role("0")
                    .avatar(student.getAvatar())
                    .build();
            return R.ok(loginVO);
        } else if ("1".equals(role)) {
            // 教师
            teacher = teacherService.login(userId, password);
            // 如果返回空对象，则证明账号密码有误
            if (Objects.isNull(teacher)) {
                return R.fail("账号密码错误");
            }
            LoginVO loginVO = LoginVO.builder()
                    .userId(teacher.getTno())
                    .username(teacher.getUsername())
                    .role("1")
                    .avatar(teacher.getAvatar())
                    .build();
            return R.ok(loginVO);
        }
        return R.fail("登录失败");
    }


    @RequestMapping(value = "/register", method = "post")
    public R register(String json) {
        RegisterDTO registerDTO = JSONObject.parseObject(json, RegisterDTO.class);
        if (Objects.isNull(registerDTO)) {
            return R.fail("注册失败");
        }
        String role = registerDTO.getRole();
        if ("0".equals(role)) {
            // 学生

            return R.ok();
        } else if ("1".equals(role)) {
            // 教师
            Teacher teacher = Teacher.builder()
                    .tno(registerDTO.getUserId())
                    .username(registerDTO.getUsername())
                    .realName(registerDTO.getRealName())
                    .password(registerDTO.getPassword())
                    .phone(registerDTO.getPhone())
                    .sex(Integer.valueOf(registerDTO.getSex()))
                    .build();
            teacherService.register(teacher);
            return R.ok();
        } else {
            return R.fail("角色不存在！");
        }
    }
}
