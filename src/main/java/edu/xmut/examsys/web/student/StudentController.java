package edu.xmut.examsys.web.student;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.StudentService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;

/**
 * @author 朔风
 * @date 2023-11-27 20:32
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @RequestMapping(value = "/pages", method = "post")
    public R pages(String json) {
        if (StrUtil.isBlank(json)) {
            throw new GlobalException("分页信息不能为空");
        }
        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);
        PageVO pageVO = studentService.getAllStudent(pageDTO);

        return R.ok(pageVO);
    }
}
