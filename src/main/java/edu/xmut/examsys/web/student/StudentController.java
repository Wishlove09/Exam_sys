package edu.xmut.examsys.web.student;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.StudentDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.StudentVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.StudentService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static edu.xmut.examsys.constants.SystemConstant.MISSING_ARGUMENT;

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
            throw new GlobalException(MISSING_ARGUMENT);
        }
        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);
        PageVO pageVO = studentService.getAllStudent(pageDTO);

        return R.ok(pageVO);
    }

    @RequestMapping(value = "/get")
    public R getById(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        StudentVO studentVO = studentService.getById(id);


        return R.ok(studentVO);
    }

    @RequestMapping(value = "/update", method = "post")
    public R update(String json) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        StudentDTO studentDTO = JSONObject.parseObject(json, StudentDTO.class);
        Boolean result = studentService.update(studentDTO);

        return result ? R.ok() : R.fail();
    }

    @RequestMapping(value = "/status", method = "get")
    public R updateStatus(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        Boolean result = studentService.updateStatus(id);
        return result ? R.ok() : R.fail();
    }
}
