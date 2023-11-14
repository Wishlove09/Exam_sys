package edu.xmut.examsys.service;

import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.ExamInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.xmut.examsys.bean.dto.ExamInfoDTO;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【exam_info(考试信息表)】的数据库操作Service
 * @createDate 2023-11-13 23:08:33
 */
public interface ExamInfoService {

    Page<ExamInfoDTO> page(String page, String pageSize);

}
