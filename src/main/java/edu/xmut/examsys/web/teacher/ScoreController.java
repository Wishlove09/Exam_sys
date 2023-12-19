package edu.xmut.examsys.web.teacher;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.Score;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.ScoreQueryPageDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.StudentScoreVO;
import edu.xmut.examsys.bean.vo.StudentVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.ScoreService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static edu.xmut.examsys.constants.SystemConstant.MISSING_ARGUMENT;

/**
 * @author 朔风
 * @date 2023-12-17 16:25
 */
@RequestMapping("/score")
@Controller
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @RequestMapping(value = "/student/get", method = "get")
    public R getStudentByUserId(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (StringUtils.isBlank(userId)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        StudentVO studentVO = scoreService.getStudentByUserId(Long.valueOf(userId));

        return R.ok(studentVO);
    }


    @RequestMapping(value = "/student/pages", method = "post")
    public R studentPages(String json) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        ScoreQueryPageDTO pageDTO = JSONObject.parseObject(json, ScoreQueryPageDTO.class);
        if (Objects.isNull(pageDTO.getTitle()) && Objects.isNull(pageDTO.getUserId())) {
            return R.ok();
        }
        PageVO pageVO = scoreService.studentListPages(pageDTO);
        return R.ok(pageVO);
    }


    @RequestMapping(value = "/exam/getAll", method = "post")
    public R getAllExam(String json) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        PageVO pageVO = scoreService.getAllExam(JSONObject.parseObject(json, PageDTO.class));

        return R.ok(pageVO);
    }

    @RequestMapping("/exam/analysis")
    public R examAnalysis(HttpServletRequest request) {
        String examId = request.getParameter("examId");
        if (StringUtils.isBlank(examId)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }

        List<Score> scores = scoreService.statisticalScoreInterval(examId);

        return R.ok(scores);
    }

    @RequestMapping(value = "/student/query", method = "post")
    public R queryStudentScore(HttpServletRequest request, String json) {
        if (StringUtils.isAnyBlank(json, request.getParameter("userId"))) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        String userId = request.getParameter("userId");
        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);
        PageVO pageVO = scoreService.queryStudentScoreList(Long.parseLong(userId),pageDTO);
        return R.ok(pageVO);
    }


}
