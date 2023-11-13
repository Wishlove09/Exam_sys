package edu.xmut.examsys.web.teacher;


import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 朔风
 * @date 2023-11-12 15:30
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @RequestMapping(value = "/getAll", method = "get")
    public R getAll(HttpServletRequest request) {

        return R.ok();
    }

}
