package edu.xmut.examsys.web.teacher;

import edu.xmut.examsys.bean.vo.TeacherVO;
import edu.xmut.examsys.service.TeacherService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 朔风
 * @date 2023-11-29 09:03
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/get")
    public R getById(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));

        TeacherVO teacherVO = teacherService.getById(id);

        return R.ok(teacherVO);
    }
}
