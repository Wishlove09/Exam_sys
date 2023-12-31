package edu.xmut.examsys.web.teacher;

import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.Clazz;
import edu.xmut.examsys.bean.dto.ClazzDTO;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.ClazzVO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.ClazzService;
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
 * @date 2023-11-27 21:58
 */
@Controller
@RequestMapping("/class")
public class ClazzController {

    @Autowired
    ClazzService clazzService;

    @RequestMapping("/getAll")
    public R getAll() {
        List<Clazz> list = clazzService.getAll();
        return R.ok(list);
    }

    @RequestMapping(value = "/pages", method = "post")
    public R pages(String json) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);
        PageVO pageVO = clazzService.pages(pageDTO);

        return R.ok(pageVO);
    }

    @RequestMapping(value = "/add", method = "post")
    public R add(String json) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }
        ClazzDTO clazzDTO = JSONObject.parseObject(json, ClazzDTO.class);
        Boolean result = clazzService.add(clazzDTO);

        return result ? R.ok("添加成功") : R.fail();
    }

    @RequestMapping("/delete")
    public R delete(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        Boolean result = clazzService.deleteById(id);

        return result ? R.ok() : R.fail();
    }


    @RequestMapping("/get/search")
    public R search(HttpServletRequest request) {
        String q = request.getParameter("q");
        if (Objects.isNull(q)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }

        List<ClazzVO> clazzVOS = clazzService.search(q);

        return R.ok(clazzVOS);
    }
}
