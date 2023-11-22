package edu.xmut.examsys.web.student;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.StudentVO;
import edu.xmut.examsys.service.StudentService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;

/**
 * @author 朔风
 * @date 2023-11-14 00:11
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/pages", method = "post")
    public R page(String json) {
        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);
        PageVO<StudentVO> page = studentService.page(pageDTO.getPageNum(), pageDTO.getPageSize());
        return R.ok(page);
    }
}
