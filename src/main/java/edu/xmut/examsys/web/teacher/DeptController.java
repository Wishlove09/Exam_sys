package edu.xmut.examsys.web.teacher;

import edu.xmut.examsys.bean.Department;
import edu.xmut.examsys.service.DeptService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-28 15:29
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/getAll", method = "get")
    public R getAll() {
        List<Department> departments = deptService.getAll();
        return R.ok(departments);
    }
}
