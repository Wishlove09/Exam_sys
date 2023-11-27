package edu.xmut.examsys.web.teacher;

import edu.xmut.examsys.bean.Clazz;
import edu.xmut.examsys.service.ClazzService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-27 21:58
 */
@Controller
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    ClazzService clazzService;

    @RequestMapping("/getAll")
    public R getALl() {
        List<Clazz> list = clazzService.getAll();
        return R.ok(list);
    }
}
