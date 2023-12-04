package edu.xmut.examsys.web.teacher;

import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.dto.*;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.QuestionDetailsVO;
import edu.xmut.examsys.bean.vo.QuestionVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.QuestionService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static edu.xmut.examsys.constants.SystemConstant.*;

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
        Boolean result = false;
        switch (action) {
            case "single":
                SingleQuestionDTO singleQuestionDTO = JSONObject.parseObject(json, SingleQuestionDTO.class);
                result = questionService.addSingleQuestion(singleQuestionDTO);
                break;
            case "multi":
                MultiQuestionDTO multiQuestionDTO = JSONObject.parseObject(json, MultiQuestionDTO.class);
                result = questionService.addMultiQuestion(multiQuestionDTO);
                break;
            case "judge":
                JudgeQuestionDTO judgeQuestionDTO = JSONObject.parseObject(json, JudgeQuestionDTO.class);
                result = questionService.addJudgeQuestion(judgeQuestionDTO);
                break;
            case "fill":
                FillQuestionDTO fillQuestionDTO = JSONObject.parseObject(json, FillQuestionDTO.class);
                result = questionService.addFillQuestion(fillQuestionDTO);
                break;
            default:
                return R.fail(ADD_FAIL);
        }

        return result ? R.ok(ADD_SUCCESS) : R.fail(ADD_FAIL);
    }

    @RequestMapping("/get")
    public R getByQid(HttpServletRequest request) {
        if (Objects.isNull(request.getParameter("id"))) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        String id = request.getParameter("id");

        QuestionDetailsVO questionDetailsVO = questionService.getById(id);

        return R.ok(questionDetailsVO);
    }


}
