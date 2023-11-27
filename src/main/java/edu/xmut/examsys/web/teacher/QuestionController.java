package edu.xmut.examsys.web.teacher;

import com.alibaba.fastjson2.JSONObject;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.QuestionVO;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.FillQuestionService;
import edu.xmut.examsys.service.JudgeQuestionService;
import edu.xmut.examsys.service.MOrSQuestionService;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author 朔风
 * @date 2023-11-22 11:08
 */
@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private JudgeQuestionService judgeQuestionService;

    @Autowired
    private FillQuestionService fillQuestionService;

    @Autowired
    private MOrSQuestionService mOrSQuestionService;

    @RequestMapping(value = "/page", method = "post")
    public R pageQuestion(String json) {
        PageDTO pageDTO = JSONObject.parseObject(json, PageDTO.class);
        if (Objects.isNull(pageDTO)) {
            throw new GlobalException("分页数据为空");
        }
        // 开始查询所有题
        List<QuestionVO> mList = mOrSQuestionService.getAllWithContentAndType();
        List<QuestionVO> jList = judgeQuestionService.getAllWithContentAndType();
        List<QuestionVO> fList = fillQuestionService.getAllWithContentAndType();

        List<QuestionVO> all = new ArrayList<>();
        all.addAll(mList);
        all.addAll(jList);
        all.addAll(fList);


        PageVO<QuestionVO> pageVO = new PageVO<>();
        pageVO.setPageNum(pageDTO.getPageNum());
        pageVO.setPageSize(pageDTO.getPageSize());
        pageVO.setTotal(all.size());
        pageVO.setRecords(all);

        return R.ok(pageVO);
    }


}
