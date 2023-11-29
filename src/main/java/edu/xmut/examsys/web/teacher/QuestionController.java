package edu.xmut.examsys.web.teacher;

import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.SingleQuestionDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.QuestionService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static edu.xmut.examsys.constants.SystemConstant.ADD_FAIL;
import static edu.xmut.examsys.constants.SystemConstant.MISSING_ARGUMENT;

/**
 * @author 朔风
 * @date 2023-11-29 08:30
 */
@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @RequestMapping(value = "/pages", method = "post")
    public R pages(String json) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);
        PageVO pageVO = questionService.pages(pageDTO);

        return R.ok(pageVO);

    }

    @RequestMapping(value = "/add", method = "post")
    public R addQuestion(String json, HttpServletRequest request) {
        String action = request.getParameter("action");
        if (StringUtils.isBlank(action)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        Integer result = 0;
        switch (action) {
            case "single":
                SingleQuestionDTO singleQuestionDTO = JSONObject.parseObject(json, SingleQuestionDTO.class);
                result = questionService.adSingleQuestion(singleQuestionDTO);
                break;
            case "multi":

                break;
            case "judge":
                break;
            case "fill":
                break;
            default:
                return R.fail(ADD_FAIL);
        }

        return result > 0 ? R.ok() : R.fail();
    }
}
