package edu.xmut.examsys.web.teacher;

import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.Clazz;
import edu.xmut.examsys.bean.vo.ClazzVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.ClazzService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author 朔风
 * @date 2023-11-17 11:52
 */
@Controller
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClazzService clazzService;

    @RequestMapping(value = "/add", method = "post")
    public R add(String json) throws Exception {
        if (StringUtils.isBlank(json)) {
            return R.fail("传入的参数为空");
        }
        Clazz clazz = JSONObject.parseObject(json, Clazz.class);
        Boolean result = clazzService.add(clazz);
        return result ? R.ok("添加成功") : R.fail("添加失败");
    }


    @RequestMapping(value = "/get")
    public R getByClassId(HttpServletRequest request) {
        String classId = request.getParameter("classId");
        if (StringUtils.isBlank(classId)) {
            return R.fail("班级id不能为空");
        }
        ClazzVO clazzVO = clazzService.getByClazzId(Long.valueOf(classId));
        return R.ok(clazzVO);
    }
}
