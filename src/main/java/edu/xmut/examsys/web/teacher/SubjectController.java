package edu.xmut.examsys.web.teacher;

import edu.xmut.examsys.bean.Subject;
import edu.xmut.examsys.service.SubjectService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-19 16:36
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;


    @RequestMapping("/getAll")
    public R getAll() {
        List<Subject> subjects = subjectService.getAll();
        return R.ok(subjects);
    }


}
