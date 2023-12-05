package edu.xmut.examsys.web.teacher;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.PaperDetailsDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.PaperService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import static edu.xmut.examsys.constants.SystemConstant.MISSING_ARGUMENT;

/**
 * @author 朔风
 * @date 2023-11-30 10:39
 */
@Controller
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    PaperService paperService;


    @RequestMapping(value = "/pages", method = "post")
    public R pages(String json) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);
        PageVO pageVO = paperService.pages(pageDTO);

        return R.ok(pageVO);
    }


    @RequestMapping(value = "/add", method = "post")
    public R addPaper(String json, HttpServletRequest request) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        PaperDetailsDTO paperDetailsDTO = JSONObject.parseObject(json, PaperDetailsDTO.class);
        Boolean result = paperService.addPaper(paperDetailsDTO, request);

        return result ? R.ok("添加成功") : R.fail("添加失败");
    }
}
