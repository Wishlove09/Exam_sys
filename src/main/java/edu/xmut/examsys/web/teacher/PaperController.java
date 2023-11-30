package edu.xmut.examsys.web.teacher;

import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import static edu.xmut.examsys.constants.SystemConstant.MISSING_ARGUMENT;

/**
 * @author 朔风
 * @date 2023-11-30 10:39
 */
@Controller
@RequestMapping("/paper")
public class PaperController {


    @RequestMapping(value = "/pages", method = "post")
    public R pages(String json) {
        if (StringUtils.isBlank(json)) {
            throw new GlobalException(MISSING_ARGUMENT);
        }



        return R.ok();
    }
}
