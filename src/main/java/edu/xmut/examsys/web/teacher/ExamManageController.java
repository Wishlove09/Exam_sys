package edu.xmut.examsys.web.teacher;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
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

import static edu.xmut.examsys.constants.SystemConstant.MISSING_ARGUMENT;

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
    public R pages(String json) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);

        PageVO pageVO = examService.pages(pageDTO);

        return R.ok(pageVO);
    }

}
