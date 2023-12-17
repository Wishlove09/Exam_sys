package edu.xmut.examsys.web.student;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.ExamAnswerRecord;
import edu.xmut.examsys.bean.ExamInfo;
import edu.xmut.examsys.bean.dto.*;
import edu.xmut.examsys.bean.vo.ExamDetailsVO;
import edu.xmut.examsys.bean.vo.ExamQuestionDetailVO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.StartExamVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.ExamService;
import edu.xmut.examsys.utils.JwtTokenUtil;
import edu.xmut.examsys.utils.R;
import edu.xmut.examsys.utils.UserUtils;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import jdk.nashorn.internal.scripts.JO;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static edu.xmut.examsys.constants.SystemConstant.AUTHORIZATION;
import static edu.xmut.examsys.constants.SystemConstant.MISSING_ARGUMENT;

/**
 * @author 朔风
 * @date 2023-12-07 08:59
 */
@Controller
@RequestMapping("/stu/exam")
public class ExamController {

    @Autowired
    private ExamService examService;


    @RequestMapping(value = "/list", method = "post")
    public R listByUser(String json, HttpServletRequest request) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        Long userId = UserUtils.getUserId(request);

        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);

        PageVO pageVO = examService.listByUser(pageDTO, userId);

        return R.ok(pageVO);
    }


    @RequestMapping("/details")
    public R details(HttpServletRequest request) {
        String examId = request.getParameter("examId");
        if (StringUtils.isBlank(examId)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        ExamDetailsVO examDetailsVO = examService.getDetailsById(examId);

        return R.ok(examDetailsVO);
    }

    @RequestMapping(value = "/isAllow", method = "get")
    public R isAllow(HttpServletRequest request) {
        String id = request.getParameter("examId");
        if (StringUtils.isBlank(id)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        Long userId = UserUtils.getUserId(request);
        Integer isExpired = examService.checkExamIsStartOrEnd(Long.parseLong(id));

        if (Objects.equals(isExpired, 0)) {
            return R.fail("考试暂未开始").code(500).data(0);
        }
        if (Objects.equals(isExpired, 2)) {
            return R.fail("考试已经结束").code(500).data(2);
        }
        Boolean isAllow = examService.checkExamPermission(Long.parseLong(id), userId);
        if (!isAllow) {
            return R.fail("你已经考过试了").code(500).data(4);
        }

        return R.ok().code(200).data(1);
    }

    @RequestMapping(value = "/start", method = "post")
    public R startExam(HttpServletRequest request, String json) {
        String id = request.getParameter("examId");
        if (StringUtils.isBlank(id)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }

        Long userId = UserUtils.getUserId(request);

        Integer isExpired = examService.checkExamIsStartOrEnd(Long.parseLong(id));
        if (!Objects.equals(isExpired, 1)) {
            return R.fail("不允许考试").code(500);
        }
        Boolean isAllow = examService.checkExamPermission(Long.parseLong(id), userId);
        if (!isAllow) {
            return R.fail("不允许考试！").code(500);
        }

        StartExamVO startExamVO = examService.startExam(Long.parseLong(id), userId);


        return R.ok(startExamVO);
    }


    @RequestMapping(value = "/question/detail", method = "get")
    public R getQuestionDetail(HttpServletRequest request) {
        String qid = request.getParameter("qid");
        if (StringUtils.isBlank(qid)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        ExamQuestionDetailVO examQuestionDetailVO = examService.getQuestionDetailByQid(qid);

        return R.ok(examQuestionDetailVO);
    }

    @RequestMapping(value = "/answer/save", method = "post")
    public R answerSave(String json, HttpServletRequest request) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }

        ExamSaveDTO examSaveDTO = JSONObject.parseObject(json, ExamSaveDTO.class);
        if (Objects.isNull(examSaveDTO.getSelectIds()) || examSaveDTO.getSelectIds().isEmpty()) {
            return R.ok();
        }

        Boolean b = examService.saveAnswer(examSaveDTO, request);

        return b ? R.ok() : R.fail("保存失败");
    }

    @RequestMapping(value = "/get/answered", method = "post")
    public R getAnswered(String json, HttpServletRequest request) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }

        ExamAnsweredQueryDTO examAnsweredQueryDTO = JSONObject.parseObject(json, ExamAnsweredQueryDTO.class);

        ExamAnsweredVO answered = examService.getAnswered(examAnsweredQueryDTO, request);

        if (Objects.isNull(answered)) {
            return R.fail("未答题").code(400);
        }
        return R.ok(answered);
    }


    @RequestMapping(value = "/submit", method = "post")
    public R submit(String json, HttpServletRequest request) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }

        ExamSubmitDTO examSubmitDTO = JSONObject.parseObject(json, ExamSubmitDTO.class);

        examService.submitExam(examSubmitDTO, request);


        return R.ok();
    }


}
