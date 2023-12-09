package edu.xmut.examsys.web.teacher;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.dto.ExamAddDTO;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.ExamInfoVO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.ExamService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static edu.xmut.examsys.constants.SystemConstant.MISSING_ARGUMENT;
import static edu.xmut.examsys.constants.SystemConstant.USER_ID;

/**
 * @author 朔风
 * @date 2023-12-06 21:24
 */
@RequestMapping("/exam")
@Controller
public class ExamManageController {

    @Autowired
    private ExamService examService;


    @RequestMapping(value = "/pages", method = "post")
    public R pages(String json, HttpServletRequest request) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);
        Long userId = Long.valueOf((String) request.getAttribute(USER_ID));
        PageVO pageVO = examService.pages(pageDTO, userId);

        return R.ok(pageVO);
    }


    @RequestMapping(value = "/add", method = "post")
    public R add(String json, HttpServletRequest request) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }

        ExamAddDTO examAddDTO = JSONObject.parseObject(json, ExamAddDTO.class);

        Boolean result = examService.addExam(examAddDTO, request);

        return result ? R.ok("添加成功") : R.fail("添加失败");
    }
}
