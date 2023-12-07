package edu.xmut.examsys.web.student;

import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.ExamService;
import edu.xmut.examsys.utils.JwtTokenUtil;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

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
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String token = request.getHeader(AUTHORIZATION);
        String userId = jwtTokenUtil.getUserId(token);

        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);

        PageVO pageVO = examService.listByUser(pageDTO, userId);

        return R.ok(pageVO);
    }

}
