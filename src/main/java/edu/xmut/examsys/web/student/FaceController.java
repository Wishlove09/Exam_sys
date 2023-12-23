package edu.xmut.examsys.web.student;

import edu.xmut.examsys.service.FaceEngineService;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author 朔风
 * @date 2023-12-23 22:57
 */
@Controller
@RequestMapping("/face")
public class FaceController {
    public final static Logger logger = LoggerFactory.getLogger(FaceController.class);

    @Autowired
    FaceEngineService faceEngineService;





}
