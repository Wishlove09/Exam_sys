package edu.xmut.examsys.web.teacher;


import edu.xmut.examsys.bean.Teacher;
import edu.xmut.examsys.mapper.TeacherMapper;
import edu.xmut.examsys.service.TeacherService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-12 15:30
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/getAll", method = "get")
    public R getAll(HttpServletRequest request) {
        List<Teacher> all = teacherService.getAll();
        System.out.println(request.getSession().getId());
        return R.ok(all);
    }


}
