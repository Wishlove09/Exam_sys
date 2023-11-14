package edu.xmut.examsys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.ExamInfo;
import edu.xmut.examsys.bean.dto.ExamInfoDTO;
import edu.xmut.examsys.service.ExamInfoService;
import edu.xmut.examsys.mapper.ExamInfoMapper;
import fun.shuofeng.myspringmvc.annotaion.Service;


/**
* @author shuofeng
* @description 针对表【exam_info(考试信息表)】的数据库操作Service实现
* @createDate 2023-11-13 23:08:33
*/
@Service
public class ExamInfoServiceImpl  implements ExamInfoService{

    @Override
    public Page<ExamInfoDTO> page(String page, String pageSize) {

        return null;
    }
}




