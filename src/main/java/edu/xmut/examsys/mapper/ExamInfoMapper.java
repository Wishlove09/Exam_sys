package edu.xmut.examsys.mapper;

import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.ExamInfo;
import edu.xmut.examsys.bean.dto.PageDTO;

/**
* @author shuofeng
* @description 针对表【exam_info(考试信息表)】的数据库操作Mapper
* @createDate 2023-12-06 21:23:01
* @Entity edu.xmut.examsys.bean.ExamInfo
*/
public interface ExamInfoMapper {

    Page<ExamInfo> pages();
}




